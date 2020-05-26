package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeMessage;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeDateVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;

@Service
public interface WarningSchemeService {

	List<WarningSchemeVO> queryWarningScheme(SessionFactory factory, WarningSchemeDTO warningSchemeDTO);

	List<AlertNoticeScheme> queryAlertNoticeSchemeByWarningId(SessionFactory factory, String code);

	String addWarningScheme(SessionFactory factory, WarningSchemeDTO warningSchemeDTO, List<AlarmRuleDTO> alarmRuleDTOList);

	Integer updateWarningScheme(SessionFactory factory, WarningSchemeDTO warningSchemeDTO);

	Integer deleteWarningSchenme(SessionFactory factory, String code);

	List<AlarmRuleDTO> queryAlarmRuleByAlarmCode(SessionFactory factory, String alarmCode);

	Integer addAlarmRule(SessionFactory factory, AlarmRuleDTO alarmRuleDTO);

	Integer deleteAlarmRuleByAlarmCode(SessionFactory factory, String alarmCode);

	Integer updateAlarmRule(SessionFactory factory, AlarmRuleDTO alarmRuleDTO);

	List<AlertNoticeMessage> queryNoticeSchemeByWarningCode(SessionFactory factory, String code);

	List<AlertSchemeListVO> queryWarningSchemeList(SessionFactory factory, WarningSchemeDTO warningSchemeDTO);

	WarningSchemeDateVO queryWarningSchemeByCode(SessionFactory factory, WarningSchemeDTO warningSchemeDTO);

}
