package com.shangpin.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shangpin.core.dao.IPushconfShangpinDAO;
import com.shangpin.core.entity.PushconfShangpin;
import com.shangpin.core.service.IPushconfShangpinService;

@Service
@Transactional
public class PushconfShangpinServiceImpl implements IPushconfShangpinService {

    @Autowired
    private IPushconfShangpinDAO dao;

    @Override
    public PushconfShangpin add(PushconfShangpin pushconfShangpin) {
        return dao.save(pushconfShangpin);
    }

    @Override
    public PushconfShangpin modify(PushconfShangpin pushconfShangpin) {
        return dao.save(pushconfShangpin);
    }

    @Override
    public void deleteById(Long id) {
        dao.delete(id);
    }

    @Override
    public PushconfShangpin findById(Long id) {
        return dao.findOne(id);
    }

    @Override
    public PushconfShangpin findByUserId(String userId) {
        return dao.findByUserId(userId);
    }

    @Override
    public List<String> findByMsgType(int msgType) {
        final int mt = msgType;
        List<PushconfShangpin> list = dao.findAll(new Specification<PushconfShangpin>() {
            @Override
            public Predicate toPredicate(Root<PushconfShangpin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> isOpenPath = root.get("isOpen");
                Path<String> msgTypePath = root.get("msgType");
                Path<String> tokenPath = root.get("token");
                List<Predicate> plist = new ArrayList<Predicate>();
                plist.add(cb.equal(isOpenPath, 1));
                if (mt >= 0 && mt <= 2) {
                    plist.add(cb.equal(msgTypePath, mt));
                }
                query.groupBy(tokenPath);
                if (plist.size() > 0) {
                    return cb.and(plist.toArray(new Predicate[plist.size()]));
                }
                return cb.conjunction();
            }
        });

        List<String> tokenList = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            for (PushconfShangpin bean : list) {
                tokenList.add(bean.getToken());
            }
        }
        return tokenList;
    }

    @Override
    public List<String> findByMsgTypeContain(int msgType) {
        final int mt = msgType;
        List<PushconfShangpin> list = dao.findAll(new Specification<PushconfShangpin>() {
            @Override
            public Predicate toPredicate(Root<PushconfShangpin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> isOpenPath = root.get("isOpen");
                Path<String> msgTypePath = root.get("msgType");
                Path<String> tokenPath = root.get("token");
                List<Predicate> plist = new ArrayList<Predicate>();
                plist.add(cb.equal(isOpenPath, 1));
                switch (mt) {
                case 0:
                    plist.add(cb.or(cb.equal(msgTypePath, 1), cb.equal(msgTypePath, 2)));
                    break;
                case 1:
                    plist.add(cb.or(cb.equal(msgTypePath, 1), cb.equal(msgTypePath, 2)));
                    break;
                default:
                    break;
                }
                query.groupBy(tokenPath);
                if (plist.size() > 0) {
                    return cb.and(plist.toArray(new Predicate[plist.size()]));
                }
                return cb.conjunction();
            }
        });

        List<String> tokenList = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            for (PushconfShangpin bean : list) {
                tokenList.add(bean.getToken());
            }
        }
        return tokenList;
    }

    @Override
    public void savePushConfig(String userId, String gender, String token, String imei, int isOpen, int msgType) {
        synchronized (token.intern()) {
            PushconfShangpin bean = findByUserId(userId);
            if (bean != null) {
                setPushconf(bean, null, token, gender, imei, isOpen, msgType, null, new Date());
                modify(bean);
            } else {
                List<PushconfShangpin> beanList = findByToken(token);
                if (beanList != null && beanList.size() > 0) {
                    int nullIndex = -1;
                    for (int i = beanList.size() - 1; i >= 0; i--) {
                        PushconfShangpin entity = beanList.get(i);
                        if (entity.getUserId() == null) {
                            nullIndex = i;
                            setPushconf(entity, userId, null, gender, null, isOpen, msgType, null, new Date());
                            modify(entity);
                            break;
                        }
                    }
                    if (nullIndex == -1) {
                        PushconfShangpin pushBean = new PushconfShangpin();
                        setPushconf(pushBean, userId, token, gender, imei, 1, 2, new Date(), new Date());
                        add(pushBean);
                    }
                } else {
                    PushconfShangpin entity = new PushconfShangpin();
                    setPushconf(entity, userId, token, gender, imei, isOpen, msgType, new Date(), new Date());
                    add(entity);
                }
            }
        }
    }

    @Override
    public List<PushconfShangpin> findByToken(String token) {
        return dao.findByToken(token);
    }

    @Override
    public void saveToken(String token, String userId, String imei, String gender) {
        synchronized (token.intern()) {
            if (StringUtils.isBlank(userId)) {
                // userId为空，查询token是否存在，如果存在则不insert，否则保存。
                List<PushconfShangpin> beanList = findByToken(token);
                if (beanList == null || beanList.size() == 0) {
                    PushconfShangpin bean = new PushconfShangpin();
                    setPushconf(bean, null, token, gender, imei, 1, 2, new Date(), new Date());
                    add(bean);
                }
            } else {
                // userId不为空，查找该userId是否存在，如果存在update
                // token，如果不存在先判断是否存在token，如果token存在补全该token记录，如果token也不存在则save
                PushconfShangpin model = findByUserId(userId);
                if (model != null) {
                    setPushconf(model, null, token, gender, imei, null, null, null, new Date());
                    modify(model);
                } else {
                    List<PushconfShangpin> modelList = findByToken(token);
                    if (modelList == null || modelList.size() == 0) {
                        PushconfShangpin entity = new PushconfShangpin();
                        setPushconf(entity, userId, token, gender, imei, 1, 2, new Date(), new Date());
                        add(entity);
                    } else if (modelList != null && modelList.size() > 0) {
                        int nullIndex = -1;
                        for (int i = modelList.size() - 1; i >= 0; i--) {
                            PushconfShangpin bean = modelList.get(i);
                            if (bean.getUserId() == null) {
                                nullIndex = i;
                                setPushconf(bean, userId, null, gender, null, null, null, null, new Date());
                                modify(bean);
                                break;
                            }
                        }
                        if (nullIndex == -1) {
                            PushconfShangpin bean = new PushconfShangpin();
                            setPushconf(bean, userId, token, gender, imei, 1, 2, new Date(), new Date());
                            add(bean);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置对象值 如果值为null,则表示不修改
     * 
     * @author zhanghongwei
     */
    private void setPushconf(PushconfShangpin entity, String userId, String token, String gender, String imei, Integer isOpen, Integer msgType, Date createTime, Date updateTime) {
        if (gender != null) {
            entity.setImei(imei);
        }
        if (imei != null) {
            entity.setIsOpen(isOpen);
        }
        if (isOpen != null) {
            entity.setMsgType(msgType);
        }
        if (msgType != null) {
            entity.setGender(gender);
        }
        if (createTime != null) {
            entity.setCreateTime(createTime);
        }
        if (updateTime != null) {
            entity.setUpdateTime(updateTime);
        }
    }

}
