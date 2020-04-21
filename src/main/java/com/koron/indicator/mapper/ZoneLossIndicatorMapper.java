package com.koron.indicator.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koron.indicator.bean.VZoneInfo;

/**
 * 分区漏损指标生成Mapper
 * @author csh 
 * @Date 2020/04/20
 *
 */
public interface ZoneLossIndicatorMapper {

	/**
	 * 获取监测点当天的时刻的最小流量
	 * @param code 监测点
	 * @return
	 */
	double getDMoniMinFlow(@Param("code") String code,@Param("startTime") Date startTime,@Param("endTime") Date endTime); 

	/**
	 * 查询虚拟分区信息
	 * @param zoneNo
	 * @return
	 */
	List<VZoneInfo> queryVZoneInfoByNo(String zoneNo);
	
	/**
	 * 根据分区编码获取日最小夜间流量
	 * @return
	 */
	double getDMnfByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);
	
	/**
	 * 获取月最小夜间流量（当前月各日日度值的平均值）
	 * @return
	 */
	double getMMnfAvg(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

	
	/**
	 * 获取月全网的最小夜间流量（当前月各日日度值的平均值）
	 * @return
	 */
	double getMWnMnfAvg(@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 获取年最小夜间流量（当前年各月月度值的平均值）
	 * @return
	 */
	double getYMnfAvg(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

}
