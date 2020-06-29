package com.koron.inwlms.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.StaffAttribute;
import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.common.FileConfigInfo;
import com.koron.inwlms.bean.DTO.sysManager.*;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.UploadFileVO;
import com.koron.inwlms.bean.VO.sysManager.*;
import com.koron.inwlms.service.sysManager.UserService;
import com.koron.inwlms.util.ExportDataUtil;
import com.koron.inwlms.util.ImportExcelUtil;
import com.koron.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.swan.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统管理Controller层
 * @author xiaozhan
 * @Date 2020.03.18
 */

@RestController
@Api(value = "systemManager", description = "系统管理Controller")
@RequestMapping(value = "/{tenantID}/systemManager")
public class SystemManagerController {
	
	@Autowired
	private UserService userService;
	@Autowired
    private FileConfigInfo fileConfigInfo;
     
	 /*
     * date:2020-03-18
     * funtion:管理员添加新职员接口
     * author:xiaozhan
     */
	@RequestMapping(value = "/addUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "管理员添加新职员接口", notes = "管理员添加新职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addUser(@RequestBody UserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(userDTO.getName()==null || StringUtils.isBlank(userDTO.getName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
		}
		if(userDTO.getLoginName()==null || StringUtils.isBlank(userDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
		}
		if(userDTO.getWorkNo()==null || StringUtils.isBlank(userDTO.getWorkNo())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "工号不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行插入职员的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addUser", Integer.class, userDTO);		 
				  if(insertRes==1) {
					//添加用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加用户成功");
				  }else if(insertRes==-2) {
					msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("登录名已经存在,请重新设置登录名");
				  }else if(insertRes==-3) {
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("工号已经存在，请重新设置工号");  
				  }
				  else {
				    //插入失败
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加用户失败");
				  }
			  
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-19
     * funtion:查询职员接口，通过此接口可以通过职员名或部门或者Id称查询职员的基本信息
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询职员接口", notes = "查询职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryUser(@RequestBody QueryUserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO result=ADOConnection.runTask(user.getEnv(),userService, "queryUser", PageListVO.class, userDTO);
			 if(result!=null  && result.getRowNumber()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关职员的信息"); 
			     msg.setData(result);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关职员的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询职员失败");
	     }
		 return msg.toJson();
		 
	}
	
	/*
     * date:2020-06-17
     * funtion:查询职员详情信息接口
     * author:lzy
     */
	@RequestMapping(value = "/queryUserDetail.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询职员接口", notes = "查询职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryUserDetail(@RequestBody QueryUserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 if(userDTO.getCode() == null || StringUtils.isBlank(userDTO.getCode())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员code不能为空", Integer.class).toJson();
		 }
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	 
		 //执行查询职员
		 try {
			 List<UserVO> result=ADOConnection.runTask(user.getEnv(),userService, "queryUserDetail", List.class,userDTO);	
			 if(result!=null  && result.size() > 0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关职员的信息"); 
			     msg.setData(result);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关职员的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询职员失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-20
     * funtion:修改新职员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/updateUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改职员信息接口", notes = "修改职员信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateUser(@RequestBody UserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(userDTO.getCode()==null || StringUtils.isBlank(userDTO.getCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码不能为空", Integer.class).toJson();
		}
		if(userDTO.getWorkNo()==null || StringUtils.isBlank(userDTO.getWorkNo())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员工号不能为空", Integer.class).toJson();
		}
		if(userDTO.getName()==null || StringUtils.isBlank(userDTO.getName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
		}
		if(userDTO.getLoginName()==null || StringUtils.isBlank(userDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
		}
		if(userDTO.getSex()==null || StringUtils.isBlank(userDTO.getSex())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "性别不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行修改职员的操作
		  try{
			  userDTO.setUpdateBy(user.getLoginName());
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateUser", Integer.class, userDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {
					//修改用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("修改用户成功");
				  }else if(updateRes== -1){
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					  msg.setDescription("loginName 重复");
				  }else if(updateRes== -2){
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					  msg.setDescription("workNo 重复");
				  }else{
					  //修改用户失败
				      msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				      msg.setDescription("修改用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//修改用户失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改用户失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-20
     * funtion:管理员一键重置密码
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/updateUserPassword.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量重置职员密码接口", notes = "批量重置职员密码接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateUserPassword(@RequestBody UserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(userDTO.getUserCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码列表参数不能为空", Integer.class).toJson();
		}
		if(userDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行批量重置密码的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateUserPassword", Integer.class, userDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {					
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("重置职员密码成功");
				  }else {				   
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			        msg.setDescription("重置职员密码失败");
				  }
			  }
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("重置失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-23
     * funtion:删除新职员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/deleteUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除职员信息接口", notes = "删除职员信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String  deleteUser(@RequestBody UserDTO userDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(userDTO.getCode()==null || "".equals(userDTO.getCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员的编码不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除职员的操作
		  try{
			  Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteUser", Integer.class, userDTO);		 
			  if(delRes!=null) {
				  if(delRes==1) {
					//删除用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("删除用户成功");
				  }else {
				    //删除用户失败
			        msg.setCode(Constant.MESSAGE_INT_DELERROR);
			        msg.setDescription("删除用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//删除用户失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除用户失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-23
     * funtion:添加新角色接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/addNewRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加新角色接口", notes = "添加新角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addNewRole(@RequestBody RoleDTO roleDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(roleDTO.getRoleName()==null || StringUtils.isBlank(roleDTO.getRoleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行插入角色的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addNewRole", Integer.class, roleDTO);		 
				  if(insertRes==1) {
					//添加角色成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加新角色成功");
				  }else if(insertRes==-2) {
					  //插入失败
				     msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				     msg.setDescription("角色名称重复");
				  }
				  else {
				    //插入失败
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加新角色失败");
				  }
			  
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加新角色失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-20
     * funtion:修改角色属性接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/updateRoleAttr.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改角色属性接口", notes = "修改角色属性接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateRoleAttr(@RequestBody RoleDTO roleDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(roleDTO.getRoleCode()==null || "".equals(roleDTO.getRoleCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		if(roleDTO.getRoleName()==null || StringUtils.isBlank(roleDTO.getRoleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();
		}	
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateRoleAttr", Integer.class, roleDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("修改角色属性成功");
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			        msg.setDescription("修改角色属性失败");
				  }
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改角色属性失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-20
     * funtion:批量删除删除角色接口（超级管理员角色不允许删除，代码待写）
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/deleteRoleAttr.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量删除角色接口", notes = "批量删除角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteRoleAttr(@RequestBody RoleDTO roleDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(roleDTO.getRoleCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色列表code参数不能为空", Integer.class).toJson();
		}	
		if(roleDTO.getRoleCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色列表code不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除角色的操作
		  try{
			  RoleMsgVO roleMsgVO=ADOConnection.runTask(user.getEnv(),userService, "deleteRoleAttr", RoleMsgVO.class, roleDTO);		 
			  if(roleMsgVO!=null) {
				  if(roleMsgVO.getResult()==-1) {
					 msg.setCode(Constant.MESSAGE_INT_DELERROR);
					 msg.setDescription(roleMsgVO.getMessage()); 
				  }else {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				      msg.setDescription(roleMsgVO.getMessage());  
				  }
				   
			 }
	        }catch(Exception e){
	        	//删除角色失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除角色失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-24
     * funtion:根据角色code加载角色人员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/queryUserByRoleCode.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据角色code加载角色人员接口", notes = "根据角色ID加载角色人员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryUserByRoleId(@RequestBody RoleDTO roleDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 if(roleDTO.getRoleCode()==null || "".equals(roleDTO.getRoleCode())) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		 }
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		//执行删除角色的操作
		  try{
			  PageListVO userVO=ADOConnection.runTask(user.getEnv(),userService, "queryUserByRoleCode", PageListVO.class, roleDTO);		 
			  if(userVO!=null && userVO.getRowNumber()>0) {				 
					 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					 msg.setDescription("根据角色code查询到相关职员信息列表"); 
					 msg.setData(userVO);
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("该角色未查询到相关职员信息列表"); 
			 }
	        }catch(Exception e){
	        	//查询角色职员失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询角色职员失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-24
     * funtion:查询所有角色接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/queryAllRole.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询所有角色接口", notes = "查询所有角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryAllRole(@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<RoleVO> roleList=ADOConnection.runTask(user.getEnv(),userService, "queryAllRole", List.class);		 
			  if(roleList.size()>0) {				 
					 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					 msg.setDescription("查询到所有角色列表"); 
					 msg.setData(roleList);
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("没有查询到所有角色"); 
			 }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询角色失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-24
     * funtion:插入职员(批量)和角色的关系
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/addRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "插入职员(批量)和角色的关系", notes = "插入职员(批量)和角色的关系", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addRoleUser(@RequestBody RoleAndUserDTO roleUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(roleUserDTO.getRoleCode()==null || "".equals(roleUserDTO.getRoleCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		if(roleUserDTO.getUserCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表参数不能为空", Integer.class).toJson();
		}
		if(roleUserDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行添加用户和角色关系的操作
		  try{
			  Integer addResult=ADOConnection.runTask(user.getEnv(),userService, "addRoleUser", Integer.class, roleUserDTO);		 
			  if(addResult==-1) {				 
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("插入职员和角色的关系失败"); 					
			 }else if(addResult==-2) {
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("职员和角色的关系在用户角色关系表已经存在"); 
			 }else if(addResult==-3) {
				 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				 msg.setDescription("角色编码在角色表中不存在"); 
			 }else if(addResult==-4) {
				 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				 msg.setDescription("用户编码在用户表中不存在"); 
			 }
			 else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("插入职员和角色的关系成功"); 
			 }
	        }catch(Exception e){
	        	//插入职员和角色的关系失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("插入职员和角色的关系失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-03-24
     * funtion:删除角色中职员(批量)接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/deleteRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除角色中职员接口", notes = "删除角色中职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteRoleUser(@RequestBody RoleAndUserDTO roleUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(roleUserDTO.getRoleCode()==null || "".equals(roleUserDTO.getRoleCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		if(roleUserDTO.getUserCodeList()==null) {
		    return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表参数不能为空", Integer.class).toJson();
		}
		if(roleUserDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除角色中职员(批量)操作
		  try{
			  Integer delResult=ADOConnection.runTask(user.getEnv(),userService, "deleteRoleUser", Integer.class, roleUserDTO);		 
			  if(delResult==-1) {				 
					 msg.setCode(Constant.MESSAGE_INT_DELERROR);
					 msg.setDescription("删除角色中职员(批量)失败"); 
					
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("删除角色中职员(批量)成功"); 
			 }
	        }catch(Exception e){
	        	//删除角色中职员(批量)失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除角色中职员(批量)失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-24
     * funtion:给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)分页
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryExceptRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询角色其他职员接口", notes = "查询角色其他职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryExceptRoleUser(@RequestBody RoleAndUserDTO roleUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(roleUserDTO.getRoleCode()==null || "".equals(roleUserDTO.getRoleCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO userVO=ADOConnection.runTask(user.getEnv(),userService, "queryExceptRoleUser", PageListVO.class, roleUserDTO);
			 if(userVO!=null && userVO.getRowNumber()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("该角色查询到其他相关职员的信息"); 
			     msg.setData(userVO);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("该角色没有查询到相关职员的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询职员失败");
	     }
		 return msg.toJson();
		 
	}
	
	/*
     * date:2020-03-25
     * funtion:给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryExceptDeptUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询部门其他职员接口", notes = "查询部门其他职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryExceptDeptUser(@RequestBody DeptAndUserDTO deptUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(deptUserDTO.getDepCode()==null || "".equals(deptUserDTO.getDepCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO userVO=ADOConnection.runTask(user.getEnv(),userService, "queryExceptDeptUser", PageListVO.class, deptUserDTO);
			 if(userVO!=null && userVO.getRowNumber()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("该部门查询到其他相关职员的信息"); 
			     msg.setData(userVO);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("该部门没有查询到相关职员的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询职员失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-25
     * funtion:插入职员(批量)和部门的关系
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/addDeptUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "插入职员(批量)和部门的关系", notes = "插入职员(批量)和部门的关系", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addDeptUser(@RequestBody DeptAndUserDTO deptUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(deptUserDTO.getDepCode()==null  || "".equals(deptUserDTO.getDepCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		if(deptUserDTO.getUserCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表参数不能为空", Integer.class).toJson();
		}
		if(deptUserDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行添加用户和部门关系的操作
		  try{
			  Integer addResult=ADOConnection.runTask(user.getEnv(),userService, "addDeptUser", Integer.class, deptUserDTO);		 
			  if(addResult==-1) {				 
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("插入职员和部门的关系失败"); 					
			 }else if(addResult==-2) {
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("职员和部门的关系在用户部门关系表已经存在"); 
			 }else if(addResult==-3) {
				 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				 msg.setDescription("部门编码在部门表中不存在"); 
			 }else if(addResult==-4) {
				 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				 msg.setDescription("用户编码在用户表中不存在"); 
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("插入职员和部门的关系成功"); 
			 }
	        }catch(Exception e){
	        	//插入职员和角色的关系失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("插入职员和部门的关系失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-03-24
     * funtion:删除部门中职员(批量)接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/deleteDeptUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除部门中职员接口", notes = "删除部门中职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteDeptUser(@RequestBody DeptAndUserDTO deptUserDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(deptUserDTO.getDepCode()==null || "".equals(deptUserDTO.getDepCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		if(deptUserDTO.getUserCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表参数不能为空", Integer.class).toJson();
		}
		if(deptUserDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除部门中职员(批量)操作
		  try{
			  Integer delResult=ADOConnection.runTask(user.getEnv(),userService, "deleteDeptUser", Integer.class, deptUserDTO);		 
			  if(delResult==-1) {				 
					 msg.setCode(Constant.MESSAGE_INT_DELERROR);
					 msg.setDescription("删除部门中职员(批量)失败"); 
					
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("删除部门中职员(批量)成功"); 
			 }
	        }catch(Exception e){
	        	//删除角色中职员(批量)失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除部门中职员(批量)失败");
	        }
		
	     return msg.toJson();
	}
	
	
	
	
     /* -------下面的是关于系统配置的接口--------  */ 
    
	 /*
     * date:2020-04-16
     * funtion:添加集成配置功能
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addIntegration.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "插入集成配置功能接口", notes = "插入集成配置功能接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addIntegration(@RequestBody IntegrationConfDTO integrationConfDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(integrationConfDTO.getOtherJDBC()==null || StringUtils.isBlank(integrationConfDTO.getOtherJDBC())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方JDBC不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getSysName()==null || StringUtils.isBlank(integrationConfDTO.getSysName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方系统名称不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getStatus()==null || "".equals(integrationConfDTO.getStatus())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "状态不能为空", Integer.class).toJson();
		}
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行集成配置添加功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addIntegration", Integer.class, integrationConfDTO);		 		  
				  if(insertRes==1) {
					//添加集成配置功能成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加集成配置成功");
				  }else {
				    //插入失败
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加集成配置失败");
				  }			  
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加配置失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-16
     * funtion:添加表格映射
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addTableMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加表格映射功能接口", notes = "添加表格映射功能接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addTableMapper(@RequestBody TableMapperDTO tableMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(tableMapperDTO.getConfigCode()==null || "".equals(tableMapperDTO.getConfigCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getOtherTabCode()==null || "".equals(tableMapperDTO.getOtherTabCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格编码不能为空", Integer.class).toJson();
		}	
		if(tableMapperDTO.getOtherTabName()==null || "".equals(tableMapperDTO.getOtherTabName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格名不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableCode()==null  || "".equals(tableMapperDTO.getTableCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableName()==null || "".equals(tableMapperDTO.getTableName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格名称不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addTableMapper", Integer.class, tableMapperDTO);		 		  
				  if(insertRes==1) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加表格映射成功");
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加表格映射失败");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加表格失败");
	        }
		
	     return msg.toJson();
	}

	 /*
     * date:2020-04-16
     * funtion:添加表格字段映射
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addFieldMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加表格字段映射功能接口", notes = "添加表格字段映射功能接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addFieldMapper(@RequestBody FieldMapperDTO fieldMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(fieldMapperDTO.getTableCode()==null || "".equals(fieldMapperDTO.getTableCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细编码不能为空", Integer.class).toJson();
		}	
		if(fieldMapperDTO.getOtherFieldCode()==null || "".equals(fieldMapperDTO.getOtherFieldCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getOtherFieldName()==null || "".equals(fieldMapperDTO.getOtherFieldName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldCode()==null || "".equals(fieldMapperDTO.getFieldCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldName()==null || "".equals(fieldMapperDTO.getFieldName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldType()==null || "".equals(fieldMapperDTO.getFieldType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段类型不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFormula()==null || "".equals(fieldMapperDTO.getFormula())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段公式不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getValue()==null || "".equals(fieldMapperDTO.getValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "code值不能为空", Integer.class).toJson();
		}				
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addFieldMapper", Integer.class, fieldMapperDTO);		 		  
				  if(insertRes==1) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加表格字段映射成功");
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加表格字段映射失败");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加表格字段失败");
	        }
		
	     return msg.toJson();
	}

	 /*
     * date:2020-04-16
     * funtion:添加枚举值映射明细
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addEnumMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加枚举值映射明细接口", notes = "添加枚举值映射明细接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addEnumMapper(@RequestBody EnumMapperDTO enumMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(enumMapperDTO.getConfCode()==null || "".equals(enumMapperDTO.getConfCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getOtherFieldValue()==null || "".equals(enumMapperDTO.getOtherFieldValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getFieldValue()==null || "".equals(enumMapperDTO.getFieldValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getMapper()==null || "".equals(enumMapperDTO.getMapper())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "映射方式不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addEnumMapper", Integer.class, enumMapperDTO);		 		  
				  if(insertRes==1) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加枚举值映射明细成功");
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加枚举值映射明细失败");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加枚举值映射明细失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-16
     * funtion:查询集成配置列表信息
     * author:xiaozhan
     */  
	@RequestMapping(value = "/queryIntegration.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询集成配置列表信息接口", notes = "查询集成配置列表信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryIntegration(@RequestBody IntegrationConfDTO integrationConfDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{
			  PageListVO pageListVO=ADOConnection.runTask(user.getEnv(),userService, "queryIntegration", PageListVO.class,integrationConfDTO);		 		  
				  if(pageListVO!=null &&  pageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询集成配置列表信息成功");
				    msg.setData(pageListVO);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询集成配置列表信息");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询集成配置列表信息失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-16
     * funtion:根据code查询集成配置信息
     * author:xiaozhan
     */  
	@RequestMapping(value = "/queryIntegrationByCode.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据code查询集成配置信息接口", notes = "根据code查询集成配置信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryIntegrationByCode(@RequestBody IntegrationConfDTO integrationConfDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(integrationConfDTO.getInteConfCode()==null || "".equals(integrationConfDTO.getInteConfCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置code不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<IntegrationConfVO> integrationConfVOList=ADOConnection.runTask(user.getEnv(),userService, "queryIntegrationByCode", List.class,integrationConfDTO);		 		  
				  if(integrationConfVOList.size()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询集成配置详情信息成功");
				    msg.setData(integrationConfVOList);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到集成配置详情信息");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询集成配置详情信息失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-17
     * funtion:根据配置主表Code查询表格映射明细列表
     * author:xiaozhan
     */  
	@RequestMapping(value = "/queryTableMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据配置主表Code查询表格映射明细列表接口", notes = "根据配置主表Code查询表格映射明细列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryTableMapper(@RequestBody TableMapperDTO tableMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(tableMapperDTO.getConfigCode()==null || "".equals(tableMapperDTO.getConfigCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置code不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{
			  PageListVO pageListVO=ADOConnection.runTask(user.getEnv(),userService, "queryTableMapper", PageListVO.class,tableMapperDTO);		 		  
				  if(pageListVO!=null &&  pageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询表格映射列表明细成功");
				    msg.setData(pageListVO);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到表格映射列表明细");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询表格映射列表明细失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-17
     * funtion:根据配置主表Code查询枚举值映射明细列表
     * author:xiaozhan
     */  
	@RequestMapping(value = "/queryEnumMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据配置主表Code查询枚举值映射明细列表接口", notes = "根据配置主表Code查询枚举值映射明细列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryEnumMapper(@RequestBody EnumMapperDTO enumMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(enumMapperDTO.getConfCode()==null || "".equals(enumMapperDTO.getConfCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置code不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{
			  PageListVO pageListVO=ADOConnection.runTask(user.getEnv(),userService, "queryEnumMapper", PageListVO.class,enumMapperDTO);		 		  
				  if(pageListVO!=null &&  pageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询枚举值映射明细列表成功");
				    msg.setData(pageListVO);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到枚举值映射明细列表");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询枚举值映射明细列表失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-17
     * funtion:根据表格Code查询表格字段映射明细列表
     * author:xiaozhan
     */  
	@RequestMapping(value = "/queryFieldMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据表格Code查询表格字段映射明细列表接口", notes = "根据表格Code查询表格字段映射明细列表接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryFieldMapper(@RequestBody FieldMapperDTO fieldMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(fieldMapperDTO.getTableCode()==null || "".equals(fieldMapperDTO.getTableCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细code不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{
			  PageListVO pageListVO=ADOConnection.runTask(user.getEnv(),userService, "queryFieldMapper", PageListVO.class,fieldMapperDTO);		 		  
				  if(pageListVO!=null &&  pageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询表格Code查询表格字段映射明细列表成功");
				    msg.setData(pageListVO);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到列表信息");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询表格Code查询表格字段映射明细列表失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-17
     * funtion:根据code修改集成配置信息
     * author:xiaozhan
     */  
	@RequestMapping(value = "/updateConf.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据code修改集成配置信息接口", notes = "根据code修改集成配置信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateConf(@RequestBody IntegrationConfDTO integrationConfDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(integrationConfDTO.getInteConfCode()==null || "".equals(integrationConfDTO.getInteConfCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "配置编码不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getStatus()==null || "".equals(integrationConfDTO.getStatus())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "配置状态不能为空", Integer.class).toJson();
		}	
		if(integrationConfDTO.getSysName()==null || "".equals(integrationConfDTO.getSysName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "系统名称不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getOtherJDBC()==null || "".equals(integrationConfDTO.getOtherJDBC())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方JDBC不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateConf", Integer.class,integrationConfDTO);		 		  
				  if(updateRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改集成配置信息失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("修改集成配置信息成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改集成配置信息失败");
	        }
		
	     return msg.toJson();
	}
	/*
     * date:2020-04-17
     * funtion:根据code修改表格映射明细信息
     * author:xiaozhan
     */  
	@RequestMapping(value = "/updateTableMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据code修改表格映射明细信息接口", notes = "根据code修改表格映射明细信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateTableMapper(@RequestBody TableMapperDTO tableMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
        if(tableMapperDTO.getTableMapperCode()==null || "".equals(tableMapperDTO.getTableMapperCode())) {
        	return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细编码不能为空", Integer.class).toJson();
        }	
		if(tableMapperDTO.getOtherTabCode()==null || "".equals(tableMapperDTO.getOtherTabCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格编码不能为空", Integer.class).toJson();
		}	
		if(tableMapperDTO.getOtherTabName()==null || "".equals(tableMapperDTO.getOtherTabName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格名不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableCode()==null || "".equals(tableMapperDTO.getTableCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableName()==null || "".equals(tableMapperDTO.getTableName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格名称不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateTableMapper", Integer.class,tableMapperDTO);		 		  
				  if(updateRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改表格映射明细信息失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("修改表格映射明细信息成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改表格映射明细信息失败");
	        }
		
	     return msg.toJson();
	}
	/*
     * date:2020-04-17
     * funtion:根据Id修改枚举明细映射接口
     * author:xiaozhan
     */  
	@RequestMapping(value = "/updateEnumMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据id修改表格映射明细信息接口", notes = "根据id修改表格映射明细信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateEnumMapper(@RequestBody EnumMapperDTO enumMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(enumMapperDTO.getId()==null || "".equals(enumMapperDTO.getId())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "id不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getOtherFieldValue()==null || "".equals(enumMapperDTO.getOtherFieldValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getFieldValue()==null || "".equals(enumMapperDTO.getFieldValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getMapper()==null || "".equals(enumMapperDTO.getMapper())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "映射方式不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateEnumMapper", Integer.class,enumMapperDTO);		 		  
				  if(updateRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改枚举明细映射失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("修改枚举明细映射成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改枚举明细映射失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-20
     * funtion:根据Code修改表格字段映射明细
     * author:xiaozhan
     */  
	@RequestMapping(value = "/updateFieldMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据Code修改表格字段映射明细接口", notes = "根据Code修改表格字段映射明细接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateFieldMapper(@RequestBody FieldMapperDTO fieldMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(fieldMapperDTO.getFieldMapperCode()==null || "".equals(fieldMapperDTO.getFieldMapperCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段映射明细编码不能为空", Integer.class).toJson();
		}	
		if(fieldMapperDTO.getOtherFieldCode()==null || "".equals(fieldMapperDTO.getOtherFieldCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getOtherFieldName()==null || "".equals(fieldMapperDTO.getOtherFieldName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldCode()==null || "".equals(fieldMapperDTO.getFieldCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldName()==null || "".equals(fieldMapperDTO.getFieldName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldType()==null || "".equals(fieldMapperDTO.getFieldType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段类型不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFormula()==null || "".equals(fieldMapperDTO.getFormula())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段公式不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getValue()==null || "".equals(fieldMapperDTO.getValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "code值不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateFieldMapper", Integer.class,fieldMapperDTO);		 		  
				  if(updateRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改表格字段映射明细失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("修改表格字段映射明细成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改表格字段映射明细失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-20
     * funtion:根据Code删除表格映射
     * author:xiaozhan
     */  
	@RequestMapping(value = "/deleteTableMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据Code删除表格映射接口", notes = "根据Code删除表格映射接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteTableMapper(@RequestBody TableMapperDTO tableMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(tableMapperDTO.getCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细编码不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteTableMapper", Integer.class,tableMapperDTO);		 		  
				  if(delRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除表格映射失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("删除表格映射成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除表格映射失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-20
     * funtion:根据Code删除表格字段映射
     * author:xiaozhan
     */  
	@RequestMapping(value = "/deleteFieldMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据Code删除表格映字段射接口", notes = "根据Code删除表格字段映射接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteFieldMapper(@RequestBody FieldMapperDTO fieldMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(fieldMapperDTO.getCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格字段明细编码不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格字段明细编码不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteFieldMapper", Integer.class,fieldMapperDTO);		 		  
				  if(delRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除表格字段映射失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("删除表格字段映射成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除表格字段映射失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-20
     * funtion:根据id删除枚举值映射明细
     * author:xiaozhan
     */  
	@RequestMapping(value = "/deleteEnumMapper.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据id删除枚举值映射明细接口", notes = "根据id删除枚举值映射明细接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteEnumMapper(@RequestBody EnumMapperDTO enumMapperDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(enumMapperDTO.getIdList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "枚举值映射id不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getIdList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "枚举值映射id不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteEnumMapper", Integer.class,enumMapperDTO);		 		  
				  if(delRes==-1) {
					msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除枚举值映射明细失败");			   				    
				  }else {			    
					msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					msg.setDescription("删除枚举值映射明细成功");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除枚举值映射明细失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-25
     * funtion:新建数据字典(主明细信息同时插入)
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典接口", notes = "新建数据字典接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicTc()==null ||  StringUtils.isBlank(dataDicDTO.getDicTc())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表繁体文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDataDicDTOList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键值参数不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDataDicDTOList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键值不能为空", Integer.class).toJson();
		}
		for(int i=0;i<dataDicDTO.getDataDicDTOList().size();i++) {
			if(dataDicDTO.getDataDicDTOList().get(i).getDicKey()==null || "".equals(dataDicDTO.getDataDicDTOList().get(i).getDicKey())) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键不能为空", Integer.class).toJson();
			}
			if(dataDicDTO.getDataDicDTOList().get(i).getDicValue()==null || "".equals(dataDicDTO.getDataDicDTOList().get(i).getDicValue())) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典中文值不能为空", Integer.class).toJson();
			}
			if(dataDicDTO.getDataDicDTOList().get(i).getDicEnValue()==null || "".equals(dataDicDTO.getDataDicDTOList().get(i).getDicEnValue())) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典英文值不能为空", Integer.class).toJson();
			}
			if(dataDicDTO.getDataDicDTOList().get(i).getDicTcValue()==null || "".equals(dataDicDTO.getDataDicDTOList().get(i).getDicTcValue())) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典繁文值不能为空", Integer.class).toJson();
			}
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addDataDic", Integer.class, dataDicDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==-1) {
					//添加数据字典功能失败
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("添加数据字典失败");
				  }else if(insertRes==-2){
					//添加数据字典功能失败
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("您添加的值域已经存在,不能重复添加");
				  }else {
				    //插入成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("添加数据字典成功");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加数据字典失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-03-25
     * funtion:新建数据字典(主表)(type 0代表系统的数据字典，1代表是用户自建的数据字典)
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addMainDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典接口(主表)", notes = "新建数据字典接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addMainDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicTc()==null ||  StringUtils.isBlank(dataDicDTO.getDicTc())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表繁体文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addMainDataDic", Integer.class, dataDicDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==-1) {
					//添加数据字典功能失败
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("添加数据字典失败");
				  }else if(insertRes==-2){
					//添加数据字典功能失败
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("您添加的值域已经存在,不能重复添加");
				  }else {
				    //插入成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("添加数据字典成功");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加数据字典失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-03-25
     * funtion:生成数据字典Key
     * author:xiaozhan
     */  
	@RequestMapping(value = "/createDataKey.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典接口(主表)", notes = "新建数据字典接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String createDataKey(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}			
		 MessageBean<String> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, String.class);	       
		//执行新数据字典功能的操作
		  try{
			  String finalKey=ADOConnection.runTask(user.getEnv(),userService, "createDataKey", String.class, dataDicDTO);		 
			  if(!"".equals(finalKey)) {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			      msg.setDescription("生成数据字典key成功");
			      msg.setData(finalKey); 
			  }else {
				//添加数据字典功能失败
				  msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			      msg.setDescription("生成数据字典key失败");
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("生成字典key失败");
	        }
		
	     return msg.toJson();
	}
	
	
	
	/*
     * date:2020-03-25
     * funtion:新建数据字典(明细信息)
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addDetDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典明细接口", notes = "新建数据字典明细接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addDetDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicKey()==null || StringUtils.isBlank(dataDicDTO.getDicKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键不能为空", Integer.class).toJson();
		}        
		if(dataDicDTO.getDicValue()==null || StringUtils.isBlank(dataDicDTO.getDicValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典中文值不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEnValue()==null || StringUtils.isBlank(dataDicDTO.getDicEnValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典英文值不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicTcValue()==null || StringUtils.isBlank(dataDicDTO.getDicTcValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典繁体值不能为空", Integer.class).toJson();
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addDetDataDic", Integer.class, dataDicDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==-1) {
					//添加数据字典功能失败
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("添加数据字典失败");
				  }else if(insertRes==-2){
					//添加数据字典功能失败
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("添加数据字典key重复");
				  }else {
				    //插入成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("添加数据字典成功");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加数据字典失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-25
     * funtion:查询数据字典接口说明(通过名称标识等等,这个查询的是明细信息)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询数据字典接口(明细信息)", notes = "查询数据字典接口(明细信息)", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	 
		 if(dataDicDTO.getDicParent()==null || "".equals(dataDicDTO.getDicParent())) {
			 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		     msg.setDescription("查询到相关数据字典键值的信息"); 
		     msg.setData(new ArrayList<DataDicVO>());
		     return msg.toJson();
		 }
		 //执行查询数据字典
		 try {
			 List<DataDicVO> dicList=ADOConnection.runTask(user.getEnv(),userService, "queryDataDic", List.class, dataDicDTO);
			 if(dicList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关数据字典键值的信息"); 
			     msg.setData(dicList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到该数据字典键值的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询数据字典键值失败");
	     }
		 return msg.toJson();
		 
	}
	

	 /*
    * date:2020-03-26
    * funtion:查询数据字典接口说明(通过名称标识等等,这个查询的是主表信息) 分页
    * author:xiaozhan
    */
	@RequestMapping(value = "/queryMainDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
   @ApiOperation(value = "查询数据字典接口(主表信息)", notes = "查询数据字典接口(主表信息)", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
   @ResponseBody
	public String queryMainDataDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询数据字典
		 try {
			 PageListVO dicVO=ADOConnection.runTask(user.getEnv(),userService, "queryMainDataDic", PageListVO.class, dataDicDTO);
			 if(dicVO!=null && dicVO.getRowNumber()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关数据字典的信息"); 
			     msg.setData(dicVO);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关数据字典的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询数据字典失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-27
     * funtion:通过字典主表parent修改数据字典接口(主表信息))说明
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通过字典主表parent修改数据字典接口(主表信息))接口", notes = "通过字典主表parent修改数据字典接口(主表信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateDic(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(dataDicDTO.getDicParent()==null || "".equals(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicTc()==null ||  StringUtils.isBlank(dataDicDTO.getDicTc())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表繁体文名称不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行查询数据字典
		 try {
			 Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateDic", Integer.class, dataDicDTO);
			 if(updateRes!=null) {
				  if(updateRes==-1) {
					//修改数据字典失败
				    msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改数据字典失败");
				  }else {
				    //修改数据字典成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("修改数据字典成功");
				  }
			  }
		 }catch(Exception e){
	     	//修改失败
	     	msg.setCode(Constant.MESSAGE_INT_EDITERROR);
	        msg.setDescription("修改这条数据字典失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-27
     * funtion:通过字典主表Parent删除数据字典接口(主表信息)(批量)说明
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteDicByParent.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "主表parent删除数据字典详情(主表信息)接口", notes = "主表parent删除数据字典详情(主表信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteDicByParent(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(dataDicDTO.getDicParentList()==null) {
			  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域参数不能为空", Integer.class).toJson();
			}	
		if(dataDicDTO.getDicParentList().size()<1) {
		  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行删除数据字典
		 try {
			 Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteDicByParent", Integer.class, dataDicDTO);
			 if(delRes!=null) {
				  if(delRes==-1) {
					//删除数据字典失败
				    msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除数据字典失败");
				  }else {
				    //删除数据字典成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("删除数据字典成功");
				  }
			  }
		 }catch(Exception e){
	     	//删除失败
	     	msg.setCode(Constant.MESSAGE_INT_DELERROR);
	        msg.setDescription("删除数据字典失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-27
     * funtion:通过字典主表id修改数据字典明细接口(明细信息))说明
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateDicDetById.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通过字典主表id修改数据字典接口(明细信息))接口", notes = "通过字典主表id修改数据字典接口(明细信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateDicDetById(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(dataDicDTO.getDicId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表ID不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicKey()==null || StringUtils.isBlank(dataDicDTO.getDicKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表键不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicValue()==null || StringUtils.isBlank(dataDicDTO.getDicValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文值不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEnValue()==null || StringUtils.isBlank(dataDicDTO.getDicEnValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典英文值不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicTcValue()==null || StringUtils.isBlank(dataDicDTO.getDicTcValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典繁体值不能为空", Integer.class).toJson();
		}
				
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行修改数据字典明细
		 try {
			 Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateDicDetById", Integer.class, dataDicDTO);
			 if(updateRes!=null) {
				  if(updateRes==-1) {
					//修改数据字典失败
				    msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改数据字典失败");
				  }else {
				    //修改数据字典成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("修改数据字典成功");
				  }
			  }
		 }catch(Exception e){
	     	//修改失败
	     	msg.setCode(Constant.MESSAGE_INT_EDITERROR);
	        msg.setDescription("修改这条数据字典失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-27
     * funtion:通过字典主表key删除数据字典接口(明细信息)(批量)说明(通过Key等等)
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteDetDicByKey.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "主表key删除数据字典详情(明细信息)接口", notes = "主表key删除数据字典详情(明细信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteDetDicByKey(@RequestBody DataDicDTO dataDicDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(dataDicDTO.getDicKeyList()==null) {
			  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表key参数不能为空", Integer.class).toJson();
			}	
		if(dataDicDTO.getDicKeyList().size()<1) {
		  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表key列表不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行删除数据字典
		 try {
			 Integer delRes=ADOConnection.runTask(user.getEnv(),userService, "deleteDetDicByKey", Integer.class, dataDicDTO);
			 if(delRes!=null) {
				  if(delRes==-1) {
					//删除数据字典失败
				    msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除数据字典失败");
				  }else {
				    //删除数据字典成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("删除数据字典成功");
				  }
			  }
		 }catch(Exception e){
	     	//删除失败
	     	msg.setCode(Constant.MESSAGE_INT_DELERROR);
	        msg.setDescription("删除数据字典失败");
	     }
		 return msg.toJson();
		 
	}
	 /*
     * date:2020-03-27
     * funtion:新建特征日接口功能描述
     * author:xiaozhan
     */
	@RequestMapping(value = "/addSpecialDate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建特征日接口", notes = "新建特征日接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addSpecialDate(@RequestBody SpecialDayDTO specialDayDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(specialDayDTO.getSpName()==null || StringUtils.isBlank(specialDayDTO.getSpName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}
		if(specialDayDTO.getSpDate()==null || StringUtils.isBlank(specialDayDTO.getSpDate())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行添加特征日的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(user.getEnv(),userService, "addSpecialDate", Integer.class, specialDayDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==-1) {
					//添加特征日失败
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("添加特征日失败");
				  }else if(insertRes==-2){
					//添加特征日失败
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("重复添加特征日");  
				  }else {
				    //添加特征日成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("添加特征日成功");
				  }
			  }else {
				//添加特征日失败
				 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				 msg.setDescription("添加失败，请检查是否登录");  
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("添加特征日失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-03-27
     * funtion:查询某年某月特征日接口功能描述
     * author:xiaozhan
     */
	@RequestMapping(value = "/querySpecialDate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询某年某月特征日接口", notes = "查询某年某月特征日接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String querySpecialDate(@RequestBody SpecialDayDTO specialDayDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(specialDayDTO.getStartTime()==null || StringUtils.isBlank(specialDayDTO.getStartTime())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日开始时间不能为空", Integer.class).toJson();
		}
		if(specialDayDTO.getEndTime()==null || StringUtils.isBlank(specialDayDTO.getEndTime())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日结束时间不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询一个月特征日的操作
		  try{
			  List<SpecialDayDTO> specialDayDTOList=ADOConnection.runTask(user.getEnv(),userService, "querySpecialDate", List.class, specialDayDTO);		 
			  if(specialDayDTOList.size()>0) {			 
					//查询特征日成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询特征日成功");
				    msg.setData(specialDayDTOList);
			   }else {
				    //查询特征日失败
			        msg.setCode(Constant.MESSAGE_INT_ERROR);
			        msg.setDescription("查询特征日失败");
				}
			  
	        }catch(Exception e){
	        	//查询特征日失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询特征日失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-27
     * funtion:根据日期查询特征日接口
     * author:xiaozhan
     */
	@RequestMapping(value = "/querySpecialDateByDay.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据日期查询特征日接口", notes = "根据日期查询特征日接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String querySpecialDateByDay(@RequestBody SpecialDayDTO specialDayDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(specialDayDTO.getSpDate()==null || "".equals(specialDayDTO.getSpDate())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询某个特征日的操作
		  try{
			  List<SpecialDayDTO> specialDayDTOList=ADOConnection.runTask(user.getEnv(),userService, "querySpecialDateByDay", List.class, specialDayDTO);		 
			  if(specialDayDTOList.size()>0) {			 
					//查询特征日成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询特征日成功");
				    msg.setData(specialDayDTOList);
			   }else {
				    //查询特征日失败
			        msg.setCode(Constant.MESSAGE_INT_ERROR);
			        msg.setDescription("查询特征日失败");
				}
			  
	        }catch(Exception e){
	        	//查询特征日失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询特征日失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-03-30
     * funtion:删除特征日接口功能描述
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteSpecialDate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除特征日接口", notes = "删除特征日接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteSpecialDate(@RequestBody SpecialDayDTO specialDayDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(specialDayDTO.getSpDate()==null || "".equals(specialDayDTO.getSpDate())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//根据日期执行删除特征日的操作
		  try{
			  Integer deleteRes=ADOConnection.runTask(user.getEnv(),userService, "deleteSpecialDate", Integer.class, specialDayDTO);		 
			  if(deleteRes!=null) {
				  if(deleteRes==-1) {
					//删除特征日失败
				    msg.setCode(Constant.MESSAGE_INT_DELERROR);
				    msg.setDescription("删除特征日失败");
				  }else {
				    //删除特征日成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("删除特征日成功");
				  }
			  }else {
				//删除特征日失败
				  msg.setCode(Constant.MESSAGE_INT_DELERROR);
				  msg.setDescription("删除特征日失败，请检查是否登录"); 
			  }
	        }catch(Exception e){
	        	//删除失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除特征日失败");
	        }
		
	     return msg.toJson();
	}
	
	
	 /*
     * date:2020-03-30
     * funtion:修改特征日接口功能描述
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateSpecialDate.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改特征日接口", notes = "修改特征日接口接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateSpecialDate(@RequestBody SpecialDayDTO specialDayDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(specialDayDTO.getSpDate()==null || "".equals(specialDayDTO.getSpDate())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}	
		if(specialDayDTO.getSpName()==null || "".equals(specialDayDTO.getSpName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//根据id执行修改特征日的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateSpecialDate", Integer.class, specialDayDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==-1) {
					//修改特征日失败
				    msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				    msg.setDescription("修改特征日失败");
				  }else {
				    //修改特征日成功
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("修改特征日成功");
				  }
			  }else {
				//修改特征日失败
				 msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				 msg.setDescription("修改特征日失败,请检查是否登录"); 
			  }
	        }catch(Exception e){
	        	//修改失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改特征日失败");
	        }
		
	     return msg.toJson();
	}
	
	 /** -----------树形组件------------------**/     
		
	 /*
     * date:2020-03-30
     * funtion:生成父节点
     * author:xiaozhan
     */
	@RequestMapping(value = "/addOrgParent.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "生成父节点接口", notes = "生成父节点接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addOrgParent(@RequestBody  LongTreeBean child,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=new Integer(child.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树的类型不能为空", Integer.class).toJson();
		}	
		if(child.getForeignkey()==null || "".equals(child.getForeignkey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "组织的外键不能为空", Integer.class).toJson();
		}
		 MessageBean<LongTreeBean> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		//生成组织的父节点
		  try{
			  LongTreeBean longTreeBean=ADOConnection.runTask(user.getEnv(),new TreeService(), "addNode", LongTreeBean.class, null,child);
			  if(longTreeBean!=null) {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("生成父节点成功");
			        msg.setData(longTreeBean);
			  }
	        }catch(Exception e){
	        	//生成失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("生成父节点失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-03-30
     * funtion:组织下添加部门,部门下添加部门
     * author:xiaozhan
     */
	@RequestMapping(value = "/addTreeDept.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加部门接口", notes = "添加部门接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addTreeDept(@RequestBody  TreeDTO parentBean,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {	
		if(parentBean.getDepName()==null || "".equals(parentBean.getDepName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}
		if(parentBean.getForeignKey()==null || "".equals(parentBean.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "上级部门的外键不能为空", Integer.class).toJson();
		}	
		if(parentBean.getType()==null || "".equals(parentBean.getType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树的类型不能为空", Integer.class).toJson();
		}
		//0代表组织添加部门,1代表部门下添加部门
		if(parentBean.getAddType()==null || "".equals(parentBean.getAddType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "添加类型不能为空", Integer.class).toJson();
		}
		 MessageBean<LongTreeBean> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		  try{			
				  //组织下添加部门的话，先插入SM_department，SM_orgDept表数据		
			      OrgAndDeptDTO orgDeptDTO=new OrgAndDeptDTO();
			      orgDeptDTO.setOrgCode(parentBean.getForeignKey());
			      orgDeptDTO.setDepName(parentBean.getDepName());	
			      Integer finalRes=null;
			      if(parentBean.getAddType()==0) {
			    	finalRes=ADOConnection.runTask(user.getEnv(),userService, "addTreeDept", Integer.class, orgDeptDTO,parentBean.getType().intValue(),parentBean.getForeignKey());
			      }else {
			    	finalRes=ADOConnection.runTask(user.getEnv(),userService, "deptAddTreeDept", Integer.class, orgDeptDTO,parentBean.getType().intValue(),parentBean.getForeignKey());  
			      }				  
			      if(finalRes!=null) {
			    	  if (finalRes==1){
						msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
						msg.setDescription("添加部门成功");					  
			    	  }else {
			    		  msg.setCode(Constant.MESSAGE_INT_ERROR);
						  msg.setDescription("添加部门失败"); 
			    	  }
				  }else {
					 msg.setCode(Constant.MESSAGE_INT_ERROR);
					 msg.setDescription("添加部门失败");
				 }
		 
			 
	        }catch(Exception e){
	        	//生成失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("生成部门失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-30
     * funtion:删除树结构的部门
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteTreeDept.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除树结构的部门接口", notes = "删除树结构的部门接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteTreeDept(@RequestBody  TreeDTO longTreeBean,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=Integer.valueOf(longTreeBean.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "类型不能为空", Integer.class).toJson();
		}	
		if(longTreeBean.getForeignKey()==null || "".equals(longTreeBean.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "节点外键不能为空", Integer.class).toJson();
		}
		//删除的时候  (deleteType  0代表删除组织下部门,1代表部门下删除部门）
		if(longTreeBean.getDeleteType()==null || "".equals(longTreeBean.getDeleteType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "删除类型不能为空", Integer.class).toJson();
		}
		
		 MessageBean<LongTreeBean> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		  try{		
			  DeptAndUserDTO deptAndUserDTO=new DeptAndUserDTO();
			  deptAndUserDTO.setDepCode(longTreeBean.getForeignKey());
			  //删除树结构部门的时候，判断该节点下的是否存在职员,存在的情况下不能删除，根据外键Code
			  Integer res=ADOConnection.runTask(user.getEnv(),userService, "judgeExistUser", Integer.class, deptAndUserDTO,longTreeBean.getType(),longTreeBean.getForeignKey(),false,longTreeBean.getDeleteType().intValue());			  
				if(res==0){
					   msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					   msg.setDescription("删除部门成功"); 
				}else if(res==-2) {
					   msg.setCode(Constant.MESSAGE_INT_DELERROR);
					   msg.setDescription("部门下存在职员,请先删除职员");  
				}else if(res==-3) {
					   msg.setCode(Constant.MESSAGE_INT_DELERROR);
					   msg.setDescription("该部门存在下级单位");  
				}else {
						//删除数结构部门失败
						  msg.setCode(Constant.MESSAGE_INT_DELERROR);
						  msg.setDescription("删除数结构部门失败");  
				} 					 						 		  
	        }catch(Exception e){
	        	//删除失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("删除失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-04-01
     * funtion:修改树结构的部门
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateTreeDept.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改树结构的部门接口", notes = "修改树结构的部门接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateTreeDept(@RequestBody DeptDTO deptDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		//修改的只有部门名称
		if(deptDTO.getDepCode()==null || "".equals(deptDTO.getDepCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		if(deptDTO.getDepName()==null || "".equals(deptDTO.getDepName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateTreeDept", Integer.class, deptDTO);	
			  if(updateRes!=null) {
				  if(updateRes!=-1) {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					   msg.setDescription("修改节点部门成功"); 
				  }else {
					//更新失败
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			        msg.setDescription("修改节点部门失败"); 
				  }
			  }
	        }catch(Exception e){
	        	//更新失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-04-01
     * funtion:查看组织树结构(展开所有)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryTreeOrg.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看组织树接口", notes = "查看组织树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryTreeOrg(@RequestBody TreeDTO treeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=Integer.valueOf(treeDTO.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(treeDTO.getForeignKey()==null || "".equals(treeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeDeptVO> treeBeanList=ADOConnection.runTask(user.getEnv(),new TreeService(), "descendantByCode", List.class,treeDTO.getType(),treeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询组织树成功"); 
					msg.setData(treeBeanList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关组织树"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-04-03
     * funtion:根据部门Code查询部门职员 分页
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryDeptUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据部门Code查询职员接口", notes = "根据部门Code查询职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDeptUser(@RequestBody DeptDTO deptDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(deptDTO.getDepCode()==null || "".equals(deptDTO.getDepCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}		
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{				
			  PageListVO pageListVO=ADOConnection.runTask(user.getEnv(),userService, "queryDeptUser", PageListVO.class,deptDTO);	
			  if(pageListVO!=null && pageListVO.getRowNumber()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询职员成功"); 
					msg.setData(pageListVO);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关职员"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-07
     * funtion:通过此接口生成菜单模块。
     * author:xiaozhan
     */
	@RequestMapping(value = "/addMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "生成菜单模块接口", notes = "生成菜单模块接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addMenu(@RequestBody MenuTreeDTO menuTreeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(menuTreeDTO.getForeignKey()==null || "".equals(menuTreeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "父部门外键不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getModuleName()==null || "".equals(menuTreeDTO.getModuleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块菜单名称不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getModuleNo()==null || "".equals(menuTreeDTO.getModuleNo())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块编号不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getType()==null || "".equals(menuTreeDTO.getType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树类型不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getLinkAddress()==null || "".equals(menuTreeDTO.getLinkAddress())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "链接地址不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getSequence()==null || "".equals(menuTreeDTO.getSequence())) {
		    return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块菜单顺序不能为空", Integer.class).toJson();
		}
		String fields =menuTreeDTO.getSequence().toString();
		//菜单顺序只能数字
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher number = pattern.matcher(fields);
		if (!number.matches()) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块菜单顺序只能填入数字", Integer.class).toJson();
		 }
		 MessageBean<MenuDTO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, MenuDTO.class);
		  try{
			  MenuDTO addRes=ADOConnection.runTask(user.getEnv(),userService, "addMenu", MenuDTO.class,menuTreeDTO);
			  if(addRes!=null) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("添加菜单成功");
					msg.setData(addRes);
			   }else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("添加菜单失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ADDERROR);
	            msg.setDescription("添加失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-08
     * funtion:查看菜单目录结构(展开所有)(测试使用)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryTreeMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看菜单树接口", notes = "查看菜单树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryTreeMenu(@RequestBody MenuTreeDTO menuTreeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null || "".equals(menuTreeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeDeptVO> treeBeanList=ADOConnection.runTask(user.getEnv(),new TreeService(), "descendantMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关目录树"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-08
     * funtion:查看菜单目录结构(查看下级的菜单)(测试使用)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryChildTreeMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看下级菜单树接口", notes = "查看下级菜单树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryChildTreeMenu(@RequestBody MenuTreeDTO menuTreeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null || "".equals(menuTreeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(user.getEnv(),new TreeService(), "childMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询下级目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到下级目录树"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-08
     * funtion:通过此接口加载该角色所有菜单以及可查看的权限。
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryRoleMenuByRoleCode.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "加载角色菜单查看权限接口", notes = "加载角色菜单查看权限接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryRoleMenuByRoleId(@RequestBody RoleDTO roleDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(roleDTO.getRoleCode()==null || "".equals(roleDTO.getRoleCode())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<RoleMenusVO> menuList=ADOConnection.runTask(user.getEnv(),userService, "queryRoleMenuByRoleCode", List.class,roleDTO);	
			  if(menuList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询角色菜单查看权限成功"); 
					msg.setData(menuList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到角色查看菜单权限"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-04-10
     * funtion:通过角色code和模块code加载该角色所有菜单以及可操作的权限。(选择第二个框的时候)
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryRoleMenuByRoleMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "加载角色菜单操作权限接口", notes = "加载角色菜单操作权限接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryRoleMenuByRoleMenu(@RequestBody RoleMenuDTO roleMenuDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		if(roleMenuDTO.getRoleCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		if(roleMenuDTO.getModuleCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块编码不能为空", Integer.class).toJson();
		}		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);
		 if(roleMenuDTO.getModuleCodeList().size()<1) {
			    List<RoleMenusVO> data=new ArrayList<RoleMenusVO>();
			    msg.setData(data);
				return  msg.toJson();
	     }
		  try{				
			  List<RoleMenusVO> menuCDUList=ADOConnection.runTask(user.getEnv(),userService, "queryRoleMenuByRoleMenu", List.class,roleMenuDTO);	
			  if(menuCDUList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询角色菜单操作权限成功"); 
					msg.setData(menuCDUList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到角色操作菜单权限"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	/*
     * date:2020-04-08
     * funtion:通过此接口修改该角色所有菜单以及打勾的权限。
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateRoleMenuByRoleCode.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改角色菜单权限接口", notes = "修改角色菜单权限接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateRoleMenuByRoleCode(@RequestBody RoleMenuDTO roleMenuDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(roleMenuDTO.getRoleMenuList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "传参不能为空", Integer.class).toJson();
		}
		if(roleMenuDTO.getRoleMenuList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "传参不能为空", Integer.class).toJson();
		}
		if(roleMenuDTO.getRoleMenuList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "传参不能为空", Integer.class).toJson();
		}
		for(int i=0;i<roleMenuDTO.getRoleMenuList().size();i++) {
			if(roleMenuDTO.getRoleMenuList().get(i).getRoleCode()==null) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
			}	
			if(roleMenuDTO.getRoleMenuList().get(i).getModuleCode()==null) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "菜单编码不能为空", Integer.class).toJson();
			}
			
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateRoleMenuByRoleCode", Integer.class,roleMenuDTO);	
			  if(updateRes!=-1) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("修改角色菜单权限成功"); 					
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("修改角色菜单权限失败"); 
			 }		  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("修改失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-09
     * funtion:模糊查询部门接口
     * author:xiaozhan
     */	
	@RequestMapping(value = "/queryDept.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "模糊查询部门接口", notes = "模糊查询部门接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDept(@RequestBody DeptDTO deptDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<DeptVO> deptList=ADOConnection.runTask(user.getEnv(),userService, "queryDept", List.class,deptDTO);	
			  if(deptList!=null && deptList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询部门成功"); 
					msg.setData(deptList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关部门"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	
	/*
     * date:2020-04-09
     * funtion:查询职位接口
     * author:xiaozhan
     */	
	@RequestMapping(value = "/queryPosition.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询职位接口", notes = "查询职位接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryPosition(@RequestBody PositionDTO positionDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<PositionVO> positionList=ADOConnection.runTask(user.getEnv(),userService, "queryPosition", List.class,positionDTO);	
			  if(positionList!=null && positionList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询职位成功"); 
					msg.setData(positionList);
				  }else if(positionList == null){
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关职位"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-08
     * funtion:根据userCode可查看菜单目录结构(查看下级的菜单)加入菜单权限,类似查询一级菜单
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryChildOneMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看一级菜单树接口", notes = "查看一级菜单树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryChildOneMenu(@RequestBody MenuTreeDTO menuTreeDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null || "".equals(menuTreeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		//TODO 测试用
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(user.getEnv(),new TreeService(), "queryChildOneMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询一级目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关一级目录树"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	
	/*
     * date:2020-04-08
     * funtion:根据userCode可查看菜单目录结构(查看所有下级的菜单)加入菜单权限,类似查询一级菜单下所有下级菜单
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryChildAllMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看一级菜单下可查看所有下级树接口", notes = "查看一级菜单下可查看所有下级树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryChildAllMenu(@RequestBody MenuTreeDTO menuTreeDTO,@StaffAttribute(Constant.LOGIN_USER) UserVO user) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null || "".equals(type)) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null || "".equals(menuTreeDTO.getForeignKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		//TODO 测试用
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(user.getEnv(),new TreeService(), "queryChildAllMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询所有下级树成功"); 
					msg.setData(treeBeanList);
				  }else {					
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("没有查询到相关所有下级树"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
	/*
     * date:2020-04-22
     * funtion:下载部门职员数据导出到Excel
     * author:xiaozhan
     */
	@RequestMapping(value = "/downUserDataExcel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载部门职员数据导出到Excel", notes = "下载部门职员数据导出到Excel", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downUserDataExcel(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			QueryUserDTO userDTO = jsonValue.fromJson(objValue, QueryUserDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			userDTO.setPage(1);
			userDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
		    PageListVO<List<UserVO>> result=ADOConnection.runTask(user.getEnv(),userService, "queryUser", PageListVO.class, userDTO); 		   
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(result.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
     * date:2020-04-22
     * funtion:下载角色职员数据导出到Excel
     * author:xiaozhan
     */
	@RequestMapping(value = "/downRoleUserDataExcel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载角色职员数据导出到Excel", notes = "下载角色职员数据导出到Excel", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downRoleUserDataExcel(@RequestParam String objValue,@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询条件字符串转对象，查询数据结果
			RoleDTO userDTO = jsonValue.fromJson(objValue, RoleDTO.class);
			// 调用系统设置方法，获取导出数据条数上限，设置到分页参数中，//暂时默认
			userDTO.setPage(1);
			userDTO.setPageCount(Constant.DOWN_MAX_LIMIT);
			// 查询到导出数据结果
		    PageListVO<List<UserVO>> result=ADOConnection.runTask(user.getEnv(),userService, "queryUserByRoleCode", PageListVO.class, userDTO); 		   
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(result.getDataList(), jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
     * date:2020-04-22
     * funtion:下载角色数据导出到Excel
     * author:xiaozhan
     */
	@RequestMapping(value = "/downRoleDataExcel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载角色数据导出到Excel", notes = "下载角色数据导出到Excel", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downRoleDataExcel(@RequestParam String titleInfos,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		try{
			Gson jsonValue = new Gson();
			// 查询到导出数据结果
		    List<RoleVO> roleList=ADOConnection.runTask(user.getEnv(),userService, "queryAllRole", List.class); 		   
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {
					}.getType());
			// 导出excel文件
			//导出list
			return ExportDataUtil.getExcelDataFileInfoByList(roleList, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
     * date:2020-04-22
     * funtion:下载职员导入的空模板的excel
     * author:xiaozhan
     */
	@RequestMapping(value = "/downUserTemplateExcel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "下载职员导入的空模板的excel", notes = "下载职员导入的空模板的excel", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public HttpEntity<?> downUserTemplateExcel() {
		try{		
			Gson jsonValue = new Gson();
			// 查询到导出数据结果
			List<UserVO> userList=new  ArrayList<UserVO>();  
			//TODO
	        String titleInfos= "[{titleName:\"职员名(必填)\",titleValue:\"name\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"登录名称(必填)\",titleValue:\"loginName\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"工号(必填)\",titleValue:\"workNo\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"性别\",titleValue:\"sex\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"手机号\",titleValue:\"photo\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"职位\",titleValue:\"position\",changeValue:\"1000\"}," + 
	        		"	        {titleName:\"邮箱\",titleValue:\"email\",changeValue:\"1000\"}" + 
	        		"	        ]		";
			List<Map<String, String>> jsonArray = jsonValue.fromJson(titleInfos,new TypeToken<List<Map<String, String>>>() {}.getType());								
			// 导出excel文件
			return ExportDataUtil.getExcelDataFileInfoByList(userList, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
     * date:2020-04-22
     * funtion：添加excel时候导入用户数据
     * author:xiaozhan
     */
	@RequestMapping(value = "/addImportUserDataExcel.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "添加excel导入用户数据接口", notes = "添加excel导入数据接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addImportUserDataExcel(MultipartFile file,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 MessageBean<ImportUserResVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, ImportUserResVO.class);	       
		  try{		
			  List<UserExcelDTO> userList=ImportExcelUtil.readExcel(file,UserExcelDTO.class);
			  if(userList==null) {
				 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "插入的数据不能为空", Integer.class).toJson();  
			  }
			  if(userList.size()<0) {
				 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "插入的数据不能为空", Integer.class).toJson();
			  }
			  for(int i=0;i<userList.size();i++) {
				  if(userList.get(i).getName()==null || StringUtils.isBlank(userList.get(i).getName())) {
						return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
					}
					if(userList.get(i).getLoginName()==null || StringUtils.isBlank(userList.get(i).getLoginName())) {
						return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
					}
					if(userList.get(i).getWorkNo()==null || StringUtils.isBlank(userList.get(i).getWorkNo())) {
						return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "工号不能为空", Integer.class).toJson();
					}					
			  }
			  ImportUserResVO importUserRes=ADOConnection.runTask(user.getEnv(),userService, "addImportUserDataExcel", ImportUserResVO.class,userList);	
			  if(importUserRes.getResult()==0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("批量导入用户数据成功");
					msg.setData(importUserRes);
			  }else if(importUserRes.getResult()==-2){
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("批量导入失败,登录名重复"); 
			        msg.setData(importUserRes);
			  }	else if(importUserRes.getResult()==-3){
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("批量导入失败,工号重复"); 
			        msg.setData(importUserRes);
			  }else if(importUserRes.getResult()==-4){
				   msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("批量导入失败,导入的表存在登录名重复,请检查excel"); 
			        msg.setData(importUserRes);
			  }else if(importUserRes.getResult()==-5){
				   msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("批量导入失败,导入的表存在工号重复,请检查excel"); 
			        msg.setData(importUserRes);
			  }
			  else {
				   msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			       msg.setDescription("导入失败"); 
			  }		  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("导入失败");
	        }
		
	     return msg.toJson();
	}
	 /*
     * date:2020-03-20
     * funtion:个人修改密码
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/updateMyPassword.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "个人修改密码接口", notes = "个人修改密码接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateMyPassword(@RequestBody UpdateWordDTO updateWordDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(updateWordDTO.getOldPassWord()==null || "".equals(updateWordDTO.getOldPassWord())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "旧密码不能为空", Integer.class).toJson();
		}
		if(updateWordDTO.getNewPassWord()==null || "".equals(updateWordDTO.getNewPassWord())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "旧密码不能为空", Integer.class).toJson();
		}
		if(updateWordDTO.getSurePassWord()==null || "".equals(updateWordDTO.getSurePassWord())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "旧密码不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       		
		  try{
			  Integer updateRes=ADOConnection.runTask(user.getEnv(),userService, "updateMyPassword", Integer.class, updateWordDTO);		 
			 
				  if(updateRes==1) {					
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("重置密码成功");
				  }else if(updateRes==-2){
					  msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				      msg.setDescription("两次输入的密码不一致");
				  }else if (updateRes==-3){
					  msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				      msg.setDescription("新密码和旧密码同样");
				  }else if(updateRes==-4){
					  msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				      msg.setDescription("8位到16位,包含大写小写字母，数字，特殊字符");
				  } 			  				  
				  else  if(updateRes==-5){
					  msg.setCode(Constant.MESSAGE_INT_EDITERROR);
				      msg.setDescription("旧密码输入不正确，请重新输入旧密码");
				  }else {				   
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			        msg.setDescription("重置密码失败");
				  }
			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("重置失败");
	        }
		
	     return msg.toJson();
	}
	
	 /**
   	 * 上传头像
   	 *
   	 * 上传头像的模块类型
   	 * @param file
   	 */
   	@RequestMapping(value = "/uploadHeadPortrait.htm", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
   	@ResponseBody
   	public String uploadFile(@RequestParam("file") MultipartFile file,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
   	if(file.isEmpty()) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "头像不能为空", Integer.class).toJson();
	} 		
   	 MessageBean<UploadFileVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, UploadFileVO.class);	         	
	 try {
		 Integer addRes=ADOConnection.runTask(user.getEnv(),userService, "uploadHeadPortrait", Integer.class, file);
		 if(addRes==-2) {
			 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
		     msg.setDescription("删除之前头像失败"); 
		 }else if(addRes==-3){
			 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
		     msg.setDescription("上传头像文件类型不对"); 
		 }else if(addRes==1) {
			 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		     msg.setDescription("上传头像文件成功"); 
		 }else{
			 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
		     msg.setDescription("上传头像失败");  
		 }
	 }catch(Exception e){
     	msg.setCode(Constant.MESSAGE_INT_ERROR);
        msg.setDescription("上传失败");
     }
	 return msg.toJson();
   	}
   	
   	/**
   	 * 2020-05-06
   	 * 根据登录的用户查询头像
   	 * @author xiaozhan
   	 */
   	@RequestMapping(value = "/queryHeadPortrait.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询头像接口", notes = "查询头像接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryHeadPortrait(@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		 try {
			 List<UploadFileNewVO> fileList=ADOConnection.runTask(user.getEnv(),userService, "queryHeadPortrait", List.class);
			 if(fileList!=null && fileList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关头像信息"); 
			     msg.setData(fileList);
			 }else {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("该职员没有查询到头像信息"); 
			 }
		 }catch(Exception e){
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("查询失败");
	     }
		 return msg.toJson();
		 
	}
   	
    /*
     * date:2020-05-21
     * funtion:修改菜单的平级顺序,平级顺序不能重复，重复不让修改
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateMenuPeersSeq.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "更新菜单平级顺序接口", notes = "更新菜单平级顺序接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateMenuPeersSeq(@RequestBody MenuSeqDTO menuSeqDTO,@StaffAttribute(Constant.LOGIN_USER)UserVO user) {
		if(menuSeqDTO.getForeignkey()==null || "".equals(menuSeqDTO.getForeignkey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "菜单编码不能为空", Integer.class).toJson();
		}
		if(menuSeqDTO.getType()==null || "".equals(menuSeqDTO.getType())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树类型不能为空", Integer.class).toJson();
		}
		if(menuSeqDTO.getMenuSequence()==null || "".equals(menuSeqDTO.getMenuSequence())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "菜单修改后的顺序值不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 try {
			 Integer updateRes=ADOConnection.runTask(user.getEnv(),new TreeService(), "updateMenuPeersSeq", Integer.class, menuSeqDTO);
			 if(updateRes>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("更新该菜单平级顺序成功"); 
			 }else if(updateRes==-2){
				 msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			     msg.setDescription("更新该菜单平级顺序失败，同级菜单出现顺序重复"); 
			 }else {
				 msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			     msg.setDescription("更新该菜单平级顺序失败"); 
			 }
		 }catch(Exception e){
	     	msg.setCode(Constant.MESSAGE_INT_ERROR);
	        msg.setDescription("更新该菜单平级顺序失败");
	     }
		 return msg.toJson();
		 
	}
	
	
}
