/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.fss.dubbo;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * dubbo配置支持类
 * 
 * @Package : com.gy.hsxt.gpf.fss.dubbo
 * @ClassName : HsDubboConfig
 * @Description : dubbo配置支持类
 * @Author : chenm
 * @Date : 2016/1/16 16:45
 * @Version V3.0.0.0
 */
public class HsDubboConfigSupport {

    /**
     * 应用名称
     */
    public static final String DUBBO_APPLICATION_NAME = "dubbo.application.name";

    /**
     * 注册地址
     */
    public static final String DUBBO_REGISTRY_ADDRESS = "dubbo.registry.address";

    /**
     * 服务协议
     */
    public static final String DUBBO_PROTOCOL = "dubbo.protocol";

    /**
     * 服务端口
     */
    public static final String DUBBO_PROTOCOL_PORT = "dubbo.protocol.port";

    /**
     * 服务端消息处理线程池类型，默认fixed 可选fixed/cached
     */
    public static final String DUBBO_PROTOCOL_THREADPOOL_TYPE = "dubbo.protocol.threadpool.type";

    /**
     * 服务端消息处理线程池大小,默认100
     */
    public static final String DUBBO_PROTOCOL_THREADPOOL_THREADS = "dubbo.protocol.threadpool.threads";

    /**
     * 服务器端最大可接收的连接数，用于服务方自我保护
     */
    public static final String DUBBO_PROTOCOL_ACCEPTS = "dubbo.protocol.accepts";

    /**
     * 每个客户端服务对每个服务提供者建立的连接数
     */
    public static final String DUBBO_REFER_CONNECTIONS = "dubbo.reference.connections";

    /**
     * 服务调用超时重发次数
     */
    public static final String DUBBO_REFER_RETIRES = "dubbo.reference.retires";

    /**
     * 限制某个服务的每个方法的最大并发调用数
     */
    public static final String DUBBO_SERV_ACTIVES = "dubbo.service.actives";

    /**
     * 服务调用超时时间5000
     */
    public static final String DUBBO_SERV_TIMEOUT5000 = "dubbo.service.timeout5000";

    /**
     * 服务调用超时时间10000
     */
    public static final String DUBBO_SERV_TIMEOUT10000 = "dubbo.service.timeout10000";

    /**
     * 服务调用超时时间15000
     */
    public static final String DUBBO_SERV_TIMEOUT15000 = "dubbo.service.timeout15000";

    /**
     * 服务调用超时时间30000
     */
    public static final String DUBBO_SERV_TIMEOUT30000 = "dubbo.service.timeout30000";

    /**
     * dubbo配置文件名称
     */
    public static final String DUBBO_PROPERTIES = "dubbo.properties";

    /**
     * 配置文件根路径
     */
    public static final String USER_DIR = "user.dir";

    /**
     * properties结尾
     */
    public static final String PROPERTIES_EXTENSION = "properties";

    /**
     * dubbo属性容器
     */
    private Properties properties = new Properties();

    /**
     * 私有构造器
     * 
     * @param systemId
     *            子系统代码
     */
    private HsDubboConfigSupport(String systemId) {
        try
        {
            // 获取文件根路径
            File root = new File(System.getProperty(USER_DIR));
            // 获取根路径下所有的properties 文件
            Collection<File> files = FileUtils.listFiles(root, new String[] { PROPERTIES_EXTENSION }, true);
            for (File f : files)
            {
                // 筛选出dubbo.properties
                String path = StringUtils.lowerCase(f.getPath());
                if (path.endsWith(DUBBO_PROPERTIES) && path.indexOf(systemId) > 0)
                {
                    PropertiesLoaderUtils.fillProperties(properties, new FileSystemResource(f));
                    break;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("============初始化dubbo的properties失败:[" + e.getMessage() + "]==============");
        }
    }

    /**
     * 构建dubbo配置
     * 
     * @param systemId
     *            子系统代码
     * @return bean
     */
    public static HsDubboConfigSupport bulid(String systemId) {
        HsAssert.hasText(systemId, RespCode.PARAM_ERROR, "子系统代码[systemId]必须配置");
        return new HsDubboConfigSupport(StringUtils.lowerCase(systemId));
    }

    /**
     * 获取dubbo的属性
     * 
     * @param key
     *            键
     * @return 值
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
