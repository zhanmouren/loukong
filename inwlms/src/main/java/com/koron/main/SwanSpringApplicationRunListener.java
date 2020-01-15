package com.koron.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class SwanSpringApplicationRunListener implements SpringApplicationRunListener {
	public SwanSpringApplicationRunListener(SpringApplication app, String[] args) {
	}

	@Override
	public void starting() {
		System.out.println("now start!!!");
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {

	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		// 服务启动完成后，应用结构的根节点需要创建（若已创建则跳过）
//		boolean tf = ADOConnection.runTask(new ApplicationService(), "isExitAppRoot", Boolean.class);
//		if (!tf) {
//			LongTreeBean child = new LongTreeBean();
//			child.setType(AppConstant.TYPE_APPLICATIONTREE).setForeignkey(AppConstant.APP_ROOT_NAME);
//			ADOConnection.runTask(TreeService.class, "addNode", LongTreeBean.class, null,child);
//		}
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		// TODO Auto-generated method stub
	}

}
