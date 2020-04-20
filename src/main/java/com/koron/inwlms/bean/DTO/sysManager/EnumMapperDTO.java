package com.koron.inwlms.bean.DTO.sysManager;

import java.sql.Timestamp;
import java.util.List;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

/**
 * 枚举值映射明细
* @Author xiaozhan
* @Date 2020.04.16
*/
public class EnumMapperDTO extends BaseDTO{
	//存储id的List
	private List<Integer> idList;

	private Integer id;
	//集成配置编码
	private String confCode;
	//对方字段枚举编码
	private String otherFieldValue;
	//我方枚举编码
	private String fieldValue;
	//映射方式
	private String mapper;
	      //创建人
			private String createBy;
			//创建时间
			private Timestamp createTime;
			//修改人
			private String updateBy;
			//修改时间
			private Timestamp updateTime;
			public List<Integer> getIdList() {
				return idList;
			}
			public void setIdList(List<Integer> idList) {
				this.idList = idList;
			}
			public Integer getId() {
				return id;
			}
			public void setId(Integer id) {
				this.id = id;
			}
			public String getConfCode() {
				return confCode;
			}
			public void setConfCode(String confCode) {
				this.confCode = confCode;
			}
			public String getOtherFieldValue() {
				return otherFieldValue;
			}
			public void setOtherFieldValue(String otherFieldValue) {
				this.otherFieldValue = otherFieldValue;
			}
			public String getFieldValue() {
				return fieldValue;
			}
			public void setFieldValue(String fieldValue) {
				this.fieldValue = fieldValue;
			}
			public String getMapper() {
				return mapper;
			}
			public void setMapper(String mapper) {
				this.mapper = mapper;
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
