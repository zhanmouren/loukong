package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 全网水平衡列表VO
 * @author csh
 * @Date 2020/04/07
 *
 */
public class WNWBReportListVO {

	private Integer id;
	
	private String reportName;
	
	private Integer templateId;
	
	private String templateName;
	
	private String timeType;
	
	private String reportTime;
	
	private String description;
	
	private String createTime;
	
	private String createBy;
	
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public String getReportName() {
		return reportName;
	}

	public String getTimeType() {
		return timeType;
	}

	public String getDescription() {
		return description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
