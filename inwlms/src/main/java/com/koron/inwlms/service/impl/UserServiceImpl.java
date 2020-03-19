package com.koron.inwlms.service.impl;

import java.sql.Timestamp;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.userDTO;
import com.koron.inwlms.mapper.master.UserMapper;
import com.koron.inwlms.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	//管理员添加职员 2020/03/18
	
	@TaskAnnotation("addUser")
	@Override
	public Integer addUser(SessionFactory factory,userDTO userDTO) {
		// TODO Auto-generated method stub	
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setUserId(3);
		userDTO.setCreateBy("xiaozhan");
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setCreateTime(timeNow);
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer addResult=userMapper.addUser(userDTO);
		return addResult;
	}

}
