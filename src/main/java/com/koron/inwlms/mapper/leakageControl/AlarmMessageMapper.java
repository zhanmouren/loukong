package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	
	@Select("select * from \"APP_warningInf\" where \"pointCode\" = #{code}")
	List<AlarmMessageVO> queryAlarmMessageByPointCode(@Param("code") String code);
	

}
