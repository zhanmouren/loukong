package com.koron.inwlms.service.common.impl;



import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.mapper.common.CommonLoginMapper;
import com.koron.inwlms.service.common.CommonLoginService;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;



/**
 * @createTime 2020/04/07
 * @author lzy
 */
@Service
public class CommonLoginServiceImpl implements CommonLoginService{

	
	@TaskAnnotation("login")
	public UserVO login(SessionFactory factory, UserLoginDTO userLoginDTO) {
		CommonLoginMapper mapper = factory.getMapper(CommonLoginMapper.class);
		UserVO uv = mapper.login(userLoginDTO.getLoginName());
		if(uv != null) {
			String result = uv.getCode();
			if(result == null) {
				return null;
			}
		}
		return uv;
	}


	
	
}
