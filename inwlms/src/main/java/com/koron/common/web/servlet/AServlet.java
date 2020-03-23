package com.koron.common.web.servlet;

import org.swan.excel.Excel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.koron.ebs.mybatis.SessionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koron.common.bean.StaffBean;
import com.koron.common.web.mapper.DepartmentMapper;
import com.koron.common.web.util.StaffAttribute;
import com.koron.ebs.permission.SPIAccountAnno;
import com.koron.ebs.permission.SPIMethod;
import com.koron.ebs.permission.StaffAccount;
import com.koron.util.Constant;
import com.koron.util.Tools;

@Controller
public class AServlet {
//	@RequestMapping("/a.htm")
//	@ResponseBody
//	@SPIMethod("bigscreen")
//	public String list(@SPIAccountAnno @StaffAttribute(Constant.USER) StaffAccount staff) {
//		System.out.println("----");
//		return "ajdsljaskajslk";
//	}
//	
//	@RequestMapping("/export.htm")
//	public HttpEntity<?> send() throws IOException {
//		try(SessionFactory factory = new SessionFactory()){
//			DepartmentMapper mapper = factory.getMapper(DepartmentMapper.class);
//			List<StaffBean> staff = mapper.getStaffInfo();
//			HashMap<String,Object> map = new HashMap<>();
//			map.put("staff", staff);
//			return Excel.createFile("职工数据库",Tools.getWebFile("/excel/employee.xlsx").openStream(), "employee.btl", map);
//		}
//	}
}
