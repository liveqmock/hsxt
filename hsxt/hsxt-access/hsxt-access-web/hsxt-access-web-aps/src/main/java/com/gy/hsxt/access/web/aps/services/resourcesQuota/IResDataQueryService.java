/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.common.exception.HsException;

/***
 * 资源数据查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: IResDataQueryService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-16 下午2:36:47
 * @version V1.0
 */
public interface IResDataQueryService extends IBaseService {

    /**
     * 统计管理公司下的资源数据
     * @param mEntResNo
     * @return
     * @throws HsException
     */
    public QuotaStatOfManager getListTable(String mEntResNo) throws HsException;

    /**
     * 资源配额查询
     * @param provinceNo
     * @param cityNo
     * @return
     * @throws HsException
     */
    public QuotaDetailOfProvince resQuotaQuery(String provinceNo, String cityNo) throws HsException;

}
