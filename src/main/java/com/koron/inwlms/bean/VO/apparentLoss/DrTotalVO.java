package com.koron.inwlms.bean.VO.apparentLoss;

/**
 * 诊断报告总VO
 * @author lu
 *
 */
public class DrTotalVO {

	/**
	 * 总体分析数据
	 */
	private DrTotalAnalysisDataVO drTotalAnalysisDataVO;
	
	/**
	 * 水表现状数据
	 */
	private DrCurrentMeterDataVO drCurrentMeterDataVO;
	
	/**
	 * 表计管理数据
	 */
	private DrMeterManageVO drMeterManageVO;
	
	/**
	 * 水表分析数据
	 */
	private DrMeterAnaDataVO drMeterAnaDataVO;
	
	/**
	 * 报告处理建议
	 */
	private DrDealAdviseVO drDealAdviseVO;

	public DrTotalAnalysisDataVO getDrTotalAnalysisDataVO() {
		return drTotalAnalysisDataVO;
	}

	public void setDrTotalAnalysisDataVO(DrTotalAnalysisDataVO drTotalAnalysisDataVO) {
		this.drTotalAnalysisDataVO = drTotalAnalysisDataVO;
	}

	public DrCurrentMeterDataVO getDrCurrentMeterDataVO() {
		return drCurrentMeterDataVO;
	}

	public void setDrCurrentMeterDataVO(DrCurrentMeterDataVO drCurrentMeterDataVO) {
		this.drCurrentMeterDataVO = drCurrentMeterDataVO;
	}

	public DrMeterManageVO getDrMeterManageVO() {
		return drMeterManageVO;
	}

	public void setDrMeterManageVO(DrMeterManageVO drMeterManageVO) {
		this.drMeterManageVO = drMeterManageVO;
	}

	public DrMeterAnaDataVO getDrMeterAnaDataVO() {
		return drMeterAnaDataVO;
	}

	public void setDrMeterAnaDataVO(DrMeterAnaDataVO drMeterAnaDataVO) {
		this.drMeterAnaDataVO = drMeterAnaDataVO;
	}

	public DrDealAdviseVO getDrDealAdviseVO() {
		return drDealAdviseVO;
	}

	public void setDrDealAdviseVO(DrDealAdviseVO drDealAdviseVO) {
		this.drDealAdviseVO = drDealAdviseVO;
	}
	
}
