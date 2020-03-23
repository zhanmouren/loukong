package com.koron.ebs.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 权限的判断规则
 * 1、判断是否有某项规则，rule直接返回true即可。
 * @author 方志文
 *
 */
public class ContainRule implements Rule {
	private String id;
	private String name;

	@Override
	@SuppressWarnings("all")
	public boolean inspect(User user, Role role,Operation operation, Map<String,Object> data) {
		if(role.getId().equals("supervisior")){//系统管理员
			return true;
		};
		String ret = operation.getProperty("compare");
		if(ret == null || ret.equals("null"))
			return true;
		String property = user.getProperty(ret);
		String[] array = property !=null ?property.split(","):null;//"11,12"
		List<String> params=null;
		if(array!=null){
			params=new ArrayList<>();
			params.addAll(Arrays.asList(array));
		}
		Object obj = null;
		try {
			obj = BeanUtils.getProperty(data.get("data"), ret);
		} catch (Exception e) {
			return false;
		}
		if(obj != null && params!=null){
			params.retainAll((ArrayList<String>)obj);// 取交集
		}
		try {
			String appids=user.getProperty("appids");
			if(appids !=null && !appids.equals("null"))
				BeanUtils.setProperty(data.get("data"), "appids", Arrays.asList(appids));
			String workflowKey=user.getProperty("workflowKey");
			if(workflowKey !=null && !workflowKey.equals("null"))
				BeanUtils.setProperty(data.get("data"), "workflowKey", Arrays.asList(workflowKey));
			BeanUtils.setProperty(data.get("data"), ret, params);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public String getProperty(String key) {
		return null;
	}

	@Override
	public void setProperty(String key, String o) {
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
