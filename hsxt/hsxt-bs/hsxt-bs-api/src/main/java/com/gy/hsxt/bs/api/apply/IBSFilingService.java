/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.apply;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.apply.FilingApp;
import com.gy.hsxt.bs.bean.apply.FilingAppInfo;
import com.gy.hsxt.bs.bean.apply.FilingApprParam;
import com.gy.hsxt.bs.bean.apply.FilingAptitude;
import com.gy.hsxt.bs.bean.apply.FilingQueryParam;
import com.gy.hsxt.bs.bean.apply.FilingSameItem;
import com.gy.hsxt.bs.bean.apply.FilingShareHolder;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.app
 * @ClassName: IBSFilingService
 * @Description: 企业报备管理接口
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午3:38:39
 * @version V1.0
 */
public interface IBSFilingService {

    /**
     * 创建报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @return 返回申请编号
     * @throws HsException
     */
    public String createFiling(FilingApp filingApp) throws HsException;

    /**
     * 服务公司修改报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @throws HsException
     */
    public void serviceModifyFiling(FilingApp filingApp) throws HsException;

    /**
     * 地区平台修改报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @throws HsException
     */
    public void platModifyFiling(FilingApp filingApp) throws HsException;

    /**
     * 创建股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息 必填
     * @throws HsException
     */
    public void createShareHolder(FilingShareHolder filingShareHolder) throws HsException;

    /**
     * 服务公司修改股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息 必填
     * @throws HsException
     */
    public void serviceModifyShareHolder(FilingShareHolder filingShareHolder) throws HsException;

    /**
     * 地区平台修改股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息
     * @throws HsException
     */
    public void platModifyShareHolder(FilingShareHolder filingShareHolder) throws HsException;

    /**
     * 删除股东信息
     * 
     * @param id
     *            股东ID 必填
     * @param operator
     *            操作员 必填
     * @throws HsException
     */
    public void deleteShareHolder(String id, String operator) throws HsException;

    /**
     * 保存报备企业资质附件信息
     * 
     * @param filingAptitudes
     *            报备企业资质附件信息 必填
     * @throws HsException
     */
    public List<FilingAptitude> saveAptitude(List<FilingAptitude> filingAptitudes) throws HsException;

    /**
     * 根据申请编号查询企业基本信息
     * 
     * @param applyId
     *            申请编号
     * @return 企业基本信息
     */
    public FilingApp queryFilingBaseInfoById(String applyId);

    /**
     * 根据申请编号查询股东信息
     * 
     * @param applyId
     *            申请编号
     * @return 股东信息列表
     */
    public List<FilingShareHolder> queryShByApplyId(String applyId);

    /**
     * 根据申请编号查询附件信息
     * 
     * @param applyId
     *            申请编号
     * @return 附件信息列表
     */
    public List<FilingAptitude> queryAptByApplyId(String applyId);

    /**
     * 服务公司提交报备申请时要检查报备表和用户中心是否有相同企业名称和相同项
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回 0: 没有相同名称和相同项 1：有相同项 2:有完全相同企业名称
     */
    public Integer isExistSameOrSimilar(String applyId);

    /**
     * 提交报备申请
     * 
     * @param applyId
     *            申请编号 必填
     * @param operator
     *            操作员 必填
     * @return 是否有相同项
     * @throws HsException
     */
    public boolean submitFiling(String applyId, String operator) throws HsException;

    /**
     * 服务公司查询报备资料
     * 
     * @param filingQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回报备企业列表，没有则返回null
     * @throws HsException
     */
    public PageData<FilingAppInfo> serviceQueryFiling(FilingQueryParam filingQueryParam, Page page) throws HsException;

    /**
     * 服务公司查询异议报备资料
     * 
     * @param filingQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回报备企业列表，没有则返回null
     * @throws HsException
     */
    public PageData<FilingAppInfo> serviceQueryDisagreedFiling(FilingQueryParam filingQueryParam, Page page)
            throws HsException;

    /**
     * 地区平台查询报备资料
     * 
     * @param filingQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回报备企业列表，没有则返回null
     * @throws HsException
     */
    public PageData<FilingAppInfo> platQueryFiling(FilingQueryParam filingQueryParam, Page page) throws HsException;

    /**
     * 地区平台查询报备审核
     * 
     * @param filingQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回报备企业列表，没有则返回null
     * @throws HsException
     */
    public PageData<FilingAppInfo> platQueryApprFiling(FilingQueryParam filingQueryParam, Page page) throws HsException;

    /**
     * 地区平台查询异议报备资料
     * 
     * @param filingQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回异议报备企业列表，没有则返回null
     * @throws HsException
     */
    public PageData<FilingAppInfo> platQueryDisagreedFiling(FilingQueryParam filingQueryParam, Page page)
            throws HsException;

    /**
     * 服务公司/地区平台查询企业报备/异议报备详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回null则未查到数据；如果有数据返回map key value FILING_INFO FilingApp FILING_SH
     *         FilingShareHolder FILING_APT FilingAptitude
     */
    public Map<String, Object> queryFilingById(String applyId);

    /**
     * 查询报备企业相同项
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回相同项，没有则返回null
     */
    public FilingSameItem querySameItem(String applyId);

    /**
     * 提出异议
     * 
     * @param applyId
     *            申请编号 必填
     * @param operator
     *            操作员 必填
     * @param remark
     *            异议说明 必填
     * @throws HsException
     */
    public void disagreedReject(String applyId, String operator, String remark) throws HsException;

    /**
     * 地区平台审批企业报备
     * 
     * @param filingApprParam
     *            审批参数
     * @throws HsException
     */
    public void apprFiling(FilingApprParam filingApprParam) throws HsException;

    /**
     * 地区平台审批企业异议报备
     * 
     * @param filingApprParam
     *            审批参数
     * @throws HsException
     */
    public void apprDisagreeFiling(FilingApprParam filingApprParam) throws HsException;

    /**
     * 根据申请编号删除报备
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    public void deleteFilingById(String applyId) throws HsException;

    /**
     * 查询报备进行步骤
     * 
     * @param applyId
     *            申请编号
     * @return 返回 0:未找到该报备 1.企业基本信息填写完成,2.股东信息填写完成,3.附件信息填写完成
     */
    public Integer queryFilingStep(String applyId);
}
