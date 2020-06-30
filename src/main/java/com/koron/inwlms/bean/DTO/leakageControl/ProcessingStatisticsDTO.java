package com.koron.inwlms.bean.DTO.leakageControl;

/**
 * 
 * @author 刘刚
 * @date 2020-04-02
 */
public class ProcessingStatisticsDTO {

	/**
	 * 一级分区
	 */
	private String firstPartion;
	
	/**
	 * 二级分区
	 */
	private String seconPartion;
	
	/**
	 * DMA编码
	 */
	private String dmaCode;
	
	private Integer type;
	
	private String startTime;
	
	private String endTime;

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFirstPartion() {
		return firstPartion;
	}

	public void setFirstPartion(String firstPartion) {
		this.firstPartion = firstPartion;
	}

	public String getSeconPartion() {
		return seconPartion;
	}

	public void setSeconPartion(String seconPartion) {
		this.seconPartion = seconPartion;
	}

	public String getDmaCode() {
		return dmaCode;
	}

	public void setDmaCode(String dmaCode) {
		this.dmaCode = dmaCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
