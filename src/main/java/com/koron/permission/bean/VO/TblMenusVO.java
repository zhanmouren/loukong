package com.koron.permission.bean.VO;

import java.util.List;

import com.koron.common.web.mapper.TreeBean;
import com.koron.common.web.mapper.TreeBean.Integer;

public class TblMenusVO extends TreeBean.Long{
	//菜单同级顺序
	   private java.lang.Integer sequence;
	    public java.lang.Integer getSequence() {
		return sequence;
	}

	public void setSequence(java.lang.Integer sequence) {
		this.sequence = sequence;
	}
    //角色code
	private String roleCode;
	//角色名称
	private String roleName;
	//模块Id
	private Integer menuId;
   //菜单code
	private String moduleCode;
	//模块编号
	private String moduleNo;
	//模块名称
	private String moduleName;
	//链接地址
	private String linkAddress;
	
	//操作code
	private String opCode;
	//操作名称
	private String opName;
	
    //按钮编码加名称
	private String opCodeName;
	//按钮编码加名称List
    private List<String> opCodeNameList;
	
    public List<String> getOpCodeNameList() {
		return opCodeNameList;
	}

	public void setOpCodeNameList(List<String> opCodeNameList) {
		this.opCodeNameList = opCodeNameList;
	}

	public String getOpCode() {
		return opCode;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * 分级ID
	 */
	private java.lang.Integer id;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 外键
	 */
	private String foreignkey;

	/**
	 * 设置分级ID
	 */
	public TblMenusVO setId(java.lang.Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * 获取分级ID
	 */
	public java.lang.Integer getId() {
		return id;
	}

	/**
	 * 设置类型
	 */
	public TblMenusVO setType(int type) {
		this.type = type;
		return this;
	}

	/**
	 * 获取类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置外键
	 */
	public TblMenusVO setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
		return this;
	}

	/**
	 * 获取外键
	 */
	public String getForeignkey() {
		return foreignkey;
	}

	@Override
	public String toString() {
		return "TblMenusVO [sequence=" + sequence + ", roleCode=" + roleCode + ", roleName="
				+ roleName + ",menuId=" + menuId + ", moduleCode=" + moduleCode
				+ ", moduleNo=" + moduleNo + ", moduleName=" + moduleName + ", linkAddress=" + linkAddress + ", opCode="
				+ opCode + ", opName=" + opName + ", opCodeName=" + opCodeName + ",opCodeNameList="+opCodeNameList+", id=" + id + ", type=" + type
				+ ", foreignkey=" + foreignkey + ",parentMask=" + super.getParentMask() + ", mask=" + super.getMask() + ", childMask=" + super.getChildMask() + "]";
	}

	public String getOpCodeName() {
		return opCodeName;
	}

	public void setOpCodeName(String opCodeName) {
		this.opCodeName = opCodeName;
	}
	

}
