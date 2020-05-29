package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessLog;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.GisExistZoneVO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
@EnvSource("_default")
public interface AlarmProcessMapper {
	
	List<AlarmProcessVO> queryAlarmProcess(AlarmProcessDTO alarmProcessDTO);
	Integer queryAlarmProcessTotalNumber(AlarmProcessDTO alarmProcessDTO);
	
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
	
	@Select(" select * from app_file where \"moduleType\" = #{type}")
	List<UploadFileDTO> queryAlarmProcessFile(@Param("type") String type);
	
	@Select("select * from gis_exist_zone where p_code = #{code}")
	GisExistZoneVO queryGisZone(@Param("code") String code);
	
	List<AlarmProcessLog> queryAlarmProcessLog(String taskCode);
	Integer addAlarmProcessLog(AlarmProcessLog alarmProcessLog);

}
