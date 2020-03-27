package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 特征日bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
public class SpecialDayDTO {
	//特征日Id
	private Integer spId;
	//特征日名称
	private String spName;
	//特征日描述
	private String spRemark;
	//特征日日期
	private String spDate;
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
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
