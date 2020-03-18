package com.koron.inwlms.bean.DTO.sysManager;


import java.util.List;

import lombok.Data;

/**
 * 集成bean
 *
* @Author xiaozhan
* @Date 2020.03.17
*/
@Data
public class integrationConfDTO {
   //集成配置Id
	private Integer inteConfId;
	//集成配置状态
	private Integer status;
	//对方系统名称
	private String sysName;
	//对方JDBC
	private String otherJDBC;
	
	//表格映射明细列表
	private List<tableMapperDTO> tableMappeList;
	
}
