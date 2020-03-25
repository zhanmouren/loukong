package com.koron.inwlms.mapper.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.DeptAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleAndUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserDTO;
import com.koron.inwlms.bean.VO.sysManager.RoleMsgVO;
import com.koron.inwlms.bean.VO.sysManager.RoleVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

/*
 * date:2020-03-18
 * author:xiaozhan
 */  	
@Repository
@EnvSource("_default")
public interface UserMapper {
   //添加职员
	public Integer addUser(UserDTO userDTO);
	
	//查询职员
	public List<UserVO> queryUser(QueryUserDTO userDTO);
	
	//修改职员
    public Integer editUser(UserDTO userDTO);
    
    //删除职员
    public Integer delUser(UserDTO userDTO);
    
    //新建新角色
    public Integer addNewRole(RoleDTO roleDTO);
  	 //修改角色属性
    public Integer editRoleAttr(RoleDTO roleDTO);
    
    //删除角色
    public Integer  delRole(List<Integer> roleList);
    
    //查询该角色是否存在职员
    public List<RoleMsgVO>  queryRoleUser(RoleDTO roleDTO);
    
   //根据角色Id加载角色人员接口
  	public List<UserVO> queryUserByRoleId(RoleDTO roleDTO);
  	
  	//加载所有角色
	public List<RoleVO> queryAllRole();
	
	//插入角色与职员(批量)的关系
	public Integer addRoleUser(List<RoleAndUserDTO> roleAndUserDTOList);
	
	//删除角色中职员(批量)
	public Integer  delRoleUser(@Param("roleId") Integer roleId,@Param("list") List<Integer> userList);
	
	//给角色挑选职员的时候弹出框，要排除该角色已经存在的职员信息，只能选其他的职员(角色弹窗选择职员)
	public List<UserVO> queryExceptRoleUser(RoleAndUserDTO roleUserDTO);
	
	//给部门挑选职员的时候弹出框，要排除该部门已经存在的职员信息，只能选其他的职员(部门弹窗选择职员) 2020/03/25	
	public List<UserVO> queryExceptDeptUser(DeptAndUserDTO deptUserDTO);
	
	//添加用户(批量)和部门关系的操作
	public Integer addDeptUser(List<DeptAndUserDTO> deptUserDTOList);
	
	//删除部门中职员(批量)
	public Integer delDeptUser(@Param("depId") Integer depId,@Param("list") List<Integer> userList);
}
