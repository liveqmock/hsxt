/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.disconf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

@Component
@Scope("singleton")
//@DisconfFile(filename = "hsxt-tm.properties")
@DisconfUpdateService(classes = { TmConfig.class })
public class TmConfig implements IDisconfUpdate {

    /**
     * 集群部署应用节点编号
     */
    private String appNode;

    /**
     * 紧急工单超时时间
     */
    private Integer timeOut;

    @DisconfFileItem(name = "system.instance.no", associateField = "appNode")
    public String getAppNode() {
        return appNode;
    }

    public void setAppNode(String appNode) {
        this.appNode = appNode;
    }

    @DisconfFileItem(name = "urgency_task.timeout", associateField = "timeOut")
    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public void reload() throws Exception {
        // TODO Auto-generated method stub

    }
}
