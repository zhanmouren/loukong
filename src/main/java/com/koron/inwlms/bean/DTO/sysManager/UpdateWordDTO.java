package com.koron.inwlms.bean.DTO.sysManager;

/**
 * @date   2020/04/23
 * @author xiaozhan
 *
 */
public class UpdateWordDTO {
	 //老密码
     private String oldPassWord;
     //新密码
     private String newPassWord;
     //确认密码
     private String surePassWord;
     //创建人
     private String createBy;
     //修改人
     private String updateBy;
     
	public String getCreateBy() {
		return createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getOldPassWord() {
		return oldPassWord;
	}
	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}
	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	public String getSurePassWord() {
		return surePassWord;
	}
	public void setSurePassWord(String surePassWord) {
		this.surePassWord = surePassWord;
	}
}
