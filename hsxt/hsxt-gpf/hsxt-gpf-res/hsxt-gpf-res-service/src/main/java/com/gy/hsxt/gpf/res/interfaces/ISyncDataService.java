/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.interfaces;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.bean.QuotaApp;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.interfaces
 * @ClassName: ISyncDataService
 * @Description: 同步数据
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午4:00:23
 * @version V1.0
 */
public interface ISyncDataService {

    /**
     * 同步配额分配到子平台
     * 
     * @param quotaApp
     *            配额分配
     * @throws HsException
     */
    public void syncQuotaAllotData(QuotaApp quotaApp) throws HsException;

    /**
     * 同步路由数据到总平台
     * 
     * @param applyId
     *            申请编号
     * @param platNo
     *            平台代码
     * @param resNoList
     *            批复互生号list
     * @throws HsException
     */
    public void syncRouteData(String applyId, String platNo, List<String> resNoList) throws HsException;

    /**
     * 同步初始化平台公司信息到地区平台
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    public void syncPlatInfo(PlatInfo platInfo) throws HsException;

    /**
     * 同步管理公司信息到用户中心
     * 
     * @param platMent
     *            管理公司信息
     * @throws HsException
     */
    public void syncManageToUc(PlatMent platMent) throws HsException;

    /**
     * 同步管理公司信息到业务系统
     * 
     * @param platMent
     *            管理公司信息
     * @throws HsException
     */
    public void syncManageToBs(PlatMent platMent) throws HsException;
}
