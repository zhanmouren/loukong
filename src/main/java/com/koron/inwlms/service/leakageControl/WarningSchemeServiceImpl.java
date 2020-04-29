package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.WarningSchemeMapper;
import com.koron.util.Constant;

@Service
public class WarningSchemeServiceImpl implements WarningSchemeService {
	
	@TaskAnnotation("queryWarningScheme")
	@Override
	public List<WarningSchemeVO> queryWarningScheme(SessionFactory factory,WarningSchemeDTO warningSchemeDTO){
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		List<WarningSchemeVO> list = mapper.queryWarningScheme(warningSchemeDTO);
		return list;
	}
	
	@TaskAnnotation("addWarningScheme")
	@Override
	public String addWarningScheme(SessionFactory factory,WarningSchemeDTO warningSchemeDTO,List<AlarmRuleDTO> alarmRuleDTOList) {
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		String num = "";
		if(warningSchemeDTO.getObjectType().equals(Constant.DATADICTIONARY_SECZONE)) {
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) {
				num = mapper.addWarningSchemeOfSZQS(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				num = mapper.addWarningSchemeOfSZCX(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_AI)) {
				num = mapper.addWarningSchemeOfSZAI(warningSchemeDTO);
			}
		}else if(warningSchemeDTO.getObjectType().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) {
				num = mapper.addWarningSchemeOfFZQS(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				num = mapper.addWarningSchemeOfFZCX(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_AI)) {
				num = mapper.addWarningSchemeOfFZAI(warningSchemeDTO);
			}
		}else if(warningSchemeDTO.getObjectType().equals(Constant.DATADICTIONARY_DPZONE)) {
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) {
				num = mapper.addWarningSchemeOfDPZQS(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				num = mapper.addWarningSchemeOfDPZCX(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_AI)) {
				num = mapper.addWarningSchemeOfDPZAI(warningSchemeDTO);
			}
		}else if(warningSchemeDTO.getObjectType().equals(Constant.DATADICTIONARY_PFPIONT)) {
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				num = mapper.addWarningSchemeOfPFPCX(warningSchemeDTO);
			}
			if(warningSchemeDTO.getAlarmType().equals(Constant.DATADICTIONARY_OFFLINE)) {
				num = mapper.addWarningSchemeOfPFPLX(warningSchemeDTO);
			}
		}else if(warningSchemeDTO.getObjectType().equals(Constant.DATADICTIONARY_NOISEPIONT)) {
			num = mapper.addWarningSchemeOfNPZS(warningSchemeDTO);
		}
		
		if(num != null || num.equals("")) {
			for(AlarmRuleDTO alarmRuleDTO : alarmRuleDTOList) {
				alarmRuleDTO.setSchemeCode(num);
				mapper.addAlarmRule(alarmRuleDTO);
			}
			
		}
		return num;
	}
	
	@TaskAnnotation("updateWarningScheme")
	@Override
	public Integer updateWarningScheme(SessionFactory factory,WarningSchemeDTO warningSchemeDTO) {
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		Integer num = mapper.updateWarningScheme(warningSchemeDTO);
		return num;
	}
	
	@TaskAnnotation("deleteWarningScheme")
	@Override
	public Integer deleteWarningSchenme(SessionFactory factory,String code) {
		
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		//删除规则表数据
		mapper.deleteAlarmRuleByAlarmCode(code);
		Integer num = mapper.deleteWarningScheme(code);
		return num; 
	}
	
	@TaskAnnotation("queryAlertNoticeSchemeByWarningId")
	@Override
	public List<AlertNoticeScheme> queryAlertNoticeSchemeByWarningId(SessionFactory factory, String code){
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		List<AlertNoticeScheme> list = mapper.queryAlertNoticeSchemeByWarningId(code);
		return list;
		
	}
	
	@TaskAnnotation("queryAlarmRuleByAlarmCode")
	@Override
	public List<AlarmRuleDTO> queryAlarmRuleByAlarmCode(SessionFactory factory, String alarmCode){
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		List<AlarmRuleDTO> list = mapper.queryAlarmRuleByAlarmCode(alarmCode);
		return list;
	}
	
	@TaskAnnotation("addAlarmRule")
	@Override
	public Integer addAlarmRule(SessionFactory factory,AlarmRuleDTO alarmRuleDTO) {
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		Integer num = mapper.addAlarmRule(alarmRuleDTO);
		return num;
	}
	
	@TaskAnnotation("deleteAlarmRuleByAlarmCode")
	@Override
	public Integer deleteAlarmRuleByAlarmCode(SessionFactory factory,String alarmCode) {
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		Integer num = mapper.deleteAlarmRuleByAlarmCode(alarmCode);
		return num;
	}
	
	@TaskAnnotation("updateAlarmRule")
	@Override
	public Integer updateAlarmRule(SessionFactory factory,AlarmRuleDTO alarmRuleDTO) {
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		Integer num = mapper.updateAlarmRule(alarmRuleDTO);
		return num;
	}
	
	@TaskAnnotation("queryNoticeSchemeByWarningCode")
	@Override
	public List<AlertNoticeSchemeVO> queryNoticeSchemeByWarningCode(SessionFactory factory,String code) {
		WarningSchemeMapper warnMapper = factory.getMapper(WarningSchemeMapper.class);
		List<AlertNoticeSchemeVO> alertNoticeSchemeList = warnMapper.queryNoticeSchemeByWarningCode(code);
		
		//TODO 通过角色ID查询用户
		
		//TODO 通过用户ID查询用户信息
		
		return alertNoticeSchemeList;
	}

}
