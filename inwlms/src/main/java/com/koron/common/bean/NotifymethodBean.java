package com.koron.common.bean;

public class NotifymethodBean implements IdentityBean {
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 默认通知方式1短信2微信
	 */
	private Integer method;
	/**
	 * 默认时段
	 */
	private String period;
	/**
	 * 标志位
	 */
	private Integer flag;

	/**
	 * 设置
	 */
	public NotifymethodBean setId(Integer id) {
		this.id = id;
		return this;
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
	public NotifymethodBean setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 获取名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置默认通知方式1短信2微信
	 */
	public NotifymethodBean setMethod(Integer method) {
		this.method = method;
		return this;
	}

	/**
	 * 获取默认通知方式1短信2微信
	 */
	public Integer getMethod() {
		return method;
	}

	/**
	 * 设置默认时段
	 */
	public NotifymethodBean setPeriod(String period) {
		this.period = period;
		return this;
	}

	/**
	 * 获取默认时段
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 设置标志位
	 */
	public NotifymethodBean setFlag(Integer flag) {
		this.flag = flag;
		return this;
	}

	/**
	 * 获取标志位
	 */
	public Integer getFlag() {
		return flag;
	}
}