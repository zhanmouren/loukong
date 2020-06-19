package com.koron.permission.bean.DTO;
/**
 * date: 2020/06/02
 * @author xiaozhan
 * description:数据库连接DTO
 *
 */
public class TblDatabaseDTO {
	//驱动
	private String dirver;	
	//链接地址
	private String url;
	//用户名
	private String username;
	//密码
	private String password;
	public String getDirver() {
		return dirver;
	}
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setDirver(String dirver) {
		this.dirver = dirver;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
