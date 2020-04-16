package com.koron.main;

import com.koron.common.StaffAttribute;
import com.koron.common.bean.StaffBean;
import com.koron.common.permission.SPIAccountAnno;
import com.koron.common.permission.SPIMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainServlet {
    @RequestMapping("/")
    public String index(){
        return "redirect:/swagger-ui.html";
    }
    @RequestMapping("/main.htm")
    @ResponseBody
    @SPIMethod("test")
    public String main(@SPIAccountAnno @StaffAttribute("_USER") StaffBean staff){
        System.out.println(staff);
        return "OK";
    }
}
