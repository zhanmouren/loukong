package com.koron.inwlms.bean.DTO.sysManager;



/**
 * 数据字典明细bean
 *
* @Author xiaozhan
* @Date 2020.03.18
*/

public class dataDicDetDTO {
	
	//数据字典明细主键
	private Integer dicDetId;
	//数据字典主键id
	private Integer dictId;
	//数据字典明细名称
	private String dicDetName;
	//数据字典明细值
	private String dicDetValue;
	
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
