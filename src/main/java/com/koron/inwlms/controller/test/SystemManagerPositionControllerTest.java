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
import com.koron.inwlms.bean.DTO.sysManager.PositionDTO;
import com.koron.main.App;


/*
 * author:lzy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemManagerPositionControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webAppliccationContext;
	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppliccationContext).build();
	}

	/*
	 * 职位查询接口单元测试（page）
	 */
	
	//测试点1：所有的查询参数都为null
	@Test
	public void testQueryPositionTestPage1() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(null);
		positionDTO.setPageCount(null);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：除了page为负整数（-1）外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPage2() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(-1);
		positionDTO.setPageCount(null);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：除了page为0外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPage3() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(0);
		positionDTO.setPageCount(null);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：除了page为整数（1）外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPage4() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(1);
		positionDTO.setPageCount(null);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	/*
	 * 职位查询接口单元测试（pageCount）
	 */
	
	//测试点1：除了pageCount为负整数（-1）外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPageCount1() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(null);
		positionDTO.setPageCount(-1);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	//测试点2：除了pageCount为负整数（0）外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPageCount2() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(null);
		positionDTO.setPageCount(0);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	//测试点3：除了pageCount为整数（20）外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestPageCount3() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(null);
		positionDTO.setPageCount(20);
		positionDTO.setName(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	/*
	 * 职位查询接口单元测试（name）
	 */
	
	//测试点1：除了name为""外,所有的查询参数都为null
	@Test
	public void testQueryPositionTestName1() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setPage(null);
		positionDTO.setPageCount(null);
		positionDTO.setName("");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPosition.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	/*
	 * 职位详情查询接口单元测试（code）
	 */
	
	//测试点1：code为""
	@Test
	public void testQqueryPositionDetailTestCode1() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setCode("");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPositionDetail.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点2：code为null
	@Test
	public void testQqueryPositionDetailTestCode2() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setCode(null);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPositionDetail.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：code为存在的code(695f3a6891564e439df26acca3caaacf)
	@Test
	public void testQqueryPositionDetailTestCode3() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setCode("695f3a6891564e439df26acca3caaacf");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPositionDetail.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	//测试点4：code为存在的code(abc)
	@Test
	public void testQqueryPositionDetailTestCode4() throws Exception {
		PositionDTO positionDTO = new PositionDTO();
		positionDTO.setCode("abc");
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerPosition/queryPositionDetail.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(positionDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

}
