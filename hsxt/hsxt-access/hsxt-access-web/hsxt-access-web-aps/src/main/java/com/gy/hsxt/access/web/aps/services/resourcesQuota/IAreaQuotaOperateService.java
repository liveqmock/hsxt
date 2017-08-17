/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsPlatQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsProvinceQuotaApp;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/***
 * 区域配额操作服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: IAreaQuotaService
 * @Description: TODO 区域配额申请、修改、初始化操作
 * @author: wangjg
 * @date: 2015-11-16 下午2:38:34
 * @version V1.0
 */
public interface IAreaQuotaOperateService extends IBaseService {

    /**
     * 一级区域配额申请
     * 
     * @param apqa
     */
    public void applyPlatQuota(ApsPlatQuotaApp apqa) throws HsException;

    /**
     * 二级区域配额申请
     * 
     * @param request
     * @return
     */
    public void provinceQuotaApply(ApsProvinceQuotaApp apqa) throws HsException;

    /**
     * 二级区域配额审批
     * 
     * @param apqa
     */
    public void provinceQuotaApprove(ApsProvinceQuotaApp apqa) throws HsException;

    /**
     * 三级区域配额修改
     * 
     * @param acqa
     * @param apsBase
     * @throws HsException
     */
    public void cityQuotaUpdate(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException;

    /**
     * 三级区域配额初始化
     * 
     * @param acqa
     * @param apsBase
     * @throws HsException
     */
    public void cityQuotaInit(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException;

    /**
     * 三级区域配额审批
     * @param acqa
     * @param apsBase
     * @throws HsException
     */
    public void cityQuotaApprove(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException;
}
