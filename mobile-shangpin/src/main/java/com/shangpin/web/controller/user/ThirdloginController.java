package com.shangpin.web.controller.user;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.UserIds;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.utils.HttpClientUtil;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.PropertyUtil;
import com.shangpin.web.utils.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * m站第三方登录controller
 * @author fengwenyu
 *
 */
@Controller
@RequestMapping("/thirdLogin")
public class ThirdloginController extends BaseController{
    /**
     * qq和微信的公众号id及access_token
     */
    private static final Logger logger  = LoggerFactory.getLogger(ThirdloginController.class);
    private final String QQ_APPID= PropertyUtil.getString("third.qq.app.id");
    private final String QQ_APP_ACCESS_KEY= PropertyUtil.getString("third.qq.app.access.key");
    private final String WX_APPID= PropertyUtil.getString("third.wx.app.id");
    private final String WX_APP_ACCESS_KEY= PropertyUtil.getString("third.wx.app.access.key");
    private final String WB_APPID= PropertyUtil.getString("third.wb.app.id");
    private final String WB_APP_ACCESS_KEY= PropertyUtil.getString("third.wb.app.access.key");

    private final String MODE_QQ= "4";
    private final String MODE_WEIXIN= "3";
    private final String MODE_WEIBO= "5";



    @Autowired
    SPBizUserService userService;


