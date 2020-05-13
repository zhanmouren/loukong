package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.mapper.leakageControl.LeakageMessageMapper;

/**
 * @author lzy
 * @Date 2020/05/09
 */

@Service
public class LeakageMessageServiceImpl implements LeakageMessageService{

	//查询预警信息
	@TaskAnnotation("queryAlarmMessage")
	@Override
	public PageListVO<List<AlarmMessageVO>> queryAlarmMessage(SessionFactory factory, String loginName) {
		LeakageMessageMapper  leakageMessageMapper = factory.getMapper(LeakageMessageMapper.class);
		List<AlarmMessageVO> leakageMessageList = leakageMessageMapper.queryAlarmMessage(loginName);
		PageListVO<List<AlarmMessageVO>> result = new PageListVO<>();
		result.setDataList(leakageMessageList);
		result.setTotalPage(1);
		result.setRowNumber(5);
		result.setPage(1);
		result.setPageCount(5);
		return result;
	}
	
	//查询漏损待办/已办
	@TaskAnnotation("queryLeakageProcessing")
	@Override
	public PageListVO<List<AlarmProcessVO>> queryLeakageProcessing(SessionFactory factory, String loginName) {
		LeakageMessageMapper leakageMessageMapper = factory.getMapper(LeakageMessageMapper.class);
		List<AlarmProcessVO> leakageProcessList = leakageMessageMapper.queryLeakageProcessing(loginName);
		
		PageListVO<List<AlarmProcessVO>> result = new PageListVO<>();
		result.setDataList(leakageProcessList);
		result.setTotalPage(1);
		result.setRowNumber(5);
		result.setPage(1);
		result.setPageCount(5);
		return result;
	}

	//查询监测待办/已办
	@TaskAnnotation("queryMonitorProcessing")
	@Override
	public PageListVO<List<AlarmProcessVO>> queryMonitorProcessing(SessionFactory factory, String loginName) {
		LeakageMessageMapper leakageMessageMapper = factory.getMapper(LeakageMessageMapper.class);
		List<AlarmProcessVO> leakageProcessList = leakageMessageMapper.queryMonitorProcessing(loginName);
		
		PageListVO<List<AlarmProcessVO>> result = new PageListVO<>();
		result.setDataList(leakageProcessList);
		result.setTotalPage(1);
		result.setRowNumber(5);
		result.setPage(1);
		result.setPageCount(5);
		return result;
	}


}
