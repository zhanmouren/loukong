package com.koron.inwlms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.swan.bean.MessageBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryIntegrationLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryOperateLogDTO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationLogVO;
import com.koron.inwlms.bean.VO.sysManager.LoginLogVO;
import com.koron.inwlms.bean.VO.sysManager.OperateLogVO;
import com.koron.inwlms.bean.VO.sysManager.PageIntegrationLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageLoginLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageOperateLogListVO;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 日志管理Controller层
 * @author lzy
 * @Date 2020.03.30
 */

@RestController
@Api(value = "systemManagerLog",description = "日志管理Controller")
@RequestMapping(value = "/systemManagerLog")
public class SystemManagerLogController {

	@Autowired
	private LogService logService;
	
	
	/*
	 * 查询登录日志接口
     * date:2020-03-30
     */  
	@RequestMapping(value = "/querySysLoginLog.htm",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询登录日志接口", notes = "查询登录日志接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryLoginLog(@RequestBody QueryLoginLogDTO 	queryLoginLogDTO) {
		if(queryLoginLogDTO.getPage() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!页码不能为空", Integer.class).toJson();
		}
		if(queryLoginLogDTO.getPageCount() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!每页数量不能为空", Integer.class).toJson();
		}
		if(queryLoginLogDTO.getStartTime() == null || queryLoginLogDTO.getStartTime().equals("")) {
	        return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryLoginLogDTO.getStartTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		if(queryLoginLogDTO.getEndTime() == null || queryLoginLogDTO.getEndTime().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryLoginLogDTO.getEndTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		int res = queryLoginLogDTO.getStartTime().compareTo(queryLoginLogDTO.getEndTime());
		if(res > 0) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间大于结束时间", Integer.class).toJson();
		}
		
		if(queryLoginLogDTO.getType()==null || StringUtils.isBlank(queryLoginLogDTO.getType())) {
			
		}else if(queryLoginLogDTO.getType().equals("全部")) {
			queryLoginLogDTO.setType(null);
		}else if(!queryLoginLogDTO.getType().equals("登入") && !queryLoginLogDTO.getType().equals("登出")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作类型必须是“全部、登入或登出”", Integer.class).toJson();
		} 
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询登录日志 
		try {
			List<LoginLogVO> loginLogList=ADOConnection.runTask(logService, "queryLoginLog", List.class, queryLoginLogDTO);
			if(loginLogList.size()>0) {
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("查询到相关日志的信息"); 
				msg.setData(loginLogList);
			}else {
				//没查询到数据
				msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				msg.setDescription("没有查询到相关日志的信息"); 
			}
		}catch(Exception e){
			//查询失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("查询日志失败");
		}
		return msg.toJson();
	}
	
	/*
     * 添加登录日志接口
     * date:2020-03-31
     */  
	@RequestMapping(value = "/addSysLoginLog.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "添加登录日志接口",notes = "添加登录日志接口", httpMethod = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addLoginLog(@RequestBody LoginLogDTO loginLogDTO) {
		MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);
		try {
			Integer addRes = ADOConnection.runTask(logService, "addLoginLog",Integer.class,loginLogDTO);
			if(addRes!=null) {
				if(addRes==1) {
					//添加登录日志成功
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("添加登录日志成功");
				}else {
					//添加登录日志失败
					msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					msg.setDescription("添加登录日志失败");
				}
			}
		}catch(Exception e) {
			//添加登录日志失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("添加登录日志失败");
		}
		return msg.toJson();
	}
	
	/*
     * 下载登录日志列表数据接口
     * date:2020-04-23
     */  
	@RequestMapping(value = "/downloadLoginLogList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载登录日志列表数据", notes = "下载登录日志列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadLoginLogList(@RequestParam(value = "objValue") String objValue,@RequestParam(value = "titleInfos") String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryLoginLogDTO queryLoginLogDTO = jsonValue.fromJson(objValue, QueryLoginLogDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			if(queryLoginLogDTO.getStartTime() == null || queryLoginLogDTO.getStartTime().equals("")) {
				return new HttpEntity<String>("参数错误!开始时间不能为空");
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryLoginLogDTO.getStartTime());
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!开始时间格式不是yyyy-mm-dd");
				}
			}
			if(queryLoginLogDTO.getEndTime() == null || queryLoginLogDTO.getEndTime().equals("")) {
				return new HttpEntity<String>("参数错误!结束时间不能为空");
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryLoginLogDTO.getEndTime());
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!结束时间格式不是yyyy-mm-dd");
				}
			}
			int res = queryLoginLogDTO.getStartTime().compareTo(queryLoginLogDTO.getEndTime());
			if(res > 0) {
				return new HttpEntity<String>("参数错误!开始时间大于结束时间");
			}
			
			if(queryLoginLogDTO.getType()==null || StringUtils.isBlank(queryLoginLogDTO.getType())) {
				
			}else if(queryLoginLogDTO.getType().equals("全部")) {
				queryLoginLogDTO.setType(null);
			}else if(!queryLoginLogDTO.getType().equals("登入") && !queryLoginLogDTO.getType().equals("登出")) {
				return new HttpEntity<String>("参数错误!操作类型必须是“全部、登入或登出”");
			} 
			queryLoginLogDTO.setPage(1);
			queryLoginLogDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageLoginLogListVO pageBean = ADOConnection.runTask(logService, "downloadLoginLogList", PageLoginLogListVO.class,queryLoginLogDTO);
			if(pageBean.getRowNumber() == 0) {
				return new HttpEntity<String>("无数据可下载");
			}
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
	
	/*
     * 查询操作日志接口
     * date:2020-04-01
     */  
	@RequestMapping(value = "/querySysOperateLog.htm",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询操作日志接口", notes = "查询操作日志接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryOperateLog(@RequestBody QueryOperateLogDTO queryOperateLogDTO) {
		if(queryOperateLogDTO.getPage() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!页码不能为空", Integer.class).toJson();
		}
		if(queryOperateLogDTO.getPageCount() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!每页数量不能为空", Integer.class).toJson();
		}
		if(queryOperateLogDTO.getStartTime() == null || queryOperateLogDTO.getStartTime().equals("")) {
	        return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryOperateLogDTO.getStartTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		if(queryOperateLogDTO.getEndTime() == null || queryOperateLogDTO.getEndTime().equals("")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryOperateLogDTO.getEndTime());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		int res = queryOperateLogDTO.getStartTime().compareTo(queryOperateLogDTO.getEndTime());
		if(res > 0) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间大于结束时间", Integer.class).toJson();
		}
		
		if(queryOperateLogDTO.getOperateType()==null || StringUtils.isBlank(queryOperateLogDTO.getOperateType())) {
			
		}else if(queryOperateLogDTO.getOperateType().equals("全部")) {
			queryOperateLogDTO.setOperateType(null);
		}else if(!queryOperateLogDTO.getOperateType().equals("增加") && !queryOperateLogDTO.getOperateType().equals("删除") && 
				!queryOperateLogDTO.getOperateType().equals("修改") && !queryOperateLogDTO.getOperateType().equals("查询")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作类型必须是“全部、增加、删除、修改或查询”", Integer.class).toJson();
		} 
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		 //执行查询操作日志
		 try {
			 List<OperateLogVO> operateLogList=ADOConnection.runTask(logService, "queryOperateLog", List.class, queryOperateLogDTO);
			 if(operateLogList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关日志的信息"); 
			     msg.setData(operateLogList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关日志的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询日志失败");
	     }
		 return msg.toJson();
	}
	
	
	/*
     * 添加操作日志接口
     * date:2020-04-01
     */  
	@RequestMapping(value = "/addSysOperateLog.htm",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ApiOperation(value = "添加操作日志接口",notes = "添加操作日志接口", httpMethod = "POST",response = MessageBean.class,consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addOperateLog(@RequestBody OperateLogDTO operateLogDTO) {
		if(operateLogDTO.getOperateModuleNo()==null || StringUtils.isBlank(operateLogDTO.getOperateModuleNo())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作对象不能为空", Integer.class).toJson();
		}
		String [] moduleNo = new String [] {"M100","M200","M200100","M200100100","M200100100100","M200100100200","M200100100300","M200100100400",
				"M200100200","M200100200100","M200100200200","M200100200300","M200200","M200200100","M200200200","M200300","M200400","M200400100",
				"M200400200","M200400300","M200400400","M300","M300100","M300100100","M300100200","M400","M400100","M400100100","M400100200",
				"M400200","M500","M500100","M500100100","M500100200","M500200","M500200100","M500200200","M500200300","M500300100","M500300200",
				"M500400","M500400100","M500400200","M500400300","M500400400","M500500","M500500100","M600","M600100","M600100100","M600100200",
				"M600100300","M600100400","M600200","M600200100","M600200200","M600300","M600300100","M600300200","M600400","M600400100","M600400200",
				"M700","M700100","M700100100","M700100200","M700100300","M700100400","M800","M800100","M800100100","M800100200","M800100300","M800200",
				"M800200100","M800200200","M800200300","M800300","M800300100","M800300200","M800300300","M800300400"};
		for(int i = 0;i < moduleNo.length;i++) {
			if(operateLogDTO.getOperateModuleNo().equals(moduleNo[i])){
				break;
			}else if (i == moduleNo.length-1) {
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作对象编码错误", Integer.class).toJson();
			}
		}
		
		
		if(operateLogDTO.getOperateType()==null || StringUtils.isBlank(operateLogDTO.getOperateType())) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作类型不能为空", Integer.class).toJson();
		}else if(!operateLogDTO.getOperateType().equals("增加") && !operateLogDTO.getOperateType().equals("删除") && 
				!operateLogDTO.getOperateType().equals("修改") && !operateLogDTO.getOperateType().equals("查询")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!操作类型必须是“增加、删除、修改或查询”", Integer.class).toJson();
		} 
		
		
		MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);
		try {
			Integer insertRes = ADOConnection.runTask(logService, "addOperateLog",Integer.class,operateLogDTO);
			if(insertRes!=null) {
				if(insertRes==1) {
					//添加操作日志成功
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("添加操作日志成功");
				}else {
					//添加操作日志失败
					msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					msg.setDescription("添加操作日志失败");
				}
			}
		}catch(Exception e) {
			//添加操作日志失败
			msg.setCode(Constant.MESSAGE_INT_ERROR);
			msg.setDescription("添加操作日志失败");
		}
		return msg.toJson();
	}
	
	
	/*
     * 下载操作日志列表数据接口
     * date:2020-04-23
     */  
	@RequestMapping(value = "/downloadOperateLogList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载操作日志列表数据", notes = "下载操作日志列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadOperateLogList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryOperateLogDTO queryOperateLogDTO = jsonValue.fromJson(objValue, QueryOperateLogDTO.class);
			if(queryOperateLogDTO.getStartTime() == null || queryOperateLogDTO.getStartTime().equals("")) {
				return new HttpEntity<String>("参数错误!开始时间不能为空");
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryOperateLogDTO.getStartTime());
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!开始时间格式不是yyyy-mm-dd");
				}
			}
			if(queryOperateLogDTO.getEndTime() == null || queryOperateLogDTO.getEndTime().equals("")) {
				return new HttpEntity<String>("参数错误!结束时间不能为空");
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryOperateLogDTO.getEndTime());
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!结束时间格式不是yyyy-mm-dd");
				}
			}
			int res = queryOperateLogDTO.getStartTime().compareTo(queryOperateLogDTO.getEndTime());
			if(res > 0) {
				return new HttpEntity<String>("参数错误!开始时间大于结束时间");
			}
			if(queryOperateLogDTO.getOperateType()==null || StringUtils.isBlank(queryOperateLogDTO.getOperateType())) {
				
			}else if(queryOperateLogDTO.getOperateType().equals("全部")) {
				queryOperateLogDTO.setOperateType(null);
			}else if(!queryOperateLogDTO.getOperateType().equals("增加") && !queryOperateLogDTO.getOperateType().equals("删除") && 
					!queryOperateLogDTO.getOperateType().equals("修改") && !queryOperateLogDTO.getOperateType().equals("查询")) {
				return new HttpEntity<String>("参数错误!操作类型必须是“全部、增加、删除、修改或查询”");
			} 
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			queryOperateLogDTO.setPage(1);
			queryOperateLogDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageOperateLogListVO pageBean = ADOConnection.runTask(logService, "downloadOperateLogList", PageOperateLogListVO.class,queryOperateLogDTO);
			if(pageBean.getRowNumber() == 0) {
				return new HttpEntity<String>("无数据可下载");
			}
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
	
	/*
     * 查询集成日志接口
     * date:2020-04-01
     */  
	@RequestMapping(value = "/querySysIntegrationLog.htm",method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询集成日志接口", notes = "查询集成日志接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryIntegrationLog(@RequestBody QueryIntegrationLogDTO queryIntegrationLogDTO) {
		
		if(queryIntegrationLogDTO.getPage() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!页码不能为空", Integer.class).toJson();
		}
		if(queryIntegrationLogDTO.getPageCount() == null ) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!每页数量不能为空", Integer.class).toJson();
		}
		if(queryIntegrationLogDTO.getStartDate()== null || queryIntegrationLogDTO.getStartDate().equals("")) {
	        return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间不能为空", Integer.class).toJson();
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryIntegrationLogDTO.getStartDate());
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		if(queryIntegrationLogDTO.getEndDate() == null || queryIntegrationLogDTO.getEndDate().equals("")) {
			queryIntegrationLogDTO.setEndDate(null);
		}else {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try{
				formatter.parse(queryIntegrationLogDTO.getEndDate());
				int res = queryIntegrationLogDTO.getStartDate().compareTo(queryIntegrationLogDTO.getEndDate());
				if(res > 0) {
					return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!开始时间大于结束时间", Integer.class).toJson();
					}
			}catch(Exception e){
				return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!结束时间格式不是yyyy-mm-dd", Integer.class).toJson();
			}
		}
		if(queryIntegrationLogDTO.getStatus()==null || StringUtils.isBlank(queryIntegrationLogDTO.getStatus())) {
			
		}else if(queryIntegrationLogDTO.getStatus().equals("全部")) {
			queryIntegrationLogDTO.setStatus(null);
		}else if(!queryIntegrationLogDTO.getStatus().equals("进行中") && !queryIntegrationLogDTO.getStatus().equals("已结束")) {
			return MessageBean.create(Constant.MESSAGE_INT_PARAMS, "参数错误!状态必须是“全部、进行中、已结束”", Integer.class).toJson();
		} 
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		 //执行查询集成日志
		 try {
			 List<IntegrationLogVO> integrationLogList=ADOConnection.runTask(logService, "queryIntegrationLog", List.class, queryIntegrationLogDTO);
			 if(integrationLogList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关日志的信息"); 
			     msg.setData(integrationLogList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关日志的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询日志失败");
	     }
		 return msg.toJson();
		 
	}
	
	/*
     * 下载集成日志列表数据接口
     * date:2020-04-23
     */  
	@RequestMapping(value = "/downloadIntegrationLogList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载集成日志列表数据", notes = "下载集成日志列表数据", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downloadIntegrationLogList(@RequestParam String objValue,@RequestParam String titleInfos) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryIntegrationLogDTO queryIntegrationLogDTO = jsonValue.fromJson(objValue, QueryIntegrationLogDTO.class);
			if(queryIntegrationLogDTO.getStartDate()== null || queryIntegrationLogDTO.getStartDate().equals("")) {
				return new HttpEntity<String>("参数错误!开始时间不能为空");
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryIntegrationLogDTO.getStartDate());
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!开始时间格式不是yyyy-mm-dd");
				}
			}
			if(queryIntegrationLogDTO.getEndDate() == null || queryIntegrationLogDTO.getEndDate().equals("")) {
				queryIntegrationLogDTO.setEndDate(null);
			}else {
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try{
					formatter.parse(queryIntegrationLogDTO.getEndDate());
					int res = queryIntegrationLogDTO.getStartDate().compareTo(queryIntegrationLogDTO.getEndDate());
					if(res > 0) {
						return new HttpEntity<String>("参数错误!开始时间大于结束时间");
					}
				}catch(Exception e){
					return new HttpEntity<String>("参数错误!结束时间格式不是yyyy-mm-dd");
				}
			}
			if(queryIntegrationLogDTO.getStatus()==null || StringUtils.isBlank(queryIntegrationLogDTO.getStatus())) {
				
			}else if(queryIntegrationLogDTO.getStatus().equals("全部")) {
				queryIntegrationLogDTO.setStatus(null);
			}else if(!queryIntegrationLogDTO.getStatus().equals("进行中") && !queryIntegrationLogDTO.getStatus().equals("已结束")) {
				return new HttpEntity<String>("参数错误!状态必须是“全部、进行中、已结束”");
			} 
			
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			
			queryIntegrationLogDTO.setPage(1);
			queryIntegrationLogDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
			PageIntegrationLogListVO pageBean = ADOConnection.runTask(logService, "downloadIntegrationLogList", PageIntegrationLogListVO.class,queryIntegrationLogDTO);
			if (pageBean.getRowNumber() == 0 ) {
				return new HttpEntity<String>("无数据可下载");
			}
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
	
}
