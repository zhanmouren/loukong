package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryVZoneInfoDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.MeterRunAnalysisVO;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.zoneLoss.PositionInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.VZoneInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;

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
	List<ZoneInfo> queryZoneNosByRank(SessionFactory factory,Integer zoneRank);
	
	/**
	 * 根据编号查询所有子分区
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<ZoneInfo> querySubZoneNos(SessionFactory factory,String zoneNo);
	
	/**
	 * 根据分区查询所有水表信息
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	List<MeterInfo> queryMeterByZoneNo(SessionFactory factory,String zoneNo);

	/**
	 * 根据分区编号查询分区名称
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	String queryZoneNameByNo(SessionFactory factory,String zoneNo);
	
	/**
	 * 查询分区定位信息
	 * @param factory
	 * @param queryZoneInfoDTO
	 * @return
	 */
	PositionInfoVO queryZonePositionInfo(SessionFactory factory,QueryZoneInfoDTO queryZoneInfoDTO);

	/**
	 * 查询分区详情信息
	 * @param factory
	 * @param queryZoneInfoDTO
	 * @return
	 */
	ZoneDetailInfoVO queryZoneDetailInfo(SessionFactory factory,QueryZoneInfoDTO queryZoneInfoDTO);

	/**
	 * 模糊查询分区信息
	 * @param factory
	 * @param queryZoneInfoDTO
	 * @return
	 */
	List<ZoneInfo> queryFuzzyZoneInfo(SessionFactory factory,QueryZoneInfoDTO queryZoneInfoDTO);

	/**
	 * 查询虚拟分区信息
	 * @param factory
	 * @param queryVZoneInfoDTO
	 * @return
	 */
	List<VZoneInfoVO> queryVZoneInfo(SessionFactory factory,QueryVZoneInfoDTO queryVZoneInfoDTO);

	/**
	 * 根据分区编号获取等级
	 * @param factory
	 * @param zoneNo
	 * @return
	 */
	Integer getZoneRankByNo(SessionFactory factory,String zoneNo);


}
