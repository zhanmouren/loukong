package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

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
	 * 父编号
	 */
	private String pZoneNo;
	
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
	
	/**
	 * 颜色，1-灰色，2-橙色，3-红色
	 */
	private Integer color;
	
	/**
	 * 子模块
	 */
	private List<ZoneWBLossVO> childrens;


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

	public String getpZoneNo() {
		return pZoneNo;
	}

	public Integer getColor() {
		return color;
	}

	public List<ZoneWBLossVO> getChildrens() {
		return childrens;
	}

	public void setpZoneNo(String pZoneNo) {
		this.pZoneNo = pZoneNo;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public void setChildrens(List<ZoneWBLossVO> childrens) {
		this.childrens = childrens;
	}
	
}
