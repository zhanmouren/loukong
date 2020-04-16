package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 定位信息VO
 * @author csh
 * @Date 2020/04/09
 */
public class PositionInfoVO {

	/**
	 * 坐标数组
	 */
	private String position;
	
	/**
	 * 坐标类型（0-点，1-线，2-面）
	 */
	private Integer positionType;

	public String getPosition() {
		return position;
	}

	public Integer getPositionType() {
		return positionType;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}
	
}
