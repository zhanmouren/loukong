package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告水表运行分析VO
 * @author csh
 * @Date 2020/03/31
 */
public class DrMeterAnaDataVO {

	/**
	 * 不同口径水表计量情况统计列表
	 */
	private List<MeterRunAnalysisVO> mraLists;
	
	/**
	 * 小口径，大口径，大口径（除消防表）的饼图统计数据
	 */
	private MeterAnalysisMapVO meterAnalysisMapVO;
	
	/**
	 * 消防表的分析数据
	 */
	private FsMeterReadData fsMeterReadData;
	
	/**
	 * 小口径的零流量统计数据
	 */
	private DrSmallDnMeterData drSmallDnMeterData;
	
	/**
	 * 小口径低流量水表月水量分布情况
	 */
	private List<DrSmallDnAnaData> dsdaLists;



	public FsMeterReadData getFsMeterReadData() {
		return fsMeterReadData;
	}

	public DrSmallDnMeterData getDrSmallDnMeterData() {
		return drSmallDnMeterData;
	}

	public List<DrSmallDnAnaData> getDsdaLists() {
		return dsdaLists;
	}


	public void setFsMeterReadData(FsMeterReadData fsMeterReadData) {
		this.fsMeterReadData = fsMeterReadData;
	}

	public void setDrSmallDnMeterData(DrSmallDnMeterData drSmallDnMeterData) {
		this.drSmallDnMeterData = drSmallDnMeterData;
	}

	public void setDsdaLists(List<DrSmallDnAnaData> dsdaLists) {
		this.dsdaLists = dsdaLists;
	}

	public List<MeterRunAnalysisVO> getMraLists() {
		return mraLists;
	}

	public void setMraLists(List<MeterRunAnalysisVO> mraLists) {
		this.mraLists = mraLists;
	}

	public MeterAnalysisMapVO getMeterAnalysisMapVO() {
		return meterAnalysisMapVO;
	}

	public void setMeterAnalysisMapVO(MeterAnalysisMapVO meterAnalysisMapVO) {
		this.meterAnalysisMapVO = meterAnalysisMapVO;
	}
	
	
}
