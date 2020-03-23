package com.koron.inwlms.bean.DTO.sysManager;

import java.util.List;


/**
 * 表格映射明细bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

public class TableMapperDTO {
    //集成表格映射明细
	private Integer tableMapperId;
	//集成配置主表Id
	private Integer configID;
	//其他表的名称
	private String otherTabName;
	//对方表格code
	private String otherTabCode;
	//我方表格
	private String tableName;
	//我方表格code
	private String tableCode;
	
	//表格字段映射明细列表
	private List<FieldMapperDTO> fieldMapperList;

	public Integer getTableMapperId() {
		return tableMapperId;
	}

	public void setTableMapperId(Integer tableMapperId) {
		this.tableMapperId = tableMapperId;
	}

	public Integer getConfigID() {
		return configID;
	}

	public void setConfigID(Integer configID) {
		this.configID = configID;
	}

	public String getOtherTabName() {
		return otherTabName;
	}

	public void setOtherTabName(String otherTabName) {
		this.otherTabName = otherTabName;
	}

	public String getOtherTabCode() {
		return otherTabCode;
	}

	public void setOtherTabCode(String otherTabCode) {
		this.otherTabCode = otherTabCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public List<FieldMapperDTO> getFieldMapperList() {
		return fieldMapperList;
	}

	public void setFieldMapperList(List<FieldMapperDTO> fieldMapperList) {
		this.fieldMapperList = fieldMapperList;
	}

}
