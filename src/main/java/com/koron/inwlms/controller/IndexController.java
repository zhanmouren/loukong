package com.koron.inwlms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.indexData.MultParamterIndicatorDTO;
import com.koron.inwlms.bean.DTO.indexData.WarningInfoDTO;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.indexData.InfoPageListVO;
import com.koron.inwlms.bean.VO.indexData.MultParamterIndicatorVO;
import com.koron.inwlms.service.indexData.IndexService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页Controller层
 * @author xiaozhan
 * @Date 2020.03.18
 */

@RestController
@Api(value = "indexManager", description = "首页管理Controller")
@RequestMapping(value = "/indexManager")
public class IndexController {
	

	@Autowired
	private IndexService indexService;
     
	 /*
     * date:2020-04-23
     * funtion:显示分区或全网综合信息功能
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryCompreInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "显示分区或全网综合信息功能接口", notes = "显示分区或全网综合信息功能接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryCompreInfo(@RequestBody IndicatorDTO indicatorDTO) {
		if(indicatorDTO.getStartTime()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间为空", Integer.class).toJson();
		}
		if(indicatorDTO.getEndTime()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间为空", Integer.class).toJson();
		}
		if(indicatorDTO.getZoneCodes()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码为空", Integer.class).toJson();
		}
		if(indicatorDTO.getZoneCodes().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<IndicatorVO> indicatorVOList=ADOConnection.runTask(indexService, "queryCompreInfo", List.class,indicatorDTO);
			  //创建虚拟数据
			  IndicatorVO indicatorVOA1=new IndicatorVO();
			  indicatorVOA1.setCode("0001");
			  indicatorVOA1.setTimeId(2);
			  indicatorVOA1.setType("A1");
			  indicatorVOA1.setValue(5500.00);
			  indicatorVOA1.setZoneNo("00001");
			  IndicatorVO indicatorVOA2=new IndicatorVO();
			  indicatorVOA2.setCode("0001");
			  indicatorVOA2.setTimeId(2);
			  indicatorVOA2.setType("A2");
			  indicatorVOA2.setValue(13829.00);
			  indicatorVOA2.setZoneNo("00001");
			  IndicatorVO indicatorVOB1=new IndicatorVO();
			  indicatorVOB1.setCode("0001");
			  indicatorVOB1.setTimeId(2);
			  indicatorVOB1.setType("B1");
			  indicatorVOB1.setValue(5200.00);
			  indicatorVOB1.setZoneNo("00001");
			  IndicatorVO indicatorVOB2=new IndicatorVO();
			  indicatorVOB2.setCode("0001");
			  indicatorVOB2.setTimeId(2);
			  indicatorVOB2.setType("B2");
			  indicatorVOB2.setValue(3500.00);
			  indicatorVOB2.setZoneNo("00001");
			  IndicatorVO indicatorVOC1=new IndicatorVO();
			  indicatorVOC1.setCode("0001");
			  indicatorVOC1.setTimeId(2);
			  indicatorVOC1.setType("C1");
			  indicatorVOC1.setValue(5400.00);
			  indicatorVOC1.setZoneNo("00001");
			  IndicatorVO indicatorVOC2=new IndicatorVO();
			  indicatorVOC2.setCode("0001");
			  indicatorVOC2.setTimeId(2);
			  indicatorVOC2.setType("C2");
			  indicatorVOC2.setValue(500.00);
			  indicatorVOC2.setZoneNo("00001");
			  IndicatorVO indicatorVOD1=new IndicatorVO();
			  indicatorVOD1.setCode("0001");
			  indicatorVOD1.setTimeId(2);
			  indicatorVOD1.setType("D1");
			  indicatorVOD1.setValue(5000.00);
			  indicatorVOD1.setZoneNo("00001");
			  IndicatorVO indicatorVOD2=new IndicatorVO();
			  indicatorVOD2.setCode("0001");
			  indicatorVOD2.setTimeId(2);
			  indicatorVOD2.setType("D2");
			  indicatorVOD2.setValue(5500.00);
			  indicatorVOD2.setZoneNo("00001");
			  IndicatorVO indicatorVOD3=new IndicatorVO();
			  indicatorVOD3.setCode("0001");
			  indicatorVOD3.setTimeId(2);
			  indicatorVOD3.setType("D3");
			  indicatorVOD3.setValue(5500.00);
			  indicatorVOA1.setZoneNo("00001");
			  IndicatorVO indicatorVOD4=new IndicatorVO();
			  indicatorVOD4.setCode("0001");
			  indicatorVOD4.setTimeId(2);
			  indicatorVOD4.setType("D4");
			  indicatorVOD4.setValue(5500.00);
			  indicatorVOD4.setZoneNo("00001");
			  IndicatorVO indicatorVOE1=new IndicatorVO();
			  indicatorVOE1.setCode("0001");
			  indicatorVOE1.setTimeId(2);
			  indicatorVOE1.setType("E1");
			  indicatorVOE1.setValue(5500.00);
			  indicatorVOE1.setZoneNo("00001");
			  IndicatorVO indicatorVOE2=new IndicatorVO();
			  indicatorVOE2.setCode("0001");
			  indicatorVOE2.setTimeId(2);
			  indicatorVOE2.setType("E2");
			  indicatorVOE2.setValue(5500.00);
			  indicatorVOE2.setZoneNo("00001");
			  IndicatorVO indicatorVOE3=new IndicatorVO();
			  indicatorVOE3.setCode("0001");
			  indicatorVOE3.setTimeId(2);
			  indicatorVOE3.setType("E3");
			  indicatorVOE3.setValue(5500.00);
			  indicatorVOE3.setZoneNo("00001");
			  IndicatorVO indicatorVOE4=new IndicatorVO();
			  indicatorVOE4.setCode("0001");
			  indicatorVOE4.setTimeId(2);
			  indicatorVOE4.setType("E4");
			  indicatorVOE4.setValue(5500.00);
			  indicatorVOE4.setZoneNo("00001");
			  indicatorVOList.add(indicatorVOA1);
			  indicatorVOList.add(indicatorVOA2);
			  indicatorVOList.add(indicatorVOB1);
			  indicatorVOList.add(indicatorVOB2);
			  indicatorVOList.add(indicatorVOC1);
			  indicatorVOList.add(indicatorVOC2);
			  indicatorVOList.add(indicatorVOD1);
			  indicatorVOList.add(indicatorVOD2);
			  indicatorVOList.add(indicatorVOD3);
			  indicatorVOList.add(indicatorVOD4);
			  indicatorVOList.add(indicatorVOE1);
			  indicatorVOList.add(indicatorVOE2);
			  indicatorVOList.add(indicatorVOE3);
			  indicatorVOList.add(indicatorVOE4);
			  
				  if(indicatorVOList.size()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询指标数据成功");
				    msg.setData(indicatorVOList);
				  }
				  else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关数据");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	/*
     * date:2020-04-23
     * function:时间段查询指标
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryComYearInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "时间段查询指标接口", notes = "时间段查询指标接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryComYearInfo(@RequestBody IndicatorNewDTO indicatorDTO) {
		 if(indicatorDTO.getStartTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		 }
		 if(indicatorDTO.getEndTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		 }
		 if(indicatorDTO.getZoneCodes()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(indicatorDTO.getZoneCodes().size()<1) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(indicatorDTO.getType()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "类型不能为空", Integer.class).toJson(); 
		 }
		 
		 MessageBean<MultParamterIndicatorVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, MultParamterIndicatorVO.class);	       
		  try{
			  MultParamterIndicatorVO multParamterIndicator=ADOConnection.runTask(indexService, "queryComYearInfo", MultParamterIndicatorVO.class, indicatorDTO);	
			  //制造假数据
			  List<IndicatorVO> curList=new  ArrayList<IndicatorVO>();
			  IndicatorVO indicatorVO01=new IndicatorVO();
			  indicatorVO01.setCode("0001");
			  indicatorVO01.setTimeId(2);
			  indicatorVO01.setValue(5500.00);
			  indicatorVO01.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO02=new IndicatorVO();
			  indicatorVO02.setCode("0001");
			  indicatorVO02.setTimeId(2);
			  indicatorVO02.setValue(5300.00);
			  indicatorVO02.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO03=new IndicatorVO();
			  indicatorVO03.setCode("0001");
			  indicatorVO03.setTimeId(2);
			  indicatorVO03.setValue(1500.00);
			  indicatorVO03.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO04=new IndicatorVO();
			  indicatorVO04.setCode("0001");
			  indicatorVO04.setTimeId(2);
			  indicatorVO04.setValue(6500.00);
			  indicatorVO04.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO05=new IndicatorVO();
			  indicatorVO05.setCode("0001");
			  indicatorVO05.setTimeId(2);
			  indicatorVO05.setValue(5500.00);
			  indicatorVO05.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO06=new IndicatorVO();
			  indicatorVO06.setCode("0001");
			  indicatorVO06.setTimeId(2);
			  indicatorVO06.setValue(5500.00);
			  indicatorVO06.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO07=new IndicatorVO();
			  indicatorVO07.setCode("0001");
			  indicatorVO07.setTimeId(2);
			  indicatorVO07.setValue(9500.00);
			  indicatorVO07.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO08=new IndicatorVO();
			  indicatorVO08.setCode("0001");
			  indicatorVO08.setTimeId(2);;
			  indicatorVO08.setValue(5500.00);
			  indicatorVO08.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO09=new IndicatorVO();
			  indicatorVO09.setCode("0001");
			  indicatorVO09.setTimeId(2);
			  indicatorVO09.setValue(4500.00);
			  indicatorVO09.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO10=new IndicatorVO();
			  indicatorVO10.setCode("0001");
			  indicatorVO10.setTimeId(2);
			  indicatorVO10.setValue(5500.00);
			  indicatorVO10.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO11=new IndicatorVO();
			  indicatorVO11.setCode("0001");
			  indicatorVO11.setTimeId(2);
			  indicatorVO11.setValue(5500.00);
			  indicatorVO11.setZoneNo("00001");
			  
			  IndicatorVO indicatorVO12=new IndicatorVO();
			  indicatorVO12.setCode("0001");
			  indicatorVO12.setTimeId(2);
			  indicatorVO12.setValue(5500.00);
			  indicatorVO12.setZoneNo("00001");
			  curList.add(indicatorVO01);
			  curList.add(indicatorVO02);
			  curList.add(indicatorVO03);
			  curList.add(indicatorVO04);
			  curList.add(indicatorVO05);
			  curList.add(indicatorVO06);
			  curList.add(indicatorVO07);
			  curList.add(indicatorVO08);
			  curList.add(indicatorVO09);
			  curList.add(indicatorVO10);
			  curList.add(indicatorVO11);				  
			  curList.add(indicatorVO12);	
			  
			  multParamterIndicator.setCurrentIndicatorList(curList);
			  
			//制造假数据
			  List<IndicatorVO> nowList=new  ArrayList<IndicatorVO>();
			  IndicatorVO indicatorVON01=new IndicatorVO();
			  indicatorVON01.setCode("0001");
			  indicatorVON01.setTimeId(2);
			  indicatorVON01.setValue(5500.00);
			  indicatorVON01.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON02=new IndicatorVO();
			  indicatorVON02.setCode("0001");
			  indicatorVON02.setTimeId(2);
			  indicatorVON02.setValue(5300.00);
			  indicatorVON02.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON03=new IndicatorVO();
			  indicatorVON03.setCode("0001");
			  indicatorVON03.setTimeId(2);
			  indicatorVON03.setValue(1500.00);
			  indicatorVON03.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON04=new IndicatorVO();
			  indicatorVON04.setCode("0001");
			  indicatorVON04.setTimeId(2);
			  indicatorVON04.setValue(6500.00);
			  indicatorVON04.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON05=new IndicatorVO();
			  indicatorVON05.setCode("0001");
			  indicatorVON05.setTimeId(2);
			  indicatorVON05.setValue(5500.00);
			  indicatorVON05.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON06=new IndicatorVO();
			  indicatorVON06.setCode("0001");
			  indicatorVON06.setTimeId(2);
			  indicatorVON06.setValue(5500.00);
			  indicatorVON06.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON07=new IndicatorVO();
			  indicatorVON07.setCode("0001");
			  indicatorVON07.setTimeId(2);
			  indicatorVON07.setValue(9500.00);
			  indicatorVON07.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON08=new IndicatorVO();
			  indicatorVON08.setCode("0001");
			  indicatorVON08.setTimeId(2);;
			  indicatorVON08.setValue(5500.00);
			  indicatorVON08.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON09=new IndicatorVO();
			  indicatorVON09.setCode("0001");
			  indicatorVON09.setTimeId(2);
			  indicatorVON09.setValue(4500.00);
			  indicatorVON09.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON10=new IndicatorVO();
			  indicatorVON10.setCode("0001");
			  indicatorVON10.setTimeId(2);
			  indicatorVON10.setValue(5500.00);
			  indicatorVON10.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON11=new IndicatorVO();
			  indicatorVON11.setCode("0001");
			  indicatorVON11.setTimeId(2);
			  indicatorVON11.setValue(5500.00);
			  indicatorVON11.setZoneNo("00001");
			  
			  IndicatorVO indicatorVON13=new IndicatorVO();
			  indicatorVON13.setCode("0001");
			  indicatorVON13.setTimeId(2);
			  indicatorVON13.setValue(5500.00);
			  indicatorVON13.setZoneNo("00001");
			  nowList.add(indicatorVON01);
			  nowList.add(indicatorVON02);
			  nowList.add(indicatorVON03);
			  nowList.add(indicatorVON04);
			  nowList.add(indicatorVON05);
			  nowList.add(indicatorVON06);
			  nowList.add(indicatorVON07);
			  nowList.add(indicatorVON08);
			  nowList.add(indicatorVON09);
			  nowList.add(indicatorVON10);
			  nowList.add(indicatorVON11);
			  nowList.add(indicatorVON13);
			  multParamterIndicator.setLastIndicatorList(nowList);
			  
				  if(multParamterIndicator!=null) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询指标数据成功");
				    msg.setData(multParamterIndicator);
				  }
				  else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询指标数据失败");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-23
     * function:查询漏损任务相关信息
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryWarningInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询漏损任务相关信息接口", notes = "查询漏损任务相关信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryWarningInfo(@RequestBody WarningInfoDTO warningInfoDTO) {
		 if(warningInfoDTO.getAreaZoneList()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(warningInfoDTO.getAreaZoneList().size()<1) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();  
		 }
		 if(warningInfoDTO.getTaskCreateTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "日期不能为空", Integer.class).toJson();   
		 }
		 MessageBean<InfoPageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, InfoPageListVO.class);	       
		  try{
			  InfoPageListVO infoPageListVO=ADOConnection.runTask(indexService, "queryWarningInfo", InfoPageListVO.class, warningInfoDTO);		 
				  if(infoPageListVO!=null && infoPageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询漏损任务信息成功");
				    msg.setData(infoPageListVO);
				  }
				  else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没查询到相关漏损任务信息");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-23
     * function:查询检测点报警信息
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryCheckWarningInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询检测点报警信息接口", notes = "查询检测点报警信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryCheckWarningInfo(@RequestBody WarningInfoDTO warningInfoDTO) {
		 if(warningInfoDTO.getAreaZoneList()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(warningInfoDTO.getAreaZoneList().size()<1) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson();  
		 }
		 if(warningInfoDTO.getTaskCreateTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "日期不能为空", Integer.class).toJson();   
		 }
		 MessageBean<InfoPageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, InfoPageListVO.class);	       
		  try{
			  InfoPageListVO infoPageListVO=ADOConnection.runTask(indexService, "queryCheckWarningInfo", InfoPageListVO.class, warningInfoDTO);		 
				  if(infoPageListVO!=null && infoPageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询监测点信息成功");
				    msg.setData(infoPageListVO);
				  }
				  else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没查询到监测点信息");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-29
     * function:查询分区排名
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryAreaRankInfo.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询分区下各个分区排名接口", notes = "查询分区下各个分区排名接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryAreaRankInfo(@RequestBody IndicatorNewDTO indicatorDTO) {
		 if(indicatorDTO.getStartTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "开始时间不能为空", Integer.class).toJson();
		 }
		 if(indicatorDTO.getEndTime()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "结束时间不能为空", Integer.class).toJson();
		 }
		 if(indicatorDTO.getZoneCodes()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(indicatorDTO.getZoneCodes().size()<1) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "地区编码不能为空", Integer.class).toJson(); 
		 }
		 if(indicatorDTO.getType()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "类型不能为空", Integer.class).toJson(); 
		 }
		 
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<IndicatorVO>  infoPageListList=ADOConnection.runTask(indexService, "queryAreaRankInfo", List.class,indicatorDTO);		 
				  if(infoPageListList!=null && infoPageListList.size()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询分区下各个分区排名接口成功");
				    msg.setData(infoPageListList);
				  }
				  else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没查询到分区下各个分区排名接口信息");
				  }
			  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	 
	
}
