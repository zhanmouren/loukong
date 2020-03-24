package com.koron.inwlms.bean.VO.sysManager;

import java.util.List;

/**
 * 加载所有角色和第一个角色对应的人员
* @Author xiaozhan
* @Date 2020.03.23
*/
public class RoleAndUserVO {
	//用户列表
   private List<UserVO> userList;
    //角色列表
   private List<RoleVO> roleList;
	public List<UserVO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}
	public List<RoleVO> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<RoleVO> roleList) {
		this.roleList = roleList;
	}
}
