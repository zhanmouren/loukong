package com.koron.inwlms.service.impl;

import com.koron.inwlms.bean.DTO.TestBean;
import com.koron.inwlms.mapper.master.TestMapper;
import com.koron.inwlms.service.TestService;

import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;
import org.springframework.stereotype.Service;

/**
 * Created by Koron on 2020/1/15.
 */
@Service
public class TestServiceImpl implements TestService {

    @TaskAnnotation("testFunction")
    @Override
    public Integer testFunction(SessionFactory factory, TestBean bean) {
        TestMapper testMapper = factory.getMapper(TestMapper.class);
        return testMapper.testFunction();
    }
}
