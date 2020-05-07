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
import com.koron.inwlms.bean.DTO.sysManager.QueryIntegrationLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryLoginLogDTO;
import com.koron.inwlms.bean.DTO.sysManager.QueryOperateLogDTO;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.main.App;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemManagerQueryLogControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private LogService logService;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * 登录日志查询接口单元测试（page）
	 */
	
	//测试点1：除了startTime、endTime、type为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPage1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、type为默认值和page为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPage2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(-1);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startTime、endTime、type为默认值和page为0外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPage3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(0);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、endTime、type为默认值和page为正整数（1）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPage4() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(1);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 登录日志查询接口单元测试（pageCount）
	 */
	
	//测试点1：除了startTime、endTime、type为默认值和pageCount为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPageCount1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(-1);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、type为默认值和pageCount为0外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPageCount2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(0);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startTime、endTime、type为默认值和pageCount为正整数（1）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestPageCount3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(1);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 登录日志查询接口单元测试（name）
	 */
	
	//测试点1：除了startTime、endTime、type为默认值和name的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestName1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setName("");
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、type为默认值和name的长度为存在的name（"zhanzhan")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestName2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setName("zhanzhan");
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了startTime、endTime、type为默认值和name的长度为不存在的name（"nobody")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestName3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setName("nobody");
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	/*
	 * 登录日志查询接口单元测试（startTime）
	 */
		
	//测试点1：除了endTime、type为默认值和startTime的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestStartTime1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点2：除了endTime、type为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestStartTime2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime(null);
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了endTime、type为默认值和startTime为正确格式但期间没有数据（"2020-04-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestStartTime3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-04-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点4：除了endTime、type为默认值和startTime为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestStartTime4() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("123");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了endTime、type为默认值和startTime为正确格式且大于endTime外，其他所有的查询参数都为null	
	@Test
	public void testQueryLoginLogTestStartTime5() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-05-29");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}	
	
	
	/*
	 * 登录日志查询接口单元测试（endTime）
	 */
		
	//测试点1：除了startTime、type为默认值和endTime的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestEndTime1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-3-25");
		queryLoginLogDTO.setEndTime("");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点2：除了startTime、type为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestEndTime2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-3-25");
		queryLoginLogDTO.setEndTime(null);
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点3：除了startTime、type为默认值和endTime为正确格式但期间没有数据（"2020-02-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestEndTime3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-01-25");
		queryLoginLogDTO.setEndTime("2020-02-25");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、type为默认值和endTime为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestEndTime4() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("123");
		queryLoginLogDTO.setType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	/*
	 * 登录日志查询接口单元测试（type）
	 */
		
	//测试点1：除了startTime、endTime为默认值，type长度为0("")外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestTpye1() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime为默认值，type为存在的type（登入）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestTpye2() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("登入");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：除了startTime、endTime为默认值，type为存在的type（登出）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestTpye3() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("登出");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、endTime为默认值，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestTpye4() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了startTime、endTime为默认值，type为不存在的type（123）外，其他所有的查询参数都为null
	@Test
	public void testQueryLoginLogTestTpye5() throws Exception{
		QueryLoginLogDTO queryLoginLogDTO = new QueryLoginLogDTO();
		queryLoginLogDTO.setPage(null);
		queryLoginLogDTO.setPageCount(null);
		queryLoginLogDTO.setStartTime("2020-03-25");
		queryLoginLogDTO.setEndTime("2020-04-30");
		queryLoginLogDTO.setType("123");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysLoginLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryLoginLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	
	
	/*
	 * 操作日志查询接口单元测试（page）
	 */
	
	//测试点1：除了startTime、endTime、operateType为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPage1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、operateType为默认值和page为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPage2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(-1);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startTime、endTime、operateType为默认值和page为0外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPage3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(0);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、endTime、operateType为默认值和page为正整数（1）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPage4() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(1);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 操作日志查询接口单元测试（pageCount）
	 */
	
	//测试点1：除了startTime、endTime、operateType为默认值和pageCount为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPageCount1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(-1);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、operateType为默认值和pageCount为0外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPageCount2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(0);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startTime、endTime、operateType为默认值和pageCount为正整数（20）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestPageCount3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(20);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 操作日志查询接口单元测试（name）
	 */
	
	//测试点1：除了startTime、endTime、operateType为默认值和name的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestName1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setName("");
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime、operateType为默认值和name的长度为存在的name（"zhanzhan")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestName2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setName("zhanzhan");
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了startTime、endTime、operateType为默认值和name的长度为不存在的name（"nobody")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestName3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setName("nobody");
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	/*
	 * 操作日志查询接口单元测试（startTime）
	 */
		
	//测试点1：除了endTime、operateType为默认值和startTime的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestStartTime1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点2：除了endTime、operateType为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestStartTime2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime(null);
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了endTime、operateType为默认值和startTime为正确格式但期间没有数据（"2020-04-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestStartTime3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-04-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点4：除了endTime、operateType为默认值和startTime为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestStartTime4() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("123");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了endTime、operateType为默认值和startTime为正确格式且大于endTime外，其他所有的查询参数都为null	
	@Test
	public void testQueryOperateLogTestStartTime5() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-05-29");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}	
	
	
	/*
	 * 登录日志查询接口单元测试（endTime）
	 */
		
	//测试点1：除了startTime、operateType为默认值和endTime的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestEndTime1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-3-25");
		queryOperateLogDTO.setEndTime("");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点2：除了startTime、operateType为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestEndTime2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-3-25");
		queryOperateLogDTO.setEndTime(null);
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点3：除了startTime、operateType为默认值和endTime为正确格式但期间没有数据（"2020-02-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestEndTime3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-01-25");
		queryOperateLogDTO.setEndTime("2020-02-25");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、operateType为默认值和endTime为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestEndTime4() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("123");
		queryOperateLogDTO.setOperateType("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	/*
	 * 操作日志查询接口单元测试（type）
	 */
		
	//测试点1：除了startTime、endTime为默认值，type长度为0("")外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye1() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startTime、endTime为默认值，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye2() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType(null);
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：除了startTime、endTime为默认值，type为存在的type（增加）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye3() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("增加");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startTime、endTime为默认值，type为存在的type（删除）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye4() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("删除");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了startTime、endTime为默认值，type为存在的type（修改）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye5() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("修改");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	

	//测试点6：除了startTime、endTime为默认值，type为存在的type（查询）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye6() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("查询");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	

	//测试点7：除了startTime、endTime为默认值，type为不存在的type（123）外，其他所有的查询参数都为null
	@Test
	public void testQueryOperateLogTestTpye7() throws Exception{
		QueryOperateLogDTO queryOperateLogDTO = new QueryOperateLogDTO();
		queryOperateLogDTO.setPage(null);
		queryOperateLogDTO.setPageCount(null);
		queryOperateLogDTO.setStartTime("2020-03-25");
		queryOperateLogDTO.setEndTime("2020-04-30");
		queryOperateLogDTO.setOperateType("123");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysOperateLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryOperateLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 集成日志查询接口单元测试（page）
	 */
	
	//测试点1：除了startDate、endDate、status为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPage1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startDate、endDate、status为默认值和page为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPage2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(-1);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startDate、endDate、status为默认值和page为0外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPage3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(0);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startDate、endDate、status为默认值和page为正整数（1）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPage4() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(1);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 集成日志查询接口单元测试（pageCount）
	 */
	
	//测试点1：除了startDate、endDate、status为默认值和pageCount为负整数（-1）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPageCount1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(-1);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startDate、endDate、status为默认值和pageCount为0外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPageCount2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(0);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	//测试点3：除了startDate、endDate、status为默认值和pageCount为正整数（20）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestPageCount3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(20);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 集成日志查询接口单元测试（jobName）
	 */
	
	//测试点1：除了startDate、endDate、status为默认值和jobName的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestjobName1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setJobName("");
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startDate、endDate、status为默认值和jobName的长度为存在的jobName（"job")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestjobName2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setJobName("job");
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了startDate、endDate、status为默认值和jobName的长度为不存在的jobName（"noJob")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestjobName3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setJobName("noJob");
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	/*
	 * 集成日志查询接口单元测试（startDate）
	 */
		
	//测试点1：除了endDate、status为默认值和startDate的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStartDate1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点2：除了endDate、status为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStartDate2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate(null);
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
		
	//测试点3：除了endDate、status为默认值和startDate为正确格式但期间没有数据（"2020-04-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStartDate3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-04-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点4：除了endDate、status为默认值和startDate为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStartDate4() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("123");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了endDate、status为默认值和startDate为正确格式且大于endDate外，其他所有的查询参数都为null	
	@Test
	public void testQueryIntegrationLogTestStartDate5() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-05-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}	
	
	
	/*
	 * 集成日志查询接口单元测试（endDate）
	 */
		
	//测试点1：除了startDate、status为默认值和endDate的长度为0（"")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestEndDate1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点2：除了startDate、status为默认值外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestEndDate2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate(null);
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	//测试点3：除了startDate、status为默认值和endDate为正确格式但期间没有数据（"2020-02-25")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestEndDate3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-03-26");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startDate、status为默认值和endDate为不正确格式外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestEndDate4() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("123");
		queryIntegrationLogDTO.setStatus("全部");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	/*
	 * 集成日志查询接口单元测试（status）
	 */
		
	//测试点1：除了startDate、endDate、status为默认值，status长度为0("")外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStatus1() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了startDate、endDate、status为默认值，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStatus2() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus(null);
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：除了startDate、endDate、status为默认值，status为存在的status（进行中）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStatus3() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setStatus("进行中");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了startDate、endDate、status为默认值，status为存在的status（已结束）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStatus4() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("已结束");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点5：除了startDate、endDate、status为默认值，status为不存在的status（123）外，其他所有的查询参数都为null
	@Test
	public void testQueryIntegrationLogTestStatus5() throws Exception{
		QueryIntegrationLogDTO queryIntegrationLogDTO = new QueryIntegrationLogDTO();
		queryIntegrationLogDTO.setPage(null);
		queryIntegrationLogDTO.setPageCount(null);
		queryIntegrationLogDTO.setStartDate("2020-03-25");
		queryIntegrationLogDTO.setEndDate("2020-04-30");
		queryIntegrationLogDTO.setStatus("123");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/querySysIntegrationLog.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryIntegrationLogDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

}
