package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ZoneDayData;
import com.koron.inwlms.bean.VO.common.IndicatorVO;

public interface ZoneHistoryDataService {

	List<ZoneDayData> queryZoneTaskData(SessionFactory factory, IndicatorDTO indicatorDTO);

}
