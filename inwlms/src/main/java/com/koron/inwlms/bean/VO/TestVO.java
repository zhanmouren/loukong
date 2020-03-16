package com.koron.inwlms.bean.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Koron on 2020/1/15.
 */
@ApiModel(value = "测试实体")
public class TestVO {

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    /**
     * 测试参数

     */
    @ApiModelProperty(value = "测试参数")
    private String test;
}
