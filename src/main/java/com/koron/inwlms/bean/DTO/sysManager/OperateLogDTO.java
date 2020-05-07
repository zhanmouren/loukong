package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;

/**
 * 操作日志bean
 * @author lzy
 * @Date 2020.03.30
 */
public class OperateLogDTO {

	//操作日志id
	private Integer id;
	//操作类型
	private String operateType;
	//操作对象（模块No）
	private String operateModuleNo;
	//操作时间
	private String operateTime;
	//操作用户的code
	private String operateUserCode;
	//操作结果
	private String result;
	//创建人
	private String createBy;
	//创建时间
	private Timestamp createTime;
	//修改人
	private String updateBy;
	//修改时间
	private Timestamp updateTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getOperateModuleNo() {
		return operateModuleNo;
	}
	public void setOperateModuleNo(String operateModuleNo) {
		this.operateModuleNo = operateModuleNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
