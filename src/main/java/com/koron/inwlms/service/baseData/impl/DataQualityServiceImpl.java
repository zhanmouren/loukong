package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.VO.baseInf.MonRepVO;
import com.koron.inwlms.mapper.baseData.DataQualityMapper;
import com.koron.inwlms.service.baseData.DataQualityService;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auother:zhongweibin
 * @date:2020-04-01
 * @description:
 */
@Service
public class DataQualityServiceImpl implements DataQualityService {

    @TaskAnnotation("queryMonRep")
    public List<MonRepVO> queryMonRep(SessionFactory factory,String reportDate){
        DataQualityMapper mapper = factory.getMapper(DataQualityMapper.class);
        List<MonRepVO> list = mapper.queryMonRep(reportDate);
        return list;
    }
}
