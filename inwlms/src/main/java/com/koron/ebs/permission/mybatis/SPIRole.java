package com.koron.ebs.permission.mybatis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.ebs.permission.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 角色
 * 
 * @author fangzw
 * 
 */
public class SPIRole implements Role {
	/**
	 * 标识
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 所拥有的操作
	 */
	private String[] operations;
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
	public List<Operation> getOperation() {
		if (operations == null) {
			List<EntityID> ents = new SPILoader().getRelation(getId(), ResourceLoader.RELATION_ROLE_OPERATION_INT);
			operations = new String[ents.size()];
			for (int i = 0; i < operations.length; i++) {
				operations[i] = ents.get(i).getId();
			}
		}

		List<Operation> list = new ArrayList<Operation>();
		Permission permission = Permission.getInstance();
		for (String operation : operations) {
			Operation r = permission.getOperation(operation);
			// Operation r = new SPILoader().get(operation, SPIOperation.class,
			// ResourceLoader.ENTITY_OPERATION_INT);
			if (r != null)
				list.add(r);
		}
		return list;
	}

	public String[] getOperations() {
		return operations;
	}

	public void setOperations(String[] operations) {
		this.operations = operations;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParam() {
		return loader.getParam();
	}

	public void setParam(String param) {
		if (param != null && !param.isEmpty())
			loader.setParam(param);
	}
	public HashMap<String, Object> getMap(){
		return loader.getMap();
	}
}
