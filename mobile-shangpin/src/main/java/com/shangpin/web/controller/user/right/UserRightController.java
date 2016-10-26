package com.shangpin.web.controller.user.right;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shangpin.web.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.user.ModifyUserInfoReq;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.biz.utils.DateTimeUtil;
import com.shangpin.biz.utils.StringUtil;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringValidateUtil;
import com.shangpin.web.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * 黄金会员试用相关页面<br/>
 * @date    2016年9月6日 <br/>
 * @author   刘少卿
 * @since    JDK 7
 */
@Slf4j
@Controller
@RequestMapping("/user/right")
public class UserRightController extends BaseController{

    private static final String TRYPAGE="user/right/try_out";

    private static final String DOCPAGE="user/right/try_member_info";

    private final String ERRORCODE = "1";

    private final String SUCCODE = "0";

    @Autowired
    private SPBizUserService userService;

    @RequestMapping("/about")
    public String userRight(HttpServletRequest request,Model model){

        //每次进页面去接口取user的状态更新到session里面
        updateSessionUser(request,null);
        UserLevelInfo ul = (UserLevelInfo) request.getSession().getAttribute(Constants.SESSION_USER_LEVEL);
        if(ul==null){
            ul=userService.getUserLevelInfo(getUserId(request));
            if(ul != null )
                request.getSession().setAttribute(Constants.SESSION_USER_LEVEL,ul);
        }
        // 页面直接掉这个方法，好不好？
        return "user/right/vip-memberBtn";
    }

    @RequestMapping(value = "/experience")
    public String vip_try(){
        return TRYPAGE;
    }


    @RequestMapping(value = "/sendCode")
    @ResponseBody
    public ResultBase sendVertifyCode(HttpServletRequest request,@RequestParam("phone") String phone){

        boolean vali = StringValidateUtil.phoneValidate(phone);
        Map<String,String> map = new HashMap<>(2);
        if(!vali){
        	map.put("code",ERRORCODE);
        	map.put("msg","手机号码有误");        	
        }
        log.info("vip 试用 sendcode userId:{},phone:{} ",getSessionId(request),phone);
        String json = userService.fromSendVerifyCode(phone, phone, "您的验证码是：{$verifyCode$}，请及时输入验证。");
        ResultBase resultBase = JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
        if(vali && resultBase!=null && resultBase.isSuccess()){
            map.put("code",SUCCODE);
            map.put("msg","success");
        }else{
            map.put("code",ERRORCODE);
            if(resultBase!=null){
                map.put("msg",resultBase.getMsg());
            }else{
                map.put("msg","发送验证码接口错误");
            }
        }
        return resultBase;
    }

    @RequestMapping(value = "/infopro",method = RequestMethod.GET)
    public String infoImproment(String topicId,HttpServletRequest request, Model model){

        User user = getSessionUser(request);
        log.info("/infopro get user email:{}",user.getEmail());
        String birth = user.getBirthday();

        String year="";
        String month="";
        String day="";
        if(StringUtil.isNotEmpty(birth)){
            Date date = new Date(Long.parseLong(birth));
            year = DateTimeUtil.getDateString(date,"yyyy");
            month =  DateTimeUtil.getDateString(date ,"MM");
            day =  DateTimeUtil.getDateString(date ,"dd");
        }
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("day",day);
        model.addAttribute("canModifyEmail",user.canModifyEmail());
        model.addAttribute("topicId",topicId);
        return DOCPAGE;
    }

