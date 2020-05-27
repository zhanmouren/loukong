package com.koron.inwlms.mapper.leakageControl;

import java.util.List;

import com.koron.inwlms.bean.DTO.leakageControl.WarningInfDTO;
import com.koron.inwlms.bean.DTO.sysManager.LabelDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmMessageVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;

/**
 * @author lzy
 * @Date 2020/05/09
 */


public interface LeakageMessageMapper {

	public List<AlarmMessageVO> queryAlarmMessage(String loginName);
	
	public List<AlarmProcessVO> queryLeakageProcessing(String loginName);
	
	public List<AlarmProcessVO> queryMonitorProcessing(String loginName);
	
	public Integer getMessageNumber(String loginName);

	public Integer getProcessingNumber(String loginName);
	
  	public Integer updateAlarmMessageStatus(List<String> codeList);
}
