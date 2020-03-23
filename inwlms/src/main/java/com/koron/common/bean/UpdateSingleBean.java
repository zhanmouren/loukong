package com.koron.common.bean;

public class UpdateSingleBean {
	private Integer id;
	private String key;
	private String value;
	private String[] valids;
	public String[] getValids() {
		return valids;
	}
	public void setValids(String[] valids) {
		this.valids = valids;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 校验是否有效字段
	 * @return
	 */
	public boolean validKey(){
		for (String valid : valids) {
			if(valid.equals(key)){
				return true;
			}	
		}
		return false;
	}
}
