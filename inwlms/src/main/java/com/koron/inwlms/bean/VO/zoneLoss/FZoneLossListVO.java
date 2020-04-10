package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 一级分区漏损列表VO
 * @author csh
 * @Date 2020.03.09
 */
public class FZoneLossListVO {

	/**
	 * 分区编号
	 */
	private String zoneNo;
	
	/**
	 * 分区名称
	 */
	private String zoneName;
	
	/**
	 * 分区等级
	 */
	private Integer zoneRank;
	
	/**
	 * 父分区编号
	 */
	private String pZoneNo;
	
	/**
	 * 父分区名称
	 */
	private String pZoneName;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 警报状态
	 */
	private Integer alarmStatus;
	
	/**
	 * 用户表数
	 */
	private Integer meterNum;
	
	/**
	 * 管长
	 */
	private Double pipeLength;
	
	/**
	 * 供水量
	 */
	private Double flow;
	
	/**
	 * 用水量
	 */
	private Double useFlow;
	
	/**
	 * 漏损量
	 */
	private Double lossFlow;
	
	/**
	 * 单位用户漏损量
	 */
	private Double perUserLossFlow;
	
	/**
	 * 单位管长漏损量
	 */
	private Double perLengthLossFlow;
	
	/**
	 * 漏损率
	 */
	private Double lossRate;
	
	/**
	 * 最小夜间流量
	 */
	private Double mnf;
	
	/**
	 * 最小夜间流量所在时刻
	 */
	private Double mnfTime;
	
	/**
	 * 夜间最小流量与日均供水量的比率
	 */
	private Double mnfTdf;
	
	/**
	 * 创建时间
	 */
	private Double createTime;

	public String getZoneNo() {
		return zoneNo;
	}

	public String getZoneName() {
		return zoneName;
	}

	public Integer getZoneRank() {
		return zoneRank;
	}

	public String getpZoneNo() {
		return pZoneNo;
	}

	public String getpZoneName() {
		return pZoneName;
	}

	public String getAddress() {
		return address;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public Integer getMeterNum() {
		return meterNum;
	}

	public Double getPipeLength() {
		return pipeLength;
	}

	public Double getFlow() {
		return flow;
	}

	public Double getUseFlow() {
		return useFlow;
	}

	public Double getLossFlow() {
		return lossFlow;
	}

	public Double getPerUserLossFlow() {
		return perUserLossFlow;
	}

	public Double getPerLengthLossFlow() {
		return perLengthLossFlow;
	}

	public Double getLossRate() {
		return lossRate;
	}

	public Double getMnf() {
		return mnf;
	}

	public Double getMnfTime() {
		return mnfTime;
	}

	public Double getMnfTdf() {
		return mnfTdf;
	}

	public Double getCreateTime() {
		return createTime;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setZoneRank(Integer zoneRank) {
		this.zoneRank = zoneRank;
	}

	public void setpZoneNo(String pZoneNo) {
		this.pZoneNo = pZoneNo;
	}

	public void setpZoneName(String pZoneName) {
		this.pZoneName = pZoneName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public void setMeterNum(Integer meterNum) {
		this.meterNum = meterNum;
	}

	public void setPipeLength(Double pipeLength) {
		this.pipeLength = pipeLength;
	}

	public void setFlow(Double flow) {
		this.flow = flow;
	}

	public void setUseFlow(Double useFlow) {
		this.useFlow = useFlow;
	}

	public void setLossFlow(Double lossFlow) {
		this.lossFlow = lossFlow;
	}

	public void setPerUserLossFlow(Double perUserLossFlow) {
		this.perUserLossFlow = perUserLossFlow;
	}

	public void setPerLengthLossFlow(Double perLengthLossFlow) {
		this.perLengthLossFlow = perLengthLossFlow;
	}

	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}

	public void setMnf(Double mnf) {
		this.mnf = mnf;
	}

	public void setMnfTime(Double mnfTime) {
		this.mnfTime = mnfTime;
	}

	public void setMnfTdf(Double mnfTdf) {
		this.mnfTdf = mnfTdf;
	}

	public void setCreateTime(Double createTime) {
		this.createTime = createTime;
	}
	
}
