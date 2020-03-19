package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 添加全网水平衡模板报表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class AddWNWBTReportDTO{

	
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
	 * 全网水平衡模板报表指标
	 */
	public WNWBTReportIndicatorDTO indicators;

	public Integer getId() {
		return id;
	}

	public String getReportName() {
		return reportName;
	}

	public String getDescription() {
		return description;
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

	public WNWBTReportIndicatorDTO getIndicators() {
		return indicators;
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

	public void setIndicators(WNWBTReportIndicatorDTO indicators) {
		this.indicators = indicators;
	}

}
