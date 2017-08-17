package com.gy.hsxt.keyserver.monitor;

import org.apache.log4j.Logger;

//import com.gy.common.log.GyLogger;
import com.gy.hsxt.keyserver.beans.MonitorInfoBean;
import com.gy.hsxt.keyserver.service.IMonitor;

public class MonitorImpl implements IMonitor{

//	private static final GyLogger LOGGER = GyLogger.getLogger(MonitorImpl.class);
	public void SetLogLevel(int logLevel) {
		Logger.getRootLogger().getLoggerRepository()
		.setThreshold(LogLevel.toLever(logLevel));
//		LOGGER.warn("set Log Level to:" + LogLevel.toLever(logLevel));
	}

	public MonitorInfoBean getSystemInfo() {
		MonitorServiceImpl rc = new MonitorServiceImpl();
		return rc.getMonitorInfoBean();
	}
	
}
