package com.koron.ebs.permission;

import java.util.HashMap;
import java.util.Map;

public class TemplateRule implements Rule {
	/**
	 * 属性
	 */
	private HashMap<String,String> properties = new HashMap<>();
	@Override
	public String getProperty(String property) {
		return properties.get(property);
	}

	@Override
	public void setProperty(String property, String value) {
		properties.put(property, value);
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean inspect(User user, Role role, Operation operation, Map<String, Object> data) {
		return false;
	}
}
