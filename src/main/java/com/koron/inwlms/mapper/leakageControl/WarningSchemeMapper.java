package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
@EnvSource("_default")
public interface WarningSchemeMapper {

	List<WarningSchemeVO> queryWarningScheme(WarningSchemeDTO warningSchemeDTO);
	List<AlertNoticeScheme> queryAlertNoticeSchemeByWarningId(String code);
	Integer addWarningScheme(WarningSchemeDTO warningSchemeDTO);
	Integer deleteWarningScheme(String code);
	Integer updateWarningScheme(WarningSchemeDTO warningSchemeDTO);
	List<AlarmRuleDTO> queryAlarmRuleByAlarmCode(String alarmCode);
	Integer addAlarmRule(AlarmRuleDTO alarmRuleDTO);
	Integer deleteAlarmRuleByAlarmCode(String schemeCode);
	Integer updateAlarmRule(AlarmRuleDTO alarmRuleDTO);
	
	List<AlertNoticeSchemeVO> queryNoticeSchemeByWarningCode(String code);
	
	@Select("select * from \"APP_warningScheme\" where \"state\" = #{state}")
	List<WarningSchemeVO> queryWarningSchemeStart(@Param("state") String state);
}
