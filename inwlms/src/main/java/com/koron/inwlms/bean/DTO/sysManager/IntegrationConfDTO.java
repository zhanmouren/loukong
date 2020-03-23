package com.koron.inwlms.bean.DTO.sysManager;


import java.util.List;


/**
 * 集成bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

public class IntegrationConfDTO {
   //集成配置Id
	private Integer inteConfId;
	//集成配置状态
	private Integer status;
	//对方系统名称
	private String sysName;
	//对方JDBC
	private String otherJDBC;
	
	//表格映射明细列表
	private List<TableMapperDTO> tableMappeList;

	public Integer getInteConfId() {
		return inteConfId;
	}

	public void setInteConfId(Integer inteConfId) {
		this.inteConfId = inteConfId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getOtherJDBC() {
		return otherJDBC;
	}

	public void setOtherJDBC(String otherJDBC) {
		this.otherJDBC = otherJDBC;
	}

	public List<TableMapperDTO> getTableMappeList() {
		return tableMappeList;
	}

	public void setTableMappeList(List<TableMapperDTO> tableMappeList) {
		this.tableMappeList = tableMappeList;
	}
	
}
