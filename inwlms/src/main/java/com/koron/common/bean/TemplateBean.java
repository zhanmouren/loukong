package com.koron.common.bean;
public class TemplateBean implements IdentityBean {
	private Integer id;
	/**
	 * 模块管理ID
	 */
	private String key;
	/**
	 * 模块管理名称属性
	 */
	private String name;
	/**
	 * 模块管理内容属性
	 */
	private String content;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getDatasource() {
		return datasource;
	}

	public void setDatasource(Integer datasource) {
		this.datasource = datasource;
	}




	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourcekey() {
		return sourcekey;
	}

	public void setSourcekey(String sourcekey) {
		this.sourcekey = sourcekey;
	}

	/**
	 * 数据来源
	 */
	private Integer datasource;
	/**
	 * 来源key
	 */
	private String sourcekey;
	
}