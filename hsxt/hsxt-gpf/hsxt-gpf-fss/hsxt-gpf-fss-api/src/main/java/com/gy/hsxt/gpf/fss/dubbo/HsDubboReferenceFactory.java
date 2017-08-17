/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.fss.dubbo;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.api.IOtherNotifyService;
import com.gy.hsxt.gpf.fss.dubbo.service.IHsDubboService;

/**
 * dubbo 注册实例引用工厂
 * 
 * @Package : com.gy.hsxt.gpf.fss.dubbo
 * @ClassName : HsDubboReferenceFactory
 * @Description : dubbo 注册实例引用工厂
 * @Author : chenm
 * @Date : 2016/1/18 20:16
 * @Version V3.0.0.0
 */
public class HsDubboReferenceFactory extends AbstractHsDubboFactory<IOtherNotifyService> {

    /**
     * 无参构造
     */
    public HsDubboReferenceFactory() {
    }

    /**
     * 含参构造函数
     * 
     * @param systemId
     *            子系统代码
     * @param clazz
     *            接口类
     */
    public HsDubboReferenceFactory(String systemId, Class<IOtherNotifyService> clazz) {
        super(systemId, clazz);
    }

    /**
     * 获取接口引用
     * 
     * @param group
     *            群组
     * @return ReferenceConfig {@link ReferenceConfig}
     */
    public ReferenceConfig<IOtherNotifyService> getReference(String group) {
        HsAssert.hasText(systemId, RespCode.PARAM_ERROR, "子系统代码[systemId]不能为空");
        return HsDubboBulider.bulidReference(this.clazz, group);
    }

    /**
     * 返回代理实例引用
     * 
     * @param group
     *            群组
     * @return T extends {@link IHsDubboService}
     */
    public IOtherNotifyService getBean(String group) {
        return getReference(group).get();
    }
}
