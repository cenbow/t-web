/**
 * <pre>
 * Copyright:       Copyright(C) 2010-2014, shangpin.com
 * Filename:        com.shangpin.controller.UserController.java
 * Class:           UserController
 * Date:            2014-07-11
 * Author:          <a href="mailto:sundful@gmail.com">sundful</a>
 * Version          2.0
 * Description:     
 *
 * </pre>
 **/

package com.shangpin.web.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

/**
 * 用户管理的controller
 * 
 * @author sunweiwei
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /** 个人中心 */
    private static final String USERINFO = "user/home";

    private static final String FORGET_PASSWORD_EMAIL = "user/email/mail_resetPwd";
    private static final String FORGET_PASSWORD_PHONE = "user/phone/tel_resetPwd1";
    private static final String FORGET_PASSWORD_PHONE2 = "user/phone/tel_resetPwd2";
    @Autowired
    private SPBizUserService userService;
    @Autowired
    private CaptchaServiceImpl captchaService;
    /**
     * @Title: userinfo
     * @Description: 跳转到个人中心
     * @author zghw
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String toUserInfo(HttpServletRequest request, ModelMap model) {
        try {
            User user = getSessionUser(request);
            if (user != null) {
            	if(getUserLevelInfo(request)==null){
            		UserLevelInfo userLevel = userService.getUserLevelInfo(user.getUserId());
            		if(userLevel !=null)
            			request.getSession().setAttribute(Constants.SESSION_USER_LEVEL,userLevel);            		
            	}
                return USERINFO;
            }
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return LoginController.LOGIN;
    }

    @RequestMapping("/checkPhoneUser")
    @ResponseBody
    public Map<String,String> checkPhoneUser(@RequestParam("mobile") String mobile){
        //校验手机号是否存在
        UserCheck userCheck = userService.checkUserByPhoneOrEmail(mobile);
        Map<String,String> map =new HashMap<>();
        map.put("isExist","0");
        if(userCheck==null){
            return map;
        }
        String isExist = userCheck.getIsExist();
        if("1".equals(isExist)){
            map.put("isExist","1");
            return map;
        }
        return map;
    }


    /**
     * 跳转到邮箱找回密码页面
     * @return String
     */
    @RequestMapping(value = "/toFindPwd/{name:email|phone}",method = RequestMethod.GET)
    public String toForgetpasswordEmail(@RequestParam(value = "mobi" ,required = false) String mobi, @PathVariable("name")String name , ModelMap model) {
        if(StringUtils.isNotBlank(mobi)){
            model.addAttribute("mobi",mobi);
        }
        if("email".equals(name)){
            return FORGET_PASSWORD_EMAIL;
        }else if("phone".equals(name)){
            return FORGET_PASSWORD_PHONE2;
        }
        return USERINFO;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/toFindPwd",method = RequestMethod.GET)
    public String toForgetpasswordEmail() {
        return FORGET_PASSWORD_PHONE;
    }


    /**
     * 手机找回密码校验
     * @return String
     */
    @RequestMapping(value = "/toFindPwd/checkCode",method = RequestMethod.POST)
    public String checkCodePhone(@RequestParam(value = "mobi" ) String mobile,@RequestParam("verCode")String iconCode, ModelMap model,HttpServletRequest request) {
        // 对验证码校验
        boolean result = captchaService.verifyCaptcha(request.getSession(), iconCode);
        String msg;
        model.put("mobi", mobile);
        if (!result) {
            msg = com.shangpin.biz.utils.Constants.ERROR_IMG_CODE;
            model.put("msg", msg);
            return FORGET_PASSWORD_PHONE;
        }
        //校验手机号是否存在
        UserCheck userCheck = userService.checkUserByPhoneOrEmail(mobile);
        if(userCheck==null){
            msg = com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
            model.put("msg", msg);
            return FORGET_PASSWORD_PHONE;
        }
        String isExist = userCheck.getIsExist();
        if(!"1".equals(isExist)){
            msg="该手机号不存在，请重新输入！";
            model.put("msg", msg);
            return FORGET_PASSWORD_PHONE;
        }
        return FORGET_PASSWORD_PHONE2;
    }

    /**
     * 手机或邮箱重置登录密码
     * @return String
     */
    @RequestMapping(value = "/resetPwd/{name:email|phone}",method = RequestMethod.POST)
    public String resetPwdByPhone(@RequestParam(value = "mobi" ) String username,@RequestParam("mobiCode")String mobiCode, @RequestParam("password") String password,@PathVariable("name") String name, ModelMap model,HttpServletRequest request) {
        String backPage ;
        String login ;
        if("email".equals(name)){
            backPage =  FORGET_PASSWORD_EMAIL;
            login = LoginController.LOGIN_EMAIL;
        }else if("phone".equals(name)){
            backPage =  FORGET_PASSWORD_PHONE2;
            login= LoginController.LOGIN_PHONE;
        }else{
            return LoginController.LOGIN;
        }

        String msg;
        model.addAttribute("mobi",username);
        if(StringUtils.isBlank(password)){
            msg="密码不能为空！";
            model.put("msg", msg);
            return backPage;
        }else if(password.length()<6||password.length()>20){
            msg="密码长度为6-20位！";
            model.put("msg", msg);
            return backPage;
        }
        //重新设定密码
        ResultBase resultBase = userService.resetLoginPassword(username, password, mobiCode);
        if(resultBase==null){
            msg = com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA;
            model.put("msg", msg);
            return backPage;
        }
        if(!"0".equals(resultBase.getCode())){
            msg = resultBase.getMsg();
            model.put("msg", msg);
            return backPage;
        }
        //清除掉当前session用户
        removeSessionUser(request);
        return login;//去登录
    }

    /**
     * 邮箱发送验证码
     * @return String
     */
    @RequestMapping(value = "/sendEmailCode",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> sendEmailCode(@RequestParam(value = "mail" ) String email) {
        Map<String,String> map =new HashMap<>();
        ResultBase resultBase = userService.sendEmailCode(email);
        if(resultBase==null){
            map.put("code","1");
            map.put("msg", com.shangpin.biz.utils.Constants.ERROR_REQUEST_NO_DATA);
        }else if(resultBase.isSuccess()){
            map.put("code","0");
        }else{
            map.put("code","1");
            map.put("msg",resultBase.getMsg());
        }
        return map;
    }

    /**
     * 获取小能的用户信息
     * @return map
     */
    @RequestMapping(value = "/ajax/chartUser")
    @ResponseBody
    public Map<String,String> getChartUser(HttpServletRequest request) {
        Map<String,String> map =new HashMap<>();
//        String userId;
//        if (StringUtils.isBlank(userId = getUserId(request))){
//            if(StringUtils.isBlank(userId = request.getHeader("userid"))){
//                return map;
//            }
//        }
//        User user = userService.findUserByUserId(userId);
//        if(user!=null){
//            map.put("userId",userId);
//            map.put("isVip","0006".endsWith(user.getLv())?"1":"0");
//        }
        User user = getSessionUser(request);
        if (user == null || org.apache.commons.lang.StringUtils.isEmpty(user.getUserId())) {
            // 未登陆
            map.put("isLogin","0");
            return map;
        }
        // 已经登录
        map.put("isLogin","1");
        map.put("userId",user.getUserId());
        map.put("level",user.getLv());
        map.put("userName",user.getName());
        map.put("isVip","0006".endsWith(user.getLv())?"1":"0");
        return map;
    }


}