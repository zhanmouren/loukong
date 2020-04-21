package com.koron.inwlms.service.leakageControl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.WarningSchemeMapper;
import com.koron.util.Constant;

@Service
public class WarningMessageProduceServiceImpl implements WarningMessageProduceService{

	@TaskAnnotation("createWarningMessage")
	@Override
	public String createWarningMessage(SessionFactory factory) {
		//TODO 获取实时数据
		
	    //TODO 查询启用的预警方案
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningSchemeStart("0");
		
		return null;
	}
	
	 /**
	  * 监测点类型报警（实时）
	  */
	public String startPointWarning(SessionFactory factory) {
		//TODO 获取实时监测点数据
		
		//TODO 对象类型为压力/流量监测点的预警方案
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		WarningSchemeDTO warningSchemeDTO = new WarningSchemeDTO();
		warningSchemeDTO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningScheme(warningSchemeDTO);
		Boolean warningFlag = true;
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			List<AlarmRuleDTO> alarmRuleList = mapper.queryAlarmRuleByAlarmCode(warningScheme.getCode());
			//超限报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				//固定限值
				if(warningScheme.getParamType().equals(Constant.DATADICTIONARY_FIXLIMIT)) {
					for(AlarmRuleDTO alarmRule : alarmRuleList) {
						//上限
						if(alarmRule.getLimitType() == 0) {
							//TODO 对比是否超上限
							
							warningFlag = false;
							
						}
						//下限
						if(alarmRule.getLimitType() == 1) {
							//TODO 对比是否低于下限
							
							warningFlag = false;
						}
					}
				}else {
					//动态限值
					
				}
				
			}
			//离线报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_OFFLINE)) {
				//数据连续缺失时间
				//TODO 判断数据连续缺失时间过大
				warningFlag = false;
			}
			
			if(warningFlag == false) {
				//产生预警信息
				String warningCode = UUID.randomUUID().toString();
				AlarmMessageVO alarmMessageVO = new AlarmMessageVO();
				alarmMessageVO.setSchemeCode(warningScheme.getCode());
				alarmMessageVO.setAlarmSchemeName(warningScheme.getName());
				alarmMessageVO.setAlarmType(warningScheme.getAlarmType());
				alarmMessageVO.setObjectType(warningScheme.getObjectType());
				alarmMessageVO.setAlarmTime(new Date());
				alarmMessageVO.setCode(warningCode);
				//TODO 填入监测点信息
				
				//TODO 判断是否需要产生预警信息处理任务
				List<AlertNoticeSchemeVO> alertNoticeSchemeList = mapper.queryNoticeSchemeByWarningCode(warningScheme.getCode());
				if(alertNoticeSchemeList != null || alertNoticeSchemeList.size() != 0) {
					//TODO 产生预警信息处理任务
					AlarmProcessVO AlarmProcessVO = new AlarmProcessVO();
					AlarmProcessVO.setWarningCode(warningCode);
				}
				//预警信息入库
				AlarmMessageMapper alarmMessageMapper = factory.getMapper(AlarmMessageMapper.class);
				alarmMessageMapper.addWarningInf(alarmMessageVO);
				
			}
		}
		
		return null;
	}
	
	/**
	 * 漏损报警
	 */
	public String startZoneWarning(SessionFactory factory) {
		//TODO 获取分区数据
		
		//
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		WarningSchemeDTO warningSchemeDTO = new WarningSchemeDTO();
		warningSchemeDTO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningScheme(warningSchemeDTO);
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			Boolean warningFlag = true;
			//查询预警规则
			List<AlarmRuleDTO> alarmRuleList = mapper.queryAlarmRuleByAlarmCode(warningScheme.getCode());
			//趋势变化报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) {
				Double avgFlow = 0.0;
				double max = 0.0;
				double min = 0.0;
				for(AlarmRuleDTO alarmRuleDTO : alarmRuleList) {
					//对比数据的天数
					if(alarmRuleDTO.getLimitType() == null) {
						//TODO 获取与历史均值天数
						
						//TODO 获取数天内日总流量或者最小夜间流量的平均值
						
						
					}else if(alarmRuleDTO.getLimitType() == 0) {
						//上限
						max = alarmRuleDTO.getConstant();	
					}else if(alarmRuleDTO.getLimitType() == 1) {
						//下限
						min = alarmRuleDTO.getConstant();	
					}
				}
				if(avgFlow > max || avgFlow < min) {
					warningFlag = false;
				}
				
				
			}
			
			//超限报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				//TODO 固定限值
				double max = 0.0;
				double min = 0.0;
				if(warningScheme.getParamType().equals(Constant.DATADICTIONARY_FIXLIMIT)) {
					for(AlarmRuleDTO alarmRuleDTO : alarmRuleList) {
						if(alarmRuleDTO.getLimitType() == 0) {
							//上限
							max = alarmRuleDTO.getConstant();
						}
						if(alarmRuleDTO.getLimitType() == 1) {
							//下限
							min = alarmRuleDTO.getConstant();
						}
						
					}
				}else {
					//TODO 动态限值
				}
				
			}
			
			//TODO AI报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_AI)) {
				
			}
			
			if(warningFlag == false) {
				//产生预警信息
				//产生预警信息
				String warningCode = UUID.randomUUID().toString();
				AlarmMessageVO alarmMessageVO = new AlarmMessageVO();
				alarmMessageVO.setSchemeCode(warningScheme.getCode());
				alarmMessageVO.setAlarmSchemeName(warningScheme.getName());
				alarmMessageVO.setAlarmType(warningScheme.getAlarmType());
				alarmMessageVO.setObjectType(warningScheme.getObjectType());
				alarmMessageVO.setAlarmTime(new Date());
				alarmMessageVO.setCode(warningCode);
				//TODO 填入分区信息
				
				//TODO 判断是否需要产生预警信息处理任务
				List<AlertNoticeSchemeVO> alertNoticeSchemeList = mapper.queryNoticeSchemeByWarningCode(warningScheme.getCode());
				if(alertNoticeSchemeList != null || alertNoticeSchemeList.size() != 0) {
					//TODO 产生预警信息处理任务
					AlarmProcessVO AlarmProcessVO = new AlarmProcessVO();
					AlarmProcessVO.setWarningCode(warningCode);
				}
				//预警信息入库
				AlarmMessageMapper alarmMessageMapper = factory.getMapper(AlarmMessageMapper.class);
				alarmMessageMapper.addWarningInf(alarmMessageVO);
			}
			
			
		}
		
		return null;
		
	}
	
}
