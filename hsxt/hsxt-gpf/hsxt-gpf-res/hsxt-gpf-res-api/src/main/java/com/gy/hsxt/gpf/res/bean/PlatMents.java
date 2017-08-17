/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.bean
 * @ClassName: PlatMent
 * @Description: 管理公司与平台关系
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:52:33
 * @version V1.0
 */
public class PlatMents implements Serializable {

    private static final long serialVersionUID = 8308863185569590807L;

    private List<PlatMent> platMentList;

    public List<PlatMent> getPlatMentList() {
        return platMentList;
    }

    public void setPlatMentList(List<PlatMent> platMentList) {
        this.platMentList = platMentList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
