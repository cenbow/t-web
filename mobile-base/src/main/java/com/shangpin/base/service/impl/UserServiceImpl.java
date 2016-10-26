package com.shangpin.base.service.impl;

import com.shangpin.base.service.UserService;
import com.shangpin.base.utils.GlobalConstants;
import com.shangpin.utils.Constants;
import com.shangpin.utils.HttpClientUtil;
import com.shangpin.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	// 注册请求的URL
	private StringBuilder registerURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("registerandlogin");

	// 登录请求的URL
	private StringBuilder loginURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("login");
	/**
	 * 获取用户权益信息的url
	 */
	private String userLevelInfoURL = GlobalConstants.USER_SERVICE_USER+"getUserLevelInfo";

	// 根据用户名或手机号查找用户URL
	private StringBuilder checkUserNameURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("checkUsername");

	// 根据手机号修改登录密码
	private StringBuilder resetLoginPasswordURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("resetLoginPassword");

	// 第三方登陆的URL
	private StringBuilder thirdLoginURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("thirdlogin");

	// 获取个人信息
	private StringBuilder getUserInfoURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("getUserInfo");

	// 第三方账户或邮箱绑定手机
	private StringBuilder thirdLoginBindPhoneURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("bindmobile");



	// check手机或者邮箱是否存在
	private StringBuilder checkUserByPhoneOrEmailURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("checkuser");

	// 查询用户下的所有id集合
	private StringBuilder getUserIdListURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("getUserIdList");

	// 给邮箱账户发送邮箱验证码
	private StringBuilder sendEmailCodeURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("sendemailvalicode");


	/**
	 * 主站方的url
     */
	// 发送手机验证码的URL
	private StringBuilder sendVerifyCodeURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("SendRegMobileVerifyCode");

	// 手机号快速注册
	private StringBuilder checkPhoneUserURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_SHANGPIN).append("checkPhoneUser");

	// 验证手机号的URL
	private StringBuilder verifyPhoneCodeURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("verifyphoneandcode");

	// 订单数量信息
	private String userBuyInfo = new StringBuffer(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("UserBuyInfo").toString();
	// 用户绑定手机
	//private StringBuilder verifySuccessURL = new StringBuilder(GlobalConstants.USER_SERVICE_USER).append("codemactchedphone");
	private StringBuilder verifySuccessURL = new StringBuilder(GlobalConstants.BASE_URL_SHANGPIN_AOLAI).append("codemactchedphone");

	//用户试用vip
	private String userTrialVip = GlobalConstants.USER_SERVICE_USER.concat("setTrial");

	//用户修改个人信息
	private String modifyUserInfo = GlobalConstants.USER_SERVICE_USER.concat("modifyUserInfo");

	//用户邀请信息接口地址
	private String invitedUserInfo = GlobalConstants.USER_SERVICE_USER.concat("getInviteCodeInfo");
	/**
	 * 登录
	 */
	@Override
	public String login(String userName, String password) {
		//plat 0 未知   1 pc   2 M站   3 客户端
	 	// @param version 版本号
		Map<String, String> params = new HashMap<>();
		params.put("name", userName);
		params.put("password", password);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doPost(loginURL.toString(), params);
	}

	@Override
	public String register(String email, String phonenum, String password,String confirmPassword, String gender, String smscode, String type, String invitecode,String channelNo,String channelId,String param,String channelType) {
		Map<String, String> params = new HashMap<>();
		params.put("name", email);
		params.put("mobile", phonenum);
		params.put("password", password);
		params.put("confirmPassword", confirmPassword);
		params.put("type", type);
		params.put("smscode", smscode);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		params.put("invitecode",invitecode);
		if(channelNo!=null && channelId!=null){
			params.put("channelNo", channelNo);
			params.put("channelId", channelId);
			params.put("param", param);
			params.put("channelType", channelType);
		}
		return HttpClientUtil.doPost(registerURL.toString(), params);
	}


	/**
	 * 根据用户名或手机号查找用户
	 */
	@Override
	public String findUserByUserName(String userName) {
		Map<String, String> params = new HashMap<>();
		return HttpClientUtil.doPost(checkUserNameURL.toString(), params);
	}

	/**
	 * 第三方登陆
	 */
	public String thirdLogin(String mode, String inviteCode, String uid, String sex, String nickname, String truename) {
		Map<String, String> params = new HashMap<>();
		params.put("mode", mode);
		//params.put("invitecode", inviteCode);
		params.put("uid", uid);
		params.put("gender", sex);
		params.put("nikckname", nickname);
		params.put("truename", StringUtils.isBlank(truename)?nickname:truename);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doPost(thirdLoginURL.toString(), params);
	}
	/**
	 * 修改登录密码
	 */
	public String resetLoginPassword(String username, String password,String smsCode) {
		Map<String, String> params = new HashMap<>();
		params.put("username", username);
		params.put("password", password);
		params.put("smsCode", smsCode);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doPost(resetLoginPasswordURL.toString(), params);
	}

	/**
	 * 发送验证码
	 */
	public String sendVerifyCode(String userId, String phoneNum,String msgTemplate){
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("phonenum", phoneNum);
		params.put("msgtemplate", msgTemplate);
		return HttpClientUtil.doGet(sendVerifyCodeURL.toString(), params);
	}


	/**
	 * 验证手机号
	 */
	@Override
	public String verifyPhoneCode(String userId, String phoneNum, String verifyCode) {
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("phonenum", phoneNum);
		params.put("verifycode", verifyCode);
		return  HttpClientUtil.doGet(verifyPhoneCodeURL.toString(), params);
	}


	@Override
	public String getUserInfo(String userId ) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doGet(getUserInfoURL.toString(), params);
	}

	@Override
	public String thirdLoginBindPhone(String mobile,String type,String userId,String os,String password){
		Map<String, String> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("type", type);
		params.put("userId", userId);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		params.put("os", os);
		if(password!=null){
			params.put("password", password);
		}
		return HttpClientUtil.doPost(thirdLoginBindPhoneURL.toString(), params);
	}

	@Override
	public String bindToUser(String userId,String typeInfo){
		Map<String, String> params = new HashMap<>();
		params.put("userid", userId);
		params.put("type", typeInfo);
		return HttpClientUtil.doGet(verifySuccessURL.toString(), params);
	}

	@Override
	public String checkPhoneUser(String phoneNum, String channelNo) {
		Map<String, String> params = new HashMap<>();
		params.put("phoneNum", phoneNum);
		params.put("channelNo", channelNo);
		return HttpClientUtil.doGet(checkPhoneUserURL.toString(), params);
	}
	@Override
	public String checkUserByPhoneOrEmail(String userName){
		Map<String, String> params = new HashMap<>();
		params.put("name", userName);
		return HttpClientUtil.doGet(checkUserByPhoneOrEmailURL.toString(), params);
	}

	@Override
	public String getUserIdList(String userId){
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		return HttpClientUtil.doGet(getUserIdListURL.toString(), params);
	}

	@Override
	public String sendEmailCode(String email){
		Map<String, String> params = new HashMap<>();
		params.put("email", email);
		params.put("plat", GlobalConstants.MOBILE_PLAT);
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doGet(sendEmailCodeURL.toString(), params);
	}

	@Override
	public String userLevelInfo(String userId) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", userId);
		return HttpClientUtil.doGet(userLevelInfoURL.toString(), params);
	}

	@Override
	public String userBuyInfo(String userId, String checktime, String shoptype) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		if(StringUtil.isNotEmpty(checktime)){
			params.put("checktime",checktime);
		}
		params.put("shoptype", shoptype);
		return HttpClientUtil.doGet(userBuyInfo, params);
	}

	@Override
	public String setUserTrial(String userId,String inviteCode) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		if(StringUtil.isNotEmpty(inviteCode)){
			params.put("inviteCode", inviteCode);
		}
		return HttpClientUtil.doGet(userTrialVip, params);
	}

	@Override
	public String modifyUserInfo(String userId, String nickName, String gender,
								 String mobile, String email, String birthday) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		if(StringUtil.isNotEmpty(nickName)){
			params.put("nickName",nickName);
		}
		if(StringUtil.isNotEmpty(gender)){
			params.put("gender",gender);
		}
		if(StringUtil.isNotEmpty(mobile)){
			params.put("mobile",mobile);
		}
		if(StringUtil.isNotEmpty(email)){
			params.put("email",email);
		}
		if(StringUtil.isNotEmpty(birthday)){
			params.put("birthday",birthday);
		}
		params.put("plat", GlobalConstants.MOBILE_PLAT);//站
		params.put("version", GlobalConstants.MOBILE_VERSION);
		return HttpClientUtil.doPost(modifyUserInfo, params);
	}

	@Override
	public String getInvitedCodeInfo(String userId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		return HttpClientUtil.doGet(invitedUserInfo, params);
	}
}
