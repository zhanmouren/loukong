package com.koron.inwlms.controller;

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
import com.koron.inwlms.bean.DTO.sysManager.userDTO;
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
	public String addUser(@RequestBody userDTO userDTO) {
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
}
