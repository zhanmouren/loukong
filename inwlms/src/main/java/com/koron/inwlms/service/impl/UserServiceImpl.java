package com.koron.inwlms.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.master.UserMapper;
import com.koron.inwlms.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	//管理员添加职员 2020/03/18
	
	@TaskAnnotation("addUser")
	@Override
	public Integer addUser(SessionFactory factory,UserDTO userDTO) {
		// TODO Auto-generated method stub	
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setCreateBy("xiaozhan");
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setCreateTime(timeNow);
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer addResult=userMapper.addUser(userDTO);
		return addResult;
	}
	
	//查询职员 2020/03/19	
	@TaskAnnotation("queryUser")
	@Override
	public List<UserVO> queryUser(SessionFactory factory,QueryUserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		List<UserVO> userList=userMapper.queryUser(userDTO);
		return userList;
	}
	//修改职员 2020/03/20
	@TaskAnnotation("editUser")
	@Override
	public Integer editUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		userDTO.setUpdateBy("xiaozhan");
		userDTO.setUpdateTime(timeNow);
		Integer editResult=userMapper.editUser(userDTO);
		return editResult;
	}
   
	//删除职员 2020/03/23
	@TaskAnnotation("delUser")
	@Override
	public Integer delUser(SessionFactory factory, UserDTO userDTO) {
		// TODO Auto-generated method stub
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		userDTO.setWhetUse(-1);
		Integer delResult=userMapper.delUser(userDTO);
		return delResult;
	}
}