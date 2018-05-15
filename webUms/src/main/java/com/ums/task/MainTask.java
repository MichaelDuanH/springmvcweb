package com.ums.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ums.common.util.StringUtil;
import com.ums.common.util.SysConfig;
@Component
public class MainTask extends Thread{
private static final Logger logger = LoggerFactory.getLogger(MainTask.class);
	
	private long intervalTime = 1000 * 10;
	
	@Override
	public void run() {
		do {
			logger.info("MAC: {}，HAS: {}", StringUtil.getLocalMac(), SysConfig.getWeight());
			logger.info("这是分布式任务的主入口，可以根据需要进行配置后台任务");
			this.ThreadSleep(intervalTime);
		} while(true);
	}
	
	private void ThreadSleep(long msNumber) {
		try {
			Thread.sleep(msNumber);
		} catch (InterruptedException e) {
			logger.info("线程休眠异常");
		}
	}
}
