package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.TreeVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;

/**
 * 
 * @author 刘刚
 *
 */
@Repository
public interface WarningSchemeMapper {

	List<WarningSchemeVO> queryWarningScheme(WarningSchemeDTO warningSchemeDTO);
	Integer queryWarningSchemeTotalNumber(WarningSchemeDTO warningSchemeDTO);
	
	List<AlertNoticeScheme> queryAlertNoticeSchemeByWarningId(String code);
	Integer addWarningScheme(WarningSchemeDTO warningSchemeDTO);
	Integer deleteWarningScheme(String code);
	Integer updateWarningScheme(WarningSchemeDTO warningSchemeDTO);
	List<AlarmRuleDTO> queryAlarmRuleByAlarmCode(String alarmCode);
	Integer addAlarmRule(AlarmRuleDTO alarmRuleDTO);
	Integer deleteAlarmRuleByAlarmCode(String schemeCode);
	Integer updateAlarmRule(AlarmRuleDTO alarmRuleDTO);
	
	Integer addNoticeScheme(AlertNoticeScheme alertNoticeScheme);
	
	Integer deleteNoticeScheme(String schemeCode);
	
	List<AlertNoticeSchemeVO> queryNoticeSchemeByWarningCode(String code);
	
	@Select("select * from app_warningscheme where \"state\" = #{state}")
	List<WarningSchemeVO> queryWarningSchemeStart(@Param("state") String state);
	
	String addWarningSchemeOfSZCX(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfSZQS(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfSZAI(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfDPZCX(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfDPZQS(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfDPZAI(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfFZCX(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfFZQS(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfFZAI(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfPFPCX(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfNPZS(WarningSchemeDTO warningSchemeDTO);
	String addWarningSchemeOfPFPLX(WarningSchemeDTO warningSchemeDTO);
	
	@Select("select tbltree.*,tbltree.parentmask ,gis_exist_zone.p_code as code,gis_exist_zone.name,gis_exist_zone.rank,gis_exist_zone.smid from tbltree left join gis_exist_zone on  gis_exist_zone.p_code=tbltree.foreignkey \r\n" + 
			" where (seq & ~((1::int8 << (62 - #{parentMask}-#{mask}))-1)) = #{seq} and tbltree.type = #{type} order by tbltree.seq")
	List<TreeVO> queryTree(@Param("seq") long seq, @Param("type") int type, @Param("mask") int mask, @Param("parentMask") int parentMask);
}
