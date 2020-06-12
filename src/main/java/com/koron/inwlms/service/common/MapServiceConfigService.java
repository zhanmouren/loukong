package com.koron.inwlms.service.common;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.common.MapServiceData;

public interface MapServiceConfigService {

	MapServiceData queryMapServiceConfig(SessionFactory factory);
	
	

}
