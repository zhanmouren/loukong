package com.koron.inwlms.bean.VO.intellectPartition;

public class GisConfigDetVO {
	
	private Integer id;
	/**
	 * 模块名
	 */
	private String moduleNo;
	/**
	 * 组件名
	 */
	private String comNo;
	
	private String param;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getComNo() {
		return comNo;
	}

	public void setComNo(String comNo) {
		this.comNo = comNo;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	

}
