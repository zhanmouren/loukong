package com.koron.inwlms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.service.common.CommonLoginService;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.inwlms.util.ResponseUtil;
import com.koron.inwlms.util.SysUtil;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * 登录控制层
 * @createTime 2020/04/07
 * @author lzy
 */
@RestController
@Api(value = "系统管理模块", description = "系统管理模块")
@RequestMapping(value = "/systemController")
public class SystemController {

	@Autowired
	private CommonLoginService commonLoginService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 用户登录方法
	 *
	 * @param userLoginDTO
	 *            用户登录实体DTO
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String login(HttpServletRequest request, @RequestBody UserLoginDTO userLoginDTO) {
		
		if(userLoginDTO.getLoginName() == null || StringUtils.isBlank(userLoginDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录账号不能为空", Integer.class).toJson();
		}
		if(userLoginDTO.getPassword() == null || StringUtils.isBlank(userLoginDTO.getPassword())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录密码不能为空", Integer.class).toJson();
		}
		// 根据用户账号，密码，查询用户信息表，都正确返回登录成功，否则登录失败
		MessageBean<UserListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UserListVO.class);	       
		try {
			UserListVO userListVO = ADOConnection.runTask(commonLoginService, "login", UserListVO.class, userLoginDTO);
			if (userListVO != null) {
				
				LoginLogDTO loginLogDTO = new LoginLogDTO();
				String ip = SysUtil.getIpAddr(request);
				loginLogDTO.setLoginIp(ip);
				loginLogDTO.setLoginUserCode(userListVO.getCode());
				loginLogDTO.setCreateBy(userListVO.getLoginName());
				loginLogDTO.setUpdateBy(userListVO.getLoginName());
				loginLogDTO.setType("登入");
				if(userListVO.getPassword().equals(userLoginDTO.getPassword())) {
					msg.setData(userListVO);
					loginLogDTO.setResult("登录成功");
					loginLogDTO.setErrorLog("————");
					// 用户权限信息入缓存
					SessionUtil.setAttribute(Constant.LOGIN_USER, userListVO);
					ADOConnection.runTask(logService, "addLoginLog", Integer.class, loginLogDTO);
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("登录成功");
					msg.setStatus(1);
				}else {
					loginLogDTO.setResult(Constant.MESSAGE_STRING_LOGINERROR);
					loginLogDTO.setErrorLog("密码错误");
					ADOConnection.runTask(logService, "addLoginLog", Integer.class, loginLogDTO);
					msg.setCode(Constant.MESSAGE_INT_LOGINERROR);
					msg.setDescription("密码错误");
					msg.setStatus(0);
				}
				
			} else {
				msg.setCode(Constant.MESSAGE_INT_LOGINERROR);
				msg.setDescription("无该用户");
				msg.setStatus(0);
			}
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_LOGINERROR);
			msg.setDescription(Constant.MESSAGE_STRING_LOGINERROR);
			msg.setStatus(0);
		}
		return msg.toJson();
	}
	
	
	
	/**
	 * 用户退出方法
	 *
	 */
	@ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	@RequestMapping(value = "/logout.htm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public String logout(HttpServletRequest request) {
		//清空用户缓存信息
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS,String.class);
		try {
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
			LoginLogDTO loginLogDTO = new LoginLogDTO();
			String ip = SysUtil.getIpAddr(request);
			loginLogDTO.setLoginIp(ip);
			loginLogDTO.setLoginUserCode(userListVO.getCode());
			loginLogDTO.setCreateBy(userListVO.getLoginName());
			loginLogDTO.setUpdateBy(userListVO.getLoginName());
			loginLogDTO.setResult(Constant.MESSAGE_STRING_SUCCESS);
			loginLogDTO.setType("登出");
			loginLogDTO.setErrorLog("————");
			ADOConnection.runTask(logService, "addLoginLog", Integer.class, loginLogDTO);
			SessionUtil.removeAttribute(Constant.LOGIN_USER);
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_NOLOGIN);
			msg.setDescription("退出失败");
		}
		return ResponseUtil.toJson(msg);
	}
	
	
}
