package com.koron.inwlms.service.baseData;

import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import org.koron.ebs.mybatis.SessionFactory;

import java.util.List;

public interface DataQualityService {

    public List<MonRepVO> queryMonRep(SessionFactory factory,String reportDate);

}
