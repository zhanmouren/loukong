package com.koron.inwlms.service.common.impl;



import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.mapper.common.CommonLoginMapper;
import com.koron.inwlms.service.common.CommonLoginService;



/**
 * @createTime 2020/04/07
 * @author lzy
 */
@Service
public class CommonLoginServiceImpl implements CommonLoginService{

	
	@TaskAnnotation("login")
	public UserListVO login(SessionFactory factory, UserLoginDTO userLoginDTO) {
		CommonLoginMapper mapper = factory.getMapper(CommonLoginMapper.class);
		UserListVO userListVO = mapper.login(userLoginDTO.getLoginName());
		if(userListVO != null) {
			String result = userListVO.getCode();
			if(result == null) {
				return null;
			}
		}
		return userListVO;
	}


	
	
}
