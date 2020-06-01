package com.koron.permission.bean.VO;

/**
 * date:2020/05/29
 * @author 小詹
 * description:用户权限列表DTO
 *
 */
public class userOperationVO {
     //用户Code
	private  String userCode;
	//用户权限标识编码
	private  String opCode;
	//权限状态
	private Integer opStatus;
	//权限权重
	private Integer opWeight;
	//权限名称
	private String opName;
	//权限标识
	private Integer opFlag;
	//数字编号
	private String seq;
	//父级掩码
	private String parentMask;
	//掩码
	private String mask;
	//子级掩码
	private String childmask;
	//树的类型
	private Integer type;
	
	public String getUserCode() {
		return userCode;
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
	public String getOpName() {
		return opName;
	}
	public Integer getOpFlag() {
		return opFlag;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setOpFlag(Integer opFlag) {
		this.opFlag = opFlag;
	}
	public String getSeq() {
		return seq;
	}
	public String getParentMask() {
		return parentMask;
	}
	public String getMask() {
		return mask;
	}
	public String getChildmask() {
		return childmask;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public void setParentMask(String parentMask) {
		this.parentMask = parentMask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public void setChildmask(String childmask) {
		this.childmask = childmask;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
