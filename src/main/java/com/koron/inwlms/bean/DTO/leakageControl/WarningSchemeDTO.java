package com.koron.inwlms.bean.DTO.leakageControl;

import java.util.Date;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;

/**
 * 
 * @author 刘刚
 *
 */
public class WarningSchemeDTO extends BaseDTO{
	
	private Integer id;
	/**
	 * 预警方案名称
	 */
	private String name;
	/**
	 * 方案状态
	 */
	private String state;
	/**
	 * 对象类型
	 */
	private String objectType;
	/**
	 * 报警类型
	 */
	private String alarmType;
	/**
	 * 
	 */
	private Integer objectId;
	
	private String firstPartion;
	
	private String seconPartion;
	
	private String dmaCode;
	/**
	 * 预警方案编码
	 */
	private String code;
	/**
	 * 报警指标类型
	 */
	private String alarmIndex;
	/**
	 * 预警规则信息
	 */
	private List<AlarmRuleDTO> alarmRuleList;
	/**
	 * 通知信息列表
	 */
	private List<AlertNoticeScheme> noticeList;
	
	private String createBy;
	
	private Date createTime;
	
	private String updateBy;
	
	private Date updateTime;
	/**
	 * 预警级别
	 */
	private String alarmLevel;
	/**
	 * 限值类型（固定限值或动态限值或无）
	 */
	private String paramType;
	
	
	public List<AlertNoticeScheme> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<AlertNoticeScheme> noticeList) {
		this.noticeList = noticeList;
	}

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
