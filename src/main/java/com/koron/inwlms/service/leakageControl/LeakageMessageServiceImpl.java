package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.LeakageMessageListVO;
import com.koron.inwlms.mapper.leakageControl.LeakageMessageMapper;

/**
 * @author lzy
 * @Date 2020/05/09
 */

@Service
public class LeakageMessageServiceImpl implements LeakageMessageService{

	//消息中心
	@TaskAnnotation("queryMessage")
	@Override
	public LeakageMessageListVO<List<AlarmProcessVO>> queryMessage(SessionFactory factory, String loginName) {
		LeakageMessageMapper  leakageMessageMapper = factory.getMapper(LeakageMessageMapper.class);
		List<AlarmMessageVO> leakageMessageList = leakageMessageMapper.queryAlarmMessage(loginName);
		List<AlarmProcessVO> leakageProcessList = leakageMessageMapper.queryLeakageProcessing(loginName);
		List<AlarmProcessVO> monitorProcessList = leakageMessageMapper.queryMonitorProcessing(loginName);
		Integer messageNumber = leakageMessageMapper.getMessageNumber(loginName);
		LeakageMessageListVO<List<AlarmProcessVO>> result = new LeakageMessageListVO<>();
		result.setAlarmMessageList(leakageMessageList);
		result.setLeakageProcessingList(leakageProcessList);
		result.setMonitorProcessingList(monitorProcessList);
		result.setNumber(messageNumber);
		return result;
	}


}
