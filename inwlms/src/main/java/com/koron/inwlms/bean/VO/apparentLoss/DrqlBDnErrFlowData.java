package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;
import java.util.Map;

/**
 * 诊断报告-问题清单-大口用水异常数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBDnErrFlowData extends DrqlBaseData{

	/**
	 * 抄表日
	 */
	private String mReadDate;
	
	
	/**
	 * 数据集合
	 */
	private List<Map<Integer,Double>> datas;
	
	/**
	 * 建议口径
	 */
	private Integer changeDn;

	public String getmReadDate() {
		return mReadDate;
	}

	public Integer getChangeDn() {
		return changeDn;
	}

	public void setmReadDate(String mReadDate) {
		this.mReadDate = mReadDate;
	}


	public void setChangeDn(Integer changeDn) {
		this.changeDn = changeDn;
	}

	public List<Map<Integer, Double>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<Integer, Double>> datas) {
		this.datas = datas;
	}

}
