package com.koron.inwlms.bean.VO.report;

/**
 * 压力流量监测设备可操作性报表VO
 * @author csh
 *
 */
public class PFLoggerOperabilityReportVO {

	 /**
     * 分区编号
     */
    private String zoneNo;

    /**
     * 监测点编号
     */
    private String loggerNo;


    /**
     * 监测设备名称
     */
    private String deviceName;

    /**
     * 关联的dma编号
     */
    private String dmaNo;

    /**
     * 总共天数
     */
    private String totalDays;

    /**
     * 可操作性天数
     */
    private String operableDays;

    /**
     * 可操作性百分比
     */
    private Double operablePercent;

    /**
     * 最后记录时间
     */
    private String lastReadingTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getLoggerNo() {
		return loggerNo;
	}

	public void setLoggerNo(String loggerNo) {
		this.loggerNo = loggerNo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDmaNo() {
		return dmaNo;
	}

	public void setDmaNo(String dmaNo) {
		this.dmaNo = dmaNo;
	}

	public String getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}

	public String getOperableDays() {
		return operableDays;
	}

	public void setOperableDays(String operableDays) {
		this.operableDays = operableDays;
	}

	public Double getOperablePercent() {
		return operablePercent;
	}

	public void setOperablePercent(Double operablePercent) {
		this.operablePercent = operablePercent;
	}

	public String getLastReadingTime() {
		return lastReadingTime;
	}

	public void setLastReadingTime(String lastReadingTime) {
		this.lastReadingTime = lastReadingTime;
	}
    
    
}
