package com.koron.inwlms.service.common;


import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import org.koron.ebs.mybatis.SessionFactory;


/**
 * 
 * @createTime 2020/04/07
 * @author lzy
 */
public interface CommonLoginService {
	
	/**
	 * 用户登录
	 * @param factory
	 * @param userLoginDTO 用户登录DTO
	 * @return
	 */
	public UserVO login(SessionFactory factory, UserLoginDTO userLoginDTO);

	
	

}
