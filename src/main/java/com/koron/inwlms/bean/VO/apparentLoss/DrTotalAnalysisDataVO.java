package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告总体分析接口
 * @author csh
 *
 */
public class DrTotalAnalysisDataVO {

	/**
	 * 水司名称
	 */
	private String groupName;
	
	/**
	 * 分区名称
	 */
	private String analysisRange;
	
	/**
	 * 分析时间段
	 */
	private String analysisTime;
	
	/**
	 * 水表数量
	 */
	private Integer meterNum;
	
	/**
	 * 平均月抄表量
	 */
	private Double mMeterReadFlow;
	
	/**
	 * 表观漏损占比
	 */
	private Double percentAL;
	
	/**
	 * 表观漏损指数
	 */
	private Double ALI;
	
	/**
	 * 超期服役水表占比
	 */
	private Double overdueMetersRate;
	
	/**
	 * 水表基础信息不完善占比（管理不规范占比）
	 */
	private Double nonBasicInfoMeterRate;
	
	/**
	 * 总体评分
	 */
	private Double score;
	
	/**
	 * 评分等级（优，良，中，差）
	 */
	private String scoreLevel;
	
	/**
	 * 子分区排名
	 */
	private ZoneDatas subZoneRank;
	
	/**
	 * 走势分析
	 */
	private TrendAnalysisData trendAnalysisData;
	
	/**
	 * 水表运行分析占比数据
	 */
	private MeterAnalysisData meterAnalysisData;
	
	public String getGroupName() {
		return groupName;
	}


	public String getAnalysisTime() {
		return analysisTime;
	}

	public Integer getMeterNum() {
		return meterNum;
	}

	public Double getmMeterReadFlow() {
		return mMeterReadFlow;
	}

	public Double getPercentAL() {
		return percentAL;
	}

	public Double getALI() {
		return ALI;
	}

	public Double getOverdueMetersRate() {
		return overdueMetersRate;
	}

	public Double getNonBasicInfoMeterRate() {
		return nonBasicInfoMeterRate;
	}

	public Double getScore() {
		return score;
	}

	public String getScoreLevel() {
		return scoreLevel;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setAnalysisTime(String analysisTime) {
		this.analysisTime = analysisTime;
	}

	public void setMeterNum(Integer meterNum) {
		this.meterNum = meterNum;
	}

	public void setmMeterReadFlow(Double mMeterReadFlow) {
		this.mMeterReadFlow = mMeterReadFlow;
	}

	public void setPercentAL(Double percentAL) {
		this.percentAL = percentAL;
	}

	public void setALI(Double aLI) {
		ALI = aLI;
	}

	public void setOverdueMetersRate(Double overdueMetersRate) {
		this.overdueMetersRate = overdueMetersRate;
	}

	public void setNonBasicInfoMeterRate(Double nonBasicInfoMeterRate) {
		this.nonBasicInfoMeterRate = nonBasicInfoMeterRate;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}


	public String getAnalysisRange() {
		return analysisRange;
	}


	public void setAnalysisRange(String analysisRange) {
		this.analysisRange = analysisRange;
	}


	public ZoneDatas getSubZoneRank() {
		return subZoneRank;
	}


	public void setSubZoneRank(ZoneDatas subZoneRank) {
		this.subZoneRank = subZoneRank;
	}


	public TrendAnalysisData getTrendAnalysisData() {
		return trendAnalysisData;
	}


	public void setTrendAnalysisData(TrendAnalysisData trendAnalysisData) {
		this.trendAnalysisData = trendAnalysisData;
	}


	public MeterAnalysisData getMeterAnalysisData() {
		return meterAnalysisData;
	}


	public void setMeterAnalysisData(MeterAnalysisData meterAnalysisData) {
		this.meterAnalysisData = meterAnalysisData;
	}
	
}
