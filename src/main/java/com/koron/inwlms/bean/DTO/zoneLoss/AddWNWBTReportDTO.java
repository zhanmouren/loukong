package com.koron.inwlms.bean.DTO.zoneLoss;

import java.util.List;

/**
 * 添加全网水平衡模板报表DTO
 * @author csh
 * @Date 2020.03.09
 */
public class AddWNWBTReportDTO{

	
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
	 * 全网水平衡模板报表指标
	 */
	private List<WNWBTReportIndicatorDTO> indicators;

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

	public List<WNWBTReportIndicatorDTO> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<WNWBTReportIndicatorDTO> indicators) {
		this.indicators = indicators;
	}

}
