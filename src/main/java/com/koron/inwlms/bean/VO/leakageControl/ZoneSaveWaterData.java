package com.koron.inwlms.bean.VO.leakageControl;

public class ZoneSaveWaterData {
	/**
	 * 分区名
	 */
	private String name;
	/**
	 * 节约水量
	 */
	private Double saveWater;
	/**
	 * 节约成本
	 */
	private Double saveCost;
	/**
	 * 占比
	 */
	private Double proportion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSaveWater() {
		return saveWater;
	}

	public void setSaveWater(Double saveWater) {
		this.saveWater = saveWater;
	}

	public Double getSaveCost() {
		return saveCost;
	}

	public void setSaveCost(Double saveCost) {
		this.saveCost = saveCost;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	
	

}
