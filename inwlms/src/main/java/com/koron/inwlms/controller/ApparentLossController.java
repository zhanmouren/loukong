package com.koron.inwlms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALMapDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.PageALListVO;
import com.koron.inwlms.service.apparentLoss.ApparentLossService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 表观漏损Controller层
 * @author csh
 * @Date 2020.03.09
 */
@Controller
@Api(value = "apparentLossController", description = "表观漏损Controller")
@RequestMapping(value = "/apparentLossController")
public class ApparentLossController {

	@Autowired
    private ApparentLossService als;
	
	/**
	 * 查询表观漏损数据总览接口
	 * @param queryALDTO 表观漏损数据总览参数
	 * @return String 字符串返回值
	 */
	@RequestMapping(value = "/queryALOverviewData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损数据总览接口", notes = "查询表观漏损数据总览接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALOverviewData(@RequestBody QueryALDTO queryALDTO) {
		MessageBean<ALOverviewDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ALOverviewDataVO.class);
		Integer timeType = queryALDTO.getTimeType();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		if(timeType == null || startTime == null || endTime == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);
		}
		
		if(startTime > endTime) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription(Constant.MESSAGE_STRING_PARAMS);
   	 	}
		try{
			ALOverviewDataVO data = ADOConnection.runTask(als, "queryALOverviewData", ALOverviewDataVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryALList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损数据列表", notes = "查询表观漏损数据列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALList(@RequestBody QueryALListDTO queryALListDTO) {
		MessageBean<PageALListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageALListVO.class);
		Integer timeType = queryALListDTO.getTimeType();
		Integer startTime = queryALListDTO.getStartTime();
		Integer endTime = queryALListDTO.getEndTime();
		if(timeType == null || startTime == null || endTime == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);
		}
		try{
			PageALListVO data = ADOConnection.runTask(als, "queryALList", PageALListVO.class,queryALListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryALMapData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询表观漏损图表数据", notes = "查询表观漏损图表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryALMapData(@RequestBody QueryALDTO queryALDTO) {
		MessageBean<ALMapDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ALMapDataVO.class);
		Integer timeType = queryALDTO.getTimeType();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		if(timeType == null || startTime == null || endTime == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);
		}
		
		if(startTime > endTime) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription(Constant.MESSAGE_STRING_PARAMS);
   	 	}
		try{
			ALMapDataVO data = ADOConnection.runTask(als, "queryALMapData", ALMapDataVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadALList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载表观漏损列表数据", notes = "下载表观漏损列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadALList(HttpServletResponse response, HttpServletRequest request) {
		try{
			String objValue = request.getParameter("objValue"); // 获取导出数据查询条件bean
			String titleInfos = request.getParameter("titleInfos"); // 获取导出列表数据表头
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryALListDTO queryALListDTO = jsonValue.fromJson(objValue, QueryALListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryALListDTO == null) {
				queryALListDTO = new QueryALListDTO();
			}
			queryALListDTO.setPage(1);
			queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageALListVO pageBean = ADOConnection.runTask(als, "queryALList", PageALListVO.class,queryALListDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(pageBean.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryMeterRunAnalysisList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表运行分析列表数据", notes = "查询水表运行分析列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryMeterRunAnalysisList(@RequestBody QueryALDTO queryALDTO) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		Integer timeType = queryALDTO.getTimeType();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		if(timeType == null || startTime == null || endTime == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);
		}
		
		if(startTime > endTime) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription(Constant.MESSAGE_STRING_PARAMS);
   	 	}
		try{
			List<MeterRunAnalysisVO> data = ADOConnection.runTask(als, "queryMeterRunAnalysisList", List.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();	
	}
	
	@RequestMapping(value = "/queryMeterRunAnalysisMapData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水表运行分析图表数据", notes = "查询水表运行分析图表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryMeterRunAnalysisMapData(@RequestBody QueryALDTO queryALDTO) {
		MessageBean<MeterAnalysisMapVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, MeterAnalysisMapVO.class);
		Integer timeType = queryALDTO.getTimeType();
		Integer startTime = queryALDTO.getStartTime();
		Integer endTime = queryALDTO.getEndTime();
		if(timeType == null || startTime == null || endTime == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);
		}
		
		if(startTime > endTime) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription(Constant.MESSAGE_STRING_PARAMS);
   	 	}
		try{
			MeterAnalysisMapVO data = ADOConnection.runTask(als, "queryMeterRunAnalysisMapData", MeterAnalysisMapVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();	
	}
	
	@RequestMapping(value = "/downloadMeterRunAnalysisList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载水表运行分析列表数据", notes = "下载水表运行分析列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downloadMeterRunAnalysisList(HttpServletResponse response, HttpServletRequest request) {
		try{
			String objValue = request.getParameter("objValue"); // 获取导出数据查询条件bean
			String titleInfos = request.getParameter("titleInfos"); // 获取导出列表数据表头
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryALDTO queryALDTO = jsonValue.fromJson(objValue, QueryALDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryALDTO == null) {
				queryALDTO = new QueryALDTO();
			}
			// 查询到导出数据结果
			List<MeterRunAnalysisVO> lists = ADOConnection.runTask(als, "queryMeterRunAnalysisList", List.class,queryALDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(lists, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryDrTotalAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告总体分析数据", notes = "查询诊断报告总体分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrTotalAnalysisData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrCurrentMeterData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告现状水表数据", notes = "查询诊断报告现状水表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrCurrentMeterData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrMeterManageData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告表计管理数据", notes = "查询诊断报告表计管理数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterManageData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrMeterRunAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告水表运行分析数据", notes = "查询诊断报告水表运行分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterRunAnalysisData(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
	
	@RequestMapping(value = "/queryDrDealAdvise.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告处理建议数据", notes = "查询诊断报告处理建议数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrDealAdvise(@RequestBody QueryALDTO queryALDTO) {
		return null;
	}
}
