package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告处理建议
 * @author csh
 *
 */
public class DrDealAdviseVO {

	/**
	 * 大口径水表处理数据
	 */
	private DrBigDnDealData bigDnDealData;
	
	/**
	 * 大口径水表统计数据
	 */
	private List<DrMeterStatisData> dmsList;
	
	/**
	 * 诊断报告过载大口径水表统计数据
	 */
	private List<DrFlowMeterData> bdfmList;
	
	/**
	 * 正常大表数量
	 */
	private Integer nomalBigMeterNum;
	
	/**
	 * 诊断报告过载小口径水表统计数据
	 */
	private List<DrFlowMeterData> sdfmList;
	
	/**
	 * 低流量水表占比
	 */
	private Double lowFlowMeterRate;
	
	/**
	 * 小于5m³流量水表占比
	 */
	private Double fFlowMeterRate;
	
	/**
	 * 5-10m³流量水表占比
	 */
	private Double ftFlowMeterRate;
	
	/**
	 * 10-20m³流量水表占比
	 */
	private Double ttFlowMeterRate;
	
	/**
	 * 大于20流量水表占比
	 */
	private Double tFlowMeterRate;

	public DrBigDnDealData getBigDnDealData() {
		return bigDnDealData;
	}

	public Integer getNomalBigMeterNum() {
		return nomalBigMeterNum;
	}


	public Double getLowFlowMeterRate() {
		return lowFlowMeterRate;
	}

	public Double getfFlowMeterRate() {
		return fFlowMeterRate;
	}

	public Double getFtFlowMeterRate() {
		return ftFlowMeterRate;
	}

	public Double getTtFlowMeterRate() {
		return ttFlowMeterRate;
	}

	public Double gettFlowMeterRate() {
		return tFlowMeterRate;
	}

	public void setBigDnDealData(DrBigDnDealData bigDnDealData) {
		this.bigDnDealData = bigDnDealData;
	}


	public void setNomalBigMeterNum(Integer nomalBigMeterNum) {
		this.nomalBigMeterNum = nomalBigMeterNum;
	}


	public void setLowFlowMeterRate(Double lowFlowMeterRate) {
		this.lowFlowMeterRate = lowFlowMeterRate;
	}

	public void setfFlowMeterRate(Double fFlowMeterRate) {
		this.fFlowMeterRate = fFlowMeterRate;
	}

	public void setFtFlowMeterRate(Double ftFlowMeterRate) {
		this.ftFlowMeterRate = ftFlowMeterRate;
	}

	public void setTtFlowMeterRate(Double ttFlowMeterRate) {
		this.ttFlowMeterRate = ttFlowMeterRate;
	}

	public void settFlowMeterRate(Double tFlowMeterRate) {
		this.tFlowMeterRate = tFlowMeterRate;
	}

	public List<DrMeterStatisData> getDmsList() {
		return dmsList;
	}

	public List<DrFlowMeterData> getBdfmList() {
		return bdfmList;
	}

	public List<DrFlowMeterData> getSdfmList() {
		return sdfmList;
	}

	public void setDmsList(List<DrMeterStatisData> dmsList) {
		this.dmsList = dmsList;
	}

	public void setBdfmList(List<DrFlowMeterData> bdfmList) {
		this.bdfmList = bdfmList;
	}

	public void setSdfmList(List<DrFlowMeterData> sdfmList) {
		this.sdfmList = sdfmList;
	}
	
}
