package com.koron.inwlms.controller.test;

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
import com.koron.inwlms.bean.DTO.sysManager.QueryUserDTO;
import com.koron.main.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemManagerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	

	
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testQueryUser() throws Exception{
		QueryUserDTO queryUserDTO=new QueryUserDTO();
		queryUserDTO.setLoginName("zhan");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManager/queryUser.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(queryUserDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
}
