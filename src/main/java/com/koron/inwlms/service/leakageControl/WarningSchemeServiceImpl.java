package com.koron.inwlms.service.leakageControl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeMessage;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeScheme;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertSchemeListVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.WarningSchemeMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
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
	
	/**
	 * 查询预警方案列表数据
	 */
	@TaskAnnotation("queryWarningSchemeList")
	@Override
	public List<AlertSchemeListVO> queryWarningSchemeList(SessionFactory factory,WarningSchemeDTO warningSchemeDTO){
		List<AlertSchemeListVO> alertSchemeListVO = new ArrayList<>();
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningScheme(warningSchemeDTO);
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			AlertSchemeListVO alertSchemeVO = new AlertSchemeListVO();
			//拼接通知方式字段
			String noticeType = "";
			List<AlertNoticeScheme> alertNoticeSchemeList = mapper.queryAlertNoticeSchemeByWarningId(warningScheme.getCode());
			for(AlertNoticeScheme alertNoticeScheme : alertNoticeSchemeList) {
				//TODO 通过通知方式的key查询其值
				
				//拼装返回格式
				noticeType = noticeType + alertNoticeScheme.getType() + "/";
			}
			
			alertSchemeVO.setNoticeType(noticeType);
			alertSchemeVO.setAlarmType(warningScheme.getAlarmType());
			alertSchemeVO.setName(warningScheme.getName());
			alertSchemeVO.setAlarmIndex(warningScheme.getAlarmIndex());
			alertSchemeVO.setId(warningScheme.getId());
			alertSchemeVO.setCode(warningScheme.getCode());
			alertSchemeVO.setObjectType(warningScheme.getObjectType());
			alertSchemeListVO.add(alertSchemeVO);
		}
		return alertSchemeListVO;
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
		
		if(num != null || !num.equals("")) {
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
	public List<AlertNoticeMessage> queryNoticeSchemeByWarningCode(SessionFactory factory,String code) {
		List<AlertNoticeMessage> alertNoticeMessageList = new ArrayList<>();
		WarningSchemeMapper warnMapper = factory.getMapper(WarningSchemeMapper.class);
		List<AlertNoticeSchemeVO> alertNoticeSchemeList = warnMapper.queryNoticeSchemeByWarningCode(code);
		
		for(AlertNoticeSchemeVO alertNoticeSchemeVO : alertNoticeSchemeList) {
			String userCode = alertNoticeSchemeVO.getRoleCode();
			//查询角色下的用户信息
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleCode(userCode);
			UserMapper userMapper = factory.getMapper(UserMapper.class);
			List<UserVO> userList = userMapper.queryUserByRoleCode(roleDTO);
			for(UserVO userVO : userList) {
				AlertNoticeMessage alertNoticeMessage = new AlertNoticeMessage();
				if(alertNoticeSchemeVO.getType().equals("邮件")) {
					alertNoticeMessage.setContact(userVO.getEmail());
					alertNoticeMessage.setName(userVO.getName());
					alertNoticeMessage.setType(alertNoticeSchemeVO.getType());
					//TODO 状态
				}else if(alertNoticeSchemeVO.getType().equals("短信")) {
					alertNoticeMessage.setContact(userVO.getPhone());
					alertNoticeMessage.setName(userVO.getName());
					alertNoticeMessage.setType(alertNoticeSchemeVO.getType());
					//TODO 状态
				}else if(alertNoticeSchemeVO.getType().equals("系统")) {
					alertNoticeMessage.setName(userVO.getName());
					alertNoticeMessage.setType(alertNoticeSchemeVO.getType());
					//TODO 状态
				}else if(alertNoticeSchemeVO.getType().equals("代办")) {
					alertNoticeMessage.setName(userVO.getName());
					alertNoticeMessage.setType(alertNoticeSchemeVO.getType());
					//TODO 状态
				}
				alertNoticeMessageList.add(alertNoticeMessage);
			}
					
		}
		
		return alertNoticeMessageList;
	}

}
