package com.koron.inwlms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;
import org.swan.excel.ExportExcel;

import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddVCZoneDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.AddWNWBTReportDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.LegitimateNightUseEditDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryDmaZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryFZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QuerySZoneLossListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVCZoneListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryVSZoneListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryWNWBTReportListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneHstDataDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneIndicatorListDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.WNWBReportFileDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.ZoneThematicValueDTO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneHstDataVO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.zoneLoss.LegitimateNightUseVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageDmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageFZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageSZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PageWNWBTReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.PositionInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.VCZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneIndicatorDicVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneWBLossVO;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.service.zoneLoss.LeakageParamSetService;
import com.koron.inwlms.service.zoneLoss.VZoneLossAnaService;
import com.koron.inwlms.service.zoneLoss.WaterBalanceAnaService;
import com.koron.inwlms.service.zoneLoss.ZoneLossAnaService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 分区漏损Controller层
 * @author csh
 * @Date 2020.03.09
 */
@Controller
@Api(value = "zoneLossController", description = "分区漏损Controller")
@RequestMapping(value = "/zoneLossController")
public class ZoneLossController {

	@Autowired
    private WaterBalanceAnaService wbas;
	
	@Autowired
    private ZoneLossAnaService zlas;
	
	@Autowired
    private VZoneLossAnaService vzlas;
	
	@Autowired
    private LeakageParamSetService lpss;
	
