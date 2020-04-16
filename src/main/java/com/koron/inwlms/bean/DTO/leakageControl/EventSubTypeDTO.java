package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;

public class EventSubTypeDTO {
	
	private String parentKey;
	
	/**
	 * 中文名称
	 */
	private String cn;
	
	/**
	 * 英文名称
	 */
	private String en;
	
	private String value;
	
	private String remark;
	
	//装key Value的List列表
	private	List<DataDicDTO> dataDicDTOList;
	
	

	public List<DataDicDTO> getDataDicDTOList() {
		return dataDicDTOList;
	}

	public void setDataDicDTOList(List<DataDicDTO> dataDicDTOList) {
		this.dataDicDTOList = dataDicDTOList;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	} 
	
	

}
