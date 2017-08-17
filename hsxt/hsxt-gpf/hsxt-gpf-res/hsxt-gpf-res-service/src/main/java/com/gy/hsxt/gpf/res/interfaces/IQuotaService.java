/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.interfaces;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.ApprParam;
import com.gy.hsxt.gpf.res.bean.IdType;
import com.gy.hsxt.gpf.res.bean.QuotaApp;
import com.gy.hsxt.gpf.res.bean.QuotaAppBaseInfo;
import com.gy.hsxt.gpf.res.bean.QuotaAppInfo;
import com.gy.hsxt.gpf.res.bean.QuotaAppParam;
import com.gy.hsxt.gpf.res.bean.QuotaStatOfMent;
import com.gy.hsxt.gpf.res.bean.QuotaStatOfPlat;
import com.gy.hsxt.gpf.um.bean.GridData;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.interfaces
 * @ClassName: IQuotaService
 * @Description: 配额管理
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:59:56
 * @version V1.0
 */
public interface IQuotaService {

    /**
     * 保存地区平台的一级配额申请数据
     * 
     * @param quotaApp
     *            一级配额申请
     * @throws HsException
     */
    public void addQuotaApp4AreaPlat(QuotaApp quotaApp) throws HsException;

    /**
     * 统计查询管理公司配额
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页信息
     * @return 管理公司配额统计
     * @throws HsException
     */
    public GridData<QuotaStatOfMent> queryQuotaStatOfMent(String entResNo, String entCustName, Page page)
            throws HsException;

    /**
     * 统计查询管理公司在地区平台上的配额
     * 
     * @param entResNo
     *            管理公司互生号
     * @param page
     *            分页信息
     * @return 管理公司在地区平台上的配额
     * @throws HsException
     */
    public GridData<QuotaStatOfPlat> queryQuotaStatOfPlat(String entResNo, Page page) throws HsException;

    /**
     * 申请配额
     * 
     * @param quotaApp
     *            申请配额信息
     * @throws HsException
     */
    public void applyQuota(QuotaApp quotaApp) throws HsException;

    /**
     * 查询配额申请
     * 
     * @param param
     *            查询条件
     * @param page
     *            分页
     * @return
     */
    public GridData<QuotaAppBaseInfo> queryQuotaApplyList(QuotaAppParam param, Page page) throws HsException;

    /**
     * 审批配额申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void apprQuotaApp(ApprParam apprParam) throws HsException;

    /**
     * 查询互生号占用情况
     * 
     * @param manageEntResNo
     *            管理公司互生号
     * @param applyId
     *            申请编号
     * @return
     */
    public List<IdType> queryIdTyp(String manageEntResNo, String applyId);

    /**
     * 查询配额申请
     * 
     * @param applyId
     *            申请编号
     * @return
     */
    public QuotaAppInfo queryQuotaAppInfo(String applyId);

    /**
     * 同步配额数据到地区平台业务系统(BS)
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    public void syncQuotaAllot(String applyId) throws HsException;

    /**
     * 同步路由数据到总平台全局配置系统(GCS)
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    public void syncQuotaRoute(String applyId) throws HsException;

}
