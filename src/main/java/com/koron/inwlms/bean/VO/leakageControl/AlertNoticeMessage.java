package com.koron.inwlms.bean.VO.leakageControl;

public class AlertNoticeMessage {
	/**
	 * 通知方式
	 */
	private String type;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 联系方式
	 */
	private String contact;
	/**
	 * 状态
	 */
	private String status;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
