package com.taotao.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {

	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;
	@Autowired
	private UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
	
		TbUser user = userService.getUserByToken(request, response);
		if(user == null){
			response.sendRedirect(SSO_LOGIN_URL + "?redirectURL=" + request.getRequestURL());
			return false;
		}
		
		request.setAttribute("user", user);
		return true;
	}

}
