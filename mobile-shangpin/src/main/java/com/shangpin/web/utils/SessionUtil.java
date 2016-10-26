package com.shangpin.web.utils;

import javax.servlet.http.HttpSession;

import com.shangpin.biz.bo.User;
import org.springframework.util.StringUtils;

import com.shangpin.biz.service.SPBizUserService;
import com.shangpin.utils.AESUtil;
import com.shangpin.web.constant.Constants;

public class SessionUtil {

    /**
     * 
     * 根据token自动登陆
     * 
     * @return boolean
     * @throws Exception
     */
    public static boolean loginFromApp(String token, String userId, HttpSession session, SPBizUserService userService) throws Exception {
        User user = null;
        String mtoken = AESUtil.decrypt(token, Constants.APP_TOKEN_KEY);
        if (StringUtils.hasText(mtoken)) {
            String[] arr = mtoken.split("[|]");
            if (arr.length == 2) {
                String userName = arr[0];
                String password = arr[1];
                user = userService.login(userName, password);
                if (addUserToSession(user, session)) {
                    return true;
                }
            }
        }
        if (StringUtils.isEmpty(userId) || "null".equals(userId)) {// 拿到APP登录的userId
            return false;
        }
        user = userService.findUserByUserId(userId);
        return addUserToSession(user, session);
    }

    /**
     * 
     * @param user
     * @param session
     * @return
     */
    public static boolean addUserToSession(User user, HttpSession session) {
        if (!StringUtils.isEmpty(user)) {
            session.setAttribute(Constants.SESSION_USER, user);
            session.setAttribute(Constants.SESSION_USERID, user.getUserId());
            return true;
        }
        return false;
    }

}
