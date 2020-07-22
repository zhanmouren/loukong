package com.koron.inwlms.bean.VO.report;

/**
 * DMA可操作性报表VO
 * @author csh
 *
 */
public class DmaOperabilityReportVO {

	/**
     * 区域
     */
    private String firstZone;

    /**
     * dma编号
     */
    private String dmaNo;

    /**
     * 位置
     */
    private String location;

    /**
     * 总天数
     */
    private Integer totalDays;

    /**
     * 可操作天数
     */
    private Integer operableDays;

    /**
     * 可操作占比
     */
    private Double operablePercent;

    /**
     * 可操作占比%
     */
    private String operablePercentName;

	public String getFirstZone() {
		return firstZone;
	}

	public void setFirstZone(String firstZone) {
		this.firstZone = firstZone;
	}

	public String getDmaNo() {
		return dmaNo;
	}

	public void setDmaNo(String dmaNo) {
		this.dmaNo = dmaNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public Integer getOperableDays() {
		return operableDays;
	}

	public void setOperableDays(Integer operableDays) {
		this.operableDays = operableDays;
	}

	public Double getOperablePercent() {
		return operablePercent;
	}

	public void setOperablePercent(Double operablePercent) {
		this.operablePercent = operablePercent;
	}

	public String getOperablePercentName() {
		return operablePercentName;
	}

	public void setOperablePercentName(String operablePercentName) {
		this.operablePercentName = operablePercentName;
	}
    
    
}
