package com.koron.inwlms.service.sysManager.impl;

import java.util.List;


import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.sysManager.LoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryIntegrationLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryOperateLogDTO;
import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.common.PageVO;
import com.koron.inwlms.bean.VO.sysManager.IntegrationLogVO;
import com.koron.inwlms.bean.VO.sysManager.LoginLogVO;
import com.koron.inwlms.bean.VO.sysManager.OperateLogVO;
import com.koron.inwlms.bean.VO.sysManager.PageIntegrationLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageLoginLogListVO;
import com.koron.inwlms.bean.VO.sysManager.PageOperateLogListVO;
import com.koron.inwlms.bean.VO.sysManager.UserListVO;
import com.koron.inwlms.mapper.sysManager.LogMapper;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.inwlms.util.PageUtil;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;

/**
 * 日志接口实现类
 * @author lzy
 * @Date 2020/03/30
 *
 */

@Service
public class LogServiceImpl implements LogService {
	
	//查询登录日志 2020/03/30
	@TaskAnnotation("queryLoginLog")
	@Override
	public PageListVO<List<LoginLogVO>> queryLoginLog(SessionFactory factory,QueryLoginLogDTO queryLoginLogDTO){
		LogMapper logMapper = factory.getMapper(LogMapper.class);
		List<LoginLogVO> loginLogList=logMapper.queryLoginLog(queryLoginLogDTO);
		int rowNumber = logMapper.getLoginLogCount(queryLoginLogDTO);
		//返回数据结果
		PageListVO<List<LoginLogVO>> result = new PageListVO<>();
		result.setDataList(loginLogList);
		
		PageVO pageVO = PageUtil.getPageBean(queryLoginLogDTO.getPage(), queryLoginLogDTO.getPageCount(), rowNumber);
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
		
	//插入登录日志 2020/03/31
	@TaskAnnotation("addLoginLog")
	@Override
	public Integer addLoginLog(SessionFactory factory,LoginLogDTO  loginLogDTO) {
		LogMapper logMapper = factory.getMapper(LogMapper.class);
		Integer addResult = logMapper.addLoginLog(loginLogDTO);
		return addResult;
	}
	
	
	//下载登录日志列表数据 2020/04/23
	@TaskAnnotation("downloadLoginLogList")
	@Override
	public PageLoginLogListVO downloadLoginLogList(SessionFactory factory, QueryLoginLogDTO queryLoginLogDTO) {
		LogMapper logMapper = factory.getMapper(LogMapper.class);
			
		List<LoginLogVO> loginLogList=logMapper.queryLoginLog(queryLoginLogDTO);
		// 返回数据结果
		PageLoginLogListVO result = new PageLoginLogListVO();
		result.setDataList(loginLogList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryLoginLogDTO.getPage(), queryLoginLogDTO.getPageCount(), loginLogList.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
	
	//查询操作日志 2020/04/01
	@TaskAnnotation("queryOperateLog")
	@Override
	public List<OperateLogVO> queryOperateLog(SessionFactory factory,QueryOperateLogDTO queryOperateLogDTO){
		LogMapper logMapper = factory.getMapper(LogMapper.class);
		List<OperateLogVO> OperateLogList=logMapper.queryOperateLog(queryOperateLogDTO);
		return OperateLogList;
	}
	
	
	//插入操作日志 2020/04/01
	@TaskAnnotation("addOperateLog")
	@Override
	public Integer addOperateLog(SessionFactory factory,OperateLogDTO  operateLogDTO) {
		LogMapper logMapper = factory.getMapper(LogMapper.class);
//		Gson jsonValue = new Gson();
//		// 查询条件字符串转对象，查询数据结果
//		UserListVO userListVO = jsonValue.fromJson(JSON.toJSON(SessionUtil.getAttribute(Constant.LOGIN_USER)).toString(), UserListVO.class);
//		operateLogDTO.setCreateBy(userListVO.getLoginName());
//		operateLogDTO.setUpdateBy(userListVO.getLoginName());
		operateLogDTO.setResult("success");
		Integer addResult = logMapper.addOperateLog(operateLogDTO);
		return addResult;
	}
	
	
	//下载操作日志列表数据 2020/04/23
	@TaskAnnotation("downloadOperateLogList")
	@Override
	public PageOperateLogListVO downloadOperateLogList(SessionFactory factory, QueryOperateLogDTO queryOperateLogDTO) {
		LogMapper logMapper = factory.getMapper(LogMapper.class);
				
		List<OperateLogVO> operateLogList=logMapper.queryOperateLog(queryOperateLogDTO);
		// 返回数据结果
		PageOperateLogListVO result = new PageOperateLogListVO();
		result.setDataList(operateLogList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryOperateLogDTO.getPage(), queryOperateLogDTO.getPageCount(), operateLogList.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
	//查询集成日志 2020/04/01
	@TaskAnnotation("queryIntegrationLog")
	@Override
	public List<IntegrationLogVO> queryIntegrationLog(SessionFactory factory,QueryIntegrationLogDTO queryIntegrationLogDTO){
		LogMapper logMapper = factory.getMapper(LogMapper.class);
		List<IntegrationLogVO> IntegrationLogList=logMapper.queryIntegrationLog(queryIntegrationLogDTO);
		return IntegrationLogList;
	}

	//下载登录日志列表数据 2020/04/23
	@TaskAnnotation("downloadIntegrationLogList")
	@Override
	public PageIntegrationLogListVO downloadIntegrationLogList(SessionFactory factory, QueryIntegrationLogDTO queryIntegrationLogDTO) {
		LogMapper logMapper = factory.getMapper(LogMapper.class);
				
		List<IntegrationLogVO> integrationLogList=logMapper.queryIntegrationLog(queryIntegrationLogDTO);
		// 返回数据结果
		PageIntegrationLogListVO result = new PageIntegrationLogListVO();
		result.setDataList(integrationLogList);
		// 插入分页信息
		PageVO pageVO = PageUtil.getPageBean(queryIntegrationLogDTO.getPage(), queryIntegrationLogDTO.getPageCount(), integrationLogList.size());
		result.setTotalPage(pageVO.getTotalPage());
		result.setRowNumber(pageVO.getRowNumber());
		result.setPageCount(pageVO.getPageCount());
		result.setPage(pageVO.getPage());
		return result;
	}
	
	
}
