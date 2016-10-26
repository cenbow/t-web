package com.shangpin.base.service;

/**
 * 用户相关的service
 * @author fengwenyu
 *
 */
public interface UserService {

	/**
	 * 登录
	 * @param userName    用户名
	 * @param password    密码
	 */
	public String login(String userName, String password);

	/**
	 *
	 * @param email 用户名称 当邮箱注册时，填邮箱，否则填手机
	 * @param phonenum 手机
	 * @param password 密码
	 * @param confirmPassword 确认密码
	 * @param gender 性别
	 * @param smscode 手机验证码
	 * //@param plat 来源：0 未知	1 pc	2 M站	3 客户端
	 * @param type 登录类型 1手机号 2 邮箱
	 * @param invitecode 短信邀请码
	 * @param channelNo 参数与网盟的source对应
	 * @param channelId 参数与网盟的campaign对应
	 * @param param  网盟自定义参数，如无参数可为空
	 * @param channelType 尚品客为0，网盟为1；当为空时，后台默认为0
	 * //@param version 版本号。读取m站的配置文件
	 */
	public String register(String email, String phonenum, String password, String confirmPassword, String gender, String smscode,  String type, String invitecode, String channelNo, String channelId, String param, String channelType);

	/**
	 * 根据手机号或邮箱查找用户
	 * @param userName  邮箱或手机号
	 */
	public String findUserByUserName(String userName);


	/**
	 * 第三方登陆（尚品、奥莱完全相同）
	 *
	 * @param mode
	 *            登录方式：weibo 新浪微博；qq 腾讯qq；zhifubao 支付宝；
	 * @param inviteCode
	 *            邀请码
	 * @param uid
	 *            第三方返回的用户id，如果已经入库，则忽略后面的字段
	 * @param sex
	 *            第三方返回的性别(0女1男),为空时不改变原有值
	 * @param nickName
	 *            第三方返回的昵称 ,为空时不改变原有值
	 * @param trueName
	 *            第三方返回的真是姓名, 为空时不改变原有值
	 */
	public String thirdLogin(String mode, String inviteCode, String uid, String sex, String nickName, String trueName);


	/**
	 * 手机号重置用户的登录密码
	 * @param username 用户手机号
	 * @param password 新密码
	 * @param smsCode 短信验证码
	 * //@param version 版本号
	 * //@param plat 来源 1：pc   2：m站  3：客户端
	 * @return
	 */
	public String resetLoginPassword(String username, String password,String smsCode);


	/**
	 * 发送手机验证码（尚品、奥莱完全相同）
	 * @param userId 用户ID
	 * @param phoneNum  手机号
	 * @param msgTemplate   发送验证码的文字模版
	 */
	public String sendVerifyCode(String userId, String phoneNum,String msgTemplate);


	/**
	 * 验证手机号（尚品、奥莱完全相同）
	 * @param userId   用户ID
	 * @param phoneNum	  手机号
	 * @param verifyCode	验证码
	 */
	public String verifyPhoneCode(String userId, String phoneNum,String verifyCode);



	/**
	 *  获取用户信息
	 * @param userId 用户id
	 * @return
	 */
	public String getUserInfo(String userId);

	/**
	 * 第三方或者邮箱用户绑定手机
	 * @param mobile 手机号
	 * @param type 1：邮箱 2：第三方
	 * @param userId 用户id
	 * //@param plat 平台 2：m站
	 * //@param version 版本
	 * @param os 操作系统
	 */
	public String thirdLoginBindPhone(String mobile,String type,String userId,String os,String password);

	/**
	 * 用户绑定相关的信息
	 *
	 * @param userId userId
	 * @param typeInfo
	 *            绑定的信息 绑定手机例如：typeInfo=bind:13699120345
	 *            绑定优惠券例如：typeInfo=coupon:123456
	 */
	public String bindToUser(String userId, String typeInfo);

	/**
	 * 判断手机号是否注册过尚品用户
	 * @param phoneNum 手机号
	 * @param channelNo 渠道号
	 */
	public String checkPhoneUser(String phoneNum, String channelNo);

	/**
	 * 根据邮箱或者手机号判断用户是否存在
	 * @param userName 手机或邮箱
	 * @return UserCheck
	 */
	public String checkUserByPhoneOrEmail(String userName);

	/**
	 * 根据用户id查询用户下的所有id集合
	 * @param userId 用户id
	 * @return String
     */
	public String getUserIdList(String userId);

	/**
	 * 向邮箱发送随机验证码
	 * @param email 邮箱
	 * //@param plat 来源类型
	 * //@param version 版本号
	 * @return String
	 */
	public String sendEmailCode(String email);
	
	/**
	 * 用户权益信息
	 * <br/>消费金额，下一级别需要金额
	 * @param userId 用户id
	 * @return
	 */
	public String userLevelInfo(String userId);

	/**
	 * 获取用户购买订单数量,礼品卡数量等
	 * @param userId
	 * @param checktime
	 * @param shoptype
     * @return
     */
	String userBuyInfo(String userId, String checktime, String shoptype);

	/**
	 * 设置用户试用vip
	 * @param userId
	 * @return
	 */
	String setUserTrial(String userId,String inviteCode);

	/**
	 * 修改用户基本信息接口
	 /**
	 * nickName	昵称	String	非必须
	 gender	性别	String	非必须	0：女，1：男，2：保密
	 birthday	生日	String	非必须	，生日格式为时间戳
	 mobile	手机	String	非必须
	 email	邮箱	String	非必须
	 userId	用户ID	String	必须
	 version	版本号	String	必须	例如 2.9.17
	 plat	用户来源	int	非必须
	 0 未知
	 1 pc
	 2 M站
	 3 客户端
	 */

	String modifyUserInfo(String userId, String nickName, String gender, String mobile, String email, String birthday);

	/**
	 * 获取用户邀请信息接口
	 * @param userId
	 * @return
	 */
	String getInvitedCodeInfo(String userId);
}
