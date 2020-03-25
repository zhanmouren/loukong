package com.koron.inwlms.service;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
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
	//添加用户(批量)和角色关系的操作
	Integer addRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//删除角色中职员(批量)接口
	Integer delRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	List<UserVO> queryExceptRoleUser(SessionFactory factory, RoleAndUserDTO roleUserDTO);
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25			
    List<UserVO> queryExceptDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//添加用户(批量)和部门关系的操作
	Integer addDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	//删除部门中职员(批量)接口
	Integer delDeptUser(SessionFactory factory, DeptAndUserDTO deptUserDTO);
	
	
	/**下面是系统配置***/
	
	//添加数据字典
	Integer addDataDic(SessionFactory factory, DataDicDTO dataDicDTO);
	
}
