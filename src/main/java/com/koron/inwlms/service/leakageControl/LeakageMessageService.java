package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.common.PageListVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;


/**
 * @author lzy
 * @Date 2020/05/09
 */

public interface LeakageMessageService {
	
	//查询预警信息
	PageListVO<List<AlarmMessageVO>> queryAlarmMessage(SessionFactory factory, String loginName);
	
	//查询漏损待办/已办
	PageListVO<List<AlarmProcessVO>> queryLeakageProcessing(SessionFactory factory, String loginName);
	
	//查询漏损待办/已办
	PageListVO<List<AlarmProcessVO>> queryMonitorProcessing(SessionFactory factory, String loginName);
	
}
