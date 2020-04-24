package com.koron.indicator.task;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	@Scheduled(cron = "0 5 * * * ?") // 整点5分执行		
	public void calHourIndicatorTask() {
		//测试
		System.out.println("执行小时指标计算任务......");
		int i = 0;
		while(i<100) {
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
	@Scheduled(cron = "0 30 0 * * ?") // 每天零点30分执行		
	public void calDayIndicatorTask() {
		
	}
	
	/**
	 * 定时计算月指标
	 * 时间为：每月1号凌晨1点
	 */
	@Scheduled(cron = "0 0 1 1 * ?") // 每月1号凌晨1点		
	public void calMonthIndicatorTask() {
		
	}
	
	/**
	 * 定时计算年指标
	 * 时间为：每月1号凌晨1点30分
	 */
	@Scheduled(cron = "0 30 1 1 * ?") // 每月1号凌晨1点30分		
	public void calYearIndicatorTask() {
		
	}
}
