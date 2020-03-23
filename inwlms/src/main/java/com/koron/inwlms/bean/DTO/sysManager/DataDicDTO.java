package com.koron.inwlms.bean.DTO.sysManager;

import java.util.List;



/**
 * 数据字典主表bean
 *
* @Author xiaozhan
* @Date 2020.03.18
*/

public class DataDicDTO {
	//数据字典Id
	private Integer dicId;
	//数据字典标识
	private String dicFlag;
	//数据字典名称
	private String dicName;
	//数据字典备注
	private String dicRemark;
	//数据字典List明细
	private List<DataDicDetDTO> dictionaryDetList;
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	public String getDicFlag() {
		return dicFlag;
	}
	public void setDicFlag(String dicFlag) {
		this.dicFlag = dicFlag;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicRemark() {
		return dicRemark;
	}
	public void setDicRemark(String dicRemark) {
		this.dicRemark = dicRemark;
	}
	public List<DataDicDetDTO> getDictionaryDetList() {
		return dictionaryDetList;
	}
	public void setDictionaryDetList(List<DataDicDetDTO> dictionaryDetList) {
		this.dictionaryDetList = dictionaryDetList;
	}
}
