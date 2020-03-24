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
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
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
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加用户失败");
				  }
			  }
	        }catch(Exception e){
	        	//插入失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
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
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
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
			        msg.setCode(Constant.MESSAGE_INT_ADDERROR);
			        msg.setDescription("添加新角色失败");
				  }
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
			        msg.setCode(Constant.MESSAGE_INT_EDITERROR);
			        msg.setDescription("修改角色属性失败");
				  }
			  }
	        }catch(Exception e){
	        	//修改用户失败
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
     * funtion:根据角色ID加载角色人员接口
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/queryUserByRoleId.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "根据角色ID加载角色人员接口", notes = "根据角色ID加载角色人员接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryUserByRoleId(@RequestBody RoleDTO roleDTO) {
		//可以不传角色Id,默认为超级管理员
		 if(roleDTO.getRoleId()==null) {
			 //测试中暂时设定id为3的为超级管理员
			 roleDTO.setRoleId(3);
		 }
		 MessageBean<List> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, List.class);	       
		//执行删除角色的操作
		  try{
			  List<UserVO> userList=ADOConnection.runTask(new UserServiceImpl(), "queryUserByRoleId", List.class, roleDTO);		 
			  if(userList.size()>0) {				 
					 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					 msg.setDescription("根据角色Id查询到相关职员信息列表"); 
					 msg.setData(userList);
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
     * funtion:查询所有角色接口以及相关职员(默认第一次进入角色的时候)
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/queryAllRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询所有角色接口以及相关职员", notes = "查询所有角色接口以及相关职员", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String queryAllRoleUser(@RequestBody RoleDTO roleDTO) {		
		 MessageBean<RoleAndUserVO> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, RoleAndUserVO.class);	       
		  try{
			  RoleAndUserVO roleAndUserVO=ADOConnection.runTask(new UserServiceImpl(), "queryAllRoleUser", RoleAndUserVO.class, roleDTO);		 
			  if(roleAndUserVO!=null) {				 
					 msg.setCode(Constant.MESSAGE_INT_SUCCESS);
					 msg.setDescription("查询到所有角色和相关职员信息列表"); 
					 msg.setData(roleAndUserVO);
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("没有查询到所有角色和相关职员信息列表"); 
			 }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询角色职员失败");
	        }
		
	     return msg.toJson();
	}
	
	 /*
     * date:2020-03-24
     * funtion:插入职员和角色的关系
     * author:xiaozhan
     */  	
	@RequestMapping(value = "/addRoleUser.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "插入职员和角色的关系", notes = "插入职员和角色的关系", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addRoleUser(@RequestBody RoleAndUserDTO roleUserDTO) {	
		if(roleUserDTO.getRoleId()==null) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "角色Id不能为空", Integer.class).toJson();
		}
		if(roleUserDTO.getUserList().size()<1) {
			return  MessageBean.create(Constant.MESSAGE_INT_PARAMS, "用户列表不能为空", Integer.class).toJson();
		}
		 MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	       
		//执行添加用户和角色关系的操作
		  try{
			  Integer addResult=ADOConnection.runTask(new UserServiceImpl(), "addRoleUser", Integer.class, roleUserDTO);		 
			  if(addResult==-1) {				 
					 msg.setCode(Constant.MESSAGE_INT_ADDERROR);
					 msg.setDescription("插入职员和角色的关系失败"); 
					
			 }else {
				     msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				     msg.setDescription("插入职员和角色的关系成功"); 
			 }
	        }catch(Exception e){
	        	//删除角色失败
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询角色职员失败");
	        }
		
	     return msg.toJson();
	}
	
}
