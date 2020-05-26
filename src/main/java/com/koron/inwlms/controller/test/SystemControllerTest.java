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
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.main.App;

/*
 * author:lzy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemControllerTest{

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	/*
	 * 登录接口单元测试（loginName）
	 */
	
	//测试点1：password为正确的password，loginName长度为0("")
	@Test
	public void testLoginName1() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("");
		userLoginDTO.setPassword("MTIzNDU2a3VuS3VuIw==");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());	
	}
	
	//测试点2：password为正确的password，loginName为null
	@Test
	public void testLoginName2() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName(null);
		userLoginDTO.setPassword("MTIzNDU2a3VuS3VuIw==");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：password为正确的password，loginName为存在的loginName
	@Test
	public void testLoginName3() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("测试11");
		userLoginDTO.setPassword("MTIzNDU2a3VuS3VuIw==");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点4：password为正确的password，loginName为不存在的loginName
	@Test
	public void testLoginName4() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("11111111");
		userLoginDTO.setPassword("MTIzNDU2a3VuS3VuIw==");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	
	/*
	 * 登录接口单元测试（password）
	 */
	
	//测试点1：loginName为存在的loginName，password长度为0("")
	@Test
	public void testPassword1() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("测试11");
		userLoginDTO.setPassword("");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());	
	}
	
	//测试点2：loginName为存在的loginName，password为null
	@Test
	public void testPassword2() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("测试11");
		userLoginDTO.setPassword(null);
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	//测试点3：loginName为存在的loginName，password为不正确的password
	@Test
	public void testPassword3() throws Exception {
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("测试11");
		userLoginDTO.setPassword("abc123");
		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemController/login.htm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userLoginDTO)))
				.andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
}
