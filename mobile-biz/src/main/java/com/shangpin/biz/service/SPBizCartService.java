package com.shangpin.biz.service;

import java.util.List;
import java.util.Map;

import com.shangpin.biz.bo.RecProduct;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.cart.Cart;
import com.shangpin.biz.bo.cart.CartModifyParamV3;

/**
 * @ClassName: BizCartService
 * @Description:购物车接口
 * @author qinyingchun
 * @date 2014年11月5日
 * @version 1.0
 */
public interface SPBizCartService{

	ResultBase beAddToCart(String userId, String productNo, String quantity, String sku, String categoryNo,String dynamicattributetext, String topicSubjectFlag, String skuFrom, String vipNo, String siteNo,String chanelNo,String chanelId);

	/**
	 * 页面无内容时获得推荐集合
	 * 
	 * @param userId
	 *            用户id
	 * @param type 1:用于收藏页面 1:尚品;2:奥莱
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @author zghw
	 */
	 List<RecProduct> findRecProductObj(String userId, String type, String pageIndex, String pageSize);


	/**
	 * 用于选中、取消选中显示购物车商品的接口
	 *
	 * @param userId
	 * @param isChecked
	 *            此用户选中的单品，通过“|”线分割组装多个选中单品
	 * @return
	 */
	Cart doShowCartV2(String userId, String isChecked);

	/**
	 * 查询购物车数量接口
	 *
	 * @param userId
	 * @param isChecked
	 *            此用户选中的单品，通过“|”线分割组装多个选中单品
	 * @return
	 */
	int cartCount(String userId, String isChecked);

	/**
	 * 用于修改购物车商品数量后展示的接口
	 *
	 * @param userId
	 * @param cartItem
	 * @param isChecked
	 * @param channelNo
	 *            (非必须)
	 * @param channelId
	 *            （非必须）
	 */
	Map<String, Object> doModifyCartV2(String userId, String cartItem,
			String isChecked, String channelNo, String channelId);

	/**
	 * 用于删除购物车商品后展示的接口
	 *
	 * @param userId
	 * @param cartDetailId
	 * @param isChecked
	 * @return
	 */
	Map<String, Object> doDeleteCartV2(String userId, String cartDetailId,
			String isChecked);


	/**
	 * 用于选中、取消选中显示购物车商品的接口
	 * @param userId
	 * @param checkedList
	 * @return
	 */
	ResultObjOne<Cart> doShowCartV3(String userId, List<String> checkedList);
	/**
	 * 修改购物车数量
     * @return
     */
	ResultObjOne<Cart> doModifyCartV3(CartModifyParamV3 cartModifyParamV3);

	/**
	 * 删除购物车数量V3
	 * @return
	 */
	ResultObjOne<Cart> doDeleteCartV3(String userId, List<String> strings, List<String> strings1);

	/**
	 * 添加购物车V3
	 * @param userId
	 * @param productNo
	 * @param sku
	 * @param quantity
	 * @param skuDynamic
	 * @param categoryNo
	 * @param topicSubjectFlag
	 * @param skuFrom
	 * @param vipNo
	 * @param siteNoSp
	 * @param channelNo
	 * @param channelId
	 * @return
	 */
	ResultBase addCartV3(String userId, String productNo, String sku,
						 String quantity, String skuDynamic, String categoryNo,
						 String topicSubjectFlag, String skuFrom, String vipNo,
						 String siteNoSp, String channelNo, String channelId);
}
