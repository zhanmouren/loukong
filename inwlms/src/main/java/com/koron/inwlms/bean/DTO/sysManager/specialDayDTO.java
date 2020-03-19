package com.koron.inwlms.bean.DTO.sysManager;

/**
 * 特征日bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
public class specialDayDTO {
	//特征日Id
	private Integer spId;
	//特征日名称
	private String spName;
	//特征日描述
	private String spRemark;
	//特征日日期
	private String spDate;
	public Integer getSpId() {
		return spId;
	}
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getSpRemark() {
		return spRemark;
	}
	public void setSpRemark(String spRemark) {
		this.spRemark = spRemark;
	}
	public String getSpDate() {
		return spDate;
	}
	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}
	
}
