package com.koron.inwlms.mapper.leakageControl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningTask;

/**
 * 
 * @author 刘刚
 *
 */

@Repository
public interface AlarmMessageMapper {
	
	List<AlarmMessageVO> queryAlarmMessage(WarningInfDTO warningInfDTO);
	
	@Select("select * from app_warninginf where \"pointCode\" = #{code}")
	List<AlarmMessageVO> queryAlarmMessageByPointCode(@Param("code") String code);
	
	Integer addWarningInf(AlarmMessageVO alarmMessageVO);
	
	Integer queryAlarmMessageTotalNumber(WarningInfDTO warningInfDTO);
	
	List<AlarmMessageVO> queryWarningCodeList(WarningInfDTO warningInfDTO);

	@Select("select * from app_warningtask where state = 0 and type = #{type}") 
	List<WarningTask> queryWarningTask(@Param("type") Integer type);
	
	Integer addWarningTask(WarningTask warningTask);
	
	@Update("update from app_warningtask set state = #{state} and \"updateTime\" = #{updateTime}")
	Integer updateWarningTask(@Param("state") Integer state,@Param("updateTime") Date updateTime);

}
