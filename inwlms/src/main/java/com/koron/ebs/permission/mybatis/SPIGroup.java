package com.koron.ebs.permission.mybatis;

import com.koron.ebs.permission.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 群级
 * 
 * @author fangzw
 */

public class SPIGroup implements Group {
	/**
	 * 标识
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 角色的KEY
	 */
	private String[] roles;
	
	private PropertyLoader loader = new PropertyLoader();

	/**
	 * @return
	 * @see com.koron.ebs.permission.mybatis.PropertyLoader#getParam()
	 */
	public String getParam() {
		return loader.getParam();
	}

	/**
	 * @param param
	 * @see com.koron.ebs.permission.mybatis.PropertyLoader#setParam(java.lang.String)
	 */
	public void setParam(String param) {
		loader.setParam(param);
	}

	@Override
	public List<Role> getRole() {
		if (roles == null) {
			List<EntityID> ents = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_GROUP_ROLE_INT);
			roles = new String[ents.size()];
			for (int i = 0; i < roles.length; i++) {
				roles[i] = ents.get(i).getId();
			}
		}

		List<Role> list = new ArrayList<Role>();
		for (String role : roles) {
			Role r = Permission.getInstance().getRole(role);
			if (r != null)
				list.add(r);
		}
		return list;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getProperty(String key) {
		return loader.getProperty(key);
	}

	@Override
	public void setProperty(String key, String value) {
		loader.setProperty(key, value);
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
