package com.koron.inwlms.bean.VO.indexData;

import java.util.List;

import com.koron.inwlms.bean.VO.common.IndicatorVO;


public class MultParamterIndicatorVO {

    //当年的
	public List<IndicatorVO>  currentIndicatorList;
	//上年的
	public List<IndicatorVO>  lastIndicatorList;
	//当年的平均值
	public double avgCurrentValue;
	//上年度的平均值
	public double avgLastValue;
	
	
	public List<IndicatorVO> getCurrentIndicatorList() {
		return currentIndicatorList;
	}
	public void setCurrentIndicatorList(List<IndicatorVO> currentIndicatorList) {
		this.currentIndicatorList = currentIndicatorList;
	}
	public List<IndicatorVO> getLastIndicatorList() {
		return lastIndicatorList;
	}
	public void setLastIndicatorList(List<IndicatorVO> lastIndicatorList) {
		this.lastIndicatorList = lastIndicatorList;
	}
	public double getAvgCurrentValue() {
		return avgCurrentValue;
	}
	public void setAvgCurrentValue(double avgCurrentValue) {
		this.avgCurrentValue = avgCurrentValue;
	}
	public double getAvgLastValue() {
		return avgLastValue;
	}
	public void setAvgLastValue(double avgLastValue) {
		this.avgLastValue = avgLastValue;
	}
	
}
