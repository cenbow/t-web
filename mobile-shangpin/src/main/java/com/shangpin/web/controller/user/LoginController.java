package com.shangpin.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shangpin.web.controller.common.CaptchaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.service.SPBizCouponService;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.core.entity.AccountWeixinBind;
import com.shangpin.core.service.IWeixinBindService;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.CookieUtil;
import com.shangpin.web.utils.UserUtil;

/**
 * 用户管理的controller
 * 
 * @author zhongchao
 */
@Controller
public class LoginController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /** 跳转到登录页面 */
    public static final String LOGIN = "user/phone/login_phone";
    public static final String LOGIN_PHONE = "user/phone/login_phone2";
    /** 手机号注册页面   */
    private static final String REGISTE_PHONE = "user/phone/registe_phone";
    /** 邮箱登录页面   */
    public static final String TOLOGIN_EMAIL = "user/email/login_email";
    public static final String LOGIN_EMAIL = "user/email/login_email2";

    /** 邮箱、第三方账户绑定手机页面   */
    public static final String MAIL_BINDPHONE_PAGE = "/user/email/mail_bindtel";
    public static final String THIRD_BINDPHONE_PAGE = "/user/thirdLogin/third_bindtel";
    public static final String THIRD_BINDPHONE_PASS_PAGE = "/user/thirdLogin/third_bindtel2";

    /** 邮箱、第三方账户合并账户页面   */
    public static final String MAIL_COMBINE_PAGE = "/user/email/mail_combine";
    /** 跳转到首页 */
    @SuppressWarnings("unused")
    private static final String INDEX_PAGE = "redirect:/index";

    /** 个人中心 */
    private static final String USERINFO = "user/home";

