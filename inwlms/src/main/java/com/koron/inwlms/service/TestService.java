package com.koron.inwlms.service;

import com.koron.inwlms.bean.DTO.TestBean;
import org.koron.ebs.mybatis.SessionFactory;

/**
 * Created by Koron on 2020/1/15.
 */
public interface TestService {

    /**
     * 测试方法
     * @return
     */
    Integer testFunction(SessionFactory factory, TestBean bean);
}
