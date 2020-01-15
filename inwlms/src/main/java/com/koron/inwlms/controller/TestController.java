package com.koron.inwlms.controller;

import com.koron.inwlms.bean.DTO.TestBean;
import com.koron.inwlms.servise.impl.TestServiceImpl;
import com.koron.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;

/**
 * Created by Koron on 2020/1/15.
 */
@Controller
@Api(value = "测试controller", description = "测试controller")
@RequestMapping(value = "/testController")
public class TestController {

    @RequestMapping(value = "/queryActivityLocation.htm", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
    @ApiOperation(value = "查询事件的坐标", notes = "条件查询事件的坐标信息", httpMethod = "POST", response = MessageBean.class, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryActivityLocation(@RequestBody TestBean bean) {
        MessageBean<Integer> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, Constant.MESSAGE_STRING_SUCCESS, Integer.class);
        try{
            ADOConnection.runTask(new TestServiceImpl(), "testFunction", Integer.class, bean);
        }catch(Exception e){
            msg.setCode(Constant.MESSAGE_INT_SUCCESS);
            msg.setDescription(Constant.MESSAGE_STRING_SELECTERROR);
        }
        return msg.toJson();
    }
}
