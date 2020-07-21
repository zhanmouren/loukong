package com.koron.inwlms.bean.VO.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;

public class WarningSchemeDateVO {
	
	private WarningSchemeVO warningScheme;
	
	private List<AlarmRuleDTO> alarmRuleList;
	
	private List<AlertNoticeScheme> noticeList;
	
	private EnvelopeDataVO envelopeDataVO;
	

	public EnvelopeDataVO getEnvelopeDataVO() {
		return envelopeDataVO;
	}

	public void setEnvelopeDataVO(EnvelopeDataVO envelopeDataVO) {
		this.envelopeDataVO = envelopeDataVO;
	}

	public List<AlertNoticeScheme> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<AlertNoticeScheme> noticeList) {
		this.noticeList = noticeList;
	}

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
