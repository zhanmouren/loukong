package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventTypeDTO;
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
	List<DataDicRelationVO> querychildKey(EventTypeDTO eventTypeDTO);
	
	Integer queryMaxKey(@Param("parent") String parent);
	
	@Insert("insert into \"SM_dataDictionaryRelation\"(\"parentKey\", \"childKey\")\r\n" + 
			" values(#{parentKey}, #{childKey})")
	Integer addEventTypeRelation(@Param("parentKey") String parentKey,@Param("childKey") String childKey);
	
	@Select("delete from \"SM_dataDictionaryRelation\" where \"childKey\" = #{childKey}")
	Integer deleteEventSubType(@Param("childKey") String childKey);
	
	@Select("select * from \"APP_eventInfo\" where code = #{code}")
	EventInfo queryEventInfoByCode(@Param("code") String code);
}
