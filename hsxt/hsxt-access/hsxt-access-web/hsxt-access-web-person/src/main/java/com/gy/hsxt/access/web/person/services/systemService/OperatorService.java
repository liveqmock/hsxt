/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.systemService;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.PersonBase;

/**
 * 操作员管理服务
 * @Package: com.gy.hsxt.access.web.person.services.systemService  
 * @ClassName: OperatorService 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-28 上午11:35:57 
 * @version V1.0
 */
public interface OperatorService extends IBaseService {

    /**
     * 获取操作员详情
     * @param scsBase
     * @return
     */
    public Map<String, Object> getOperatorDetail(PersonBase personBase,String hs_isCard);
}
