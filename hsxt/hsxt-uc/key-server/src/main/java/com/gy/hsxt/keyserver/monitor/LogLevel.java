package com.gy.hsxt.keyserver.monitor;


import org.apache.log4j.Level;

public enum LogLevel {
	OFF(0),FATAL(1),ERROR(2),WARN(3),INFO(4),DEBUG(5),TRACE(6),ALL(7);

	private int code;

	LogLevel(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	public static Level toLever(int logLevel) {
		switch (logLevel) {
		case 0:
			return Level.OFF;
		case 1:
			return Level.FATAL;
		case 2:
			return Level.ERROR;
		case 3:
			return Level.WARN;
		case 4:
			return Level.INFO;
		case 5:
			return Level.DEBUG;
		case 6:
			return Level.TRACE;
		case 7:
			return Level.ALL;
		}
		return Level.DEBUG;
	}

	public static LogLevel getStatus(int code) {
		for (LogLevel e : LogLevel.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
	
}
