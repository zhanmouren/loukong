package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 全网水平报表指标
 * @author csh
 * @Date 2020.04.08
 */
public class WNWBReportIndicator {

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
	
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 创建账号
	 */
	private String createBy;
	/**
	 * 更新账号
	 */
	private String updateBy;

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

	public String getCreateTime() {
		return createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
