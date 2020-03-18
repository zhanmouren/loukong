package com.koron.inwlms.bean.DTO.sysManager;

import lombok.Data;

/**
 * 表格字段映射明细bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
@Data
public class fieldMapperDTO {
	//表格映射明细id
	private Integer tableID;
	//对方字段code
	private String otherFieldCode;
	//对方字段名称
	private String otherFieldName;
	//我方字段名
	private String fieldName;
	//我方字段Code
	private String fieldCode;
	
}
