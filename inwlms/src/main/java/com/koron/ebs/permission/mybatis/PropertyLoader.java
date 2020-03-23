package com.koron.ebs.permission.mybatis;

import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PropertyLoader {
	private String param;

	private HashMap<String, Object> map = new HashMap<>();

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
		map = new Gson().fromJson(param,new TypeToken<HashMap<String, Object>>() {}.getType());
	}

	public String getProperty(String key) {
		if (map.get(key) == null)
			return null;
		return map.get(key).toString();
	}

	public void setProperty(String key, String value) {
		map.put(key, value);
	}
	public HashMap<String,Object> getMap(){
		return map;
	}
}
