package com.shangpin.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.LogisticsInfo;
import com.shangpin.biz.bo.LogisticsShow;
import com.shangpin.biz.bo.ReturnContent;
import com.shangpin.biz.bo.base.ResultBaseNew;
import com.shangpin.biz.bo.base.ResultObjList;
import com.shangpin.biz.service.SPBizReturnGoodsService;
import com.shangpin.biz.service.abstraction.AbstractBizReturnGoodsService;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringUtil;

@Service
public class SPBizReturnGoodsServiceImpl extends AbstractBizReturnGoodsService implements SPBizReturnGoodsService {
    /**
     * 返回实体类ResultBaseNew
     */
    @Override
    public ResultBaseNew returnDetailPojo(String userId,String returnId) {
        String date = this.returnDetail(userId, returnId);
        if (StringUtils.isBlank(date)) {
            return null;
        }
        ResultBaseNew resultBaseNew = ResultBaseNew.formatToPojo(date,ReturnContent.class);
        return resultBaseNew;
    }
    /**
     * 退货申请提交
     * @param userId
     * @param orderNo
     * @param productNo
     * @param orderDetailNo
     * @param skuNo
     * @param returnReason
     * @param bankInfo
     * @param imageList
     */
    @Override
    public String returnSubmit(String userId, String orderNo, String productNo,
            String orderDetailNo, String skuNo, String returnReason,
            String bankInfo, String imageList, String returnReasonDetail,
            String refundType, String remark) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 物流公司
     */
	@Override
	public List<LogisticsShow> findListLogisticsDatail() {
		Gson gson = new Gson();
		List<LogisticsShow> lInfos=new ArrayList<LogisticsShow>(); 
		String json = findListLogisticsDatails();
		if (StringUtil.isNotEmpty(json)) {
			List<LogisticsInfo> logisticsInfos = gson.fromJson(json, new TypeToken<List<LogisticsInfo>>(){}.getType()); 
			if (logisticsInfos!=null && logisticsInfos.size()>0) {
				for (LogisticsInfo logisticsInfo : logisticsInfos) {
					LogisticsShow lShow=new LogisticsShow ();
					lShow.setValue(logisticsInfo.getDeliveryCompanyName());
					lShow.setCode(logisticsInfo.getCompanyCode());
					lInfos.add(lShow);
				}				
			}
		}
		return lInfos; 
	}
}
