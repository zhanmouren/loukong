package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

/**
 * 添加全网水平衡报表DTO
 * @author csh
 * @Date 2020.04.08
 */
public class WNWBReportDetailVO{

	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 报表名称
	 */
	private String reportName;
	
	/**
	 * 报表时间
	 */
	private Integer reportTime;
	
	/**
	 * 报表描述
	 */
	private String description;
	
	/**
	 * 模板id
	 */
	private Integer templateId;
	
	/**
	 * 时间类型（3：月，4：年）
	 */
	private Integer timeType;
	
	/**
	 * 开始时间
	 */
	private Integer startTime;
	
	/**
	 * 结束时间
	 */
	private Integer endTime;
	
	/**
	 * 状态（0：不存在，1：存在）
	 */
	private Integer status;
	
	/**
	 * 创建时间（时间戳）
	 */
	private String createTime;
	
	/**
	 * 修改时间（时间戳）
	 */
	private String updateTime;
	
	/**
	 * 创建人
	 */
	private String createBy;
	
	/**
	 * 修改人
	 */
	private String updateBy;
	
	/**
	 * 备注
	 */
	private String remarks;
	
	/**
	 * 报表模板指标信息
	 */
	private List<WNWBReportIndicator> indicators;
	/**
	 * 上传附件信息
	 */
	private List<WNWBReportFile> files;
	public Integer getId() {
		return id;
	}
	public String getReportName() {
		return reportName;
	}
	public Integer getReportTime() {
		return reportTime;
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
	public String getCreateBy() {
		return createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public List<WNWBReportIndicator> getIndicators() {
		return indicators;
	}
	public List<WNWBReportFile> getFiles() {
		return files;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
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
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setIndicators(List<WNWBReportIndicator> indicators) {
		this.indicators = indicators;
	}
	public void setFiles(List<WNWBReportFile> files) {
		this.files = files;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
