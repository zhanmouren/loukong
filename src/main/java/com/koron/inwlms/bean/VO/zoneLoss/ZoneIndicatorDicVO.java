package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 分区指标数据字典VO
 * @author csh
 * @Date 2020/04/15
 */
public class ZoneIndicatorDicVO {

	private Integer timeType;
	
	private String itemCode;
	
	private String itemName;
	
	private String itemNameEn;

	public String getItemCode() {
		return itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemNameEn() {
		return itemNameEn;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemNameEn(String itemNameEn) {
		this.itemNameEn = itemNameEn;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	
	
}
