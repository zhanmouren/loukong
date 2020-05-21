package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

/**
 * 消息中心
 * @author lzy
 *
 */
public class LeakageMessageListVO<T> {

	//预警信息列表
	private List<AlarmMessageVO> AlarmMessageList;
	//漏损待办/已办列表
	private T LeakageProcessingList;
	//监测待办/已办列表
	private T MonitorProcessingList;
	//消息条数
	private Integer number;
	public List<AlarmMessageVO> getAlarmMessageList() {
		return AlarmMessageList;
	}
	public void setAlarmMessageList(List<AlarmMessageVO> leakageMessageList) {
		AlarmMessageList = leakageMessageList;
	}
	public T getLeakageProcessingList() {
		return LeakageProcessingList;
	}
	public void setLeakageProcessingList(T leakageProcessingList) {
		LeakageProcessingList = leakageProcessingList;
	}
	public T getMonitorProcessingList() {
		return MonitorProcessingList;
	}
	public void setMonitorProcessingList(T monitorProcessingList) {
		MonitorProcessingList = monitorProcessingList;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
