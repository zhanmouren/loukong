package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 表观漏损统计指标分区排行榜
 * @author csh
 * @Date 2020/03/26
 */
public class ZoneRankData {
	
	/**
	 * 表观漏损量排行榜
	 */
	private ZoneDatas zrALList;
	
	/**
	 * 单位户数表观漏损量排行榜
	 */
	private ZoneDatas zrPerCustomerAccALList;
	
	/**
	 * 表观漏损占比排行榜
	 */
	private ZoneDatas zrPercentALList;
	
	/**
	 * 表观漏损指数排行榜
	 */
	private ZoneDatas zrALIList;

	public ZoneDatas getZrALList() {
		return zrALList;
	}

	public ZoneDatas getZrPerCustomerAccALList() {
		return zrPerCustomerAccALList;
	}

	public ZoneDatas getZrPercentALList() {
		return zrPercentALList;
	}

	public ZoneDatas getZrALIList() {
		return zrALIList;
	}

	public void setZrALList(ZoneDatas zrALList) {
		this.zrALList = zrALList;
	}

	public void setZrPerCustomerAccALList(ZoneDatas zrPerCustomerAccALList) {
		this.zrPerCustomerAccALList = zrPerCustomerAccALList;
	}

	public void setZrPercentALList(ZoneDatas zrPercentALList) {
		this.zrPercentALList = zrPercentALList;
	}

	public void setZrALIList(ZoneDatas zrALIList) {
		this.zrALIList = zrALIList;
	}
}
