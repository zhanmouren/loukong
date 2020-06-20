package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventSubTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.QueryEventFileDTO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.bean.VO.leakageControl.EventInfoListReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.EventWarnRelation;

@Service
public interface EventInfoService {

	EventInfoListReturnVO queryEventInfo(SessionFactory factory, EventInfoDTO eventInfoDTO);

	Integer deleteEventInfo(SessionFactory factory, String code);

	Integer updateEventInfo(SessionFactory factory, EventInfo eventInfo);

	String addEventInfo(SessionFactory factory, EventInfo eventInfo);

	List<DataDicRelationVO> querychildKey(SessionFactory factory, EventTypeDTO eventTypeDTO);

	Integer queryMaxKey(SessionFactory factory, String parent);

	Integer addEventSubType(SessionFactory factory, EventSubTypeDTO eventSubTypeDTO);

	Integer deleteEventSubType(SessionFactory factory, String key);

	EventInfo queryEventInfoByCode(SessionFactory factory, String code);

	Integer addEventWarnRelation(SessionFactory factory, List<EventWarnRelation> eventWarnRelationList);

	List<EventWarnRelation> queryEventWarnRelation(SessionFactory factory, String processCode);

	Integer deleteEventWarnRelation(SessionFactory factory, String processCode, String eventCode);

	UploadFileDTO queryFileDataById(SessionFactory factory, Integer id);

	List<UploadFileDTO> queryEventFile(SessionFactory factory, QueryEventFileDTO queryEventFileDTO);

	Integer deleteFileRelation(SessionFactory factory, QueryEventFileDTO queryEventFileDTO);
	
	

}
