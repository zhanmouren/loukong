package com.koron.inwlms.bean.VO.report;

/**
 * 噪声监测设备列表报表VO
 * @author csh
 *
 */
public class NoiseLoggerListReportVO {
	/**
     * Logger ID
     */
    private String loggerId;

    /**
     * 供水类型
     */
    private String networkType;

    /**
     * 关联设施标识
     */
    private String facId;

    /**
     * DMS图层
     */
    private String facType;

    /**
     * X坐标
     */
    private String coordinateX;

    /**
     * X坐标
     */
    private String coordinateY;

    /**
     * 运作区
     */
    private String firstZone;

    /**
     * 子运作区
     */
    private String secondZone;

    /**
     * 型号
     */
    private String model;

    /**
     * 安装日期
     */
    private String installationDate;

	public String getLoggerId() {
		return loggerId;
	}

	public void setLoggerId(String loggerId) {
		this.loggerId = loggerId;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getInstallationDate() {
		return installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
    
    
}
