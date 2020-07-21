package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ObjectData;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeHisData;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessLog;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.EnvelopeDataVO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
public interface AlarmProcessMapper {
	
	List<AlarmProcessVO> queryAlarmProcess(AlarmProcessDTO alarmProcessDTO);
	Integer queryAlarmProcessTotalNumber(AlarmProcessDTO alarmProcessDTO);
	List<AlarmProcessVO> queryAlarmMessageByP(AlarmProcessDTO alarmProcessDTO);
	AlarmProcessVO queryAlarmMessageByCode(String code);
	List<AlarmProcessVO> queryAlarmProcessByTaskCode(String taskCode);
	Integer addAlarmProcess(AlarmProcessVO alarmProcessVO);
	Integer updateAlarmProcess(AlarmProcessVO alarmProcessVO);
	Integer deleteAlarmProcess(String code);
	
	String addAlarmProcessOfZQS(AlarmProcessVO alarmProcessVO);
	String addAlarmProcessOfZCX(AlarmProcessVO alarmProcessVO);
	String addAlarmProcessOfPCX(AlarmProcessVO alarmProcessVO);
	String addAlarmProcessOfPLX(AlarmProcessVO alarmProcessVO);
	String addAlarmProcessOfPZS(AlarmProcessVO alarmProcessVO);
	
	@Select(" select * from app_file where id = #{id}")
	UploadFileDTO queryAlarmProcessFile(@Param("id") Integer id);
	
	@Select(" select \"fileId\" from app_filerelation where code = #{code}")
	List<Integer> queryAlarmProcessFileRelation(@Param("code") String code);
	
	
	@Select("select * from gis_exist_zone where p_code = #{code}")
	GisExistZoneVO queryGisZone(@Param("code") String code);
	
	List<AlarmProcessLog> queryAlarmProcessLog(String taskCode);
	Integer addAlarmProcessLog(AlarmProcessLog alarmProcessLog);
	
	@Select("select name from sm_user where code = #{code}")
	String queryUserNameByCode(@Param("code") String code);
	
	List<GisExistZoneVO> queryZoneData(ObjectData objectData);
	
	Integer addEnvelopeData(List<WarningSchemeHisData> list);
	
	List<WarningSchemeHisData> queryEnvelopeData(String schemeCode);

}
