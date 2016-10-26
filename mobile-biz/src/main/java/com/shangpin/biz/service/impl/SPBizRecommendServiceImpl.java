package com.shangpin.biz.service.impl;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.service.SPBizRecommendService;
import com.shangpin.biz.service.abstraction.AbstractBizRecommendService;
import com.shangpin.biz.utils.ApiBizData;
import com.shangpin.biz.utils.SearchUtil;
import com.shangpin.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPBizRecommendServiceImpl extends AbstractBizRecommendService implements SPBizRecommendService {

	@Override
	public List<Recommend> doRecommendList(String userId) throws Exception {
		return doRecommends(userId);
	}

	@Override
	public String doRecProduct(String type, String userId, String imei, String coord, String ip, String pageIndex, String pageSize) throws Exception {
		String data = null;
		RecProductFor rec = doBaseRecProduct(type, userId, imei, coord, ip, pageIndex, pageSize);
		if (rec != null) {
			rec.setSystime(String.valueOf(System.currentTimeMillis()));
		}
		if (null == rec) {
			data = ApiBizData.spliceData(CodeMsgEnum.CODE_ERROR.getInfo(), CodeMsgEnum.MSG_ERROR.getInfo());
		} else {
			data = ApiBizData.spliceData(rec, CodeMsgEnum.CODE_SUCCESS.getInfo(), CodeMsgEnum.MSG_SUCCESS.getInfo());
		}
		return data;
	}

	@Override
	public WorthTitle doRecProduct(String type, String userId, String pageIndex, String pageSize) throws Exception {
		WorthTitle worthTitle = doBaseRecWorth(type, userId, pageIndex, pageSize);
		return worthTitle;
	}
	@Override
    public List<Product> doRecProduct(String type, String userId, String shopType,String productId,String pageIndex, String pageSize) throws Exception {
        List<Product> products = new ArrayList<Product>();
        products=doBase(type, userId, shopType,productId,pageIndex, pageSize);
        return products;
    }

	@Override
	public List<Product> products(List<String> product,String userId, String version) {
		String json=recRuleService.findByProductNos(product);
		List<Product> list = null;
		List<Product> resultlist = new ArrayList<Product>();
		SearchProductJson<ProductSreach> productJson = JsonUtil.fromJson(json, new TypeToken<SearchProductJson<ProductSreach>>(){}.getType());
		if(productJson!=null){
			logger.debug("searchProductListFloor   result:{description:"+productJson.getDiscription()+", qtime:"+productJson.getQTime()+", status:"+productJson.getStatus()+" ,total:"+productJson.getTotal()+" ,sid:"+productJson.getSid()+" ,dosCount:"+productJson.getDocs().size());
			//查询结果大于0
			List<ProductSreach> searchListDoc = productJson.getDocs();
			if(productJson.getTotal()>0 && searchListDoc!=null && searchListDoc.size()>0){
				list = SearchUtil.initJsonProductList(searchListDoc, "1", null, version);
				//设置下图片宽和高
				for (Product pro:list) {
					pro.setWidth("320");
					pro.setHeight("426");
					resultlist.add(pro);
				}

			}
		}
		return resultlist;
	}

}

