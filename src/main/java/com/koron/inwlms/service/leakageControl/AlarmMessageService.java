package com.koron.inwlms.service.leakageControl;

import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningTask;

public interface AlarmMessageService {

	AlarmMessageReturnVO queryAlarmMessage(SessionFactory factory, WarningInfDTO warningInfDTO);


	AlarmMessageByTypeVO queryAlarmMessageByObjectType(SessionFactory factory,
			WarningInfDTO warningInfDTO);


	List<AlarmMessageVO> queryAlarmMessageByPointCode(SessionFactory factory, String code);


	AlarmMessageByTypeVO queryAlarmMessageByAlarmType(SessionFactory factory, WarningInfDTO warningInfDTO);


	List<AlarmMessageVO> queryWarningCodeList(SessionFactory factory, WarningInfDTO warningInfDTO);


	List<WarningTask> queryWarningTask(SessionFactory factory, Integer type);


	Integer addWarningTask(SessionFactory factory, WarningTask warningTask);


	Integer updateWarningTask(SessionFactory factory, Integer state, Date updateTime);

}
