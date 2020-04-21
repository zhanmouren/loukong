package com.koron.inwlms.service.leakageControl;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.stereotype.Service;

/**
 * 预警信息产生
 * @author 刘刚
 *
 */
@Service
public interface WarningMessageProduceService {

	String createWarningMessage(SessionFactory factory);

}
