package com.shangpin.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shangpin.base.service.UserService;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.user.UserBuyInfo;
import com.shangpin.biz.bo.order.OrderPriceInfo;
import com.shangpin.biz.utils.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.shangpin.base.service.OrderService;
import com.shangpin.base.vo.QuickSubmitVO;
import com.shangpin.base.vo.SubmitOrderParam;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.base.ResultObjOneResult;
import com.shangpin.biz.common.page.PageContext;
import com.shangpin.biz.service.SPBizOrderService;
import com.shangpin.biz.service.abstraction.AbstractBizOrderService;
import com.shangpin.biz.utils.Constants;
import com.shangpin.biz.utils.ObjectMapperConversionUtil;
import com.shangpin.utils.JsonUtil;

@Service
public class SPBizOrderServiceImpl extends AbstractBizOrderService implements SPBizOrderService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(SPBizOrderServiceImpl.class);
	// 订单分页每页默认获取10条记录
	private final String ORDER_PAGE_SIZE = "10";

	/*@Override
	public OrderItem findOrderDetail(String userId, String mainOrderNum) {
		try {
			ResultObjOneResult<OrderItem> obj = beOrderDetail(userId, mainOrderNum);
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return obj.getObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("调用主站查询订单简单信息接口返回数据错误!" + e);
		}
		return null;
	}*/
	
	@Override
	public OrderDetailResult findOrderDetail(String userId, String mainOrderId, String orderId, String isSplitOrder) {
		try {
			ResultObjOne<OrderDetailResult> obj = beOrderDetail(userId, mainOrderId, orderId, isSplitOrder);
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return obj.getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("调用主站查询订单简单信息接口返回数据错误!" + e);
		}
		return null;
	}

	@Override
	public OrderResult getOrderlist(String userId, String status) {
		int pageIndex = PageContext.getOffset() + 1;
		String pageSize = ORDER_PAGE_SIZE;
		try {
			ResultObjOneResult<OrderResult> obj = beOrderList(userId, status, String.valueOf(pageIndex), pageSize,"0");
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				OrderResult orderResult = obj.getObj();
				if (orderResult != null) {
					String totalPages = orderResult.getTotalPages();
					int pageCount = Integer.valueOf(totalPages);
					// haveMore为1则可以获取更多，为0则不能获取更多
					String haveMore = "";
					if (pageIndex < pageCount) {
						haveMore = "1";
					} else {
						haveMore = "0";
					}
					orderResult.setHaveMore(haveMore);
					orderResult.setMsg(obj.getMsg());
					orderResult.setPageIndex(pageIndex + "");
				}
				return orderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误" + e);
		}
		return null;
	}
	
	@Override
	public OrderListResult orderlist(List<String> userIds, String status) {

		int pageIndex = PageContext.getOffset() + 1;
		String pageSize = ORDER_PAGE_SIZE;
		try {
			JSONObject json = new JSONObject();
			json.element("listUserId",userIds);
			json.element("status",status);
			json.element("pageIndex",pageIndex);
			json.element("pageSize",Integer.parseInt(pageSize));
			json.element("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
			json.element("versionNo",GlobalConstants.ORDER_VERSION);
			String list = orderService.list(json.toString());

			ResultObjOne<OrderListResult> obj = JsonUtil.fromJson(list, new TypeToken<ResultObjOne<OrderListResult>>() {});
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				OrderListResult orderResult = obj.getContent();		
				orderResult.setPageIndex(pageIndex);
				if(orderResult!=null&&orderResult.getList()!=null){
					if(orderResult.getList().size() < Integer.valueOf(pageSize)){
						orderResult.setHaveMore("0");
					}else{
						orderResult.setHaveMore("1");
					}
				}
				return orderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误" + e);
			
		}
		return null;
	}
	@Override
	public OrderListResult orderlist(String userId, String status,String pageIndex) {
		String pageSize = ORDER_PAGE_SIZE;
		try {
			ResultObjOne<OrderListResult> obj = beOrderList(userId, status, String.valueOf(pageIndex), pageSize);
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				OrderListResult orderResult = obj.getContent();		
				orderResult.setPageIndex(Integer.valueOf(pageIndex));
				if(orderResult!=null&&orderResult.getList().size() < Integer.valueOf(pageSize)){
					orderResult.setHaveMore("0");
				}else{
					orderResult.setHaveMore("1");
				}
				return orderResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误" + e);
		}
		return null;
	}
	@Override
	public Map<String, Object> getMoreOrderlist(String userId, String status, String pageIndex) {
		String pageSize = ORDER_PAGE_SIZE;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String json = fromOrderList(userId, status, pageIndex, pageSize,"0");
			ObjectMapper mapper = new ObjectMapper();
			ObjectMapperConversionUtil.Conversion(mapper);
			JsonNode readTree = mapper.readTree(json);
			String code = readTree.path("code").asText();
			String msg = readTree.path("msg").asText();
			JsonNode jsonList = null;
			if (Constants.SUCCESS.equals(code)) {
				map.put("code", "0");
				JsonNode result = readTree.path("result");
				if (result == null || "null".equals(result.asText())||result.size()==0) {
					map.put("orderList", null);
					return map;
				}
				String totalPages = result.path("TotalPages").asText();
				int pageCount = Integer.valueOf(totalPages);
				logger.debug("调用base订单列表接口返回数据:" + json);
				// haveMore为1则可以获取更多，为0则不能获取更多
				String haveMore = "";
				if (Integer.valueOf(pageIndex) < pageCount) {
					haveMore = "1";
				} else {
					haveMore = "0";
				}
				jsonList = result.path("Items");
			
				map.put("haveMore", haveMore);
				map.put("orderList", jsonList);
				map.put("msg", msg);
				map.put("pageIndex", pageIndex + "");
				return map;
			} else {
				map.put("code", "1");
				map.put("msg", msg);
				return map;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误" + e);
		}
		return null;
	}

	/**
	 * Jackson将json字符串转成特定集合
	 * 
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	@Override
	public Map<String, Object> cancelOrder(String userId, String mainorderNo) {
		String json = fromCancelOrder(userId, mainorderNo);
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode readTree;
		try {
			if (null != json && !"".equals(json)) {
				readTree = mapper.readTree(json);
				String code = readTree.path("code").asText();
				String msg = readTree.path("msg").asText();
				map.put("code", code);
				map.put("msg", msg);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误:e={}", e);
		}
		map.put("code", "-1");
		map.put("msg", "数据错误，请重试");
		return map;
	}

	@Override
	public ResultObjOne<QuickResult> quickOrder(QuickOrderParam params) {
		try {
			ResultObjOne<QuickResult> result = this.beQuickOrder(this.convert(params));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: convert
	 * @Description:将controller提交的参数转换为base接口需要的参数实体
	 * @param
	 * @return QuickSubmitVO
	 * @throws
	 * @Create By qinyingchun
	 * @Create Date 2014年11月20日
	 */
	private QuickSubmitVO convert(QuickOrderParam param) {
		QuickSubmitVO quickSubmitVO = new QuickSubmitVO();
		quickSubmitVO.setUserId(param.getUserId());
		quickSubmitVO.setProductId(param.getProductId());
		quickSubmitVO.setSkuId(param.getSkuId());
		quickSubmitVO.setPayTypeId(param.getPayTypeId());
		quickSubmitVO.setPayTypeChildId(param.getPayTypeChildId());
		quickSubmitVO.setOrderOrigin(param.getOrderOrigin());
		quickSubmitVO.setShopType(param.getShopType());
		quickSubmitVO.setConsigneeName(param.getConsigneeName());
		quickSubmitVO.setProvince(param.getProvince());
		quickSubmitVO.setProvinceName(param.getProvinceName());
		quickSubmitVO.setCity(param.getCity());
		quickSubmitVO.setCityName(param.getCityName());
		quickSubmitVO.setArea(param.getArea());
		quickSubmitVO.setAreaName(param.getAreaName());
		quickSubmitVO.setTown(param.getTown());
		quickSubmitVO.setTownName(param.getTownName());
		quickSubmitVO.setAddress(param.getAddress());
		quickSubmitVO.setZip(param.getZip());
		quickSubmitVO.setTel(param.getTel());
		return quickSubmitVO;
	}

	@Override
	public boolean updateOrderStatus(String mainPay, String subPay, String orderId, String payMoney) {
		try {
			ResultBase obj = beUpdateOrderStatus(mainPay, subPay, orderId, payMoney);
			if (!StringUtils.isEmpty(obj)) {
				if (obj.isSuccess()) {
					return true;
				} else {
					logger.info("update order status error: code={}, msg={}, mainPay={}, subPay={}, orderId={}, payMoney={}", obj.getCode(), obj.getMsg(), mainPay, subPay, orderId, payMoney);
				}
				
			}
		} catch (Exception e) {
			logger.debug("base interface update order status data occur error");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Map<String, Object> submitCommonOrderMap(Map<String, String> cookieMap,String... params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SubmitOrderParam sop = setSubmitOrderParam(params);
			//增加推广链接的参数
			if(cookieMap!=null){
				sop.setChannelNo(cookieMap.get(Constants.THRID_COOKIE_SOURCE));
				sop.setChannelId(cookieMap.get(Constants.THRID_COOKIE_CAMPAIGN));
				sop.setParam(cookieMap.get(Constants.THRID_COOKIE_PARAM));
				sop.setChannelType(cookieMap.get(Constants.THRID_COOKIE_CHANNEL_TYPE));
			}else{
				sop.setChannelNo("");
				sop.setChannelId("");
				sop.setParam("");
				sop.setChannelType("");
			}
			
			ResultObjOne<OrderReturn> obj = beSubmitCommonOrder(sop);

			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				map.put("code", "0");
				map.put("orderInfo", obj.getObj());
				return map;
			}else {
				map.put("code", obj.getCode());
				map.put("msg", obj.getMsg());
				return map;
			}
		} catch (Exception e) {
			logger.error("base interface submit common order return data occur error!");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public OrderReturn submitCommonOrder(String... params) {
		try {
			SubmitOrderParam sop = setSubmitOrderParam(params);
			ResultObjOne<OrderReturn> obj = beSubmitCommonOrder(sop);

			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return obj.getObj();
			}
		} catch (Exception e) {
			logger.error("base interface submit common order return data occur error!");
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public GiftCardReturn cardPay(String userId, String shopType, String payType, String orderId, String password) {
		try {
			ResultObjOne<GiftCardReturn> obj = beModifyPayGiftCards(userId, password, "", orderId, "", shopType,
					payType);
			if (!StringUtils.isEmpty(obj) && obj.isSuccess()) {
				return obj.getObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultObjOne<GiftCardReturn> payGiftCards(String userId,String password,String orderId) {

		try {
			JSONObject obj = new JSONObject();
			obj.put("userId",userId);
			obj.put("orderId",orderId);
			obj.put("password",password);
			obj.put("requestFrom", GlobalConstants.ORDER_REQUEST_FRMO);
			obj.put("versionNo",GlobalConstants.ORDER_VERSION);
			String s= orderService.payGiftCards(obj.toString());
			ResultObjOne<GiftCardReturn> result = JsonUtil.fromJson(s, new TypeToken<ResultObjOne<GiftCardReturn>>() {});
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private SubmitOrderParam setSubmitOrderParam(String... params) {
		SubmitOrderParam sop = new SubmitOrderParam();
		sop.setUserId(params[0]);
		sop.setAddrId(params[1]);
		sop.setInvoiceAddrId(params[2]);
		sop.setInvoiceFlag(params[3]);
		sop.setInvoiceContent(params[4]);
		sop.setInvoiceType(params[5]);
		sop.setInvoiceTitle(params[6]);
		sop.setCouponFlag(params[7]);
		sop.setCoupon(params[8]);
		sop.setExpress(params[9]);
		sop.setOrderFrom(params[10]);
		sop.setBuysIds(params[11]);
		sop.setPayTypeId(params[12]);
		sop.setPayTypeChildId(params[13]);
		sop.setOrderType(params[14]);
		sop.setIsUseGiftCardPay(params[15]);
		sop.setOrderSource(params[16]);
		sop.setPostArea(params[17]);
		System.out.print(params.length);
		if(params.length>18){
			sop.setType(params[18]);
		}
		return sop;
	}

	@Override
	public boolean hasWaitPayOrder(String userId, String status) {
		OrderResult result = getOrderlist(userId, status);
		if (result != null) {
			if (Integer.valueOf(result.getTotalItems()) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 确认订单信息时更改地址和优惠码/券内容
	 */
	@Override
	public Map<String, Object> updateOrder(String userId, String couponFlag, String coupon, String buyGids,
			String addressId, String topicNo, String orderSource) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResultObjOne<CouponReturn> obj = beModifyOrderItems(userId, couponFlag, coupon, buyGids, addressId, topicNo, orderSource);
			if (obj != null) {
				map.put("couponVO", obj.getContent());
			}
			map.put("msgcode", obj.getCode());
			map.put("msg", obj.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public ResultObjOne<OrderReturn> beSubmitCommonOrder(Map<String, String> cookieMap ,OverseasOrderParam params) {
		SubmitOrderParam sop = setSubmitOrderParam(params);
		//增加推广链接的参数
		if(cookieMap!=null){
			sop.setChannelNo(cookieMap.get(Constants.THRID_COOKIE_SOURCE));
			sop.setChannelId(cookieMap.get(Constants.THRID_COOKIE_CAMPAIGN));
			sop.setParam(cookieMap.get(Constants.THRID_COOKIE_PARAM));
			sop.setChannelType(cookieMap.get(Constants.THRID_COOKIE_CHANNEL_TYPE));
		}else{
			sop.setChannelNo("");
			sop.setChannelId("");
			sop.setParam("");
			sop.setChannelType("");
		}
		ResultObjOne<OrderReturn> obj = beSubmitCommonOrder(sop);
		return obj;
	}

	private SubmitOrderParam setSubmitOrderParam(OverseasOrderParam params) {
		SubmitOrderParam sop = new SubmitOrderParam();
		sop.setUserId(params.getUserid());
		sop.setAddrId(params.getAddrid());
		sop.setInvoiceAddrId(params.getInvoiceaddrid());
		sop.setInvoiceFlag(params.getInvoiceflag());
		sop.setInvoiceContent(params.getInvoicecontent());
		sop.setInvoiceType(params.getInvoicetype());
		sop.setInvoiceTitle(params.getInvoicetitle());
		sop.setCouponFlag(params.getCouponflag());
		sop.setCoupon(params.getCoupon());
		sop.setExpress(params.getExpress());
		sop.setOrderFrom(params.getOrderfrom());
		sop.setBuysIds(params.getBuysIds());
		sop.setPayTypeId(params.getPaytypeid());
		sop.setPayTypeChildId(params.getPaytypechildid());
		sop.setOrderType(params.getOrdertype());
		sop.setIsUseGiftCardPay(params.getIsUseGiftCardPay());
		sop.setSkuId(params.getSkuId());
		sop.setOrderSource(params.getOrderSource());
		sop.setPostArea(params.getPostArea());
		return sop;
	}

	public Map<String, Object> finishedOrder(String userId, String mainorderNo) {
		String json = finishOrder(userId, mainorderNo);
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode readTree;
		try {
			if (null != json && !"".equals(json)) {
				readTree = mapper.readTree(json);
				String code = readTree.path("code").asText();
				String msg = readTree.path("msg").asText();
				map.put("code", code);
				map.put("msg", msg);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("确认收货返回数据错误:e={}", e);
		}
		map.put("code", "-1");
		map.put("msg", "数据错误，请重试");
		return map;
	}

	@Override
	public Map<String, Object> cancelOrder(String userId, String mainOrderNo,
			String postArea) {
		String json = fromCancelOrder(userId, mainOrderNo,postArea);
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode readTree;
		try {
			if (null != json && !"".equals(json)) {
				readTree = mapper.readTree(json);
				String code = readTree.path("code").asText();
				String msg = readTree.path("msg").asText();
				map.put("code", code);
				map.put("msg", msg);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用订单列表接口返回数据错误:e={}", e);
		}
		map.put("code", "-1");
		map.put("msg", "数据错误，请重试");
		return map;
	}

	@Override
	public Map<String, Object> submitGiftOrder(SubmitOrder params) {
		Map<String, Object> map = new HashMap<String, Object>();
		String json = orderService.submitGiftOrder(convert(params));
		if(StringUtils.isEmpty(json)){
			map.put("code", "1");
			map.put("msg", "返回json数据为null");
			return map;
		}
		ResultObjOne<OrderReturn> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<OrderReturn>>(){});
		if(null != result && result.getCode().equals(Constants.SUCCESS)){
			OrderReturn orderReturn = result.getObj();
			map.put("code", "0");
			map.put("orderInfo", orderReturn);
		}else {
			map.put("code", result.getCode());
			map.put("msg", result.getMsg());
		}
		return map;
	}
	
	private SubmitOrderParam convert(SubmitOrder params){
		SubmitOrderParam param = new SubmitOrderParam();
		param.setUserId(params.getUserid());
		param.setType(params.getType());
		param.setAddrId(params.getAddrid());
		param.setInvoiceFlag(params.getInvoiceflag());
		param.setInvoiceAddrId(params.getInvoiceaddrid());
		param.setInvoiceType(params.getInvoicetype());
		param.setInvoiceTitle(params.getInvoicetitle());
		param.setInvoiceContent(params.getInvoicecontent());
		param.setExpress(params.getExpress());
		param.setOrderFrom(params.getOrderfrom());
		param.setBuysIds(params.getBuysIds());
		param.setPayTypeId(params.getPaytypeid());
		param.setPayTypeChildId(params.getPaytypechildid());
		param.setOrderType(params.getOrdertype());
		return param;
	}

	@Override
	public Map<String,Object> finishedOrderV3(String userId, String orderId) {

		JSONObject reObj = new JSONObject();
		reObj.element("userId",userId).element("orderNo",orderId);
		reObj.put("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		reObj.put("versionNo",GlobalConstants.ORDER_VERSION);
		String result = orderService.finishOrderV3(reObj.toString());
		JSONObject resultObj = JSONObject.fromObject(result);
		return resultObj;
	}

	@Override
	public ResultObjOne<OrderDetailResult> findOrderDetailV3(List<String> userIds, String mainOrderId, String orderId, String isSplitOrder) {

		JSONObject reObj = new JSONObject();
		reObj.element("listUserId",userIds).element("mainOrderNo",mainOrderId).element("supplierOrderNo",orderId);
		reObj.element("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO).element("versionNo",GlobalConstants.ORDER_VERSION);
		String resultStr = orderService.detailV3(reObj.toString());
		ResultObjOne<OrderDetailResult> result =  JsonUtil.fromJson(resultStr, new TypeToken<ResultObjOne<OrderDetailResult>>(){});
		return result;
	}

    @Override
    public ResultObjOne<OrderPriceInfo> findOrderPriceInfo(String userId, String orderId) {

        JSONObject reObj = new JSONObject();
        reObj.element("userId",userId).element("mainOrderNo",orderId);
		reObj.element("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		reObj.element("versionNo",GlobalConstants.ORDER_VERSION);
        String resultStr = orderService.orderPriceInfo(reObj.toString());
        ResultObjOne<OrderPriceInfo> result =  JsonUtil.fromJson(resultStr, new TypeToken<ResultObjOne<OrderPriceInfo>>(){});
        return result;
    }

	@Override
	public UserBuyInfo getOrderCount(String userId) {

		//用户id,物流状态，尚品奥莱
		String resultStr = userService.userBuyInfo(userId,"",Constants.SHOP_TYPE_SHANGPIN);
		if(StringUtil.isNotEmpty(resultStr)){
			ResultObjOne<UserBuyInfo> result =  JsonUtil.fromJson(resultStr, new TypeToken<ResultObjOne<UserBuyInfo>>(){});
			if(result.isSuccess()){
				return result.getObj();
			}
		}
		return null;
	}

	@Override
	public ResultBase updatePayType(String userId, String orderId, String mainPayId,String subPayId,String bankId) {

		JSONObject reObj = new JSONObject();
		reObj.element("secureFlag",userId).element("mainOrderNo",orderId);
        reObj.element("signature","");//安全签名目前为空
		reObj.element("requestFrom",GlobalConstants.ORDER_REQUEST_FRMO);
		reObj.element("versionNo",GlobalConstants.ORDER_VERSION);
		JSONObject subJson = new JSONObject();
		subJson.put("typeBankValue",bankId);
		subJson.put("payTypeId",mainPayId);
		subJson.put("payTypeChildId",subPayId);
		reObj.put("payType",subJson.toString());
		String json = orderService.updatePayType(reObj.toString());
		if(StringUtils.isEmpty(json)){
			return null;
		}
		ResultBase resultBase = JsonUtil.fromJson(json, ResultBase.class);
		return resultBase;
	}
}
