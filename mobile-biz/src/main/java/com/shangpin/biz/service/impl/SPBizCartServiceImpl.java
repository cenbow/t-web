package com.shangpin.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shangpin.base.service.ShoppingCartService;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.cart.CartModifyParamV3;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangpin.biz.bo.CartContent;
import com.shangpin.biz.bo.CartQuantity;
import com.shangpin.biz.bo.RecProduct;
import com.shangpin.biz.bo.Settlement;
import com.shangpin.biz.bo.ShopCartItem;
import com.shangpin.biz.bo.base.ResultObjMapList;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.cart.Cart;
import com.shangpin.biz.service.SPBizCartService;
import com.shangpin.biz.service.abstraction.AbstractBizCartService;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.JSONUtils;

/**
 * @ClassName: BizCartServiceImpl
 * @Description:购物车接口的实现类
 * @author qinyingchun
 * @date 2014年11月5日
 * @version 1.0
 */
@Service
public class SPBizCartServiceImpl extends AbstractBizCartService implements SPBizCartService {

	private static final Logger logger = LoggerFactory.getLogger(SPBizCartServiceImpl.class);

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Override
	public List<RecProduct> findRecProductObj(String userId, String type, String pageIndex, String pageSize) {
		try {
			ResultObjMapList<RecProduct> obj = beRecProduct(userId, type, Constants.SHOP_TYPE_SHANGPIN, pageIndex,
					pageSize);
			if (obj != null && obj.isSuccess()) {
				return obj.getList("productList");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Cart doShowCartV2(String userId, String isChecked){
		try{
			String json=doBaseShowCartV2(userId,isChecked);
		logger.debug("调用base接口返回数据:" + json);
		ResultObjOne<Cart> obj = JSONUtils
				.toGenericsCollection(json, ResultObjOne.class,
						Cart.class);
		if(null ==obj)
			return null;
		
		String code = obj.getCode();
		if (!Constants.SUCCESS.equals(code)) {
			return null;
		}
		return obj.getObj();
		}catch (Exception e) {
			logger.error("调用base接口返回数据发生错误！");
			e.printStackTrace();
		}
		return null;
			
	}

	@Override
	public int cartCount(String userId, String isChecked){
		try{

			JSONObject reqJSON = new JSONObject();
			reqJSON.element("userId",userId);
			reqJSON.element("requestFrom", GlobalConstants.ORDER_REQUEST_FRMO);
			reqJSON.element("versionNo",GlobalConstants.ORDER_VERSION);
			String json=shoppingCartService.cartCountV3(reqJSON.toString());
			logger.debug("调用base接口返回数据:" + json);
			ResultObjOne<Map<String,String>> obj = JSONUtils
					.toGenericsCollection(json, ResultObjOne.class,
							Map.class);
			if(null ==obj)
				return 0;

			String code = obj.getCode();
			if (!Constants.SUCCESS.equals(code)) {
				return 0;
			}
			return Integer.parseInt(obj.getObj().get("cartGoodsCount"));
		}catch (Exception e) {
			logger.error("调用base接口返回数据发生错误！");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Map<String, Object> doModifyCartV2(String userId, String cartItem, String isChecked,String channelNo, String channelId) {
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			String json= doBaseModifyCartV2(userId, cartItem, isChecked, channelNo, channelId);
			logger.debug("调用base接口返回数据:" + json);
			ResultObjOne<Cart> obj = JSONUtils.toGenericsCollection(json, ResultObjOne.class, Cart.class);
			if(null ==obj)
				return null;
			String code = obj.getCode();
			String msg=obj.getMsg();
			map.put("code", code);
			map.put("msg", msg);
			if (!Constants.SUCCESS.equals(code)) {
				return map;
			}
			map.put("cart", obj.getObj());
			return map;
		}catch(Exception e){
			logger.error("调用base接口返回数据发生错误！");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map<String, Object> doDeleteCartV2(String userId, String cartDetailId, String isChecked) {
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			String json= doBaseDeleteCartV2(userId, cartDetailId, isChecked);
			logger.debug("调用base接口返回数据:" + json);
			ResultObjOne<Cart> obj = JSONUtils
					.toGenericsCollection(json, ResultObjOne.class,
							Cart.class);
			if(null ==obj)
				return null;
			String code = obj.getCode();
			String msg=obj.getMsg();
			map.put("code", code);
			map.put("msg", msg);
			if (!Constants.SUCCESS.equals(code)) {
				return map;
			}
			map.put("cart", obj.getObj());
			return map;
		}catch(Exception e){
			logger.error("调用base接口返回数据发生错误！");
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public ResultObjOne<Cart> doModifyCartV3(CartModifyParamV3 cartModifyParamV3) {

		ResultObjOne<Cart> obj = null;
		cartModifyParamV3.setVersionNo(GlobalConstants.ORDER_VERSION);
		cartModifyParamV3.setRequestFrom(GlobalConstants.ORDER_REQUEST_FRMO);
		try {
			String result = shoppingCartService.modifyCartV3(JSONUtils.obj2json(cartModifyParamV3));
			 obj = JSONUtils.toGenericsCollection(result, ResultObjOne.class, Cart.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public ResultObjOne<Cart> doDeleteCartV3(String userId, List<String> cartDetails, List<String> isChecked) {

		JSONObject reqJson = new JSONObject();
		reqJson.put("userId",userId);
		reqJson.put("cartDetailId",cartDetails);
		reqJson.put("isChecked",isChecked);
		reqJson.put("versionNo",GlobalConstants.ORDER_VERSION);
		reqJson.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		ResultObjOne<Cart> obj = null;
		try {
			String result = shoppingCartService.deleteCartV3(reqJson.toString());
			obj = JSONUtils.toGenericsCollection(result, ResultObjOne.class, Cart.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public ResultObjOne<Cart> doShowCartV3(String userId, List<String> checkedList) {

		JSONObject reqJson = new JSONObject();
		reqJson.put("userId",userId);
		reqJson.put("isChecked",checkedList);
		reqJson.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		reqJson.put("versionNo",GlobalConstants.ORDER_VERSION);
		ResultObjOne<Cart> obj = null;
		try {
			String result = shoppingCartService.showCartV3(reqJson.toString());
			obj = JSONUtils.toGenericsCollection(result, ResultObjOne.class, Cart.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public ResultBase addCartV3(String userId, String productNo, String sku, String quantity, String skuDynamic, String categoryNo, String topicSubjectFlag, String skuFrom, String vipNo, String siteNoSp, String channelNo, String channelId) {

		JSONObject reqJson = new JSONObject();
		reqJson.put("userId",userId);
		reqJson.put("productNo",productNo);
		reqJson.put("skuNo",sku);
		reqJson.put("quantity",quantity);
		reqJson.put("dynamicattributetext",skuDynamic);
		reqJson.put("categoryNo",categoryNo);
		reqJson.put("topicSubjectFlag",topicSubjectFlag);
		reqJson.put("skuFrom",skuFrom);
		reqJson.put("vipNo",vipNo);
		reqJson.put("siteNo",siteNoSp);
		reqJson.put("channelNo",channelNo);
		reqJson.put("channelId",channelId);
		reqJson.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		reqJson.put("versionNo",GlobalConstants.ORDER_VERSION);

		ResultBase obj = null;
		try {
			String result = shoppingCartService.addCartV3(reqJson.toString());
			obj = JSONUtils.toGenericsCollection(result, ResultBase.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
