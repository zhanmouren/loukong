package com.koron.inwlms.service.common.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.MinMonitorPoint;
import com.koron.inwlms.bean.DTO.common.PointParamDTO;
import com.koron.inwlms.bean.VO.common.GdhRaw;
import com.koron.inwlms.bean.VO.common.GisScadaStation;
import com.koron.inwlms.bean.VO.common.PointDataVO;
import com.koron.inwlms.bean.VO.common.PointSensor;
import com.koron.inwlms.bean.VO.common.PointTypeVO;
import com.koron.inwlms.mapper.common.PointHistoryDataMapper;
import com.koron.inwlms.service.common.PointHistoryDataService;
import com.koron.inwlms.util.TimeUtil;
@Service
public class PointHistoryDataServiceImpl implements PointHistoryDataService {

	@TaskAnnotation("queryPointHistoryData")
	@Override
	public List<PointDataVO> queryPointHistoryData(SessionFactory factory,String code,Date alarmDate,String stationCode) throws ParseException {
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date alarmDate = format.parse(alarmTime);
		Date nowDate = new Date();
		Date endDate = TimeUtil.addDay(alarmDate, 3);
		//int start = 0;
		int end = (int)endDate.getTime();
		int nowTime = (int)nowDate.getTime();
		//判断前后7天范围是否超过当前日期
		Date startDate = new Date();
		if(nowTime < end) {
			startDate = TimeUtil.addDay(nowDate, -7);
			endDate = nowDate;
			end = nowTime;
			//start = (int)startDate.getTime();
		}else {
			startDate = TimeUtil.addDay(alarmDate, -3);
			//start = (int)startDate.getTime();
		}
		PointHistoryDataMapper mapper = factory.getMapper(PointHistoryDataMapper.class);
		
		//查询监测点小时表
		List<PointDataVO> pointDataList = new ArrayList<>();
		List<MinMonitorPoint> minMonitorPointList = mapper.queryPointHourDataByDay(code, startDate, endDate, stationCode);
		for(MinMonitorPoint minMonitorPoint : minMonitorPointList) {
			PointDataVO pointDataVO = new PointDataVO();
			pointDataVO.setValue(minMonitorPoint.getValue().doubleValue());
			pointDataVO.setTime(minMonitorPoint.getAnalysisDate());
			pointDataList.add(pointDataVO);
		}
		
//		List<PointSensor> pointTypeList = mapper.queryPointType(code);
//		for(PointSensor pointSensor : pointTypeList) {
//			PointTypeVO pointTypeVO = new PointTypeVO();
//			String indexCode = "";
//			if(pointSensor.getStype().equals("Q4") && !pointSensor.getSname().equals("反向瞬时")) {
//				indexCode = "MOHFDF";
//			}else if(pointSensor.getStype().equals("Q4") && pointSensor.getSname().equals("反向瞬时")) {
//				indexCode = "MOHRF";
//			}
//			pointTypeVO.setStype(indexCode);
//			pointTypeVO.setSname(pointSensor.getName());
//			List<PointDataVO> pointDataList = new ArrayList<>();
//			
//			//查询监测点小时表
//			List<MinMonitorPoint> minMonitorPointList = mapper.queryPointHourDataByDay(indexCode, startDate, endDate);
//			for(MinMonitorPoint minMonitorPoint : minMonitorPointList) {
//				PointDataVO pointDataVO = new PointDataVO();
//				pointDataVO.setValue(minMonitorPoint.getValue().doubleValue());
//				pointDataVO.setTime(minMonitorPoint.getAnalysisDate());
//				pointDataList.add(pointDataVO);
//			}
//			pointTypeVO.setPointDataList(pointDataList);
//			pointTypeVOList.add(pointTypeVO);
			
//			List<GdhRaw> gdhRawList = mapper.queryPointHistoryData(pointSensor.getCode(), start, end);
//			for(GdhRaw gdhRaw : gdhRawList) {
//				PointDataVO pointDataVO = new PointDataVO();
//				pointDataVO.setValue(Double.valueOf(gdhRaw.getValue()));
//				long time = gdhRaw.getDatetime().longValue();
//				Date timeDate = new Date(time);
//				pointDataVO.setTime(timeDate);
//				pointDataList.add(pointDataVO);
//			}
//			pointTypeVO.setPointDataList(pointDataList);
//			
		
		
		return pointDataList;
	}
	
	@TaskAnnotation("queryPointHistoryDataType")
	@Override
	public List<PointTypeVO> queryPointHistoryDataType(SessionFactory factory,String code) {
		PointHistoryDataMapper mapper = factory.getMapper(PointHistoryDataMapper.class);
		List<PointTypeVO> pointTypeVOList = new ArrayList<>();
		List<PointSensor> pointTypeList = mapper.queryPointType(code);
		boolean flag = false;
		for(PointSensor pointSensor : pointTypeList) {
			PointTypeVO pointTypeVO = new PointTypeVO();
			String indexCode = "";
			if(pointSensor.getStype().equals("Q4") && !pointSensor.getSname().equals("反向瞬时")) {
				indexCode = "MOHFDF";
			}else if(pointSensor.getStype().equals("Q4") && pointSensor.getSname().equals("反向瞬时")) {
				indexCode = "MOHRF";
			}else {
				continue;
			}
			if(pointSensor.getStype().equals("Q4")) {
				flag = true;
			}
			pointTypeVO.setSname(pointSensor.getName());
			pointTypeVO.setIndexCode(indexCode);
			pointTypeVOList.add(pointTypeVO);
		}
		if(flag) {
			PointTypeVO pointTypeVO = new PointTypeVO();
			String indexCode = "MOHFLOW";
			pointTypeVO.setSname("流量");
			pointTypeVO.setIndexCode(indexCode);
			pointTypeVOList.add(pointTypeVO);
			
			PointTypeVO pointTypeVO1 = new PointTypeVO();
			String indexCode1 = "MOHFV";
			pointTypeVO1.setSname("正向流速");
			pointTypeVO1.setIndexCode(indexCode1);
			pointTypeVOList.add(pointTypeVO1);
			
			PointTypeVO pointTypeVO2 = new PointTypeVO();
			String indexCode2 = "MOHRV";
			pointTypeVO2.setSname("反向流速");
			pointTypeVO2.setIndexCode(indexCode2);
			pointTypeVOList.add(pointTypeVO2);
		}
		
		return pointTypeVOList;
	}
	
	@TaskAnnotation("queryPointHourData")
	@Override
	public List<MinMonitorPoint> queryPointHourData(SessionFactory factory,Date datatime){
		PointHistoryDataMapper mapper = factory.getMapper(PointHistoryDataMapper.class);
		List<MinMonitorPoint> list = mapper.queryPointHourData(datatime);
		return list;
		
	}
	
	@TaskAnnotation("queryPointMessageByName")
	@Override
	public List<GisScadaStation> queryPointMessageByName(SessionFactory factory,PointParamDTO pointParamDTO){
		PointHistoryDataMapper mapper = factory.getMapper(PointHistoryDataMapper.class);
		List<GisScadaStation> list = mapper.queryPointMessageByName(pointParamDTO);
		return list;
		
	}
	
	
}
