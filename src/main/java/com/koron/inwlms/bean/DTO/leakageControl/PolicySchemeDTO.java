package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class PolicySchemeDTO {

	/**
	 * 控漏损策略方案编码
	 */
	private String code;
	/**
	 * 控漏损策略方案名称
	 */
	private String name;
	/**
	 * 控漏损策略方案描述
	 */
	private String content;
	/**
	 * 控漏损策略方案状态
	 */
	private String state;
	
	private String createBy;

	private Date createTime;

	private String updateBy;
	
	private Date updateTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
