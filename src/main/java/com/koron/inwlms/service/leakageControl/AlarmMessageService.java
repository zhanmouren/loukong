package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;

public interface AlarmMessageService {

	List<AlarmMessageVO> queryAlarmMessage(SessionFactory factory, WarningInfDTO warningInfDTO);


	List<AlarmMessageByTypeVO> queryAlarmMessageByType(SessionFactory factory,
			List<AlarmMessageVO> alarmMessageList);


	List<AlarmMessageVO> queryAlarmMessageByPointCode(SessionFactory factory, String code);

}
