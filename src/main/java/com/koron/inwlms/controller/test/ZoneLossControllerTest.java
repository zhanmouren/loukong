package com.koron.inwlms.controller.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.koron.inwlms.bean.DTO.intellectPartition.AutomaticPartitionDTO;
import com.koron.inwlms.bean.DTO.sysManager.UserLoginDTO;
import com.koron.inwlms.bean.DTO.zoneLoss.QueryZoneWBLossDTO;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import com.koron.inwlms.controller.IntellectPartitionController;
import com.koron.inwlms.controller.SystemController;
import com.koron.inwlms.controller.ZoneLossController;
import com.koron.main.App;


/*
 * author:lzy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ZoneLossControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ZoneLossController zoneLossController;
	
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
	 * 查询分区水平衡漏损数据单元测试（timeType）
	 */
	//测试点1：timeType为null，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataTimeType1() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点2：所有参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataTimeType2() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点3：与选择时间不符的timeType(4)，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataTimeType3() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(4);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点4：不正确的timeType(1)，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataTimeType4() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(1);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	/*
	 * 查询分区水平衡漏损数据单元测试（startTime）
	 */
	//测试点1：startTime为null，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataStartTime1() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点2：startTime大于endTime，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataStartTime2() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202007);
		queryZoneWBLossDTO.setEndTime(202006);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点3：startTime为不正确的格式，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataStartTime3() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(-1);
		queryZoneWBLossDTO.setEndTime(202007);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	/*
	 * 查询分区水平衡漏损数据单元测试（endTime）
	 */
	//测试点1：endTime为null，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataEndTime1() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点2：endTime为null，其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataEndTime2() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(-1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	/*
	 * 查询分区水平衡漏损数据单元测试（zoneRank）
	 */
	//测试点1：所有参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataZoneRank1() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		queryZoneWBLossDTO.setZoneRank(1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	//测试点2：zoneRank为-1（不正确的内容），其他参数为正确的内容
	@Test
	public void testQueryZoneWBLossDataZoneRank2() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		queryZoneWBLossDTO.setZoneRank(-1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	/*
	 * 添加全网水平衡报表单元测试（reportName）
	 */
	//测试点1：所有参数为正确的内容
	@Test
	public void testAddWNWBReportReportName1() throws Exception{
		QueryZoneWBLossDTO queryZoneWBLossDTO = new QueryZoneWBLossDTO();
		queryZoneWBLossDTO.setTimeType(3);
		queryZoneWBLossDTO.setStartTime(202006);
		queryZoneWBLossDTO.setEndTime(202007);
		queryZoneWBLossDTO.setZoneRank(1);
		UserVO user = new UserVO();
		user.setEnv("mz"+"_default");
		user.setCode("08ee448d0e014926905af8d40455877f");
		System.out.println(zoneLossController.queryZoneWBLossData(queryZoneWBLossDTO, user));
	}
	
	@Test
	public void testCheckWNWBReportListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadWNWBReport() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteWNWBReport() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadWNWBReportList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddWNWBReport() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateWNWBReport() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNWBReportDetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNWBReporFileList() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteWNWBReporFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNWBIndicatorData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNWBTReportList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckWNWBTReportListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadWNWBReporTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteWNWBReporTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddWNWBReporTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateWNWBReporTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNWBTReportDetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckFZoneLossListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadFZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckSZoneLossListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadSZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckDmaZoneLossListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadDmaZoneLossList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZonelocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySFZonelocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZonelocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZonelocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZonedetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySZonedetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZonedetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryVZonedetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZonedetail() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZoneHstData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySZoneHstData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZoneHstData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryVZoneHstData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZoneHstData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryVSZoneList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckVSZoneListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadVSZoneHstList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryVCZoneList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckVCZoneListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadVCZoneList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVCZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteVCZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNLeakMapList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWNLeakList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZoneIndicatorDic() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZoneThematicValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySZoneThematicValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZoneThematicValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZoneThematicValue() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFZoneIndicatorList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySZoneIndicatorList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryDmaZoneIndicatorList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZoneIndicatorList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckZoneIndicatorListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadZoneIndicatorList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryWBIndicatorData() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryLeakageExponent() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateLeakageExponent() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryLegitimateNightUseList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckLegitimateNightUseListParam() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadLegitimateNightUseList() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateLegitimateNightUse() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryZoneHstIndicatorDic() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryBurstLeakAnalysis() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryBurstLeakById() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryBurstLeakList() {
		fail("Not yet implemented");
	}

	@Test
	public void testDownloadBurstLeakList() {
		fail("Not yet implemented");
	}

}
