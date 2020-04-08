package com.koron.inwlms.bean.DTO.sysManager;


/**
 * 树状传参DTO 模块DTO
 *
* @Author xiaozhan
* @Date 2020.04.08
*/
public class MenuTreeDTO {
	 //父部门外键
	   private String foreignKey;
	  //树的类型
	   private Integer type;
	   //目录的名称
	   private String moduleName;
	   //模块编号
	   private String moduleNo;
	   //模块编码
	   private String moduleCode;
	   //模块编号
	   private String linkAddress;
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getLinkAddress() {
		return linkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}
	public String getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
