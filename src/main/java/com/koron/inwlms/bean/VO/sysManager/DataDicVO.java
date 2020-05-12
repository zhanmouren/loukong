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
	//数据字典中文名称
   private String dicCn;
	//数据字典英文名称
	private String dicEn;
	//数据字典繁体名称
	private String dicTc;
	//数据字典备注
	private String dicRemark;
   
	//数据字典明细键
	private String dicKey;
	//数据字典明细中值
	private String dicValue;
	//数据字典明细英文值
	private String dicEnValue;
	//数据字典明细繁体值
	private String dicTcValue;
	//数据字典明细备注
	private String dicDetRemark;
	//数据字典明细顺序
	private Integer dicSeq;
	//数据字典的类型  (type 0代表系统的数据字典，1代表是用户自建的数据字典)
		private Integer dicType;
	
	
	public Integer getDicType() {
			return dicType;
		}
		public void setDicType(Integer dicType) {
			this.dicType = dicType;
		}
	public String getDicDetRemark() {
		return dicDetRemark;
	}
	public void setDicDetRemark(String dicDetRemark) {
		this.dicDetRemark = dicDetRemark;
	}
	public String getDicEnValue() {
		return dicEnValue;
	}
	public String getDicTcValue() {
		return dicTcValue;
	}
	public void setDicEnValue(String dicEnValue) {
		this.dicEnValue = dicEnValue;
	}
	public void setDicTcValue(String dicTcValue) {
		this.dicTcValue = dicTcValue;
	}
	public String getDicTc() {
		return dicTc;
	}
	public void setDicTc(String dicTc) {
		this.dicTc = dicTc;
	}
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
	
	
	public String getDicCn() {
		return dicCn;
	}
	public void setDicCn(String dicCn) {
		this.dicCn = dicCn;
	}
	public String getDicEn() {
		return dicEn;
	}
	public void setDicEn(String dicEn) {
		this.dicEn = dicEn;
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
