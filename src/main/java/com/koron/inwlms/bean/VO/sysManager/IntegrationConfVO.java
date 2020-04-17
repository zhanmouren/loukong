package com.koron.inwlms.bean.VO.sysManager;


import java.sql.Timestamp;

/**
 * 集成bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
public class IntegrationConfVO {
	 //集成配置Id
		private Integer inteConfId;
		//集成配置的编码
		private String inteConfCode;
		//集成配置状态
		private Integer status;
		//对方系统名称
		private String sysName;
		//对方JDBC
		private String otherJDBC;
		
		//创建人
		private String createBy;
		//创建时间
		private Timestamp createTime;
		//修改人
		private String updateBy;
		//修改时间
		private Timestamp updateTime;
		public Integer getInteConfId() {
			return inteConfId;
		}
		public void setInteConfId(Integer inteConfId) {
			this.inteConfId = inteConfId;
		}
		public String getInteConfCode() {
			return inteConfCode;
		}
		public void setInteConfCode(String inteConfCode) {
			this.inteConfCode = inteConfCode;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getSysName() {
			return sysName;
		}
		public void setSysName(String sysName) {
			this.sysName = sysName;
		}
		public String getOtherJDBC() {
			return otherJDBC;
		}
		public void setOtherJDBC(String otherJDBC) {
			this.otherJDBC = otherJDBC;
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

