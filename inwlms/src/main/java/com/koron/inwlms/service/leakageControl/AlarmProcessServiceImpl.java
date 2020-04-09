package com.koron.inwlms.service.leakageControl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.TimeAndFlowData;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.mapper.leakageControl.AlarmProcessMapper;
import com.koron.inwlms.util.TimeUtil;

@Service
public class AlarmProcessServiceImpl implements AlarmProcessService {

	@TaskAnnotation("queryAlarmProcess")
	@Override
	public List<AlarmProcessVO> queryAlarmProcess(SessionFactory factory,AlarmProcessDTO alarmProcessDTO){
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		List<AlarmProcessVO> list = mapper.queryAlarmProcess(alarmProcessDTO);
		return list;
	}
	
	@TaskAnnotation("updateAlarmProcess")
	@Override
	public Integer updateAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.updateAlarmProcess(alarmProcessVO);
		return num;
	}
	
	@TaskAnnotation("addAlarmProcess")
	@Override
	public Integer addAlarmProcess(SessionFactory factory,AlarmProcessVO alarmProcessVO) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.addAlarmProcess(alarmProcessVO);
		return num;
	}
	
	@TaskAnnotation("deleteAlarmProcess")
	@Override
	public Integer deleteAlarmProcess(SessionFactory factory,String code) {
		AlarmProcessMapper mapper = factory.getMapper(AlarmProcessMapper.class);
		Integer num = mapper.deleteAlarmProcess(code);
		return num;
	}
	
	@TaskAnnotation("queryTreatmentEffect")
	@Override
	public TreatmentEffectVO queryTreatmentEffect(SessionFactory factory,TreatmentEffectDTO treatmentEffectDTO) {
		
		Date startDate = new Date();
		Date endDate = new Date();
		Date nowDate = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startDate = format.parse(treatmentEffectDTO.getStartTime());
			endDate = format.parse(treatmentEffectDTO.getEndTime());
		}catch(Exception e) {
			
		}
		//获取当前月份
		int nowY = TimeUtil.getYears(nowDate);
		int nowM = TimeUtil.getMonth(nowDate);
		int dateNow = nowY*10 + nowM;
		
		//月漏损量统计
		//获取工单开始的月份和结束的月份
		Date startDateL = TimeUtil.addMonth(startDate, 2);
		Date endDateL = TimeUtil.addMonth(endDate, 2);

		int startYL = TimeUtil.getYears(startDateL);
		int startML = TimeUtil.getMonth(startDateL);
		int endYL = TimeUtil.getYears(endDateL);
		int endML = TimeUtil.getMonth(endDateL);
		//获取分区漏损量的开始时间
		int dateStartL = startYL*10 + startML;
		//获取分区漏损量的结束时间
		int dateEndL = endYL*10 + endML;
		
		List<TimeAndFlowData> lossFlowList = new ArrayList<>();
		//当前月份在工单结束俩个月之后
		if(dateEndL <= dateNow) {
			//月份为开始至结束连续时间，末尾添加当前月的漏损量
			while (dateStartL <= dateEndL) {
				TimeAndFlowData timeAndFlowData = new TimeAndFlowData();
				//TODO 查询分区漏损月指标表，获取月漏损数据
				
				
				//统计数据
				timeAndFlowData.setTimeNum(dateStartL);
				
				lossFlowList.add(timeAndFlowData);
				//月份+1
				startML = startML +1;
				if(startML < 13) {
					dateStartL = startYL*10 + startML;
				}else {
					dateStartL = (startYL + 1)*10 + startML - 12;
				}
				
			}
		}
		
		
		//TODO 供水量统计
		
		
		
		
		
		return null;
	}
	
	
}
