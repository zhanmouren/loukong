package com.koron.inwlms.service.sysManager;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryIntegrationLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryOperateLogDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationLogVO;
import com.koron.inwlms.bean.VO.sysManager.LoginLogVO;
import com.koron.inwlms.bean.VO.sysManager.OperateLogVO;
import com.koron.inwlms.bean.VO.sysManager.PageIntegrationLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageLoginLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageOperateLogListVO;

/**
 * @author lzy
 * @Date 2020/03/30
 */
public interface LogService {
	
	//查询登录日志
	PageListVO<List<LoginLogVO>> queryLoginLog(SessionFactory factory, QueryLoginLogDTO queryLoginLogDTO);
	//插入登录日志
	Integer addLoginLog(SessionFactory factory,LoginLogDTO loginLogDTO);
	//下载登录日志列表数据
	PageLoginLogListVO downloadLoginLogList(SessionFactory factory,QueryLoginLogDTO queryLoginLogDTO);
  
	
	//查询操作日志（操作人，开始时间与结束时间，操作类型）
	PageListVO<List<OperateLogVO>> queryOperateLog(SessionFactory factory, QueryOperateLogDTO queryOperateLogDTO);
	//插入操作日志
	Integer addOperateLog(SessionFactory factory,OperateLogDTO operateLogDTO);	
	//下载操作日志列表数据
	PageOperateLogListVO downloadOperateLogList(SessionFactory factory,QueryOperateLogDTO queryOperateLogDTO);
	  
	
	
	//查询集成日志（任务名，状态，开始时间与结束时间）
	PageListVO<List<IntegrationLogVO>> queryIntegrationLog(SessionFactory factory, QueryIntegrationLogDTO queryIntegrationLogDTO);
	//下载集成日志列表数据
	PageIntegrationLogListVO downloadIntegrationLogList(SessionFactory factory,QueryIntegrationLogDTO queryIntegrationLogDTO);
		  
}
