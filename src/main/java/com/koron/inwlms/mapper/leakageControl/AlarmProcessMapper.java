package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
@EnvSource("_default")
public interface AlarmProcessMapper {
	
	List<AlarmProcessVO> queryAlarmProcess(AlarmProcessDTO alarmProcessDTO);
	List<AlarmProcessVO> queryAlarmProcessByTaskCode(String taskCode);
	Integer addAlarmProcess(AlarmProcessVO alarmProcessVO);
	Integer updateAlarmProcess(AlarmProcessVO alarmProcessVO);
	Integer deleteAlarmProcess(String code);
	
	Integer addAlarmProcessOfZQS(AlarmProcessVO alarmProcessVO);
	Integer addAlarmProcessOfZCX(AlarmProcessVO alarmProcessVO);
	Integer addAlarmProcessOfPCX(AlarmProcessVO alarmProcessVO);
	Integer addAlarmProcessOfPLX(AlarmProcessVO alarmProcessVO);
	Integer addAlarmProcessOfPZS(AlarmProcessVO alarmProcessVO);
	
	@Select(" select * from \"APP_file\" where \"moduleType\" = #{type}")
	List<UploadFileDTO> queryAlarmProcessFile(@Param("type") String type);

}
