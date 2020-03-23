package com.koron.common.web.bean;
public class DepartmentTreeBean extends DepartmentBean{
	/**
	 * 分级编码
	 */
	private Long seq;
	/**
	 * 父级掩位数
	 */
	private Integer parentmask;
	/**
	 * 节点打开(默认关闭)
	 */
	private boolean open =false;
	
	/**
	 *部门关联的角色key
	 */
	private String rolekey ;
	
	/**
	 * 节点选择(默认不选中)
	 */
	private boolean checked=false;
	/**|
	*设置分级编码
	*/
	
	public DepartmentTreeBean setSeq(Long seq){
		this.seq = seq;
	return this;
	}
	/**
	*获取分级编码
	*/
	public Long getSeq(){
		return seq;
	}
	/**
	*设置父级掩位数
	*/
	public DepartmentTreeBean setParentmask(Integer parentmask){
		this.parentmask = parentmask;
	return this;
	}
	/**
	*获取父级掩位数
	*/
	public Integer getParentmask(){
		return parentmask;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getRolekey() {
		return rolekey;
	}
	public void setRolekey(String rolekey) {
		this.rolekey = rolekey;
	}

}