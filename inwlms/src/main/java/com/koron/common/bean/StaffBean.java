package com.koron.common.bean;

import com.koron.common.bean.IdentityBean;

public class StaffBean implements IdentityBean{
	/**
	 * 不可删除
	 */
	public static final int FLAG_UNDELETABLE = 1;
	/**
	 * 启用
	 */
	public static final int STATUS_ENABLE = 1;
	/**
	 * 停用
	 */
	public static final int STATUS_DISABLE = 0;

	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 职员编号
	 */
	private String code;
	/**
	 * 状态 0停用 1启用
	 * 
	 * @see #STATUS_ENABLE
	 * @see #STATUS_DISABLE
	 */
	private Integer status;
	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 部门ID
	 */
	private Integer departmentid;
	private String departmentname;
	private Long seq;
	/**
	 * 级别
	 */
	private Integer level;
	/**
	 * 登录人ID
	 */
	private String loginid;
	
	/**
	 * 登录名
	 */
	private String loginname;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 证件号码
	 */
	private String idcard;
	/**
	 * 设置
	 */
	public StaffBean setId(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * 获取
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置姓名
	 */
	public StaffBean setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 获取姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置职位
	 */
	public StaffBean setPosition(String position) {
		this.position = position;
		return this;
	}

	/**
	 * 获取职位
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * 设置状态 0停用 1启用
	 * 
	 * @see #STATUS_ENABLE
	 * @see #STATUS_DISABLE
	 */
	public StaffBean setStatus(Integer status) {
		this.status = status;
		return this;
	}

	/**
	 * 获取状态 0停用 1启用
	 * @see #STATUS_ENABLE
	 * @see #STATUS_DISABLE
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置电话号码
	 */
	public StaffBean setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	/**
	 * 获取电话号码
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 获取手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 获取手机号码
	 * @param mobile
	 */
	public StaffBean setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	/**
	 * 获取邮箱
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 * @param emiail
	 */
	public StaffBean setEmail(String emiail) {
		this.email = emiail;
		return this;
	}


	/**
	 * 设置级别
	 */
	public StaffBean setLevel(Integer level) {
		this.level = level;
		return this;
	}

	/**
	 * 获取级别
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置登录人ID
	 */
	public StaffBean setLoginid(String loginid) {
		this.loginid = loginid;
		return this;
	}

	/**
	 * 获取登录人ID
	 */
	public String getLoginid() {
		return loginid;
	}

	/**
	 * 获取登录名
	 * @return
	 */
	public String getLoginname() {
		return loginname;
	}

	/**
	 * 设置登录名
	 * @param loginname
	 */
	public StaffBean setLoginname(String loginname) {
		this.loginname = loginname;
		return this;
	}

	public Integer getDepartmentid() {
		return departmentid;
	}

	public StaffBean setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
		return this;
	}

	public Integer getSex() {
		return sex;
	}

	public StaffBean setSex(Integer sex) {
		this.sex = sex;
		return this;
	}

	public String getIdcard() {
		return idcard;
	}

	public StaffBean setIdcard(String idcard) {
		this.idcard = idcard;
		return this;
	}

	public String getCode() {
		return code;
	}

	public StaffBean setCode(String code) {
		this.code = code;
		return this;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
	
}