    @RequestMapping(value = "/infopro",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> infoImpromentPost(HttpServletRequest request, Model model,
                                                @RequestParam(value = "phone",required = false) String phone,
                                                @RequestParam(value = "code",required = false) String code,
                                                @RequestParam(value = "gender",required = false) String gender,
                                                @RequestParam(value = "email",required = false) String email,
                                                @RequestParam(value = "birthday",required = false) String birthday){ //yyyyMMdd
        User user = getSessionUser(request);
        Map<String, String> resultMap = validateForm(user, phone, code, gender, email, birthday);
        log.info("validate result: {},{}",resultMap.get("code"),resultMap.get("msg"));
        if(resultMap.get("code").equals(ERRORCODE)){
            return resultMap;
        }
        ModifyUserInfoReq req = new ModifyUserInfoReq();
        req.setUserId(user.getUserId());
        req.setEmail(StringUtils.trim(email));
        if(StringUtil.isNotEmpty(birthday)){
            req.setBirthday(String.valueOf(DateTimeUtil.getDate(StringUtils.trim(birthday), "yyyyMMdd").getTime()));
        }
        req.setGender(StringUtils.trim(gender));
        req.setMobile(StringUtils.trim(phone));
        log.info("modifyUserInfo :{}",req);
        ResultObjOne<User> userResult = userService.modifyUserInfo(req);
        if(userResult!= null ){
            if(userResult.isSuccess()){
                updateSessionUser(request,userResult.getObj());
                log.info("userTrial user userId:{}",user.getUserId());
                ResultBase resultBase = userService.setUserTrial(user.getUserId(),null);
                if(resultBase!=null){
                    if(resultBase.isSuccess()){
                        updateSessionUserLevel(request);
                        resultMap.put("code",SUCCODE);
                        resultMap.put("msg","success");
                    }else{
                        resultMap.put("code",ERRORCODE);
                        resultMap.put("msg",resultBase.getMsg());
                    }
                }else{
                    resultMap.put("code",ERRORCODE);
                    resultMap.put("msg","用户试用失败");
                }
            }else{
                resultMap.put("code",ERRORCODE);
                resultMap.put("msg",userResult.getMsg());
            }

        }else{
            resultMap.put("code",ERRORCODE);
            resultMap.put("msg","更新用户接口错误!");
        }
        return resultMap;
    }

    /**
     * 更新session中用户信息
     * @param request
     * @param newUser
     */
    private void updateSessionUser(HttpServletRequest request, User newUser){

        User sessionUser = getSessionUser(request);
        if(newUser==null){
            newUser = userService.findUserByUserId(sessionUser.getUserId());
        }
        sessionUser.setIsEmailVerified(newUser.getIsEmailVerified());
        sessionUser.setBindBirthday(newUser.getBindBirthday());
        sessionUser.setFirstDiscountEnd(newUser.getFirstDiscountEnd());
        sessionUser.setIsTrial(newUser.getIsTrial());
        sessionUser.setBindGiftPassword(newUser.getBindGiftPassword());
        sessionUser.setBindRelation(newUser.getBindRelation());
        sessionUser.setBirthday(newUser.getBirthday());
        sessionUser.setConsumedAmount(newUser.getConsumedAmount());
        sessionUser.setEmail(newUser.getEmail());
        sessionUser.setGender(newUser.getGender());
        sessionUser.setIcon(newUser.getIcon());
        sessionUser.setLevel(newUser.getLevel());
        sessionUser.setLv(newUser.getLv());
        sessionUser.setMobile(newUser.getMobile());
        sessionUser.setNickName(newUser.getNickName());
        sessionUser.setRegOrigin(newUser.getRegOrigin());
        sessionUser.setRegTime(newUser.getRegTime());
        sessionUser.setIsTrial30(newUser.getIsTrial30());
        sessionUser.setMemberType(newUser.getMemberType());
        request.getSession().setAttribute(Constants.SESSION_USER,sessionUser);
    }

    /**
     * 更新session中用户级别
     */
    private void updateSessionUserLevel(HttpServletRequest request){
        //删除掉session里面userLevel的东西，下次取再更新
        updateSessionUser(request,null);
        request.getSession().removeAttribute(Constants.SESSION_USER_LEVEL);
    }

    private Map<String,String> validateForm(User user,String phone,String code,String gender,String email,String birthday){

        log.info("validateForm phone:{},code:{},gender:{},email:{},birthday:{}",phone,code,gender,email,birthday);
        String errorMsg = "";
        boolean hasNotError  = true;
        if(StringUtil.isNotEmpty(StringUtils.trim(gender))){
            if(!StringUtils.trim(gender).equals("0") && !StringUtils.trim(gender).equals("1")){
                errorMsg = "性别错误!";
                hasNotError = false;
            }
        }
        if(hasNotError && StringUtil.isNotEmpty(StringUtils.trim(email))){

            if(!StringValidateUtil.emailValidate(StringUtils.trim(email))){
                errorMsg = "email格式错误!";
                hasNotError = false;
            }
        }
        //yyyyMMdd
        if(hasNotError && StringUtil.isNotEmpty(StringUtils.trim(birthday))){
            Date date = DateTimeUtil.getDate(StringUtils.trim(birthday), "yyyyMMdd");
            if(date ==null){
                errorMsg = "生日格式错误!";
                hasNotError = false;
            }else{
                if(date.after(new Date())){
                    errorMsg = "生日格式错误!";
                    hasNotError = false;
                }
            }
        }
        //校验手机
        if(hasNotError && StringUtil.isNotEmpty(StringUtils.trim(phone))){
            if(hasNotError &&!StringUtil.isNotEmpty(StringUtils.trim(code))){
                errorMsg = "请输入手机验证码!";
                hasNotError = false;
            }
            //验证码校验
            ResultBase result = userService.beVerifyPhoneCode(phone.trim(), phone.trim(), code.trim());
            if(hasNotError && result!=null && !result.isSuccess()){
                errorMsg = "验证码错误!";
                hasNotError = false;
            }
        }else{
            if(hasNotError && !StringUtil.isNotEmpty(StringUtils.trim(user.getMobile()))){
                errorMsg = "用户手机号错误!";
                hasNotError = false;
            }
        }
        Map<String,String> map = new HashMap<>();
        if(!hasNotError){
            map.put("code",ERRORCODE);
            map.put("msg",errorMsg);
        }else{
            map.put("code",SUCCODE);
            map.put("msg","success");
        }
        return map;
    }

}
