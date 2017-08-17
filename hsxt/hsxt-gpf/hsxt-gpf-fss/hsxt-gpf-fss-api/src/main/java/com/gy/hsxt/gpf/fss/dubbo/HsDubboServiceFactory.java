/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.fss.dubbo;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.api.IOtherNotifyService;

/**
 * dubbo 注册服务工厂
 * 
 * @Package : com.gy.hsxt.gpf.fss.dubbo
 * @ClassName : DubboServiceFactory
 * @Description : dubbo 注册服务工厂
 * @Author : chenm
 * @Date : 2016/1/16 11:53
 * @Version V3.0.0.0
 */
public class HsDubboServiceFactory extends AbstractHsDubboFactory<IOtherNotifyService> {

    /**
     * 实现引用
     */
    private IOtherNotifyService interfaceRef;

    /**
     * 无参构造
     */
    public HsDubboServiceFactory() {
        super();
    }

    /**
     * 含参构造函数
     * 
     * @param systemId
     *            子系统代码
     * @param clazz
     *            接口类
     * @param interfaceRef
     *            接口实现引用
     */
    public HsDubboServiceFactory(String systemId, Class<IOtherNotifyService> clazz, IOtherNotifyService interfaceRef) {
        super(systemId, clazz);
        this.interfaceRef = interfaceRef;
    }

    public void setInterfaceRef(IOtherNotifyService interfaceRef) {
        this.interfaceRef = interfaceRef;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>
     * This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an exception
     * in the event of misconfiguration.
     * 
     * @throws Exception
     *             in the event of misconfiguration (such as failure to set an
     *             essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        HsAssert.notNull(interfaceRef, RespCode.PARAM_ERROR, "接口实例引用[interfaceRef]不能为空");
        // 构建并注册服务
        HsDubboBulider.bulidService(clazz, interfaceRef, systemId).export();
    }

    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     * 
     * @throws Exception
     *             in case of shutdown errors. Exceptions will get logged but
     *             not rethrown to allow other beans to release their resources
     *             too.
     */
    @Override
    public void destroy() throws Exception {
        super.destroy();
        this.interfaceRef = null;
    }
}
