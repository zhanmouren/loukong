package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

/**
 * 消息中心
 * @author lzy
 *
 */
public class LeakageMessageListVO<T> {

	//预警信息列表
	private List<AlarmMessageVO> alarmMessageList;
	//漏损待办/已办列表
	private T leakageProcessingList;
	//监测待办/已办列表
	private T monitorProcessingList;
	//所有的消息条数
	private Integer totals;
	//待办条数
	private Integer number;
	//漏损待办条数
	private Integer leakageProcessingNumber;
	//监测待办条数
	private Integer monitorProcessingNumber;
	
	
	public Integer getMonitorProcessingNumber() {
		return monitorProcessingNumber;
	}
	public void setMonitorProcessingNumber(Integer monitorProcessingNumber) {
		this.monitorProcessingNumber = monitorProcessingNumber;
	}
	public List<AlarmMessageVO> getAlarmMessageList() {
		return alarmMessageList;
	}
	public void setAlarmMessageList(List<AlarmMessageVO> alarmMessageList) {
		this.alarmMessageList = alarmMessageList;
	}
	public T getLeakageProcessingList() {
		return leakageProcessingList;
	}
	public void setLeakageProcessingList(T leakageProcessingList) {
		this.leakageProcessingList = leakageProcessingList;
	}
	public T getMonitorProcessingList() {
		return monitorProcessingList;
	}
	public void setMonitorProcessingList(T monitorProcessingList) {
		this.monitorProcessingList = monitorProcessingList;
	}
	public Integer getTotals() {
		return totals;
	}
	public void setTotals(Integer totals) {
		this.totals = totals;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getLeakageProcessingNumber() {
		return leakageProcessingNumber;
	}
	public void setLeakageProcessingNumber(Integer leakageProcessingNumber) {
		this.leakageProcessingNumber = leakageProcessingNumber;
	}
	
	
}
