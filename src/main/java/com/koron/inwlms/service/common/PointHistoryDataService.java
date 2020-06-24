package com.koron.inwlms.service.common;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.common.MinMonitorPoint;
import com.koron.inwlms.bean.VO.common.PointDataVO;
import com.koron.inwlms.bean.VO.common.PointTypeVO;

public interface PointHistoryDataService {

	List<PointDataVO> queryPointHistoryData(SessionFactory factory, String code, Date alarmDate) throws ParseException;

	List<MinMonitorPoint> queryPointHourData(SessionFactory factory, Date datatime);

	List<PointTypeVO> queryPointHistoryDataType(SessionFactory factory, String code);
	

}