//    private static final String WXBINDSUCCESS = "weixin/bindSuccess";
    private static final String SENDCOUPON = "coupon/sendCoupon";
    private static final Object RETURN_URL = "back";

    @Autowired
    private SPBizUserService userService;
    @Autowired
    private CaptchaServiceImpl captchaService;
    @Autowired
    private IWeixinBindService weixinBindService;
    @Autowired
    private SPBizCouponService bizCouponService;

    /**
     * 到邮箱登录页面
     */
    @RequestMapping(value = "/toEmailLogin")
    public String toEmailLogin() {
        return TOLOGIN_EMAIL;
    }

    /**
     * 跳转到登录界面
     * @param back      回调地址
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(@RequestParam(value = "back", required = false) String back, HttpServletRequest request) {
        logger.debug("back URL :" + back);
        //回调地址放入session
        request.getSession().setAttribute(Constants.THRIDLOGIN_SESSION_BACK,back==null?"":back);
        return LOGIN;
    }

    /**
     * 手机登录第一步，验证图形验证码是否正确
     * @param captcha 图形验证码
     * @param mobile 手机号
     * @return phone2 page
     */
    @RequestMapping(value = "/login/checkCode/{name:email|phone}")
    public String checkMobile(@RequestParam(value = "verCode") String captcha, @RequestParam(value = "mobi") String mobile, @PathVariable(value = "name")String name, HttpServletRequest request, ModelMap model) {
        String login;
        String login2;
        boolean isPhone =false;
        if("email".equals(name)){
            login = TOLOGIN_EMAIL;
            login2 = LOGIN_EMAIL;

        }else if("phone".equals(name)){
            login = LOGIN;
            login2 = LOGIN_PHONE;
            isPhone =true;
        }else{
            return LOGIN;
        }

        // 对验证码校验
        boolean result = captchaService.verifyCaptcha(request.getSession(), captcha);
        String msg;
        model.put("mobi", mobile);
        if (!result) {
            msg = com.shangpin.biz.utils.Constants.ERROR_IMG_CODE;
            model.put("msg", msg);
            return login;
        }
        //校验手机号是否存在
        UserCheck userCheck = userService.checkUserByPhoneOrEmail(mobile);
        if(userCheck==null){
            msg = com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
            model.put("msg", msg);
            return login;
        }
        String isExist = userCheck.getIsExist();
        if("1".equals(isExist)){//Type 1:手机  2:邮箱
           return login2;
        }else if("0".equals(isExist)){
            //新用户
            if(isPhone){
                return REGISTE_PHONE;//去手机注册
            }else{
                //邮箱账户
                msg = "该邮箱账号不存在，请重新输入！";
                model.put("msg", msg);
                return login;
            }
        }
        return LOGIN;
    }


    /**
     *
     * 用户登录
     * @param mobile    用户名
     * @param password   密码
     */
    @RequestMapping(value = "/login/normal", method = RequestMethod.POST)
    public String login( @RequestParam("mobi") String mobile, @RequestParam("password") String password,@RequestParam("checkType") String type ,HttpServletRequest request ,ModelMap model) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        if (user!=null) {
            session.removeAttribute(Constants.SESSION_USER);
        }
        String msg ;

        // 调用主站接口校验登录
        if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(password)) {
            try {
                user = userService.login(mobile, password);
                if (user!=null) {
                    String MODE_Mail ="2";
                    session.setAttribute(Constants.THRIDLOGIN_SESSION_TYPE,MODE_Mail);//2 邮箱  3 微信,4 QQ,5微博,6支付宝
                    model.put("type_num",MODE_Mail);
                    return UserUtil.loginUserToSession(userService.toUserHaveIds(user),session);
                } else {
                    model.put("mobi",mobile);
                    msg = "用户名或密码错误，请重新输入...";
                    model.put("msg", msg);
                    if("1".equals(type)){
                        return LOGIN_PHONE;
                    }else if("2".equals(type)){
                        return LOGIN_EMAIL;
                    }else{
                        return LOGIN;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception:" + e);
            }

        }
        return LOGIN_PHONE;
    }

    /**
     *
     * 手机注册
     * @param userName 用户名
     * @param password 密码
     * @param mobiCode 手机验证码
     *
     */
    @RequestMapping(value = "/registe/mobile", method = RequestMethod.POST)
    public String registerVerify( @RequestParam("mobi") String userName, @RequestParam("password") String password, @RequestParam(value = "mobiCode") String mobiCode, HttpServletRequest request,ModelMap model) {
        model.put("mobi", userName);
        String msg ;
        if (StringUtils.isBlank(userName)){
            msg="手机号不能为空！";
            model.put("msg", msg);
            return REGISTE_PHONE;// 跳转到注册页
        } else if(StringUtils.isBlank(password)){
            msg="密码不能为空！";
            model.put("msg", msg);
            return REGISTE_PHONE;// 跳转到注册页
        }else if(password.length()<6||password.length()>20){
            msg="密码长度为6-20位！";
            model.put("msg", msg);
            return REGISTE_PHONE;// 跳转到注册页
        }


        //注册增加推广参数channelNo channelId param channelType
        HttpSession session = request.getSession();
        Map<String, String> cookieMap = UserUtil.getThridBothCookie(request);
        // 调用用户系统接口注册 性别默认为保密
        String gender = "2";
        String data = userService.toRegister(userName, userName, password, password, gender, mobiCode, "1", null, cookieMap.get(com.shangpin.biz.utils.Constants.THRID_COOKIE_SOURCE), cookieMap.get(com.shangpin.biz.utils.Constants.THRID_COOKIE_CAMPAIGN), cookieMap.get(com.shangpin.biz.utils.Constants.THRID_COOKIE_PARAM), cookieMap.get(com.shangpin.biz.utils.Constants.THRID_COOKIE_CHANNEL_TYPE));
        if(org.apache.commons.lang3.StringUtils.isBlank(data)){
            msg= com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
            model.put("msg", msg);
            return REGISTE_PHONE;// 跳转到注册页
        }
        ResultObjOne<User> result = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<User>>() {});
        User user;
        if (result.isSuccess()) {
           user = result.getObj();
        }else {
            model.put("msg", result.getMsg());
            return REGISTE_PHONE;// 跳转到注册页
        }
        logger.info("调用注册接口成功返回数据:{}" , JsonUtil.toJson(user));
        //用户放入session获取回调地址
        String back = UserUtil.loginUserToSession(userService.toUserHaveIds(user), session);
        //发放注册优惠券 线上活动为首次购买9折，优惠券逻辑去掉
       /* Map<String, String> mapCoupon = SendCoupon.isSendCouponAndGetCoupon();
        if (mapCoupon != null) {
            if (mapCoupon.get("isSendCoupon").equals("true")) {
                bizCouponService.sendCoupon(user.getUserId(), "1", "coupon:" + mapCoupon.get("coupon"));
               *//* if("0".equals(coupon.get("code"))){
                    model.put("sendcoupondesc", mapCoupon.get("sendcoupondesc"));
                    return SENDCOUPON;
                }*//*
            }
        }*/
        if (!StringUtils.isEmpty(back)) {
            // 不为空就返回之前请求的地址
            return back;
        }
        // 跳转到个人中心
        logger.debug("login success  userid :{}, username:{} " , user.getUserId() ,user.getName());
        return USERINFO;
    }


    /**
     * @Description: 注册时重复发送手机验证码
     * @param mobi
     *            手机号
     * @Create By zhongchao
     * @Create Date 2014年10月22日
     */
    @ResponseBody
    @RequestMapping(value = "/repeatSendRegistPhoneCode", method = RequestMethod.POST)
    public String sendRegistPhoneCodeRepeat(@RequestParam("mobi") String mobi, HttpServletRequest request) {
        User user = getSessionUser(request);
        String userId = user == null ? mobi : user.getUserId();
        return userService.fromSendVerifyCode(userId, mobi, "尊敬的顾客，您正在注册尚品网，短信验证码为：{$verifyCode$}，请在页面内填写。如非本人操作，请联系客服4006-900-900。");
    }

    /**
     * @Description: 注册时重复发送手机验证码
     * @param mobi
     *            手机号
     * @Create By zhongchao
     * @Create Date 2014年10月22日
     */
    @ResponseBody
    @RequestMapping(value = "/repeatSendFindpwdCode", method = RequestMethod.POST)
    public String sendfindpwdCodeRepeat(@RequestParam("mobi") String mobi, HttpServletRequest request) {
        //User user = getSessionUser(request);
       // String userId = user == null ? mobi : user.getUserId();
        String jsonString = userService.fromSendVerifyCode(mobi, mobi, "您的验证码是：{$verifyCode$}，请及时输入验证。");
        return jsonString;
    }
