package com.koron.inwlms.mapper.master;

import java.util.List;

import org.koron.ebs.mybatis.EnvSource;
import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Repository;

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
	
	//插入角色与职员的关系
	public Integer addRoleUser(List<RoleAndUserDTO> roleAndUserDTOList);
     
}
