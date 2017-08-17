/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.fss.dubbo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.dubbo.service.IHsDubboService;

/**
 * 抽象的dubbo工厂
 * 
 * @Package : com.gy.hsxt.gpf.fss.dubbo
 * @ClassName : AbstractHsDubboFactory
 * @Description : 抽象的dubbo工厂
 * @Author : chenm
 * @Date : 2016/1/18 20:15
 * @Version V3.0.0.0
 */
public abstract class AbstractHsDubboFactory<T extends IHsDubboService> implements InitializingBean, DisposableBean {

    /**
     * 子系统代码
     */
    protected String systemId;

    /**
     * 引用类clazz
     */
    protected Class<T> clazz;

    /**
     * 无参构造
     */
    public AbstractHsDubboFactory() {
    }

    /**
     * 含参构造函数
     * 
     * @param systemId
     *            子系统代码
     * @param clazz
     *            接口类
     */
    public AbstractHsDubboFactory(String systemId, Class<T> clazz) {
        this.systemId = systemId;
        this.clazz = clazz;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
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
        HsAssert.hasText(systemId, RespCode.PARAM_ERROR, "子系统代码[systemId]不能为空");
        HsAssert.isAssignable(IHsDubboService.class, clazz, RespCode.PARAM_ERROR, "接口类型[clazz]不是IHsDubboService的子类");
        // 获取dubbo配置信息
        HsDubboBulider.setDubboConfigSupport(HsDubboConfigSupport.bulid(systemId));
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
        this.clazz = null;
    }
}
