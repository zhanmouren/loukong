package com.koron.inwlms.service.leakageControl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventSubTypeDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.mapper.leakageControl.EventInfoMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.util.Constant;


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
	
	@TaskAnnotation("querychildKey")
	@Override
	public List<DataDicRelationVO> querychildKey(SessionFactory factory,String parentKey){
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		List<DataDicRelationVO> list = mapper.querychildKey(parentKey);
		return list;
	}
	
	@TaskAnnotation("queryMaxKey")
	@Override
	public Integer queryMaxKey(SessionFactory factory,String parent) {
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		Integer maxKey = mapper.queryMaxKey(parent);
		return maxKey;
	}
	
	@TaskAnnotation("addEventSubType")
	@Override
	public Integer addEventSubType(SessionFactory factory,EventSubTypeDTO eventSubTypeDTO) {
		//插入数据字典关联表
		EventInfoMapper mapper = factory.getMapper(EventInfoMapper.class);
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		Integer relationNum = 0;
		
		int i = 0;
		List<DataDicDTO> dataDicDTOList=new ArrayList<DataDicDTO>();
		for(DataDicDTO dataDicDTO : eventSubTypeDTO.getDataDicDTOList()) {
			relationNum = mapper.addEventTypeRelation(eventSubTypeDTO.getParentKey(), dataDicDTO.getDicKey());
			if(relationNum > 0) {
				DataDicDTO dataDicDTONew = new DataDicDTO();
				dataDicDTONew.setDicCn(dataDicDTO.getDicCn());
				dataDicDTONew.setDicEn(dataDicDTO.getDicEn());
				dataDicDTONew.setDicParent(Constant.EVENTSUBTYPE);
				dataDicDTONew.setDicRemark(dataDicDTO.getDicRemark());
				dataDicDTONew.setDicKey(dataDicDTO.getDicKey());
				dataDicDTONew.setDicValue(dataDicDTO.getDicValue());
				dataDicDTONew.setDicSeq(i);
				//TODO 创建人等
				dataDicDTOList.add(dataDicDTONew);
			}
		}
		Integer eventSubTypeNum = userMapper.addDataDic(dataDicDTOList);
		
		return eventSubTypeNum;
	}
	
	

}
