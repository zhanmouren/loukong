package com.koron.inwlms.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;


public interface UserService {
	
	//管理员添加职员
	Integer addUser(SessionFactory factory, UserDTO userDTO);
	//查询职员（名称或部门）
	List<UserVO> queryUser(SessionFactory factory, QueryUserDTO userDTO);
	//修改职员
    Integer  editUser(SessionFactory factory, UserDTO userDTO);
    //删除职员（不物理删除）
    Integer  delUser(SessionFactory factory, UserDTO userDTO);
	
}
