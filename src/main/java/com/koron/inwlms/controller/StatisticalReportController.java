package com.koron.inwlms.controller;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.report.ZoneMnfDTO;
import com.koron.inwlms.bean.VO.report.statisticalReport.FlowMeterAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.MeterAbnormalAnalysisVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.NetFaultVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfStatisticalVO;
import com.koron.inwlms.bean.VO.report.statisticalReport.ZoneMnfVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.report.AnalysisReportService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "StatisticalReportController", description = "统计报表Controller")
@RequestMapping(value = "/{tenantID}/StatisticalReportController")
public class StatisticalReportController {
	
	@Autowired
	private AnalysisReportService ars;
	
	/**
	 * 分区最小夜间流量报表
	 */
	@RequestMapping(value = "/queryZoneMnf.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "分区最小夜间流量报表", notes = "分区最小夜间流量报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMnf(@RequestBody ZoneMnfDTO zoneMnfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<ZoneMnfVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ZoneMnfVO.class);
		
		if(zoneMnfDTO.getStartTime() == null || zoneMnfDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		
		try {
			ZoneMnfVO zoneMnfVO = ADOConnection.runTask(user.getEnv(),ars, "queryZoneMnf", ZoneMnfVO.class, zoneMnfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(zoneMnfVO);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询报表失败");
		}
		return msg.toJson();
	}
	
	/**
	 * 分区最小夜间流量统计报表
	 * @return
	 */
	@RequestMapping(value = "/queryZoneMnfStatistical.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "分区最小夜间流量统计报表", notes = "分区最小夜间流量统计报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryZoneMnfStatistical(@RequestBody ZoneMnfDTO zoneMnfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<ZoneMnfStatisticalVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ZoneMnfStatisticalVO.class);
		
		if(zoneMnfDTO.getStartTime() == null || zoneMnfDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		if(zoneMnfDTO.getEndTime() == null || zoneMnfDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!结束时间为空");
	        return msg.toJson();
		}
		
		try {
			
			ZoneMnfStatisticalVO zoneMnfVO = ADOConnection.runTask(user.getEnv(),ars, "queryZoneMnfStatistical", ZoneMnfStatisticalVO.class, zoneMnfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(zoneMnfVO);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询报表失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryFlowMeterAnalysis.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "用户水表分析对比报表", notes = "用户水表分析对比报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryFlowMeterAnalysis(@RequestBody ZoneMnfDTO zoneMnfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<FlowMeterAnalysisVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, FlowMeterAnalysisVO.class);
		
		if(zoneMnfDTO.getStartTime() == null || zoneMnfDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		
		try {
			
			FlowMeterAnalysisVO zoneMnfVO = ADOConnection.runTask(user.getEnv(),ars, "queryFlowMeterAnalysis", FlowMeterAnalysisVO.class, zoneMnfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(zoneMnfVO);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询报表失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryMeterAbnormalAnalysis.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "用户水表异常分析对比报表", notes = "用户水表异常分析对比报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMeterAbnormalAnalysis(@RequestBody ZoneMnfDTO zoneMnfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<MeterAbnormalAnalysisVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, MeterAbnormalAnalysisVO.class);
		if(zoneMnfDTO.getStartTime() == null || zoneMnfDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		if(zoneMnfDTO.getEndTime() == null || zoneMnfDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!结束时间为空");
	        return msg.toJson();
		}
		
		try {
			MeterAbnormalAnalysisVO result = ADOConnection.runTask(user.getEnv(),ars, "queryMeterAbnormalAnalysis", MeterAbnormalAnalysisVO.class, zoneMnfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(result);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询报表失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryNteFault.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "分区管网故障报表", notes = "分区管网故障报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryNteFault(@RequestBody ZoneMnfDTO zoneMnfDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<NetFaultVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, NetFaultVO.class);
		if(zoneMnfDTO.getStartTime() == null || zoneMnfDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!开始时间为空");
	        return msg.toJson();
		}
		if(zoneMnfDTO.getEndTime() == null || zoneMnfDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("参数错误!结束时间为空");
	        return msg.toJson();
		}
		
		try {
			NetFaultVO result = ADOConnection.runTask(user.getEnv(),ars, "queryNteFault", NetFaultVO.class, zoneMnfDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(result);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询报表失败");
		}
		return msg.toJson();
	}
	
	

}
