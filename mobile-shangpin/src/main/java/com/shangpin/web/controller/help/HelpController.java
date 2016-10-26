package com.shangpin.web.controller.help;

import com.shangpin.biz.bo.User;
import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户帮助的controller
 * 
 */
@Controller
@RequestMapping("/help")
public class HelpController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HelpController.class);

    private static final String HELP_CENTER = "user/help/index";

    @Autowired
    private SPBizUserService userService;

    @RequestMapping("/index")
    public String toHelpCenter(HttpServletRequest request,ModelMap model){
        User sessionUser = getSessionUser(request);
        boolean isApp = UserUtil.isApp(request);
        if(sessionUser==null){
            String loginUrl;
            if(isApp){
                String userid = request.getHeader("userid");
                if(StringUtils.isNotBlank(userid)){
                    User userByUserId = userService.findUserByUserId(userid);
                    model.addAttribute("user",userByUserId);
                    loginUrl=null;
                }else{
                    loginUrl = Constants.APP_NOT_LOGIN_URL+"?"+Constants.APP_NAME_CALLBACK_URL+"="+Constants.SHANGPIN_URL+"/help/index";
                }
            }else{
                loginUrl =request.getContextPath()+"/login?back=/help/index";
            }
            model.addAttribute("loginUrl",loginUrl);
            model.addAttribute("isApp",isApp?"1":"0");
        }else{
            model.addAttribute("user",sessionUser);
        }
        return HELP_CENTER;
    }
}