package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区漏点信息VO
 * @author csh
 *
 */
public class BurstLeakAnalysisVO {

	private String faultType;
	private String[] smGeometry;
	private	String mainAge;
	private	String mainBurstId;
	private	String mainDiameter;
	private	String processingStatus;
	private String xy;
	public String getFaultType() {
		return faultType;
	}
	public void setFaultType(String faultType) {
		this.faultType = faultType;
	}
	public String getMainAge() {
		return mainAge;
	}
	public void setMainAge(String mainAge) {
		this.mainAge = mainAge;
	}
	public String getMainBurstId() {
		return mainBurstId;
	}
	public void setMainBurstId(String mainBurstId) {
		this.mainBurstId = mainBurstId;
	}
	public String getMainDiameter() {
		return mainDiameter;
	}
	public void setMainDiameter(String mainDiameter) {
		this.mainDiameter = mainDiameter;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public String[] getSmGeometry() {
		return smGeometry;
	}
	public void setSmGeometry(String[] smGeometry) {
		this.smGeometry = smGeometry;
	}
	public String getXy() {
		return xy;
	}
	public void setXy(String xy) {
		this.xy = xy;
	}
	
}
