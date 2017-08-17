/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.entstatus;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.ChangeInfoQueryParam;
import com.gy.hsxt.bs.bean.entstatus.EntChangeBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.EntChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.entstatus
 * @ClassName: IBSChangeInfoService
 * @Description: 重要信息变更接口
 * 
 * @author: xiaofl
 * @date: 2015-9-6 下午2:07:09
 * @version V1.0
 */
public interface IBSChangeInfoService {

    /**
     * 创建个人消费者重要信息变更申请
     * 
     * @param perChangeInfo
     *            个人消费者重要信息变更 必填
     * @throws HsException
     */
    public void createPerChange(PerChangeInfo perChangeInfo) throws HsException;

    /**
     * 修改个人消费者重要信息变更申请
     * 
     * @param perChangeInfo
     *            个人消费者重要信息变更 必填
     * @throws HsException
     */
    public void modifyPerChange(PerChangeInfo perChangeInfo) throws HsException;

    /**
     * 查询个人重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人重要信息变更列表，没有则返回null
     * @throws HsException
     */
    public PageData<PerChangeBaseInfo> queryPerChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException;

    /**
     * 关联工单查询个人重要信息变更审批
     * 
     * @param changeInfoQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人重要信息变更列表，没有则返回null
     * @throws HsException
     */
    public PageData<PerChangeBaseInfo> queryPerChange4Appr(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException;

    /**
     * 通过申请编号查询个人重要信息变更详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回查询个人重要信息变更详情，没有则返回null
     */
    public PerChangeInfo queryPerChangeById(String applyId);

    /**
     * 通过客户号查询个人重要信息变更详情
     * 
     * @param perCustId
     *            个人客户号 必填
     * @return 返回查询个人重要信息变更详情，没有则返回null
     */
    public PerChangeInfo queryPerChangeByCustId(String perCustId);

    /**
     * 审批个人重要信息变更
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void apprPerChange(ApprParam apprParam) throws HsException;

    /**
     * 审批复核个人重要信息变更
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void reviewApprPerChange(ApprParam apprParam) throws HsException;

    /**
     * 查看个人重要信息变更办理状态
     * 
     * @param applyId
     *            申请编号 必填
     * @param page
     *            分页 必填
     * @return 个人重要信息变更办理状态
     */
    public PageData<OptHisInfo> queryPerChangeHis(String applyId, Page page) throws HsException;

    /**
     * 查询个人重要信息变更记录
     * 
     * @param perCustId
     *            个人客户号
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @param page
     *            分页信息
     * @return 个人重要信息变更记录列表
     * @throws HsException
     */
    public PageData<SysBizRecord> queryPerChagneRecord(String perCustId, String startDate, String endDate, Page page)
            throws HsException;

    /**
     * 创建企业重要信息变更
     * 
     * @param entChangeInfo
     *            企业重要信息变更 必填
     * @throws HsException
     */
    public void createEntChange(EntChangeInfo entChangeInfo) throws HsException;

    /**
     * 修改企业重要信息变更申请
     * 
     * @param entChangeInfo
     *            企业重要信息变更 必填
     * @throws HsException
     */
    public void modifyEntChange(EntChangeInfo entChangeInfo) throws HsException;

    /**
     * 企业查询重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    public PageData<EntChangeBaseInfo> entQueryChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台查询企业重要信息变更
     * 
     * @param changeInfoQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    public PageData<EntChangeBaseInfo> platQueryEntChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台关联工单查询企业重要信息变更审批
     * 
     * @param changeInfoQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    public PageData<EntChangeBaseInfo> platQueryEntChange4Appr(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException;

    /**
     * 通过申请编号查询企业重要信息变更详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回重要信息变更详情，没有则返回null
     */
    public EntChangeInfo queryEntChangeById(String applyId);

    /**
     * 通过客户号查询企业重要信息变更详情
     * 
     * @param entCustId
     *            企业客户号 必填
     * @return 返回重要信息变更详情，没有则返回null
     */
    public EntChangeInfo queryEntChangeByCustId(String entCustId);

    /**
     * 审批重要信息变更
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void apprEntChange(ApprParam apprParam) throws HsException;

    /**
     * 审批复核重要信息变更
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void reviewApprEntChange(ApprParam apprParam) throws HsException;

    /**
     * 查看企业重要信息变更办理状态
     * 
     * @param applyId
     *            申请编号 必填
     * @param page
     *            分页信息
     * @return 企业重要信息变更办理状态
     */
    public PageData<OptHisInfo> queryEntChangeHis(String applyId, Page page) throws HsException;
}
