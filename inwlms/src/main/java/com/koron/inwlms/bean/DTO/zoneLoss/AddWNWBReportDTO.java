package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 添加全网水平衡报表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class AddWNWBReportDTO{

	
	/**
	 * id
	 */
	public Integer id;
	
	/**
	 * 报表名称
	 */
	public String reportName;
	
	/**
	 * 报表描述
	 */
	public String description;
	
	/**
	 * 模板id
	 */
	public Integer templateId;
	
	/**
	 * 时间类型（0：分 ，1：时，2：日，3：月，4：年）
	 */
	public Integer timeType;
	
	/**
	 * 开始时间
	 */
	public Integer startTime;
	
	/**
	 * 结束时间
	 */
	public Integer endTime;
	
	/**
	 * 状态（0：不存在，1：存在）
	 */
	public Integer status;
	
	/**
	 * 创建时间（时间戳）
	 */
	public Integer createTime;
	
	/**
	 * 修改时间（时间戳）
	 */
	public Integer updateTime;
	
	/**
	 * 创建人
	 */
	public String createBy;
	
	/**
	 * 修改人
	 */
	public String updateBy;
	
	/**
	 * 备注
	 */
	public String remarks;
	
	/**
	 * 全网水平衡报表附件信息
	 */
	public WNWBReportAttachmentDTO attachments;

	public Integer getId() {
		return id;
	}

	public String getReportName() {
		return reportName;
	}

	public String getDescription() {
		return description;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public WNWBReportAttachmentDTO getAttachments() {
		return attachments;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setAttachments(WNWBReportAttachmentDTO attachments) {
		this.attachments = attachments;
	}
	
}
