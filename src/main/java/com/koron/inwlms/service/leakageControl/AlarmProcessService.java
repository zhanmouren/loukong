package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessLog;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessReturnVO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;

@Service
public interface AlarmProcessService {

	AlarmProcessReturnVO queryAlarmProcess(SessionFactory factory, AlarmProcessDTO alarmProcessDTO);

	Integer updateAlarmProcess(SessionFactory factory, AlarmProcessVO alarmProcessVO,UserVO user);

	String addAlarmProcess(SessionFactory factory, AlarmProcessVO alarmProcessVO,UserVO user);

	Integer deleteAlarmProcess(SessionFactory factory, String code);

	TreatmentEffectVO queryTreatmentEffect(SessionFactory factory, TreatmentEffectDTO treatmentEffectDTO) throws ParseException;

	List<AlarmProcessVO> queryAlarmProcessByTaskCode(SessionFactory factory, String taskCode);

	List<UploadFileDTO> queryAlarmProcessFile(SessionFactory factory, String type);

	String getEstimatedTime(SessionFactory factory, String code);

	List<AlarmProcessLog> queryAlarmProcessLog(SessionFactory factory, String taskCode);


}
