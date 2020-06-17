package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
    * 1管理员添加职员信息bean
    * 2修改职员信息bean
 * @Author xiaozhan
 * @Date 2020.03.17
 */


public class UserDTO{
  //存储多个用户code的List
  private List<String> userCodeList;
  //职员编码
  private String code;
  //职员id
  private Integer userId;
  //职员名称
  private String name;
  //职员登录名称
  private String loginName;
  //职员登录密码
  private String password;
  //职员手机号
  private String phone;
  //职员Email	
  private String Email;
  //职员职位
  private String position;

//职员性别
  private String sex;
  //职员电话
  private String photo;
  //微信Id
  private String openID;
  //工号
  private String workNo;
  //创建人
  private String createBy;
  //创建时间
  private Timestamp createTime;
  //修改人
  private String updateBy;
  //修改时间
  private Timestamp updateTime;
  //是否停用(0 启用，-1 停用)
  private Integer whetUse;
  //状态（0 在职，-1 离职）
  private Integer status;
  //部门code
  private String depCode;
  
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public List<String> getUserCodeList() {
	return userCodeList;
}
public void setUserCodeList(List<String> userCodeList) {
	this.userCodeList = userCodeList;
}

public String getDepCode() {
	return depCode;
}
public void setDepCode(String depCode) {
	this.depCode = depCode;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public Integer getWhetUse() {
	return whetUse;
}
public void setWhetUse(Integer whetUse) {
	this.whetUse = whetUse;
}
public Integer getStatus() {
	return status;
}
public void setStatus(Integer status) {
	this.status = status;
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
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLoginName() {
	return loginName;
}
public void setLoginName(String loginName) {
	this.loginName = loginName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}



public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getOpenID() {
	return openID;
}
public void setOpenID(String openID) {
	this.openID = openID;
}
public String getWorkNo() {
	return workNo;
}
public void setWorkNo(String workNo) {
	this.workNo = workNo;
}

}
