package com.ums.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ums.common.util.SpringUtil;
import com.ums.task.MainTask;

public class TaskStartListener  implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(TaskStartListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("服务器关闭...");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("服务器启动...");
		//启动主任务线程
		MainTask bean = null;
		try{
			bean = (MainTask) SpringUtil.getBean("mainTask");
		}catch(NullPointerException e) {
			logger.info("获取不到bean");
		}
	    bean.start();
	}
}
