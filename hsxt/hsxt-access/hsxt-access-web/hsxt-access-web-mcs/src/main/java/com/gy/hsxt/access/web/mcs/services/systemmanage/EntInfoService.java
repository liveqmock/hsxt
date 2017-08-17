/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.systemmanage;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/**
 * 查询平台（企业）信息服务
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage  
 * @ClassName: EntInfoService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-13 下午7:42:42 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface EntInfoService extends IBaseService {

    /**
     * 查询平台信息
     * @return
     * @throws HsException
     */
    public  AsEntInfo  searchRegionalPlatform () throws HsException;
}
