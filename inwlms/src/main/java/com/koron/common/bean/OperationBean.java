package com.koron.common.bean;

public class OperationBean implements IdentityBean {
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 关键字，用来权限判断用
	 */
	private String key;
	/**
	 * 所需参数
	 */
	private String param;

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
	 * 设置关键字，用来权限判断用
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取关键字，用来权限判断用
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置所需参数
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 获取所需参数
	 */
	public String getParam() {
		return param;
	}
}