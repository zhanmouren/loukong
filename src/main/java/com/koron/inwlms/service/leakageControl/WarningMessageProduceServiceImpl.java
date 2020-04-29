package com.koron.inwlms.service.leakageControl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.Indicator;
import com.koron.inwlms.bean.DTO.common.MinMonitorPoint;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmRuleDTO;
import com.koron.inwlms.bean.DTO.leakageControl.PolicySchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.RecommendStrategy;
import com.koron.inwlms.bean.DTO.leakageControl.WarningSchemeDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ZoneDayData;
import com.koron.inwlms.bean.DTO.sysManager.RoleDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.AlertNoticeSchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.Policy;
import com.koron.inwlms.bean.VO.leakageControl.PolicySchemeVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningSchemeVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.common.CommonMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.mapper.leakageControl.PolicyMapper;
import com.koron.inwlms.mapper.leakageControl.WarningSchemeMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.inwlms.util.sendMail.SendMail;
import com.koron.inwlms.util.sendNote.SendNoteUtil;
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
	public String startPointWarning(SessionFactory factory,List<MinMonitorPoint> minMonitorPointList) {
		//TODO 获取测点数据的精度
		CommonMapper commonMapper = factory.getMapper(CommonMapper.class);
		Indicator forwardSpeed = commonMapper.queryIndicatorByCode(Constant.INDICATOR_FORWARDSPEED);
		BigInteger precision = new BigInteger(forwardSpeed.getPrecision().toString());
		
		//TODO 对象类型为压力/流量监测点的预警方案
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		WarningSchemeDTO warningSchemeDTO = new WarningSchemeDTO();
		warningSchemeDTO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningScheme(warningSchemeDTO);
		Boolean warningFlag = true;
		String dataCode = "";
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			
			//判断数据标志
			if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_FORWARDSPEED)) {
				dataCode = Constant.INDICATOR_FORWARDSPEED;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_REVERSESPEED)) {
				dataCode = Constant.INDICATOR_REVERSESPEED;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_PRESSONE)) {
				dataCode = Constant.INDICATOR_PRESSONE;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_PRESSTWO)) {
				dataCode = Constant.INDICATOR_PRESSTWO;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_PRESSBAD)) {
				dataCode = Constant.INDICATOR_PRESSBAD;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_STATUS)) {
				dataCode = Constant.INDICATOR_STATUS;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_DAYFLOW)) {
				dataCode = Constant.INDICATOR_DAYFLOW;
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_MINNIGFLOW)) {
				dataCode = Constant.INDICATOR_MINNIGFLOW;
			}
			
			double max = 0.0;
			double min = 0.0;	
			List<AlarmRuleDTO> alarmRuleList = mapper.queryAlarmRuleByAlarmCode(warningScheme.getCode());
			//超限报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_OVERRUN)) {
				//固定限值
				if(warningScheme.getParamType().equals(Constant.DATADICTIONARY_FIXLIMIT)) {
					for(AlarmRuleDTO alarmRule : alarmRuleList) {
						//上限
						if(alarmRule.getLimitType() == 0) {
							max = alarmRule.getConstant();		
						}
						//下限
						if(alarmRule.getLimitType() == 1) {
							min = alarmRule.getConstant();
						}
					}
					//判断数据是否报警
					for(MinMonitorPoint minMonitorPoint : minMonitorPointList) {
						if(minMonitorPoint.getCode().equals(dataCode)) {
							double monitorPointData = minMonitorPoint.getValue().divide(precision).doubleValue();
							if(monitorPointData < min || monitorPointData > max) {
								//产生预警信息
								String warningCode = "PCX" + new Date().getTime();
								AlarmMessageVO alarmMessageVO = new AlarmMessageVO();
								alarmMessageVO.setSchemeCode(warningScheme.getCode());
								alarmMessageVO.setAlarmSchemeName(warningScheme.getName());
								alarmMessageVO.setAlarmType(warningScheme.getAlarmType());
								alarmMessageVO.setObjectType(warningScheme.getObjectType());
								alarmMessageVO.setAlarmTime(new Date());
								alarmMessageVO.setCode(warningCode);
								//产生主报警编码
								String pointCode = warningScheme.getCode() + minMonitorPoint.getCode(); 
								alarmMessageVO.setPointCode(pointCode);
								//TODO 填入监测点信息(缺失分区编码)
								alarmMessageVO.setObjectCode(minMonitorPoint.getStationCode());
								//TODO 查询监测点的检测用途（若是表不加这个字段则无需该步骤）
								
								//预警信息入库
								AlarmMessageMapper alarmMessageMapper = factory.getMapper(AlarmMessageMapper.class);
								alarmMessageMapper.addWarningInf(alarmMessageVO);
								
								//TODO 判断是否需要产生预警信息处理任务
								List<AlertNoticeSchemeVO> alertNoticeSchemeList = mapper.queryNoticeSchemeByWarningCode(warningScheme.getCode());
								if(alertNoticeSchemeList != null || alertNoticeSchemeList.size() != 0) {
									//TODO 产生预警信息处理任务编码
									
									AlarmProcessVO alarmProcessVO = new AlarmProcessVO();
									alarmProcessVO.setWarningCode(warningCode);
									alarmProcessVO.setAlarmType(warningScheme.getAlarmType());
									alarmProcessVO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
									alarmProcessVO.setState(Constant.DATADICTIONARY_TASKSTATUSUN);
									alarmProcessVO.setLeadingCadre(alertNoticeSchemeList.get(0).getUserName());
									
									AlarmProcessMapper alarmProcessMapper = factory.getMapper(AlarmProcessMapper.class);
									alarmProcessMapper.addAlarmProcess(alarmProcessVO);
									
								}
								
							}
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
		}
		
		return null;
	}
	
	/**
	 * 漏损报警
	 */
	public String startZoneWarning(SessionFactory factory,ZoneDayData zoneDayData) {
		//获取分区数据
		double zoneFlow = 0.0;
		//
		WarningSchemeMapper mapper = factory.getMapper(WarningSchemeMapper.class);
		WarningSchemeDTO warningSchemeDTO = new WarningSchemeDTO();
		warningSchemeDTO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
		List<WarningSchemeVO> warningSchemeList = mapper.queryWarningScheme(warningSchemeDTO);
		for(WarningSchemeVO warningScheme : warningSchemeList) {
			//通过报警指标获取数据源数据
			if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_DAYFLOW)) {
				zoneFlow = zoneDayData.getAllFlow();
			}else if(warningScheme.getAlarmIndex().equals(Constant.DATADICTIONARY_MINNIGFLOW)) {
				zoneFlow = zoneDayData.getMinNigFlow();
			}
			Boolean warningFlag = true;
			//查询预警规则
			List<AlarmRuleDTO> alarmRuleList = mapper.queryAlarmRuleByAlarmCode(warningScheme.getCode());
			//趋势变化报警
			if(warningScheme.getAlarmType().equals(Constant.DATADICTIONARY_TRENDCHANGE)) {
				double increase = 0.0;
				double max = 0.0;
				double min = 0.0;
				for(AlarmRuleDTO alarmRuleDTO : alarmRuleList) {
					//对比数据的天数
					if(alarmRuleDTO.getLimitType() == null) {
						//获取与历史均值天数
						double day = alarmRuleDTO.getConstant();
						//TODO 获取数天内日总流量或者最小夜间流量的平均值
						double oldDayFlow = 2.2;
						increase = (zoneFlow - oldDayFlow)/oldDayFlow;
						increase = increase * 100;
						
					}else if(alarmRuleDTO.getLimitType() == 0) {
						//上限
						max = alarmRuleDTO.getConstant();	
					}else if(alarmRuleDTO.getLimitType() == 1) {
						//下限
						min = alarmRuleDTO.getConstant();	
					}
				}
				if(increase > max || increase < min) {
					//产生预警信息
					String warningCode = "ZQS" + new Date().getTime();
					AlarmMessageVO alarmMessageVO = new AlarmMessageVO();
					alarmMessageVO.setSchemeCode(warningScheme.getCode());
					alarmMessageVO.setAlarmSchemeName(warningScheme.getName());
					alarmMessageVO.setAlarmType(warningScheme.getAlarmType());
					alarmMessageVO.setObjectType(warningScheme.getObjectType());
					alarmMessageVO.setAlarmTime(new Date());
					alarmMessageVO.setCode(warningCode);
					String pointCode = warningScheme.getCode() + zoneDayData.getZoneCode(); 
					alarmMessageVO.setPointCode(pointCode);
					//TODO 填入分区信息
					alarmMessageVO.setAreaCode(zoneDayData.getZoneCode());
					
					//TODO 判断是否需要产生预警信息处理任务
					List<AlertNoticeSchemeVO> alertNoticeSchemeList = mapper.queryNoticeSchemeByWarningCode(warningScheme.getCode());
					if(alertNoticeSchemeList != null || alertNoticeSchemeList.size() != 0) {
						//TODO 产生预警信息处理任务
						AlarmProcessVO alarmProcessVO = new AlarmProcessVO();
						alarmProcessVO.setWarningCode(warningCode);
						alarmProcessVO.setAlarmType(Constant.DATADICTIONARY_TRENDCHANGE);
						alarmProcessVO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
						alarmProcessVO.setAlarmContent(alarmMessageVO.getContent());
						alarmProcessVO.setState(Constant.DATADICTIONARY_TASKSTATUSUN);
						alarmProcessVO.setLeadingCadre(alertNoticeSchemeList.get(0).getUserName());
						
						AlarmProcessMapper alarmProcessMapper = factory.getMapper(AlarmProcessMapper.class);
						alarmProcessMapper.addAlarmProcess(alarmProcessVO);
					}
					//预警信息入库
					AlarmMessageMapper alarmMessageMapper = factory.getMapper(AlarmMessageMapper.class);
					alarmMessageMapper.addWarningInf(alarmMessageVO);
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
					if(zoneFlow > max || zoneFlow < min) {
						//TODO 产生预警信息
						String warningCode = "ZCX" + new Date().getTime();
						AlarmMessageVO alarmMessageVO = new AlarmMessageVO();
						alarmMessageVO.setSchemeCode(warningScheme.getCode());
						alarmMessageVO.setAlarmSchemeName(warningScheme.getName());
						alarmMessageVO.setAlarmType(warningScheme.getAlarmType());
						alarmMessageVO.setObjectType(warningScheme.getObjectType());
						alarmMessageVO.setAlarmTime(new Date());
						alarmMessageVO.setCode(warningCode);
						String pointCode = warningScheme.getCode() + zoneDayData.getZoneCode(); 
						alarmMessageVO.setPointCode(pointCode);
						//TODO 填入分区信息
						alarmMessageVO.setAreaCode(zoneDayData.getZoneCode());
						
						//TODO 判断是否需要产生预警信息处理任务
						List<AlertNoticeSchemeVO> alertNoticeSchemeList = mapper.queryNoticeSchemeByWarningCode(warningScheme.getCode());
						if(alertNoticeSchemeList != null || alertNoticeSchemeList.size() != 0) {
							//TODO 产生预警信息处理任务
							AlarmProcessVO alarmProcessVO = new AlarmProcessVO();
							alarmProcessVO.setWarningCode(warningCode);
							alarmProcessVO.setAlarmType(Constant.DATADICTIONARY_TRENDCHANGE);
							alarmProcessVO.setObjectType(Constant.DATADICTIONARY_OBJECTTYPE);
							alarmProcessVO.setAlarmContent(alarmMessageVO.getContent());
							alarmProcessVO.setState(Constant.DATADICTIONARY_TASKSTATUSUN);
							alarmProcessVO.setLeadingCadre(alertNoticeSchemeList.get(0).getUserName());
							
							AlarmProcessMapper alarmProcessMapper = factory.getMapper(AlarmProcessMapper.class);
							alarmProcessMapper.addAlarmProcess(alarmProcessVO);
						}
						//预警信息入库
						AlarmMessageMapper alarmMessageMapper = factory.getMapper(AlarmMessageMapper.class);
						alarmMessageMapper.addWarningInf(alarmMessageVO);
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
				
			}
			
			
		}
		
		return null;
		
	}
	
	/**
	 * 短信邮件报警发送
	 * @param factory
	 * @param userCode
	 * @param type
	 */
	public void sendNoteAndMail(SessionFactory factory,String userCode,String type) {
		//查询角色对应的用户信息
		UserMapper userMapper = factory.getMapper(UserMapper.class);
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleCode(userCode);
		List<UserVO> userList = userMapper.queryUserByRoleCode(roleDTO);
		
		if(type.equals("短信通知")) {
			String content = "";
			
			if(userList != null && userList.size() != 0) {
				for(UserVO user : userList) {
					try {
						SendNoteUtil.sendNote(user.getPhone(), content);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(type.equals("邮件通知")) {
			String theme = "";
			String content = "";
			if(userList != null && userList.size() != 0) {
				for(UserVO user : userList) {
					try {
						SendMail.sendMail(user.getName(), theme, content, user.getEmail());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}
	
	
	public void getRecommendStrategy(SessionFactory factory,RecommendStrategy recommendStrategy, double mnf,double ali) {
		double lenghC = recommendStrategy.getLenghC();
		double lenghNC = recommendStrategy.getLenghNC();
		int pNum = recommendStrategy.getpNum();
		double age = recommendStrategy.getAge();
		double press = recommendStrategy.getPress();
		double a1 = recommendStrategy.getA1();
		double a2 = recommendStrategy.getA2();
		double a3 = recommendStrategy.getA3();
		double b = recommendStrategy.getB();
		double c = recommendStrategy.getC();
		
		//策略标志
		int stra1 = 0;
		int stra2 = 0;
		int stra3 = 0;
		int stra4 = 0;
		
		//计算最低可达最小夜间流量
		double lmnf = 0.0;
		lmnf = (a1*lenghC + a2*lenghNC + a3*pNum)*Math.pow(age, b)*Math.pow(press, c);
		
		//查询策略信息
		PolicySchemeDTO policySchemeDTO = new PolicySchemeDTO();
		policySchemeDTO.setState("0");
		PolicyMapper mapper = factory.getMapper(PolicyMapper.class);
		List<PolicySchemeVO> policySchemeList = mapper.queryPolicyScheme(policySchemeDTO);
		List<Policy> policyList = mapper.queryPolicySetting(policySchemeList.get(0).getCode());
		
		for(Policy policy : policyList) {
			//TODO 判断分区类型
			
			if(policy.getType().equals(Constant.DATADICTIONARY_FLOWCHANGE)) {
				if(ali > 1) {
					double changeNum = pNum*policy.getTabUserRatio();
					
				}
				
			}
			
		}
		
		
	}
	
	
	
}
