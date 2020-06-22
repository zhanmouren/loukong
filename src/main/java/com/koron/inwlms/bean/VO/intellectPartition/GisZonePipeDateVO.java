package com.koron.inwlms.bean.VO.intellectPartition;

public class GisZonePipeDateVO {
	
	/**
	 * 管道点号（本点号）
	 */
	private String pip_p;
	/**
	 * 管道连接点号（上点号）
	 */
	private String pip_p_pre;
	/**
	 * 
	 */
	private String pip_obj_code;
	/**
	 * 管长
	 */
	private Double pip_len;
	/**
	 * 本节点坐标X
	 */
	private Double point_a;
	/**
	 * 本节点坐标Y
	 */
	private Double point_b;
	/**
	 * 本节点高程
	 */
	private Double point_c;
	/**
	 * 上节点高程
	 */
	private Double point_z;
	/**
	 * 管线是否有已关闭阀门
	 */
	private String pip_value;
	/**
	 * 管线是否已经安装水表
	 */
	private Integer pip_gauge;
	/**
	 * 管线是否经过河流等
	 */
	private Integer pip_river;
	/**
	 * 管线是否经过铁路
	 */
	private Integer railway;
	/**
	 * 管线是否经过行政区域
	 */
	private Integer administration;
	
	private Integer layerOne;
	
	private Integer layerTwo;
	
	private Integer layerThree;
	
	private Integer layerFour;
	/**
	 * 管径大小
	 */
	private Integer pip_d;
	/**
	 * 管径单价
	 */
	private Double pip_price;
	
	private Integer is_border;

	public String getPip_p() {
		return pip_p;
	}

	public void setPip_p(String pip_p) {
		this.pip_p = pip_p;
	}

	public String getPip_p_pre() {
		return pip_p_pre;
	}

	public void setPip_p_pre(String pip_p_pre) {
		this.pip_p_pre = pip_p_pre;
	}

	public String getPip_obj_code() {
		return pip_obj_code;
	}

	public void setPip_obj_code(String pip_obj_code) {
		this.pip_obj_code = pip_obj_code;
	}

	public Double getPip_len() {
		return pip_len;
	}

	public void setPip_len(Double pip_len) {
		this.pip_len = pip_len;
	}

	public Double getPoint_a() {
		return point_a;
	}

	public void setPoint_a(Double point_a) {
		this.point_a = point_a;
	}

	public Double getPoint_b() {
		return point_b;
	}

	public void setPoint_b(Double point_b) {
		this.point_b = point_b;
	}

	public Double getPoint_c() {
		return point_c;
	}

	public void setPoint_c(Double point_c) {
		this.point_c = point_c;
	}

	public Double getPoint_z() {
		return point_z;
	}

	public void setPoint_z(Double point_z) {
		this.point_z = point_z;
	}

	public String getPip_value() {
		return pip_value;
	}

	public void setPip_value(String pip_value) {
		this.pip_value = pip_value;
	}

	public Integer getPip_gauge() {
		return pip_gauge;
	}

	public void setPip_gauge(Integer pip_gauge) {
		this.pip_gauge = pip_gauge;
	}

	public Integer getPip_river() {
		return pip_river;
	}

	public void setPip_river(Integer pip_river) {
		this.pip_river = pip_river;
	}

	public Integer getRailway() {
		return railway;
	}

	public void setRailway(Integer railway) {
		this.railway = railway;
	}

	public Integer getAdministration() {
		return administration;
	}

	public void setAdministration(Integer administration) {
		this.administration = administration;
	}

	public Integer getLayerOne() {
		return layerOne;
	}

	public void setLayerOne(Integer layerOne) {
		this.layerOne = layerOne;
	}

	public Integer getLayerTwo() {
		return layerTwo;
	}

	public void setLayerTwo(Integer layerTwo) {
		this.layerTwo = layerTwo;
	}

	public Integer getLayerThree() {
		return layerThree;
	}

	public void setLayerThree(Integer layerThree) {
		this.layerThree = layerThree;
	}

	public Integer getLayerFour() {
		return layerFour;
	}

	public void setLayerFour(Integer layerFour) {
		this.layerFour = layerFour;
	}

	public Integer getPip_d() {
		return pip_d;
	}

	public void setPip_d(Integer pip_d) {
		this.pip_d = pip_d;
	}

	public Double getPip_price() {
		return pip_price;
	}

	public void setPip_price(Double pip_price) {
		this.pip_price = pip_price;
	}

	public Integer getIs_border() {
		return is_border;
	}

	public void setIs_border(Integer is_border) {
		this.is_border = is_border;
	}
	
	

}
