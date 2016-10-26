package com.shangpin.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.shangpin.base.service.RecRuleService;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.utils.HttpClientInnerUtil;
import com.shangpin.utils.HttpClientUtil;
import com.shangpin.utils.StringUtil;
@Service
public class RecRule implements RecRuleService {

	//推荐规则URL
    private StringBuffer sendRedURL =new StringBuffer(GlobalConstants.BASE_URL_REPCONMONED).append("rs");
    //商品详情页推荐商品
    private StringBuffer rmdProductDetailURL =new StringBuffer(GlobalConstants.BASE_URL_REPCONMONED).append("viewAR");
   //搜索商品地址
   private StringBuilder subjectProductListURL = new StringBuilder(GlobalConstants.BASE_URL_SEARCH).append("Product");
	public String doRecRuleProduct(String userId, String imei,String offset,String num)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userId);
		if(StringUtil.isNotEmpty(imei)){
			map.put("imei", imei);
		}
		map.put("offset",offset );//开始查询的条数
		if(null!=num&&!"".equals(num)){
			map.put("num", num);
		}else{
			map.put("num", "0");
		}
		String result=HttpClientInnerUtil.doGet(sendRedURL.toString(), map);
        return result;
	}

    @Override
    public String doRecRuleCookie(String userId, String uvId, String offset, String num) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        if(StringUtil.isNotEmpty(userId)){
            map.put("userid", userId);
        }
        if(StringUtil.isNotEmpty(uvId)){
            map.put("uvid", uvId);
        }
        map.put("offset",offset );//开始查询的条数
        if(null!=num&&!"".equals(num)){
            map.put("num", num);
        }else{
            map.put("num", "0");
        }
        String result=HttpClientInnerUtil.doGet(sendRedURL.toString(), map);
        return result;
    }

    @Override
	public String findByProductNos(List<String> productNos) {
		if(CollectionUtils.isEmpty(productNos)){
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < productNos.size(); i++) {
			String str=	productNos.get(i).toString();
			builder.append(str).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageSize",String.valueOf(productNos.size()));
		params.put("productNo", builder.toString());
		String result=HttpClientInnerUtil.doGet(subjectProductListURL.toString(), params);
		
		return result;
	}
	
	@Override
	public String productDetailRmd(String userid, String imei, String uvid,
			String location, String ip, String brandId, String categoryId,
			String itemId, String num, String offset) {
		Map<String, String> params = new HashMap<String, String>();
		if(!StringUtils.isEmpty(userid)){
			params.put("userid", userid);
		}
		if(!StringUtils.isEmpty(imei)){
			params.put("imei", imei);
		}
		if(!StringUtils.isEmpty(uvid)){
			params.put("uvid", uvid);
		}
		if(!StringUtils.isEmpty(location)){
			params.put("location", location);
		}
		if(!StringUtils.isEmpty(ip)){
			params.put("ip", ip);
		}
		if(!StringUtils.isEmpty(categoryId)){
			params.put("categoryId", categoryId);
		}
		params.put("brandId", brandId);
		params.put("itemId", itemId);
		params.put("num", num);
		params.put("offset", offset);
		return HttpClientInnerUtil.doGet(rmdProductDetailURL.toString(), params);
	}

}
