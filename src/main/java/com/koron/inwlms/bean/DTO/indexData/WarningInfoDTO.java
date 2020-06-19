package com.koron.inwlms.bean.DTO.indexData;

import java.security.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 报警信息DTO
 * @author 小詹
 * @Date 2020/04/27
 */
public class WarningInfoDTO {
  //组装多个分区编号的List
	private List<String> areaZoneList;
	//预警信息编码
   private String warningInfoCode;
   //主报警编码
   private String pointCode;
   //预警方案编码
   private String schemeCode;
   //报警类型
   private String alarmType;
   //测站编码
   private String objectCode;
   //分区编号
   private String areaCode;
   //报警时间
   private String alarmTime;
   //任务创建时间
   private String taskCreateTime;
   //查找的结束时间
   private String searchTaskEndTime;
   //状态
   private String state;
   //分区类型
   private Integer areaType;
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getTaskCreateTime() {
	return taskCreateTime;
}
public void setTaskCreateTime(String taskCreateTime) {
	this.taskCreateTime = taskCreateTime;
}
public List<String> getAreaZoneList() {
	return areaZoneList;
}
public void setAreaZoneList(List<String> areaZoneList) {
	this.areaZoneList = areaZoneList;
}
public String getWarningInfoCode() {
	return warningInfoCode;
}
public void setWarningInfoCode(String warningInfoCode) {
	this.warningInfoCode = warningInfoCode;
}
public String getPointCode() {
	return pointCode;
}
public void setPointCode(String pointCode) {
	this.pointCode = pointCode;
}
public String getSchemeCode() {
	return schemeCode;
}
public void setSchemeCode(String schemeCode) {
	this.schemeCode = schemeCode;
}
public String getAlarmType() {
	return alarmType;
}
public void setAlarmType(String alarmType) {
	this.alarmType = alarmType;
}
public String getObjectCode() {
	return objectCode;
}
public void setObjectCode(String objectCode) {
	this.objectCode = objectCode;
}
public String getAreaCode() {
	return areaCode;
}
public void setAreaCode(String areaCode) {
	this.areaCode = areaCode;
}
public String getAlarmTime() {
	return alarmTime;
}
public void setAlarmTime(String alarmTime) {
	this.alarmTime = alarmTime;
}
public Integer getAreaType() {
	return areaType;
}
public void setAreaType(Integer areaType) {
	this.areaType = areaType;
}
public String getSearchTaskEndTime() {
	return searchTaskEndTime;
}
public void setSearchTaskEndTime(String searchTaskEndTime) {
	this.searchTaskEndTime = searchTaskEndTime;
}


   
}
