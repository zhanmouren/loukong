package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 表计管理指标分区排行榜
 * @author csh
 * @Date 2020/03/26
 */
public class MeterManageRankData {
	
	/**
	 * 未抄表占比排行榜
	 */
	private ZoneDatas mmrPercentNonMeterReadList;
	
	/**
	 * 抄表及时率排行榜
	 */
	private ZoneDatas mmrMeterReadRateList;
	
	/**
	 * 抄表时间不固定占比排行榜
	 */
	private ZoneDatas mmrNonMeterReadTimeRateList;
	
	/**
	 * 超期服役水表占比排行榜
	 */
	private ZoneDatas mmrOverdueMetersRateList;

	public ZoneDatas getMmrPercentNonMeterReadList() {
		return mmrPercentNonMeterReadList;
	}

	public ZoneDatas getMmrMeterReadRateList() {
		return mmrMeterReadRateList;
	}

	public ZoneDatas getMmrNonMeterReadTimeRateList() {
		return mmrNonMeterReadTimeRateList;
	}

	public ZoneDatas getMmrOverdueMetersRateList() {
		return mmrOverdueMetersRateList;
	}

	public void setMmrPercentNonMeterReadList(ZoneDatas mmrPercentNonMeterReadList) {
		this.mmrPercentNonMeterReadList = mmrPercentNonMeterReadList;
	}

	public void setMmrMeterReadRateList(ZoneDatas mmrMeterReadRateList) {
		this.mmrMeterReadRateList = mmrMeterReadRateList;
	}

	public void setMmrNonMeterReadTimeRateList(ZoneDatas mmrNonMeterReadTimeRateList) {
		this.mmrNonMeterReadTimeRateList = mmrNonMeterReadTimeRateList;
	}

	public void setMmrOverdueMetersRateList(ZoneDatas mmrOverdueMetersRateList) {
		this.mmrOverdueMetersRateList = mmrOverdueMetersRateList;
	}
	
}
