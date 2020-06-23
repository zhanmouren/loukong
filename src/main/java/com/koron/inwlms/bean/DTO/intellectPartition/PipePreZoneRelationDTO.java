package com.koron.inwlms.bean.DTO.intellectPartition;

public class PipePreZoneRelationDTO {

	private Integer id;
	/**
	 * 分区编码
	 */
	private String rCode;
	/**
	 * 管道编码
	 */
	private String pipeCode;
	/**
	 * 是否为边界
	 */
	private Integer isBorder;
	
	private String createBy;
	
	private String updateBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public String getPipeCode() {
		return pipeCode;
	}

	public void setPipeCode(String pipeCode) {
		this.pipeCode = pipeCode;
	}

	

	public Integer getIsBorder() {
		return isBorder;
	}

	public void setIsBorder(Integer isBorder) {
		this.isBorder = isBorder;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
	
}
