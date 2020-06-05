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
import com.koron.inwlms.bean.VO.zoneLoss.AnalysisIndicatorVO;
import com.koron.inwlms.bean.VO.zoneLoss.DmaZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.FZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.LegitimateNightUseVO;
import com.koron.inwlms.bean.VO.zoneLoss.PositionInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.SZoneLossListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VCZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.VSZoneListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReporFileListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBReportListVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportDetailVO;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportIndicator;
import com.koron.inwlms.bean.VO.zoneLoss.WNWBTReportListVO;
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

	private QueryDmaZoneLossListDTO queryFZoneLossListDTO;
	
	@RequestMapping(value = "/queryZoneWBLossData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区水平衡漏损数据", notes = "查询分区水平衡漏损数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneWBLossData(@RequestBody QueryZoneWBLossDTO queryZoneWBLossDTO) {
		MessageBean<ZoneWBLossVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, ZoneWBLossVO.class);
		if(queryZoneWBLossDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(queryZoneWBLossDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneWBLossDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryZoneWBLossDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryZoneWBLossDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryZoneWBLossDTO.getStartTime() > queryZoneWBLossDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(queryZoneWBLossDTO.getZoneRank() != null && (queryZoneWBLossDTO.getZoneRank() < Constant.RANK_F || queryZoneWBLossDTO.getZoneRank() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			ZoneWBLossVO data = ADOConnection.runTask(wbas, "queryZoneWBLossData", ZoneWBLossVO.class,queryZoneWBLossDTO);
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
		//参数校验
		msg = checkWNWBReportListParam(queryWNWBReportListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO data = ADOConnection.runTask(wbas, "queryWNWBReportList", PageListVO.class,queryWNWBReportListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	/**
	 * 校验全网水平衡报表参数
	 * @return
	 */
	public MessageBean checkWNWBReportListParam(QueryWNWBReportListDTO queryWNWBReportListDTO,MessageBean<PageListVO> msg){
		if(queryWNWBReportListDTO.getStartTime() != null || queryWNWBReportListDTO.getEndTime() != null ) {
			if(queryWNWBReportListDTO.getTimeType() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("时间粒度为空");
			}
		}else if(queryWNWBReportListDTO.getTimeType() != null && (queryWNWBReportListDTO.getTimeType() < Constant.TIME_TYPE_M || queryWNWBReportListDTO.getTimeType() > Constant.TIME_TYPE_Y)) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if((queryWNWBReportListDTO.getStartTime() != null && queryWNWBReportListDTO.getEndTime() != null) && (queryWNWBReportListDTO.getStartTime() > queryWNWBReportListDTO.getEndTime())) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if(queryWNWBReportListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryWNWBReportListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
	}	
	
	@RequestMapping(value = "/downloadWNWBReport.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载全网水平衡报表信息", notes = "下载全网水平衡报表信息", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
						indicatorValue.put(tReportIndic.get(i).getIndicatorCode(), reportIndic.get(j).getValue());
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
	
	@RequestMapping(value = "/deleteWNWBReport.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表", notes = "删除全网水平衡报表", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
	public HttpEntity<?> downloadWNWBReportList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryWNWBReportListDTO qwnwbr = jsonValue.fromJson(objValue, QueryWNWBReportListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qwnwbr == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			//参数校验
			MessageBean msg = checkWNWBReportListParam(qwnwbr,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			qwnwbr.setPage(1);
			qwnwbr.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<WNWBReportListVO>> data = ADOConnection.runTask(wbas, "queryWNWBReportList", PageListVO.class,qwnwbr);
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
		MessageBean<AddWNWBReportDTO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBReportDTO.class);
		if(addWNWBReportDTO.getReportName() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表名称为空");
		}else if(addWNWBReportDTO.getIndicators() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表指标为空");
		}else if(addWNWBReportDTO.getReportTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表时间为空");
		}else if(addWNWBReportDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间类型为空");
		}else if(addWNWBReportDTO.getTemplateId() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表id为空");
		}
		try{
			ADOConnection.runTask(wbas,"addWNWBReport",Integer.class,addWNWBReportDTO);
			msg.setData(addWNWBReportDTO);
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updateWNWBReport.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡报表", notes = "编辑全网水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReport(@RequestBody AddWNWBReportDTO editWNWBReportDTO) {
		MessageBean<AddWNWBReportDTO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBReportDTO.class);
		if(editWNWBReportDTO.getId() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表id为空");
		}else if(editWNWBReportDTO.getReportName() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表名称为空");
		}else if(editWNWBReportDTO.getIndicators() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表指标为空");
		}else if(editWNWBReportDTO.getReportTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("报表时间为空");
		}else if(editWNWBReportDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间类型为空");
		}else if(editWNWBReportDTO.getTemplateId() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表id为空");
		}
		try{
			ADOConnection.runTask(wbas,"updateWNWBReport",Integer.class,editWNWBReportDTO);
			msg.setData(editWNWBReportDTO);
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			msg.setDescription(Constant.MESSAGE_STRING_EDITERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBReportDetail.htm", method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询全网水平衡报表详情",notes = "查询全网水平衡报表详情",httpMethod = "GET",consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping(value = "/deleteWNWBReporFile.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡报表附件", notes = "删除全网水平衡报表附件", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping(value = "/queryWNWBIndicatorData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡报表指标值", notes = "查询全网水平衡报表指标值", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBIndicatorData(@RequestBody IndicatorDTO indicatorDTO) {
		MessageBean<List> data = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		try{
			@SuppressWarnings("unchecked")
			List<IndicatorVO> lists = ADOConnection.runTask(wbas,"queryWNWBIndicatorData",List.class,indicatorDTO);
			data.setData(lists);
		}catch(Exception e){
			data.setCode(Constant.MESSAGE_INT_ERROR);
			data.setDescription(Constant.MESSAGE_STRING_ERROR);
		}
		return data.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBTReportList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询全网水平衡模板报表列表", notes = "查询全网水平衡模板报表列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWNWBTReportList(@RequestBody QueryWNWBTReportListDTO queryWNWBTReportListDTO) {
		MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
		msg = checkWNWBTReportListParam(queryWNWBTReportListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
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
	 * 校验全网水平衡模板报表参数
	 * @return
	 */
	public MessageBean checkWNWBTReportListParam(QueryWNWBTReportListDTO queryWNWBTReportListDTO,MessageBean<PageListVO> msg){
		if(queryWNWBTReportListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryWNWBTReportListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkWNWBTReportListParam(qwnwbtr,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			qwnwbtr.setPage(1);
			qwnwbtr.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<WNWBTReportListVO>> data = ADOConnection.runTask(wbas, "queryWNWBTReportList", PageListVO.class,qwnwbtr);
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
	
	@RequestMapping(value = "/deleteWNWBReporTemplate.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除全网水平衡模板报表", notes = "删除全网水平衡模板报表", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
		MessageBean<AddWNWBTReportDTO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBTReportDTO.class);
		if(addWNWBTReportDTO.getReportName() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表名称为空");
		}else if(addWNWBTReportDTO.getIndicators() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表指标为空");
		}
		try{
			ADOConnection.runTask(wbas,"addWNWBTReport",Integer.class,addWNWBTReportDTO);
			msg.setData(addWNWBTReportDTO);
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			msg.setDescription(Constant.MESSAGE_STRING_ADDERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/updateWNWBReporTemplate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "编辑全网水平衡模板报表", notes = "编辑全网水平衡模板报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateWNWBReporTemplate(@RequestBody AddWNWBTReportDTO editWNWBTReportDTO) {
		MessageBean<AddWNWBTReportDTO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, AddWNWBTReportDTO.class);
		if(editWNWBTReportDTO.getId() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表id为空");
		}else if(editWNWBTReportDTO.getReportName() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表名称为空");
		}else if(editWNWBTReportDTO.getIndicators() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("模板报表指标为空");
		}
		try{
			ADOConnection.runTask(wbas,"updateWNWBTReport",Integer.class,editWNWBTReportDTO);
			msg.setData(editWNWBTReportDTO);
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			msg.setDescription(Constant.MESSAGE_STRING_EDITERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/queryWNWBTReportDetail.htm", method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ApiOperation(value = "查询全网水平衡模板报表详情",notes = "查询全网水平衡报表详情",httpMethod = "GET",consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
		msg = checkFZoneLossListParam(queryFZoneLossListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO data = ADOConnection.runTask(zlas, "queryFZoneLossList", PageListVO.class,queryFZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	/**
	 * 校验一级分区参数
	 * @return
	 */
	public MessageBean checkFZoneLossListParam(QueryFZoneLossListDTO queryFZoneLossListDTO,MessageBean<PageListVO> msg){
		if(queryFZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(queryFZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_D || queryFZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(queryFZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(queryFZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(queryFZoneLossListDTO.getStartTime() > queryFZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if((StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryFZoneLossListDTO.getMinNrw())) 
   	 			|| (StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryFZoneLossListDTO.getMaxNrw()))
   	 		|| (StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryFZoneLossListDTO.getMinWl())) 
	 			|| (StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryFZoneLossListDTO.getMaxWl()))) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("请输入数字类型的数据");
		}else if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinNrw()) && StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxNrw())) {
			if(Double.parseDouble(queryFZoneLossListDTO.getMinNrw()) > Double.parseDouble(queryFZoneLossListDTO.getMaxNrw())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("产销差范围输入错误");
	   	 	}
		}else if(StringUtil.isNotEmpty(queryFZoneLossListDTO.getMinWl()) && StringUtil.isNotEmpty(queryFZoneLossListDTO.getMaxWl())) {
			if(Double.parseDouble(queryFZoneLossListDTO.getMinWl()) > Double.parseDouble(queryFZoneLossListDTO.getMaxWl())) {
				//漏损量范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("漏损水量范围输入错误");
	   	 	}
		}else if(queryFZoneLossListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryFZoneLossListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkFZoneLossListParam(qfzlDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			qfzlDTO.setPage(1);
			qfzlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<FZoneLossListVO>> data = ADOConnection.runTask(zlas, "queryFZoneLossList", PageListVO.class,qfzlDTO);
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
		msg = checkSZoneLossListParam(querySZoneLossListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO data = ADOConnection.runTask(zlas, "querySZoneLossList", PageListVO.class,querySZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	
	/**
	 * 校验二级分区参数
	 * @return
	 */
	public MessageBean checkSZoneLossListParam(QuerySZoneLossListDTO querySZoneLossListDTO,MessageBean<PageListVO> msg){
		if(querySZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(querySZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_D || querySZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(querySZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(querySZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(querySZoneLossListDTO.getStartTime() > querySZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if((StringUtil.isNotEmpty(querySZoneLossListDTO.getMinNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(querySZoneLossListDTO.getMinNrw())) 
   	 			|| (StringUtil.isNotEmpty(querySZoneLossListDTO.getMaxNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(querySZoneLossListDTO.getMaxNrw()))
   	 		|| (StringUtil.isNotEmpty(querySZoneLossListDTO.getMinWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(querySZoneLossListDTO.getMinWl())) 
	 			|| (StringUtil.isNotEmpty(querySZoneLossListDTO.getMaxWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(querySZoneLossListDTO.getMaxWl()))) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("请输入数字类型的数据");
		}else if(StringUtil.isNotEmpty(querySZoneLossListDTO.getMinNrw()) && StringUtil.isNotEmpty(querySZoneLossListDTO.getMaxNrw())) {
			if(Double.parseDouble(querySZoneLossListDTO.getMinNrw()) > Double.parseDouble(querySZoneLossListDTO.getMaxNrw())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("产销差范围输入错误");
	   	 	}
		}else if(StringUtil.isNotEmpty(querySZoneLossListDTO.getMinWl()) && StringUtil.isNotEmpty(querySZoneLossListDTO.getMaxWl())) {
			if(Double.parseDouble(querySZoneLossListDTO.getMinWl()) > Double.parseDouble(querySZoneLossListDTO.getMaxWl())) {
				//漏损量范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("漏损水量范围输入错误");
	   	 	}
		}else if(querySZoneLossListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(querySZoneLossListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkSZoneLossListParam(qszlDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			qszlDTO.setPage(1);
			qszlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<SZoneLossListVO>> data = ADOConnection.runTask(zlas, "querySZoneLossList", PageListVO.class,qszlDTO);
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
		msg = checkDmaZoneLossListParam(QueryDmaZoneLossListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO data = ADOConnection.runTask(zlas, "queryDmaZoneLossList", PageListVO.class,QueryDmaZoneLossListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	/**
	 * 校验dma分区参数
	 * @return
	 */
	public MessageBean checkDmaZoneLossListParam(QueryDmaZoneLossListDTO QueryDmaZoneLossListDTO,MessageBean<PageListVO> msg){
		if(QueryDmaZoneLossListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(QueryDmaZoneLossListDTO.getTimeType() < Constant.TIME_TYPE_M || QueryDmaZoneLossListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(QueryDmaZoneLossListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(QueryDmaZoneLossListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(QueryDmaZoneLossListDTO.getStartTime() > QueryDmaZoneLossListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if((StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMinNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(QueryDmaZoneLossListDTO.getMinNrw())) 
   	 			|| (StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMaxNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(QueryDmaZoneLossListDTO.getMaxNrw()))
   	 		|| (StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMinWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(QueryDmaZoneLossListDTO.getMinWl())) 
	 			|| (StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMaxWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(QueryDmaZoneLossListDTO.getMaxWl()))) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("请输入数字类型的数据");
		}else if(StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMinNrw()) && StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMaxNrw())) {
			if(Double.parseDouble(QueryDmaZoneLossListDTO.getMinNrw()) > Double.parseDouble(QueryDmaZoneLossListDTO.getMaxNrw())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("产销差范围输入错误");
	   	 	}
		}else if(StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMinWl()) && StringUtil.isNotEmpty(QueryDmaZoneLossListDTO.getMaxWl())) {
			if(Double.parseDouble(QueryDmaZoneLossListDTO.getMinWl()) > Double.parseDouble(QueryDmaZoneLossListDTO.getMaxWl())) {
				//漏损量范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("漏损水量范围输入错误");
	   	 	}
		}else if(QueryDmaZoneLossListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(QueryDmaZoneLossListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkDmaZoneLossListParam(qdzlDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			qdzlDTO.setPage(1);
			qdzlDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<DmaZoneLossListVO>> data = ADOConnection.runTask(zlas, "queryDmaZoneLossList", PageListVO.class,qdzlDTO);
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
		}else if(queryZoneInfoDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}else if(queryZoneInfoDTO.getZoneType() != null && (queryZoneInfoDTO.getZoneType() < 1 || queryZoneInfoDTO.getZoneType() > 4)) {
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
		}else if(queryZoneInfoDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}else if(queryZoneInfoDTO.getZoneType() != null && (queryZoneInfoDTO.getZoneType() < 1 || queryZoneInfoDTO.getZoneType() > 4)) {
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
		}else if(queryZoneHstDataDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneHstDataDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(queryZoneHstDataDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(queryZoneHstDataDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(queryZoneHstDataDTO.getZoneNo() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区编号为空");
			return msg.toJson();
		}else if(queryZoneHstDataDTO.getCodes() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("指标编码为空");
			return msg.toJson();
		}else if(queryZoneHstDataDTO.getStartTime() > queryZoneHstDataDTO.getEndTime()) {
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
		msg = checkVSZoneListParam(queryVSZoneListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
//		try{
			PageListVO<List<VSZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVSZoneList", PageListVO.class,queryVSZoneListDTO);
			msg.setData(data);
//    	}catch(Exception e){
//    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
//    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
//    	}
		return msg.toJson();
	}
	
	/**
	 * 校验虚拟分区（相减）参数
	 * @return
	 */
	public MessageBean checkVSZoneListParam(QueryVSZoneListDTO queryVSZoneListDTO,MessageBean<PageListVO> msg){
		if(queryVSZoneListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(queryVSZoneListDTO.getTimeType() < Constant.TIME_TYPE_D || queryVSZoneListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(queryVSZoneListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(queryVSZoneListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(queryVSZoneListDTO.getStartTime() > queryVSZoneListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if((StringUtil.isNotEmpty(queryVSZoneListDTO.getMinNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVSZoneListDTO.getMinNrw())) 
   	 			|| (StringUtil.isNotEmpty(queryVSZoneListDTO.getMaxNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVSZoneListDTO.getMaxNrw()))
   	 		|| (StringUtil.isNotEmpty(queryVSZoneListDTO.getMinWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVSZoneListDTO.getMinWl())) 
	 			|| (StringUtil.isNotEmpty(queryVSZoneListDTO.getMaxWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVSZoneListDTO.getMaxWl()))) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("请输入数字类型的数据");
		}else if(StringUtil.isNotEmpty(queryVSZoneListDTO.getMinNrw()) && StringUtil.isNotEmpty(queryVSZoneListDTO.getMaxNrw())) {
			if(Double.parseDouble(queryVSZoneListDTO.getMinNrw()) > Double.parseDouble(queryVSZoneListDTO.getMaxNrw())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("产销差范围输入错误");
	   	 	}
		}else if(StringUtil.isNotEmpty(queryVSZoneListDTO.getMinWl()) && StringUtil.isNotEmpty(queryVSZoneListDTO.getMaxWl())) {
			if(Double.parseDouble(queryVSZoneListDTO.getMinWl()) > Double.parseDouble(queryVSZoneListDTO.getMaxWl())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("漏损水量范围输入错误");
	   	 	}
		}else if(queryVSZoneListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryVSZoneListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkVSZoneListParam(qvszlDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL); 
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
		msg = checkVCZoneListParam(queryVCZoneListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO<List<VCZoneListVO>> data = ADOConnection.runTask(vzlas, "queryVCZoneList", PageListVO.class,queryVCZoneListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		return msg.toJson();
	}
	
	/**
	 * 校验虚拟分区（合并）参数
	 * @return
	 */
	public MessageBean checkVCZoneListParam(QueryVCZoneListDTO queryVCZoneListDTO,MessageBean<PageListVO> msg){
		if(queryVCZoneListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(queryVCZoneListDTO.getTimeType() < Constant.TIME_TYPE_D || queryVCZoneListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(queryVCZoneListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(queryVCZoneListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(queryVCZoneListDTO.getStartTime() > queryVCZoneListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if((StringUtil.isNotEmpty(queryVCZoneListDTO.getMinNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVCZoneListDTO.getMinNrw())) 
   	 			|| (StringUtil.isNotEmpty(queryVCZoneListDTO.getMaxNrw()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVCZoneListDTO.getMaxNrw()))
   	 		|| (StringUtil.isNotEmpty(queryVCZoneListDTO.getMinWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVCZoneListDTO.getMinWl())) 
	 			|| (StringUtil.isNotEmpty(queryVCZoneListDTO.getMaxWl()) && !com.koron.inwlms.util.StringUtil.isNumeric(queryVCZoneListDTO.getMaxWl()))) {
			//产销差范围输入错误
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("请输入数字类型的数据");
		}else if(StringUtil.isNotEmpty(queryVCZoneListDTO.getMinNrw()) && StringUtil.isNotEmpty(queryVCZoneListDTO.getMaxNrw())) {
			if(Double.parseDouble(queryVCZoneListDTO.getMinNrw()) > Double.parseDouble(queryVCZoneListDTO.getMaxNrw())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("产销差范围输入错误");
	   	 	}
		}else if(StringUtil.isNotEmpty(queryVCZoneListDTO.getMinWl()) && StringUtil.isNotEmpty(queryVCZoneListDTO.getMaxWl())) {
			if(Double.parseDouble(queryVCZoneListDTO.getMinWl()) > Double.parseDouble(queryVCZoneListDTO.getMaxWl())) {
				//产销差范围输入错误
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("漏损水量范围输入错误");
	   	 	}
		}else if(queryVCZoneListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryVCZoneListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
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
			MessageBean msg = checkVCZoneListParam(qvczlDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
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
		//TODO 判断合并分区是否存在
		try{
			ADOConnection.runTask(vzlas,"addVCZone",Void.class,addVCZoneDTO);
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_DELERROR);
			msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteVCZone.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除虚拟分区（合并）", notes = "删除虚拟分区（合并）", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping(value = "/queryZoneIndicatorDic.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区指标数据字典", notes = "查询分区指标数据字典", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryZoneIndicatorDic(Integer zoneType) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		if(zoneType == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
			return msg.toJson();
		}else if(zoneType < 1 || zoneType > 3) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区类型数值错误");
			return msg.toJson();
		}
		try{
			List<ZoneIndicatorDicVO> lists = ADOConnection.runTask(zlas,"queryZoneIndicatorDic",List.class,zoneType);
			msg.setData(lists);
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
		MessageBean<Map> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, Map.class);
		if(zoneThematicValueDTO.getItemCode() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("指标编码为空");
			return msg.toJson();
		}else if(zoneThematicValueDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
			return msg.toJson();
		}else if(zoneThematicValueDTO.getTimeType() < Constant.TIME_TYPE_M || zoneThematicValueDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(zoneThematicValueDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(zoneThematicValueDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(zoneThematicValueDTO.getStartTime() > zoneThematicValueDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
			return msg.toJson();
   	 	}else if(zoneThematicValueDTO.getZoneType() != null && (zoneThematicValueDTO.getZoneType() < Constant.RANK_F || zoneThematicValueDTO.getZoneType() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
			return msg.toJson();
		}
		try{
			Map<String,Map<Object, Object>> data = ADOConnection.runTask(zlas, "queryZoneThematicValue", Map.class,zoneThematicValueDTO);
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
		msg = checkZoneIndicatorListParam(queryZoneIndicatorListDTO,msg);
		if(msg.getCode() != 0) return msg.toJson();
		try{
			PageListVO<List<Map<Object,Object>>> data = ADOConnection.runTask(zlas, "queryZoneIndicatorList", PageListVO.class,queryZoneIndicatorListDTO);
			msg.setData(data);
    	}catch(Exception e){
    		msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
    		msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
    	}
		
		return msg.toJson();
	}
	
	/**
	 * 校验分区指标数据参数
	 * @return
	 */
	public MessageBean checkZoneIndicatorListParam(QueryZoneIndicatorListDTO queryZoneIndicatorListDTO,MessageBean<PageListVO> msg){
		if(queryZoneIndicatorListDTO.getZoneType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("分区类型为空");
		}else if(queryZoneIndicatorListDTO.getTimeType() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("时间粒度为空");
		}else if(queryZoneIndicatorListDTO.getTimeType() < Constant.TIME_TYPE_M || queryZoneIndicatorListDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
		}else if(queryZoneIndicatorListDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
		}else if(queryZoneIndicatorListDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
		}else if(queryZoneIndicatorListDTO.getStartTime() > queryZoneIndicatorListDTO.getEndTime()) {
			//开始时间不能大于结束时间
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("开始时间大于结束时间");
   	 	}else if(queryZoneIndicatorListDTO.getZoneType() != null && (queryZoneIndicatorListDTO.getZoneType() < Constant.RANK_F || queryZoneIndicatorListDTO.getZoneType() > Constant.RANK_T)) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("分区等级数值错误");
		}else if(queryZoneIndicatorListDTO.getPage() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("页数为空");
		}else if(queryZoneIndicatorListDTO.getPageCount() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("每页记录数为空");
		}
		return msg;
	}
	
	@RequestMapping(value = "/downloadZoneIndicatorList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载分区指标数据", notes = "下载分区指标数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadZoneIndicatorList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryZoneIndicatorListDTO qzilDTO = jsonValue.fromJson(objValue, QueryZoneIndicatorListDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (qzilDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			MessageBean msg = checkZoneIndicatorListParam(qzilDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
			if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL); 
			qzilDTO.setPage(1);
			qzilDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageListVO<List<Map<Object,Object>>> data = ADOConnection.runTask(zlas, "queryZoneIndicatorList", PageListVO.class,qzilDTO);
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
		}else if(indicatorDTO.getTimeType() < Constant.TIME_TYPE_M || indicatorDTO.getTimeType() > Constant.TIME_TYPE_Y) {
			//传参数值不正确
			msg.setCode(Constant.MESSAGE_INT_PARAMS);
			msg.setDescription("时间粒度数值错误");
			return msg.toJson();
		}else if(indicatorDTO.getStartTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}else if(indicatorDTO.getEndTime() == null) {
			//参数不正确
			msg.setCode(Constant.MESSAGE_INT_NULL);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}else if(indicatorDTO.getStartTime() > indicatorDTO.getEndTime()) {
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
	 
	 @ApiOperation(value = "更新漏损量指标值", notes = "更新漏损量指标值", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	 @RequestMapping(value = "/updateLeakageExponent.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
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
	     * 分页查询合理夜晚使用量列表信息方法
	     *
	     * @param legitimateNightUseListDTO 滤水厂回用水数据列表查询DTO
	     * @return
	     */
	    @ApiOperation(value = "分页查询合理夜晚使用量列表", notes = "分页查询合理夜晚使用量列表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	    @RequestMapping(value = "/queryLegitimateNightUseList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	    @ResponseBody
	    public String queryLegitimateNightUseList(@RequestBody LegitimateNightUseDTO lnuDTO) {
	    	MessageBean<PageListVO> msg = MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);
	    	msg = checkLegitimateNightUseListParam(lnuDTO,msg);
	    	if(msg.getCode() != 0) return msg.toJson();
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
		 * 校验合理夜晚使用量列表参数
		 * @return
		 */
		public MessageBean checkLegitimateNightUseListParam(LegitimateNightUseDTO lnuDTO,MessageBean<PageListVO> msg){
			if(lnuDTO.getPage() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("页数为空");
			}else if(lnuDTO.getPageCount() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("每页记录数为空");
			}
			return msg;
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
				MessageBean msg = checkLegitimateNightUseListParam(lnuDTO,MessageBean.create(0,Constant.MESSAGE_STRING_SUCCESS, PageListVO.class));
				if(msg.getCode() != 0) return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
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
			}else if(legitimateNightUseEditDTO.getCode() == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("类别编码为空");
				return msg.toJson();
			}else if(legitimateNightUseEditDTO.getCode() == null) {
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
	  
	    @RequestMapping(value = "/queryZoneHstIndicatorDic.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
	    @ApiOperation(value = "分区历史指标数据字典", notes = "分区历史指标数据字典", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	    @ResponseBody
		public String queryZoneHstIndicatorDic(Integer zoneType) {
			MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
			if(zoneType == null) {
				//参数不正确
				msg.setCode(Constant.MESSAGE_INT_NULL);
				msg.setDescription("分区类型为空");
				return msg.toJson();
			}else if(zoneType < 1 || zoneType > 4) {
				//传参数值不正确
				msg.setCode(Constant.MESSAGE_INT_PARAMS);
				msg.setDescription("分区类型数值错误");
				return msg.toJson();
			}
			try{
				List<ZoneIndicatorDicVO> lists = ADOConnection.runTask(zlas,"queryZoneHstIndicatorDic",List.class,zoneType);
				msg.setData(lists);
			}catch(Exception e){
				msg.setCode(Constant.MESSAGE_INT_DELERROR);
				msg.setDescription(Constant.MESSAGE_STRING_DELERROR);
			}
			return msg.toJson();
			
		}
}
