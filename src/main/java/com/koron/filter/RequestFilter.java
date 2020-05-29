package com.koron.filter;

import com.google.gson.Gson;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.swan.bean.MessageBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求过滤器
 * @author lzy
 * @date 2020.04.03
 */

@Component
@WebFilter(urlPatterns = "/*", filterName = "requestFilter")
public class RequestFilter implements Filter {

	//TODO 注解
	String environment="111";
	
	/**
	 * 开发环境
	 */
	private static final String DEV = "dev";

	/**
	 * 生产环境
	 */
	private static final String PROD = "prod";

	private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	/**
	 * 不需要过滤的url
	 */
	private static final String[] urls = {"login",".json",".js",".css",".ico",".jpg",".png",".woff",".ttf",".gif"};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("请求过滤器初始化");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.info("开始进行过滤处理");
		HttpServletRequest hsRequest = (HttpServletRequest)request;
		HttpServletResponse hsResponse = (HttpServletResponse)response;
		// 获取请求url
		String servletPath = hsRequest.getServletPath();
		//获取用户的session缓存
		Object attribute = hsRequest.getSession().getAttribute(Constant.LOGIN_USER);
		// 如果是生产环境
		//TODO  prod需要更改
		if (PROD.equals("prod")) {
			boolean flag = true;
			for (String str : urls) {
				if (servletPath.indexOf(str) != -1) {
					flag =false;
					break;
				}
			}
			if (flag) {
				if (attribute != null) {
					chain.doFilter(request, response);
				} else {
					if (servletPath.endsWith(".html")) {
						// 如果是页面,则重定向到登录界面
						hsResponse.sendRedirect(hsRequest.getContextPath() + "/dist/views/login/index.html");
					} else {
						// 如果是controller下的api,返回未登录信息,让前端处理
						hsResponse.setContentType("application/json; charset=utf-8");
						hsResponse.setCharacterEncoding("utf-8");
						MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_NOLOGIN, "未登录", String.class);
						Gson gson = new Gson();
						hsResponse.getWriter().write(gson.toJson(msg));
					}
					return;
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (attribute== null) {
				UserListVO userListVO = new UserListVO();
				userListVO.setLoginName("admin");
				userListVO.setName("管理员账号");
				userListVO.setCode("92070DF42CFF279FE05305010D0A1637");
				SessionUtil.setAttribute(Constant.LOGIN_USER, userListVO);
			}
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		logger.info("Filter销毁中");
	}
}