	@RequestMapping(value = "/queryZoneWBLossData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区水平衡漏损数据", notes = "查询分区水平衡漏损数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneWBLossData(@RequestBody QueryZoneWBLossDTO queryZoneWBLossDTO) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(queryZoneWBLossDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryZoneWBLossDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneWBLossDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryZoneWBLossDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryZoneWBLossDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryZoneWBLossDTO.getStartTime() > queryZoneWBLossDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(queryZoneWBLossDTO.getZoneRank() != null && (queryZoneWBLossDTO.getZoneRank() < Constant.RANK_F || queryZoneWBLossDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			List<ZoneWBLossVO> data = ADOConnection.runTask(wbas, "queryZoneWBLossData", List.class,queryZoneWBLossDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡报表列表", notes = "查询全网水平衡报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBReportList(@RequestBody QueryWNWBReportListDTO queryWNWBReportListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryWNWBReportListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryWNWBReportListDTO.getTimeType() < Constant.TIME_TYPE_M || queryWNWBReportListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryWNWBReportListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryWNWBReportListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryWNWBReportListDTO.getStartTime() > queryWNWBReportListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		try{
			PageListVO data = ADOConnection.runTask(wbas, "queryWNWBReportList", PageListVO.class,queryWNWBReportListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡报表信息", notes = "下载全网水平衡报表信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadWNWBReport(Integer id) {
		if(id == null) return null;
		HttpEntity<?> entity = null;
		try{
			//查询报表
			WNWBReportDetailVO report = ADOConnection.runTask(wbas,"queryWNWBReportDetail",WNWBReportDetailVO.class,id);
			if(report == null) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			//查询模板
			WNWBTReportDetailVO tReport = ADOConnection.runTask(wbas,"queryWNWBTReportDetail",WNWBTReportDetailVO.class,report.getTemplateId());
			Map<String,Double> indicatorValue = new HashMap<>();
			List<WNWBReportIndicator> reportIndic = report.getIndicators();
			List<WNWBTReportIndicator> tReportIndic = tReport.getIndicators();
			for(int i=0;i<tReportIndic.size();i++){
				for(int j=0;j<reportIndic.size();j++){
					if(tReportIndic.get(i).getId().equals(reportIndic.get(j).getTemplateIndicatorId())){
						indicatorValue.put(tReportIndic.get(i).getCode(), reportIndic.get(j).getValue());
					}
				}
			}
			String tempTitle = "Best Estimate for Water Losses For the ";
			String tempTilte2 = "Water Balance for ";
			if(Constant.TIME_TYPE_M.equals(report.getTimeType())){
				tempTitle += "Month (" +report.getReportTime()+")";
			}else{
				tempTitle += "Year (" +report.getReportTime()+")";
			}
			tempTilte2 += "(" +report.getReportTime()+")";
			Map<String, Object> data = new HashMap<>();
			data.put("indicatorValue", indicatorValue);
			data.put("title", tempTitle);
			data.put("title2", tempTilte2);
			entity = ExportExcel.export("file", "static/excelTemplate/waterBalanceIWAEn.xlsx", "/excelTemplate/waterBalanceIWA.btl", data);
		}catch(Exception e){
			e.printStackTrace();
		}
		return entity;
	}
	
	@RequestMapping(value = "/deleteWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表", notes = "删除全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReport(Integer id) {
		MessageBean<Void> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Void.class);
		try{
			if(id != null){
				ADOConnection.runTask(wbas,"deleteWNWBReport",Void.class,id);	
			}else{
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription(Constant.MESSAGE_STRING_NULL);
			}
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	/**
	 * 
	 * @param objValue 数据查询条件bean
	 * @param titleInfos 导出列表数据表头
	 * @return 
	 */
	@RequestMapping(value = "/downloadWNWBReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡报表列表", notes = "下载全网水平衡报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadWNWBReportList(@RequestBody String objValue,@RequestBody String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryWNWBReportListDTO qwnwbr = jsonValue.fromJson(objValue, QueryWNWBReportListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qwnwbr == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qwnwbr.setPage(1);
			qwnwbr.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageWNWBReportListVO data = ADOConnection.runTask(wbas, "queryWNWBReportList", PageWNWBReportListVO.class,qwnwbr);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/addWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加全网水平衡报表", notes = "添加全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addWNWBReport(@RequestBody AddWNWBReportDTO addWNWBReportDTO) {
		MessageBean<AddWNWBReportDTO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBReportDTO.class);
		try{
			ADOConnection.runTask(wbas,"addWNWBReport",Integer.class,addWNWBReportDTO);
			data.setData(addWNWBReportDTO);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_ADDERROR);
			data.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/updateWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡报表", notes = "编辑全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReport(@RequestBody AddWNWBReportDTO editWNWBReportDTO) {
		MessageBean<AddWNWBReportDTO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBReportDTO.class);
		try{
			ADOConnection.runTask(wbas,"updateWNWBReport",Integer.class,editWNWBReportDTO);
			data.setData(editWNWBReportDTO);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_EDITERROR);
			data.setDescription(Constant.MESSAGE_STRING_EDITERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBReportDetail.htm", method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询全网水平衡报表详情",notes = "查询全网水平衡报表详情",httpMethod = "POST",consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryWNWBReportDetail(Integer id){
		MessageBean<WNWBReportDetailVO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, WNWBReportDetailVO.class);
		try{
			if(id != null){ //判断查看详情时，id是否为空
				WNWBReportDetailVO report = ADOConnection.runTask(wbas,"queryWNWBReportDetail",WNWBReportDetailVO.class,id);
				data.setData(report);
			}else{
				data.setCode(Constant.MESSAGE_INT_NULL);
				data.setDescription(Constant.MESSAGE_STRING_NULL);
			}
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_SELECTERROR);
			data.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBReporFileList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡报表附件列表", notes = "查查询全网水平衡报表附件列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBReporFileList(@RequestBody WNWBReportFileDTO wNWBReportFileDTO) {
		MessageBean<List> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		try{
			@SuppressWarnings("unchecked")
			List<WNWBReporFileListVO> lists = ADOConnection.runTask(wbas,"queryWNWBReporFileList",List.class,wNWBReportFileDTO);
			data.setData(lists);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_ERROR);
			data.setDescription(Constant.MESSAGE_STRING_ERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/deleteWNWBReporFile.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表附件", notes = "删除全网水平衡报表附件", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReporFile(Integer fileId) {
		MessageBean<Void> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Void.class);
		try{
			if(fileId != null){
				ADOConnection.runTask(wbas,"deleteReportFileById",Void.class,fileId);	
			}else{
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription(Constant.MESSAGE_STRING_NULL);
			}
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBTReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡模板报表列表", notes = "查询全网水平衡模板报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBTReportList(@RequestBody QueryWNWBTReportListDTO queryWNWBTReportListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		try{
			PageListVO data = ADOConnection.runTask(wbas, "queryWNWBTReportList", PageListVO.class,queryWNWBTReportListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	/**
	 * 
	 * @param objValue 数据查询条件bean
	 * @param titleInfos 导出列表数据表头
	 * @return
	 */
	@RequestMapping(value = "/downloadWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡模板报表列表", notes = "下载全网水平衡模板报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadWNWBReporTemplate(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryWNWBTReportListDTO qwnwbtr = jsonValue.fromJson(objValue, QueryWNWBTReportListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qwnwbtr == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qwnwbtr.setPage(1);
			qwnwbtr.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageWNWBTReportListVO data = ADOConnection.runTask(wbas, "queryWNWBTReportList", PageWNWBTReportListVO.class,qwnwbtr);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡模板报表", notes = "删除全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteWNWBReporTemplate(Integer tId) {
		MessageBean<Void> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Void.class);
		try{
			if(tId != null){
				ADOConnection.runTask(wbas,"deleteWNWBTReport",Void.class,tId);	
			}else{
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription(Constant.MESSAGE_STRING_NULL);
			}
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/addWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加全网水平衡模板报表", notes = "添加全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addWNWBReporTemplate(@RequestBody AddWNWBTReportDTO addWNWBTReportDTO) {
		MessageBean<AddWNWBTReportDTO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBTReportDTO.class);
		try{
			ADOConnection.runTask(wbas,"addWNWBTReport",Integer.class,addWNWBTReportDTO);
			data.setData(addWNWBTReportDTO);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_ADDERROR);
			data.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/updateWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡模板报表", notes = "编辑全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReporTemplate(@RequestBody AddWNWBTReportDTO editWNWBTReportDTO) {
		MessageBean<AddWNWBTReportDTO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBTReportDTO.class);
		try{
			ADOConnection.runTask(wbas,"updateWNWBTReport",Integer.class,editWNWBTReportDTO);
			data.setData(editWNWBTReportDTO);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_EDITERROR);
			data.setDescription(Constant.MESSAGE_STRING_EDITERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBTReportDetail.htm", method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询全网水平衡模板报表详情",notes = "查询全网水平衡报表详情",httpMethod = "POST",consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryWNWBTReportDetail(Integer id){
		MessageBean<WNWBTReportDetailVO> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, WNWBTReportDetailVO.class);
		try{
			if(id != null){ //判断查看详情时，id是否为空
				WNWBTReportDetailVO report = ADOConnection.runTask(wbas,"queryWNWBTReportDetail",WNWBTReportDetailVO.class,id);
				data.setData(report);
			}else{
				data.setCode(Constant.MESSAGE_INT_NULL);
				data.setDescription(Constant.MESSAGE_STRING_NULL);
			}
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_SELECTERROR);
			data.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/queryFZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询一级分区漏损分析列表", notes = "查询一级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryFZoneLossList(@RequestBody QueryFZoneLossListDTO queryFZoneLossListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryFZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryFZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_M || queryFZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryFZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryFZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryFZoneLossListDTO.getStartTime() > queryFZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(queryFZoneLossListDTO.getMinNrw() > queryFZoneLossListDTO.getMaxNrw()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("产销差范围输入错误");
			return msg.toJson();
   	 	}
		if(queryFZoneLossListDTO.getMinUfwc() > queryFZoneLossListDTO.getMaxUfwc()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("漏损水量范围输入错误");
			return msg.toJson();
   	 	}
		try{
			PageListVO data = ADOConnection.runTask(zlas, "queryFZoneLossList", PageListVO.class,queryFZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadFZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载一级分区漏损分析列表", notes = "下载一级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadFZoneLossList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryFZoneLossListDTO qfzlDTO = jsonValue.fromJson(objValue, QueryFZoneLossListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qfzlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qfzlDTO.setPage(1);
			qfzlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageFZoneLossListVO data = ADOConnection.runTask(zlas, "queryFZoneLossList", PageFZoneLossListVO.class,qfzlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/querySZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询二级分区漏损分析列表", notes = "查询二级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String querySZoneLossList(@RequestBody QuerySZoneLossListDTO querySZoneLossListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(querySZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(querySZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_M || querySZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(querySZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(querySZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(querySZoneLossListDTO.getStartTime() > querySZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(querySZoneLossListDTO.getMinNrw() > querySZoneLossListDTO.getMaxNrw()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("产销差范围输入错误");
			return msg.toJson();
   	 	}
		if(querySZoneLossListDTO.getMinUfwc() > querySZoneLossListDTO.getMaxUfwc()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("漏损水量范围输入错误");
			return msg.toJson();
   	 	}
		try{
			PageListVO data = ADOConnection.runTask(zlas, "querySZoneLossList", PageListVO.class,querySZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadSZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载二级分区漏损分析列表", notes = "下载二级分区漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadSZoneLossList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QuerySZoneLossListDTO qszlDTO = jsonValue.fromJson(objValue, QuerySZoneLossListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qszlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qszlDTO.setPage(1);
			qszlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageSZoneLossListVO data = ADOConnection.runTask(zlas, "querySZoneLossList", PageSZoneLossListVO.class,qszlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryDmaZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询DMA漏损分析列表", notes = "查询DMA漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDmaZoneLossList(@RequestBody QueryDmaZoneLossListDTO QueryDmaZoneLossListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(QueryDmaZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(QueryDmaZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_M || QueryDmaZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(QueryDmaZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(QueryDmaZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(QueryDmaZoneLossListDTO.getStartTime() > QueryDmaZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(QueryDmaZoneLossListDTO.getMinNrw() > QueryDmaZoneLossListDTO.getMaxNrw()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("产销差范围输入错误");
			return msg.toJson();
   	 	}
		if(QueryDmaZoneLossListDTO.getMinUfwc() > QueryDmaZoneLossListDTO.getMaxUfwc()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("漏损水量范围输入错误");
			return msg.toJson();
   	 	}
		try{
			PageListVO data = ADOConnection.runTask(zlas, "queryDmaZoneLossList", PageListVO.class,QueryDmaZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadDmaZoneLossList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载DMA漏损分析列表", notes = "下载DMA漏损分析列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadDmaZoneLossList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryDmaZoneLossListDTO qdzlDTO = jsonValue.fromJson(objValue, QueryDmaZoneLossListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qdzlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qdzlDTO.setPage(1);
			qdzlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageDmaZoneLossListVO data = ADOConnection.runTask(zlas, "queryDmaZoneLossList", PageDmaZoneLossListVO.class,qdzlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryZonelocation.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区地图定位信息", notes = "查询分区地图定位信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZonelocation(@RequestBody QueryZoneInfoDTO queryZoneInfoDTO) {
		MessageBean<PositionInfoVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PositionInfoVO.class);
		if(StringUtil.isEmpty(queryZoneInfoDTO.getZoneNo())) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区编号为空");
			return msg.toJson();
		}
		if(queryZoneInfoDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}
		if(queryZoneInfoDTO.getZoneType() != null && (queryZoneInfoDTO.getZoneType() < 1 || queryZoneInfoDTO.getZoneType() > 4)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区类型数值错误");
			return msg.toJson();
		}
		try{
			PositionInfoVO data = ADOConnection.runTask(new GisZoneServiceImpl(), "queryZonePositionInfo", PositionInfoVO.class,queryZoneInfoDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryZonedetail.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区详情", notes = "查询分区详情", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZonedetail(@RequestBody QueryZoneInfoDTO queryZoneInfoDTO) {
		MessageBean<ZoneDetailInfoVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ZoneDetailInfoVO.class);
		if(StringUtil.isEmpty(queryZoneInfoDTO.getZoneNo())) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区编号为空");
			return msg.toJson();
		}
		if(queryZoneInfoDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}
		if(queryZoneInfoDTO.getZoneType() != null && (queryZoneInfoDTO.getZoneType() < 1 || queryZoneInfoDTO.getZoneType() > 4)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区类型数值错误");
			return msg.toJson();
		}
		try{
			ZoneDetailInfoVO data = ADOConnection.runTask(new GisZoneServiceImpl(), "queryZoneDetailInfo", ZoneDetailInfoVO.class,queryZoneInfoDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryZoneHstData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区漏损历史数据", notes = "查询分区漏损历史数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneHstData(@RequestBody QueryZoneHstDataDTO queryZoneHstDataDTO) {
		MessageBean<ZoneHstDataVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ZoneHstDataVO.class);
		if(queryZoneHstDataDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneHstDataDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getZoneNo() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区编号为空");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getCodes() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("指标编码为空");
			return msg.toJson();
		}
		if(queryZoneHstDataDTO.getStartTime() > queryZoneHstDataDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		try{
			ZoneHstDataVO data = ADOConnection.runTask(zlas, "queryZoneHstData", ZoneHstDataVO.class,queryZoneHstDataDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryVSZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询虚拟分区（相减）列表", notes = "查询虚拟分区（相减）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryVSZoneList(@RequestBody QueryVSZoneListDTO queryVSZoneListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryVSZoneListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryVSZoneListDTO.getTimeType() < Constant.TIME_TYPE_M || queryVSZoneListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryVSZoneListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryVSZoneListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryVSZoneListDTO.getStartTime() > queryVSZoneListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(queryVSZoneListDTO.getMinNrw() > queryVSZoneListDTO.getMaxNrw()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("产销差范围输入错误");
			return msg.toJson();
   	 	}
		if(queryVSZoneListDTO.getMinUfwc() > queryVSZoneListDTO.getMaxUfwc()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("漏损水量范围输入错误");
			return msg.toJson();
   	 	}
		try{
			PageListVO<List<VSZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVSZoneList", PageListVO.class,queryVSZoneListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadVSZoneHstList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载虚拟分区（相减）列表", notes = "下载虚拟分区（相减）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadVSZoneHstList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryVSZoneListDTO qvszlDTO = jsonValue.fromJson(objValue, QueryVSZoneListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qvszlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qvszlDTO.setPage(1);
			qvszlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<VSZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVSZoneList", PageListVO.class,qvszlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryVCZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询拟分区（合并）列表", notes = "查询拟分区（合并）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryVCZoneList(@RequestBody QueryVCZoneListDTO queryVCZoneListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryVCZoneListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryVCZoneListDTO.getTimeType() < Constant.TIME_TYPE_M || queryVCZoneListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryVCZoneListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryVCZoneListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryVCZoneListDTO.getStartTime() > queryVCZoneListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(queryVCZoneListDTO.getMinNrw() > queryVCZoneListDTO.getMaxNrw()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("产销差范围输入错误");
			return msg.toJson();
   	 	}
		if(queryVCZoneListDTO.getMinUfwc() > queryVCZoneListDTO.getMaxUfwc()) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("漏损水量范围输入错误");
			return msg.toJson();
   	 	}
		try{
			PageListVO<List<VCZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVCZoneList", PageListVO.class,queryVCZoneListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadVCZoneList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载拟分区（合并）列表", notes = "下载拟分区（合并）列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadVCZoneList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryVCZoneListDTO qvczlDTO = jsonValue.fromJson(objValue, QueryVCZoneListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qvczlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qvczlDTO.setPage(1);
			qvczlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<VCZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVCZoneList", PageListVO.class,qvczlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/addVCZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加虚拟分区（合并）", notes = "添加虚拟分区（合并）", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addVCZone(@RequestBody AddVCZoneDTO addVCZoneDTO) {
		MessageBean<Void> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Void.class);
		if(StringUtil.isEmpty(addVCZoneDTO.getsZoneNos())) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("合并的分区编号为空");
			return msg.toJson();
		}
		try{
			ADOConnection.runTask(vzlas,"addVCZone",Void.class,addVCZoneDTO);
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);	
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteVCZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除虚拟分区（合并）", notes = "删除虚拟分区（合并）", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteVCZone(String vZoneNo) {
		MessageBean<Void> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Void.class);
		if(StringUtil.isEmpty(vZoneNo)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("虚拟分区编号为空");
			return msg.toJson();
		}
		try{
			ADOConnection.runTask(vzlas,"deleteVCZone",Void.class,vZoneNo);
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);	
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	/**
	 * 查询全网漏点专题图（调用gis接口）
	 * @param thematicMapType 专题图类型（0：管径，1：漏损现象，2：处理情况，3：漏点密度）
	 * @return
	 */
	@RequestMapping(value = "/queryWNLeakMapList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网专题图列表", notes = "查询全网专题图列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNLeakMapList(Integer thematicMapType) {
		return null;
	}
	
	/**
	 * 查询全网漏点列表信息（调用gis接口）
	 * @return
	 */
	@RequestMapping(value = "/queryWNLeakList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网漏点列表信息", notes = "查询全网漏点列表信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNLeakList() {
		return null;
	}
	
	@RequestMapping(value = "/queryZoneIndicatorDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区指标数据字典", notes = "查询分区指标数据字典", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneIndicatorDic(Integer zoneType) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(zoneType == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}
		if(zoneType < 1 || zoneType > 3) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区类型数值错误");
			return msg.toJson();
		}
		try{
			List<ZoneIndicatorDicVO> lists = ADOConnection.runTask(zlas,"queryZoneIndicatorDic",List.class,zoneType);
			msg.setData(lists);
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription(Constant.MESSAGE_STRING_NULL);	
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
		
	}
	
	@RequestMapping(value = "/queryZoneThematicValue.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区专题图指标数据", notes = "查询分区专题图指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneThematicValue(@RequestBody ZoneThematicValueDTO zoneThematicValueDTO) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(zoneThematicValueDTO.getItemCode() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("指标编码为空");
			return msg.toJson();
		}
		if(zoneThematicValueDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(zoneThematicValueDTO.getTimeType() < Constant.TIME_TYPE_M || zoneThematicValueDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(zoneThematicValueDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(zoneThematicValueDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(zoneThematicValueDTO.getStartTime() > zoneThematicValueDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(zoneThematicValueDTO.getZoneType() != null && (zoneThematicValueDTO.getZoneType() < Constant.RANK_F || zoneThematicValueDTO.getZoneType() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			List<Map<Object, Object>> data = ADOConnection.runTask(zlas, "queryZoneThematicValue", List.class,zoneThematicValueDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryZoneIndicatorList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区指标数据", notes = "查询分区指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneIndicatorList(@RequestBody QueryZoneIndicatorListDTO queryZoneIndicatorListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		if(queryZoneIndicatorListDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}
		if(queryZoneIndicatorListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(queryZoneIndicatorListDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneIndicatorListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(queryZoneIndicatorListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(queryZoneIndicatorListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(queryZoneIndicatorListDTO.getStartTime() > queryZoneIndicatorListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		if(queryZoneIndicatorListDTO.getZoneType() != null && (queryZoneIndicatorListDTO.getZoneType() < Constant.RANK_F || queryZoneIndicatorListDTO.getZoneType() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			PageListVO<List<Map<Object,Object>>> data = ADOConnection.runTask(zlas, "queryZoneIndicatorList", PageListVO.class,queryZoneIndicatorListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downloadZoneIndicatorList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载分区指标数据", notes = "下载分区指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadZoneIndicatorList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryVCZoneListDTO qvczlDTO = jsonValue.fromJson(objValue, QueryVCZoneListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qvczlDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			qvczlDTO.setPage(1);
			qvczlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<Map<Object,Object>>> data = ADOConnection.runTask(zlas, "queryZoneIndicatorList", PageListVO.class,qvczlDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/queryWBIndicatorData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询水平衡指标数据", notes = "查询水平衡指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWBIndicatorData(@RequestBody IndicatorDTO indicatorDTO) {
		MessageBean<List> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(indicatorDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}
		if(indicatorDTO.getTimeType() < Constant.TIME_TYPE_M || indicatorDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}
		if(indicatorDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(indicatorDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		if(indicatorDTO.getStartTime() > indicatorDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}
		try{
			List<IndicatorVO> data = ADOConnection.runTask(wbas, "queryWBIndicatorData", List.class,indicatorDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	 @ApiOperation(value = "查询漏损量指标值", notes = "查询漏损量指标值", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @RequestMapping(value = "/queryLeakageExponent.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	 @ResponseBody
	 public String queryLeakageExponent() {
	     MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
	     try {
	         String leakageExponent = ADOConnection.runTask(lpss, "queryLeakageExponent", String.class);
	         msg.setData(leakageExponent);
	     } catch (Exception e) {
	         msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
	         msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
	     }
	     return msg.toJson();
	 }
	 
	 @ApiOperation(value = "更新漏损量指标值", notes = "更新漏损量指标值", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @RequestMapping(value = "/updateLeakageExponent.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	 @ResponseBody
	 public String updateLeakageExponent(String value) {
	     MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
	     if(StringUtil.isEmpty(value)) {
	    	 msg.setCode(Constant.MESSAGE_INT_NULL);
			 msg.setDescription("漏损量指标值为空");
			 return msg.toJson();
	     }
	     try {
	         ADOConnection.runTask(lpss, "updateLeakageExponent", Void.class,value);
	     } catch (Exception e) {
	         msg.setCode(Constant.MESSAGE_INT_EDITERROR);
	         msg.setDescription(Constant.MESSAGE_STRING_EDITERROR);
	     }
	     return msg.toJson();
	 }
	 
	 /**
	     * 分页查询合理夜晚使用量列表信息信息方法
	     *
	     * @param legitimateNightUseListDTO 滤水厂回用水数据列表查询DTO
	     * @return
	     */
	    @ApiOperation(value = "分页查询合理夜晚使用量列表信息信息方法", notes = "条件分页查询合理夜晚使用量列表信息信息方法", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	    @RequestMapping(value = "/queryLegitimateNightUseList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	    @ResponseBody
	    public String queryLegitimateNightUseList(@RequestBody LegitimateNightUseDTO lnuDTO) {
	    	MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
			try{
				PageListVO data = ADOConnection.runTask(lpss, "queryLegitimateNightUseList", PageListVO.class,lnuDTO);
				msg.setData(data);
	    	}catch(Exception e){
	    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
	    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
	    	}
			return msg.toJson();
	    }

	    /**
	     * 导出合理夜晚使用量信息
	     *
	     * @return
	     */
	    @ApiOperation(value = "导出合理夜晚使用量信息", notes = "导出合理夜晚使用量信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	    @RequestMapping(value = "/downloadLegitimateNightUseList.htm", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
	    @ResponseBody
	    public HttpEntity<?> downloadLegitimateNightUseList(@RequestParam String objValue,@RequestParam String titleInfos) {
	    	try{
				Gson jsonValue = new Gson();
				// 查询条件字符串转对象，查询数据结果
				LegitimateNightUseDTO lnuDTO = jsonValue.fromJson(objValue, LegitimateNightUseDTO.class);
				// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
				if (lnuDTO == null) {
					return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
				}
				lnuDTO.setPage(1);
				lnuDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
				// 查询到导出数据结果
				PageListVO<List<LegitimateNightUseVO>> data = ADOConnection.runTask(lpss, "queryLegitimateNightUseList", PageListVO.class,lnuDTO);
				List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
						}.getType());
				// 导出excel文件
				//导出list
				return ExportDataUtil.getExcelDataFileInfoByList(data.getDataList(), jsonArray);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	    }


	    /**
	     * 更新合理夜晚使用量信息
	     *
	     * @param legitimateNightUseEditDTO 滤水厂回用水数据更新DTO
	     * @return
	     */
	    @ApiOperation(value = "更新合理夜晚使用量信息", notes = "更新合理夜晚使用量信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	    @RequestMapping(value = "/updateLegitimateNightUse.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	    @ResponseBody
	    public String updateLegitimateNightUse(@RequestBody LegitimateNightUseEditDTO legitimateNightUseEditDTO) {
	    	MessageBean msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, Void.class);
			if(legitimateNightUseEditDTO.getId() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("id为空");
				return msg.toJson();
			}
			if(legitimateNightUseEditDTO.getCode() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("类别编码为空");
				return msg.toJson();
			}
			if(legitimateNightUseEditDTO.getCode() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("夜间合理流量为空");
				return msg.toJson();
			}
			try{
				ADOConnection.runTask(lpss, "updateLegitimateNightUse", Void.class,legitimateNightUseEditDTO);
	    	}catch(Exception e){
	    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
	    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
	    	}
			return msg.toJson();
	    }
	
}
