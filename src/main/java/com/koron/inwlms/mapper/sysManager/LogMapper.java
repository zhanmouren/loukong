package com.koron.inwlms.mapper.sysManager;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryIntegrationLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryOperateLogDTO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationLogVO;
import com.koron.inwlms.bean.VO.sysManager.LoginLogVO;
import com.koron.inwlms.bean.VO.sysManager.OperateLogVO;

/** 
 * @author lzy
 * @Date 2020/03/30
 */

@Repository
public interface LogMapper {
	
	//添加登录日志
	public Integer addLoginLog(LoginLogDTO loginLogDTO);
	//查询登录日志
	public List<LoginLogVO> queryLoginLog(QueryLoginLogDTO queryLoginLogDTO);
	
	//查询操作日志
	public List<OperateLogVO> queryOperateLog(QueryOperateLogDTO queryOperateLogDTO);
	//添加操作日志
	public Integer addOperateLog(OperateLogDTO operateLogDTO);
	
	//查询集成日志
	public List<IntegrationLogVO> queryIntegrationLog(QueryIntegrationLogDTO queryIntegrationLogDTO);
}
