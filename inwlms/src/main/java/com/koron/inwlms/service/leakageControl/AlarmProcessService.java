package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;

@Service
public interface AlarmProcessService {

	List<AlarmProcessVO> queryAlarmProcess(SessionFactory factory, AlarmProcessDTO alarmProcessDTO);

	Integer updateAlarmProcess(SessionFactory factory, AlarmProcessDTO alarmProcessDTO);

	Integer addAlarmProcess(SessionFactory factory, AlarmProcessDTO alarmProcessDTO);

	Integer deleteAlarmProcess(SessionFactory factory, Integer id);

}
