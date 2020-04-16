package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
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
	//表格映射code
	private String tableMapperCode;
	//集成配置主表Code
	private String configCode;
	//其他表的名称
	private String otherTabName;
	//对方表格code
	private String otherTabCode;
	//我方表格
	private String tableName;
	//我方表格code
	private String tableCode;
	
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	
	public String getTableMapperCode() {
		return tableMapperCode;
	}

	public void setTableMapperCode(String tableMapperCode) {
		this.tableMapperCode = tableMapperCode;
	}	

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

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

	//表格字段映射明细列表
	private List<FieldMapperDTO> fieldMapperList;

	public Integer getTableMapperId() {
		return tableMapperId;
	}

	public void setTableMapperId(Integer tableMapperId) {
		this.tableMapperId = tableMapperId;
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
