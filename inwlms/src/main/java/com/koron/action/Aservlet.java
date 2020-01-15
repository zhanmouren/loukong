package com.koron.action;

import com.koron.common.StaffAttribute;
import com.koron.common.bean.StaffBean;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.common.permission.SPIData;
import com.koron.common.permission.SPIMethod;
import com.koron.common.stub.Port;
import com.koron.bean.CardBean;
import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.swan.bean.MessageBean;
import org.swan.validation.ValidationKey;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Aservlet {
    @Autowired
    Port port;

    /**
     * 用来接收登录
     * @param ticket
     * @param request
     * @return
     */
    @RequestMapping("/login.htm")
    @ResponseBody
    public String login(@RequestParam("ticket") String ticket, HttpServletRequest request){
        MessageBean<StaffBean> msg =  port.getStaff(ticket);
        MessageBean<List> staff = port.staff(msg.getData().getCode(),null);
        request.getSession().setAttribute("USER",staff.getData().get(0));
        return msg.toJson();
    }

    /**
     * 演示获取用户信息
     * @param staffBean
     * @return
     */
    @RequestMapping("/detail.htm")
    @ResponseBody
    public String detail(@StaffAttribute("FRONT") StaffBean staffBean){
        MessageBean<StaffBean> msg= MessageBean.create(0,"success",StaffBean.class);
        msg.setData(staffBean);
        return msg.toJson();
    }

    /**
     *
     * @param cardBean
     * @return
     */
    @RequestMapping("/check.htm")
    @ValidationKey(value="update",clazz = {CardBean.class},param = {"idCard"})
    @ResponseBody
    public String checkCard(CardBean cardBean,@RequestParam("idcard") String idCard,@RequestParam("page")String page)
    {
        return  MessageBean.create(0,"success",Void.class).toJson();
    }

    @RequestMapping("/privilege.htm")
    @SPIMethod("manage")
    @ResponseBody
    public String operation(@SPIAccountAnno @StaffAttribute("USER") StaffBean staff){
        return  MessageBean.create(0,"success",Void.class).toJson();
    }

}
