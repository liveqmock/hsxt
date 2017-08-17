package com.gy.hsxt.keyserver.service;

import com.gy.hsxt.keyserver.beans.MonitorInfoBean;

public interface IMonitor {
	void SetLogLevel(int logLevel);
	MonitorInfoBean getSystemInfo();
}
