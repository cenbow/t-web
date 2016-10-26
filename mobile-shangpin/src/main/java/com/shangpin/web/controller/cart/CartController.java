package com.shangpin.web.controller.cart;

import com.shangpin.biz.bo.RecProduct;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.cart.Cart;
import com.shangpin.biz.bo.cart.CartList;
import com.shangpin.biz.bo.cart.CartModifyParamV3;
import com.shangpin.biz.bo.cart.CartProductList;
import com.shangpin.biz.service.SPBizAddressService;
import com.shangpin.biz.service.SPBizCartService;
import com.shangpin.biz.utils.StringUtil;
import com.shangpin.utils.ArithmeticUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: CartController
 * @Description:购物车控制层
 * @author qinyingchun
 * @date 2014年11月5日
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {


	private static final String CART_LIST_NULL = "cart520/no_product";

	private static final String NEW_CART_LIST = "cart520/cartList";

	private static final String MODIFY = "cart520/modify";

	@Autowired
	private SPBizCartService bizCartService;
	
	/**
	 * 计算购物车商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public int count(HttpServletRequest request){
		User user=getSessionUser(request);
		if(StringUtils.isEmpty(user)){
            return -1;
        }
		String userId=user.getUserId();
		if(StringUtils.isEmpty(userId)){
			return -1;
		}
		try{
			int count =bizCartService.cartCount(userId, null);
			return count;
		}catch (Exception e){
			return 0;
		}
	}

	/**
	 * 购物车页面单选中商品时访问(520)
	 * @param isChecked
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShowCart", method = RequestMethod.GET)
	public String updateShowCart(String isChecked, Model model, HttpServletRequest request) throws Exception {
		User user=getSessionUser(request);
		String userId=user.getUserId();
        List<String> checkIds= new ArrayList<>();
        if(com.shangpin.utils.StringUtil.isNotEmpty(isChecked)){
            checkIds= Arrays.asList(isChecked.split("\\|"));
        }
        ResultObjOne<Cart> cartResultObjOne = bizCartService.doShowCartV3(userId,checkIds);
        if(cartResultObjOne.isSuccess()){
            model.addAttribute("cart",cartResultObjOne.getObj());
        }
		updateSessionCartCount(cartResultObjOne,request);
		model.addAttribute("isVip",user.isVip());
		return MODIFY;
	}

	/**
	 * 购物车为空
	 * @return
	 */
	@RequestMapping(value = "/no", method = RequestMethod.GET)
	public String noProduct(){
		return CART_LIST_NULL;
	}

    /**
     * @Title: add
     * @Description: 登录加入购物车
     * @date 2016-08-11
     * @return String
     * @Create By liushaoqing
     */
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addV3AjAx(String productNo, String quantity, String sku, String topicNo,
									 HttpServletRequest request,HttpServletResponse resonse) {

		Map<String, Object> map = addCart(productNo, quantity, sku, topicNo, request, resonse);
		return map;
	}

	/**
	 * @Title: add
	 * @Description: 未登录加入购物车跳转
	 * @date 2016-08-11
	 * @return String
	 * @Create By liushaoqing
	 */
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String addV3Get(String productNo, String quantity, String sku, String topicNo,
									 HttpServletRequest request,HttpServletResponse resonse) {

		Map<String, Object> map = addCart(productNo, quantity, sku, topicNo, request, resonse);
		return "redirect:/cart/list";
	}

	/**
	 * 添加购物车逻辑
	 * @param productNo
	 * @param quantity
	 * @param sku
	 * @param topicNo
	 * @param request
     * @param resonse
     * @return
     */
	private Map<String, Object> addCart(String productNo, String quantity, String sku, String topicNo,
										HttpServletRequest request,HttpServletResponse resonse){
		Map<String, Object> map = new HashMap<String, Object>();
		String code = "";
		String msg = "";
		User user = getSessionUser(request);
		if (user == null || StringUtils.isEmpty(user.getUserId())) {
			code = Constants.UNLOGIN;
		} else {
			String userId = user.getUserId();
			String categoryNo;
			String topicSubjectFlag;
			if (!StringUtils.isEmpty(topicNo)) {
				categoryNo = topicNo;
				topicSubjectFlag = Constants.BUY_TOPIC;
			} else {
				categoryNo = Constants.DEFAULT_CATEGORYNO;
				topicSubjectFlag = Constants.BUY_LIST;
			}

			//增加商品客的参数channelNo channelId
			HttpSession session = request.getSession();
			String channelNo = (String) session.getAttribute(Constants.THRID_TOKEN_ChannelNo);
			String channelId = (String) session.getAttribute(Constants.THRID_TOKEN_ChannelId);
			String spuNo = (String) session.getAttribute(Constants.THRID_TOKEN_SpuNo);
			String topicId = (String) session.getAttribute(Constants.THRID_TOKEN_TopicId);
			boolean isTopic = false;
			boolean isSpu = false;
			//校验活动id
			if(StringUtil.isNotEmpty(topicId,channelNo,channelId)){
				if(topicId.equals(categoryNo)){
					isTopic = true;
					session.setAttribute(Constants.THRID_TOKEN+productNo, productNo);//将商品sku放入session
				}
			}
			//验证商品是否是那件spu商品
			if(StringUtil.isNotEmpty(spuNo,channelNo,channelId)){
				if(spuNo.equals(productNo)){
					isSpu = true;
				}
			}
			ResultBase result =null;
			if(isTopic||isSpu){
				// "3" spu来源 1 尚品,3 手机
				result = bizCartService.addCartV3(userId, productNo, sku,quantity,Constants.SKU_DYNAMIC,categoryNo,
						topicSubjectFlag, "3",Constants.VIP_NO,Constants.SITE_NO_SP,channelNo,channelId);
			}else{
				result = bizCartService.addCartV3(userId, productNo, sku,quantity,Constants.SKU_DYNAMIC,categoryNo,
						topicSubjectFlag, "3",Constants.VIP_NO,Constants.SITE_NO_SP,"","");
			}
			updateSessionCartCount(null,request);
			code = result.getCode().equals(Constants.SUCCESS) ? Constants.SUCCESS : Constants.FAILE;
			msg = result.getMsg();
		}
		map.put("code", code);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 进入购物车页面(查询购物车)
	 * @param model
	 * @param request
	 * @param isChecked
	 * @date 2016-08-11
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listV3(Model model, HttpServletRequest request, String isChecked){

		User user = getSessionUser(request);
		String userId = user.getUserId();
        List<String> checkIds= new ArrayList<>();
        if(com.shangpin.utils.StringUtil.isNotEmpty(isChecked)){
            checkIds= Arrays.asList(isChecked.split("\\|"));
        }
        ResultObjOne<Cart> cartResultObjOne = bizCartService.doShowCartV3(userId,checkIds);
		updateSessionCartCount(cartResultObjOne,request);
		if(!cartResultObjOne.isSuccess() || cartResultObjOne.getObj().getCartList().size()<=0){
            List<RecProduct> productList = bizCartService.findRecProductObj(userId, Constants.STRING_1, Constants.STRING_1, "12");
            if (null != productList && productList.size() > 0) {
                List<List<RecProduct>> recList = ArithmeticUtil.createList(productList, 3);
                model.addAttribute("groupList", recList);
            }
            return CART_LIST_NULL;
        }
		model.addAttribute("cart",cartResultObjOne.getObj());
		model.addAttribute("isVip",user.isVip());
		return NEW_CART_LIST;
	}

	/**
	 * 修改购物车商品数量(520)
	 * @param cartItem
	 * @param isChecked
	 * @param cartSpuNo
	 * @param model
	 * @param request
	 * @date 2016-08-11
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyV3(String cartItem, String isChecked,String cartSpuNo,Model model, HttpServletRequest request) throws Exception{

		User user=getSessionUser(request);
		String userId=user.getUserId();
		log.debug("User ID:" + user.getUserId());

		//增加尚品客的参数channelNo channelId(非必须)
		HttpSession session=request.getSession();
		String channelNo = (String) session.getAttribute(Constants.THRID_TOKEN_ChannelNo);
		String channelId = (String) session.getAttribute(Constants.THRID_TOKEN_ChannelId);
		String spuNo = (String) session.getAttribute(Constants.THRID_TOKEN_SpuNo);
		//获取session存储的加入购物车的spu
		String cartSpu = (String) session.getAttribute(Constants.THRID_TOKEN+cartSpuNo);
		boolean isTopicSpu = false;
		boolean isSpu = false;
		//验证商品是否是那件spu商品
		if(StringUtil.isNotEmpty(cartSpu,channelNo,channelId)){
			isTopicSpu = true;
		}
		if(StringUtil.isNotEmpty(spuNo,channelNo,channelId)){
			if(spuNo.equals(cartSpuNo)){
				isSpu = true;
			}
		}
		ResultObjOne<Cart> cartResult = null;
		CartModifyParamV3 paramV3 = new CartModifyParamV3();
		paramV3.setUserId(userId);
		paramV3.setChannelId(channelId);
		paramV3.setChannelNo(channelNo);
		if(isChecked.length()>0){
			paramV3.setIsChecked(Arrays.asList(isChecked.split("\\|")));
		}else{
            paramV3.setIsChecked(new ArrayList<String>());
        }
		if(cartItem.length()>0){
			List<CartModifyParamV3.CartItemsBean> list = new ArrayList<>();
			for (String s : cartItem.split("\\|")) {
				CartModifyParamV3.CartItemsBean item = new CartModifyParamV3.CartItemsBean();
				item.setQuantity(s.split("_")[1]);
				item.setShoppingCartId(s.split("_")[0]);
                list.add(item);
			}
			paramV3.setCartItems(list);
		}

		if(isTopicSpu||isSpu){
			cartResult = bizCartService.doModifyCartV3(paramV3);
		}else{
			cartResult = bizCartService.doModifyCartV3(paramV3);
		}
		if(cartResult!=null){
			if(cartResult.getCode().equals(ResultObjOne.SUCCESS)){
				model.addAttribute("cart",cartResult.getObj());

			}
		}
		updateSessionCartCount(cartResult,request);
		model.addAttribute("isVip",user.isVip());
		return MODIFY;
	}

	/**
	 * 删除购物车商品
	 * @param cartDetailId
	 * @param isChecked
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2016-08-11
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteV3(String cartDetailId,String isChecked,Model model, HttpServletRequest request) throws Exception{
		User user=getSessionUser(request);
		String userId=user.getUserId();
		log.debug("User ID:" + user.getUserId());

        List<String> cartDetailIds= new ArrayList<>();
        if(com.shangpin.utils.StringUtil.isNotEmpty(cartDetailId)){
            cartDetailIds.add(cartDetailId);
        }

        List<String> isCheckeds= new ArrayList<>();
        if(com.shangpin.utils.StringUtil.isNotEmpty(cartDetailId)){
            isCheckeds= Arrays.asList(isChecked.split("\\|"));
        }

		ResultObjOne<Cart> cartResult =bizCartService.doDeleteCartV3(userId,cartDetailIds, isCheckeds);
		if(cartResult!=null && cartResult.getCode().equals(ResultObjOne.SUCCESS)){
			model.addAttribute("cart",cartResult.getObj());
		}
		updateSessionCartCount(cartResult,request);
		model.addAttribute("isVip",user.isVip());
		return MODIFY;
	}

	/**
	 * 更新session中购物车数量
	 * @param cartResult
	 * @param request
     */
	private void updateSessionCartCount(ResultObjOne<Cart> cartResult,HttpServletRequest request){

        User user=getSessionUser(request);
		int size = 0;
		if(cartResult!=null && cartResult.isSuccess() ){

			for (CartList cartList : cartResult.getObj().getCartList()) {
                for (CartProductList cartProductList : cartList.getProductList()) {
                    size += Integer.parseInt(cartProductList.getQuantity());
                }
            }
		}else{
			size = bizCartService.cartCount(user.getUserId(), null);
		}
		user.setCartCount(size);
	}

}
