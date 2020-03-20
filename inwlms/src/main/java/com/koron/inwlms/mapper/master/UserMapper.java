package com.koron.inwlms.mapper.master;

import java.util.List;

import org.koron.ebs.mybatis.EnvSource;
import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.queryUserDTO;
import com.koron.inwlms.bean.DTO.sysManager.userDTO;
import com.koron.inwlms.bean.VO.sysManager.userVO;

/*
 * date:2020-03-18
 * author:xiaozhan
 */  	
@Repository
@EnvSource("_default")
public interface UserMapper {
   //添加职员
	public Integer addUser(userDTO userDTO);
	
	//查询职员
	public List<userVO> queryUser(queryUserDTO userDTO);
	
	//修改职员
    public Integer editUser(userDTO userDTO);
}
