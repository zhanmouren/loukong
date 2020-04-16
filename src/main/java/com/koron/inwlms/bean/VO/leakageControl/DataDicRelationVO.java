package com.koron.inwlms.bean.VO.leakageControl;

/**
 * 
 * @author 刘刚
 *
 */
public class DataDicRelationVO {

	private Integer id;
	
	private String parentKey;
	
	private String childKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getChildKey() {
		return childKey;
	}

	public void setChildKey(String childKey) {
		this.childKey = childKey;
	}
	
	
	
}
