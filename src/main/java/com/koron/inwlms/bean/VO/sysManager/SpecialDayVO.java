package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;

public class SpecialDayVO {
	//特征日Id
		private Integer spId;
		//特征日中文名称
		private String cnName;
		
		//特征日英文名称
		private String enName;
		//繁文
		private String tcName;
		
		//特征日描述
		private String spRemark;
		//特征日日期
		private String spDate;
		//创建人
		private String createBy;
		//创建时间
		private Timestamp createTime;
		//修改人
		private String updateBy;
		//修改时间
		private Timestamp updateTime;
		public Integer getSpId() {
			return spId;
		}
		public void setSpId(Integer spId) {
			this.spId = spId;
		}
	   
		public String getCnName() {
			return cnName;
		}
		public String getEnName() {
			return enName;
		}
		public String getTcName() {
			return tcName;
		}
		public void setCnName(String cnName) {
			this.cnName = cnName;
		}
		public void setEnName(String enName) {
			this.enName = enName;
		}
		public void setTcName(String tcName) {
			this.tcName = tcName;
		}
		public String getSpRemark() {
			return spRemark;
		}
		public void setSpRemark(String spRemark) {
			this.spRemark = spRemark;
		}
		public String getSpDate() {
			return spDate;
		}
		public void setSpDate(String spDate) {
			this.spDate = spDate;
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
