package com.koron.inwlms.controller.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.koron.inwlms.bean.DTO.indexData.IndicatorNewDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.intellectPartition.TotalSchemeDetDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.VO.intellectPartition.SchemeDet;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.controller.IndexController;
import com.koron.inwlms.controller.IntellectPartitionController;
import com.koron.inwlms.controller.SystemController;
import com.koron.main.App;


/*
 * author:lzy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class IntellectPartitionControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private IntellectPartitionController intellectPartitionController;
	
	@Autowired
	private SystemController systemController;
	
	private MockHttpServletRequest request;

	public String tenantID = "mz";
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		request = new MockHttpServletRequest();
		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setLoginName("admin");
		userLoginDTO.setPassword("WWhzdzEyMyE=");
		systemController.login(request, userLoginDTO, tenantID);
	}

	/*
	 * 智能分区单元测试（maxZone）
	 */
	//测试点1：maxZone为null，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMaxZone1() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点2：所有参数为正确的内容
	@Test
	public void testAutomaticPartitionMaxZone2() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点3：maxZone为-1，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMaxZone3() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setMaxZone(-1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点4：小于minZone的maxZone，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMaxZone4() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(3);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setMaxZone(1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点5：minZone与maxZone相等，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMaxZone5() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(2);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setMaxZone(2);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	/*
	 * 智能分区单元测试（minZone）
	 */
	//测试点1：minZone为null，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMinZone1() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点2：minZone为-1，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionMinZone2() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(-1);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	/*
	 * 智能分区单元测试（areaType）
	 */
	//测试点1：areaType为null，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionAreaType1() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点2：areaType为-1(不正确的内容），其他参数为正确的内容
	@Test
	public void testAutomaticPartitionAreaType2() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setZoneType(-1);
		automaticPartitionDTO.setZoneCode("C02100201");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	/*
	 * 智能分区单元测试（zoneCode）
	 */
	//测试点1：zoneCode为null，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionZoneCode1() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setZoneType(1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	//测试点2：zoneCode为""，其他参数为正确的内容
	@Test
	public void testAutomaticPartitionZoneCode2() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setMinZone(1);
		automaticPartitionDTO.setMaxZone(6);
		automaticPartitionDTO.setZoneType(1);
		automaticPartitionDTO.setZoneCode("");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.automaticPartition(automaticPartitionDTO, user, tenantID));
	}
	
	/*
	 * 获取自动分区范围 单元测试（zoneType）
	 */
	//测试点1：zoneType为null，其他参数为正确的内容
	@Test
	public void testGetZoneRangeZoneType1() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getZoneRange(automaticPartitionDTO, user));
	}
	
	//测试点2：zoneType为-1（不正确的内容），其他参数为正确的内容
	@Test
	public void testGetZoneRangeZoneType2() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setZoneType(-1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getZoneRange(automaticPartitionDTO, user));
	}
	
	//测试点3：zoneType为1（正确的内容），其他参数为正确的内容
	@Test
	public void testGetZoneRangeZoneType3() throws Exception{
		AutomaticPartitionDTO automaticPartitionDTO = new AutomaticPartitionDTO();
		automaticPartitionDTO.setZoneType(1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getZoneRange(automaticPartitionDTO, user));
	}
	
	/*
	 * 查询方案总表数据 单元测试（startTime）
	 */
	//测试点1：startTime为null，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetStartTime1() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setEndTime("2020-07-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点2：所有参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetStartTime2() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("2020-06-13");
		totalSchemeDetDTO.setEndTime("2020-07-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点3：startTime为""(长度为0)，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetStartTime3() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("");
		totalSchemeDetDTO.setEndTime("2020-07-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点4：startTime为abc(内容不正确)，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetStartTime4() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("abc");
		totalSchemeDetDTO.setEndTime("2020-07-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点5：startTime大于endTime
	@Test
	public void testQueryTotalSchemeDetStartTime5() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("2020-07-13");
		totalSchemeDetDTO.setEndTime("2020-06-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	/*
	 * 查询方案总表数据 单元测试（endTime）
	 */
	//测试点1：endTime为null，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetEndTime1() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("2020-06-13");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点2：endTime为""(长度为0)，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetEndTime2() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("2020-06-13");
		totalSchemeDetDTO.setEndTime("");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	//测试点3：endTime为abc(不正确的内容)，其他参数为正确的内容
	@Test
	public void testQueryTotalSchemeDetEndTime3() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setStartTime("2020-06-13");
		totalSchemeDetDTO.setEndTime("abc");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.queryTotalSchemeDet(totalSchemeDetDTO, user));
	}
	
	/*
	 * 查询分区方案数据 单元测试（code）
	 */
	//测试点1：code为null，其他参数为正确的内容
	@Test
	public void testGetKafkaReturnCode1() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getKafkaReturn(totalSchemeDetDTO, user));
	}
	
	//测试点2：code为""(长度为0)，其他参数为正确的内容
	@Test
	public void testGetKafkaReturnCode2() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setCode("");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getKafkaReturn(totalSchemeDetDTO, user));
	}
	
	//测试点3：所有参数为正确的内容
	@Test
	public void testGetKafkaReturnCode3() throws Exception{
		TotalSchemeDetDTO totalSchemeDetDTO = new TotalSchemeDetDTO();
		totalSchemeDetDTO.setCode("123");
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getKafkaReturn(totalSchemeDetDTO, user));
	}
	
	/*
	 * 通过方案总表code查询分区方案数据 单元测试
	 */
	//测试点1：code为null，其他参数为正确的内容
	@Test
	public void testGetZonePipeReturnCode1() throws Exception{
		SchemeDet schemeDet = new SchemeDet();
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(intellectPartitionController.getZonePipeReturn(schemeDet, user));
	}
}
