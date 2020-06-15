package com.koron.inwlms.bean.VO.zoneLoss;

/**
 * 合理夜间流量VO
 * @author csh
 * @Date 2020/04/16
 *
 */
public class LegitimateNightUseVO {

	private Integer id;
	private String code;
	private String name;
	private Double flow;
	public Integer getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public Double getFlow() {
		return flow;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setFlow(Double flow) {
		this.flow = flow;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
