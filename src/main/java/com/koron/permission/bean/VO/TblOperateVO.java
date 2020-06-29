package com.koron.permission.bean.VO;

/**
 * 返回菜单
 * @author cc
 *
 */
public class TblOperateVO {
	//操作code
    private String opCode;
    //操作名称
    private String opName;
    //标识
    private Integer opFlag;
    //操作状态
    private Integer opStatus;
    
    private java.lang.Integer seq;
    
    private Integer parentmask;
    
    private Integer mask;
    
    private Integer childmask;
    
    
    
	public java.lang.Integer getSeq() {
		return seq;
	}
	public Integer getParentmask() {
		return parentmask;
	}
	public Integer getMask() {
		return mask;
	}
	public Integer getChildmask() {
		return childmask;
	}
	public void setSeq(java.lang.Integer seq) {
		this.seq = seq;
	}
	public void setParentmask(Integer parentmask) {
		this.parentmask = parentmask;
	}
	public void setMask(Integer mask) {
		this.mask = mask;
	}
	public void setChildmask(Integer childmask) {
		this.childmask = childmask;
	}
	public String getOpCode() {
		return opCode;
	}
	public String getOpName() {
		return opName;
	}
	public Integer getOpFlag() {
		return opFlag;
	}
	public Integer getOpStatus() {
		return opStatus;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public void setOpFlag(Integer opFlag) {
		this.opFlag = opFlag;
	}
	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}
    
    
}
