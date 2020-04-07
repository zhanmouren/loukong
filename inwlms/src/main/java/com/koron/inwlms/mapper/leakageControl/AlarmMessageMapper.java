package com.koron.inwlms.mapper.master.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;

/**
 * 
 * @author 刘刚
 *
 */

@Repository
@EnvSource("_default")
public interface AlarmMessageMapper {
	
	List<AlarmMessageVO> queryAlarmMessage(WarningInfDTO warningInfDTO);
	
	List<AlarmMessageVO> queryAlarmMessageByRref(Integer id);
	

}
