package com.koron.inwlms.bean.VO.sysManager;

import java.util.List;

import com.koron.common.web.mapper.TreeBean;

/**
 * 角色菜单VO
* @Author xiaozhan
* @Date 2020.03.23
*/
public class RoleMenusVO extends TreeBean.Long{
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
	private List<String> opList;
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
	
	
	public List<String> getOpList() {
		return opList;
	}
	public void setOpList(List<String> opList) {
		this.opList = opList;
	}


	/**
	 * 分级ID
	 */
	private java.lang.Integer id;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 外键
	 */
	private String foreignkey;

	/**
	 * 设置分级ID
	 */
	public RoleMenusVO setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * 获取分级ID
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * 设置类型
	 */
	public RoleMenusVO setType(int type) {
		this.type = type;
		return this;
	}

	/**
	 * 获取类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置外键
	 */
	public RoleMenusVO setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
		return this;
	}

	/**
	 * 获取外键
	 */
	public String getForeignkey() {
		return foreignkey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoleMenusVO [id=" + id + ", type=" + type + ", foreignkey=" + foreignkey + "]";
	}
}
