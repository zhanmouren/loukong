package com.koron.inwlms.bean.DTO.sysManager;


/**
 * 1 菜单操作菜单节点DTO
 *
* @Author xiaozhan
* @Date 2020.07.08
*/
public class MenuOPDTO {

	//操作编码
	private String code;
	//创建时间
	private String createTime;
	//创建人
	private String createBy;
	//修改人
	private String updateBy;
    //修改时间
	private String updateTime;
	//名称
	private String name;
	//状态
	private Integer status;
	//权重
	private Integer weight;
	//标识
	private Integer flag;
	public String getCode() {
		return code;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public String getUpdateTime() {
		return updateTime;
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
	public Integer getFlag() {
		return flag;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
	
}
