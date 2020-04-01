package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.Date;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 
 * @author 刘刚
 *
 */
public class WarningSchemeDTO extends BaseDTO{
	
	private Integer id;
	
	private String name;
	
	private String state;
	
	private String objectType;
	
	private String alarmType;
	
	private Integer objectId;
	
	private String firstPartion;
	
	private String seconPartion;
	
	private String dmaCode;
	
	private String code;
	
	private String alarmIndex;
	
	private List<AlarmRuleDTO> alarmRuleList;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;
	
	private String alarmLevel;
	
	private String paramType;
	
	
	
	

	public String getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAlarmIndex() {
		return alarmIndex;
	}

	public void setAlarmIndex(String alarmIndex) {
		this.alarmIndex = alarmIndex;
	}

	

	public List<AlarmRuleDTO> getAlarmRuleList() {
		return alarmRuleList;
	}

	public void setAlarmRuleList(List<AlarmRuleDTO> alarmRuleList) {
		this.alarmRuleList = alarmRuleList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getFirstPartion() {
		return firstPartion;
	}

	public void setFirstPartion(String firstPartion) {
		this.firstPartion = firstPartion;
	}

	public String getSeconPartion() {
		return seconPartion;
	}

	public void setSeconPartion(String seconPartion) {
		this.seconPartion = seconPartion;
	}

	public String getDmaCode() {
		return dmaCode;
	}

	public void setDmaCode(String dmaCode) {
		this.dmaCode = dmaCode;
	}
	
	
	

}
