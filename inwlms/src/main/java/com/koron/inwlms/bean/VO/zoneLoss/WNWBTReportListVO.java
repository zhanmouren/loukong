package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 全网水平衡列表模板VO
 * @author csh
 * @Date 2020/04/07
 *
 */
public class WNWBTReportListVO {

	private Integer id;
	
	private String reportName;
	
	private String description;
	
	private String createTime;
	
	private String createBy;
	
	private String updateTime;
	
	private String updateBy;

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
}
