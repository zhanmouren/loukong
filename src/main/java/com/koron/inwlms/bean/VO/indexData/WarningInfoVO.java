package com.koron.inwlms.bean.VO.indexData;

import java.security.Timestamp;

/**
 * 报警信息VO
 * @author 小詹
 * @Date 2020/04/24
 */
public class WarningInfoVO {

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
	   //创建时间
	   private Timestamp createTime;
	   //修改时间
	   private Timestamp updateTime;
	   //创建人
	   private String createBy;
	   //修改人
	   private String updateBy;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	   
}
