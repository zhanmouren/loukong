package com.koron.inwlms.bean.DTO.zoneLoss;

/**
 * 添加虚拟分区（合并）DTO
 * 
 * @author csh
 * @Date 2020/04.14
 *
 */
public class AddVCZoneDTO {

	/**
	 * 虚拟分区编号
	 */
	private String vZoneNo;

	/**
	 * 虚拟分区名称
	 */
	private String vZoneName;

	/**
	 * 子分区编号字符串集合，用逗号隔开
	 */
	private String sZoneNos;

	/**
	 * 虚拟分区类型，1-加，2-减
	 */
	private String zoneType;

	public String getvZoneNo() {
		return vZoneNo;
	}

	public String getvZoneName() {
		return vZoneName;
	}

	public String getsZoneNos() {
		return sZoneNos;
	}

	public String getZoneType() {
		return zoneType;
	}

	public void setvZoneNo(String vZoneNo) {
		this.vZoneNo = vZoneNo;
	}

	public void setvZoneName(String vZoneName) {
		this.vZoneName = vZoneName;
	}

	public void setsZoneNos(String sZoneNos) {
		this.sZoneNos = sZoneNos;
	}

	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}

}
