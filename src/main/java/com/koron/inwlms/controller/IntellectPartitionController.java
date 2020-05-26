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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZoneData;
import com.koron.inwlms.bean.DTO.intellectPartition.GisZonePipeData;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.VO.intellectPartition.ModelReturn;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.intellectPartition.TotalSchemeDet;
import com.koron.inwlms.service.intellectPartition.PartitionSchemeDetService;
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
@RequestMapping(value = "/intellectPartitionController")
public class IntellectPartitionController {
	@Autowired
	private PartitionSchemeDetService psds;

	/**
	 * 智能自动分区接口
	 */
	@RequestMapping(value = "/automaticPartition.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "智能自动分区接口", notes = "智能自动分区接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String automaticPartition(@RequestBody AutomaticPartitionDTO automaticPartitionDTO) {
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
			ModelReturn data = ADOConnection.runTask(psds, "test", ModelReturn.class, automaticPartitionDTO, totalSchemeDet);
			msg.setData(data);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("智能分区数据传输失败！");
		}
		
		return msg.toJson();
	}

	
	@RequestMapping(value = "/queryTotalSchemeDet.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询方案总表数据", notes = "查询方案总表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryTotalSchemeDet(@RequestBody TotalSchemeDetDTO totalSchemeDetDTO) {
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
			List<TotalSchemeDet> list = ADOConnection.runTask(psds, "queryTotalSchemeDet", List.class,totalSchemeDetDTO);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(list);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表数据查询失败");
		}
		
		return msg.toJson();
	}
	
	/**
	 * 通过方案总表code查询分区方案数据
	 * @return
	 */
	@RequestMapping(value = "/querySchemeDet.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区方案数据", notes = "查询分区方案数据", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String querySchemeDet(String totalSchemeCode) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(totalSchemeCode == null || totalSchemeCode.equals("")) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			List<SchemeDet> list = ADOConnection.runTask(psds, "querySchemeDet", List.class,totalSchemeCode);
			msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			msg.setData(list);
			
		}catch(Exception e) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("分区方案查询失败");
		}
		
		return msg.toJson();
	}
	
	@RequestMapping(value = "/deleteSchemeDet.htm", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除分区方案数据", notes = "删除分区方案数据", httpMethod = "GET", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String deleteSchemeDet(List<Integer> ids) {
		MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);
		
		try {
			Integer num = ADOConnection.runTask(psds, "deleteSchemeDetByCode", Integer.class,ids);
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
    public String deleteTotalSchemeDet(@RequestBody List<String> codes) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(codes == null || codes.size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(psds, "deleteTotalSchemeDet", Integer.class,  codes);
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
    public String changeTotalSchemeDet(@RequestBody List<String> codes) {
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		
		if(codes == null || codes.size() == 0) {
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("方案总表编码为空");
			return msg.toJson();
		}
		
		try {
			Integer num = ADOConnection.runTask(psds, "changeSchemeDet", Integer.class,  codes);
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
