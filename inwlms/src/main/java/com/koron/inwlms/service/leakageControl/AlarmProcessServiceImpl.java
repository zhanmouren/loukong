package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.mapper.master.leakageControl.AlarmProcessMapper;

@Service
public class AlarmProcessServiceImpl implements AlarmProcessService {

	@TaskAnnotation("queryAlarmProcess")
	@Override
	public List<AlarmProcessVO> queryAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcess(alarmProcessDTO);
		return list;
	}
	
	@TaskAnnotation("updateAlarmProcess")
	@Override
	public Integer updateAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.updataAlarmProcess(alarmProcessDTO);
		return num;
	}
	
	@TaskAnnotation("addAlarmProcess")
	@Override
	public Integer addAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.addAlarmProcess(alarmProcessDTO);
		return num;
	}
	
	@TaskAnnotation("deleteAlarmProcess")
	@Override
	public Integer deleteAlarmProcess(SessionFactory factory,Integer id) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.deleteAlarmProcess(id);
		return num;
	}
	
}
