package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;



/**
 * 数据字典主表bean
 *
* @Author xiaozhan
* @Date 2020.03.18
*/

public class DataDicDTO {
	//数据字典Id
	private Integer dicId;
	//数据字典标识
	private String dicFlag;
	//数据字典名称
	private String dicName;
	//数据字典备注
	private String dicRemark;
	//创建人
	private String createBy;
	//创建时间
     private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	       
	//数据字典List明细
	private List<DataDicDetDTO> dictionaryDetList;
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
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	public String getDicFlag() {
		return dicFlag;
	}
	public void setDicFlag(String dicFlag) {
		this.dicFlag = dicFlag;
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
	public List<DataDicDetDTO> getDictionaryDetList() {
		return dictionaryDetList;
	}
	public void setDictionaryDetList(List<DataDicDetDTO> dictionaryDetList) {
		this.dictionaryDetList = dictionaryDetList;
	}
}
