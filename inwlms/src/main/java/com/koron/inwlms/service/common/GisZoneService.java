package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

/**
 * GIS分区接口（临时）
 * @author csh
 * @Date 2020/03/25
 *
 */
public interface GisZoneService {

	List<String> queryZoneNosByRank(SessionFactory factory,Integer zoneRank);
	
	List<String> querySubZoneNos(SessionFactory factory,String zoneNo);
}
