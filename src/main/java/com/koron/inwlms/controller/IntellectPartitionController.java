package com.koron.inwlms.controller;

import java.util.List;
import java.util.Map;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZoneData;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZonePipeData;
import com.koron.inwlms.bean.DTO.intellectPartition.SchemeParamDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.intellectPartition.ModelReturn;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.ZoneRange;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListReturnVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.intellectPartition.PartitionSchemeDetService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.InterfaceUtil;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
/**
 * 智能分区Controller层
 * @author 刘刚
 *
 */
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "intellectPartitionController", description = "智能分区Controller")
@RequestMapping(value = "/{tenantID}/intellectPartitionController")
public class IntellectPartitionController {
	@Autowired
	private PartitionSchemeDetService psds;

	/**
	 * 智能自动分区接口
	 */
	@RequestMapping(value = "/automaticPartition.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "智能自动分区接口", notes = "智能自动分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String automaticPartition(@RequestBody AutomaticPartitionDTO automaticPartitionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user,@PathVariable("tenantID") String tenantID) {
		MessageBean<ModelReturn> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ModelReturn.class);
		
		
		//接收到信号，开始存储方案总表信息
		TotalSchemeDet totalSchemeDet = new TotalSchemeDet();
		totalSchemeDet.setAmbientLayer(automaticPartitionDTO.getAmbientLayer());
		totalSchemeDet.setFlowLayer(automaticPartitionDTO.getFlowLayer());
		totalSchemeDet.setMaxZone(automaticPartitionDTO.getMaxZone());
		totalSchemeDet.setMinZone(automaticPartitionDTO.getMinZone());
		totalSchemeDet.setZoneCode(automaticPartitionDTO.getZoneCode());
		totalSchemeDet.setZoneType(automaticPartitionDTO.getZoneType());
		totalSchemeDet.setZoneGrade(automaticPartitionDTO.getZoneGrade());
		try {
			ModelReturn data = ADOConnection.runTask(user.getEnv(),psds, "getModelReturnData", ModelReturn.class, automaticPartitionDTO, totalSchemeDet,tenantID);
			msg.setData(data);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("智能分区数据传输失败！");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/getZoneRange.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取自动分区范围接口", notes = "获取自动分区范围接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getZoneRange(@RequestBody AutomaticPartitionDTO automaticPartitionDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<ZoneRange> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ZoneRange.class);
		
		if(automaticPartitionDTO.getZoneCode() == null || automaticPartitionDTO.getZoneCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区 编码为空");
			return msg.toJson();
		}
		
		if(automaticPartitionDTO.getZoneType() == null ) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区 类型为空");
			return msg.toJson();
		}
		
		try {
			ZoneRange data = ADOConnection.runTask(user.getEnv(),psds, "getZoneNum", ZoneRange.class, automaticPartitionDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(data);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区范围查询失败");
		}
		
		return msg.toJson();
	}

	
	@RequestMapping(value = "/queryTotalSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询方案总表数据", notes = "查询方案总表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryTotalSchemeDet(@RequestBody TotalSchemeDetDTO totalSchemeDetDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(totalSchemeDetDTO.getStartTime() == null || totalSchemeDetDTO.getStartTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("开始时间为空");
			return msg.toJson();
		}
		if(totalSchemeDetDTO.getEndTime() == null || totalSchemeDetDTO.getEndTime().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("结束时间为空");
			return msg.toJson();
		}
		
		try {
			List<TotalSchemeDet> list = ADOConnection.runTask(user.getEnv(),psds, "queryTotalSchemeDet", List.class,totalSchemeDetDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(list);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表数据查询失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downTotalSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载分区总方案列表数据", notes = "下载分区总方案列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downTotalSchemeDet(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			TotalSchemeDetDTO totalSchemeDetDTO = jsonValue.fromJson(objValue, TotalSchemeDetDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (totalSchemeDetDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			totalSchemeDetDTO.setPage(1);
			totalSchemeDetDTO.setPageCount(Constant.DOWN_MAX_LIMIT); 
			// 查询到导出数据结果
			List<TotalSchemeDet> list = ADOConnection.runTask(user.getEnv(),psds, "queryTotalSchemeDet", List.class,totalSchemeDetDTO);
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过方案总表code查询分区方案数据
	 * @return
	 */
	@RequestMapping(value = "/querySchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区方案数据", notes = "查询分区方案数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String querySchemeDet(@RequestBody TotalSchemeDetDTO totalSchemeDetDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(totalSchemeDetDTO.getCode() == null || totalSchemeDetDTO.getCode().equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			List<SchemeDet> list = ADOConnection.runTask(user.getEnv(),psds, "querySchemeDet", List.class,totalSchemeDetDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(list);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区方案查询失败"); 
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/downSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载分区总方案列表数据", notes = "下载分区总方案列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpEntity<?> downSchemeDet(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			TotalSchemeDetDTO totalSchemeDetDTO = jsonValue.fromJson(objValue, TotalSchemeDetDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if (totalSchemeDetDTO == null) {
				return new HttpEntity<Integer>(Constant.MESSAGE_INT_NULL);
			}
			totalSchemeDetDTO.setPage(1);
			totalSchemeDetDTO.setPageCount(Constant.DOWN_MAX_LIMIT); 
			// 查询到导出数据结果
			List<SchemeDet> list = ADOConnection.runTask(user.getEnv(),psds, "querySchemeDet", List.class,totalSchemeDetDTO.getCode());
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(list, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除分区方案数据", notes = "删除分区方案数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteSchemeDet(@RequestBody SchemeParamDTO schemeParamDto,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		try {
			Integer num = ADOConnection.runTask(user.getEnv(),psds, "deleteSchemeDetByCode", Integer.class,schemeParamDto.getIds());
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区方案删除失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteTotalSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "方案删除接口", notes = "方案删除接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteTotalSchemeDet(@RequestBody SchemeParamDTO schemeParamDto,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(schemeParamDto.getCodes() == null || schemeParamDto.getCodes().size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(user.getEnv(),psds, "deleteTotalSchemeDet", Integer.class,  schemeParamDto.getCodes());
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e){
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案删除失败");
		}
		return msg.toJson();
	}
	
	@RequestMapping(value = "/changeTotalSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "自动智能分区方案保存接口", notes = "自动智能分区方案保存接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String changeTotalSchemeDet(@RequestBody SchemeParamDTO schemeParamDto,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(schemeParamDto.getCodes() == null || schemeParamDto.getCodes().size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(user.getEnv(),psds, "changeSchemeDet", Integer.class,  schemeParamDto.getCodes());
			if(num > 0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("方案保存成功");
			}else {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("无方案保存");
			}
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案保存失败");
		}
		
		
		return msg.toJson();
	}
	
	
}
