package com.zach.gasTrade.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zach.gasTrade.service.OrderFinanceStatisticsService;

@Component
public class TaskSchedule {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderFinanceStatisticsService orderFinanceStatisticsService;

	// 每天凌晨2点执行
	@Scheduled(cron = "0 0 2 * * ? ")
	public void perDayStatisticsTask() {
		logger.info("每天凌晨定时执行订单相关统计任务");
		// System.out.println("使用SpringMVC框架配置定时任务");
		orderFinanceStatisticsService.save();
	}

}
