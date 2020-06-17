package com.koron.permission.bean.DTO;

import java.sql.Timestamp;

/**
 * date: 2020/05/28
 * @author 小詹
 * description:应用-数据目录DTO
 *
 */
public class TblAppCatalogueDTO{
    //编码
	private String code;
	//应用编码
	private String appCode;
	//域名称
	private String name;
	//域状态
	private Integer status;
	//域权重
	private Integer weight;
	
	//创建人
    private String creator;
    //修改人
    private String modifier;
    //创建时间
    private Timestamp create_time;
    //修改时间
    private Timestamp modify_time;
	public String getCode() {
		return code;
	}
	public String getAppCode() {
		return appCode;
	}
	public String getName() {
		return name;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getWeight() {
		return weight;
	}
	public String getCreator() {
		return creator;
	}
	public String getModifier() {
		return modifier;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public Timestamp getModify_time() {
		return modify_time;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}
    
    
	
	
}
