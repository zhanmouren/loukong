package com.koron.inwlms.service.common.impl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;
import com.koron.inwlms.mapper.common.CommonMapper;
import com.koron.inwlms.service.common.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@TaskAnnotation("querySysConfig")
	@Override
	public List<SysConfigVO> querySysConfig(SessionFactory factory) {
		CommonMapper mapper = factory.getMapper(CommonMapper.class);
		List<SysConfigVO> lists = mapper.querySysConfig();
		return lists;
	}

	//查询所有数据字典说明
	@TaskAnnotation("queryAllDataDic")
	@Override
	public List<DataDicVO> queryAllDataDic(SessionFactory factory) {
		CommonMapper mapper = factory.getMapper(CommonMapper.class);
		List<DataDicVO> dicList=mapper.queryAllDataDic();
		return dicList;
	}

}
