package com.koron.common.web.servlet;

import org.swan.bean.MessageBean;

import javax.servlet.http.HttpServletRequest;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.koron.common.bean.StaffBean;
import com.koron.common.web.mapper.StaffMapper;
import com.koron.ebs.permission.ResourceLoader;
import com.koron.ebs.permission.StaffAccount;
import com.koron.ebs.permission.mybatis.SPILoader;
import com.koron.util.Constant;
import com.koron.util.Tools;

@Controller
public class LoginAction {
	@RequestMapping("/login.htm")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView mv = Tools.getView("login.btl");
		return mv;
	}

	@RequestMapping("/login/login.htm")
	@ResponseBody
	public String login(@RequestParam("name") String name, @RequestParam("password") String password,
			HttpServletRequest request) {
		try (SessionFactory factory = new SessionFactory()) {
			return new Gson().toJson(factory.runTask(this, "login", MessageBean.class, name, password, request));
		}
	}

	/**
	 * 注销登录
	 * 
	 * @return
	 */
	@RequestMapping("/logout.htm")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute(Constant.USER);
		ModelAndView mv = Tools.getView("login.btl");
		return mv;
	}

	/**
	 * 账号登陆查询
	 */
	@TaskAnnotation("login")
	private MessageBean<Integer> login(SessionFactory factory, String name, String password,
			HttpServletRequest request) {
		MessageBean<Integer> ret = new MessageBean<>();
		StaffBean bean = factory.getMapper(StaffMapper.class).login(name, Tools.MD5(password));
		if (bean == null) {
			ret.setCode(Constant.MESSAGE_INT_PWDERROR);
			ret.setDescription("账号或密码不正确");
		} else {
			ret.setCode(Constant.MESSAGE_INT_SUCCESS);
			ret.setDescription("登录成功");
			StaffAccount staff = new SPILoader().get(bean.getLoginid(), StaffAccount.class, ResourceLoader.ENTITY_ACCOUNT_INT);
			staff.setStaff(bean);
			request.getSession().setAttribute(Constant.USER, staff);
		}
		return ret;
	}
}