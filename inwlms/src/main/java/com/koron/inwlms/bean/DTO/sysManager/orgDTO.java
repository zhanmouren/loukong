package com.koron.inwlms.bean.DTO.sysManager;

import lombok.Data;

/**
 * 1 组织bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/

@Data
public class orgDTO {
	//组织Id
	private Integer orgId;
	//组织Code
	private String orgCode;
	//组织名称
	private String orgName;
	//组织地址
	private String orgAddr;
	//组织类型
	private String orgType;
   
}
