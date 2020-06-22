package com.koron.inwlms.bean.VO.zoneLoss;

import java.util.List;

/**
 * 虚拟分区列表VO
 * @author lzy
 * @Date 2020.06.22
 */
public class VirtualZoneVO {

	//分区编号
	private String zoneNo;
	//父级分区编码
	private String baseRegion;
	//分区名称
	private String name;

	private List<String> cutRegion;
	
	public String getZoneNo() {
		return zoneNo;
	}

	public void setZoneNo(String zoneNo) {
		this.zoneNo = zoneNo;
	}

	public String getBaseRegion() {
		return baseRegion;
	}

	public void setBaseRegion(String baseRegion) {
		this.baseRegion = baseRegion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCutRegion() {
		return cutRegion;
	}

	public void setCutRegion(List<String> cutRegion) {
		this.cutRegion = cutRegion;
	}

}
