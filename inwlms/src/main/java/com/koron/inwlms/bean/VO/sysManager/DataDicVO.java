package com.koron.inwlms.bean.VO.sysManager;

import java.sql.Timestamp;
import java.util.List;


/**
 * 数据字典主表VO
 *
* @Author xiaozhan
* @Date 2020.03.18
*/
public class DataDicVO {
	//数据字典Id
	private Integer dicId;
	//数据字典标识
	private String dicParent;
	//数据字典名称
	private String dicName;
	//数据字典备注
	private String dicRemark;
   
	//数据字典明细键
	private String dicKey;
	//数据字典明细值
	private String dicValue;
	//数据字典明细顺序
	private Integer dicSeq;
	
	public String getDicParent() {
		return dicParent;
	}
	public void setDicParent(String dicParent) {
		this.dicParent = dicParent;
	}
	public String getDicKey() {
		return dicKey;
	}
	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}
	public String getDicValue() {
		return dicValue;
	}
	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}
	public Integer getDicSeq() {
		return dicSeq;
	}
	public void setDicSeq(Integer dicSeq) {
		this.dicSeq = dicSeq;
	}
	//创建人
	private String createBy;
	//创建时间
     private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;

	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicRemark() {
		return dicRemark;
	}
	public void setDicRemark(String dicRemark) {
		this.dicRemark = dicRemark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
