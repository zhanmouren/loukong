package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 同级菜单排序dto
 * @date:2020-05-21
 * @author xiaozhan
 *
 */
public class MenuSeqDTO {
    //操作的菜单code
	private String foreignkey;
	
	//树的类型
	private Integer type;
	
	

	//修改后同级排序的值
	private String menuSequence;

	//创建人
	  private String createBy;
	  //创建时间
	  private Timestamp createTime;
	  //修改人
	  private String updateBy;
	  //修改时间
	  private Timestamp updateTime;

	  
	public String getCreateBy() {
		return createBy;
	}




	public Timestamp getCreateTime() {
		return createTime;
	}




	public String getUpdateBy() {
		return updateBy;
	}




	public Timestamp getUpdateTime() {
		return updateTime;
	}




	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}




	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}




	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}




	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}




	public String getForeignkey() {
		return foreignkey;
	}




	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}




	public Integer getType() {
		return type;
	}




	public void setType(Integer type) {
		this.type = type;
	}


	  
	public String getMenuSequence() {
		return menuSequence;
	}


	public void setMenuSequence(String menuSequence) {
		this.menuSequence = menuSequence;
	}
	
}
