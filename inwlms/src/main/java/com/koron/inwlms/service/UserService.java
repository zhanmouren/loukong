package com.koron.inwlms.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.RoleAndUserVO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
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
    //新建新角色
	Integer addNewRole(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性
	Integer editRoleAttr(SessionFactory factory, RoleDTO roleDTO);
	 //修改角色属性(物理删除)
	RoleMsgVO delRoleAttr(SessionFactory factory, RoleDTO roleDTO);	
	//根据角色Id加载角色人员接口
	List<UserVO> queryUserByRoleId(SessionFactory factory, RoleDTO roleDTO);
	//查询所有角色接口以及相关职员(默认第一次进入角色的时候)
	RoleAndUserVO queryAllRoleUser(SessionFactory factory, RoleDTO roleDTO);
	//添加用户和角色关系的操作
	Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
}
