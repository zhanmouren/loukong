package com.koron.inwlms.bean.DTO.sysManager;

/**
 * 表格字段映射明细bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

public class FieldMapperDTO {
	//表格映射明细id
	private Integer tableId;
	//对方字段code
	private String otherFieldCode;
	//对方字段名称
	private String otherFieldName;
	//我方字段名
	private String fieldName;
	//我方字段Code
	private String fieldCode;
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public String getOtherFieldCode() {
		return otherFieldCode;
	}
	public void setOtherFieldCode(String otherFieldCode) {
		this.otherFieldCode = otherFieldCode;
	}
	public String getOtherFieldName() {
		return otherFieldName;
	}
	public void setOtherFieldName(String otherFieldName) {
		this.otherFieldName = otherFieldName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
}
