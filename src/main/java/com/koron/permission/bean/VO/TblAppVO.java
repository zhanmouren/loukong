package com.koron.permission.bean.VO;

import java.sql.Timestamp;

/**
 * date: 2020/06/01
 * @author xiaozhan
 * description:应用appVO
 *
 */
public class TblAppVO {
	//主键id
    private Integer appId;
    //应用编码
    private String appCode;
    //应用名称
    private String appName;
    //应用状态 0启用1停用
    private Integer appStatus;
    //应用权重
    private Integer appWeight;
    
    //创建人
    private String creator;
    //修改人
    private String modifier;
    //创建时间
    private Timestamp create_time;
    //修改时间
    private Timestamp modify_time;
	public Integer getAppId() {
		return appId;
	}
	public String getAppCode() {
		return appCode;
	}
	public String getAppName() {
		return appName;
	}
	public Integer getAppStatus() {
		return appStatus;
	}
	public Integer getAppWeight() {
		return appWeight;
	}
	public String getCreator() {
		return creator;
	}
	public String getModifier() {
		return modifier;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public Timestamp getModify_time() {
		return modify_time;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}
	public void setAppWeight(Integer appWeight) {
		this.appWeight = appWeight;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}
    
}
