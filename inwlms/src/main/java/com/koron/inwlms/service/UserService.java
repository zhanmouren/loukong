package com.koron.inwlms.service;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.userDTO;

public interface UserService {
	
	//管理员添加职员
	Integer addUser(SessionFactory factory, userDTO userDTO);
}
