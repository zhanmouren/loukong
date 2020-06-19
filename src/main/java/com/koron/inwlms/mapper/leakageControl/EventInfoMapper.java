package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventInfoDTO;
import com.koron.inwlms.bean.DTO.leakageControl.EventTypeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.QueryEventFileDTO;
import com.koron.inwlms.bean.VO.leakageControl.DataDicRelationVO;
import com.koron.inwlms.bean.VO.leakageControl.EventInfo;
import com.koron.inwlms.bean.VO.leakageControl.EventWarnRelation;


/**
 * 
 * @author 刘刚
 *
 */
@Repository

public interface EventInfoMapper {

	List<EventInfo> queryEventInfo(EventInfoDTO eventInfoDTO);
	
	Integer queryEventInfoTotalNumber(EventInfoDTO eventInfoDTO);
	
	Integer deleteEventInfo(String code);
	
	Integer updateEventInfo(EventInfo eventInfo); 
	
	Integer addEventInfo(EventInfo eventInfo);
	List<DataDicRelationVO> querychildKey(EventTypeDTO eventTypeDTO);
	
	Integer queryMaxKey(@Param("parent") String parent);
	
	@Insert("insert into sm_datadictionaryrelation(\"parentKey\", \"childKey\")\r\n" + 
			" values(#{parentKey}, #{childKey})")
	Integer addEventTypeRelation(@Param("parentKey") String parentKey,@Param("childKey") String childKey);
	
	@Select("delete from sm_datadictionaryrelation where \"childKey\" = #{childKey}")
	Integer deleteEventSubType(@Param("childKey") String childKey);
	
	@Select("select * from app_eventinfo where code = #{code}")
	EventInfo queryEventInfoByCode(@Param("code") String code);
	
	Integer queryChildKeyNum(EventTypeDTO eventTypeDTO);
	
	Integer addEventWarnRelation(List<EventWarnRelation> eventWarnRelationList);
	
	List<EventWarnRelation> queryEventWarnRelation(String processCode);
	
	@Delete("delete from app_eventwarnrelation where \"processCode\" = #{processCode} and \"eventCode\" = #{eventCode}")
	Integer deleteEventWarnRelation(@Param("processCode") String processCode,@Param("eventCode") String eventCode);
	
	@Select("select * from app_file where id = #{id}")
	UploadFileDTO queryFileById(@Param("id") Integer id);
	
	List<UploadFileDTO> queryEventFile(QueryEventFileDTO queryEventFileDTO);
	
}
