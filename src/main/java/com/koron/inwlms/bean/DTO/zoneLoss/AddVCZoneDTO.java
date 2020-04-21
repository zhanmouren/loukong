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
	private Integer zoneType;
	
	/**
	 * 实际主分区编码
	 */
	private String masCode;
	
	/**
	 * 实际辅分区编码
	 */
	private String secCode;
	
	private String createBy;
	
	private String updateBy;

	public String getvZoneNo() {
		return vZoneNo;
	}

	public String getvZoneName() {
		return vZoneName;
	}

	public String getsZoneNos() {
		return sZoneNos;
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

	public Integer getZoneType() {
		return zoneType;
	}

	public void setZoneType(Integer zoneType) {
		this.zoneType = zoneType;
	}

	public String getMasCode() {
		return masCode;
	}

	public String getSecCode() {
		return secCode;
	}

	public void setMasCode(String masCode) {
		this.masCode = masCode;
	}

	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}
