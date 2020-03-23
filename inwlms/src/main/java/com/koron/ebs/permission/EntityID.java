package com.koron.ebs.permission;

import org.koron.ebs.util.Propertible;

public interface EntityID extends Propertible{
	/**
	 * 获取实体的ID
	 * @return
	 */
	public String getId();
	/**
	 * 获取实体的名字
	 * @return
	 */
	public String getName();
}
