/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.entstatus;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.entstatus
 * @ClassName: IBSRealNameAuthService
 * @Description: 实名认证管理接口
 * 
 * @author: xiaofl
 * @date: 2015-9-6 下午12:24:29
 * @version V1.0
 */
public interface IBSRealNameAuthService {

    /**
     * 创建个人实名认证申请
     * 
     * @param perRealnameAuth
     *            实名认证申请 必填
     * @throws HsException
     */
    public void createPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException;

    /**
     * 修改个人实名认证申请
     * 
     * @param perRealnameAuth
     *            实名认证申请 必填
     * @throws HsException
     */
    public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException;

    /**
     * 查询个人实名认证
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人实名认证列表，没有则返回null
     * @throws HsException
     */
    public PageData<PerRealnameBaseInfo> queryPerRealnameAuth(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException;

    /**
     * 关联工单查询个人实名认证审批
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人实名认证列表，没有则返回null
     * @throws HsException
     */
    public PageData<PerRealnameBaseInfo> queryPerRealnameAuth4Appr(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException;

    /**
     * 通过申请编号查询个人实名认证详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回个人实名认证详情，没有则返回null
     */
    public PerRealnameAuth queryPerRealnameAuthById(String applyId);

    /**
     * 通过客户号查询个人实名认证详情
     * 
     * @param perCustId
     *            个人客户号 必填
     * @return 返回个人实名认证详情，没有则返回null
     */
    public PerRealnameAuth queryPerRealnameAuthByCustId(String perCustId);

    /**
     * 审批个人实名认证
     * 
     * @param apprParam
     *            申报信息 必填
     * @throws HsException
     */
    public void apprPerRealnameAuth(ApprParam apprParam) throws HsException;

    /**
     * 审批复核个人实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void reviewApprPerRealnameAuth(ApprParam apprParam) throws HsException;

    /**
     * 查询个人实名认证办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    public PageData<OptHisInfo> queryPerRealnameAuthHis(String applyId, Page page) throws HsException;

    /**
     * 查询个人实名认证记录
     * 
     * @param perCustId
     *            个人客户号
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @param page
     *            分页信息
     * @return 个人实名认证记录列表
     * @throws HsException
     */
    public PageData<SysBizRecord> queryPerAuthRecord(String perCustId, String startDate, String endDate, Page page)
            throws HsException;

    /**
     * 创建企业实名认证信息
     * 
     * @param entRealnameAuth
     *            企业实名认证信息 必填
     * @throws HsException
     */
    public void createEntRealnameAuth(EntRealnameAuth entRealnameAuth) throws HsException;

    /**
     * 修改企业实名认证申请
     * 
     * @param entRealnameAuth
     *            企业实名认证信息 必填
     * @throws HsException
     */
    public void modifyEntRealnameAuth(EntRealnameAuth entRealnameAuth) throws HsException;

    /**
     * 查询企业实名认证
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业实名认证信息列表，没有则返回null
     * @throws HsException
     */
    public PageData<EntRealnameBaseInfo> queryEntRealnameAuth(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException;

    /**
     * 关联工单查询企业实名认证审批
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业实名认证信息列表，没有则返回null
     * @throws HsException
     */
    public PageData<EntRealnameBaseInfo> queryEntRealnameAuth4Appr(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException;

    /**
     * 通过申请编号查询企业实名认证详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回实名认证详情,没有则返回null
     */
    public EntRealnameAuth queryEntRealnameAuthById(String applyId);

    /**
     * 通过客户号查询企业实名认证详情
     * 
     * @param entCustId
     *            企业客户号 必填
     * @return 返回实名认证详情,没有则返回null
     */
    public EntRealnameAuth queryEntRealnameAuthByCustId(String entCustId);

    /**
     * 审批企业实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void apprEntRealnameAuth(ApprParam apprParam) throws HsException;

    /**
     * 审批复核企业实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    public void reviewApprEntRealnameAuth(ApprParam apprParam) throws HsException;

    /**
     * 查询企业实名认证办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    public PageData<OptHisInfo> queryEntRealnameAuthHis(String applyId, Page page) throws HsException;
}
