package com.koron.inwlms.mapper.master;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.userDTO;

/*
 * date:2020-03-18
 * author:xiaozhan
 */  	
@Repository
@EnvSource("_default")
public interface UserMapper {
	public Integer addUser(userDTO userDTO);
}
