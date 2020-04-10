package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;

@Service
public interface EventInfoService {

	List<EventInfo> queryEventInfo(SessionFactory factory, EventInfoDTO eventInfoDTO);

	Integer deleteEventInfo(SessionFactory factory, String code);

	Integer updateEventInfo(SessionFactory factory, EventInfo eventInfo);

	Integer addEventInfo(SessionFactory factory, EventInfo eventInfo);
	
	

}
