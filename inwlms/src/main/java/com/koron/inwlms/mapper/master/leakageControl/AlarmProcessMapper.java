package com.koron.inwlms.mapper.master.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

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
	Integer addAlarmProcess(AlarmProcessDTO alarmProcessDTO);
	Integer updataAlarmProcess(AlarmProcessDTO alarmProcessDTO);
	Integer deleteAlarmProcess(Integer id);

}
