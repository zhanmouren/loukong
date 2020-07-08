package com.koron.inwlms.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneInfoDTO;
import com.koron.inwlms.bean.VO.apparentLoss.MeterInfo;
import com.koron.inwlms.bean.VO.apparentLoss.ZoneInfo;
import com.koron.inwlms.bean.VO.intellectPartition.GisConfigDetVO;
import com.koron.inwlms.bean.VO.zoneLoss.PositionInfoVO;
import com.koron.inwlms.bean.VO.zoneLoss.ZoneDetailInfoVO;

/**
 * 
 * GIS Mapper(暂用，后续需通过gis系统接口获取)
 * @author csh
 * @Date 2020/04/14
 */
public interface GisMapper {

	/**
	 * 根据分区等级查询分区信息
	 * @param rank
	 * @return
	 */
	List<ZoneInfo> queryZoneNosByRank(@Param("rank") String rank,@Param("zoneNo") String zoneNo);
	
	/**
	 * 根据编号查询所有子分区
	 * @param zoneNo
	 * @return
	 */
//	List<ZoneInfo> querySubZoneNos(@Param("zoneNo") String zoneNo);
	
	/**
	 * 根据分区查询所有水表信息
	 * @param zoneNo
	 * @return
	 */
//	List<MeterInfo> queryMeterByZoneNo(String zoneNo);
	
	/**
	 * 根据分区编号查询分区名称
	 * @param zoneNo
	 * @return
	 */
	String queryZoneNameByNo(String zoneNo);
	
	/**
	 * 查询分区定位信息
	 * @param queryZoneInfoDTO
	 * @return
	 */
	PositionInfoVO queryZonePositionInfo(QueryZoneInfoDTO queryZoneInfoDTO);

	/**
	 * 查询分区详情信息
	 * @param queryZoneInfoDTO
	 * @return
	 */
	ZoneDetailInfoVO queryZoneDetailInfo(@Param("rank") String rank,@Param("zoneNo") String zoneNo);
	
	/**
	 * 模糊查询分区信息
	 * @param queryZoneInfoDTO
	 * @return
	 */
	List<ZoneInfo> queryFuzzyZoneInfo(@Param("rank") String rank,@Param("zoneNo") String zoneNo,@Param("zoneName") String zoneName);

	/**
	 * 根据分区编号获取等级
	 * @param zoneNo
	 * @return
	 */
	Integer getZoneRankByNo(String zoneNo);
	
	/**
	 * 查询虚拟分区详情信息
	 * @param queryZoneInfoDTO
	 * @return
	 */
	ZoneDetailInfoVO queryVZoneDetailInfo(@Param("zoneNo") String zoneNo);
	
	/**
	 * 通过模块名查询地图配置
	 * @param moduleNo
	 * @return
	 */
	List<GisConfigDetVO> queryGisMapConfig(@Param("moduleNo") String moduleNo);
	
	/**
	 * 根据分区编号查询等级
	 * @param zoneNo
	 * @return
	 */
	Integer queryZoneRankByNo(@Param("zoneNo") String zoneNo);
	
}
