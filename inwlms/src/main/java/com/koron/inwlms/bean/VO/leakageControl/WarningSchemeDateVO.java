package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;

public class WarningSchemeDateVO {
	
	private WarningSchemeVO warningScheme;
	
	private List<AlarmRuleDTO> alarmRuleList;

	public WarningSchemeVO getWarningScheme() {
		return warningScheme;
	}

	public void setWarningScheme(WarningSchemeVO warningScheme) {
		this.warningScheme = warningScheme;
	}

	public List<AlarmRuleDTO> getAlarmRuleList() {
		return alarmRuleList;
	}

	public void setAlarmRuleList(List<AlarmRuleDTO> alarmRuleList) {
		this.alarmRuleList = alarmRuleList;
	}
	
	

}
