package com.koron.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleOperationModifyBean implements IdentityBean{
	/**
	 * 角色ID
	 */
	private Integer roleid;
	/**
	 * 需要添加的operationid值
	 */
	private Integer[] addopids;
	/**
	 * 需要删除的operationid值
	 */
	private Integer[] delopids;
	/**
	 * 需要添加param值
	 */
	private String[] addparams;
	
	private List<Map<String, Object>> adds;
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	@Override
	public Integer getId() {
		return -1;
	}
	public Integer[] getAddopids() {
		return addopids;
	}
	public void setAddopids(Integer[] addopids) {
		this.addopids = addopids;
	}
	public Integer[] getDelopids() {
		return delopids;
	}
	public void setDelopids(Integer[] delopids) {
		this.delopids = delopids;
	}
	public String[] getAddparams() {
		return addparams;
	}
	public void setAddparams(String[] addparams) {
		this.addparams = addparams;
	}
	public List<Map<String, Object>> getAdds() {
		if(adds==null){
			adds=new ArrayList<>();
		}
		if(addopids!=null&&addparams!=null&&addopids.length==addparams.length){
			for(int i=0;i<addopids.length;i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("opid", addopids[i]);
				map.put("param", addparams[i]);
				adds.add(map);
			}
		}
		return adds;
	}
	public void setAdds(List<Map<String, Object>> adds) {
		this.adds = adds;
	}
}