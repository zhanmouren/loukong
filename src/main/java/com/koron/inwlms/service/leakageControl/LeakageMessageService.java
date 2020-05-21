package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;

import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.LeakageMessageListVO;


/**
 * @author lzy
 * @Date 2020/05/09
 */

public interface LeakageMessageService {
	
	//消息中心信息
	LeakageMessageListVO<List<AlarmProcessVO>> queryMessage(SessionFactory factory, String loginName);
}
