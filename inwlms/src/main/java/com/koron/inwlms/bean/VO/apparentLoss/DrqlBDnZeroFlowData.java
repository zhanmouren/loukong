package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;
import java.util.Map;

/**
 * 诊断报告-问题清单-大口径零流量数据
 * @author csh
 * @Date 2020/04/02
 */
public class DrqlBDnZeroFlowData extends DrqlBaseData{

	/**
	 * 抄表日
	 */
	private String mReadDate;
	
	
	/**
	 * 数据集合
	 */
	private List<Map<Integer,Double>> datas;


	public String getmReadDate() {
		return mReadDate;
	}

	public void setmReadDate(String mReadDate) {
		this.mReadDate = mReadDate;
	}

	public List<Map<Integer, Double>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<Integer, Double>> datas) {
		this.datas = datas;
	}

}
