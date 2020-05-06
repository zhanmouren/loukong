package com.koron.inwlms.service.baseData.impl;

import com.koron.inwlms.bean.VO.baseInf.PipeVO;
import com.koron.inwlms.mapper.baseData.PropertyMapper;
import com.koron.inwlms.service.baseData.PipeService;
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
public class PipeServiceImpl implements PipeService {

    @TaskAnnotation("queryALList")
    public List<PipeVO> queryALList(SessionFactory factory){
        PropertyMapper mapper = factory.getMapper(PropertyMapper.class);
        List<PipeVO> list = mapper.queryALList();
        return list;
    }

}
