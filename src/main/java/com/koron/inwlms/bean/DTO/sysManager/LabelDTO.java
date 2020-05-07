package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

/**
 * 标签bean
* @Author lzy
* @Date 2020.04.17
*/

public class LabelDTO {

	//标签id
	private Integer id;
	//简体中文标签名
	private String cn;
	//繁体中文标签名
	private String cnHK;
	//英文标签名
	private String en;
	//标签编码
	private String code;
	//标签类型
	private String type;
	//备注（用途）
	private String remark;
	//创建人
	private String createBy;
	//修改人
	private String updateBy;
	//创建时间
	private Timestamp createTime;
	//修改时间
	private Timestamp updateTime;
	//标签Code List
	private List<String> labelCodeList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCnHK() {
		return cnHK;
	}

	public void setCnHK(String cnHK) {
		this.cnHK = cnHK;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getLabelCodeList() {
		return labelCodeList;
	}

	public void setLabelCodeList(List<String> labelCodeList) {
		this.labelCodeList = labelCodeList;
	}

	
}
