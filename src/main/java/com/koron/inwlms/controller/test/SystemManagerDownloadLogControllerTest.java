package com.koron.inwlms.controller.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.koron.inwlms.controller.SystemManagerLogController;
import com.koron.inwlms.service.sysManager.LogService;
import com.koron.main.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SystemManagerDownloadLogControllerTest {

//	private MockMvc mockMvc;
	
	@Autowired
	private SystemManagerLogController systemManagerLogController;
	
//	@Autowired
//	private WebApplicationContext webApplicationContext;
	
//	@Autowired
//	private LogService logService;
	
	@Before
	public void setUp() throws Exception {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * 登录日志下载接口单元测试（name）
	 */
	private String loginTitleInfos =  "[{\"titleValue\":\"name\",\"titleName\":\"用户名称\"},\r\n" + 
			"{\"titleValue\":\"position\",\"titleName\":\"职务\"},\r\n" + 
			"{\"titleValue\":\"result\",\"titleName\":\"登录结果\"},\r\n" + 
			"{\"titleValue\":\"loginTime\",\"titleName\":\"登录时间\"},\r\n" + 
			"{\"titleValue\":\"errorLog\",\"titleName\":\"错误日志\"}]";
	//测试点1：startTime、endTime、type为默认值和name的长度为0（"")
	@Test
	public void testDownloadLoginLogListTestName1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
//		MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/systemManagerLog/downloadLoginLogList.htm")
//				.contentType(MediaType.APPLICATION_JSON).param("objValue", objValue).param("titleInfos", loginTitleInfos))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andDo(MockMvcResultHandlers.print())
//				.andReturn();
//		System.out.println(System.out.println(mvcResult.getResponse().getContentAsString()));
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点2：startTime、endTime、type为默认值和name为null
	@Test
	public void testDownloadLoginLogListTestName2() throws Exception{
		String objValue = "{\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点3：startTime、endTime、type为默认值和name为存在的name（"zhanzhan")
	@Test
	public void testDownloadLoginLogListTestName3() throws Exception{
		String objValue = "{\"name\":\"zhanzhan\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点4：startTime、endTime、type为默认值和name为不存在的name（"nobody")
	@Test
	public void testDownloadLoginLogListTestName4() throws Exception{
		String objValue = "{\"name\":\"nobody\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	/*
	 * 登录日志下载接口单元测试（startTime）
	 */
	
	//测试点1：name、endTime、type为默认值和startTime的长度为0（"")
	@Test
	public void testDownloadLoginLogListTestStartTime1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点2：name、endTime、type为默认值和startTime为null
	@Test
	public void testDownloadLoginLogListTestStartTime2() throws Exception{
		String objValue = "{\"name\":\"\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点3：name、endTime、type为默认值和startTime为正确格式但期间没有数据（"2020-04-25")
	@Test
	public void testDownloadLoginLogListTestStartTime3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-04-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点4：name、endTime、type为默认值和startTime为不正确格式
	@Test
	public void testDownloadLoginLogListTestStartTime4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"123\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点5：name、endTime、type为默认值和startTime为正确格式且大于endTime
	@Test
	public void testDownloadLoginLogListTestStartTime5() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-05-25\",\"endTime\":\"2020-04-30\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	/*
	 * 登录日志下载接口单元测试（endTime）
	 */
	
	//测试点1：name、startTime、type为默认值和endTime的长度为0（"")
	@Test
	public void testDownloadLoginLogListTestEndTime1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点2：name、startTime、type为默认值和endTime为null
	@Test
	public void testDownloadLoginLogListTestEndTime2() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点3：name、startTime、type为默认值和endTime为正确格式但期间没有数据（"2020-04-25")
	@Test
	public void testDownloadLoginLogListTestEndTime3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-01-25\",\"endTime\":\"2020-02-25\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点4：name、startTime、type为默认值和endTime为不正确格式
	@Test
	public void testDownloadLoginLogListTestEndTime4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"123\",\"type\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	/*
	 * 登录日志下载接口单元测试（type）
	 */
	
	//测试点1：name、endTime、startTime为默认值和type的长度为0（"")
	@Test
	public void testDownloadLoginLogListTestType1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点2：name、endTime、startTime为默认值和type为存在的type（登入）
	@Test
	public void testDownloadLoginLogListTestType2() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"登入\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点3：name、endTime、startTime为默认值和type为存在的type（登出）
	@Test
	public void testDownloadLoginLogListTestType3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"登出\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点4：name、endTime、startTime为默认值和type为null
	@Test
	public void testDownloadLoginLogListTestType4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	//测试点5：name、endTime、startTime为默认值和type为不存在的type（123）
	@Test
	public void testDownloadLoginLogListTestType5() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"type\":\"123\"}";
		System.out.println(systemManagerLogController.downloadLoginLogList(objValue, loginTitleInfos));
		}
	
	
	/*
	 * 操作日志下载接口单元测试（name）
	 */
	private String operateTitleInfos = "[{\"titleValue\":\"moduleName\",\"titleName\":\"操作对象\"},\r\n" + 
			"{\"titleValue\":\"operateType\",\"titleName\":\"操作类型\"},\r\n" + 
			"{\"titleValue\":\"operateTime\",\"titleName\":\"操作时间\"},\r\n" + 
			"{\"titleValue\":\"name\",\"titleName\":\"操作人员\"},\r\n" + 
			"{\"titleValue\":\"result\",\"titleName\":\"操作结果\"}]";
	
	//测试点1：startTime、endTime、operateType为默认值和name的长度为0（"")
	@Test
	public void testDownloadOperateLogListTestName1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点2：startTime、endTime、operateType为默认值和name为null
	@Test
	public void testDownloadOperateLogListTestName2() throws Exception{
		String objValue = "{\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点3：startTime、endTime、operateType为默认值和name为存在的name（"zhanzhan")
	@Test
	public void testDownloadOperateLogListTestName3() throws Exception{
		String objValue = "{\"name\":\"zhanzhan\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点4：startTime、endTime、operateType为默认值和name为不存在的name（"nobody")
	@Test
	public void testDownloadOperateLogListTestName4() throws Exception{
		String objValue = "{\"name\":\"nobody\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	/*
	 * 操作日志下载接口单元测试（startTime）
	 */
	
	//测试点1：name、endTime、operateType为默认值和startTime的长度为0（"")
	@Test
	public void testDownloadOperateLogListTestStartTime1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点2：name、endTime、operateType为默认值和startTime为null
	@Test
	public void testDownloadOperateLogListTestStartTime2() throws Exception{
		String objValue = "{\"name\":\"\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点3：name、endTime、operateType为默认值和startTime为正确格式但期间没有数据（"2020-04-25")
	@Test
	public void testDownloadOperateLogListTestStartTime3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-04-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点4：name、endTime、operateType为默认值和startTime为不正确格式
	@Test
	public void testDownloadOperateLogListTestStartTime4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"123\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点5：name、endTime、operateType为默认值和startTime为正确格式且大于endTime
	@Test
	public void testDownloadOperateLogListTestStartTime5() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-05-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	/*
	 * 登录日志下载接口单元测试（endTime）
	 */
	
	//测试点1：name、startTime、operateType为默认值和endTime的长度为0（"")
	@Test
	public void testDownloadOperateLogListTestEndTime1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点2：name、startTime、operateType为默认值和endTime为null
	@Test
	public void testDownloadOperateLogListTestEndTime2() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点3：name、startTime、operateType为默认值和endTime为正确格式但期间没有数据（"2020-04-25")
	@Test
	public void testDownloadOperateLogListTestEndTime3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-01-25\",\"endTime\":\"2020-02-25\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点4：name、startTime、operateType为默认值和endTime为不正确格式
	@Test
	public void testDownloadOperateLogListTestEndTime4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"123\",\"operateType\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	/*
	 * 操作日志下载接口单元测试（operateType）
	 */
	
	//测试点1：name、endTime、startTime为默认值和operateType的长度为0（"")
	@Test
	public void testDownloadOperateLogListTestType1() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点2：name、endTime、startTime为默认值和operateType为null
	@Test
	public void testDownloadOperateLogListTestType2() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点3：name、endTime、startTime为默认值和operateType为存在的type（增加）
	@Test
	public void testDownloadOperateLogListTestType3() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"增加\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点4：name、endTime、startTime为默认值和type为存在的operateType（删除）
	@Test
	public void testDownloadOperateLogListTestType4() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"删除\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点5：name、endTime、startTime为默认值和type为存在的operateType（修改）
	@Test
	public void testDownloadOperateLogListTestType5() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"修改\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点6：name、endTime、startTime为默认值和type为存在的operateType（查询）
	@Test
	public void testDownloadOperateLogListTestType6() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"查询\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	//测试点7：name、endTime、startTime为默认值和operateType为不存在的operateType（123）
	@Test
	public void testDownloadOperateLogListTestType7() throws Exception{
		String objValue = "{\"name\":\"\",\"startTime\":\"2020-03-25\",\"endTime\":\"2020-04-30\",\"operateType\":\"123\"}";
		System.out.println(systemManagerLogController.downloadOperateLogList(objValue, operateTitleInfos));
		}
	
	/*
	 * 集成日志下载接口单元测试（name）
	 */
	private String integrationTitleInfos = "[{\"titleValue\":\"jobName\",\"titleName\":\"任务名称\"},\r\n" + 
			"{\"titleValue\":\"startDate\",\"titleName\":\"开始时间\"},\r\n" + 
			"{\"titleValue\":\"endDate\",\"titleName\":\"结束时间\"},\r\n" + 
			"{\"titleValue\":\"status\",\"titleName\":\"任务状态\"}]";
	
	//测试点1：startDate、endDate、status为默认值和jobName的长度为0（"")
	@Test
	public void testDownloadIntegrationLogListTestName1() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点2：startDate、endDate、status为默认值和jobName为null
	@Test
	public void testDownloadIntegrationLogListTestName2() throws Exception{
		String objValue = "{\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点3：startDate、endDate、status为默认值和jobName为存在的jobName（"job")
	@Test
	public void testDownloadIntegrationLogListTestName3() throws Exception{
		String objValue = "{\"jobName\":\"job\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点4：startTime、endDate、status为默认值和jobName为不存在的jobName（"noJob")
	@Test
	public void testDownloadIntegrationLogListTestName4() throws Exception{
		String objValue = "{\"jobName\":\"noJob\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	/*
	 * 集成日志下载接口单元测试（startDate）
	 */
	
	//测试点1：jobName、endDate、status为默认值和startDate的长度为0（"")
	@Test
	public void testDownloadIntegrationLogListTestStartDate1() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点2：jobName、endDate、status为默认值和startDate为null
	@Test
	public void testDownloadIntegrationLogListTestStartDate2() throws Exception{
		String objValue = "{\"jobName\":\"\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点3：jobName、endDate、status为默认值和startDate为正确格式但期间没有数据（"2020-04-25")
	@Test
	public void testDownloadIntegrationLogListTestStartDate3() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-04-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点4：jobName、endDate、status为默认值和startDate为不正确格式
	@Test
	public void testDownloadIntegrationLogListTestStartDate4() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"123\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点5：jobName、endDate、status为默认值和startDate为正确格式且大于endDate
	@Test
	public void testDownloadIntegrationLogListTestStartDate5() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-05-25\",\"endDate\":\"2020-04-30\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	/*
	 * 集成日志下载接口单元测试（endDate）
	 */
	
	//测试点1：jobName、startDate、status为默认值和endDate的长度为0（"")
	@Test
	public void testDownloadIntegrationLogListTestEndDate1() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点2：jobName、startDate、status为默认值和endDate为null
	@Test
	public void testDownloadIntegrationLogListTestEndDate2() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点3：jobName、startDate、status为默认值和endDate为正确格式但期间没有数据（"2020-03-26")
	@Test
	public void testDownloadIntegrationLogListTestEndDate3() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-03-26\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点4：jobName、startDate、status为默认值和endDate为不正确格式
	@Test
	public void testDownloadIntegrationLogListTestEndDate4() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"123\",\"status\":\"全部\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	/*
	 * 集成日志下载接口单元测试（status）
	 */
	
	//测试点1：jobName、endDate、startDate为默认值和status的长度为0（"")
	@Test
	public void testDownloadIntegrationLogListTestStatus1() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点2：jobName、endDate、startDate为默认值和status为null
	@Test
	public void testDownloadIntegrationLogListTestStatus2() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点3：jobName、endDate、startDate为默认值和status为存在的status（进行中）
	@Test
	public void testDownloadIntegrationLogListTestStatus3() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"status\":\"进行中\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点4：jobName、endDate、startDate为默认值和status为存在的status（已结束）
	@Test
	public void testDownloadIntegrationLogListTestStatus4() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"已结束\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}
	
	//测试点5：jobName、endDate、startDate为默认值和type为不存在的status（123）
	@Test
	public void testDownloadIntegrationLogListTestStatus5() throws Exception{
		String objValue = "{\"jobName\":\"\",\"startDate\":\"2020-03-25\",\"endDate\":\"2020-04-30\",\"status\":\"123\"}";
		System.out.println(systemManagerLogController.downloadIntegrationLogList(objValue, integrationTitleInfos));
		}

}
