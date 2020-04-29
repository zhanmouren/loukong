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
