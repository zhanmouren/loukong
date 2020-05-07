package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.DataDicDTO;
import com.koron.inwlms.bean.VO.common.SysConfigVO;
import com.koron.inwlms.bean.VO.sysManager.DataDicVO;

/**
 * 通用接口
 * @author csh
 * @Date 2020/03/27
 */
public interface CommonService {

	List<SysConfigVO> querySysConfig(SessionFactory factory);
	
	//查询所有数据字典说明
	List<DataDicVO> queryAllDataDic(SessionFactory factory);
}
