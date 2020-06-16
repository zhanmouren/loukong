package com.koron.permission.bean.VO;

/**
 * date: 2020/06/04
 * @author xiaozhan
 * description:操作OpVO
 *
 */
public class TblOperationVO {
	//操作名称
	private String opName;
	//标识 1 菜单 2 按钮
	private Integer opFlag;
	//编码
	private String opCode;
	//状态
	private Integer opStatus;
	//权重
	private Integer opWeight;
	public String getOpName() {
		return opName;
	}
	public Integer getOpFlag() {
		return opFlag;
	}
	public String getOpCode() {
		return opCode;
	}
	public Integer getOpStatus() {
		return opStatus;
	}
	public Integer getOpWeight() {
		return opWeight;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setOpFlag(Integer opFlag) {
		this.opFlag = opFlag;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}
	public void setOpWeight(Integer opWeight) {
		this.opWeight = opWeight;
	}
	
	
	

}
