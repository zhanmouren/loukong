package com.koron.indicator.task;

import java.util.Date;

import org.koron.ebs.mybatis.ADOConnection;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.koron.indicator.bean.CalZoneInfos;
import com.koron.indicator.service.ZoneLossIndicatorService;
import com.koron.inwlms.bean.VO.apparentLoss.ALOverviewDataVO;
import com.koron.inwlms.service.common.impl.GisZoneServiceImpl;
import com.koron.inwlms.util.TimeUtil;

/**
 * 定时任务，用于定时计算指标，包括：小时，日，月，年四种指标
 * @author csh
 * @Date 2020/04/24
 *
 */
@Component
@Lazy(false)
public class TimeTask {

	/**
	 * 定时计算小时指标
	 * 时间为：整点05分
	 */
//	@Scheduled(cron = "0 5 * * * ?") // 整点5分执行		
	public void calHourIndicatorTask() {
		//测试
		System.out.println("执行小时指标计算任务......");
		int i = 0;
		while(i<10) {
			i++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("开始执行.......");
		}
		System.out.println("结束执行.......");
	}
	
	/**
	 * 定时计算日指标
	 * 时间为：每天零点30分
	 */
//	@Scheduled(cron = "0 30 0 * * ?") // 每天零点30分执行		
	public void calDayIndicatorTask() {
		//1.获取分区信息，包括：一级，二级，DMA，虚拟分区
		CalZoneInfos calZoneInfos = new GisZoneServiceImpl().getZoneInfos();
		//2.获取计算时间
		Date preDay = TimeUtil.getPreDay();
		//3.调用所有日指标计算接口
		//基础数据指标
		//监测点监测数据指标
		//分区监测数据指标
		ZoneLossIndicatorService zlis = new ZoneLossIndicatorService();
		ADOConnection.runTask(zlis, "calDMnf", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDMnfNoBigUser", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDAznp", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLegitimateNightUse", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDHourDayFactor", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLeakage", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLeakageRate", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLeakagePerCustomer", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLeakagePerPipeLength", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDMnfTdf", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLossRate", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLossPerCustomerAccount", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLossPerPipeLength", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLossPerPipeLengthPerUnitPress", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDLossPerCustomerAccountPerUnitPress", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDNrwRate", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDNrwPerPipeLength", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDNrwPerCustomerAccount", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDUfwcRate", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDUfwcPerPipeLength", Void.class,calZoneInfos, preDay);
		ADOConnection.runTask(zlis, "calDUfwcPerCustomerAccount", Void.class,calZoneInfos, preDay);
		
		//水平衡基础数据指标
		//分区漏损数据指标
		//表观漏损指标
		//爆管/渗漏指标
		
	}
	
	/**
	 * 定时计算月指标
	 * 时间为：每月1号凌晨1点
	 */
//	@Scheduled(cron = "0 0 1 1 * ?") // 每月1号凌晨1点		
	public void calMonthIndicatorTask() {
		//1.获取分区信息，包括：一级，二级，DMA，虚拟分区
		CalZoneInfos calZoneInfos = new GisZoneServiceImpl().getZoneInfos();
		//2.获取计算时间
		Integer preMonth = TimeUtil.getPreMonth();
		//3.调用所有月指标计算接口
		//基础数据指标
		//监测点监测数据指标
		//分区监测数据指标
		ZoneLossIndicatorService zlis = new ZoneLossIndicatorService();
		ADOConnection.runTask(zlis, "calMMnf", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMMnfNoBigUser", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMAznp", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLegitimateNightUse", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLeakage", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLeakageRate", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLeakagePerCustomer", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLeakagePerPipeLength", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMMnfTdf", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLossRate", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLossPerCustomerAccount", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLossPerPipeLength", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLossPerPipeLengthPerUnitPress", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMLossPerCustomerAccountPerUnitPress", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMNrwRate", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMNrwPerPipeLength", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMNrwPerCustomerAccount", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMUfwcRate", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMUfwcPerPipeLength", Void.class,calZoneInfos, preMonth);
		ADOConnection.runTask(zlis, "calMUfwcPerCustomerAccount", Void.class,calZoneInfos, preMonth);
		
		//水平衡基础数据指标
		//分区漏损数据指标
		//表观漏损指标
		//爆管/渗漏指标		
	}
	
	/**
	 * 定时计算年指标
	 * 时间为：每月1号凌晨1点30分
	 */
//	@Scheduled(cron = "0 30 1 1 * ?") // 每月1号凌晨1点30分		
	public void calYearIndicatorTask() {
		//1.获取分区信息，包括：一级，二级，DMA，虚拟分区
		CalZoneInfos calZoneInfos = new GisZoneServiceImpl().getZoneInfos();
		//2.获取计算时间
		Integer year = TimeUtil.getPreOrCurrentYear();
		//3.调用所有月指标计算接口
		//基础数据指标
		//监测点监测数据指标
		//分区监测数据指标
		ZoneLossIndicatorService zlis = new ZoneLossIndicatorService();
		ADOConnection.runTask(zlis, "calYMnf", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYMnfNoBigUser", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLegitimateNightUse", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLeakage", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLeakageRate", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLeakagePerCustomer", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLeakagePerPipeLength", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYMnfTdf", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLossRate", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLossPerCustomerAccount", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLossPerPipeLength", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLossPerPipeLengthPerUnitPress", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYLossPerCustomerAccountPerUnitPress", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYNrwRate", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYNrwPerPipeLength", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYNrwPerCustomerAccount", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYUfwcRate", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYUfwcPerPipeLength", Void.class,calZoneInfos, year);
		ADOConnection.runTask(zlis, "calYUfwcPerCustomerAccount", Void.class,calZoneInfos, year);
		
		//水平衡基础数据指标
		//分区漏损数据指标
		//表观漏损指标
		//爆管/渗漏指标				
	}
}
