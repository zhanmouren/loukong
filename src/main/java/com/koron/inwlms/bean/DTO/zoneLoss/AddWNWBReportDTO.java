package com.koron.inwlms.bean.DTO.zoneLoss;

import java.util.List;

/**
 * 添加全网水平衡报表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class AddWNWBReportDTO{

	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 报表名称
	 */
	private String reportName;
	
	/**
	 * 报表描述
	 */
	private String description;
	
	/**
	 * 报表时间
	 */
	private Integer reportTime;
	
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
	private Integer createTime;
	
	/**
	 * 修改时间（时间戳）
	 */
	private Integer updateTime;
	
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
	private List<WNWBReportIndicatorDTO> indicators;
	/**
	 * 上传附件信息
	 */
	private List<WNWBReportFileDTO> files;
	
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

	public List<WNWBReportIndicatorDTO> getIndicators() {
		return indicators;
	}

	public List<WNWBReportFileDTO> getFiles() {
		return files;
	}

	public void setIndicators(List<WNWBReportIndicatorDTO> indicators) {
		this.indicators = indicators;
	}

	public void setFiles(List<WNWBReportFileDTO> files) {
		this.files = files;
	}

	public Integer getReportTime() {
		return reportTime;
	}

	public void setReportTime(Integer reportTime) {
		this.reportTime = reportTime;
	}
	
}
