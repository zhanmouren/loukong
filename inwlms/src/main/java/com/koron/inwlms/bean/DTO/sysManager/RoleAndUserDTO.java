package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 角色和用户DTO
* @Author xiaozhan
* @Date 2020.03.24
*/
public class RoleAndUserDTO extends BaseDTO {
	//人员名称
	private String name;
	//登录名称
	private String loginName;
	//
	private Integer whetUse;
	
	//角色Code
	private String roleCode;
	//角色Code列表
	private List<String> userCodeList;
	//用户Code
	private String userCode;
	//角色Id
	private Integer roleId;
	//存储userId的List
	private List<Integer> userList;
	//存储单个的userId
	private Integer userId;
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	
	public Integer getWhetUse() {
		return whetUse;
	}
	public void setWhetUse(Integer whetUse) {
		this.whetUse = whetUse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public List<String> getUserCodeList() {
		return userCodeList;
	}
	public void setUserCodeList(List<String> userCodeList) {
		this.userCodeList = userCodeList;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<Integer> getUserList() {
		return userList;
	}
	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}
}
