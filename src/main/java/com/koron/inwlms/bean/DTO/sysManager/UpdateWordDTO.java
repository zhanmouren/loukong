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
