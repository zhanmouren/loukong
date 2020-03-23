package com.koron.ebs.permission.mybatis;

import com.koron.ebs.permission.EntityID;

public class EntID implements EntityID {
	private String id;
	private String name;
	private int type;
	private PropertyLoader loader = new PropertyLoader();
	
	public EntID() {
		
	}

	public EntID(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String getProperty(String key) {
		return loader.getProperty(key);
	}

	@Override
	public void setProperty(String key, String value) {
		loader.setProperty(key, value);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "EntID [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

	public String getParam() {
		return loader.getParam();
	}

	public void setParam(String param) {
		if(param != null && !param.isEmpty())
		loader.setParam(param);
	}
}
