package com.koron.inwlms.controller.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.koron.inwlms.bean.DTO.sysManager.OperateLogDTO;
import com.koron.main.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemManagerAddLogControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	

	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/*
	 * 操作日志添加接口单元测试（operateModuleNo）
	 */
	
	//测试点1：operateType为默认值，operateModuleNo长度为0("")
	@Test
	public void testAddOperateLogTestModuleNo1() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("");
		operateLog.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：operateType为默认值，operateModuleNo为null
	@Test
	public void testAddOperateLogTestModuleNo2() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo(null);
		operateLog.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：operateType为默认值，operateModuleNo为存在的值("M800300400")
	@Test
	public void testAddOperateLogTestModuleNo3() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：operateType为默认值，operateModuleNo为不存在的值("M800000")
	@Test
	public void testAddOperateLogTestModuleNo4() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800000");
		operateLog.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	/*
	 * 操作日志添加接口单元测试（operateType）
	 */
	
	//测试点1：operateModuleNo为默认值，operateType长度为0("")
	@Test
	public void testAddOperateLogTestType1() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：operateModuleNo为默认值，operateType长度为null
	@Test
	public void testAddOperateLogTestType2() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType(null);
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：operateModuleNo为默认值，operateType为存在的type("增加")
	@Test
	public void testAddOperateLogTestType3() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("增加");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：operateModuleNo为默认值，operateType为存在的type("删除")
	@Test
	public void testAddOperateLogTestType4() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("删除");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：operateModuleNo为默认值，operateType为存在的type("修改")
	@Test
	public void testAddOperateLogTestType5() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("修改");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点6：operateModuleNo为默认值，operateType为存在的type("查询")
	@Test
	public void testAddOperateLogTestType6() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点7：operateModuleNo为默认值，operateType为不存在的type("123")
	@Test
	public void testAddOperateLogTestType7() throws Exception{
		OperateLogDTO operateLog = new OperateLogDTO();
		operateLog.setOperateModuleNo("M800300400");
		operateLog.setOperateType("123");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/addSysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(operateLog)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
}
