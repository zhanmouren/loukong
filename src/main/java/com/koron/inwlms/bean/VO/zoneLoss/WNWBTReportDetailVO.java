package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

/**
 * 全网水平衡模板报表VO
 * @author csh
 * @Date 2020.04.08
 */
public class WNWBTReportDetailVO{

	
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
	 * 全网水平衡模板报表指标
	 */
	private List<WNWBTReportIndicator> indicators;

	public Integer getId() {
		return id;
	}

	public String getReportName() {
		return reportName;
	}

	public String getDescription() {
		return description;
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


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<WNWBTReportIndicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<WNWBTReportIndicator> indicators) {
		this.indicators = indicators;
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
