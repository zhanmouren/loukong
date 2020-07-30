package com.koron.inwlms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.koron.common.StaffAttribute;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.report.waterBalanceReport.WB1BalanceDTO;
import com.koron.inwlms.bean.DTO.sysManager.TreeDTO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB1BalanceVO;
import com.koron.inwlms.bean.VO.report.waterBalanceReport.WB2OneZoneVO;
import com.koron.inwlms.bean.VO.sysManager.TreeDeptVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.report.waterReport.WaterReportService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 统计报表-水平衡报表controller
 * @author xiaozhan
 * @Date 2020.07.22
 */
@RestController
@Api(value = "report", description = "水平衡报表管理Controller")
@RequestMapping(value = "/{tenantID}/report")
public class WaterReportController {
	
	@Autowired
	private WaterReportService waterReportService;
	 /*
     * date:2020-07.22
     * function:报表：(WB_01)水司及一级分区产销差率同比报表      以月为时间间隔,汇总分析所选运作区或全网在指定时间范围内用水量、产销差和未计量食水用水量。
     * author:xiaozhan
     */

	@RequestMapping(value = "/queryPartitionData.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "水司及一级分区产销差率同比报表", notes = "水司及一级分区产销差率同比报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryPartitionData(@RequestBody IndicatorNewDTO indicatorNewDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(indicatorNewDTO.getStartTime()==null || "".equals(indicatorNewDTO.getStartTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getEndTime()==null || "".equals(indicatorNewDTO.getEndTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes()==null) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes().size()<1) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();
		}
		Gson gson=new Gson();
		Map<String,Object>    resultMap=new HashMap<>();	
		// MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       		
		  try{
			  Map<String,Object>  WB1BalanceVO=ADOConnection.runTask(user.getEnv(),waterReportService, "queryPartitionData", Map.class, indicatorNewDTO);		 
				  if(WB1BalanceVO!=null ) {			
//				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
//				    msg.setDescription("查询水司及一级分区产销差率同比信息成功");
//				    msg.setData(WB1BalanceVOList);
					resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "查询水司及一级分区产销差率同比信息成功");
					resultMap.put("data", WB1BalanceVO);	
				  }
				  else {				   
//			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
//			        msg.setDescription("未查询到水司及一级分区产销差率同比信息");
					resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "未查询到水司及一级分区产销差率同比信息");					
				  }
			  
	        }catch(Exception e){
//	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
//	            msg.setDescription("查询失败");
	        	resultMap.put("code", Constant.MESSAGE_INT_ERROR);
				resultMap.put("description", "查询失败");	
	        }
		
//	     return msg.toJson();
		  return gson.toJson(resultMap);
	}
	
	/*
     * date:2020-07-27
     * funtion:查看分区树结构下级(一级分区)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryTreeOneZone.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看分区树结构下级(一级分区)", notes = "查看分区树结构下级(一级分区)", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryTreeOneZone(@RequestBody TreeDTO treeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(treeDTO.getType()==null || "".equals(treeDTO.getType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(treeDTO.getForeignKey()==null || "".equals(treeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		if(treeDTO.getAllFlag()==null || "".equals(treeDTO.getAllFlag())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "是否包含全网不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeDeptVO> zoneBeanList=ADOConnection.runTask(user.getEnv(),waterReportService, "queryTreeOneZone", List.class,treeDTO.getType(),treeDTO.getForeignKey(),treeDTO.getAllFlag());	
			  if(zoneBeanList == null || zoneBeanList.size()<1) {		
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			      msg.setDescription("没有查询到相关分区树结构"); 
			  }else {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
				  msg.setDescription("查询分区树结构成功"); 
				  msg.setData(zoneBeanList);
			  }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-07.22
     * function:报表：(WB_02)以月为时间间隔，对比指定时间段内运作区在产销差和未计量食水用水量的表现。
     * author:xiaozhan
     */

	@RequestMapping(value = "/queryOneZoneCXC.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "一级分区产销差率比较报表", notes = "一级分区产销差率比较报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryOneZoneCXC(@RequestBody IndicatorNewDTO indicatorNewDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(indicatorNewDTO.getStartDate() == null || indicatorNewDTO.getStartDate().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getEndDate() == null || indicatorNewDTO.getEndDate().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		}
		  Gson gson=new Gson();
		  Map<String,Object>    resultMap=new HashMap<>();	   		
		  try{
			  List<Map<String,Object>>   WB2OneZoneVO=ADOConnection.runTask(user.getEnv(),waterReportService, "queryOneZoneCXC", List.class, indicatorNewDTO);		 
				  if(WB2OneZoneVO!=null) {							    
				    resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "查询一级分区产销差率比较报表成功");
					resultMap.put("data", WB2OneZoneVO);	
				  }
				  else {				   			      
			        resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "未查询到一级分区产销差率比较报表信息");
				  }
			  
	        }catch(Exception e){        	
	            resultMap.put("code", Constant.MESSAGE_INT_ERROR);
				resultMap.put("description", "查询失败");
	        }
		
	     //return msg.toJson();
		  return gson.toJson(resultMap);
	}
	
	/*
     * date:2020-07.28
     * function:报表：(WB_03)二级分区水平衡报表 展示所选区域范围内各食水供水区在指定时间段的漏损分析数据。
     * author:xiaozhan
     */

	@RequestMapping(value = "/queryTwoZoneWater.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "二级分区水平衡报表", notes = "二级分区水平衡报表", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryTwoZoneWater(@RequestBody IndicatorNewDTO indicatorNewDTO,@SPIAccountAnno @StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(indicatorNewDTO.getStartTime()==null || "".equals(indicatorNewDTO.getStartTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getEndTime()==null || "".equals(indicatorNewDTO.getEndTime())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes()==null) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();
		}
		if(indicatorNewDTO.getZoneCodes().size()<1) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();
		}
		Gson gson=new Gson();
		  Map<String,Object>    resultMap=new HashMap<>();	
		 // MessageBean<Map> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Map.class);	       
		  try{
			  Map<String, Object> WB2OneZoneVO=ADOConnection.runTask(user.getEnv(),waterReportService, "queryTwoZoneWater", Map.class, indicatorNewDTO);		 
				  if(WB2OneZoneVO!=null) {		
					resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "查询二级分区水平衡报表成功");
					resultMap.put("data", WB2OneZoneVO);	
//					    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
//					    msg.setDescription("查询二级分区水平衡报表成功");
//					    msg.setData(WB2OneZoneVO);
				  }
				  else {	
					resultMap.put("code", Constant.MESSAGE_INT_SUCCESS);
					resultMap.put("description", "未查询到二级分区水平衡报表信息");	
//					   msg.setCode(Constant.MESSAGE_INT_SUCCESS);
//					   msg.setDescription("未查询到二级分区水平衡报表信息");
				  }
			  
	        }catch(Exception e){
	        	resultMap.put("code", Constant.MESSAGE_INT_ERROR);
				resultMap.put("description", "查询失败");
//	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
//	            msg.setDescription("查询失败");
	        }
		
	    // return msg.toString();
		 return gson.toJson(resultMap);
	}
}
