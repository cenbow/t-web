package com.shangpin.web.controller.campaign;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.QuickUser;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.UserCheck;
import com.shangpin.biz.bo.base.ResultBase;
import com.shangpin.biz.bo.user.UserInvitedInfo;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringValidateUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.PropertyUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @auther 刘少卿
 * @date 2016-09-26
 * 尚品网用户分享链接活动
 */
@Slf4j
@Controller
@RequestMapping("share")
public class ShareUrlController extends BaseController{

    private static final String SUCCODE = "0";
    private static final String ERRORCODE = "1"; //错误
    private static final String ERRORCODE2 = "2";//已经是金牌会员
    private static final String ERRORCODE3 = "3";//已经体验过了
    private static final String ERRORCODE4 = "4";//邀请的名额满了,前端需要code

    private static final String FULLGETCODE = "1044"; //当邀请名额已满,接口返回code

    private static final String GENTERATEURL="campaign/shareUrl/sharedPage";

    private static final String SHAREDURL = "campaign/shareUrl/shareUrl";

    private static final String contextUrl = PropertyUtil.getString("shangpin_url");

    @Autowired
    private SPBizUserService userService;

    @RequestMapping("/me")
    public String shareUrl(HttpServletRequest req,Model model) throws Exception {
        User user = getSessionUser(req);
        log.info("share url me: {}", user.getUserId());
        String userId = user.getUserId();
        if(user.isShangUser()){
            UserInvitedInfo inviteCodeInfo = userService.getInviteCodeInfo(userId);
            model.addAttribute("canInviteCount",inviteCodeInfo.getInviteCount());
            model.addAttribute("invitedCount",inviteCodeInfo.getInvitedCount());
            model.addAttribute("url",contextUrl+"/share/d/"+inviteCodeInfo.getSerialNo()+"/");
            return SHAREDURL;
        }else{
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "d/{inviteCode}/{name}",produces = "text/html;charset=UTF-8")
    public String shareUrlOpen(@PathVariable("inviteCode") String inviteCode,@PathVariable("name")String name,Model model) throws UnsupportedEncodingException {
        //如果tomcat 没有配置URIEconding="UTF-8"
       // name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        model.addAttribute("inviteCode",inviteCode);
        model.addAttribute("name",name);
        log.info("name shared url inviteCode: {},name: {}",inviteCode,name);
        return GENTERATEURL;
    }

    @RequestMapping(value = "d/sendCode" ,method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult sendVertifyCode(@RequestParam("phone") String phone){

        //校验手机号
        boolean vali = StringValidateUtil.phoneValidate(phone);
        if(!vali){
            return getErrorMsg("手机号码有误");
        }
        // 判断用户是否存在
        UserCheck userCheck = userService.checkUserByPhoneOrEmail(phone);
        if(userCheck.isExist()) {
            if (!userCheck.canTrial30()) {
                if(UserCheck.CANTTRIAL30GOLDEN.equals(userCheck.getCanTrial30())){
                   return getErrorMsg("您是金牌会员，不能使用",ERRORCODE2);
                }else if(UserCheck.CANTTRIAL30USED.equals(userCheck.getCanTrial30())){
                   return getErrorMsg("您已经试用过了",ERRORCODE3);
                }
            }
        }
        String json = userService.fromSendVerifyCode(phone, phone, "您的验证码是：{$verifyCode$}，请及时输入验证。");
        ResultBase resultBase = JsonUtil.fromJson(json, new TypeToken<ResultBase>() {});
        if(resultBase!=null && resultBase.isSuccess()){
            return getSucMsg();
        }else{
            return getErrorMsg("发送验证码接口错误");
        }
    }

    @RequestMapping("d/submit")
    @ResponseBody
    public AjaxResult sharedUrl(@RequestParam(value = "inviteCode") String inviteCode,
                                 @RequestParam(value="phone") String phone,
                                 @RequestParam(value = "verifyCode") String code){

        log.info("share submit inviteCode: {},phone: {},verifyCode: {}",inviteCode,phone,code);
        //校验手机验证码
        AjaxResult ajaxResult = validatePhoneCode(phone, code);
        if(!ajaxResult.isSucsess()){
            return ajaxResult;
        }
        // 判断用户是否存在
        String userId="";
        UserCheck userCheck = userService.checkUserByPhoneOrEmail(phone);
        boolean isNewUser =false;
        if(userCheck.isExist()) {
            if (!userCheck.canTrial30()) {
                if(UserCheck.CANTTRIAL30GOLDEN.equals(userCheck.getCanTrial30())){
                    return getErrorMsg("您是金牌会员，不能使用",ERRORCODE2);
                }else if(UserCheck.CANTTRIAL30USED.equals(userCheck.getCanTrial30())){
                    return getErrorMsg("您已经试用过了",ERRORCODE3);
                }
            }
            userId = userCheck.getUserId();
        }else{
            QuickUser quickUser = userService.checkUser(phone, Constants.IS_NEW_USER, null);
            if(Constants.IS_NEW_USER.equals(quickUser.getIsNewUser())){
                isNewUser =true;
            }
            userId=quickUser.getUserId();
        }
        log.info("share trial userId: {},inviteCode:{}",userId,inviteCode);
        ResultBase resultBase = userService.setUserTrial(userId,inviteCode);
        if(!resultBase.isSuccess() && FULLGETCODE.equals(resultBase.getCode())){
            return getErrorMsg("机会已经被用完了",ERRORCODE4);
        }
        //发送用户注册新用户短信
        sendSms(phone,resultBase,isNewUser);
        return getFromResultBase(resultBase);
    }

    /**
     * 发送用户注册短信
     * @param phone
     * @param resultBase
     * @param isNewUser
     */
    private void sendSms(String phone,ResultBase resultBase,boolean isNewUser){

        String msgTemplateRe = "";
        if(resultBase!=null && resultBase.isSuccess()){
            if(isNewUser){
                msgTemplateRe = "你已成为尚品网 PRIME 金牌会员，手机号直接登录，密码为手机号后6位";
            }else{
                msgTemplateRe = "你已成为尚品网 PRIME 金牌会员，立即登录查看所有特权 t.cn/Rc7I2Dm";
            }
        }else{
            if(isNewUser){
                msgTemplateRe = "你已成为尚品网 会员，手机号直接登录，密码为手机号后6位";
            }
        }
        userService.fromSendVerifyCode(phone, phone, msgTemplateRe);
    }

    /**
     * 校验手机验证码
     * @param phone
     * @param code
     * @return
     */
    private AjaxResult validatePhoneCode(String phone,String code){

        //校验手机,验证码
        boolean vali = StringValidateUtil.phoneValidate(phone);
        if(!vali){
           return getErrorMsg("手机号码有误");
        }
        if(StringUtils.isBlank(code)||code.length()!=6){
            return getErrorMsg("验证码输入有误");
        }
        //校验短信
        ResultBase result = userService.beVerifyPhoneCode(phone, phone,code);
        log.info("校验短信mobile:{},msgCode:{},校验短信结果：{}",phone,code,result);
        if(result==null || !result.isSuccess()){
            return getErrorMsg("验证码输入有误");
        }
        return getSucMsg();
    }

    @Getter
    @Setter
    static class AjaxResult{

        private String code;
        private String msg;

        boolean isSucsess(){
            return code.equals(SUCCODE);
        }
    }

    private AjaxResult getErrorMsg(String msg){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(ERRORCODE);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    private AjaxResult getErrorMsg(String msg,String code){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(code);
        ajaxResult.setMsg(msg);
        return ajaxResult;
    }

    private AjaxResult getSucMsg(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setCode(SUCCODE);
        ajaxResult.setMsg("Sucess");
        return ajaxResult;
    }

    private AjaxResult getFromResultBase(ResultBase resultBase){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setMsg(resultBase.getMsg());
        if(resultBase.isSuccess()){
            ajaxResult.setCode(SUCCODE);
        }else{
            ajaxResult.setCode(ERRORCODE);
        }
        return ajaxResult;
    }

}
