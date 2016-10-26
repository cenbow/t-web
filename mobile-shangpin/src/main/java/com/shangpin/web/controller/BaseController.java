package com.shangpin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.shangpin.biz.bo.User;
import com.shangpin.biz.bo.user.UserLevelInfo;
import com.shangpin.web.constant.Constants;

public class BaseController {
    
    /**
     * 获得当前session中的用户信息
     * 
     * @param request
     *            HttpServletRequest
     * @return
     */
    protected User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Constants.SESSION_USER);
    }
    
    /**
     * 获得当前session中的用户信息
     * 
     * @param request
     *            HttpServletRequest
     * @return
     */
    protected void addSessionUser(HttpServletRequest request,User u) {
    	HttpSession session = request.getSession();
    	session.setAttribute(Constants.SESSION_USER,u);
    }
    
    /**
     * 移除当前session中的用户信息
     * 
     * @param request
     *            HttpServletRequest
     * @return
     */
    protected void removeSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.SESSION_USER);
        session.removeAttribute(Constants.SESSION_USERID);
        session.removeAttribute(Constants.WX_ID_NAME);
        session.invalidate();
    }

    /**
     * 
     * 检查用户是否登录
     * @return boolean
     */
    protected boolean isUser(HttpServletRequest request) {
        User user = getSessionUser(request);
        return (user!=null && org.apache.commons.lang3.StringUtils.isNotBlank(user.getUserId()));
    }

    /**
     * 
     * @param request
     * @return
     */
    protected String getUserId(HttpServletRequest request) {
        if (isUser(request)) {
            return getSessionUser(request).getUserId();
        } else {
            return null;
        }
    }

    /**
     * 
     * @param request
     * @return
     */
    protected String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }
    /**
     * 获取用户等级数据,只有在用户中心会设置这个数据，其他页面不设置
     * <br/>
     * @param request
     * @return
     */
    protected UserLevelInfo getUserLevelInfo(HttpServletRequest request){
    	HttpSession session = request.getSession();
        return (UserLevelInfo) session.getAttribute(Constants.SESSION_USER_LEVEL);
    }
}
