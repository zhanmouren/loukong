package com.koron.inwlms.bean.VO.sysManager;

/**
 * 角色VO
 *主要是返回哪条角色下面有关联职员
* @Author xiaozhan
* @Date 2020.03.23
*/
public class RoleMsgVO {
	//角色名称
  private String roleName;
   //返回信息
  private String  message;
   //返回结果
  private Integer result;
  public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Integer getResult() {
	return result;
}
public void setResult(Integer result) {
	this.result = result;
}


}