    /**
     * 第三方qq登录
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/qqlogin")
    public String thirdQQLogin(String code ,String state,HttpServletRequest request,ModelMap model) {
        String uuid;
        if(state!=null){
            uuid = state;
        }else{
            uuid = UUID.randomUUID().toString();
        }
        String qq_back_url = userService.getShangpinDomain()+"thirdLogin/qqlogin";
        if(StringUtils.isBlank(code)){
            //用户未登录状态，拼装参数去登录
            return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id="+QQ_APPID+"&state="+uuid+"&g_ut=1&which=ConfirmPage&redirect_uri="+qq_back_url;
        }
        //获取Access Token
        String accessUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+QQ_APPID+"&client_secret="+QQ_APP_ACCESS_KEY+"&code="+code+"&redirect_uri="+qq_back_url;
        String accessData = HttpClientUtil.doGet(accessUrl);
        String access_token=null;
        String expires_in=null;
        if(StringUtils.isNotBlank(accessData) && accessData.length()>0){
            String[] accesss = accessData.trim().split("&");
            if(accesss.length>0){
                for (String access : accesss) {
                    String[] k_v = access.split("=");
                    if(k_v.length==2){
                        switch (k_v[0].trim()){
                            case "access_token":
                                access_token = k_v[1];break;
                            case "expires_in":
                                expires_in = k_v[1];break;
//                            case "refresh_token":
//                                refresh_token = k_v[1];break;
                        }
                    }
                }
            }
        }
        if(access_token==null || expires_in ==null){
            logger.info("获取qq的accesstoken失败，返回值{}",accessData);
            return null;
        }
        //拿到用户的qq号
        String qq_url="https://graph.qq.com/oauth2.0/me?access_token="+access_token;
        String qq_num = HttpClientUtil.doGet(qq_url);
        if(qq_num!=null){
            qq_num = qq_num.replace("callback(","").replace(")","").replace("\n","").replace(";","");
        }
        JSONObject qqnum_obj = JSONObject.fromObject(qq_num);
        String openid = qqnum_obj.getString("openid");
        if(openid==null){
            logger.info("获取qq用户info信息失败,返回值：{}",qqnum_obj);
            return null;
        }
        //获取用户信息
        String user_info_url="https://graph.qq.com/user/get_user_info?access_token="+access_token+"&oauth_consumer_key="+QQ_APPID+"&openid="+openid;
        String qq_info_data = HttpClientUtil.doGet(user_info_url);
        JSONObject info_obj = JSONObject.fromObject(qq_info_data);
        String nickName = info_obj.getString("nickname");
        String qq_gender = info_obj.getString("gender");
        String gender;
        if("男".equals(qq_gender)){
            gender="1";
        }else if ("女".equals(qq_gender)){
            gender="0";
        }else {
            gender ="2";
        }
       // String user_icon = info_obj.getString("figureurl");//头像 暂时未使用  剩余可选值figureurl_1，figureurl_2，figureurl_qq_1，figureurl_qq_2
        //调用登录
        String login_data = userService.thirdLogin(MODE_QQ, null, openid, gender, nickName, null);
        //登录成功,检测是否绑定手机号
        model.put("type_num",MODE_QQ);
        return loginToSession(login_data,request,MODE_QQ);
    }


    /**
     * 第三方微信登录
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/wxlogin")
    public String thirdWXLogin(String code ,String state,HttpServletRequest request,ModelMap model) {
        String uuid ;
        if(state!=null){
            uuid = state;
        }else{
            uuid = UUID.randomUUID().toString();
        }
        String wx_back_url = userService.getShangpinDomain()+"thirdLogin/wxlogin";
       if(StringUtils.isBlank(code)){
           return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APPID+"&redirect_uri="+wx_back_url+"&response_type=code&scope=snsapi_userinfo&state="+uuid+"#wechat_redirect";
       }

        //获取access_token
        String access_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WX_APPID+"&secret="+WX_APP_ACCESS_KEY+"&code="+code+"&grant_type=authorization_code";
        String access_data = HttpClientUtil.doGet(access_url);
        JSONObject access_obj = JSONObject.fromObject(access_data);
        String access_token = access_obj.getString("access_token");
        String openid = access_obj.getString("openid");
        if(access_token==null|| openid==null){
            logger.info("获取微信accessid、openid失败，返回值：{}",access_data);
            return null;
        }

        //获取用户信息
        String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String userinfo_data = HttpClientUtil.doGet(userinfo_url);
        if(userinfo_data==null || userinfo_data.contains("errcode")){
            logger.info("获取微信用户info信息失败，返回值："+userinfo_data);
            return null;
        }
        JSONObject userinfo_obj = JSONObject.fromObject(userinfo_data);
        String nickName = userinfo_obj.getString("nickname");
//        String icon = userinfo_obj.getString("headimgurl");
        String sex = userinfo_obj.getString("sex");//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        String gender;
        if("1".equals(sex)){
            gender = "1";//男
        }else if("2".equals(sex)){
            gender = "0";//女
        }else {
            gender = "2";
        }
        String login_data = userService.thirdLogin(MODE_WEIXIN, null, openid, gender, nickName, null);
        model.put("type_num",MODE_WEIXIN);
        return loginToSession(login_data,request,MODE_WEIXIN);
    }
    /**
     * 第三方微博登录
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/wblogin")
    public String thirdWBLogin(String code ,String state,HttpServletRequest request,ModelMap model) {
        String uuid ;
        if(state!=null){
            uuid = state;
        }else{
            uuid = UUID.randomUUID().toString();
        }
        String wb_back_url = userService.getShangpinDomain()+"thirdLogin/wblogin";
       if(StringUtils.isBlank(code)){
           return "redirect:https://api.weibo.com/oauth2/authorize?client_id="+WB_APPID+"&redirect_uri="+wb_back_url+"&response_type=code&state="+uuid;
       }

        //获取access_token
        String access_url = "https://api.weibo.com/oauth2/access_token";
        Map<String,String> param = new HashMap<>();
        param.put("client_id",WB_APPID);
        param.put("client_secret",WB_APP_ACCESS_KEY);
        param.put("grant_type","authorization_code");
        param.put("redirect_uri",wb_back_url);
        param.put("code",code);
        String access_data = HttpClientUtil.doPost(access_url,param);
        JSONObject access_obj = JSONObject.fromObject(access_data);
        String access_token = access_obj.getString("access_token");
        //获取微博用户的uid
        String uid = access_obj.getString("uid");
        if(access_token==null||uid==null){
            logger.info("获取微博access_token和uid失败，返回值{}",access_data);
            return null;
        }
        //获取用户信息
        String userinfo_url = "https://api.weibo.com/2/users/show.json?access_token="+access_token+"&uid="+uid;
        String userinfo_data = HttpClientUtil.doGet(userinfo_url);
        if(userinfo_data==null || userinfo_data.contains("error_code")){
            logger.info("获取微信用户info信息失败，返回值{}",userinfo_data);
            return null;
        }

        JSONObject userinfo_obj = JSONObject.fromObject(userinfo_data);
        String nickName = userinfo_obj.getString("screen_name");
        String idstr = userinfo_obj.getString("idstr");
        String name = userinfo_obj.getString("name");
//        String icon = userinfo_obj.getString("profile_image_url");
        String sex = userinfo_obj.getString("gender");//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        String gender;//m：男、f：女、n：未知
        if("m".equals(sex)){
            gender = "1";//男
        }else if("f".equals(sex)){
            gender = "0";//女
        }else {
            gender = "2";
        }
        String login_data = userService.thirdLogin(MODE_WEIBO, null, idstr, gender, nickName, name);
        model.put("type_num",MODE_WEIBO);
        return loginToSession(login_data,request,MODE_WEIBO);
    }

    /**
     * 将用户放入session
     * @param data
     *
     */
    private String loginToSession(String data,HttpServletRequest request,String thirdType){
        ResultObjOne<User> result = JsonUtil.fromJson(data, new TypeToken<ResultObjOne<User>>() {
        });
        if("0".equals(result.getCode())){
            User user = result.getContent();
            HttpSession session = request.getSession();
            session.setAttribute(Constants.THRIDLOGIN_SESSION_TYPE,thirdType);//2 邮箱  3 微信,4 QQ,5微博,6支付宝
            return UserUtil.loginUserToSession(userService.toUserHaveIds(user),session);//放入session成功，跳转到登录前的回调地址
        }
        logger.info("调取主站的第三方登录失败，返回值为：{}",data);
        return null;
    }


