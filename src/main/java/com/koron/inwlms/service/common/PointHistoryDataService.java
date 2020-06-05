package com.koron.inwlms.service.common;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.common.PointTypeVO;

public interface PointHistoryDataService {

	List<PointTypeVO> queryPointHistoryData(SessionFactory factory, String code, Date alarmDate) throws ParseException;
	

}
