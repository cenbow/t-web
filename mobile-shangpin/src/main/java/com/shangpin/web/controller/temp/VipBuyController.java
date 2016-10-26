package com.shangpin.web.controller.temp;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

/**
 * 299vip购买Controller
 *
 */
@Controller
@RequestMapping("/t/vip")
public class VipBuyController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(VipBuyController.class);
	/**
	 * 299购买下单提交页面 
	 * <br/>
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/buy")
	public String toVipBuy(ModelMap model, HttpServletRequest request) {
		User user = getSessionUser(request);
		UserLevelInfo ul = getUserLevelInfo(request);
		if(user!=null && user.isVip() && !(ul!=null && ul.isTryVip())){
			return "redirect:/user/right/about";
		}
		
		logger.info("header id:{},seesion user:{}", request.getHeader("userid"),JsonUtil.toJson((User)getSessionUser(request)));
		String loginUrl = Constants.APP_NOT_LOGIN_URL+"?"+Constants.APP_NAME_CALLBACK_URL+"="+Constants.SHANGPIN_URL+"/t/vip/buy";
		model.addAttribute("loginUrl",loginUrl);
		return "temp/vipBuy/t-vip-buy";
	}
	
}