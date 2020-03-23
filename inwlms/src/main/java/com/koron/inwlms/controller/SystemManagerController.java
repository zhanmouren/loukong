package com.koron.inwlms.controller;

import java.util.List;

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

import com.koron.inwlms.bean.DTO.TestBean;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.service.UserService;
import com.koron.inwlms.service.impl.TestServiceImpl;
import com.koron.inwlms.service.impl.UserServiceImpl;
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
		if(userDTO.getPassword()==null || StringUtils.isBlank(userDTO.getPassword())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "密码不能为空", Integer.class).toJson();
		}
		if(userDTO.getWorkNo()==null || StringUtils.isBlank(userDTO.getWorkNo())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "工号不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行插入职员的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(new UserServiceImpl(), "addUser", Integer.class, userDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==1) {
					//添加用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加用户成功");
				  }else {
				    //插入失败
			        msg.setCode(Constant.MESSAGE_INT_Failed);
			        msg.setDescription("添加用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
	            msg.setDescription("添加用户失败");
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
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		 //执行查询职员
		 try {
			 List<UserVO> userList=ADOConnection.runTask(new UserServiceImpl(), "queryUser", List.class, userDTO);
			 if(userList.size()>0) {
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("查询到相关职员的信息"); 
			     msg.setData(userList);
			 }else {
			   //没查询到数据
				 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
			     msg.setDescription("没有查询到相关职员的信息"); 
			 }
		 }catch(Exception e){
	     	//查询失败
	     	msg.setCode(Constant.MESSAGE_INT_Failed);
	        msg.setDescription("查询职员失败");
	     }
		 return msg.toJson();
		 
	}
	
	 /*
     * date:2020-03-20
     * funtion:修改新职员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/editUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改职员信息接口", notes = "修改职员信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String editUser(@RequestBody UserDTO userDTO) {
		if(userDTO.getUserId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员的Id不能为空", Integer.class).toJson();
		}
		if(userDTO.getName()==null || StringUtils.isBlank(userDTO.getName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员名不能为空", Integer.class).toJson();
		}
		if(userDTO.getLoginName()==null || StringUtils.isBlank(userDTO.getLoginName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "登录名称不能为空", Integer.class).toJson();
		}
		if(userDTO.getPassword()==null || StringUtils.isBlank(userDTO.getPassword())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "密码不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行修改职员的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(new UserServiceImpl(), "editUser", Integer.class, userDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {
					//修改用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("修改用户成功");
				  }else {
				    //修改用户失败
			        msg.setCode(Constant.MESSAGE_INT_Failed);
			        msg.setDescription("修改用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//修改用户失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
	            msg.setDescription("修改用户失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-23
     * funtion:删除新职员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/delUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "删除职员信息接口", notes = "删除职员信息接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String  delUser(@RequestBody UserDTO userDTO) {
		if(userDTO.getUserId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "职员的Id不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除职员的操作
		  try{
			  Integer delRes=ADOConnection.runTask(new UserServiceImpl(), "delUser", Integer.class, userDTO);		 
			  if(delRes!=null) {
				  if(delRes==1) {
					//删除用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("删除用户成功");
				  }else {
				    //删除用户失败
			        msg.setCode(Constant.MESSAGE_INT_Failed);
			        msg.setDescription("删除用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//删除用户失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
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
		//执行插入职员的操作
		  try{
			  Integer insertRes=ADOConnection.runTask(new UserServiceImpl(), "addNewRole", Integer.class, roleDTO);		 
			  if(insertRes!=null) {
				  if(insertRes==1) {
					//添加用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("添加新角色成功");
				  }else {
				    //插入失败
			        msg.setCode(Constant.MESSAGE_INT_Failed);
			        msg.setDescription("添加新角色失败");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
	            msg.setDescription("添加新角色失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-20
     * funtion:修改角色属性接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/editRoleAttr.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "修改角色属性接口", notes = "修改角色属性接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String editRoleAttr(@RequestBody RoleDTO roleDTO) {
		if(roleDTO.getRoleId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色Id不能为空", Integer.class).toJson();
		}
		if(roleDTO.getRoleName()==null || StringUtils.isBlank(roleDTO.getRoleName())) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色名称不能为空", Integer.class).toJson();
		}	
		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行修改职员的操作
		  try{
			  Integer updateRes=ADOConnection.runTask(new UserServiceImpl(), "editRoleAttr", Integer.class, roleDTO);		 
			  if(updateRes!=null) {
				  if(updateRes==1) {
					//修改用户成功
				    msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				    msg.setDescription("修改角色属性成功");
				  }else {
				    //修改用户失败
			        msg.setCode(Constant.MESSAGE_INT_Failed);
			        msg.setDescription("修改角色属性失败");
				  }
			  }
	        }catch(Exception e){
	        	//修改用户失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
	            msg.setDescription("修改角色属性失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-20
     * funtion:批量删除删除角色接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/delRoleAttr.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "批量删除角色接口", notes = "批量删除角色接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String delRoleAttr(@RequestBody RoleDTO roleDTO) {
		if(roleDTO.getRoleIdList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色列表Id不能为空", Integer.class).toJson();
		}		
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行删除角色的操作
		  try{
			  RoleMsgVO roleMsgVO=ADOConnection.runTask(new UserServiceImpl(), "delRoleAttr", RoleMsgVO.class, roleDTO);		 
			  if(roleMsgVO!=null) {
				  if(roleMsgVO.getResult()==-1) {
					 msg.setCode(Constant.MESSAGE_INT_Failed);
					 msg.setDescription(roleMsgVO.getMessage()); 
				  }else {
					  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				      msg.setDescription(roleMsgVO.getMessage());  
				  }
				   
			 }
	        }catch(Exception e){
	        	//删除角色失败
	        	msg.setCode(Constant.MESSAGE_INT_Failed);
	            msg.setDescription("删除角色失败");
	        }
		
	     return msg.toJson();
	}
	

}
