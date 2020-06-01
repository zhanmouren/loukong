package com.koron.permission.controller;

import java.util.List;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.swan.bean.MessageBean;

import com.koron.permission.bean.VO.userOperationVO;
import com.koron.permission.service.PermissionService;
import com.koron.util.Constant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *      权限控制Controller层
 * @author xiaozhan
 * @Date 2020.05.28
 */

@RestController
@Api(value = "permissionController", description = "权限控制Controller")
@RequestMapping(value = "/permissionController")
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	
	/*
	 * @author xiaozhan
	 * @Date 2020.05.28
	 * description:根据用户信息获取有关联(有权限)的操作，如果传父操作， 则只返回此操作下有权限的操作。
	 */
	@RequestMapping(value = "/getUserOPList.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "获取用户相关联操作接口", notes = "获取用户相关联操作接口", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String addUser() {	
		 MessageBean<?> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);	
		  try{
			  List<?> opList=ADOConnection.runTask(permissionService, "getUserOPList", List.class);
			  if(opList.size()>=0) {
				  msg.setCode(Constant.MESSAGE_INT_SUCCESS);
				  msg.setDescription("查询用户权限列表成功");
			  }else {
				  msg.setCode(Constant.MESSAGE_INT_SELECTERROR);
				  msg.setDescription("查询用户权限列表失败");
			  }
	        }catch(Exception e){
	        	msg.setCode(Constant.MESSAGE_INT_ERROR);
	            msg.setDescription("查询失败");
	        }
		
	     return msg.toJson();
	}

}
