package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;



/**
 * 数据字典主表bean
 *
* @Author xiaozhan
* @Date 2020.03.18
*/

public class DataDicDTO extends BaseDTO{
	//装数据字典的标识parent的List集合
	private List<String> dicParentList;
	//装数据字典的的Id集合
	private List<Integer> dicIdList;
	
	//key 集合
	private List<String > dicKeyList;
	
	
	public List<String> getDicKeyList() {
		return dicKeyList;
	}
	public void setDicKeyList(List<String> dicKeyList) {
		this.dicKeyList = dicKeyList;
	}
	public List<Integer> getDicIdList() {
		return dicIdList;
	}
	public void setDicIdList(List<Integer> dicIdList) {
		this.dicIdList = dicIdList;
	}
	public List<String> getDicParentList() {
		return dicParentList;
	}
	public void setDicParentList(List<String> dicParentList) {
		this.dicParentList = dicParentList;
	}
	//数据字典Id
	private Integer dicId;
	//数据字典标识
	private String dicParent;
	//数据字典中文名称
	private String dicCn;
	//数据字典英文名称
	private String dicEn;
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
	       
	//数据字典明细键
		private String dicKey;
		//数据字典明细值
		private String dicValue;
		//数据字典明细顺序
		private Integer dicSeq;
	    //装key Value的List列表
		private	List<DataDicDTO> dataDicDTOList;

	 public List<DataDicDTO> getDataDicDTOList() {
			return dataDicDTOList;
		}
		public void setDataDicDTOList(List<DataDicDTO> dataDicDTOList) {
			this.dataDicDTOList = dataDicDTOList;
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
	
	public String getDicParent() {
		return dicParent;
	}
	public void setDicParent(String dicParent) {
		this.dicParent = dicParent;
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
	
}
