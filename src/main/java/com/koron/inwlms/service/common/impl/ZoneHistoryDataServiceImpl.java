package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.koron.inwlms.bean.DTO.common.IndicatorDTO;
import com.koron.inwlms.bean.DTO.leakageControl.ZoneDayData;
import com.koron.inwlms.bean.VO.common.IndicatorVO;
import com.koron.inwlms.mapper.common.IndicatorMapper;
import com.koron.inwlms.service.common.ZoneHistoryDataService;
import com.koron.util.Constant;

public class ZoneHistoryDataServiceImpl implements ZoneHistoryDataService {
	
	@TaskAnnotation("queryZoneTaskData")
	@Override
	public List<ZoneDayData> queryZoneTaskData(SessionFactory factory,IndicatorDTO indicatorDTO) {
		IndicatorMapper indicMapper = factory.getMapper(IndicatorMapper.class);
		List<IndicatorVO> dataList = indicMapper.queryWBBaseIndicData(indicatorDTO);
		List<ZoneDayData> zoneDayDataList = new ArrayList<>();
		for(IndicatorVO indicatorVO : dataList) {
			ZoneDayData zoneDayData = new ZoneDayData();
			zoneDayData.setAllFlow(indicatorVO.getValue());
			zoneDayData.setZoneCode(indicatorVO.getZoneNo());
			if(indicatorVO.getCode().equals("FLDFWSSITDF")) {
				zoneDayData.setZoneIndex("1");
			}else if(indicatorVO.getCode().equals("DMDFWSSITDF")) {
				zoneDayData.setZoneIndex("2");
			}
			zoneDayDataList.add(zoneDayData);
		}
		List<IndicatorVO> dataList1 = indicMapper.queryZoneLossIndicData(indicatorDTO);
		for(IndicatorVO indicatorVO : dataList1) {
			ZoneDayData zoneDayData = new ZoneDayData();
			zoneDayData.setAllFlow(indicatorVO.getValue());
			zoneDayData.setZoneCode(indicatorVO.getZoneNo());
			if(indicatorVO.getCode().equals("FLDFWSSITDF")) {
				zoneDayData.setZoneIndex("1");
			}else if(indicatorVO.getCode().equals("DMDFWSSITDF")) {
				zoneDayData.setZoneIndex("2");
			}
			zoneDayDataList.add(zoneDayData);
		}
		
		return zoneDayDataList;
	}

}
