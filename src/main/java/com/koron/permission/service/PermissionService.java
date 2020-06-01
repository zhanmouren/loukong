package com.koron.permission.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.permission.bean.VO.userOperationVO;

public interface PermissionService {

	//查询用户查询权限列表
	public List<userOperationVO> getUserOPList(SessionFactory factory);
}
