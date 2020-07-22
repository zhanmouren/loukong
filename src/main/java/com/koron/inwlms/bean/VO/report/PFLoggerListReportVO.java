package com.koron.inwlms.bean.VO.report;

/**
 * 压力流量监测设备列表报告VO
 * @author csh
 *
 */
public class PFLoggerListReportVO {
	/**
     * logger参考编号
     */
    private String loggerRef;

    /**
     * logger ID
     */
    private String loggerId;

    /**
     * 关联设施标识
     */
    private String facId;

    /**
     * DMS图层DMS图层
     */
    private String facType;

    /**
     * 厂商
     */
    private String manufacturer;

    /**
     * 数据采集时间间隔
     */
    private String readInterva;

    /**
     * X坐标
     */
    private String coordinateX;

    /**
     * Y坐标
     */
    private String coordinateY;

    /**
     * 一级分区
     */
    private String firstZone;

    /**
     * 二级分区
     */
    private String secondZone;

    /**
     * DMA编号
     */
    private String dmaNos;

	public String getLoggerRef() {
		return loggerRef;
	}

	public void setLoggerRef(String loggerRef) {
		this.loggerRef = loggerRef;
	}

	public String getLoggerId() {
		return loggerId;
	}

	public void setLoggerId(String loggerId) {
		this.loggerId = loggerId;
	}

	public String getFacId() {
		return facId;
	}

	public void setFacId(String facId) {
		this.facId = facId;
	}

	public String getFacType() {
		return facType;
	}

	public void setFacType(String facType) {
		this.facType = facType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getReadInterva() {
		return readInterva;
	}

	public void setReadInterva(String readInterva) {
		this.readInterva = readInterva;
	}

	public String getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(String coordinateX) {
		this.coordinateX = coordinateX;
	}

	public String getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(String coordinateY) {
		this.coordinateY = coordinateY;
	}

	public String getFirstZone() {
		return firstZone;
	}

	public void setFirstZone(String firstZone) {
		this.firstZone = firstZone;
	}

	public String getSecondZone() {
		return secondZone;
	}

	public void setSecondZone(String secondZone) {
		this.secondZone = secondZone;
	}

	public String getDmaNos() {
		return dmaNos;
	}

	public void setDmaNos(String dmaNos) {
		this.dmaNos = dmaNos;
	}
    
    

}
