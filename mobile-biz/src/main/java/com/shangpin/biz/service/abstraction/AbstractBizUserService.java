package com.shangpin.biz.service.abstraction;

import com.shangpin.base.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.shangpin.base.service.AoLaiService;
import com.shangpin.base.service.CommonService;
import com.shangpin.base.service.CustomerService;
import com.shangpin.biz.bo.QuickUser;
import com.shangpin.biz.bo.SourceEnum;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserBuyInfo;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.utils.ApiBizData;
import com.shangpin.biz.utils.Constants;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringUtil;

public abstract class AbstractBizUserService {

	public static Logger logger = LoggerFactory.getLogger(AbstractBizUserService.class);

	@Autowired
	public CommonService commonService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AoLaiService aoLaiService;
	
	@Autowired
	UserService userService;

/*	public String fromLogin(String userName, String password) {
		String json = userService.login(userName, password);
		return json;
	}*/



	/*public ResultObjOne<User> beLogin(String userName, String password) {
		String json = fromLogin(userName, password);
		if (!StringUtils.isEmpty(json)) {
			ResultObjOne<User> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<User>>() {
			});
			return result;
		}
		return null;
	}
*/
	/*public String fromRegister(String email, String phonenum, String password, String gender, String smscode, String type, String invitecode) {
		String json = commonService.register(email, phonenum, password, gender, smscode, type, invitecode);
		return json;
	}*/


	/*public ResultObjOne<User> beRegister(String email, String phonenum, String password, String gender, String smscode, String type, String invitecode) {
		String json = fromRegister(email, phonenum, password, gender, smscode, type, invitecode);
		if (!StringUtils.isEmpty(json)) {
			ResultObjOne<User> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<User>>() {
			});
			return result;
		}
		return null;
	}*/

//	public String fromFindUserInfo(String userId) {
//		String json = commonService.findUserInfo(userId);
//		return json;
//	}

//	public ResultObjOne<User> beFindUserInfo(String userId) {
//		String json = fromFindUserInfo(userId);
//		if (!StringUtils.isEmpty(json)) {
//			ResultObjOne<User> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<User>>() {
//			});
//			return result;
//		}
//		return null;
//	}


	public ResultObjOne<QuickUser> beCheckUser(String phone, String type, String source) {
		String json = fromCheckUser(phone, type, source);
		if (!StringUtils.isEmpty(json)) {
			ResultObjOne<QuickUser> result = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<QuickUser>>() {
			});
			return result;
		}
		return null;
	}

	private String fromCheckUser(String phone, String type, String source) {
		String json = commonService.checkUser(phone, type, source);
		return json;
	}




	public String getUserBuyInfo(String userId, String type) {
		String json = aoLaiService.findUserBuyInfo(userId, type);
		if (!StringUtils.isEmpty(json)) {
			return json;
		}
		return null;
	}

	public UserBuyInfo getUserBuyInfoObj(String userId, String type) {
		String json = getUserBuyInfo(userId, type);
		if (!StringUtils.isEmpty(json)) {
			ResultObjOne<UserBuyInfo> obj = JsonUtil.fromJson(json, new TypeToken<ResultObjOne<UserBuyInfo>>() {
			});
			return obj.getObj();
		}
		return null;
	}

	/**
	 * 保存发送手机信息到缓存中
	 * 
	 * @param phone
	 *            手机号码
	 * @param imei
	 *            手机imei值
	 * @param source
	 *            发送手机时间来源
	 * @author zghw
	 */
	public void saveSendPhoneInfo(String phone, String imei, SourceEnum source) {
		// 手机号码+imei+来源作为key
		String key = getSendPhoneInfoKey(phone, imei, source);
		// 递增添加到缓存中
		int expire = 0;
		switch (source) {
		case Ql:
			expire = 1800;
			break;
		}
		ApiBizData.incrKey(key, expire);
	}

	/**
	 * 缓存中的key
	 * 
	 * @author zghw
	 */
	private String getSendPhoneInfoKey(String phone, String imei, SourceEnum source) {
		String key = phone.concat("-").concat(imei).concat("-").concat(source.name());
		return key;
	}

	/**
	 * 是否打开验证
	 * 
	 * @param phone
	 *            手机号码
	 * @param imei
	 *            手机imei值
	 * @param source
	 *            发送手机时间来源
	 * @author zghw
	 */
	public boolean isOpenCheck(String phone, String imei, SourceEnum source) {
		// 手机号码+imei+来源作为key
		Integer count = getSendCount(phone, imei, source);
		boolean isOpenCheck = false;
		switch (source) {
		case Ql:
			if (count > 1) {
				isOpenCheck = true;
			}
			break;
		}
		return isOpenCheck;
	}
	/**
	 * 手机发送短信的次数
	 * 
	 * @param phone
	 *            手机号码
	 * @param imei
	 *            手机imei值
	 * @param source
	 *            发送手机时间来源
	 * @author zghw
	 */
	public Integer getSendCount(String phone, String imei, SourceEnum source) {
		String key = getSendPhoneInfoKey(phone, imei, source);
		String value = ApiBizData.getCache(key);
		Integer count = 0;
		if (StringUtil.isNotEmpty(value)) {
			count = Integer.valueOf(value);
		}
		return count;
	}
	 /**
     * 登录后在个人中心修改密码或修改礼品卡支付密码
     * @param userId
     * @param type
     * @param nowPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
	public String modifyPassword(String userId, String type,String nowPassword, String newPassword, String confirmPassword) {
		String data = commonService.fromModifyPassword(userId, type, nowPassword, newPassword, confirmPassword);
		return data;
	}

	/**
	 * 获取m站的域名
	 * @return
     */
	public String getShangpinDomain() {
		return commonService.getShangpinDomain();
	}

	/**
	 * 修改用户头像
	 * @param userId 用户id
	 * @param picUrl 头像url
     * @return
     */
	public String modifyUserInfoIcon(String userId, String picUrl) {
		return customerService.modifyUserInfoIcon(userId, picUrl);
	}


}
