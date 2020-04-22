package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

import com.koron.inwlms.util.ImportExcelUtil;

public class UserExcelDTO {

	 //职员名称
	 @ImportExcelUtil.ExcelColumn("职员名(必填)")
     @ImportExcelUtil.RequiredColumn
	 private String name;
	  //职员登录名称
	  @ImportExcelUtil.ExcelColumn("登录名称(必填)")
      @ImportExcelUtil.RequiredColumn
	  private String loginName;
	  //职员手机号
	  @ImportExcelUtil.ExcelColumn("手机号")
      @ImportExcelUtil.RequiredColumn
	  private String phone;
	  //职员Email	
	  @ImportExcelUtil.ExcelColumn("邮箱")
      @ImportExcelUtil.RequiredColumn
	  private String email;
	  //职员职位	
	  @ImportExcelUtil.ExcelColumn("职位")
      @ImportExcelUtil.RequiredColumn
	  private String positionStr;
	  
	  //职员性别
	  @ImportExcelUtil.ExcelColumn("性别")
      @ImportExcelUtil.RequiredColumn	
	  private String sexStr;
	  
     //工号
	  @ImportExcelUtil.ExcelColumn("工号(必填)")
      @ImportExcelUtil.RequiredColumn	  
	  private String workNo;

	  private Integer sex;
	  private Integer position;
	  //职员照片
	  private String photo;
	  //微信Id
	  private String openID;
	//职员登录密码
	  private String password;  
	  //职员编码
	  private String code;
	  public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPositionStr() {
		return positionStr;
	}

	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	 
}
