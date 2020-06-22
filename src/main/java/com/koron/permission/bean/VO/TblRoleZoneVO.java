package com.koron.permission.bean.VO;

import com.koron.common.web.mapper.TreeBean;

public class TblRoleZoneVO extends TreeBean.Long{

	//角色-数据范围id
	private Integer roleRangeId;
	//角色编码
	private String role;
	//数据类型
	private String catalogue;
	//分区编码
	private String value;
	//分区名称
	private String name;
	//是否打勾
	private boolean booltick;
	
	
	
	public boolean isBooltick() {
		return booltick;
	}

	public void setBooltick(boolean booltick) {
		this.booltick = booltick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoleRangeId() {
		return roleRangeId;
	}

	public String getRole() {
		return role;
	}

	public String getCatalogue() {
		return catalogue;
	}

	public String getValue() {
		return value;
	}

	public void setRoleRangeId(Integer roleRangeId) {
		this.roleRangeId = roleRangeId;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setCatalogue(String catalogue) {
		this.catalogue = catalogue;
	}

	public void setValue(String value) {
		this.value = value;
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
	public TblRoleZoneVO setId(java.lang.Integer id) {
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
	public TblRoleZoneVO setType(int type) {
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
	public TblRoleZoneVO setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
		return this;
	}

	/**
	 * 获取外键
	 */
	public String getForeignkey() {
		return foreignkey;
	}

	@Override
	public String toString() {
		return "TblRoleZoneVO [roleRangeId=" + roleRangeId + ", role=" + role + ", catalogue=" + catalogue + ", value="
				+ value + ", name="+name+",,booltick="+booltick+",id=" + id + ", type=" + type + ", foreignkey=" + foreignkey + ",parentMask=" + super.getParentMask() + ", mask=" + super.getMask() + ", childMask=" + super.getChildMask() + "]";
	}
	
}
