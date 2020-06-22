package com.koron.inwlms.service.leakageControl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.PageInfo;
import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByType;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageByTypeVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.WarningTask;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.leakageControl.AlarmMessageMapper;
import com.koron.inwlms.mapper.sysManager.UserMapper;
import com.koron.util.Constant;

@Service
public class AlarmMessageServiceImpl implements AlarmMessageService {

	@TaskAnnotation("queryAlarmMessage")
	@Override
	public AlarmMessageReturnVO queryAlarmMessage(SessionFactory factory, WarningInfDTO warningInfDTO){
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		//TODO 查询分区编码
		if(warningInfDTO.getFirstPartion() != null && !warningInfDTO.getFirstPartion().equals("")) {
			if(warningInfDTO.getSecondPartition() != null && !warningInfDTO.getSecondPartition().equals("")) {
				 
			}
		}
		
		List<AlarmMessageVO> list = mapper.queryAlarmMessage(warningInfDTO);
		Integer totalNumber = mapper.queryAlarmMessageTotalNumber(warningInfDTO);
		AlarmMessageReturnVO alarmMessageReturnVO = new AlarmMessageReturnVO();
		alarmMessageReturnVO.setAlarmMessageList(list);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setTotalNumber(totalNumber);
		pageInfo.setPage(warningInfDTO.getPage());
		pageInfo.setSize(warningInfDTO.getPageCount()); 
		alarmMessageReturnVO.setQuery(pageInfo);
		
		return alarmMessageReturnVO;
	}
	
	@TaskAnnotation("queryWarningCodeList")
	@Override
	public List<AlarmMessageVO> queryWarningCodeList(SessionFactory factory, WarningInfDTO warningInfDTO) {
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryWarningCodeList(warningInfDTO);
		return list;
	}

	/**
	 * 统计对象类型数据
	 */
	@TaskAnnotation("queryAlarmMessageByObjectType")
	@Override
	public AlarmMessageByTypeVO queryAlarmMessageByObjectType(SessionFactory factory, WarningInfDTO warningInfDTO){
		AlarmMessageByTypeVO alarmMessageByTypeVO = new AlarmMessageByTypeVO();
		
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryAlarmMessage(warningInfDTO);
		//统计监测预警不同对象的数据
		List<AlarmMessageByType> pointList = new ArrayList<>();
		//获取监测类型
		int pfNum = 0;
		int zsNum = 0;
		for(AlarmMessageVO alarmMessageVO : list) {
			if(alarmMessageVO.getObjectType().equals(Constant.DATADICTIONARY_PFPIONT)) {
				pfNum = pfNum + 1;
			}else if(alarmMessageVO.getObjectType().equals(Constant.DATADICTIONARY_NOISEPIONT)) {
				zsNum = zsNum + 1;
			}
		}
		AlarmMessageByType alarmMessageByType1 = new AlarmMessageByType();
		alarmMessageByType1.setNumber(pfNum);
		alarmMessageByType1.setObjectType(Constant.DATADICTIONARY_PFPIONT);
		pointList.add(alarmMessageByType1);
		
		AlarmMessageByType alarmMessageByType2 = new AlarmMessageByType();
		alarmMessageByType2.setNumber(zsNum);
		alarmMessageByType2.setObjectType(Constant.DATADICTIONARY_NOISEPIONT);
		pointList.add(alarmMessageByType2);
		
		//分区类型
		List<AlarmMessageByType> zoneList = new ArrayList<>();
		int firstNum = 0;
		int secondNum = 0;
		int dpNum = 0;
		for(AlarmMessageVO alarmMessageVO : list) {
			if(alarmMessageVO.getObjectType().equals(Constant.DATADICTIONARY_DPZONE)) {
				dpNum = dpNum + 1;
			}else if(alarmMessageVO.getObjectType().equals(Constant.DATADICTIONARY_FIRSTZONE)) {
				firstNum = firstNum + 1;
			}else if(alarmMessageVO.getObjectType().equals(Constant.DATADICTIONARY_SECZONE)) {
				secondNum = secondNum + 1;
			}
		}
		AlarmMessageByType alarmMessageByType3 = new AlarmMessageByType();
		alarmMessageByType3.setNumber(dpNum);
		alarmMessageByType3.setObjectType(Constant.DATADICTIONARY_DPZONE);
		zoneList.add(alarmMessageByType3);
		
		AlarmMessageByType alarmMessageByType4 = new AlarmMessageByType();
		alarmMessageByType4.setNumber(firstNum);
		alarmMessageByType4.setObjectType(Constant.DATADICTIONARY_FIRSTZONE);
		zoneList.add(alarmMessageByType4);
		
		AlarmMessageByType alarmMessageByType5 = new AlarmMessageByType();
		alarmMessageByType5.setNumber(secondNum);
		alarmMessageByType5.setObjectType(Constant.DATADICTIONARY_SECZONE);
		zoneList.add(alarmMessageByType5);
		
		 
		alarmMessageByTypeVO.setMonitorAlarm(pointList);
		alarmMessageByTypeVO.setZoneAlarm(zoneList);
		
		return alarmMessageByTypeVO;
		
	}
	
