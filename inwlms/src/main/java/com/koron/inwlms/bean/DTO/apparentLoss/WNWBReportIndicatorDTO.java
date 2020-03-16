package com.koron.inwlms.bean.DTO.apparentLoss;

/**
 * 全网水平衡报表指标信息DTO
 * @author csh
 * @Date 2020.03.09
 */
public class WNWBReportIndicatorDTO {

	/**
	 * 主键id
	 */
	public Integer id;
	
	/**
	 * 报表id
	 */
	public Integer reportId;
	
	/**
	 * 模板指标id
	 */
	public Integer templateIndicatorId;
	
	/**
	 * 指标值
	 */
	public Double value;

	public Integer getId() {
		return id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public Integer getTemplateIndicatorId() {
		return templateIndicatorId;
	}

	public Double getValue() {
		return value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public void setTemplateIndicatorId(Integer templateIndicatorId) {
		this.templateIndicatorId = templateIndicatorId;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
