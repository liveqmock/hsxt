/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.dubbo;

import com.alibaba.dubbo.config.*;
import org.apache.commons.lang3.math.NumberUtils;

import static com.gy.hsxt.gpf.fss.dubbo.HsDubboConfigSupport.*;

/**
 * dubbo 元素构建工具
 *
 * @Package : com.gy.hsxt.gpf.fss.dubbo
 * @ClassName : HsDubboBulider
 * @Description : dubbo 元素构建工具
 * @Author : chenm
 * @Date : 2016/1/16 18:23
 * @Version V3.0.0.0
 */
public abstract class HsDubboBulider {

    /**
     * 默认版本号
     */
    public static final String DEFAULT_VERSION = "1.0.0";

    /**
     * 配置支持类
     */
    private volatile static HsDubboConfigSupport dubboConfigSupport;

    /**
     * 设置配置支持类
     *
     * @param support 配置支持类
     */
    public static void setDubboConfigSupport(HsDubboConfigSupport support) {
        if (dubboConfigSupport == null) {
            dubboConfigSupport = support;
        }
    }


    /**
     * 构建dubbo服务配置
     *
     * @param clazz 接口类
     * @param ref   接口实现
     * @param group 群组
     * @param <T>   泛型
     * @return @{link ServiceConfig}
     */
    public static <T> ServiceConfig<T> bulidService(Class<T> clazz, T ref, String group) {
        ServiceConfig<T> serviceConfig = new ServiceConfig<>();
        //设置接口类
        serviceConfig.setInterface(clazz);
        //bean 引用
        serviceConfig.setRef(ref);
        //设置分组
        serviceConfig.setGroup(group);
        //设置版本号
        serviceConfig.setVersion(DEFAULT_VERSION);
        //默认设置超时链接为15秒
        serviceConfig.setTimeout(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_SERV_TIMEOUT15000)));
        //设置注册配置
        serviceConfig.setRegistry(bulidRegistry());
        //设置应用信息
        serviceConfig.setApplication(bulidApplication());
        //设置协议信息
        serviceConfig.setProtocol(bulidProtocol());
        //限制某个服务的每个方法的最大并发调用数
        serviceConfig.setActives(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_SERV_ACTIVES)));
        return serviceConfig;
    }

    /**
     * 构建dubbo 引用@{link ReferenceConfig}
     *
     * @param clazz 接口类
     * @param group 群组
     * @param <T>   泛型
     * @return @{link ReferenceConfig}
     */
    public static <T> ReferenceConfig<T> bulidReference(Class<T> clazz, String group) {
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        //设置接口类
        referenceConfig.setInterface(clazz);
        //设置群组
        referenceConfig.setGroup(group);
        //设置每个客户端服务对每个服务提供者建立的连接数
        referenceConfig.setConnections(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_REFER_CONNECTIONS)));
        //设置服务调用超时重发次数
        referenceConfig.setRetries(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_REFER_RETIRES)));
        //设置版本
        referenceConfig.setVersion(DEFAULT_VERSION);
        //
        referenceConfig.setRegistry(bulidRegistry());

        referenceConfig.setApplication(bulidApplication());

        referenceConfig.setProtocol(dubboConfigSupport.getProperty(DUBBO_PROTOCOL));

        referenceConfig.setCheck(false);

        return referenceConfig;
    }

    /**
     * 构建应用信息配置
     *
     * @return @{link ApplicationConfig}
     */
    private static ApplicationConfig bulidApplication() {
        //新建应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        //设置应用配置名称
        applicationConfig.setName(dubboConfigSupport.getProperty(DUBBO_APPLICATION_NAME));
        //应用版本
        applicationConfig.setVersion(DEFAULT_VERSION);

        return applicationConfig;
    }

    /**
     * 构建注册信息配置
     *
     * @return @{link RegistryConfig}
     */
    private static RegistryConfig bulidRegistry() {
        RegistryConfig registryConfig = new RegistryConfig();
        //注册地址
        registryConfig.setAddress(dubboConfigSupport.getProperty(DUBBO_REGISTRY_ADDRESS));

        return registryConfig;
    }


    /**
     * 构建协议信息配置
     *
     * @return @{link ProtocolConfig}
     */
    private static ProtocolConfig bulidProtocol() {
        //协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        //协议名称
        protocolConfig.setName(dubboConfigSupport.getProperty(DUBBO_PROTOCOL));
        //端口
        protocolConfig.setPort(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_PROTOCOL_PORT)));
        //线程池类型
        protocolConfig.setThreadpool(dubboConfigSupport.getProperty(DUBBO_PROTOCOL_THREADPOOL_TYPE));
        //线程数
        protocolConfig.setThreads(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_PROTOCOL_THREADPOOL_THREADS)));
        //服务器端最大可接收的连接数
        protocolConfig.setAccepts(NumberUtils.createInteger(dubboConfigSupport.getProperty(DUBBO_PROTOCOL_ACCEPTS)));

        return protocolConfig;
    }
}
