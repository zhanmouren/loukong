package com.koron.inwlms.bean.VO.report.statisticalReport;

public class NetFault {
	/**
	 * 分区级别
	 */
	private String zoneGrade;
	/**
	 * 分区名称
	 */
	private String zoneName;
	/**
	 * 分区编码
	 */
	private String zoneCode;
	/**
	 * 分区地址
	 */
	private String zoneAddress;
	/**
	 * 分区类型
	 */
	private String zoneType;
	/**
	 * 管网总长
	 */
	private Double allLength;
	/**
	 * 爆管数
	 */
	private Double tubeBurstNum;
	/**
	 * 渗漏数
	 */
	private Double leakageNum;
	/**
	 * 总故障数
	 */
	private Double allFaultNum;
	/**
	 * 单位管长故障数
	 */
	private Double unitFaultNum;
	/**
	 * 漏失率
	 */
	private Double loss;
	/**
	 * 投诉/上报数
	 */
	private Double complaintNum;

	public String getZoneGrade() {
		return zoneGrade;
	}

	public void setZoneGrade(String zoneGrade) {
		this.zoneGrade = zoneGrade;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneAddress() {
		return zoneAddress;
	}

	public void setZoneAddress(String zoneAddress) {
		this.zoneAddress = zoneAddress;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

	public Double getAllLength() {
		return allLength;
	}

	public void setAllLength(Double allLength) {
		this.allLength = allLength;
	}

	public Double getTubeBurstNum() {
		return tubeBurstNum;
	}

	public void setTubeBurstNum(Double tubeBurstNum) {
		this.tubeBurstNum = tubeBurstNum;
	}

	public Double getLeakageNum() {
		return leakageNum;
	}

	public void setLeakageNum(Double leakageNum) {
		this.leakageNum = leakageNum;
	}

	public Double getAllFaultNum() {
		return allFaultNum;
	}

	public void setAllFaultNum(Double allFaultNum) {
		this.allFaultNum = allFaultNum;
	}

	public Double getUnitFaultNum() {
		return unitFaultNum;
	}

	public void setUnitFaultNum(Double unitFaultNum) {
		this.unitFaultNum = unitFaultNum;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public Double getComplaintNum() {
		return complaintNum;
	}

	public void setComplaintNum(Double complaintNum) {
		this.complaintNum = complaintNum;
	}
	
	
	
	

}
