package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;

public interface AlarmMessageService {

	List<AlarmMessageVO> queryAlarmMessage(SessionFactory factory, WarningInfDTO warningInfDTO);

}
