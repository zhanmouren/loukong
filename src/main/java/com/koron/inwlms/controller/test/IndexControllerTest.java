package com.koron.inwlms.controller.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.controller.IndexController;
import com.koron.inwlms.controller.SystemController;
import com.koron.main.App;
import com.koron.util.Constant;
import com.koron.util.SessionUtil;


/*
 * author:lzy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class IndexControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private IndexController indexController;
	
	@Autowired
	private SystemController systemController;
	
	private MockHttpServletRequest request;
	
	//测试方法运行前都会运行一遍登录
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		request = new MockHttpServletRequest();
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("admin");
		userLoginDTO.setPassword("WWhzdzEyMyE=");
		String tenantID = "mz";
		systemController.login(request, userLoginDTO, tenantID);
	}

	
	/*
	 * 首页指标查询单元测试（startTime）
	 */
	//测试点1：startTime为null，其他参数为正确的内容
	@Test
	public void testStartTime1() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(0);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点2：所有参数为正确的内容
	@Test
	public void testStartTime2() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202006);
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(0);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点3：startTime与endTime不一致，其他参数为正确的内容
	@Test
	public void testStartTime3() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202007);
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(0);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点4：startTime与endTime的月份格式不正确，其他参数为正确的内容
	@Test
	public void testStartTime4() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202013);
		indicatorDTO.setEndTime(202013);
		indicatorDTO.setAreaType(0);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点5：startTime与endTime的格式不正确，其他参数为正确的内容
	@Test
	public void testStartTime5() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(11111111);
		indicatorDTO.setEndTime(11111111);
		indicatorDTO.setAreaType(0);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	
	/*
	 * 首页指标查询单元测试（areaType）
	 */
	//测试点1：areaType为null，其他参数为正确的内容
	@Test
	public void testAreaType1() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202006);
		indicatorDTO.setEndTime(202006);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点2：areaType的格式不正确，其他参数为正确的内容
	@Test
	public void testAreaType2() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202006);
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(1111111);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	
	/*
	 * 首页指标查询单元测试（zoneCode）
	 */
	//测试点1：zoneCode为null，其他参数为正确的内容
	@Test
	public void testZoneCode1() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202006);
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}
	//测试点2：zoneCode的格式不正确，其他参数为正确的内容
	@Test
	public void testZoneCode2() throws Exception{
		IndicatorNewDTO  indicatorDTO = new IndicatorNewDTO();
		indicatorDTO.setStartTime(202006);
		indicatorDTO.setEndTime(202006);
		indicatorDTO.setAreaType(1);
		List<String> code = new ArrayList();
		code.add("C02100202");
		indicatorDTO.setZoneCodes(code);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(indexController.queryCompreInfo(indicatorDTO, user));
	}

}
