package com.koron.inwlms.bean.DTO.intellectPartition;

import java.util.List;

/**
 * 
 * @author 刘刚
 *
 */
public class GisZoneData {
	/**
	 * 分区总方案编码
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
	 * 管道信息
	 */
	private List<GisZonePipeData> pipe_info;
	
	

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

	public List<GisZonePipeData> getPipe_info() {
		return pipe_info;
	}

	public void setPipe_info(List<GisZonePipeData> pipe_info) {
		this.pipe_info = pipe_info;
	}


	
	
	

}
