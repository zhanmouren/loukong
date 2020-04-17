package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

/**
 * 表格映射明细bean
 *
* @Author xiaozhan
* @Date 2020.04.17
*/
public class TableMapperVO {

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
		public Integer getTableMapperId() {
			return tableMapperId;
		}
		public void setTableMapperId(Integer tableMapperId) {
			this.tableMapperId = tableMapperId;
		}
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
}
