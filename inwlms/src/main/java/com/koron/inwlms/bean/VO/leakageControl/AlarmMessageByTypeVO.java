package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

public class AlarmMessageByTypeVO {

	private List<AlarmMessageByType> monitorAlarm;
	
	private List<AlarmMessageByType> zoneAlarm;

	public List<AlarmMessageByType> getMonitorAlarm() {
		return monitorAlarm;
	}

	public void setMonitorAlarm(List<AlarmMessageByType> monitorAlarm) {
		this.monitorAlarm = monitorAlarm;
	}

	public List<AlarmMessageByType> getZoneAlarm() {
		return zoneAlarm;
	}

	public void setZoneAlarm(List<AlarmMessageByType> zoneAlarm) {
		this.zoneAlarm = zoneAlarm;
	}
	
	
	
}
