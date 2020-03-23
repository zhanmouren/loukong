package com.koron.common.bean.query;


/**
 * 搜索角色的条件
 * @author swan
 *
 */
public class RoleOperationQueryBean extends BaseQueryBean {
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 操作名称
	 */
	private String operatename;
	/**
	 * 关键字
	 */
	private String operationkey;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * @return 获取角色ID
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param 设置角色ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return 获取操作名称
	 */
	public String getOperatename() {
		return operatename;
	}
	/**
	 * @param 设置操作名称
	 */
	public void setOperatename(String operatename) {
		this.operatename = operatename;
	}
	/**
	 * @return 获取关键字
	 */
	public String getOperationkey() {
		return operationkey;
	}
	/**
	 * @param 设置关键字
	 */
	public void setOperationkey(String operationkey) {
		this.operationkey = operationkey;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
