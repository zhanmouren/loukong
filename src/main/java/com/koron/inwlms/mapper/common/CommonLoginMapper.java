package com.koron.inwlms.mapper.common;

import org.apache.ibatis.annotations.Param;


import com.koron.inwlms.bean.VO.sysManager.UserListVO;


/**
 * 通用登录功能mapper层
 * @createTime 2020/04/07
 * @author lzy
 */

public interface CommonLoginMapper {
	
	
	/**
	 * 用户登录
	 */
	public UserListVO login(@Param("loginName") String loginName);
	
	
	
}
