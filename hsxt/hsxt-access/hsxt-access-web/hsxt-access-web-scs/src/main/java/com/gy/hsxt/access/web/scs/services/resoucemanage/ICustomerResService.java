/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;

/***
 * 消费者资源统计接口类
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.resoucemanage
 * @ClassName: ICustomerResService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-5 下午3:05:51
 * @version V1.0
 */
public interface ICustomerResService extends IBaseService {
    
    /**
     *  消费者关联资源统计
     * @param resNo
     * @return
     */
    public Map<String, Object> getCorrelatStatistics(String resNo);

}
