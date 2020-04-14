package com.koron.inwlms.bean.VO.sysManager;

/**
 * 菜单VO
* @Author xiaozhan
* @Date 2020.03.23
*/
public class ModuleMenuVO {
	//id
	private Integer moduleId;
	//菜单code
		private String moduleCode;
		//模块编号
		private String moduleNo;
		//模块名称
		private String moduleName;
		//链接地址
		private String linkAddress;
		public Integer getModuleId() {
			return moduleId;
		}
		public void setModuleId(Integer moduleId) {
			this.moduleId = moduleId;
		}
		public String getModuleCode() {
			return moduleCode;
		}
		public void setModuleCode(String moduleCode) {
			this.moduleCode = moduleCode;
		}
		public String getModuleNo() {
			return moduleNo;
		}
		public void setModuleNo(String moduleNo) {
			this.moduleNo = moduleNo;
		}
		public String getModuleName() {
			return moduleName;
		}
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}
		public String getLinkAddress() {
			return linkAddress;
		}
		public void setLinkAddress(String linkAddress) {
			this.linkAddress = linkAddress;
		}
}
