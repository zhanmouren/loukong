package com.koron.inwlms.service.common;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.common.SysConfigVO;

/**
 * 通用接口
 * @author csh
 * @Date 2020/03/27
 */
public interface CommonService {

	List<SysConfigVO> querySysConfig(SessionFactory factory);
}
