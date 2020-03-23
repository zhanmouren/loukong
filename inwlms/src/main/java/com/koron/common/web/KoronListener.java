package com.koron.common.web;

import org.swan.PingYin;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.koron.ebs.mybatis.ClassListener;
import org.koron.ebs.mybatis.SessionFactory;

import com.koron.common.bean.StaffBean;
import com.koron.common.web.bean.DepartmentTreeBean;
import com.koron.common.web.mapper.DepartmentMapper;
import com.koron.util.Constant;

public class KoronListener implements ServletContextListener, ClassListener {

	@Override
	public void process(Class<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//	private static WatchService watch = null;
//	private static ServletContext context;
//	private static Timer timer = new Timer("系统配置数据更新");
//	private boolean shutdown = false;
//	static {
//		try {
//			watch = FileSystems.getDefault().newWatchService();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent arg0) {
//		shutdown = true;
//		timer.cancel();
//		Enumeration<Driver> ens = DriverManager.getDrivers();
//		while(ens.hasMoreElements())
//			try {
//				DriverManager.deregisterDriver(ens.nextElement());
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			}
//	}
//
//	public void process(Class<?> clazz) {
//		try {
//			File file = new File(context.getRealPath("/WEB-INF/classes"));
//			String sub = clazz.getName();
//			file = new File(file, sub.substring(0, sub.lastIndexOf('.')).replace('.', File.separatorChar));
//			file.toPath().register(watch, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void contextInitialized(ServletContextEvent arg0) {
//		System.setProperty("FieldBeanProcessor", "com.koron.common.KoronFieldBeanProcessor");
//		SessionFactory.listener = this;
//		context = arg0.getServletContext();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				Constant.fieldCache.clear();
//				Constant.layerCache.clear();
//				Constant.layerFieldCache.clear();
//				Constant.enumCache.clear();
//				//Permission.getInstance().clearCache();
//				Constant.STAFF.clear();
//				try (SessionFactory factory = new SessionFactory();) {
//					DepartmentMapper mapper = factory.getMapper(DepartmentMapper.class);
//					List<StaffBean> list = mapper.getStaffInfo();
//					PingYin parser = PingYin.getInstance();
//					for (StaffBean item : list) {
//						String str = parser.getSelling(item.getLoginid()+item.getName(),true,"")+parser.getSelling(item.getLoginid()+item.getName(),false,"");
//						Constant.STAFF.put(str+item.getName(),item);
//					}
//				}
//				Constant.DEPARTMENT.clear();
//				try (SessionFactory factory = new SessionFactory();) {
//					DepartmentMapper mapper = factory.getMapper(DepartmentMapper.class);
//					List<DepartmentTreeBean> list = mapper.getDepartmentInfo();
//					PingYin parser = PingYin.getInstance();
//					for (DepartmentTreeBean item : list) {
//						String str = parser.getSelling(item.getName(),true,"")+parser.getSelling(item.getName(),false,"");
//						Constant.DEPARTMENT.put(str+item.getName(),item);
//					}
//				}
//			}
//		}, 1000, 1000 * 600);
////		timer.schedule(new OutStoreTask(), 1000, 10000);
//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("监听器启动");
//				try {
//					while (!shutdown) {
//						WatchKey key = watch.poll(10, TimeUnit.SECONDS);
//						if(key == null)
//							continue;
//						List<WatchEvent<?>> pollEvents = key.pollEvents();
//						for (int i = 0; i < pollEvents.size(); i++) {
//							System.out.println("reload db" + pollEvents.get(i).kind());
//							SessionFactory.refresh();
//						}
//						if (!key.reset())
//							break;
//					}
//					watch.close();
//				} catch (InterruptedException | IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		thread.setName("监听数据库文件");
//		thread.start();
//	}
}
