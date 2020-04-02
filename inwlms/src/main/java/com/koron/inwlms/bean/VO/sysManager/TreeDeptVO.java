package com.koron.inwlms.bean.VO.sysManager;

import com.koron.common.web.mapper.TreeBean;

/**
 * 返回树形 结构的组织部门
 *
* @Author xiaozhan
* @Date 2020.04.02
*/
public class TreeDeptVO extends TreeBean.Long{

	private java.lang.Integer depId;
	//部门名称
	private String depName;
	//部门编码
	private String depCode;
	//部门状态
	private java.lang.Integer  depstatus;

	

	

	public java.lang.Integer getDepId() {
		return depId;
	}

	public TreeDeptVO setDepId(java.lang.Integer depId) {
		this.depId = depId;
		return this;
	}

	public java.lang.Integer getDepstatus() {
		return depstatus;
	}

	public TreeDeptVO setDepstatus(java.lang.Integer depstatus) {
		this.depstatus = depstatus;
		return this;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
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
	public TreeDeptVO setId(java.lang.Integer id) {
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
	public TreeDeptVO setType(int type) {
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
	public TreeDeptVO setForeignkey(String foreignkey) {
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
		return "TreeDeptVO [id=" + id + ", type=" + type + ", foreignkey=" + foreignkey + "]";
	}
}
