/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
public interface InfoManageService extends IBaseService {

    /**
     * 分页查询要审批的结果列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public abstract PageData<?> queryApprResult(Map filterMap, Map sortMap, Page page) throws HsException;
}
