package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
@EnvSource("_default")
public interface EventInfoMapper {

	List<EventInfo> queryEventInfo(EventInfoDTO eventInfoDTO);
	
	Integer deleteEventInfo(String code);
	
	Integer updateEventInfo(EventInfo eventInfo); 
	
	Integer addEventInfo(EventInfo eventInfo);
	List<DataDicRelationVO> querychildKey(String parentKey);
	
	Integer queryMaxKey(@Param("parent") String parent);
	Integer addEventTypeRelation(@Param("parentKey") String parentKey,@Param("childKey") String childKey);
	
}
