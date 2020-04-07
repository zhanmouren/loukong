package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区水平衡VO
 * @author csh
 * @Date 2020/04/07
 *
 */
public class ZoneWBLossVO {

	/**
	 * 分区名称
	 */
	private String zoneName;
	
	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 总表水量
	 */
	private Double totalMeterFlow;
	
	/**
	 * 抄表水量
	 */
	private Double meterReadFlow;
	
	/**
	 * 水损量
	 */
	private Double lossFlow;
	
	/**
	 * 水损率
	 */
	private Double lossRate;


	public String getZoneName() {
		return zoneName;
	}

	public String getZoneNo() {
		return zoneNo;
	}

	public Double getTotalMeterFlow() {
		return totalMeterFlow;
	}

	public Double getMeterReadFlow() {
		return meterReadFlow;
	}

	public Double getLossFlow() {
		return lossFlow;
	}

	public Double getLossRate() {
		return lossRate;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setTotalMeterFlow(Double totalMeterFlow) {
		this.totalMeterFlow = totalMeterFlow;
	}

	public void setMeterReadFlow(Double meterReadFlow) {
		this.meterReadFlow = meterReadFlow;
	}

	public void setLossFlow(Double lossFlow) {
		this.lossFlow = lossFlow;
	}

	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}
	
	
}
