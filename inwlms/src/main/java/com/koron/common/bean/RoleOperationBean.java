package com.koron.common.bean;
public class RoleOperationBean implements IdentityBean{
	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String role;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 角色ID
	 */
	private Integer roleid;
	/**
	 * 操作ID
	 */
	private Integer operationid;
	/**
	 * 参数
	 */
	private String param;
	/**
	 * 名称
	 */
	private String operatename;
	/**
	 * 关键字，用来权限判断用
	 */
	private String operationkey;
	/**
	*设置名称
	*/
	public void setRole(String role){
		this.role = role;
	}
	/**
	*获取名称
	*/
	public String getRole(){
		return role;
	}
	/**
	*设置描述
	*/
	public void setDescription(String description){
		this.description = description;
	}
	/**
	*获取描述
	*/
	public String getDescription(){
		return description;
	}
	/**
	*设置角色ID
	*/
	public void setRoleid(Integer roleid){
		this.roleid = roleid;
	}
	/**
	*获取角色ID
	*/
	public Integer getRoleid(){
		return roleid;
	}
	/**
	*设置操作ID
	*/
	public void setOperationid(Integer operationid){
		this.operationid = operationid;
	}
	/**
	*获取操作ID
	*/
	public Integer getOperationid(){
		return operationid;
	}
	/**
	*设置参数
	*/
	public void setParam(String param){
		this.param = param;
	}
	/**
	*获取参数
	*/
	public String getParam(){
		return param;
	}
	/**
	*设置名称
	*/
	public void setOperatename(String operatename){
		this.operatename = operatename;
	}
	/**
	*获取名称
	*/
	public String getOperatename(){
		return operatename;
	}
	/**
	*设置关键字，用来权限判断用
	*/
	public void setOperationkey(String operationkey){
		this.operationkey = operationkey;
	}
	/**
	*获取关键字，用来权限判断用
	*/
	public String getOperationkey(){
		return operationkey;
	}
	/**
	 * @return 获取ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param 设置ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
}