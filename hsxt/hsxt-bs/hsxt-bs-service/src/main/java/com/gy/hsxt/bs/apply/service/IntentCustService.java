/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.apply.IBSIntentCustService;
import com.gy.hsxt.bs.apply.mapper.IntentCustMapper;
import com.gy.hsxt.bs.bean.apply.AcceptStatus;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.apply.IntentCustBaseInfo;
import com.gy.hsxt.bs.bean.apply.IntentCustQueryParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.apply.IntentCustStatus;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 意向客户管理
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: IntentCustService
 * @Description: 意向客户管理接口实现
 * 
 * @author: xiaofl
 * @date: 2015-11-10 上午9:15:16
 * @version V1.0
 */
@Service
public class IntentCustService implements IBSIntentCustService {

    /** 业务服务私有配置参数 **/
    @Autowired
    private BsConfig bsConfig;

    @Autowired
    private IntentCustMapper intentCustMapper;

    @Autowired
    private IOperationService operationService;

    /**
     * 客户查询受理进度状态
     * 
     * @param acceptNo
     *            受理编号 必填
     * @param licenseNo
     *            意向客户企业营业执照号 必填
     * @return 返回受理状态信息
     * @throws HsException
     */
    @Override
    public AcceptStatus queryStatus(String acceptNo, String licenseNo) throws HsException {
        AcceptStatus acceptStatus = new AcceptStatus();
        int status = 1;
        IntentCust intentCust = intentCustMapper.queryIntentCustByAcceptNoAndLicenseNo(acceptNo, licenseNo);
        List<OptHisInfo> optHis = null;
        if (intentCust != null)
        {
            optHis = operationService.queryOptHisAll(intentCust.getApplyId(), OptTable.T_BS_INTENT_CUST_APPR.getCode());
        }
        if (null == optHis || optHis.size() == 0)
        {
            return null;
        }
        else
        {
            for (OptHisInfo operationHis : optHis)
            {
                if (IntentCustStatus.WAIT_TO_HANDLE.getCode() == operationHis.getBizStatus())
                {// 待处理
                    acceptStatus.setCreatedDate(operationHis.getOptDate());
                }
                else if (IntentCustStatus.CONTACTING_CUST.getCode() == operationHis.getBizStatus())
                {// 客户联系中
                    acceptStatus.setContactCustDate(operationHis.getOptDate());
                    if (status < IntentCustStatus.CONTACTING_CUST.getCode())
                    {
                        acceptStatus.setStatus(IntentCustStatus.CONTACTING_CUST.getCode());
                    }
                }
                else if (IntentCustStatus.APPLYING.getCode() == operationHis.getBizStatus())
                {// 申报中
                    acceptStatus.setAppEntDate(operationHis.getOptDate());
                    if (status < IntentCustStatus.APPLYING.getCode())
                    {
                        acceptStatus.setStatus(IntentCustStatus.APPLYING.getCode());
                    }
                }
                else if (IntentCustStatus.APPLY_SUCCESS.getCode() == operationHis.getBizStatus())
                {// 申报成功
                    acceptStatus.setAppSuccessDate(operationHis.getOptDate());
                    if (status < IntentCustStatus.APPLY_SUCCESS.getCode())
                    {
                        acceptStatus.setStatus(IntentCustStatus.APPLY_SUCCESS.getCode());
                    }
                }
            }
        }
        return acceptStatus;
    }

    /**
     * 服务公司查询意向客户
     * 
     * @param intentCustQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回意向客户基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<IntentCustBaseInfo> serviceEntQueryIntentCust(IntentCustQueryParam intentCustQueryParam, Page page)
            throws HsException {
        if (null == intentCustQueryParam || isBlank(intentCustQueryParam.getSerEntResNo()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return this.queryIntentCust(intentCustQueryParam, page);
    }

    /**
     * 地区平台查询意向客户
     * 
     * @param intentCustQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回意向客户基本信息列表
     * @throws HsException
     */
    @Override
    public PageData<IntentCustBaseInfo> platQueryIntentCust(IntentCustQueryParam intentCustQueryParam, Page page)
            throws HsException {
        if (null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        if (null != intentCustQueryParam)
        {
            intentCustQueryParam.setSerEntResNo(null);
        }
        return this.queryIntentCust(intentCustQueryParam, page);
    }

    /**
     * 服务公司/地区平台查询意向客户信息详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回意向客户信息
     */
    @Override
    public IntentCust queryIntentCustById(String applyId) {
        return intentCustMapper.queryIntentCustById(applyId);
    }

    /**
     * 服务公司审批意向客户
     * 
     * @param applyId
     *            申请编号 必填
     * @param apprOperator
     *            审批操作员客户号 必填
     * @param status
     *            状态 必填
     * @param apprRemark
     *            审批意见
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprIntentCust(String applyId, String apprOperator, Integer status, String apprRemark)
            throws HsException {
        BizLog.biz(this.getClass().getName(), "apprIntentCust", "input param:", "applyId=" + applyId + ",apprOperator"
                + apprOperator + ",status=" + status + ",apprRemark=" + apprRemark);

        if (isBlank(applyId) || isBlank(apprOperator) || null == status)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空[applyId,apprOperator,status]");
        }

        try
        {
            // 更新意向客状态
            intentCustMapper.apprIntentCust(applyId, apprOperator, status, apprRemark);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_INTENT_CUST_APPR.getCode(), applyId, 1, status, apprOperator,
                    null, null, apprRemark, null, null);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprIntentCust", BSRespCode.BS_INTENT_CUST_APPR_ERROR.getCode()
                    + ":处理意向客户失败[param=applyId:" + applyId + ",apprOperator=" + apprOperator + ",status:" + status
                    + ",apprRemark" + apprRemark + "]", e);
            throw new HsException(BSRespCode.BS_INTENT_CUST_APPR_ERROR, "处理意向客户失败[param=applyId:" + applyId
                    + ",apprOperator=" + apprOperator + ",status:" + status + ",apprRemark:" + apprRemark + "]" + e);
        }
    }

    /**
     * 查询意向客户
     * 
     * @param intentCustQueryParam
     *            查询参数
     * @param page
     *            分页参数
     * @return 意向客户列表
     */
    private PageData<IntentCustBaseInfo> queryIntentCust(IntentCustQueryParam intentCustQueryParam, Page page) {
        PageContext.setPage(page);
        PageData<IntentCustBaseInfo> pageData = null;
        List<IntentCustBaseInfo> list = intentCustMapper.queryIntentCustListPage(intentCustQueryParam);

        if (null != list && list.size() > 0)
        {
            pageData = new PageData<IntentCustBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

}
