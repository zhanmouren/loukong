package com.koron.indicator.bean;

import java.util.List;

/**
 * 参与计算的分区信息Bean
 * @author csh
 * @Date 2020/04/20
 *
 */
public class CalZoneInfos {

	private List<String> firstZoneLists;
	
	private List<String> secondZoneLists;
	
	private List<String> dmaZoneLists;
	
	private List<String> vZoneLists;

	public List<String> getFirstZoneLists() {
		return firstZoneLists;
	}

	public List<String> getSecondZoneLists() {
		return secondZoneLists;
	}

	public List<String> getDmaZoneLists() {
		return dmaZoneLists;
	}

	public List<String> getvZoneLists() {
		return vZoneLists;
	}

	public void setFirstZoneLists(List<String> firstZoneLists) {
		this.firstZoneLists = firstZoneLists;
	}

	public void setSecondZoneLists(List<String> secondZoneLists) {
		this.secondZoneLists = secondZoneLists;
	}

	public void setDmaZoneLists(List<String> dmaZoneLists) {
		this.dmaZoneLists = dmaZoneLists;
	}

	public void setvZoneLists(List<String> vZoneLists) {
		this.vZoneLists = vZoneLists;
	}
	
}
