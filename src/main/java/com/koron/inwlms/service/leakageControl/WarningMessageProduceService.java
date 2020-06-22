package com.koron.inwlms.service.leakageControl;

import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

import com.koron.inwlms.bean.DTO.common.MinMonitorPoint;

/**
 * 预警信息产生
 * @author 刘刚
 *
 */
@Service
public interface WarningMessageProduceService {

	String createWarningMessage(SessionFactory factory);

	String startPointWarning(SessionFactory factory, List<MinMonitorPoint> minMonitorPointList);

}
