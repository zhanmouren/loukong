package com.koron.permission.controller;

import java.util.List;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.common.StaffAttribute;
import com.koron.common.bean.StaffBean;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.permission.authority.DataInject;
import com.koron.permission.authority.DataRangeMethod;
import com.koron.permission.authority.OPSPIMethod;
import com.koron.permission.bean.DTO.TblOpDTO;
import com.koron.permission.bean.DTO.TblTenantDTO;
import com.koron.permission.bean.VO.TblRoleRangeValueListVO;
import com.koron.permission.service.PermissionService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "PermissionOuterController", description = "权限控制对外接口Controller")
@RequestMapping(value = "/PermissionOuterController")
public class PermissionOuterController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 登录用户在SESSION里的储存KEY
	 */
	public static final String USER = "_user";
	
	/**
	 * 参数校验异常码
	 */
	public static final int MESSAGE_INT_PARAMS = 20108;
	/**
	 * 操作成功码
	 */
	public static final int MESSAGE_INT_SUCCESS = 0;
	/**
	 * 操作成功提示
	 */
	public static final String MESSAGE_STRING_SUCCESS = "操作成功";
	
	/**
	 * 操作失败码
	 */
	public static final int MESSAGE_INT_ERROR = 1;
	/**
	 * 添加失败码
	 */
	public static final int MESSAGE_INT_ADDERROR = 20102;
	
	/**
	 * 修改失败码
	 */
	public static final int MESSAGE_INT_EDITERROR = 20103;
	
	/**
	 * 删除失败码
	 */
	public static final int MESSAGE_INT_DELERROR = 20104;
	
	/**
	 * 登录失败码
	 */
	public static final int MESSAGE_INT_LOGINERROR = 20105;
	/**
	 * 查询失败码
	 */
	public static final int MESSAGE_INT_SELECTERROR = 20107;
	
	/*
	 * @author xiaozhan
	 * @Date 2020.05.28
	 * description:根据用户信息获取有关联(有权限)的操作，如果传父操作， 则只返回此操作下有权限的操作1.0。
	 */
	@OPSPIMethod("op001")
	@DataRangeMethod
	@RequestMapping(value = "/getUserOPList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取用户相关联操作接口", notes = "获取用户相关联操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String getUserOPList(@RequestBody TblTenantDTO tblTenantDTO,@DataInject  TblRoleRangeValueListVO tblRoleRangeValueListVO) {	
		if(tblTenantDTO.get_tenantCode()==null || "".equals(tblTenantDTO.get_tenantCode())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "租户code不能为空", Integer.class).toJson();
		}
		if(tblTenantDTO.get_app()==null || "".equals(tblTenantDTO.get_app())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "应用code不能为空", Integer.class).toJson();
		}
		if(tblTenantDTO.get_userCode()==null || "".equals(tblTenantDTO.get_userCode())) {
			return  MessageBean.create(MESSAGE_INT_PARAMS, "用户code不能为空", Integer.class).toJson();
		}
		MessageBean<List> msg = MessageBean.create(MESSAGE_INT_SUCCESS, MESSAGE_STRING_SUCCESS, List.class);	
		  try{
			  List<?> opList=ADOConnection.runTask(permissionService, "getUserOPList", List.class,tblTenantDTO);
			  if(opList.size()>=0) {
				  msg.setCode(MESSAGE_INT_SUCCESS);
				  msg.setDescription("获取用户权限列表成功");
				  msg.setData(opList);
			  }else {
				  msg.setCode(MESSAGE_INT_SELECTERROR);
				  msg.setDescription("获取用户权限列表失败");
			  }
	        }catch(Exception e){
	        	msg.setCode(MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	
	 /*
	 * @author xiaozhan
	 * @Date 2020.06.03
	 * description:根据用户信息，获取用户所有角色1.0
	 */
	@RequestMapping(value = "/getUserRoleList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取用户所有角色接口", notes = "获取用户所有角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String getUserRoleList(@RequestBody TblTenantDTO tblTenantDTO) {	
    	 if(tblTenantDTO.get_tenantCode()==null || "".equals(tblTenantDTO.get_tenantCode())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "租户code不能为空", Integer.class).toJson();
	     }
    	 if(tblTenantDTO.get_userCode()==null || "".equals(tblTenantDTO.get_userCode())) {
    		 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户code不能为空", Integer.class).toJson();
    	 }
    	 if(tblTenantDTO.get_app()==null || "".equals(tblTenantDTO.get_app())) {
    		 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "应用code不能为空", Integer.class).toJson();
    	 }	
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	
		  try{
			  List<?> opList=ADOConnection.runTask(permissionService, "getUserRoleList", List.class,tblTenantDTO);
			  if(opList.size()>=0) {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				  msg.setDescription("查询用户角色列表成功");
				  msg.setData(opList);
			  }else {
				  msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
				  msg.setDescription("查询用户角色列表失败");
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
	 * @author xiaozhan
	 * @Date 2020.06.03
	 * description:获取用户是否存在操作权限1.0
	 */
	@RequestMapping(value = "/getOPList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取用户是否存在操作权限接口", notes = "获取用户是否存在操作权限接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String getOPList(@RequestBody TblOpDTO tblOpDTO,@SPIAccountAnno @StaffAttribute(Constant.USER) StaffBean account) {				
		if(tblOpDTO.get_tenantCode()==null || "".equals(tblOpDTO.get_tenantCode())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "租户code不能为空", Integer.class).toJson();
	    }
		if(tblOpDTO.get_app()==null || "".equals(tblOpDTO.get_app())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "应用code不能为空", Integer.class).toJson();
		}
		if(tblOpDTO.getUserCode()==null || "".equals(tblOpDTO.getUserCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户code不能为空", Integer.class).toJson();
		}
		if(tblOpDTO.getOpCode()==null || "".equals(tblOpDTO.getOpCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "操作code不能为空", Integer.class).toJson();
		}			
		MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	
		  try{
			  List<?> opList=ADOConnection.runTask(permissionService, "getOPList", List.class,tblOpDTO);
			  if(opList.size()>=0) {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				  msg.setDescription("查询用户操作权限成功");
				  msg.setData(opList);
			  }else {
				  msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
				  msg.setDescription("查询用户操作权限失败");
				  msg.setData(opList);
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
	 * @author xiaozhan
	 * @Date 2020.06.03
	 * description:获取用户数据范围1.0
	 */
	@RequestMapping(value = "/getUserRangeValueList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取用户数据范围接口", notes = "获取用户数据范围接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String getUserRangeValueList(@RequestBody TblTenantDTO tblOpDTO) {	
		if(tblOpDTO.get_tenantCode()==null || "".equals(tblOpDTO.get_tenantCode())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "租户code不能为空", Integer.class).toJson();
	    }
		if(tblOpDTO.get_app()==null || "".equals(tblOpDTO.get_app())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "应用code不能为空", Integer.class).toJson();
		}
		if(tblOpDTO.get_userCode()==null || "".equals(tblOpDTO.get_userCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户code不能为空", Integer.class).toJson();
		}
    	MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	
		  try{
			  List<?> opList=ADOConnection.runTask(permissionService, "getUserRangeValueList", List.class,tblOpDTO);
			  if(opList.size()>=0) {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				  msg.setDescription("获取用户数据范围成功");
				  msg.setData(opList);
			  }else {
				  msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
				  msg.setDescription("获取用户数据范围失败");
				  msg.setData(opList);
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
}
