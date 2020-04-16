package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 表观漏损图表数据
 * @author csh
 *
 */
public class ALMapDataVO {
	
	/**
	 * 表观漏损统计指标数据
	 */
	private ALData alData;
	
	/**
	 * 表计管理指标分析数据
	 */
	private MeterManageData meterManageData;
	
	/**
	 * 表观漏损统计指标分区排行榜
	 */
	private ZoneRankData zoneRankData;
	
	/**
	 * 表计管理指标分区排行榜
	 */
	private MeterManageRankData meterManageRankData;

	public ALData getAlData() {
		return alData;
	}

	public MeterManageData getMeterManageData() {
		return meterManageData;
	}

	public ZoneRankData getZoneRankData() {
		return zoneRankData;
	}

	public MeterManageRankData getMeterManageRankData() {
		return meterManageRankData;
	}

	public void setAlData(ALData alData) {
		this.alData = alData;
	}

	public void setMeterManageData(MeterManageData meterManageData) {
		this.meterManageData = meterManageData;
	}

	public void setZoneRankData(ZoneRankData zoneRankData) {
		this.zoneRankData = zoneRankData;
	}

	public void setMeterManageRankData(MeterManageRankData meterManageRankData) {
		this.meterManageRankData = meterManageRankData;
	}
}
