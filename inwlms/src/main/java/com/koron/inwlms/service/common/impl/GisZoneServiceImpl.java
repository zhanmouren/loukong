package com.koron.inwlms.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.service.common.GisZoneService;

@Service
public class GisZoneServiceImpl implements GisZoneService {

	@TaskAnnotation("queryZoneNosByRank")
	@Override
	public List<String> queryZoneNosByRank(SessionFactory factory, Integer zoneRank) {
		List<String> list =  new ArrayList<>();
		list.add("FL01");
		list.add("FL02");
		return list;
	}

	@TaskAnnotation("querySubZoneNos")
	@Override
	public List<String> querySubZoneNos(SessionFactory factory, String zoneNo) {
		List<String> list =  new ArrayList<>();
		if(("FL01").equals(zoneNo)) {
			list.add("SL01001");
			list.add("SL01002");
		}else if(("FL02").equals(zoneNo)) {
			list.add("SL02001");
			list.add("SL02002");
		}else if(("SL01001").equals(zoneNo)) {
			list.add("DM01001001");
			list.add("DM01001002");
		}else if(("SL01002").equals(zoneNo)) {
			list.add("DM01002001");
			list.add("DM01002002");
		}else if(("SL02001").equals(zoneNo)) {
			list.add("DM02001001");
			list.add("DM02001002");
		}else if(("SL02002").equals(zoneNo)) {
			list.add("DM02002001");
			list.add("DM02002002");
		}
		return list;
	}

}
