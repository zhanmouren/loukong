package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;

/**
 * @author lzy
 * @Date 2020/05/09
 */


public interface LeakageMessageMapper {

	public List<AlarmMessageVO> queryAlarmMessage(String loginName);
}
