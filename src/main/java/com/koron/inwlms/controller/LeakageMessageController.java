package com.koron.inwlms.controller;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.service.leakageControl.LeakageMessageService;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 漏损消息中心Controller层
 * @author lzy
 * @Date 2020/05/09
 */

@Controller
@Api(value = "leackageMessageController",description = "漏损消息中心Controller")
@RequestMapping(value = "/leackageMessageController")
public class LeakageMessageController {
	
	@Autowired
	private LeakageMessageService leakageMessageService;
	
	@RequestMapping(value = "/queryAlarmMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询预警信息接口", notes = "查询预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessage(String loginName) {
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try {
//			Gson jsonValue = new Gson();
//			// 查询条件字符串转对象，查询数据结果
//			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//			loginName = userListVO.getLoginName();
			PageListVO alarmMessageList = ADOConnection.runTask(leakageMessageService, "queryAlarmMessage", PageListVO.class, loginName);
			if(alarmMessageList != null && alarmMessageList.getRowNumber() >0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			    msg.setDescription("查询到预警信息"); 
			    msg.setData(alarmMessageList);
			}
			else {
				//没查询到数据
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			    msg.setDescription("无该预警信息"); 
			}
		}catch (Exception e) {
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("预警信息查询失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryLeakageProcessing.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询漏损待办/已办接口",notes = "查询漏损待办/已办接口",httpMethod = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryLeakageProcessing(String loginName) {
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try {
//			Gson jsonValue = new Gson();
//			// 查询条件字符串转对象，查询数据结果
//			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//			loginName = userListVO.getLoginName();
			PageListVO leakageProcessingList = ADOConnection.runTask(leakageMessageService, "queryLeakageProcessing", PageListVO.class, loginName);
			if(leakageProcessingList != null && leakageProcessingList.getRowNumber() >0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("查询到漏损待办/已办");
				msg.setData(leakageProcessingList);
			}
			else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无漏损待办/已办");
			}
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询漏损待办/已办失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryMonitorProcessing.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询监测待办/已办接口",notes = "查询监测待办/已办接口",httpMethod = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryMonitorProcessing(String loginName) {
		MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try {
//			Gson jsonValue = new Gson();
//			// 查询条件字符串转对象，查询数据结果
//			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//			loginName = userListVO.getLoginName();
			PageListVO leakageProcessingList = ADOConnection.runTask(leakageMessageService, "queryMonitorProcessing", PageListVO.class, loginName);
			if(leakageProcessingList != null && leakageProcessingList.getRowNumber() >0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("查询到监测待办/已办");
				msg.setData(leakageProcessingList);
			}
			else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无监测待办/已办");
			}
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询监测待办/已办失败");
		}
		return msg.toJson();
	}
	
}
