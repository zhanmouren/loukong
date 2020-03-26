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

import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.service.impl.UserServiceImpl;
import com.koron.inwlms.service.leakageControl.AlarmMessageService;
import com.koron.inwlms.service.leakageControl.AlarmProcessService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 漏损控制Controller层
 * @author 刘刚
 * 
 */

@Controller
@Api(value = "leakageControlController", description = "漏损控制Controller")
@RequestMapping(value = "/leakageControlController")
public class leakageControlController {
	
	@Autowired
	private AlarmMessageService ams;
	@Autowired
	private AlarmProcessService aps;

	@RequestMapping(value = "/queryAlarmMessage.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息接口", notes = "查询预警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmMessage(@RequestBody WarningInfDTO warningInfDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(warningInfDTO.getFirstPartion().equals("全部")) {
			warningInfDTO.setAreaCode(null); 
		}
		//通过分区编码查询出所属的所有0级分区编码，将参数分区编码设置为0级分区编码
		
		try {
			List<AlarmMessageVO> alarmMessageList = ADOConnection.runTask(ams, "queryAlarmMessage", List.class, warningInfDTO);
			if(alarmMessageList != null && alarmMessageList.size() != 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setData(alarmMessageList);
			}
		}catch(Exception e){
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_Failed);
	        msg.setDescription("查询预警信息失败");
		}
			
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询预警信息处理任务接口", notes = "查询预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryAlarmProcess(@RequestBody AlarmProcessDTO alarmProcessDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		//查询参数设置调整
		System.out.print(0);
		try {
			List<AlarmProcessVO> alarmProcessList = ADOConnection.runTask(aps,"queryAlarmProcess",List.class,alarmProcessDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(alarmProcessList);
		}catch(Exception e) {
			//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_Failed);
	        msg.setDescription("查询预警信息处理任务失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updateAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改预警信息处理任务接口", notes = "修改预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateAlarmProcess(@RequestBody AlarmProcessDTO alarmProcessDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			Integer num = ADOConnection.runTask(aps,"updateAlarmProcess",Integer.class,alarmProcessDTO);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				
			}
			
		}catch(Exception e) {
			//修改失败
	     	msg.setCode(Constant.MESSAGE_INT_Failed);
	        msg.setDescription("修改预警信息处理任务失败");
		}
		
		return null;
		
	}
	
	@RequestMapping(value = "/addAlarmProcess.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加预警信息处理任务接口", notes = "添加预警信息处理任务接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addAlarmProcess(@RequestBody AlarmProcessDTO alarmProcessDTO) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		//自动产生任务编码
		
		//添加预警信息处理任务
		try {
			Integer num = ADOConnection.runTask(aps, "addAlarmProcess",Integer.class,alarmProcessDTO);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}else {
				
			}
		}catch(Exception e) {
			//添加失败
	     	msg.setCode(Constant.MESSAGE_INT_Failed);
	        msg.setDescription("添加预警信息处理任务失败");
		}
		
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/deleteAlarmProcess.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除预警信息处理任务接口", notes = "删除预警信息处理任务接口", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteAlarmProcess(@RequestBody Integer id) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		try {
			Integer num = ADOConnection.runTask(aps, "deleteAlarmProcess",Integer.class,id);
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			}
			
		}catch(Exception e) {
			
		}
		return msg.toJson();
		
	}
	
	
}
