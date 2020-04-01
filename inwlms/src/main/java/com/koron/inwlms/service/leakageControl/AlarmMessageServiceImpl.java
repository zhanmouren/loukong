package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
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

	/**
	 * 统计报警类型数据或者对象类型数据
	 */
	@TaskAnnotation("queryAlarmMessageByType")
	@Override
	public List<AlarmMessageByTypeVO> queryAlarmMessageByType(SessionFactory factory, List<AlarmMessageVO> alarmMessageList){
		//统计监测预警不同对象的数据
		int numP = 0;
		int numF = 0;
		int numN = 0;
		int numPF = 0;
		for(AlarmMessageVO alarmMessageVO : alarmMessageList) {
			
		}
		return null;
		
	}
	
	@TaskAnnotation("queryAlarmMessageByRref")
	@Override
	public List<AlarmMessageVO> queryAlarmMessageByRref(SessionFactory factory,Integer id){
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryAlarmMessageByRref(id);
		return list;
	}
	
}
