package com.koron.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.cache.CacheKey;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * HttpSession工具，简单存取HttpRequest缓存
 * @author Gaoyuan
 * @date 2018年8月16日
 *
 */
public class SessionUtil {
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpSession getSession(){
		HttpServletRequest request = getRequest();
		if(null != request){
			return request.getSession();
		}
		return null;
	}
	
	public static Object getAttribute(String key){
		HttpSession session = getSession();
		if(null != session){
			return session.getAttribute(key);
		}
		return null;
	}
	
	public static void setAttribute(String key, Object value){
		HttpSession session = getSession();
		if(null != session){
			session.setAttribute(key, value);
		}
	}
	
	public static void removeAttribute(String key){
		HttpSession session = getSession();
		if(null != session){
			session.removeAttribute(key);
		}
	}
	
	
//	public static UserListVO getLoginUser(){
//		if(null != getSession().getAttribute(Constant.LOGIN_USER)){
//			return (UserListVO)getSession().getAttribute(Constant.LOGIN_USER);
//		}
//		
//		return null;
//	}
	
}
