package com.koron.permission.bean.DTO;

import java.sql.Timestamp;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:操作DTO
 *
 */
public class TblOperationDTO{
	 //操作编码
     private String  opCode;
     //操作启用状态  0启用1停用
     private Integer opStatus;
     //操作权重
     private Integer opWeight;
     //操作名称 
     private String  opName;
     //标识 1菜单2按钮
     private Integer opFlag;
     
     //父类的外键
     private String foreignkey;
     //树的类型
	 private Integer type;
       
	//创建人
     private String creator;
     //修改人
     private String modifier;
     //创建时间
     private Timestamp create_time;
     //修改时间
     private Timestamp modify_time;
     
     //修改环境
     private String env;
     
     //增删改查标识
     private Integer crduFlag;
     
     
     
     
   
	public Integer getCrduFlag() {
		return crduFlag;
	}
	public void setCrduFlag(Integer crduFlag) {
		this.crduFlag = crduFlag;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public Integer getType() {
 		return type;
 	}
 	public void setType(Integer type) {
 		this.type = type;
 	}  
     
	public String getForeignkey() {
		return foreignkey;
	}
	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}
	public String getOpCode() {
		return opCode;
	}
	public Integer getOpStatus() {
		return opStatus;
	}
	public Integer getOpWeight() {
		return opWeight;
	}
	public String getOpName() {
		return opName;
	}
	public Integer getOpFlag() {
		return opFlag;
	}
	public String getCreator() {
		return creator;
	}
	public String getModifier() {
		return modifier;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public Timestamp getModify_time() {
		return modify_time;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}
	public void setOpWeight(Integer opWeight) {
		this.opWeight = opWeight;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setOpFlag(Integer opFlag) {
		this.opFlag = opFlag;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}
     
     
}
