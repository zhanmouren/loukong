package com.koron.ebs.permission.mybatis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.ebs.permission.*;

import java.util.HashMap;
import java.util.List;


public class SPIOperation implements Operation {
	/**
	 * 标识
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 同位操作的KEY
	 */
	private String peer;
	/**
	 * 父级操作的KEY
	 * NULL为未加载,""为加载后无数据
	 */
	private String parent;
	/**
	 * 拒绝的RULE.
	 * NULL为未加载,""为加载后无数据
	 */
	private String deny;
	/**
	 * 接受的RULE
	 * NULL为未加载,""为加载后无数据
	 */
	private String accept;
	private PropertyLoader loader = new PropertyLoader();

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

	@Override
	public Operation getParent() {
		if(parent == null)
		{
			List<EntityID> list = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_OPERATION_PARENT_INT);
			if(list.isEmpty())
				parent = "";
			else
				parent = list.get(0).getId();
		}
		if(parent.isEmpty()) return null;
		Operation r = new SPILoader().get(parent, SPIOperation.class, ResourceLoader.ENTITY_OPERATION_INT);
		if (r != null)
			return r;
		return null;
	}

	@Override
	public Rule getDeny() {
		if(deny == null)
		{
			List<EntityID> list = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_OPERATION_DENY_INT);
			if(list.isEmpty())
				deny = "";
			else
				deny = list.get(0).getId();
		}
		if(deny.isEmpty()) return null;
		Rule r = new SPILoader().get(deny, Rule.class, ResourceLoader.ENTITY_RULE_INT);
		if (r != null)
			return r;
		return null;
	}

	@Override
	public Rule getAccept() {
		if(accept == null)
		{
			List<EntityID> list = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_OPERATION_ACCEPT_INT);
			if(list.isEmpty())
				accept = "";
			else
				accept = list.get(0).getId();
		}
		if(accept.isEmpty()) return null;
		Rule r = new SPILoader().get(accept, Rule.class, ResourceLoader.ENTITY_RULE_INT);
		if (r != null)
			return r;
		return null;
	}

	@Override
	public Operation getPeerOperation() {
		if(peer == null)
		{
			List<EntityID> list = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_OPERATION_PEER_INT);
			if(list.isEmpty())
				peer = "";
			else
				peer = list.get(0).getId();
		}
		if(peer.isEmpty()) return null;
		Operation r = new SPILoader().get(peer, SPIOperation.class, ResourceLoader.ENTITY_OPERATION_INT);
		if (r != null)
			return r;
		return null;
	}

	public String getPeer() {
		return peer;
	}

	public void setPeer(String peer) {
		this.peer = peer;
	}

	public String getAccess() {
		return accept;
	}

	public void setAccess(String access) {
		this.accept = access;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setDeny(String deny) {
		this.deny = deny;
	}
	
	public String getParam() {
		return loader.getParam();
	}

	public void setParam(String param) {
		if(param != null && !param.isEmpty())
			loader.setParam(param);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SPIOperation [id=" + id + ", name=" + name + ", peer=" + peer + ", parent=" + parent + ", deny=" + deny + ", accept=" + accept + "]";
	}
	
}
