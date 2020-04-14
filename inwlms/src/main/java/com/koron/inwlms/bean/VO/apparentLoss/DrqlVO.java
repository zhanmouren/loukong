package com.koron.inwlms.bean.VO.apparentLoss;

import java.util.List;
import java.util.Map;

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
	private List<Map<Object,Object>> drqlBDnZeroFlowData;
	
	/**
	 * 大口径低流量、过载
	 */
	private List<Map<Object,Object>> drqlBDnLHFlowData;
	
	/**
	 * 大口径用水异常
	 */
	private List<Map<Object,Object>> drqlBDnErrFlowData;
	
	/**
	 * 用水性质可疑
	 */
	private List<Map<Object,Object>> drqlSusUseData;
	
	/**
	 * 小口径零流量
	 */
	private List<Map<Object,Object>> drqlsDnZeroFlowData;
	
	/**
	 * 小口径低流量、过载
	 */
	private List<Map<Object,Object>> drqlsDnLHFlowData;

	public List<Map<Object, Object>> getDrqlBDnZeroFlowData() {
		return drqlBDnZeroFlowData;
	}

	public List<Map<Object, Object>> getDrqlBDnLHFlowData() {
		return drqlBDnLHFlowData;
	}

	public List<Map<Object, Object>> getDrqlBDnErrFlowData() {
		return drqlBDnErrFlowData;
	}

	public List<Map<Object, Object>> getDrqlSusUseData() {
		return drqlSusUseData;
	}

	public List<Map<Object, Object>> getDrqlsDnZeroFlowData() {
		return drqlsDnZeroFlowData;
	}

	public List<Map<Object, Object>> getDrqlsDnLHFlowData() {
		return drqlsDnLHFlowData;
	}

	public void setDrqlBDnZeroFlowData(List<Map<Object, Object>> drqlBDnZeroFlowData) {
		this.drqlBDnZeroFlowData = drqlBDnZeroFlowData;
	}

	public void setDrqlBDnLHFlowData(List<Map<Object, Object>> drqlBDnLHFlowData) {
		this.drqlBDnLHFlowData = drqlBDnLHFlowData;
	}

	public void setDrqlBDnErrFlowData(List<Map<Object, Object>> drqlBDnErrFlowData) {
		this.drqlBDnErrFlowData = drqlBDnErrFlowData;
	}

	public void setDrqlSusUseData(List<Map<Object, Object>> drqlSusUseData) {
		this.drqlSusUseData = drqlSusUseData;
	}

	public void setDrqlsDnZeroFlowData(List<Map<Object, Object>> drqlsDnZeroFlowData) {
		this.drqlsDnZeroFlowData = drqlsDnZeroFlowData;
	}

	public void setDrqlsDnLHFlowData(List<Map<Object, Object>> drqlsDnLHFlowData) {
		this.drqlsDnLHFlowData = drqlsDnLHFlowData;
	}


}
