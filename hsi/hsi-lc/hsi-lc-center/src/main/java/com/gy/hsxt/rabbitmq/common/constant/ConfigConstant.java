package com.gy.hsxt.rabbitmq.common.constant;



public interface ConfigConstant {
	
	public static final String RABBITMQ_HOSTADDR = "rabbitmq.hostaddr";
	
	public static final String RABBITMQ_PORT = "rabbitmq.port";
	
	public static final String RABBITMQ_USERNAME = "rabbitmq.username";
	
	public static final String RABBITMQ_PASSWD = "rabbitmq.password";
	 
	public static final String RABBITMQ_SYSLOG_QUEUENAME = "rabbitmq.syslog.queuename";
	
	public static final String RABBITMQ_SYSLOG_EXCHANGE = "rabbitmq.syslog.exchange";
	
	public static final String RABBITMQ_SYSLOG_ROUTERKEY = "rabbitmq.syslog.routerKey";
	
	public static final String RABBITMQ_BIZLOG_QUEUENAME = "rabbitmq.bizlog.queuename";
	
	public static final String RABBITMQ_BIZLOG_EXCHANGE = "rabbitmq.bizlog.exchange";
	
	public static final String RABBITMQ_BIZLOG_ROUTERKEY = "rabbitmq.bizlog.routerKey";
	
	public static final String PLATFORM_NAME = "platformName";
	
	public static final String SYSTEM_NAME = "systemName";
	
	public static final String NODE_NAME = "systemNodeName";
	
	public static final String SYSTEM_INSTANCE_NAME = "systemInstanceName";
	
	public static final String RABBITMQ_CHANNEL_COUNT = "rabbitmq.channel.count";
	
	public static final String PUSH_SYSLOG2CENTER = "pushSyslog2Logcenter";
	
	public static final String PUSH_BIZLOG2CENTER = "pushBizlog2Logcenter";
	
	public static final String YES = "1";
	
	public static final String NO = "0";
	/** tianxh add by 2015-08-06  begin **/
	public final static String WHIPPTREE = "-";
	public final static String UNDERLINE = "_";
	public final static String VERTICAL = "|";
	public final static String CR = "\r\n";
	public final static int FIXEDLENGTH = 500;
	public final static String MOUDLENAME = "moudleName";
	public final static String FUNNAME = "funName";
	public final static String BIZLOG_PATH = "bizLogPath";
	public final static String SYSLOG_PATH = "sysLogPath";
	public final static String LOGFILE_TYPE = ".log";
	public final static String DATE_FIELD = "LOGDATE";
	/** tianxh add by 2015-08-06  end **/
	
	public final static String BOOTSTRAPSERVERS = "bootstrap.servers";
	public final static String GROUPID = "group.id";
	public final static String ENABLEAUTOCOMMIT = "enable.auto.commit";
	public final static String AUTOCOMMITINTERVALMS = "auto.commit.interval.ms";
	public final static String SESSIONTIMEOUTMS = "session.timeout.ms";
	public final static String KEYDESERIALIZER = "key.deserializer";
	public final static String VALUEDESERIALIZER = "value.deserializer";
	public final static String DESC = "desc";
	public final static String TOPIC = "topic";
	public final static String TOPICS = "topics";
	public final static String PARTITIONS = "partitions";
	
	public static final String DATE = "时间";
	public static final String PLATFORMNAME = "平台";
	public static final String SYSTEMNAME = "子系统";
	public static final String SYSTEMINSTANCENAME = "子系统实例";
	public static final String IPADDRESS = "主机IP地址";
	public static final String HOSTNAME = "主机名";
	public static final String MONITOR_MOUDLENAME = "业务功能模块";
	public static final String MONITOR_FUNNAME = "业务功能点";
	public static final String MONITORLEVEL = "告警级别";
	public static final String LOGCONTENT = "告警日志信息";
	public static final String EXCEPTIONMESSAGE = "告警详细信息";
	public static final String FILEPATH = "日志查看位置";
	public static final String MONITOR_LIST = "告警日志清单";
	public static final int EXCEL_COLUMNS = 12;

}
