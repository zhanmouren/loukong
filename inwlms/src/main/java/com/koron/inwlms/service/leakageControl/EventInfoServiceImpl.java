package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.mapper.leakageControl.EventInfoMapper;


@Service
public class EventInfoServiceImpl implements EventInfoService{
	
	@TaskAnnotation("queryEventInfo")
	@Override
	public List<EventInfo> queryEventInfo(SessionFactory factory,EventInfoDTO eventInfoDTO){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<EventInfo> list = mapper.queryEventInfo(eventInfoDTO);
		return list;
	}
	
	@TaskAnnotation("deleteEventInfo")
	@Override
	public Integer deleteEventInfo(SessionFactory factory, String code) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.deleteEventInfo(code);
		return num;
	}
	
	@TaskAnnotation("updateEventInfo")
	@Override
	public Integer updateEventInfo(SessionFactory factory, EventInfo eventInfo) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.updateEventInfo(eventInfo);
		return num;
	}
	
	@TaskAnnotation("addEventInfo")
	@Override
	public Integer addEventInfo(SessionFactory factory, EventInfo eventInfo) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer num = mapper.addEventInfo(eventInfo);
		return num;
		
	}
	
	

}
