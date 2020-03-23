package com.koron.common.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.koron.inwlms.bean.VO.sysManager.UserVO;

public class PersonResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		StaffAttribute attr = parameter.getParameterAnnotation(StaffAttribute.class);
		return parameter.getParameterType().equals(UserVO.class) && attr != null;
	}

	@Override	
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		StaffAttribute attr = parameter.getParameterAnnotation(StaffAttribute.class);
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Object o = request.getSession().getAttribute(attr.value());
		if(o == null) {//如果session中没有值,通过yzjticket可以进行云之家账号获取
			String ticket = request.getParameter("yzjticket");
			if(ticket == null)
			{
				Cookie[] cookies = request.getCookies();
				if(cookies == null)
					return null;
				for (Cookie cookie : cookies) {
					if(cookie.getName().equals("yzjticket")) {
						ticket = cookie.getValue();
						break;
					}
				}
			}
			if(ticket == null || ticket.isEmpty())
				return null;
			return YzjSSO.getUser(ticket);
		}
		return request.getSession().getAttribute(attr.value());
	}
}