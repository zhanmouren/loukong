package com.koron.ebs.permission.mybatis;

import java.util.ArrayList;
import java.util.List;

import com.koron.ebs.permission.*;
/**
 * 账户
 * @author fangzw
 *
 */
public class SPIAccount implements Account{
	/**
	 * 标识
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 角色的KEY.
	 * 为null即为未加载相关数据,长度为0为无角色
	 */
	private String[] roles;
	/**
	 * 群组的KEY.
	 * 为null即为未加载相关数据,长度为0为无群组
	 */
	private String[] groups;
	
	private PropertyLoader loader = new PropertyLoader();
	@Override
	public List<Role> getRole() {
		List<Role> list = new ArrayList<Role>();
		if(roles == null)
		{
			List<EntityID> ents = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_ACCOUNT_ROLE_INT);
			roles = new String[ents.size()];
			for (int i = 0; i < roles.length; i++) {
				roles[i] = ents.get(i).getId();
			}
		}
		for (String role : roles) {
			Role r = Permission.getInstance().getRole(role);
			if(r!=null)
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

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Group> getGroup() {
		if(groups == null)
		{
			List<EntityID> ents = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_ACCOUNT_GROUP_INT);
			groups = new String[ents.size()];
			for (int i = 0; i < groups.length; i++) {
				groups[i] = ents.get(i).getId();
			}
		}
		List<Group> list = new ArrayList<Group>();
		for (String group : groups) {
			Group r = Permission.getInstance().getGroup(group);
			if(r!=null)
				list.add(r);
		}
		return list;
	}

	public String getParam() {
		return loader.getParam();
	}

	public void setParam(String param) {
		loader.setParam(param);
	}

	@Override
	public String getProperty(String key) {
		return loader.getProperty(key);
	}

	@Override
	public void setProperty(String key, String value) {
		loader.setProperty(key, value);
	}
}