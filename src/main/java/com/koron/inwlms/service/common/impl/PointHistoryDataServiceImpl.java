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
import com.koron.inwlms.bean.VO.common.GdhRaw;
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
	public List<PointTypeVO> queryPointHistoryData(SessionFactory factory,String code,Date alarmDate) throws ParseException {
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date alarmDate = format.parse(alarmTime);
		Date nowDate = new Date();
		Date endDate = TimeUtil.addDay(alarmDate, 3);
		int start = 0;
		int end = (int)endDate.getTime();
		int nowTime = (int)nowDate.getTime();
		//判断前后7天范围是否超过当前日期
		if(nowTime < end) {
			Date startDate = TimeUtil.addDay(nowDate, -7);
			end = nowTime;
			start = (int)startDate.getTime();
		}else {
			Date startDate = TimeUtil.addDay(alarmDate, -3);
			start = (int)startDate.getTime();
		}
		
		List<PointTypeVO> pointTypeVOList = new ArrayList<>();
		//根据监测点编码获取采集通道
		PointHistoryDataMapper mapper = factory.getMapper(PointHistoryDataMapper.class);
		List<PointSensor> pointTypeList = mapper.queryPointType(code);
		for(PointSensor pointSensor : pointTypeList) {
			PointTypeVO pointTypeVO = new PointTypeVO();
			pointTypeVO.setStype(pointSensor.getStype());
			pointTypeVO.setSname(pointSensor.getName());
			List<PointDataVO> pointDataList = new ArrayList<>();
			List<GdhRaw> gdhRawList = mapper.queryPointHistoryData(code, start, end);
			for(GdhRaw gdhRaw : gdhRawList) {
				PointDataVO pointDataVO = new PointDataVO();
				pointDataVO.setValue(Double.valueOf(gdhRaw.getValue()));
				long time = gdhRaw.getDatetime().longValue();
				Date timeDate = new Date(time);
				pointDataVO.setTime(timeDate);
				pointDataList.add(pointDataVO);
			}
			pointTypeVO.setPointDataList(pointDataList);
			pointTypeVOList.add(pointTypeVO);
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
	
	
}
