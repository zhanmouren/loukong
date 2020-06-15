package com.koron.inwlms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALDTO;
import com.koron.inwlms.bean.DTO.apparentLoss.QueryALListDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ALListVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALMapDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrCurrentMeterDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrDealAdviseVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterAnaDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrMeterManageVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrTotalAnalysisDataVO;
import com.koron.inwlms.bean.VO.apparentLoss.DrqlVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterAnalysisMapVO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
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
@RequestMapping(value = "/{tenantID}/apparentLossController")
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
	public String queryALOverviewData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<ALOverviewDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ALOverviewDataVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			ALOverviewDataVO data = ADOConnection.runTask(user.getEnv(),als, "queryALOverviewData", ALOverviewDataVO.class,queryALDTO);
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
	public String queryALList(@RequestBody QueryALListDTO queryALListDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryALListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALListDTO.getTimeType() < Constant.TIME_TYPE_M || queryALListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALListDTO.getStartTime() > queryALListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALListDTO.getZoneRank() != null && (queryALListDTO.getZoneRank() < Constant.RANK_F || queryALListDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}else if(queryALListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
			return msg.toJson();
		}else if(queryALListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
			return msg.toJson();
		}
		try{
			PageListVO data = ADOConnection.runTask(user.getEnv(),als, "queryALList", PageListVO.class,queryALListDTO);
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
	public String queryALMapData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<ALMapDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ALMapDataVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			ALMapDataVO data = ADOConnection.runTask(user.getEnv(),als, "queryALMapData", ALMapDataVO.class,queryALDTO);
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
	public HttpEntity<?> downloadALList(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryALListDTO queryALListDTO = jsonValue.fromJson(objValue, QueryALListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryALListDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			queryALListDTO.setPage(1);
			queryALListDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<ALListVO>> pageBean = ADOConnection.runTask(user.getEnv(),als, "queryALList", PageListVO.class,queryALListDTO);
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
	public String queryMeterRunAnalysisList(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			List<MeterRunAnalysisVO> data = ADOConnection.runTask(user.getEnv(),als, "queryMeterRunAnalysisList", List.class,queryALDTO);
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
	public String queryMeterRunAnalysisMapData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<MeterAnalysisMapVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, MeterAnalysisMapVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			MeterAnalysisMapVO data = ADOConnection.runTask(user.getEnv(),als, "queryMeterRunAnalysisMapData", MeterAnalysisMapVO.class,queryALDTO);
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
    public HttpEntity<?> downloadMeterRunAnalysisList(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryALDTO queryALDTO = jsonValue.fromJson(objValue, QueryALDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryALDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			// 查询到导出数据结果
			List<MeterRunAnalysisVO> lists = ADOConnection.runTask(user.getEnv(),als, "queryMeterRunAnalysisList", List.class,queryALDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			HttpEntity<?> entity = ExportDataUtil.getExcelDataFileInfoByList(lists, jsonArray);
			return entity;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryDrTotalAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告总体分析数据", notes = "查询诊断报告总体分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrTotalAnalysisData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrTotalAnalysisDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrTotalAnalysisDataVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrTotalAnalysisDataVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrTotalAnalysisData", DrTotalAnalysisDataVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryDrCurrentMeterData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告现状水表数据", notes = "查询诊断报告现状水表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrCurrentMeterData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrCurrentMeterDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrCurrentMeterDataVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrCurrentMeterDataVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrCurrentMeterData", DrCurrentMeterDataVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryDrMeterManageData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告表计管理数据", notes = "查询诊断报告表计管理数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterManageData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrMeterManageVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrMeterManageVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrMeterManageVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrMeterManageData", DrMeterManageVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryDrMeterRunAnalysisData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告水表运行分析数据", notes = "查询诊断报告水表运行分析数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrMeterRunAnalysisData(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrMeterAnaDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrMeterAnaDataVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrMeterAnaDataVO data = ADOConnection.runTask(user.getEnv(),als, "queryMeterAnaData", DrMeterAnaDataVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryDrDealAdvise.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告处理建议数据", notes = "查询诊断报告处理建议数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrDealAdvise(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrDealAdviseVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrDealAdviseVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrDealAdviseVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrDealAdvise", DrDealAdviseVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryDrQuestionList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询诊断报告问题清单数据", notes = "查询诊断报告问题清单数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDrQuestionList(@RequestBody QueryALDTO queryALDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<DrqlVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, DrqlVO.class);
		if(queryALDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryALDTO.getTimeType() < Constant.TIME_TYPE_M || queryALDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryALDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryALDTO.getStartTime() > queryALDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryALDTO.getZoneRank() != null && (queryALDTO.getZoneRank() < Constant.RANK_F || queryALDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			DrqlVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrQuestionList", DrqlVO.class,queryALDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadDrQuestionList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载诊断报告问题清单列表", notes = "下载诊断报告问题清单列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadDrQuestionList(@RequestParam String objValue,@RequestParam String titleInfos,@RequestParam String labelId,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			if(StringUtil.isEmpty(labelId)) return null;
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryALDTO queryALDTO = jsonValue.fromJson(objValue, QueryALDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (queryALDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			// 查询到导出数据结果
			DrqlVO data = ADOConnection.runTask(user.getEnv(),als, "queryDrQuestionList", DrqlVO.class,queryALDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			if("0".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlBDnZeroFlowData(), jsonArray);
			}else if("1".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlBDnLHFlowData(), jsonArray);
			}else if("2".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlBDnErrFlowData(), jsonArray);
			}else if("3".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlSusUseData(), jsonArray);
			}else if("4".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlsDnZeroFlowData(), jsonArray);
			}else if("5".equals(labelId)) {
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDrqlsDnLHFlowData(), jsonArray);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
