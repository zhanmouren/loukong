package com.koron.inwlms.service.leakageControl;

import java.text.ParseException;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.UploadFileDTO;
import com.koron.inwlms.bean.DTO.leakageControl.AlarmProcessDTO;
import com.koron.inwlms.bean.DTO.leakageControl.TreatmentEffectDTO;
import com.koron.inwlms.bean.VO.leakageControl.AlarmProcessVO;
import com.koron.inwlms.bean.VO.leakageControl.TreatmentEffectVO;

@Service
public interface AlarmProcessService {

	List<AlarmProcessVO> queryAlarmProcess(SessionFactory factory, AlarmProcessDTO alarmProcessDTO);

	Integer updateAlarmProcess(SessionFactory factory, AlarmProcessVO alarmProcessVO);

	String addAlarmProcess(SessionFactory factory, AlarmProcessVO alarmProcessVO);

	Integer deleteAlarmProcess(SessionFactory factory, String code);

	TreatmentEffectVO queryTreatmentEffect(SessionFactory factory, TreatmentEffectDTO treatmentEffectDTO) throws ParseException;

	List<AlarmProcessVO> queryAlarmProcessByTaskCode(SessionFactory factory, String taskCode);

	List<UploadFileDTO> queryAlarmProcessFile(SessionFactory factory, String type);


}
