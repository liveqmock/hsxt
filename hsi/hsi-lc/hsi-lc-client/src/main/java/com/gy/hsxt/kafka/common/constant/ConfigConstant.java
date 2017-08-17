package com.gy.hsxt.kafka.common.constant;

public interface ConfigConstant {
	
	public static final String RABBITMQ_HOSTADDR = "rabbitmq.hostaddr";
	
	public static final String KAFKA_HOSTADDR = "kafka.hostaddr";
	
	public static final String RABBITMQ_PORT = "rabbitmq.port";
	
	public static final String KAFKA_PORT = "kafka.port";
	 
	public static final String RABBITMQ_USERNAME = "rabbitmq.username";
	
	public static final String RABBITMQ_PASSWD = "rabbitmq.password";
	
	public static final String RABBITMQ_BIZLOG_QUEUENAME = "rabbitmq.bizlog.queuename";
	
	public static final String RABBITMQ_BIZLOG_EXCHANGE = "rabbitmq.bizlog.exchange";
	
	public static final String RABBITMQ_BIZLOG_ROUTERKEY = "rabbitmq.bizlog.routerKey";
	
	public static final String BIZ_PLATFORM_NAME = "bizName";
	
	public static final String BIZ_SYSTEM_NAME = "bizNodeName";
	
	public static final String BIZ_SYSTEM_INSTANCE_NAME = "bizInstanceName";
	
	public static final String SYS_PLATFORM_NAME = "systemName";
	
	public static final String SYS_SYSTEM_NAME = "systemNodeName";
	
	public static final String SYS_SYSTEM_INSTANCE_NAME = "systemInstanceName";
	
	public static final String RABBITMQ_CHANNEL_COUNT = "rabbitmq.channel.count";
	
	public static final String PUSH_SYSLOG2CENTER = "pushSyslog2Logcenter";
	
	public static final String PUSH_BIZLOG2CENTER = "pushBizlog2Logcenter";
	
	public static final String ISEMAILNOTIFY = "isEmailNotify";
	
	public static final String ISMOBILENOTIFY = "isMobileNotify";
	
	public static final String YES = "1";
	
	public static final String NO = "0";
	
	public static final String VERTICAL = "|";
	
	public final static String CR = "\r\n";
	
	public final static String MONITORLOG_QUEUE = "monitorLog_queue";
	public final static String MONITORLOG_EXCHANGE = "monitorLog_exchange";
	
	public final static String BOOTSTRAPSERVERS = "bootstrap.servers";
	public final static String ACKS = "acks";
	public final static String RETRIES = "retries";
	public final static String BATCHSIZE = "batch.size";
	public final static String LINGERMS = "linger.ms";
	public final static String BUFFERMEMORY = "buffer.memory";
	public final static String KEYSERIALIZER = "key.serializer";
	public final static String VALUESERIALIZER = "value.serializer";
	public final static String PARTITION = "partition";
	public final static String TOPIC = "topic";
	public final static String ISKAFKA = "isKafka";
	public final static String EMAIL = "email";
	public final static String MOBILE = "mobile";

	public final static String BIZSWITCH = "bizSwitch";
	
	public final static String SYSSWITCH = "sysSwitch";
	
	public static final String FILTER_LEVEL = "filterLevel";
}
