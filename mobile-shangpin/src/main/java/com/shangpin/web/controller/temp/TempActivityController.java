package com.shangpin.web.controller.temp;


import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.QuickUser;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.biz.service.SPBizCouponService;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.PropertyUtil;
import com.shangpin.web.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 临时活动的Controller
 *
 */
@Controller


@RequestMapping("/t/act")
public class TempActivityController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(TempActivityController.class);
	
    private static final String INDEX = "/star/1698/index";

	@Autowired
	private SPBizUserService userService;

	@Autowired
	private SPBizCouponService couponService;


	/**
	 * 跳转到活动页面
	 * @param type
	 * @return
     */
	@RequestMapping("/red/{type}")
	public String toActivityPage(@PathVariable("type") String type,Model model){
		logger.info("活动type:{}",type);
		if("1".equals(type)){
			model.addAttribute("type", type);
			return INDEX;
		}
		return "redirect:/index";
	}



	/**
	 * 发送验证码，并判断用户是否存在
	 * @param mobile
	 * @return code: 1-失败  0-成功
	 * @return msg:msg
	 * @return //isExist:是否账户存在： 0不存在 1 存在"
	 *
     */
	@RequestMapping("/sendCheck")
	@ResponseBody
	public Map<String,String> sendCodeAndCheckUser(@RequestParam("mobi") String mobile){
		Map<String,String> map = new HashMap<>();
		String code="0";
		String msg="";
		map.put("code",code);
		map.put("msg",msg);
        //验证用户是否存在
       /* UserCheck userCheck = userService.checkUserByPhoneOrEmail(mobile);
        if(userCheck==null){
            code = "1";
            msg = com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
            map.put("code",code);
            map.put("msg",msg);
            return map;
        }
        String isExist = userCheck.getIsExist();//"是否账户存在： 0不存在 1 存在",
        map.put("isExist",isExist);*/
		//发送短信
		String json = userService.fromSendVerifyCode(mobile, mobile, "您的验证码是：{$verifyCode$}，请及时输入验证。");
		logger.info("活动发送短信json data:{}",json);
		ResultBase resultBase;
		if(json==null|| (resultBase= JsonUtil.fromJson(json, new TypeToken<ResultBase>() {}))==null){
			code = "1";
			msg = com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
			map.put("code",code);
			map.put("msg",msg);
			return map;
		}
		if(!resultBase.isSuccess()){
			code = "1";
			msg = resultBase.getMsg();
			map.put("code",code);
			map.put("msg",msg);
			return map;
		}

		return map;
	}

	@RequestMapping("/getCoupon")
	@ResponseBody
	public Map<String,String> getCouponsByType(@RequestParam("mobi") String mobile,@RequestParam("verifyCode") String msgCode,String type,HttpServletRequest request) {
		Map<String,String> map = new HashMap<>();
		String code ="0";
		String msg="";
		map.put("code",code);
		map.put("msg",msg);
		//map.put("msg","手机号码不能为空!");
		if(StringUtils.isBlank(mobile) || mobile.trim().length()!=11){
			map.put("code","11");
			return map;
		}
		if(StringUtils.isBlank(msgCode)||msgCode.length()!=6){
			map.put("code","12");
			return map;
		}
		//校验短信
		ResultBase result = userService.beVerifyPhoneCode(mobile, mobile,msgCode);
		logger.info("校验短信mobile:{},msgCode:{},校验短信结果：{}",mobile,code,result);
		if(result==null){
			map.put("code","1");
			map.put("msg",com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA);
			return map;
		}else if(!result.isSuccess()){
			map.put("code","12");
			//map.put("msg","请输入正确的验证码！");
			return map;
		}
		//验证用户是否存在
		QuickUser quickUser = userService.checkUser(mobile, Constants.IS_NEW_USER, null);
		if(Constants.IS_NEW_USER.equals(quickUser.getIsNewUser())){//表示是新用户，给用户下发短信通知
			String msgTemplateRe = "您好，感谢参与尚品网活动，您同时尊享尚品网会员权益。使用您的手机号即可登录，密码为手机号后6位。";
			userService.fromSendVerifyCode(mobile, mobile, msgTemplateRe);
		}
		String userId = quickUser.getUserId();
		//调用冲券
		String couponCode ="6319838700";
		//检测是否领取过
		ResultBase rBase = couponService.checkActiveCode(mobile, couponCode);
		if (rBase ==null){
			map.put("code","1");
			map.put("msg",com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA);
			return map;
		}else if(!rBase.isSuccess()) {//已经领取过
			map.put("code","3");
			//用户放入session
			User user = userService.findUserByUserId(userId);
			UserUtil.loginUserToSession(userService.toUserHaveIds(user), request.getSession());
			return map;
		}
		Map<String, Object> couponResult = couponService.sendCoupon(userId, "1", "coupon:" + couponCode,"30");
		String cCode =(String) couponResult.get("code");
		if(cCode == null){
			map.put("code","1");
			map.put("msg",com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA);
			return map;
		}
		if("1".equals(couponResult.get("code"))){
			map.put("code","1");
			map.put("msg",(String)couponResult.get("msg"));
			return map;
		}
		/*//充值成功
		String url = "";//跳转的url
		map.put("url",url);*/
		//用户放入session
		User user = userService.findUserByUserId(userId);
		UserUtil.loginUserToSession(userService.toUserHaveIds(user), request.getSession());
		return map;
	}


}