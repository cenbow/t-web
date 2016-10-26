package com.shangpin.web.interceptor;

import com.shangpin.base.service.UserService;
import com.shangpin.biz.bo.User;
import com.shangpin.biz.utils.ClientUtil;
import com.shangpin.utils.JsonUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.utils.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 解决app用户过来，m站没有session的问题，适用于页面不要求强行登陆，但是需要知道是否用户登陆，如用户是否是vip。
 */
public class AppUserInterceptor extends HandlerInterceptorAdapter {

	public static final Logger logger = LoggerFactory.getLogger(AppUserInterceptor.class);

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String useragent = request.getHeader("User-Agent");
		String origin = request.getHeader(Constants.APP_COOKIE_NAME_ORIGIN);
		String userId = request.getHeader(Constants.APP_COOKIE_NAME_UID);
		//是app并且用户在app登陆
		if(StringUtils.isNotBlank(userId)&& (ClientUtil.CheckOrigin(origin) || ClientUtil.CheckApp(useragent))){
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constants.SESSION_USER);
			if(user==null||!userId.equals(user.getUserId())){
				String data = userService.getUserInfo(userId);
				try {
					JSONObject obj = JSONObject.fromObject(data);
					String content = obj.getString("content");
					if(StringUtils.isNotBlank(content)){
						user = JsonUtil.fromJson(content, User.class);
						if(user!=null){
							logger.info("app to m login success");
							UserUtil.loginUserToSession(user,session);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}
