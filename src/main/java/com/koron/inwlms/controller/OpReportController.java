package com.koron.inwlms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.inwlms.aspect.OperateAspect;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.report.DmaOperabilityReportDTO;
import com.koron.inwlms.bean.DTO.report.LoggerFlowQuestionReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerListReportDTO;
import com.koron.inwlms.bean.DTO.report.PFLoggerOperabilityReportDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.report.PFLoggerListReportVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.apparentLoss.ApparentLossService;
import com.koron.inwlms.service.report.OpReportService;
import com.koron.permission.authority.OPSPIMethod;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 通用controller层
 *
 * @author csh
 */
@Controller
@Api(value = "操作报表模块", description = "操作报表模块")
@RequestMapping(value = "/{tenantID}/opReportController")
@EnableAutoConfiguration
public class OpReportController {

	@Autowired
    private OpReportService oprs;
	
	/**
	 * 查询压力流量监测设备列表接口
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryPFLoggerListReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询压力流量监测设备列表接口", notes = "查询压力流量监测设备列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryPFLoggerListReport(@RequestBody PFLoggerListReportDTO pFLoggerListReportDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),oprs, "queryPFLoggerListReport", PageListVO.class,pFLoggerListReportDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	
	 @RequestMapping(value = "/downloadPFLoggerListReport.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	 @ApiOperation(value = "导出压力流量监测设备列表", notes = "导出压力流量监测设备列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @ResponseBody
	 public void downloadPFLoggerListReport(HttpServletResponse response, HttpServletRequest request) {
//		 try {
//	            response.reset();// 清空
//	            // 设置响应的文件的头文件格式
//	            response.setContentType("application/octet-stream");
//	            response.setHeader("Content-Disposition", "attachment;filename = file.xlsx");
//	            response.addHeader("Content-type", "application-download");
//	            String titleInfo = request.getParameter("titleInfo");  //table表头
//	            String objValue = request.getParameter("objValue");  //查询条件
//	            Gson jsonValue = new Gson();
//	            List<List<Map<String, Object>>> titleInfos = jsonValue.fromJson(titleInfo, new TypeToken<List<List<Map<String, Object>>>>() {
//	            }.getType());
//	            DmaOperabilityReportDTO dmaOperabilityReportDTO = jsonValue.fromJson(objValue, DmaOperabilityReportDTO.class);
//	            Map<String, Object> map = new HashMap<>();
//	            map.put("dto", dmaOperabilityReportDTO);
//	            map.put("titleInfos", titleInfos);
//	            XSSFWorkbook wb = ADOConnection.runTask(oprs, "downloadPFLoggerListReport",
//	                    XSSFWorkbook.class, map);
//	            //向流中写入文件
//	            ServletOutputStream out = response.getOutputStream();
//	            wb.write(out);
//	            //更新缓冲区
//	            out.flush();
//	            //关闭流
//	            out.close();
//	            wb.close();
//	        } catch (Exception e) {
//	        }
	    }   
 
	
	/**
	 * 查询噪声监测设备列表接口
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryNoiseLoggerListReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询噪声监测设备列表接口", notes = "查询噪声监测设备列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryNoiseLoggerListReport(@RequestBody PFLoggerListReportDTO pFLoggerListReportDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),oprs, "queryNoiseLoggerListReport", PageListVO.class,pFLoggerListReportDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadNoiseLoggerListReport.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	 @ApiOperation(value = "导出噪声监测设备列表", notes = "导出噪声监测设备列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @ResponseBody
	 public void downloadNoiseLoggerListReport(HttpServletResponse response, HttpServletRequest request) {
	 
	 }   
	
	/**
	 * 查询DMA可操作性报表接口
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryDmaOperabilityReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询DMA可操作性报表接口", notes = "查询DMA可操作性报表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDmaOperabilityReport(@RequestBody DmaOperabilityReportDTO dmaOperabilityReportDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),oprs, "queryDmaOperabilityReport", PageListVO.class,dmaOperabilityReportDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadDmaOperabilityReport.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	 @ApiOperation(value = "导出DMA可操作性报表", notes = "导出DMA可操作性报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @ResponseBody
	 public void downloadDmaOperabilityReport(HttpServletResponse response, HttpServletRequest request) {
	 
	 }   
	
	/**
	 * 查询压力流量监测设备可操作性报表接口
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryPFLoggerOperabilityReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询压力流量监测设备可操作性报表接口", notes = "查询压力流量监测设备可操作性报表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryPFLoggerOperabilityReport(@RequestBody PFLoggerOperabilityReportDTO pFLoggerOperabilityReportDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),oprs, "queryPFLoggerOperabilityReport", PageListVO.class,pFLoggerOperabilityReportDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadPFLoggerOperabilityReport.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	 @ApiOperation(value = "导出压力流量监测设备可操作性报表", notes = "导出压力流量监测设备可操作性报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @ResponseBody
	 public void downloadPFLoggerOperabilityReport(HttpServletResponse response, HttpServletRequest request) {
	 
	 }   
	
	/**
	 * 查询监测水量存疑报表接口
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryLoggerFlowQuestionReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询监测水量存疑报表接口", notes = "查询监测水量存疑报表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryLoggerFlowQuestionReport(@RequestBody LoggerFlowQuestionReportDTO loggerFlowQuestionReportDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),oprs, "queryLoggerFlowQuestionReport", PageListVO.class,loggerFlowQuestionReportDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadLoggerFlowQuestionReport.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	 @ApiOperation(value = "导出监测水量存疑报表", notes = "导出监测水量存疑报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @ResponseBody
	 public void downloadLoggerFlowQuestionReport(HttpServletResponse response, HttpServletRequest request) {
	 
	 }   
	
}
