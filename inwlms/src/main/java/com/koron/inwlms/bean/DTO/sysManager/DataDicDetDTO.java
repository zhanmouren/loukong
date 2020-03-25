package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 数据字典明细bean
 *
* @Author xiaozhan
* @Date 2020.03.18
*/

public class DataDicDetDTO {
	
	//数据字典明细主键
	private Integer dicDetId;
	//数据字典主键id
	private Integer dictId;
	//数据字典明细名称
	private String dicDetName;
	//数据字典明细值
	private String dicDetValue;
	
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
	public Integer getDicDetId() {
		return dicDetId;
	}
	public void setDicDetId(Integer dicDetId) {
		this.dicDetId = dicDetId;
	}
	public Integer getDictId() {
		return dictId;
	}
	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}
	public String getDicDetName() {
		return dicDetName;
	}
	public void setDicDetName(String dicDetName) {
		this.dicDetName = dicDetName;
	}
	public String getDicDetValue() {
		return dicDetValue;
	}
	public void setDicDetValue(String dicDetValue) {
		this.dicDetValue = dicDetValue;
	}
	
}
