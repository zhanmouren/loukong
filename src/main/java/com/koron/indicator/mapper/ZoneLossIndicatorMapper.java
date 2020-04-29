package com.koron.indicator.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.koron.indicator.bean.MnfAndMnfTimeInfo;
import com.koron.indicator.bean.VZoneInfo;

/**
 * 分区漏损指标生成Mapper
 * @author csh 
 * @Date 2020/04/20
 *
 */
public interface ZoneLossIndicatorMapper {

	/**
	 * 获取监测点当天最小流量和所在时间
	 * @param code 监测点
	 * @return
	 */
	MnfAndMnfTimeInfo getDMoniMinFlow(@Param("stationCode") String stationCode,@Param("code") String code,@Param("startTime") Date startTime,@Param("endTime") Date endTime); 

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
	Integer getDMnfByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);
	
	/**
	 * 根据分区编码获取月最小夜间流量
	 * @return
	 */
	Integer getMMnfByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);
	
	/**
	 * 根据分区编码获取年最小夜间流量
	 * @return
	 */
	Integer getYMnfByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);
	
	/**
	 * 获取月指标平均值（当前月各日日度值的平均值）
	 * @return
	 */
	double getMIndicatorAvg(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

	
	/**
	 * 获取月全网的指标（当前月各日日度值的平均值）
	 * @return
	 */
	double getMWnIndicatorAvg(@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 获取年指标（当前年各月月度值的平均值）
	 * @return
	 */
	double getYIndicatorAvg(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 获取年全网的年指标（当前年各月月度值的平均值）
	 * @return
	 */
	double getYWnIndicatorAvg(@Param("code") String code,@Param("year") Integer year); 

	/**
	 * 根据分区编码获取日除大用户的最小夜间流量
	 * @return
	 */
	Integer getDMnflfByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取日最小夜间流量所在时刻
	 * @return
	 */
	Integer getDMnftByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 获取监测点时刻平均压力
	 * @param code 监测点
	 * @return
	 */
	List<Integer> getHMoniPress(@Param("stationCode") String stationCode,@Param("code") String code,@Param("startTime") Date startTime,@Param("endTime") Date endTime); 

	/**
	 * 根据分区编码获取日平均夜间供水压力
	 * @return
	 */
	Integer getDAznpByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);
	

	/**
	 * 根据分区编码获取日转换因子
	 * @return
	 */
	Integer getDDnfByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取日供水量
	 * @return
	 */
	Integer getDFwssitdfByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月供水量
	 * @return
	 */
	Integer getMFwssitdfByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年供水量
	 * @return
	 */
	Integer getYFwssitdfByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	
	/**
	 * 根据分区编码获取日漏失量
	 * @return
	 */
	Integer getDLaByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月漏失量
	 * @return
	 */
	Integer getMLaByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取月漏失量
	 * @return
	 */
	Integer getYLaByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	
	/**
	 * 获取月指标平均值（当前月各日日度值的加和）
	 * @return
	 */
	Integer getMIndicatorSum(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 获取月全网的指标（当前月各日日度值的加和）
	 * @return
	 */
	Integer getMWnIndicatorSum(@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 获取年全网的指标（当前年各月月度值的加和）
	 * @return
	 */
	Integer getYWnIndicatorSum(@Param("code") String code,@Param("year") Integer year); 

	/**
	 * 获取年指标（当前年各月月度值的加和）
	 * @return
	 */
	Integer getYIndicatorSum(@Param("zoneNo") String zoneNo,@Param("code") String code,@Param("month") Integer month); 

	/**
	 * 根据分区编码获取月用户数
	 * @return
	 */
	Integer getMNocmByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年用户数
	 * @return
	 */
	Integer getYNocmByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	
	/**
	 * 根据分区编码获取月管长
	 * @return
	 */
	Integer getMFtplByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年管长
	 * @return
	 */
	Integer getYFtplByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	
	/**
	 * 获取全网的日漏失量
	 * @return
	 */
	Integer getDWnLaByNo(@Param("date") Date date);

	/**
	 * 获取全网的日供水量
	 * @return
	 */
	Integer getDWnFwssitdfByNo(@Param("date") Date date);

	/**
	 * 获取全网的月漏失量
	 * @return
	 */
	Integer getMWnLaByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的月供水量
	 * @return
	 */
	Integer getMWnFwssitdfByNo(@Param("yearMonth") Integer yearMonth);
	
	/**
	 * 获取全网的年漏失量
	 * @return
	 */
	Integer getYWnLaByNo(@Param("year") Integer year);

	/**
	 * 获取全网的年供水量
	 * @return
	 */
	Integer getYWnFwssitdfByNo(@Param("year") Integer year);
	
	/**
	 * 根据全网月用户数
	 * @return
	 */
	Integer getMWnNocmByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据全网年用户数
	 * @return
	 */
	Integer getYWnNocmByNo(@Param("year") Integer year);

	/**
	 * 获取全网月管长
	 * @return
	 */
	Integer getMWnFtplByNo(@Param("yearMonth") Integer yearMonth);
	
	/**
	 * 获取全网年管长
	 * @return
	 */
	Integer getYWnFtplByNo(@Param("year") Integer year);

	/**
	 * 获取全网的日MNF
	 * @return
	 */
	Integer getDWnMnfByNo(@Param("date") Date date);

	/**
	 * 获取全网的月MNF
	 * @return
	 */
	Integer getMWnMnfByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年MNF
	 * @return
	 */
	Integer getYWnMnfByNo(@Param("year") Integer year);

	/**
	 * 根据分区编码获取日漏损量
	 * @return
	 */
	Integer getDWlByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月漏损量
	 * @return
	 */
	Integer getMWlByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年漏损量
	 * @return
	 */
	Integer getYWlByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日漏损量
	 * @return
	 */
	Integer getDWnWlByNo(@Param("date") Date date);

	/**
	 * 获取全网的月漏损量
	 * @return
	 */
	Integer getMWnWlByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年漏损量
	 * @return
	 */
	Integer getYWnWlByNo(@Param("year") Integer year);
	
	/**
	 * 根据分区编码获取日单位户数漏损量
	 * @return
	 */
	Integer getDLcaByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月单位户数漏损量
	 * @return
	 */
	Integer getMLcaByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年单位户数漏损量
	 * @return
	 */
	Integer getYLcaByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日单位户数漏损量
	 * @return
	 */
	Integer getDWnLcaByNo(@Param("date") Date date);

	/**
	 * 获取全网的月单位户数漏损量
	 * @return
	 */
	Integer getMWnLcaByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年单位户数漏损量
	 * @return
	 */
	Integer getYWnLcaByNo(@Param("year") Integer year);
	
	/**
	 * 根据分区编码获取日平均供水压力
	 * @return
	 */
	Integer getDAspByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月平均供水压力
	 * @return
	 */
	Integer getMAspByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年平均供水压力
	 * @return
	 */
	Integer getYAspByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日平均供水压力
	 * @return
	 */
	Integer getDWnAspByNo(@Param("date") Date date);

	/**
	 * 获取全网的月平均供水压力
	 * @return
	 */
	Integer getMWnAspByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年平均供水压力
	 * @return
	 */
	Integer getYWnAspByNo(@Param("year") Integer year);
	
	/**
	 * 根据分区编码获取日单位管长漏损量
	 * @return
	 */
	Integer getDLplByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月单位管长漏损量
	 * @return
	 */
	Integer getMLplByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年单位管长漏损量
	 * @return
	 */
	Integer getYLplByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日单位管长漏损量
	 * @return
	 */
	Integer getDWnLplByNo(@Param("date") Date date);

	/**
	 * 获取全网的月单位管长漏损量
	 * @return
	 */
	Integer getMWnLplByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年单位管长漏损量
	 * @return
	 */
	Integer getYWnLplByNo(@Param("year") Integer year);
	
	/**
	 * 根据分区编码获取日产销差水量
	 * @return
	 */
	Integer getDNrwByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月产销差水量
	 * @return
	 */
	Integer getMNrwByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年产销差水量
	 * @return
	 */
	Integer getYNrwByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日产销差水量
	 * @return
	 */
	Integer getDWnNrwByNo(@Param("date") Date date);

	/**
	 * 获取全网的月产销差水量
	 * @return
	 */
	Integer getMWnNrwByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年产销差水量
	 * @return
	 */
	Integer getYWnNrwByNo(@Param("year") Integer year);
	
	/**
	 * 根据分区编码获取日未计量水量
	 * @return
	 */
	Integer getDUfwcByNo(@Param("zoneNo") String zoneNo,@Param("date") Date date);

	/**
	 * 根据分区编码获取月未计量水量
	 * @return
	 */
	Integer getMUfwcByNo(@Param("zoneNo") String zoneNo,@Param("yearMonth") Integer yearMonth);

	/**
	 * 根据分区编码获取年未计量水量
	 * @return
	 */
	Integer getYUfwcByNo(@Param("zoneNo") String zoneNo,@Param("year") Integer year);

	/**
	 * 获取全网的日未计量水量
	 * @return
	 */
	Integer getDWnUfwcByNo(@Param("date") Date date);

	/**
	 * 获取全网的月未计量水量
	 * @return
	 */
	Integer getMWnUfwcByNo(@Param("yearMonth") Integer yearMonth);

	/**
	 * 获取全网的年未计量水量
	 * @return
	 */
	Integer getYWnUfwcByNo(@Param("year") Integer year);
}
