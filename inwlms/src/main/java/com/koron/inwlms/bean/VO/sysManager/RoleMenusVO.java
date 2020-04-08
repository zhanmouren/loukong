package com.koron.inwlms.bean.VO.sysManager;

import java.util.List;

/**
 * 角色菜单VO
* @Author xiaozhan
* @Date 2020.03.23
*/
public class RoleMenusVO {
	//角色Id
	private Integer roleId;
    //角色code
	private String roleCode;
	//角色名称
	private String roleName;
	//模块Id
	private Integer menuId;
    //菜单code
	private String moduleCode;
	//模块编号
	private String moduleNo;
	//模块名称
	private String moduleName;
	//链接地址
	private String linkAddress;
    //菜单操作权限的op List
	private List<Integer> opList;
	//菜单操作权限的op
	private String op;
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getLinkAddress() {
		return linkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public List<Integer> getOpList() {
		return opList;
	}
	public void setOpList(List<Integer> opList) {
		this.opList = opList;
	}
}
