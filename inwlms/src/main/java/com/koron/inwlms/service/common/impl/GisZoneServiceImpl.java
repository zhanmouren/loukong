package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.service.common.GisZoneService;
import com.koron.util.Constant;

@Service
public class GisZoneServiceImpl implements GisZoneService {

	@TaskAnnotation("queryZoneNosByRank")
	@Override
	public List<ZoneInfo> queryZoneNosByRank(SessionFactory factory, Integer zoneRank) {
		List<ZoneInfo> list =  new ArrayList<>();
		ZoneInfo zoneInfo = new ZoneInfo();
		zoneInfo.setZoneNo("FL01");
		zoneInfo.setZoneName("FL01一级分区");
		list.add(zoneInfo);
		ZoneInfo zoneInfo1 = new ZoneInfo();
		zoneInfo.setZoneNo("FL02");
		zoneInfo.setZoneName("FL02一级分区");
		list.add(zoneInfo);
		list.add(zoneInfo1);
		return list;
	}

	@TaskAnnotation("querySubZoneNos")
	@Override
	public List<ZoneInfo> querySubZoneNos(SessionFactory factory, String zoneNo) {
		List<ZoneInfo> list =  new ArrayList<>();
		if(("FL01").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("SL01001");
			zoneInfo.setZoneName("SL01001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("SL01002");
			zoneInfo1.setZoneName("SL01002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}else if(("FL02").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("SL02001");
			zoneInfo.setZoneName("SL02001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("SL02002");
			zoneInfo1.setZoneName("SL02002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}else if(("SL01001").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("DM01001001");
			zoneInfo.setZoneName("DM01001001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("DM01001002");
			zoneInfo1.setZoneName("DM01001002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}else if(("SL01002").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("DM01002001");
			zoneInfo.setZoneName("DM01002001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("DM01002002");
			zoneInfo1.setZoneName("DM01002002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}else if(("SL02001").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("DM02001001");
			zoneInfo.setZoneName("DM02001001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("DM02001002");
			zoneInfo1.setZoneName("DM02001002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}else if(("SL02002").equals(zoneNo)) {
			ZoneInfo zoneInfo = new ZoneInfo();
			zoneInfo.setZoneNo("DM02002001");
			zoneInfo.setZoneName("DM02002001二级分区");
			list.add(zoneInfo);
			ZoneInfo zoneInfo1 = new ZoneInfo();
			zoneInfo1.setZoneNo("DM02002002");
			zoneInfo1.setZoneName("DM02002002二级分区");
			list.add(zoneInfo);
			list.add(zoneInfo1);
		}
		return list;
	}

	@TaskAnnotation("queryMeterByZoneNo")
	@Override
	public List<MeterInfo> queryMeterByZoneNo(SessionFactory factory, String zoneNo) {
		List<MeterInfo> lists = new ArrayList<>();
		MeterInfo meterInfo = new MeterInfo();
		meterInfo.setAccName("xxx公司");
		meterInfo.setUseType(Constant.USER_TYPE_PP);
		meterInfo.setMeterNo("C0210280000021");
		meterInfo.setMeterDn(100);
		meterInfo.setMeterType(Constant.FS_METER);
		MeterInfo meterInfo1 = new MeterInfo();
		meterInfo1.setAccName("xxx银行");
		meterInfo1.setUseType(Constant.USER_TYPE_PP);
		meterInfo1.setMeterNo("C0210280000022");
		meterInfo1.setMeterDn(100);
		meterInfo1.setMeterType(Constant.NOR_METER);
		MeterInfo meterInfo2 = new MeterInfo();
		meterInfo2.setAccName("张某某");
		meterInfo2.setUseType(Constant.USER_TYPE_PP);
		meterInfo2.setMeterNo("C0210280000167");
		meterInfo2.setMeterDn(40);
		meterInfo2.setMeterType(Constant.NOR_METER);
		MeterInfo meterInfo3 = new MeterInfo();
		meterInfo3.setAccName("科荣股份有限公司");
		meterInfo3.setUseType(Constant.USER_TYPE_SP);
		meterInfo3.setMeterNo("C0210280000169");
		meterInfo3.setMeterDn(50);
		meterInfo3.setMeterType(Constant.FS_METER);
		MeterInfo meterInfo4 = new MeterInfo();
		meterInfo4.setAccName("陈某某");
		meterInfo4.setUseType(Constant.USER_TYPE_PP);
		meterInfo4.setMeterNo("C0210280000188");
		meterInfo4.setMeterDn(50);
		meterInfo4.setMeterType(Constant.NOR_METER);
		MeterInfo meterInfo5 = new MeterInfo();
		meterInfo4.setAccName("xx洗浴中心");
		meterInfo4.setUseType(Constant.USER_TYPE_PP);
		meterInfo5.setMeterNo("C0210280000172");
		meterInfo5.setMeterDn(20);
		meterInfo5.setMeterType(Constant.NOR_METER);
		lists.add(meterInfo);
		lists.add(meterInfo1);
		lists.add(meterInfo2);
		lists.add(meterInfo3);
		lists.add(meterInfo4);
		lists.add(meterInfo5);
		return lists;
	}

	@TaskAnnotation("queryZoneNameByNo")
	@Override
	public String queryZoneNameByNo(SessionFactory factory, String zoneNo) {
		return "A001一级分区";
	}
	
}
