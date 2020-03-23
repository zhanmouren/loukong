package com.koron.ebs.permission;

import java.util.List;
/**
 * 角色
 * @author 方志文
 *
 */
public interface Role extends EntityID {
	/**
	 * 获取角色相关的所有操作
	 * @return
	 */
	public List<Operation> getOperation();
}
