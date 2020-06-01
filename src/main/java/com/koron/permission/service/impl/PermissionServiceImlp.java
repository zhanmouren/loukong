package com.koron.permission.service.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;
import org.swan.bean.MessageBean;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.permission.bean.DTO.TblRoleDTO;
import com.koron.permission.bean.VO.UserListVO;
import com.koron.permission.bean.VO.userOperationVO;
import com.koron.permission.mapper.PermissionMapper;
import com.koron.permission.service.PermissionService;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

@Service
public class PermissionServiceImlp implements PermissionService{

	//根据登录的用户名查询权限列表
	@TaskAnnotation("getUserOPList")
	@Override
	public List<userOperationVO> getUserOPList(SessionFactory factory) {
		PermissionMapper mapper=factory.getMapper(PermissionMapper.class);
		 //判断用户是否登录
		 Gson jsonValue = new Gson();
		 if(SessionUtil.getAttribute(Constant.LOGIN_USER)==null) {
		   return null;
		 }
	    UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);	
		  
		TblRoleDTO tblRoleDTO=new TblRoleDTO();
		List<userOperationVO> userOPList=mapper.getUserOPList(tblRoleDTO);
		return userOPList;
	}

}
