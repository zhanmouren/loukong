package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;

/**
 * 诊断报告-问题清单VO
 * @author csh
 * @Date 2020/04/02
 *
 */
public class DrqlVO {

	/**
	 * 大口径零流量
	 */
	private List<DrqlBDnZeroFlowData> drqlBDnZeroFlowData;
	
	/**
	 * 大口径低流量、过载
	 */
	private List<DrqlBDnLHFlowData> drqlBDnLHFlowData;
	
	/**
	 * 大口径用水异常
	 */
	private List<DrqlBDnErrFlowData> drqlBDnErrFlowData;
	
	/**
	 * 用水性质可疑
	 */
	private List<DrqlBDnZeroFlowData> drqlSusUseData;
	
	/**
	 * 小口径零流量
	 */
	private List<DrqlBDnZeroFlowData> drqlsDnZeroFlowData;
	
	/**
	 * 小口径低流量、过载
	 */
	private List<DrqlBDnLHFlowData> drqlsDnLHFlowData;

	public List<DrqlBDnZeroFlowData> getDrqlBDnZeroFlowData() {
		return drqlBDnZeroFlowData;
	}

	public List<DrqlBDnLHFlowData> getDrqlBDnLHFlowData() {
		return drqlBDnLHFlowData;
	}

	public List<DrqlBDnErrFlowData> getDrqlBDnErrFlowData() {
		return drqlBDnErrFlowData;
	}

	public List<DrqlBDnZeroFlowData> getDrqlSusUseData() {
		return drqlSusUseData;
	}

	public List<DrqlBDnZeroFlowData> getDrqlsDnZeroFlowData() {
		return drqlsDnZeroFlowData;
	}

	public List<DrqlBDnLHFlowData> getDrqlsDnLHFlowData() {
		return drqlsDnLHFlowData;
	}

	public void setDrqlBDnZeroFlowData(List<DrqlBDnZeroFlowData> drqlBDnZeroFlowData) {
		this.drqlBDnZeroFlowData = drqlBDnZeroFlowData;
	}

	public void setDrqlBDnLHFlowData(List<DrqlBDnLHFlowData> drqlBDnLHFlowData) {
		this.drqlBDnLHFlowData = drqlBDnLHFlowData;
	}

	public void setDrqlBDnErrFlowData(List<DrqlBDnErrFlowData> drqlBDnErrFlowData) {
		this.drqlBDnErrFlowData = drqlBDnErrFlowData;
	}

	public void setDrqlSusUseData(List<DrqlBDnZeroFlowData> drqlSusUseData) {
		this.drqlSusUseData = drqlSusUseData;
	}

	public void setDrqlsDnZeroFlowData(List<DrqlBDnZeroFlowData> drqlsDnZeroFlowData) {
		this.drqlsDnZeroFlowData = drqlsDnZeroFlowData;
	}

	public void setDrqlsDnLHFlowData(List<DrqlBDnLHFlowData> drqlsDnLHFlowData) {
		this.drqlsDnLHFlowData = drqlsDnLHFlowData;
	}

}
