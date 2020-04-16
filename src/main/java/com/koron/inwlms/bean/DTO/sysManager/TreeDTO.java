package com.koron.inwlms.bean.DTO.sysManager;


/**
 * 树状传参DTO 组织部门DTO
 *
* @Author xiaozhan
* @Date 2020.04.01
*/
public class TreeDTO {
	//组织Id
   private Integer Id;
   //组织Code
   private String orgCode;
   //部门名称
   private String depName;
   //父部门外键
   private String foreignKey;
  //树的类型
   private Integer type;
   
   public String getOrgCode() {
	return orgCode;
}
public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
}
//组织下添加树还是部门下添加部门
   private Integer addType;
 
   //判断组织下删除部门还是部门下删除部门
   private Integer deleteType;

public Integer getDeleteType() {
	return deleteType;
}
public void setDeleteType(Integer deleteType) {
	this.deleteType = deleteType;
}
public Integer getAddType() {
	return addType;
}
public void setAddType(Integer addType) {
	this.addType = addType;
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
public Integer getId() {
	return Id;
}
public void setId(Integer id) {
	Id = id;
}
public String getDepName() {
	return depName;
}
public void setDepName(String depName) {
	this.depName = depName;
}

   
}
