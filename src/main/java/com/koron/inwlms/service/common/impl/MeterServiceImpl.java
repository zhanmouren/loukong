package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfoLossData;
import com.koron.inwlms.bean.VO.apparentLoss.MeterUserTimeVO;
import com.koron.inwlms.service.common.MeterService;

@Service
public class MeterServiceImpl implements MeterService {

	

	@TaskAnnotation("queryMeterUserTimeInfo")
	@Override
	public List<MeterUserTimeVO> queryMeterUserTimeInfo(SessionFactory factory, List<MeterInfo> lists) {
		List<MeterUserTimeVO> list = new ArrayList<>();
		MeterUserTimeVO mutVO = new MeterUserTimeVO();
		mutVO.setMeterDn(15);
		mutVO.setUserYear(4.0);
		mutVO.setMeterNo("10000021");
		list.add(mutVO);
		MeterUserTimeVO mutVO1 = new MeterUserTimeVO();
		mutVO1.setMeterDn(40);
		mutVO1.setUserYear(3.0);
		mutVO1.setMeterNo("10000025");
		list.add(mutVO1);
		MeterUserTimeVO mutVO2 = new MeterUserTimeVO();
		mutVO2.setMeterDn(300);
		mutVO2.setUserYear(8.0);
		mutVO2.setMeterNo("10000026");
		list.add(mutVO2);
		MeterUserTimeVO mutVO3 = new MeterUserTimeVO();
		mutVO3.setMeterDn(100);
		mutVO3.setUserYear(7.0);
		mutVO3.setMeterNo("10000028");
		list.add(mutVO3);
		MeterUserTimeVO mutVO4 = new MeterUserTimeVO();
		mutVO4.setMeterDn(25);
		mutVO4.setUserYear(13.1);
		mutVO4.setMeterNo("10000029");
		list.add(mutVO4);
		MeterUserTimeVO mutVO5 = new MeterUserTimeVO();
		mutVO5.setMeterDn(100);
		mutVO5.setUserYear(10.0);
		mutVO5.setMeterNo("10000042");
		list.add(mutVO5);
		MeterUserTimeVO mutVO6 = new MeterUserTimeVO();
		mutVO6.setMeterDn(100);
		mutVO6.setUserYear(5.0);
		mutVO6.setMeterNo("10000125");
		list.add(mutVO6);
		MeterUserTimeVO mutVO7 = new MeterUserTimeVO();
		mutVO7.setMeterDn(300);
		mutVO7.setUserYear(2.0);
		mutVO7.setMeterNo("10000053");
		list.add(mutVO7);
		MeterUserTimeVO mutVO8 = new MeterUserTimeVO();
		mutVO8.setMeterDn(100);
		mutVO8.setUserYear(9.0);
		mutVO8.setMeterNo("10000020");
		list.add(mutVO8);
		return list;
	}

	@TaskAnnotation("queryMeterInfoLossData")
	@Override
	public MeterInfoLossData queryMeterInfoLossData(SessionFactory factory, List<MeterInfo> lists) {
		MeterInfoLossData meterInfoLossVO = new MeterInfoLossData();
		meterInfoLossVO.setLossDnNum(1);
		meterInfoLossVO.setLossMTypeNum(7);
		meterInfoLossVO.setLossUTypeNum(5);
		return meterInfoLossVO;
	}

}
