package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;

/**
 * GIS分区接口（临时）
 * @author csh
 * @Date 2020/03/25
 *
 */
public interface GisZoneService {

	/**
	 * 通过等级查询分区编号
	 * @param factory
	 * @param zoneRank
	 * @return
	 */
	List<String> queryZoneNosByRank(SessionFactory factory,Integer zoneRank);
	
	/**
	 * 根据编号查询所有子分区
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<String> querySubZoneNos(SessionFactory factory,String zoneNo);
	
	/**
	 * 根据分区查询所有水表信息
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<MeterInfo> queryMeterByZoneNo(SessionFactory factory,String zoneNo);
}
