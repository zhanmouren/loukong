package com.koron.inwlms.bean.VO.report.waterBalanceReport;

import java.util.List;

import com.koron.inwlms.bean.VO.common.IndicatorVO;

/**
 * WB2-一级分区产销差率报表VO
 * @author xiaozhan
 *
 */
public class WB2OneZoneVO {
    //月份
	private String monthId;
	//供水量
	private List<IndicatorVO> gslList;
	//抄表量
	private List<IndicatorVO> cbList;
	//产销差率
	private List<IndicatorVO> cxcList;
	public String getMonthId() {
		return monthId;
	}
	
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public List<IndicatorVO> getGslList() {
		return gslList;
	}

	public List<IndicatorVO> getCbList() {
		return cbList;
	}

	public List<IndicatorVO> getCxcList() {
		return cxcList;
	}

	public void setGslList(List<IndicatorVO> gslList) {
		this.gslList = gslList;
	}

	public void setCbList(List<IndicatorVO> cbList) {
		this.cbList = cbList;
	}

	public void setCxcList(List<IndicatorVO> cxcList) {
		this.cxcList = cxcList;
	}
	
	
}
