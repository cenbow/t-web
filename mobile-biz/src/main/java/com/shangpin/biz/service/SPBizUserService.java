package com.shangpin.biz.service;

import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.user.ModifyUserInfoReq;
import com.shangpin.biz.bo.user.UserInvitedInfo;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.core.entity.AccountWeixinBind;

public interface SPBizUserService {
    /**
     * 根据手机号或邮箱查找用户
     * @param userName  邮箱或手机号
     */
    public User findUserByUserName(String userName);

    /**
     * 根据用户Id（尚品、奥莱完全相同）
     * 
     * @param userId
     *            用户Id
     * @return
     * @author sunweiwei
     */
    public User findUserByUserId(String userId);

    /**
     * 登录（尚品、奥莱完全相同）
     * 
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @return
     */
    public User login(String userName, String password);

    /**
     * 微信登录（尚品、奥莱完全相同）
     */
    public User weixinAutoLogin(AccountWeixinBind account);

    /**
     * 
     * @Title: checkUser
     * @Description: 检查是否为新用户
     * @param phone
     * @param type
     *            0:只查找是否存在；1：查找是否存在用户，不存在创建一个新的用户
     * @return QuickUser
     */
    public QuickUser checkUser(String phone, String type);
    
    /**
     * 获取用户购买信息
     * @param userId
     * @param type
     * @return
     */
    public UserBuyInfo getUserBuyInfoObj(String userId, String type);

	public QuickUser checkUser(String phone, String createNewUser,
			String fashionRegisteSource);
	
	/**
	 * 检查手机号是否注册尚品用户
	 * @param phoneNum
	 * @param channelNo
	 * @return
	 */
	public QuickUser checkPhoneUser(String phoneNum, String channelNo);

    /**
     * 微信 qq 第三方登录
     * @param invitecode 邀请码
     * @param mode 请求来源：qq weixin等
     * @param uid   第三方的用户id openid
     * @param gender   gender
     * @param nickname 第三方的昵称
     * @param truename 第三方的用户真实名字
     * //@param version 版本号
     */
    public String thirdLogin(String mode, String invitecode, String uid,String gender,String nickname,String truename );

    /**
     * 获取m站的域名
     * @return
     */
    public String getShangpinDomain();


    /**
     * 修改用户头像
     * @param userId 用户id
     * @param iconUrl 头像url
     */
    public String modifyUserInfoIcon(String userId, String iconUrl);


    /**
     *  m站用户注册
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
    public String toRegister(String email, String phonenum, String password,String confirmPassword, String gender, String smscode , String type, String invitecode,String channelNo,String channelId,String param,String channelType );

    /**
     * 手机号（邮箱）重置用户的登录密码
     * @param username 用户手机号 / 或者邮箱
     * @param password 新密码
     * @param smsCode 短信验证码 / 邮箱验证码
     * //@param version 版本号
     * //@param plat 来源 1：pc   2：m站  3：客户端
     * @return
     */
    public ResultBase resetLoginPassword(String username, String password,String smsCode);


    /**
     * 发送手机验证码（尚品、奥莱完全相同）
     *
     * @param userId 用户ID
     * @param phoneNum  手机号
     * @param msgTemplate   发送验证码的文字模版
     */
    public String fromSendVerifyCode(String userId, String phoneNum, String msgTemplate);


    /**
     * 验证手机号
     * @param userId       用户ID
     * @param phoneNum     手机号
     * @param verifyCode   验证码
     */
    public ResultBase beVerifyPhoneCode(String userId, String phoneNum, String verifyCode);

    /**
     * 第三方或者邮箱用户绑定手机
     * @param mobile 手机号
     * @param type 1：邮箱 2：第三方
     * @param userId 用户id
     * //@param plat 平台 2：m站
     * //@param version 版本
     * @param os 操作系统
     */
    public ResultBase thirdLoginBindPhone(String mobile,String type,String userId,String os,String password);


    /**
     * 用户绑定相关
     * @param userId userId
     * @param typeInfo
     *            绑定的信息 绑定手机例如：typeInfo=bind:13699120345
     *            绑定优惠券例如：typeInfo=coupon:123456
     */
    public ResultBase beBindToUser(String userId, String typeInfo);

    /**
     * 根据邮箱或者手机号判断用户是否存在
      * @param userName 手机或邮箱
     * @return UserCheck
     */
    public UserCheck checkUserByPhoneOrEmail(String userName);

    /**
     * 某个用户id查询用户的所有账号id
      * @param userId 手机或邮箱
     * @return String
     */
    public UserIds getUserIdList(String userId);

    /**
     * 向邮箱发送随机验证码
     * @param email 邮箱
     * //@param plat 来源类型
     * //@param version 版本号
     * @return ResultBase
     */
    public ResultBase sendEmailCode(String email);

    /**
     * 将用户的所有id注入进去
     */
    public User toUserHaveIds(User user);

    /**
     * 获取用权益信息 
     * <br/>
     * @param userId
     * @return
     */
    UserLevelInfo getUserLevelInfo(String userId);

    /**
     * 设置用户试用vip
     * <br/>
     * @param userId
     * @return
     */
    ResultBase setUserTrial(String userId,String inviteCode);


    ResultObjOne<User> modifyUserInfo(ModifyUserInfoReq req);

    /**
     * 获取用户邀请信息接口
     * @param userId
     * @return
     */
    UserInvitedInfo getInviteCodeInfo(String userId);
}
