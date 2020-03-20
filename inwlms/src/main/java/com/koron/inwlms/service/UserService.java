package com.koron.inwlms.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.queryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.userDTO;
import com.koron.inwlms.bean.VO.sysManager.userVO;


public interface UserService {
	
	//管理员添加职员
	Integer addUser(SessionFactory factory, userDTO userDTO);
	//查询职员（名称或部门）
	List<userVO> queryUser(SessionFactory factory, queryUserDTO userDTO);
	//修改职员
    Integer  editUser(SessionFactory factory, userDTO userDTO);
	
}
