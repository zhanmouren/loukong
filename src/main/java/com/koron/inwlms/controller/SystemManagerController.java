package com.koron.inwlms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.authority.ValidatePermission;
import com.koron.common.web.mapper.LongTreeBean;
import com.koron.common.web.service.TreeService;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.EnumMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.FieldMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.IntegrationConfDTO;
import com.koron.inwlms.bean.DTO.sysManager.MenuTreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.OrgAndDeptDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleMenuDTO;
import com.koron.inwlms.bean.DTO.sysManager.SpecialDayDTO;
import com.koron.inwlms.bean.DTO.sysManager.TableMapperDTO;
import com.koron.inwlms.bean.DTO.sysManager.TreeDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.bean.VO.sysManager.DeptVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationConfVO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMenusVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.TreeDeptVO;
import com.koron.inwlms.bean.VO.sysManager.TreeMenuVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.sysManager.UserService;
import com.koron.inwlms.service.sysManager.impl.UserServiceImpl;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统管理Controller层
 * @author xiaozhan
 * @Date 2020.03.18
 */

@RestController
@Api(value = "systemManager", description = "系统管理Controller")
@RequestMapping(value = "/systemManager")
public class SystemManagerController {
	
	@Autowired
	private UserService userService;
     
	 /*
     * date:2020-03-18
     * funtion:管理员添加新职员接口
     * author:xiaozhan
     */
	@RequestMapping(value = "/addUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "管理员添加新职员接口", notes = "管理员添加新职员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addUser(@RequestBody UserDTO userDTO) {
		if(userDTO.getName()==null || StringUtils.isBlank(userDTO.getName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
		}
		if(userDTO.getLoginName()==null || StringUtils.isBlank(userDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
		}
		if(userDTO.getWorkNo()==null || StringUtils.isBlank(userDTO.getWorkNo())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "工号不能为空", Integer.class).toJson();
		}
		if(userDTO.getSex()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "性别不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行插入职员的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addUser", Integer.class, userDTO);		 
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
	public String queryUser(@RequestBody QueryUserDTO userDTO) {
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO user=ADOConnection.runTask(userService, "queryUser", PageListVO.class, userDTO);
			 if(user!=null  && user.getRowNumber()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关职员的信息"); 
			     msg.setData(user);
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
	public String updateUser(@RequestBody UserDTO userDTO) {
		if(userDTO.getCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码不能为空", Integer.class).toJson();
		}
		if(userDTO.getName()==null || StringUtils.isBlank(userDTO.getName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
		}
		if(userDTO.getLoginName()==null || StringUtils.isBlank(userDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
		}
		if(userDTO.getSex()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "性别不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行修改职员的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(userService, "updateUser", Integer.class, userDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {
					//修改用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("修改用户成功");
				  }else {
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
	public String updateUserPassword(@RequestBody UserDTO userDTO) {
		if(userDTO.getUserCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码列表参数不能为空", Integer.class).toJson();
		}
		if(userDTO.getUserCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员编码列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行批量重置密码的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(userService, "updateUserPassword", Integer.class, userDTO);		 
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
	public String  deleteUser(@RequestBody UserDTO userDTO) {
		if(userDTO.getCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员的编码不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除职员的操作
		  try{
			  Integer delRes=ADOConnection.runTask(userService, "deleteUser", Integer.class, userDTO);		 
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
	public String addNewRole(@RequestBody RoleDTO roleDTO) {
		if(roleDTO.getRoleName()==null || StringUtils.isBlank(roleDTO.getRoleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行插入角色的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addNewRole", Integer.class, roleDTO);		 
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
	public String updateRoleAttr(@RequestBody RoleDTO roleDTO) {
		if(roleDTO.getRoleCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		if(roleDTO.getRoleName()==null || StringUtils.isBlank(roleDTO.getRoleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();
		}	
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer updateRes=ADOConnection.runTask(userService, "updateRoleAttr", Integer.class, roleDTO);		 
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
	public String deleteRoleAttr(@RequestBody RoleDTO roleDTO) {
		if(roleDTO.getRoleCodeList()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色列表code参数不能为空", Integer.class).toJson();
		}	
		if(roleDTO.getRoleCodeList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色列表code不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除角色的操作
		  try{
			  RoleMsgVO roleMsgVO=ADOConnection.runTask(userService, "deleteRoleAttr", RoleMsgVO.class, roleDTO);		 
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
	public String queryUserByRoleId(@RequestBody RoleDTO roleDTO) {
		 if(roleDTO.getRoleCode()==null) {
			 return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		 }
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		//执行删除角色的操作
		  try{
			  PageListVO userVO=ADOConnection.runTask(userService, "queryUserByRoleCode", PageListVO.class, roleDTO);		 
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
	public String queryAllRole() {		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<RoleVO> roleList=ADOConnection.runTask(userService, "queryAllRole", List.class);		 
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
	public String addRoleUser(@RequestBody RoleAndUserDTO roleUserDTO) {	
		if(roleUserDTO.getRoleCode()==null) {
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
			  Integer addResult=ADOConnection.runTask(userService, "addRoleUser", Integer.class, roleUserDTO);		 
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
	public String deleteRoleUser(@RequestBody RoleAndUserDTO roleUserDTO) {	
		if(roleUserDTO.getRoleCode()==null) {
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
			  Integer delResult=ADOConnection.runTask(userService, "deleteRoleUser", Integer.class, roleUserDTO);		 
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
	public String queryExceptRoleUser(@RequestBody RoleAndUserDTO roleUserDTO) {
		if(roleUserDTO.getRoleCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO userVO=ADOConnection.runTask(userService, "queryExceptRoleUser", PageListVO.class, roleUserDTO);
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
	public String queryExceptDeptUser(@RequestBody DeptAndUserDTO deptUserDTO) {
		if(deptUserDTO.getDepCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询职员
		 try {
			 PageListVO userVO=ADOConnection.runTask(userService, "queryExceptDeptUser", PageListVO.class, deptUserDTO);
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
	public String addDeptUser(@RequestBody DeptAndUserDTO deptUserDTO) {	
		if(deptUserDTO.getDepCode()==null) {
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
			  Integer addResult=ADOConnection.runTask(userService, "addDeptUser", Integer.class, deptUserDTO);		 
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
	public String deleteDeptUser(@RequestBody DeptAndUserDTO deptUserDTO) {	
		if(deptUserDTO.getDepCode()==null) {
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
			  Integer delResult=ADOConnection.runTask(userService, "deleteDeptUser", Integer.class, deptUserDTO);		 
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
	public String addIntegration(@RequestBody IntegrationConfDTO integrationConfDTO) {
		if(integrationConfDTO.getOtherJDBC()==null || StringUtils.isBlank(integrationConfDTO.getOtherJDBC())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方JDBC不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getSysName()==null || StringUtils.isBlank(integrationConfDTO.getSysName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方系统名称不能为空", Integer.class).toJson();
		}
		if(integrationConfDTO.getStatus()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "状态不能为空", Integer.class).toJson();
		}
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行集成配置添加功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addIntegration", Integer.class, integrationConfDTO);		 		  
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
	public String addTableMapper(@RequestBody TableMapperDTO tableMapperDTO) {		
		if(tableMapperDTO.getConfigCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getOtherTabCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格编码不能为空", Integer.class).toJson();
		}	
		if(tableMapperDTO.getOtherTabName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方表格名不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格编码不能为空", Integer.class).toJson();
		}
		if(tableMapperDTO.getTableName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方表格名称不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addTableMapper", Integer.class, tableMapperDTO);		 		  
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
    @ApiOperation(value = "添加表格映射功能接口", notes = "添加表格映射功能接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addFieldMapper(@RequestBody FieldMapperDTO fieldMapperDTO) {		
		if(fieldMapperDTO.getTableCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "表格映射明细编码不能为空", Integer.class).toJson();
		}	
		if(fieldMapperDTO.getOtherFieldCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getOtherFieldName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段code不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方字段名称不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFieldType()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段类型不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getFormula()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "字段公式不能为空", Integer.class).toJson();
		}
		if(fieldMapperDTO.getValue()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "code值不能为空", Integer.class).toJson();
		}				
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addFieldMapper", Integer.class, fieldMapperDTO);		 		  
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
	public String addEnumMapper(@RequestBody EnumMapperDTO enumMapperDTO) {		
		if(enumMapperDTO.getConfCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getOtherFieldValue()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "对方字段枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getFieldValue()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "我方枚举编码不能为空", Integer.class).toJson();
		}
		if(enumMapperDTO.getMapper()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "映射方式不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addEnumMapper", Integer.class, enumMapperDTO);		 		  
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
	public String queryIntegration(@RequestBody IntegrationConfDTO integrationConfDTO) {		
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{
			  PageListVO pageListVO=ADOConnection.runTask(userService, "queryIntegration", PageListVO.class,integrationConfDTO);		 		  
				  if(pageListVO!=null &&  pageListVO.getRowNumber()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询集成配置列表信息成功");
				    msg.setData(pageListVO);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询集成配置列表信息失败");
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
	public String queryIntegrationByCode(@RequestBody IntegrationConfDTO integrationConfDTO) {
		if(integrationConfDTO.getInteConfCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "集成配置code不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{
			  List<IntegrationConfVO> integrationConfVOList=ADOConnection.runTask(userService, "queryIntegrationByCode", List.class,integrationConfDTO);		 		  
				  if(integrationConfVOList.size()>0) {
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("查询集成配置详情信息成功");
				    msg.setData(integrationConfVOList);
				  }else {			    
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询集成配置详情信息失败");
				  }			  
	        }catch(Exception e){	        	
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询集成配置详情信息失败");
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
	public String addDataDic(@RequestBody DataDicDTO dataDicDTO) {
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
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
			if(dataDicDTO.getDataDicDTOList().get(i).getDicKey()==null) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键不能为空", Integer.class).toJson();
			}
			if(dataDicDTO.getDataDicDTOList().get(i).getDicValue()==null) {
				return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典值不能为空", Integer.class).toJson();
			}
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addDataDic", Integer.class, dataDicDTO);		 
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
     * funtion:新建数据字典(主表)
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addMainDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典接口(主表)", notes = "新建数据字典接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addMainDataDic(@RequestBody DataDicDTO dataDicDTO) {
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addMainDataDic", Integer.class, dataDicDTO);		 
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
     * funtion:新建数据字典(明细信息)
     * author:xiaozhan
     */  
	@RequestMapping(value = "/addDetDataDic.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "新建数据字典明细接口", notes = "新建数据字典明细接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addDetDataDic(@RequestBody DataDicDTO dataDicDTO) {
		if(dataDicDTO.getDicParent()==null || StringUtils.isBlank(dataDicDTO.getDicParent())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表标识不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicKey()==null || StringUtils.isBlank(dataDicDTO.getDicKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典键不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicValue()==null || StringUtils.isBlank(dataDicDTO.getDicValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典值不能为空", Integer.class).toJson();
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行新数据字典功能的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addDetDataDic", Integer.class, dataDicDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==-1) {
					//添加数据字典功能失败
				    msg.setCode(Constant.MESSAGE_INT_ADDERROR);
				    msg.setDescription("添加数据字典失败");
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
	public String queryDataDic(@RequestBody DataDicDTO dataDicDTO) {
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	 
		 if(dataDicDTO.getDicParent()==null) {
			 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
		     msg.setDescription("查询到相关数据字典键值的信息"); 
		     msg.setData(new ArrayList<DataDicVO>());
		     return msg.toJson();
		 }
		 //执行查询数据字典
		 try {
			 List<DataDicVO> dicList=ADOConnection.runTask(userService, "queryDataDic", List.class, dataDicDTO);
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
	public String queryMainDataDic(@RequestBody DataDicDTO dataDicDTO) {
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		 //执行查询数据字典
		 try {
			 PageListVO dicVO=ADOConnection.runTask(userService, "queryMainDataDic", PageListVO.class, dataDicDTO);
			 if(dicVO!=null) {
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
     * funtion:通过字典主表ID修改数据字典接口(主表信息))说明(通过ID等等)
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateDicById.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通过字典主表ID修改数据字典接口(主表信息))接口", notes = "通过字典主表ID修改数据字典接口(主表信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateDicById(@RequestBody DataDicDTO dataDicDTO) {
		
		if(dataDicDTO.getDicParent()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicCn()==null || StringUtils.isBlank(dataDicDTO.getDicCn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表中文名称不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicEn()==null || StringUtils.isBlank(dataDicDTO.getDicEn())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表英文名称不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行查询数据字典
		 try {
			 Integer updateRes=ADOConnection.runTask(userService, "updateDicById", Integer.class, dataDicDTO);
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
     * funtion:通过字典主表Parent删除数据字典接口(主表信息)(批量)说明(通过ID等等)
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteDicById.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "主表parent删除数据字典详情(主表信息)接口", notes = "主表parent删除数据字典详情(主表信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteDicById(@RequestBody DataDicDTO dataDicDTO) {	
		if(dataDicDTO.getDicParentList()==null) {
			  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域参数不能为空", Integer.class).toJson();
			}	
		if(dataDicDTO.getDicParentList().size()<1) {
		  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值域不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行删除数据字典
		 try {
			 Integer delRes=ADOConnection.runTask(userService, "deleteDicById", Integer.class, dataDicDTO);
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
     * funtion:通过字典主表ID修改数据字典明细接口(明细信息))说明(通过ID等等)
     * author:xiaozhan
     */
	@RequestMapping(value = "/updateDicDetById.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "通过字典主表ID修改数据字典接口(明细信息))接口", notes = "通过字典主表ID修改数据字典接口(明细信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String updateDicDetById(@RequestBody DataDicDTO dataDicDTO) {
		if(dataDicDTO.getDicId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表ID不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicKey()==null || StringUtils.isBlank(dataDicDTO.getDicKey())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表键不能为空", Integer.class).toJson();
		}
		if(dataDicDTO.getDicValue()==null || StringUtils.isBlank(dataDicDTO.getDicValue())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表值不能为空", Integer.class).toJson();
		}
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行修改数据字典明细
		 try {
			 Integer updateRes=ADOConnection.runTask(userService, "updateDicDetById", Integer.class, dataDicDTO);
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
     * funtion:通过字典主表id删除数据字典接口(明细信息)(批量)说明(通过ID等等)
     * author:xiaozhan
     */
	@RequestMapping(value = "/deleteDetDicById.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "主表ID删除数据字典详情(明细信息)接口", notes = "主表ID删除数据字典详情(明细信息))接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String deleteDetDicById(@RequestBody DataDicDTO dataDicDTO) {
		if(dataDicDTO.getDicIdList()==null) {
			  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表ID参数不能为空", Integer.class).toJson();
			}	
		if(dataDicDTO.getDicIdList().size()<1) {
		  return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "数据字典主表ID列表不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		 //执行删除数据字典
		 try {
			 Integer delRes=ADOConnection.runTask(userService, "deleteDetDicById", Integer.class, dataDicDTO);
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
	public String addSpecialDate(@RequestBody SpecialDayDTO specialDayDTO) {
		if(specialDayDTO.getSpName()==null || StringUtils.isBlank(specialDayDTO.getSpName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}
		if(specialDayDTO.getSpDate()==null || StringUtils.isBlank(specialDayDTO.getSpDate())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}
			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行添加特征日的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(userService, "addSpecialDate", Integer.class, specialDayDTO);		 
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
	public String querySpecialDate(@RequestBody SpecialDayDTO specialDayDTO) {
		if(specialDayDTO.getSelectYear()==null || StringUtils.isBlank(specialDayDTO.getSelectYear())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日年份不能为空", Integer.class).toJson();
		}
		if(specialDayDTO.getSelectMonth()==null || StringUtils.isBlank(specialDayDTO.getSelectMonth())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日月份不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询一个月特征日的操作
		  try{
			  List<SpecialDayDTO> specialDayDTOList=ADOConnection.runTask(userService, "querySpecialDate", List.class, specialDayDTO);		 
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
	public String querySpecialDateByDay(@RequestBody SpecialDayDTO specialDayDTO) {
		if(specialDayDTO.getSpDate()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行查询某个特征日的操作
		  try{
			  List<SpecialDayDTO> specialDayDTOList=ADOConnection.runTask(userService, "querySpecialDateByDay", List.class, specialDayDTO);		 
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
	public String deleteSpecialDate(@RequestBody SpecialDayDTO specialDayDTO) {
		if(specialDayDTO.getSpDate()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}			
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//根据日期执行删除特征日的操作
		  try{
			  Integer deleteRes=ADOConnection.runTask(userService, "deleteSpecialDate", Integer.class, specialDayDTO);		 
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
	public String updateSpecialDate(@RequestBody SpecialDayDTO specialDayDTO) {
		if(specialDayDTO.getSpDate()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日日期不能为空", Integer.class).toJson();
		}	
		if(specialDayDTO.getSpName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "特征日名称不能为空", Integer.class).toJson();
		}	
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//根据id执行修改特征日的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(userService, "updateSpecialDate", Integer.class, specialDayDTO);		 
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
	public String addOrgParent(@RequestBody  LongTreeBean child) {
		Integer type=new Integer(child.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树的类型不能为空", Integer.class).toJson();
		}	
		if(child.getForeignkey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "组织的外键不能为空", Integer.class).toJson();
		}
		 MessageBean<LongTreeBean> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		//生成组织的父节点
		  try{
			  LongTreeBean longTreeBean=ADOConnection.runTask(new TreeService(), "addNode", LongTreeBean.class, null,child);
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
	public String addTreeDept(@RequestBody  TreeDTO parentBean) {	
		if(parentBean.getDepName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}
		if(parentBean.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "上级部门的外键不能为空", Integer.class).toJson();
		}	
		if(parentBean.getType()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树的类型不能为空", Integer.class).toJson();
		}
		//0代表组织添加部门,1代表部门下添加部门
		if(parentBean.getAddType()==null) {
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
			    	finalRes=ADOConnection.runTask(userService, "addTreeDept", Integer.class, orgDeptDTO,parentBean.getType().intValue(),parentBean.getForeignKey());
			      }else {
			    	finalRes=ADOConnection.runTask(userService, "deptAddTreeDept", Integer.class, orgDeptDTO,parentBean.getType().intValue(),parentBean.getForeignKey());  
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
	public String deleteTreeDept(@RequestBody  TreeDTO longTreeBean) {
		Integer type=Integer.valueOf(longTreeBean.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "类型不能为空", Integer.class).toJson();
		}	
		if(longTreeBean.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "节点外键不能为空", Integer.class).toJson();
		}
		//删除的时候  (deleteType  0代表删除组织下部门,1代表部门下删除部门）
		if(longTreeBean.getDeleteType()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "删除类型不能为空", Integer.class).toJson();
		}
		
		 MessageBean<LongTreeBean> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, LongTreeBean.class);	       
		  try{		
			  DeptAndUserDTO deptAndUserDTO=new DeptAndUserDTO();
			  deptAndUserDTO.setDepCode(longTreeBean.getForeignKey());
			  //删除树结构部门的时候，判断该节点下的是否存在职员,存在的情况下不能删除，根据外键Code
			  Integer res=ADOConnection.runTask(userService, "judgeExistUser", Integer.class, deptAndUserDTO,longTreeBean.getType(),longTreeBean.getForeignKey(),false,longTreeBean.getDeleteType().intValue());			  
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
	public String updateTreeDept(@RequestBody DeptDTO deptDTO) {
		//修改的只有部门名称
		if(deptDTO.getDepCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门编码不能为空", Integer.class).toJson();
		}
		if(deptDTO.getDepName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		  try{				
			  Integer updateRes=ADOConnection.runTask(userService, "updateTreeDept", Integer.class, deptDTO);	
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
	public String queryTreeOrg(@RequestBody TreeDTO treeDTO) {
		Integer type=Integer.valueOf(treeDTO.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(treeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeDeptVO> treeBeanList=ADOConnection.runTask(new TreeService(), "descendantByCode", List.class,treeDTO.getType(),treeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询组织树成功"); 
					msg.setData(treeBeanList);
				  }else {
					//查询失败
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询组织树失败"); 
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
	public String queryDeptUser(@RequestBody DeptDTO deptDTO) {		
		if(deptDTO.getDepCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "部门名称不能为空", Integer.class).toJson();
		}		
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{				
			  PageListVO pageListVO=ADOConnection.runTask(userService, "queryDeptUser", PageListVO.class,deptDTO);	
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
	public String addMenu(@RequestBody MenuTreeDTO menuTreeDTO) {		
		if(menuTreeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "父部门外键不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getModuleName()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块菜单名称不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getModuleNo()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "模块编号不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getType()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "树类型不能为空", Integer.class).toJson();
		}
		if(menuTreeDTO.getLinkAddress()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "链接地址不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  Integer addRes=ADOConnection.runTask(userService, "addMenu", Integer.class,menuTreeDTO);	
			  if(addRes==1) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("添加菜单成功"); 				
			   }else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("添加菜单失败"); 
			   }		  
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
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
	public String queryTreeMenu(@RequestBody MenuTreeDTO menuTreeDTO) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeDeptVO> treeBeanList=ADOConnection.runTask(new TreeService(), "descendantMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
					//查询失败
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询目录树失败"); 
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
	public String queryChildTreeMenu(@RequestBody MenuTreeDTO menuTreeDTO) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(new TreeService(), "childMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询下级目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
					//查询失败
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
			        msg.setDescription("查询下级目录树失败"); 
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
	public String queryRoleMenuByRoleId(@RequestBody RoleDTO roleDTO) {		
		if(roleDTO.getRoleCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<RoleMenusVO> menuList=ADOConnection.runTask(userService, "queryRoleMenuByRoleCode", List.class,roleDTO);	
			  if(menuList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询角色菜单查看权限成功"); 
					msg.setData(menuList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
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
	public String queryRoleMenuByRoleMenu(@RequestBody RoleMenuDTO roleMenuDTO) {		
		if(roleMenuDTO.getRoleCode()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色编码不能为空", Integer.class).toJson();
		}
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<RoleMenusVO> menuCDUList=ADOConnection.runTask(userService, "queryRoleMenuByRoleMenu", List.class,roleMenuDTO);	
			  if(menuCDUList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询角色菜单操作权限成功"); 
					msg.setData(menuCDUList);
				  }else {
			        msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
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
	public String updateRoleMenuByRoleCode(@RequestBody RoleMenuDTO roleMenuDTO) {
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
			  Integer updateRes=ADOConnection.runTask(userService, "updateRoleMenuByRoleCode", Integer.class,roleMenuDTO);	
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
	//@ValidatePermission("首页下的-query")
	@RequestMapping(value = "/queryDept.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "模糊查询部门接口", notes = "模糊查询部门接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryDept(@RequestBody DeptDTO deptDTO) {		
		 MessageBean<PageListVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, PageListVO.class);	       
		  try{				
			  PageListVO pageListVO=ADOConnection.runTask(userService, "queryDept", PageListVO.class,deptDTO);	
			  if(pageListVO!=null && pageListVO.getRowNumber()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询部门成功"); 
					msg.setData(pageListVO);
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
     * date:2020-04-08
     * funtion:根据userCode可查看菜单目录结构(查看下级的菜单)加入菜单权限,类似查询一级菜单
     * author:xiaozhan
     */
	@RequestMapping(value = "/queryChildOneMenu.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查看一级菜单树接口", notes = "查看一级菜单树接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryChildOneMenu(@RequestBody MenuTreeDTO menuTreeDTO) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		//TODO 测试用
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(new TreeService(), "queryChildOneMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询一级目录树成功"); 
					msg.setData(treeBeanList);
				  }else {
					//查询失败
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("查询一级目录树失败"); 
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
	public String queryChildAllMenu(@RequestBody MenuTreeDTO menuTreeDTO) {
		Integer type=Integer.valueOf(menuTreeDTO.getType());
		if(type==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点类型不能为空", Integer.class).toJson();
		}	
		if(menuTreeDTO.getForeignKey()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "该树节点外键不能为空", Integer.class).toJson();
		}
		//TODO 测试用
		
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		  try{				
			  List<TreeMenuVO> treeBeanList=ADOConnection.runTask(new TreeService(), "queryChildAllMenu", List.class,menuTreeDTO.getType(),menuTreeDTO.getForeignKey());	
			  if(treeBeanList.size()>0) {			 
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS); 
					msg.setDescription("查询所有下级树成功"); 
					msg.setData(treeBeanList);
				  }else {
					//查询失败
			        msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			        msg.setDescription("查询所有下级树失败"); 
			 }		  
	        }catch(Exception e){
	        	//查询失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}
}
