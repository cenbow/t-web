package com.shangpin.biz.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shangpin.base.service.ShangPinService;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangpin.biz.service.SPBizBrandActivityService;
import com.shangpin.biz.service.abstraction.AbstractBizBrandActivityService;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class SPBizBrandActivityServiceImpl extends AbstractBizBrandActivityService implements SPBizBrandActivityService {
    private static final Logger logger = LoggerFactory.getLogger(SPBizBrandActivityServiceImpl.class);


    @Autowired
    private ShangPinService shangPinService;
    @Override
    public BrandActivityHead headInfoObj(String userid, String id, String type) {
        BrandActivityHead brandActivityHead = this.headInfo(userid, id, type);
        if(null == brandActivityHead){
            return null;
        }
        if("0".equals(type)){
            if(null ==brandActivityHead.getHead()){
                return null;
            }
            String about = Constants.BRAND_STORY_URL + "?id=" + id + "&type=0";
            logger.debug("brand story url :" + about);
            brandActivityHead.getHead().setAbout(about);
        }
        return brandActivityHead;
    }


    @Override
    public String subjectFloorInfo(String topicId) {
        return shangPinService.subjectFloorInfo(topicId);
    }

    @Override
    public List<BrandActivityCoupon> couponInfoObj(String userId, String id, String type) {
        String json = shangPinService.findCouponInfo(userId, id, type);
        if(StringUtils.isEmpty(json)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(json);
            String code = jsonNode.path("code").asText();
            String couponStr = jsonNode.path("content").path("coupon").toString();
            logger.debug("coupon json data:" + couponStr);
            if(!"0".equals(code) || StringUtils.isEmpty(couponStr)){
                return null;
            }
            List<BrandActivityCoupon> coupons = JSONUtils.json2list(couponStr, BrandActivityCoupon.class);
            return coupons;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Gallery> queryGalleryList(String type, String frames, String brandId,String isSizePic) {
        String json = shangPinService.queryGalleryList(type, frames, brandId,isSizePic);
//		String json = "{\"code\": \"0\",\"msg\": \"\",\"content\": {\"promotion\": [{\"name\": \"分组名称\",\"list\": [{\"name\": \"分类名称\",\"pic\": \"http://www.shangpin.com\",\"type\": \"通用规则\",\"refContent\": \"http://www.shangpin.com\"}]}]}}";
        if(StringUtils.isEmpty(json)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(json);
            String code = jsonNode.path("code").asText();
            String proStr = jsonNode.path("content").path("gallery").toString();
            logger.debug("template json data:" + proStr);
            if(!"0".equals(code) || StringUtils.isEmpty(proStr)){
                return null;
            }
            List<Gallery> gallery = JSONUtils.json2list(proStr, Gallery.class);
            return gallery;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BrandActivityModelOne modelOneObj(String id, String type, String version) {
        String json = shangPinService.findModelInfo(id, type, version,null);
//		String json = "{\"code\": \"0\",\"msg\": \"\",\"content\": {\"promotion\": [{\"name\": \"分组名称\",\"list\": [{\"name\": \"分类名称\",\"pic\": \"http://www.shangpin.com\",\"type\": \"通用规则\",\"refContent\": \"http://www.shangpin.com\"}]}]}}";
        if(StringUtils.isEmpty(json)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(json);
            String code = jsonNode.path("code").asText();
            String proStr = jsonNode.path("content").toString();
            logger.debug("template json data:" + proStr);
            if(!"0".equals(code) || StringUtils.isEmpty(proStr)){
                return null;
            }
            BrandActivityModelOne modelOne = (BrandActivityModelOne) JSONUtils.json2pojo(proStr, BrandActivityModelOne.class);
            return modelOne;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<BrandActivityPromotions> promotionsObj(String id, String type) {
        String json = shangPinService.findPromotionInfo(id, type);
//		String json = "{\"code\": \"0\",\"msg\": \"\",\"content\": {\"promotion\": [{\"name\": \"分组名称\",\"list\": [{\"name\": \"分类名称\",\"pic\": \"http://www.shangpin.com\",\"type\": \"通用规则\",\"refContent\": \"http://www.shangpin.com\"}]}]}}";
        if(StringUtils.isEmpty(json)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(json);
            String code = jsonNode.path("code").asText();
            String proStr = jsonNode.path("content").path("promotion").toString();
            logger.debug("promotion json data:" + proStr);
            if(!"0".equals(code) || StringUtils.isEmpty(proStr)){
                return null;
            }
            List<BrandActivityPromotions> promotions = JSONUtils.json2list(proStr, BrandActivityPromotions.class);
            return promotions;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
