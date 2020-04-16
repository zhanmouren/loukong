package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 表格字段映射明细bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

public class FieldMapperDTO {
	//字段映射code
	private String fieldMapperCode;
	//表格映射明细编码
	private String tableCode;
	//对方字段code
	private String otherFieldCode;
	//对方字段名称
	private String otherFieldName;
	//我方字段名
	private String fieldName;
	//我方字段Code
	private String fieldCode;
	//字段类型
	private String fieldType;
	//字段公式
	private String formula;
	//code值
	private Integer value;
	//表格名称
	private String tableNames;
	public String getTableNames() {
		return tableNames;
	}
	public void setTableNames(String tableNames) {
		this.tableNames = tableNames;
	}
		//创建人
		private String createBy;
		//创建时间
		private Timestamp createTime;
		//修改人
		private String updateBy;
		//修改时间
		private Timestamp updateTime;
	public String getFieldMapperCode() {
			return fieldMapperCode;
		}
		public void setFieldMapperCode(String fieldMapperCode) {
			this.fieldMapperCode = fieldMapperCode;
		}
		public String getTableCode() {
			return tableCode;
		}
		public void setTableCode(String tableCode) {
			this.tableCode = tableCode;
		}
		public String getFieldType() {
			return fieldType;
		}
		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
		public String getFormula() {
			return formula;
		}
		public void setFormula(String formula) {
			this.formula = formula;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
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
