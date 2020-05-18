package com.koron.inwlms.bean.DTO.intellectPartition;

import java.util.List;

/**
 * 
 * @author 刘刚
 *
 */
public class KafkaReturnData {
	
	/**
	 * 方案总表编码
	 */
	private String total_plan_code;
	/**
	 * 分区数量上限
	 */
	private Integer num_up;
	/**
	 * 分区数量下限
	 */
	private Integer num_down;
	/**
	 * 分区个数
	 */
	private Integer partition_no;
	/**
	 * 
	 */
	private Double tightness;
	/**
	 * 
	 */
	private Double economy;
	/**
	 * 
	 */
	private Integer flow_num;
	/**
	 * 
	 */
	private Integer is_end;
	/**
	 * 分区管线
	 */
	private List<ZoneSchemeData> partition_detail;
	/**
	 * 边界管线编码
	 */
	private List<String> border;

	public String getTotal_plan_code() {
		return total_plan_code;
	}

	public void setTotal_plan_code(String total_plan_code) {
		this.total_plan_code = total_plan_code;
	}

	public Integer getNum_up() {
		return num_up;
	}

	public void setNum_up(Integer num_up) {
		this.num_up = num_up;
	}

	public Integer getNum_down() {
		return num_down;
	}

	public void setNum_down(Integer num_down) {
		this.num_down = num_down;
	}

	public Integer getPartition_no() {
		return partition_no;
	}

	public void setPartition_no(Integer partition_no) {
		this.partition_no = partition_no;
	}

	public Double getTightness() {
		return tightness;
	}

	public void setTightness(Double tightness) {
		this.tightness = tightness;
	}

	public Double getEconomy() {
		return economy;
	}

	public void setEconomy(Double economy) {
		this.economy = economy;
	}

	public Integer getFlow_num() {
		return flow_num;
	}

	public void setFlow_num(Integer flow_num) {
		this.flow_num = flow_num;
	}

	public Integer getIs_end() {
		return is_end;
	}

	public void setIs_end(Integer is_end) {
		this.is_end = is_end;
	}

	public List<ZoneSchemeData> getPartition_detail() {
		return partition_detail;
	}

	public void setPartition_detail(List<ZoneSchemeData> partition_detail) {
		this.partition_detail = partition_detail;
	}

	public List<String> getBorder() {
		return border;
	}

	public void setBorder(List<String> border) {
		this.border = border;
	}
	
	
	

}
