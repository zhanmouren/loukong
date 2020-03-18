package com.koron.inwlms.bean.DTO.sysManager;

import com.koron.inwlms.bean.DTO.common.BaseDTO;

import lombok.Data;

/**
 * 1.查询职员信息bean
 * 2.根据Id删除职员Bean
* @Author xiaozhan
* @Date 2020.03.17
*/

@Data
public class queryUserDTO extends BaseDTO{
	//职员Id
	private Integer userId;
	//职员名
	private String name;
	//部门名称
	private String depName;
	
}
