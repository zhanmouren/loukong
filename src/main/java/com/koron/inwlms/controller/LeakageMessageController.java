package com.koron.inwlms.controller;

import java.util.List;

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
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.LeakageMessageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
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
@RequestMapping(value = "/{tenantID}/leackageMessageController")
public class LeakageMessageController {
	
	@Autowired
	private LeakageMessageService leakageMessageService;
	
	@RequestMapping(value = "/queryMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询预警信息接口", notes = "查询预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMessage(String loginName,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<LeakageMessageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LeakageMessageListVO.class);
		try {
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
			loginName = userListVO.getLoginName();
			LeakageMessageListVO leakageMessageList = ADOConnection.runTask(user.getEnv(),leakageMessageService, "queryMessage", LeakageMessageListVO.class, loginName);
			if(leakageMessageList != null ) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			    msg.setDescription("查询到预警信息"); 
			    msg.setData(leakageMessageList);
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
	
	@RequestMapping(value = "/updateAlarmMessageStatus.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "修改预警信息读取状态接口", notes = "修改预警信息读取状态接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateAlarmMessageStatus(@RequestBody WarningInfDTO warningInfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<LeakageMessageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LeakageMessageListVO.class);
		try {
			Integer result = ADOConnection.runTask(user.getEnv(),leakageMessageService, "updateAlarmMessageStatus", Integer.class, warningInfDTO);
			if(result != null ) {
				if(result != 0) {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("成功修改预警信息读取状态"); 
				
				}else {
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("无该预警信息"); 
				}
			}
		}catch (Exception e) {
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("修改预警信息读取状态失败");
		}
		return msg.toJson();
	}
}
