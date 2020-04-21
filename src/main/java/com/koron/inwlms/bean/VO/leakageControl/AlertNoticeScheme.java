package com.koron.inwlms.bean.VO.leakageControl;

import java.util.Date;

/**
 * 
 * @author 刘刚
 *
 */
public class AlertNoticeScheme {

	private Integer id;
	
	private String warningCode;
	
	private String type;
	
	private Integer userId;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWarningCode() {
		return warningCode;
	}

	public void setWarningCode(String warningCode) {
		this.warningCode = warningCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
