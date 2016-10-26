package com.shangpin.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.shangpin.biz.bo.user.UserInvitedInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.shangpin.base.service.ShoppingCartService;
import com.shangpin.base.service.UserService;
import com.shangpin.biz.bo.QuickUser;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.UserIds;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.user.ModifyUserInfoReq;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.biz.service.abstraction.AbstractBizUserService;
import com.shangpin.biz.utils.Constants;
import com.shangpin.core.entity.AccountWeixinBind;
import com.shangpin.utils.JSONUtils;
import com.shangpin.utils.JsonUtil;

import net.sf.json.JSONObject;

@Service
public class SPBizUserServiceImpl extends AbstractBizUserService implements SPBizUserService {

	public static final Logger logger = LoggerFactory.getLogger(SPBizUserServiceImpl.class);
	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService cartService;

	@Override
	public User findUserByUserName(String userName) {
		String json = userService.findUserByUserName(userName);
		return this.fromJsonToUser(json);
	}

	@Override
	public User weixinAutoLogin(AccountWeixinBind account) {
		try {
			String data = userService.getUserInfo(account.getUserId());
			User user = null;
			if(data!=null){
				JSONObject obj = JSONObject.fromObject(data);
				String content = obj.getString("content");
				if(org.apache.commons.lang3.StringUtils.isNotBlank(content)){
					user = JsonUtil.fromJson(content, User.class);
				}
			}
			if (user != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				user.setUserId(account.getUserId());
				user.setGender(account.getGender());
				user.setRegTime(sdf.format((account.getRegTime())));
				user.setRegOrigin(account.getRegOrigin());
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public QuickUser checkUser(String phone, String type) {
		try {
			ResultObjOne<QuickUser> obj = beCheckUser(phone, type,null);
			if (obj!=null && obj.isSuccess()) {
				return obj.getObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public QuickUser checkUser(String phone, String type,String fashionRegisteSource) {
		try {
			ResultObjOne<QuickUser> obj = beCheckUser(phone, type,fashionRegisteSource);
			if (obj!=null && obj.isSuccess()) {
				return obj.getObj();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String thirdLogin( String mode,String invitecode, String uid, String gender,String nickName,String trueName ) {
		return userService.thirdLogin(mode, invitecode, uid, gender, nickName, trueName);
	}

	@Override
	public User login(String userName, String password) {
		String userData = userService.login(userName, password);
		return fromJsonToUser(userData);
	}

	@Override
	public String toRegister(String email, String phonenum, String password,String confirmPassword, String gender, String smscode, String type, String invitecode,String channelNo,String channelId,String param,String channelType) {
		return userService.register(email, phonenum, password, confirmPassword, gender, smscode, type, invitecode, channelNo, channelId, param, channelType);
	}

	@Override
	public ResultBase resetLoginPassword(String username, String password,String smsCode) {
		String json = userService.resetLoginPassword(username, password, smsCode);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(json)) {
			return JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
		}
		return null;
	}

	@Override
	public String fromSendVerifyCode(String userId, String phoneNum, String msgTemplate) {
		return userService.sendVerifyCode(userId, phoneNum, msgTemplate);
	}

	@Override
	public ResultBase beVerifyPhoneCode(String userId, String phoneNum, String verifyCode) {
		String json = userService.verifyPhoneCode(userId, phoneNum, verifyCode);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(json)) {
			return JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
		}
		return null;
	}

	/**
	 * 根据用户id查询用户
	 */
	@Override
	public User findUserByUserId(String userId) {
		String json = userService.getUserInfo(userId);
		return fromJsonToUser(json);
	}

	public ResultBase thirdLoginBindPhone(String mobile,String type,String userId,String os,String password){
		String data = userService.thirdLoginBindPhone(mobile, type, userId, os,password);
		if(org.apache.commons.lang3.StringUtils.isBlank(data)){
			ResultBase resultBase = new ResultBase();
			resultBase.setCode("2");
			resultBase.setMsg("网络异常，请稍后重试！");
			return resultBase;
		}
		return JsonUtil.fromJson(data,ResultBase.class);
	}

	@Override
	public ResultBase beBindToUser(String userId, String typeInfo) {
		String json = userService.bindToUser(userId, typeInfo);
		if (!StringUtils.isEmpty(json)) {
			return JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
		}
		return null;
	}

	@Override
	public QuickUser checkPhoneUser(String phoneNum, String channelNo) {
		String json = userService.checkPhoneUser(phoneNum, channelNo);
		try {
			if (StringUtils.isEmpty(json)) {
				return null;
			}
			ResultObjOne<QuickUser> resultObjOne = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<QuickUser>>() {
			});
			if (null != resultObjOne && Constants.SUCCESS.equals(resultObjOne.getCode())) {
				return resultObjOne.getObj();
			}
		} catch (Exception e) {
			logger.error("check phone user occur error,return data:{}", json);
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public UserCheck checkUserByPhoneOrEmail(String userName){
		String data = userService.checkUserByPhoneOrEmail(userName);
		if(org.apache.commons.lang3.StringUtils.isBlank(data)){
			return null;
		}
		ResultObjOne<UserCheck> userCheck = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<UserCheck>>() {
		});
		if(userCheck!=null && userCheck.isSuccess()){
			return userCheck.getObj();
		}
		return null;
	}

	private User fromJsonToUser(String data){
		if(org.apache.commons.lang3.StringUtils.isBlank(data)){
			return null;
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(data)) {
			ResultObjOne<User> result = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<User>>() {});
			if (result!=null && result.isSuccess()) {
				return result.getObj();
			}
		}
		return null;
	}

	@Override
	public UserIds getUserIdList(String userId){
		String data = userService.getUserIdList(userId);
		ResultObjOne<UserIds> result = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<UserIds>>() {});
		if(result!=null && result.isSuccess()){
			return result.getObj();
		}
		return null;
	}

	@Override
	public ResultBase sendEmailCode(String email){
		String json = userService.sendEmailCode(email);
		if (!StringUtils.isEmpty(json)) {
			return JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
		}
		return null;
	}

	@Override
	public User toUserHaveIds(User user){
		if(user==null || org.apache.commons.lang3.StringUtils.isBlank(user.getMobile())){
			return user;
		}
		if(org.apache.commons.lang3.StringUtils.isBlank(user.getMainId())){
			UserIds userIds = this.getUserIdList(user.getUserId());
			if(userIds == null){//说明获取失败
				logger.error("getUserIdList error  once， userId:{} ",user.getUserId());
				userIds = this.getUserIdList(user.getUserId());
				if(userIds == null){//第二次失败，则不注入
					logger.error("getUserIdList error  two  error， userId:{} ",user.getUserId());
					return user;
				}
			}
			user.setMainId(userIds.getMainId());
			user.setUserIds(userIds.getUserIds());
			user.setCartCount(cartCount(user.getUserId()));	/*用户添加购物车数量*/
		}
		return user;
	}

	/**
	 * 查询用户购物车数量
	 * @param userId
	 * @return
     */
	private int cartCount(String userId){

		JSONObject reqJSON = new JSONObject();
		reqJSON.element("userId",userId);
		String json=cartService.cartCountV3(reqJSON.toString());
		logger.debug("调用base接口返回数据:" + json);
		ResultObjOne<Map<String,String>> obj = null;
		try {
			obj = JSONUtils.toGenericsCollection(json, ResultObjOne.class, Map.class);
			if (!Constants.SUCCESS.equals(obj.getCode())) {
				return 0;
			}else{
				return Integer.parseInt(obj.getObj().get("cartGoodsCount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public UserLevelInfo getUserLevelInfo(String userId) {
		String data=userService.userLevelInfo(userId);
		ResultObjOne<UserLevelInfo> result = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<UserLevelInfo>>() {});
		if(result!=null && result.isSuccess()){
			UserLevelInfo ul =result.getObj();
			if(ul==null) return null;
			String consumedAmount = ul.getConsumedAmount();
	        if(StringUtils.isNotBlank(consumedAmount) && consumedAmount.indexOf(".")>0){
	            ul.setConsumedAmount(consumedAmount.substring(0,consumedAmount.indexOf(".")));
	        }
	        String toNextLevelAmount = ul.getToNextLevelAmount();
	        if(StringUtils.isNotBlank(ul.getToNextLevelAmount())&& toNextLevelAmount.indexOf(".")>0){
	            ul.setToNextLevelAmount(toNextLevelAmount.substring(0,toNextLevelAmount.indexOf(".")));
	        }
	        return ul;
		}
		return null;
	}

	@Override
	public ResultBase setUserTrial(String userId,String inviteCode) {
		String data=userService.setUserTrial(userId,inviteCode);
		return JsonUtil.fromJson(data, new TypeToken<ResultBase>() {});
	}

	@Override
	public ResultObjOne<User> modifyUserInfo(ModifyUserInfoReq req) {

		String json = userService.modifyUserInfo(
				req.getUserId(),req.getNickName(),req.getGender(),
				req.getMobile(), req.getEmail(),req.getBirthday());

		ResultObjOne<User> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<User>>() {});
		return result;
	}

	@Override
	public UserInvitedInfo getInviteCodeInfo(String userId) {

		String json = userService.getInvitedCodeInfo(userId);
		ResultObjOne<UserInvitedInfo> res = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<UserInvitedInfo>>() {});
		if (res!=null && res.isSuccess()) {
			return res.getObj();
		}
		return null;
	}
}
