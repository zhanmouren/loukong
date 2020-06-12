package com.koron.inwlms.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.sysManager.DBInfoDTO;
import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.common.CommonLoginService;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.inwlms.util.RSAUtil;
import com.koron.inwlms.util.ResponseUtil;
import com.koron.inwlms.util.SysUtil;
import com.koron.permission.bean.VO.UserListVO;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.koron.ebs.mybatis.ADOSessionImpl;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.swan.bean.MessageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 登录控制层
 * @createTime 2020/04/07
 * @author lzy
 */
@RestController
@Api(value = "系统管理模块", description = "系统管理模块")
//@RequestMapping(value = "/systemController")
@RequestMapping(value = "/{tenantID}/systemController")
public class SystemController {

	@Autowired
	private CommonLoginService commonLoginService;
	
	@Autowired
	private LogService logService;

	@Value("${cloud.management.platform.url}")
	private String cloudManagePlat;

	@Value("${cloud.management.platform.privateKey}")
	private String privateKey;

	@Value("${postgresql.driver.name}")
	private String postgresqlDriver;

	private final Map<String,String> envMap=new HashMap<>();
	
	/**
	 * 用户登录方法
	 *
	 * @param userLoginDTO
	 *            用户登录实体DTO
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@RequestMapping(value = "/login.htm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String login(HttpServletRequest request, @RequestBody UserLoginDTO userLoginDTO,@PathVariable("tenantID") String tenantID) {
	//public String login(HttpServletRequest request, @RequestBody UserLoginDTO userLoginDTO) {
		if(userLoginDTO.getLoginName() == null || StringUtils.isBlank(userLoginDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录账号不能为空", Integer.class).toJson();
		}
		if(userLoginDTO.getPassword() == null || StringUtils.isBlank(userLoginDTO.getPassword())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录密码不能为空", Integer.class).toJson();
		}
		// 根据用户账号，密码，查询用户信息表，都正确返回登录成功，否则登录失败
		MessageBean<UserVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UserVO.class);
		try {
			//登录成功，数据源注册操作
			String env = tenantID+ EnvSource.DEFAULT;
			//String env = "";

			if(envMap.get(tenantID)==null || "".equals(envMap.get(tenantID))) {
				//调用云管平台接口获取租户数据
				String token = getTenantToken(Constant.APPID, tenantID);
				if (token != null) {
					DBInfoDTO dbd = getDBInfo(token);
					if (dbd != null) {
						Properties prop = new Properties();
						prop.put(SessionFactory.PROPERTY_DRIVER, postgresqlDriver);
						prop.put(SessionFactory.PROPERTY_URL, dbd.getUrl());
						prop.put(SessionFactory.PROPERTY_USER, dbd.getUser());
						prop.put(SessionFactory.PROPERTY_PASSWORD, dbd.getPassword());
						new ADOSessionImpl().registeDBMap(env, prop);
						//设置到envMap里面
						envMap.put(tenantID, tenantID);
					} else {
						msg.setCode(Constant.MESSAGE_INT_LOGINERROR);
						msg.setDescription("数据源初始化失败");
						msg.setStatus(0);
						return msg.toJson();
					}
				} else {
					msg.setCode(Constant.MESSAGE_INT_LOGINERROR);
					msg.setDescription("数据源初始化失败");
					msg.setStatus(0);
					return msg.toJson();
				}
			}

			UserVO userVO = ADOConnection.runTask(env,commonLoginService, "login", UserVO.class, userLoginDTO);
			if (userVO != null) {
				
				LoginLogDTO loginLogDTO = new LoginLogDTO();
				String ip = SysUtil.getIpAddr(request);
				loginLogDTO.setLoginIp(ip);
				loginLogDTO.setLoginUserCode(userVO.getCode());
				loginLogDTO.setCreateBy(userVO.getLoginName());
				loginLogDTO.setUpdateBy(userVO.getLoginName());
				loginLogDTO.setType("L102110002");
				if(userVO.getPassword().equals(userLoginDTO.getPassword())) {

					userVO.setEnv(env);

					msg.setData(userVO);
					loginLogDTO.setResult("登录成功");
					loginLogDTO.setErrorLog("————");
					// 用户权限信息入缓存
					SessionUtil.setAttribute(Constant.LOGIN_USER, userVO);
					ADOConnection.runTask(env,logService, "addLoginLog", Integer.class, loginLogDTO);
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("登录成功");
					msg.setStatus(1);
				}else {
					loginLogDTO.setResult(Constant.MESSAGE_STRING_LOGINERROR);
					loginLogDTO.setErrorLog("密码错误");
					ADOConnection.runTask(env,logService, "addLoginLog", Integer.class, loginLogDTO);
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
			loginLogDTO.setResult("登出成功");
			loginLogDTO.setType("L102110003");
			loginLogDTO.setErrorLog("————");
			ADOConnection.runTask(logService, "addLoginLog", Integer.class, loginLogDTO);
			SessionUtil.removeAttribute(Constant.LOGIN_USER);
		} catch (Exception e) {
			msg.setCode(Constant.MESSAGE_INT_NOLOGIN);
			msg.setDescription("未登录");
		}
		return ResponseUtil.toJson(msg);
	}

	private String getTenantToken(String APPID,String tenantID){
		String path = this.cloudManagePlat+"/port/tenant/token.htm?appCode="+APPID+"&tenantCode="+tenantID+"&version=1.0.0";
		JsonObject ret =  InterfaceUtil.interfaceUtil(path);
		Gson gson = new Gson();
		MessageBean msg = gson.fromJson(ret, new TypeToken<MessageBean>(){}.getType());
		if(msg.getCode()!=0){
			return null;
		}
		try {
			Map data = (Map) msg.getData();
			return (String)data.get("token");
		}catch(Exception e){
			return null;
		}
	}

	private DBInfoDTO getDBInfo(String token){
		String path = this.cloudManagePlat+"/port/tenant/dbinfo.htm?token="+token;
		JsonObject ret =  InterfaceUtil.interfaceUtil(path);
		Gson gson = new Gson();
		MessageBean msg = gson.fromJson(ret, new TypeToken<MessageBean>(){}.getType());
		if(msg.getCode()!=0){
			return null;
		}
		try {
			//List<DBInfoDTO> data = gson.fromJson(msg.getData(), new TypeToken<List<DBInfoDTO>>(){}.getType());
			List<Map> data =(List<Map>)msg.getData();
			String  dbInfo = (String)data.get(0).get("dbinfo");
			String source = RSAUtil.decrypt(dbInfo, privateKey);
			DBInfoDTO dbd = gson.fromJson(source, new TypeToken<DBInfoDTO>(){}.getType());
			dbd.setMaxConnection(((Double)data.get(0).get("maxConnection")).intValue());

			return dbd;
		}catch(Exception e){
			return null;
		}
	}
	
}
