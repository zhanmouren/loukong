package com.koron.common.bean;
@Deprecated
public class RoleBean implements IdentityBean {
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;

	/**
	 * 设置
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取描述
	 */
	public String getDescription() {
		return description;
	}
}