	/**
	 * 统计报警类型数据
	 */
	@TaskAnnotation("queryAlarmMessageByAlarmType")
	@Override
	public AlarmMessageByTypeVO queryAlarmMessageByAlarmType(SessionFactory factory, WarningInfDTO warningInfDTO){
		AlarmMessageByTypeVO alarmMessageByTypeVO = new AlarmMessageByTypeVO();
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryAlarmMessage(warningInfDTO);
		
		int numPCX = 0;
		int numPLX = 0;
		int numPZS = 0;
		int numZCX = 0;
		int numZQS = 0;
		int numZAI = 0;
		
		for(AlarmMessageVO alarmMessageVO : list) {
			String objectType = alarmMessageVO.getObjectType();
			if(objectType.equals(Constant.DATADICTIONARY_PFPIONT) || objectType.equals(Constant.DATADICTIONARY_NOISEPIONT)) {
				switch(alarmMessageVO.getAlarmType()) {
				case Constant.DATADICTIONARY_OVERRUN :
					numPCX = numPCX +1;
					break;
				case Constant.DATADICTIONARY_OFFLINE : 
					numPLX = numPLX + 1;
					break;
				case Constant.DATADICTIONARY_NOISE :
					numPZS = numPZS +1;
					break;
				default :
					break;
				}
			}else {
				switch(alarmMessageVO.getAlarmType()) {
				case Constant.DATADICTIONARY_OVERRUN :
					numZCX = numZCX +1;
					break;
				case Constant.DATADICTIONARY_TRENDCHANGE :
					numZQS = numZQS + 1;
					break;
				case Constant.DATADICTIONARY_AI :
					numZAI = numZAI +1;
					break;
				default :
					break;
				}
			}
		}
		
		//整理返回数据
		List<AlarmMessageByType> pointList = new ArrayList<>();
		List<AlarmMessageByType> zoneList = new ArrayList<>();
		AlarmMessageByType aMBT = new AlarmMessageByType();
		aMBT.setNumber(numPCX);
		aMBT.setObjectType(Constant.DATADICTIONARY_OVERRUN);
		pointList.add(aMBT);
		AlarmMessageByType aMBT1 = new AlarmMessageByType();
		aMBT1.setNumber(numPLX);
		aMBT1.setObjectType(Constant.DATADICTIONARY_OFFLINE);
		pointList.add(aMBT1);
		AlarmMessageByType aMBT2 = new AlarmMessageByType();
		aMBT2.setNumber(numPZS);
		aMBT2.setObjectType(Constant.DATADICTIONARY_NOISE);
		pointList.add(aMBT2);
		AlarmMessageByType aMBT3 = new AlarmMessageByType();
		aMBT3.setNumber(numZCX);
		aMBT3.setObjectType(Constant.DATADICTIONARY_OVERRUN);
		zoneList.add(aMBT3);
		AlarmMessageByType aMBT4 = new AlarmMessageByType();
		aMBT4.setNumber(numZCX);
		aMBT4.setObjectType(Constant.DATADICTIONARY_TRENDCHANGE);
		zoneList.add(aMBT4);
		AlarmMessageByType aMBT5 = new AlarmMessageByType();
		aMBT5.setNumber(numZCX);
		aMBT5.setObjectType(Constant.DATADICTIONARY_AI);
		zoneList.add(aMBT5);
		
		alarmMessageByTypeVO.setMonitorAlarm(pointList);
		alarmMessageByTypeVO.setZoneAlarm(zoneList);
		return alarmMessageByTypeVO;
	}
	
	@TaskAnnotation("queryAlarmMessageByPointCode")
	@Override
	public List<AlarmMessageVO> queryAlarmMessageByPointCode(SessionFactory factory,String code){
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<AlarmMessageVO> list = mapper.queryAlarmMessageByPointCode(code);
		return list;
	}
	
	@TaskAnnotation("queryWarningTask")
	@Override
	public List<WarningTask> queryWarningTask(SessionFactory factory,Integer type){
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		List<WarningTask> list = mapper.queryWarningTask(type);
		return list;
	}
	
	@TaskAnnotation("addWarningTask")
	@Override
	public Integer addWarningTask(SessionFactory factory,WarningTask warningTask) {
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		Integer num = mapper.addWarningTask(warningTask);
		return num;
		
	}
	
	@TaskAnnotation("updateWarningTask")
	@Override
	public Integer updateWarningTask(SessionFactory factory,Integer state,Date updateTime) {
		AlarmMessageMapper mapper = factory.getMapper(AlarmMessageMapper.class);
		Integer num = mapper.updateWarningTask(state, updateTime);
		return num;
	}
	
}
