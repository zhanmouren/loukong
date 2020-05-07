package com.koron.inwlms.bean.VO.sysManager;

/**
 * 数据字典主表VO
 *
* @Author xiaozhan
* @Date 2020.03.18
*/
public class DataDicNewVO {

	//数据字典Id
		private Integer dicId;
		//数据字典标识
		private String parentCode;
		//数据字典中文名称
	   private String scName;
		//数据字典英文名称
		private String enName;
		//数据字典繁体名称
		private String tcName;
		//数据字典备注
		private String description;
	   
		//数据字典明细键
		private String code;
		//值
		private String dicValue;
		
		//数据字典明细顺序
		private Integer sort;

		public Integer getDicId() {
			return dicId;
		}

		public String getParentCode() {
			return parentCode;
		}

		public String getScName() {
			return scName;
		}

		public String getEnName() {
			return enName;
		}

		public String getTcName() {
			return tcName;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}

		public String getDicValue() {
			return dicValue;
		}

		public Integer getSort() {
			return sort;
		}

		public void setDicId(Integer dicId) {
			this.dicId = dicId;
		}

		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}

		public void setScName(String scName) {
			this.scName = scName;
		}

		public void setEnName(String enName) {
			this.enName = enName;
		}

		public void setTcName(String tcName) {
			this.tcName = tcName;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public void setDicValue(String dicValue) {
			this.dicValue = dicValue;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}
		
}
