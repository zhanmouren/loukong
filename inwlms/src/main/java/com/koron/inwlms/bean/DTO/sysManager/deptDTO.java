package com.koron.inwlms.bean.DTO.sysManager;

import lombok.Data;

/**
 * 1 添加部门bean
 * 2 修改部门bean
* @Author xiaozhan
* @Date 2020.03.17
*/

@Data
public class deptDTO {
	//部门Id
	private Integer depId;
	//部门名称
	private String depName;
	//部门编码
	private String depCode;
	//部门状态
	private Integer status;

}
