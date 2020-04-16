package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 全网水平衡报表附件DTO
 * @author csh
 * @Date 2020.03.09
 */
public class WNWBReportFileDTO {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 关联报表ID
	 */
	private Integer reportId;
	/**
	 * 上传保存路径
	 */
	private Integer fileId;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 修改时间
	 */
	private String updateTime;
	/**
	 * 创建账号
	 */
	private String createBy;
	/**
	 * 修改账号
	 */
	private String updateBy;
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Integer getId() {
		return id;
	}
	public Integer getReportId() {
		return reportId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
