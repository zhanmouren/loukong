package com.koron.permission.bean.DTO;
/**
 * date: 2020/05/28
 * @author 小詹
 * description:角色表DTO
 *
 */
public class TblRoleDTO {

	//主键id
	private Integer roleId;
	//角色标识编码
	private String roleCode;
	//状态
	private Integer roleStatus;
	//权重
	private Integer roleWeight;
	//角色名称
	private String roleName;
	//应用编码
	private String app;
	
	
	
	public Integer getRoleId() {
		return roleId;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public Integer getRoleStatus() {
		return roleStatus;
	}
	public Integer getRoleWeight() {
		return roleWeight;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getApp() {
		return app;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}
	public void setRoleWeight(Integer roleWeight) {
		this.roleWeight = roleWeight;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setApp(String app) {
		this.app = app;
	}
	
}