//    /**
//     * @Description: 验证验证码
//     * @param captcha
//     *            验证码
//     * @Create By zhongchao
//     * @Create Date 2014年10月22日
//     */
//    @ResponseBody
//    @RequestMapping(value = "/checkCaptchaCode", method = RequestMethod.GET)
//    public String checkCaptchaCode(@RequestParam("captcha") String captcha,@RequestParam("mobi") String mobi, HttpServletRequest request, Map<String, Object> map) {
//    	//判断手机号是否注册过
//    	QuickUser quickUser = userService.checkUser(mobi, "0");
//        if(quickUser!=null&&"0".equals(quickUser.getIsNewUser())){
//        	return "{\"msg\": \"该手机号已注册过尚品网！\"}";
//        }
//        // 判断验证码是否正确
//        boolean flag = captchaService.verifyCaptcha(request.getSession(), captcha);
//        // String msg = (flag == true ? null : "手机号或验证码输入错误！");
//        if (!flag) {
//            return "{\"msg\": \"手机号或验证码输入错误！\"}";
//        }
//        return null;
//    }

    /**
     * @Description: 验证手机验证码
     * @param mobi
     *            手机号
     * @param verifycode
     *            手机验证码
     * @Create By zhongchao
     * @Create Date 2014年10月22日
     */
    @ResponseBody
    @RequestMapping(value = "/checkPhoneCode", method = RequestMethod.GET)
    public String checkPhoneCode(@RequestParam("mobi") String mobi, @RequestParam("verifycode") String verifycode, HttpServletRequest request) {
        User user = getSessionUser(request);

        String userId = user == null ? mobi : user.getUserId();
        // 调用主站接口对手机验证码进行验证
        return userService.fromSendVerifyCode(userId, mobi, verifycode);
    }



    /**
     *  退出登录状态
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout( HttpServletRequest request, HttpServletResponse response) {
        try {
            // 删除session中user
            String referer = request.getHeader("referer");
            String userId = getUserId(request);
            logger.debug("LoginController logout session id={} and sessionId={} ", userId,getSessionId(request));
            removeSessionUser(request);
            response.sendRedirect(referer);
        } catch (Exception e) {
            logger.error("注销异常：" + e);
        }
        return null;
    }

    /**
     * @Title:
     * @Description: 生成验证码
     * @param request
     * @param response
     * @Create By zhongchao
     * @Create Date 2014年10月22日
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void writeImage(HttpServletRequest request, HttpServletResponse response) {
        try {
            captchaService.writeImage(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("writeImage :" + e);
        }
    }

    /**
     * @Title:
     * @Description: 获取渠道号
     * @param request
     * @Create By zhongchao
     * @Create Date 2014年10月22日
     */
    public String getChannelNo(HttpServletRequest request) {
        String channelNo = request.getParameter(Constants.CHANNEL_PARAM_NAME);
        if (StringUtils.isEmpty(channelNo)) {
            if (CookieUtil.getCookieByName(request, Constants.CHANNEL_PARAM_NAME) != null) {
                channelNo = CookieUtil.getCookieByName(request, Constants.CHANNEL_PARAM_NAME).getValue();
            } else {
                channelNo = Constants.SP_WAP_DEFAULT_CHANNELNO;
            }
        }
        return channelNo;
    }

    private void WeixinBind(String wxId, String userName, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 绑定
        AccountWeixinBind accountWeixinBind = new AccountWeixinBind();
        accountWeixinBind.setWeixinId(wxId);
        accountWeixinBind.setCreateTime(new Date());
        accountWeixinBind.setLoginName(userName);
        accountWeixinBind.setUserId(user.getUserId());
        accountWeixinBind.setNickname(user.getName());
        accountWeixinBind.setGender(user.getGender());
        try {
            accountWeixinBind.setRegTime(sdf.parse(user.getRegTime()));
            accountWeixinBind.setRegOrigin(user.getRegOrigin());
            accountWeixinBind.setBindTime(new Date());
            accountWeixinBind.setMarkup("hand");
            weixinBindService.addOrModifyAccountWeixinBind(accountWeixinBind);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void WeixinBindModify(AccountWeixinBind accountWeixinBind, String userName, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 绑定
        accountWeixinBind.setCreateTime(new Date());
        accountWeixinBind.setLoginName(userName);
        accountWeixinBind.setUserId(user.getUserId());
        accountWeixinBind.setNickname(user.getName());
        accountWeixinBind.setGender(user.getGender());
        try {
            accountWeixinBind.setRegTime(sdf.parse(user.getRegTime()));
            accountWeixinBind.setRegOrigin(user.getRegOrigin());
            accountWeixinBind.setBindTime(new Date());
            accountWeixinBind.setMarkup("hand");
            weixinBindService.addOrModifyAccountWeixinBind(accountWeixinBind);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Title: wexinLoginURL
     * @Description: 微信定登陆请求并拼接back url
     * @param
     * @return String
     * @throws
     * @Create By liling
     * @Create Date 2014年11月17日
     */
    private String wexinLoginURL(String backUrl) {
        StringBuilder url = new StringBuilder("redirect:");

        url.append("/login");
        if (!StringUtils.isEmpty(backUrl)) {
            url.append("?");
            url.append(RETURN_URL).append("=");
            String encoderReturnURL;
            try {
                encoderReturnURL = URLEncoder.encode(backUrl, Constants.DEFAULT_ENCODE);
                url.append(encoderReturnURL);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return url.toString();
    }
    
    /**
     * 验证下发手机的验证码是否正确
     * @param mobi
     * @param verifycode
     * @return
     */
    private boolean checkPhoneCode(String mobi, String verifycode) {
        // 调用主站接口对手机验证码进行验证
    	ResultBase result = userService.beVerifyPhoneCode(mobi, mobi, verifycode);
        if(!result.isSuccess()){
        	return false; 
        }
        return true;
    }

}