   /* private User uploadUserIcon(String iconUrl, User user){
        String icon = user.getIcon();
        //数据库头像为空,并且传递过来的头像不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(icon)&&org.apache.commons.lang3.StringUtils.isNotBlank(iconUrl)){
            //调用上传头像服务
            String result = userService.modifyUserInfoIcon(user.getUserId(), iconUrl);
            logger.info("iconResult头像服务："+result);
            ResultObjOne<User> objOne = JsonUtil.fromJson(result, new TypeToken<ResultObjOne<User>>() {
            });
            if(objOne!= null && "0".equals(objOne.getCode())){
                user.setIcon(iconUrl);
            }
        }
        //增加添加头像逻辑结束
        //给用户增加默认头像
        return UserUtil.setDefaultUserIcon(user);
    }*/


    @RequestMapping(value = "/bindPhone")
    public String bindTel(@RequestParam("mobi") String phone,@RequestParam(value = "mobiCode",required = false) String verifyCode,@RequestParam(value="bindteltype")String bindType, @RequestParam(value = "needCombine",required = false) String isNeedCombine,HttpServletRequest request, ModelMap model) {
        String backTel;
        if("1".equals(bindType)){
            backTel =  LoginController.THIRD_BINDPHONE_PAGE;
        }else if("2".equals(bindType)){
            backTel =  LoginController.THIRD_BINDPHONE_PASS_PAGE;
        }else if("3".equals(bindType)){
            backTel =  LoginController.MAIL_BINDPHONE_PAGE;
        }else if("4".equals(bindType)){
            backTel =  LoginController.MAIL_COMBINE_PAGE;
        }else{
            backTel = LoginController.LOGIN;
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Constants.THRIDLOGIN_SESSION_USER);
        if(user==null||StringUtils.isBlank(user.getUserId())){
            return "redirect:/login";
        }
        model.put("mobi",phone);
        String type = (String)session.getAttribute(Constants.THRIDLOGIN_SESSION_TYPE);//2 邮箱  3 微信,4 QQ,5微博,6支付宝
        boolean isEmail = false;
        boolean isThird = false;
        model.put("type_num",type);
        if("3".equals(type)){
            model.put("third_type", "微信");
            isThird = true;
        }else if("4".equals(type)){
            model.put("third_type", "QQ");
            isThird = true;
        }else if("5".equals(type)){
            model.put("third_type", "微博");
            isThird = true;
        }else if("2".equals(type)){
            isEmail = true;
        }
        if(StringUtils.isBlank(verifyCode)&&StringUtils.isNotBlank(phone)){
            //需要校验是否需要合并用户
            UserCheck userCheck = userService.checkUserByPhoneOrEmail(phone);
            if(userCheck!=null){
                if("1".equals(userCheck.getIsExist())){
                    if(isEmail||isThird){
                        //到邮箱合并用户页面
                        return LoginController.MAIL_COMBINE_PAGE;
                    }
                }else if ("0".equals(userCheck.getIsExist()) && isThird){
                    //到第三方注册手机页面
                    return LoginController.THIRD_BINDPHONE_PASS_PAGE;
                }

            }else{
                return "redirect:/login";
            }
        }

        ResultBase result = userService.beVerifyPhoneCode(phone, phone,verifyCode);
        if (!result.isSuccess()) {
            // 验证失败，返回重新输入手机号码
            model.put("msg", result.getMsg());
            return backTel;//UserUtil.loginUserToSession(userService.toUserHaveIds(user),session);
        }
        //判断是那种系统
        String ua = request.getHeader("User-Agent").toLowerCase();
        String os;
        if(ua.contains("iphone")||ua.contains("ipod")||ua.contains("ipad")){
            os="ios";
        }else if(ua.contains("android")){
            os = "android";
        }else{
            os="windows pc";
        }
        // 绑定手机号
        String userType =(String) session.getAttribute(Constants.THRIDLOGIN_SESSION_TYPE);
        ResultBase bindObj = userService.thirdLoginBindPhone(phone,userType,user.getUserId(),os,null);
        if(!bindObj.isSuccess()){
            logger.info("绑定手机错误，返回信息：{}",JsonUtil.toJson(bindObj));
            if(StringUtils.isNotBlank(bindObj.getMsg())){
                model.put("msg", bindObj.getMsg());
            }
            return backTel;//LoginController.MAIL_BINDPHONE_PAGE;
        }
        if(isNeedCombine!=null && "1".equals(isNeedCombine)){
            //需要拉取主账户的信息
            User userByUserId =null;
            UserIds userIds = userService.getUserIdList(user.getUserId());
            if(userIds!=null){
                String mainId = userIds.getMainId();
                if(StringUtils.isNotBlank(mainId)){
                    userByUserId = userService.findUserByUserId(mainId);
                    if(userByUserId!=null){
                        userByUserId.setSessionId(user.getSessionId());
                        userByUserId.setMainId(mainId);
                        userByUserId.setUserIds(userIds.getUserIds());
                    }
                }
            }
            if(userByUserId!=null){
               user = userByUserId;
            }else{
                 return LoginController.LOGIN;
            }

        }

        //绑定成功，将用户信息放入session
        user.setMobile(phone);
        return UserUtil.loginUserToSession(userService.toUserHaveIds(user),session);
    }


}
