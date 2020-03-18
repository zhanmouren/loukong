package com.koron.inwlms.bean.DTO.sysManager;

import java.util.List;

import lombok.Data;

/**
 * 表格映射明细bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
@Data
public class tableMapperDTO {
    //集成表格映射明细
	private Integer tableMapperId;
	//集成配置主表Id
	private Integer configID;
	//其他表的名称
	private String otherTabName;
	//对方表格code
	private String otherTabCode;
	//我方表格
	private String tableName;
	//我方表格code
	private String tableCode;
	
	//表格字段映射明细列表
	private List<fieldMapperDTO> fieldMapperList;

}
