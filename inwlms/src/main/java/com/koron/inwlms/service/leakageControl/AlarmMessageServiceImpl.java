package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.mapper.master.TestMapper;
import com.koron.inwlms.mapper.master.leakageControl.AlarmMessageMapper;

@Service
public class AlarmMessageServiceImpl implements AlarmMessageService {

	@TaskAnnotation("queryAlarmMessage")
	@Override
	public List<AlarmMessageVO> queryAlarmMessage(SessionFactory factory, WarningInfDTO warningInfDTO){
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryAlarmMessage(warningInfDTO);
		
		return list;
	}

	
}
