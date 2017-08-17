/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.apply.IBSFilingService;
import com.gy.hsxt.bs.apply.bean.FilingParam;
import com.gy.hsxt.bs.apply.bean.SameItemParam;
import com.gy.hsxt.bs.apply.interfaces.IFilingService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
import com.gy.hsxt.bs.apply.mapper.FilingMapper;
import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.apply.FilingAppKey;
import com.gy.hsxt.bs.common.enumtype.apply.FilingBizAction;
import com.gy.hsxt.bs.common.enumtype.apply.FilingBizResult;
import com.gy.hsxt.bs.common.enumtype.apply.FilingStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * 企业报备管理
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: FilingService
 * @Description: TODO
 * 
 * @author: xiaofl
 * @date: 2015-11-17 下午12:00:24
 * @version V1.0
 */
@Service
public class FilingService implements IBSFilingService, IFilingService {

    /** 业务服务私有配置参数 **/
    @Resource
    private BsConfig bsConfig;

    @Resource
    private FilingMapper filingMapper;

    @Resource
    private IOperationService operationService;

    @Resource
    private ICommonService commonService;

    @Resource
    private ITaskService taskService;

    @Resource
    private IUCBsEntService iUCBsEntService;

    @Resource
    private DeclareMapper declareMapper;

    /**
     * 创建报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @return 返回申请编号
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String createFiling(FilingApp filingApp) throws HsException {
        BizLog.biz(this.getClass().getName(), "createFiling", "input param:", filingApp+"");
        if (null == filingApp || isBlank(filingApp.getOpResNo()) || isBlank(filingApp.getEntCustName()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空[opResNo,entCustName]");
        }
        if (filingMapper.existSameEntName(null, filingApp.getEntCustName()))
        {// 存在完全相同的报备企业名称
            throw new HsException(BSRespCode.BS_FILING_EXIST_SAME_ENT_NAME, "存在完全相同的报备企业名称");
        }
        String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
        try
        {
            // 创建企业报备申请
            filingApp.setApplyId(applyId);
            filingApp.setStatus(FilingStatus.WAIT_TO_SUBMIT.getCode());
            filingMapper.createFilingApp(filingApp);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createFiling", BSRespCode.BS_FILING_SAVE_ENT_ERROR.getCode()+ ":保存报备企业基本信息失败[param=" + filingApp + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_ENT_ERROR, "保存报备企业基本信息失败[param=" + filingApp + "]" + e);
        }
        return applyId;
    }

    /**
     * 服务公司修改报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void serviceModifyFiling(FilingApp filingApp) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceModifyFiling", "input param:", filingApp+"" );
        if (null == filingApp || isBlank(filingApp.getApplyId()) || isBlank(filingApp.getEntCustName()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空[applyId,opResNo,entCustName]");
        }
        if (filingMapper.existSameEntName(filingApp.getApplyId(), filingApp.getEntCustName()))
        {// 存在完全相同的报备企业名称
            throw new HsException(BSRespCode.BS_FILING_EXIST_SAME_ENT_NAME, "存在完全相同的报备企业名称");
        }
        try
        {
            filingMapper.updateFilingApp(filingApp);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "serviceModifyFiling", BSRespCode.BS_FILING_SAVE_ENT_ERROR
                    .getCode()
                    + ":保存报备企业基本信息失败[param=" + filingApp + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_ENT_ERROR, "保存报备企业基本信息失败[param=" + filingApp + "]" + e);
        }
    }

    /**
     * 地区平台修改报备企业信息
     * 
     * @param filingApp
     *            报备企业信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void platModifyFiling(FilingApp filingApp) throws HsException {
        BizLog.biz(this.getClass().getName(), "platModifyFiling", "input param:", filingApp+"");
        if (null == filingApp || isBlank(filingApp.getApplyId()) || isBlank(filingApp.getEntCustName())
                || isBlank(filingApp.getOptCustId()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空[applyId,entCustName,optCustId]");
        }
        if (filingMapper.existSameEntName(filingApp.getApplyId(), filingApp.getEntCustName()))
        {// 存在完全相同的报备企业名称
            throw new HsException(BSRespCode.BS_FILING_EXIST_SAME_ENT_NAME, "存在完全相同的报备企业名称");
        }
        try
        {
            // 更新报备记录
            filingMapper.updateFilingApp(filingApp);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), filingApp.getApplyId(),
                    FilingBizAction.MODIFY_FILING.getCode(), FilingBizResult.PLAT_MODIFY_FILING.getCode(), filingApp
                            .getOptCustId(), filingApp.getOptName(), filingApp.getOptEntName(), filingApp
                            .getOptRemark(), filingApp.getDblOptCustId(), filingApp.getChangeContent());

            boolean existSameItem = false;
            if (null != this.querySameItem(filingApp.getApplyId()))
            {// 如果有相同项，更新相同项标识
                existSameItem = true;
            }
            filingMapper.updateSameItemFlag(filingApp.getApplyId(), existSameItem, filingApp.getUpdatedBy());

        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "platModifyFiling", BSRespCode.BS_FILING_SAVE_ENT_ERROR
                    .getCode()
                    + ":保存报备企业基本信息失败[param=" + filingApp + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_ENT_ERROR, "保存报备企业基本信息失败[param=" + filingApp + "]" + e);
        }
    }

    /**
     * 创建股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createShareHolder(FilingShareHolder filingShareHolder) throws HsException {
        BizLog.biz(this.getClass().getName(), "createShareHolder", "input param:", null == filingShareHolder ? "NULL"
                : filingShareHolder.toString());
        if (null == filingShareHolder || isBlank(filingShareHolder.getApplyId()))
        {
            throw new HsException(RespCode.PARAM_ERROR, "参数不能为空[applyId]");
        }
        try
        {
            if (filingShareHolder.getIdType() != null && filingShareHolder.getIdNo() != null)
            {
                String filingShId = filingMapper.findShareHolder(filingShareHolder.getApplyId(), filingShareHolder
                        .getIdType(), filingShareHolder.getIdNo());
                if (filingShId != null)
                {
                    // 新增股东时，如果股东证件类型与号码不为空，并且已经存在，则报股东重复异常
                    throw new HsException(BSRespCode.BS_SHAREHOLDER_IS_EXIST, "保存失败,股东已经存在param：" + filingShareHolder);
                }
            }
            filingShareHolder.setFilingShId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            filingMapper.createShareHolder(filingShareHolder);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createShareHolder", BSRespCode.BS_FILING_SAVE_SH_ERROR
                    .getCode()
                    + ":保存报备企业股东信息失败[param=" + filingShareHolder + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_SH_ERROR, "保存报备企业股东信息失败[param=" + filingShareHolder + "]"
                    + e);
        }
    }

    /**
     * 服务公司修改股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void serviceModifyShareHolder(FilingShareHolder filingShareHolder) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceModifyShareHolder ", "input param:", null == filingShareHolder
                ? "NULL" : filingShareHolder.toString());
        if (null == filingShareHolder || isBlank(filingShareHolder.getFilingShId())
                || isBlank(filingShareHolder.getApplyId()))
        {
            throw new HsException(RespCode.PARAM_ERROR, "参数不能为空[filingShId,applyId]");
        }
        try
        {
            if (filingShareHolder.getIdType() != null && filingShareHolder.getIdNo() != null)
            {
                String filingShId = filingMapper.findShareHolder(filingShareHolder.getApplyId(), filingShareHolder
                        .getIdType(), filingShareHolder.getIdNo());
                if (filingShId != null && !filingShId.equals(filingShareHolder.getFilingShId()))
                {
                    // 更新股东时，如果股东证件类型与号码不为空，并且已经存在，则报股东重复异常
                    throw new HsException(BSRespCode.BS_SHAREHOLDER_IS_EXIST, "保存失败,股东已经存在param：" + filingShareHolder);
                }
            }
            filingMapper.updateShareHolder(filingShareHolder);
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "serviceModifyShareHolder", BSRespCode.BS_FILING_SAVE_SH_ERROR
                    .getCode()
                    + ":保存报备企业股东信息失败[param=" + filingShareHolder + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_SH_ERROR, "保存报备企业股东信息失败[param=" + filingShareHolder + "]"
                    + e);
        }
    }

    /**
     * 地区平台修改股东信息
     * 
     * @param filingShareHolder
     *            报备企业股东信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void platModifyShareHolder(FilingShareHolder filingShareHolder) throws HsException {
        BizLog.biz(this.getClass().getName(), "platModifyShareHolder ", "input param:", filingShareHolder+"");
        if (null == filingShareHolder || isBlank(filingShareHolder.getFilingShId())
                || isBlank(filingShareHolder.getOptCustId()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空[filingShId,optCustId]");
        }
        try
        {
            if (filingShareHolder.getIdType() != null && filingShareHolder.getIdNo() != null)
            {
                String filingShId = filingMapper.findShareHolder(filingShareHolder.getApplyId(), filingShareHolder
                        .getIdType(), filingShareHolder.getIdNo());
                if (filingShId != null && !filingShId.equals(filingShareHolder.getFilingShId()))
                {
                    // 更新股东时，如果股东证件类型与号码不为空，并且已经存在，则报股东重复异常
                    throw new HsException(BSRespCode.BS_SHAREHOLDER_IS_EXIST, "保存失败,股东已经存在param：" + filingShareHolder);
                }
            }
            // 更新股东信息
            filingMapper.updateShareHolder(filingShareHolder);
            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), filingShareHolder.getApplyId(),
                    FilingBizAction.MODIFY_FILING.getCode(), FilingBizResult.PLAT_MODIFY_FILING.getCode(),
                    filingShareHolder.getOptCustId(), filingShareHolder.getOptName(),
                    filingShareHolder.getOptEntName(), filingShareHolder.getOptRemark(), filingShareHolder
                            .getDblOptCustId(), filingShareHolder.getChangeContent());

            boolean existSameItem = false;
            if (null != this.querySameItem(filingShareHolder.getApplyId()))
            {// 如果有相同项，更新相同项标识
                existSameItem = true;
            }
            filingMapper.updateSameItemFlag(filingShareHolder.getApplyId(), existSameItem, filingShareHolder
                    .getOptCustId());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "platModifyShareHolder", BSRespCode.BS_FILING_SAVE_SH_ERROR
                    .getCode()
                    + ":保存报备企业股东信息失败[param=" + filingShareHolder + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_SH_ERROR, "保存报备企业股东信息失败[param=" + filingShareHolder + "]"
                    + e);
        }
    }

    /**
     * 删除股东信息
     * 
     * @param id
     *            股东ID 必填
     * @param operator
     *            操作员 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteShareHolder(String id, String operator) throws HsException {
        BizLog.biz(this.getClass().getName(), "deleteShareHolder ", "input param:", "id=" + id + ",operator="
                + operator);
        if (isBlank(id))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            FilingShareHolder sh = filingMapper.queryShById(id);
            if (null != sh)
            {
                filingMapper.deleteShareHolder(id);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "deleteShareHolder", BSRespCode.BS_FILING_DEL_SH_ERROR.getCode()
                    + ":删除报备企业股东失败[param=id:" + id + ",operator:" + operator + "]", e);
            throw new HsException(BSRespCode.BS_FILING_DEL_SH_ERROR, "删除报备企业股东失败[param=id:" + id + ",operator:"
                    + operator + "]" + e);
        }
    }

    /**
     * 保存报备企业资质附件信息
     * 
     * @param filingAptitudes
     *            报备企业资质附件信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<FilingAptitude> saveAptitude(List<FilingAptitude> filingAptitudes) throws HsException {
        BizLog.biz(this.getClass().getName(), "saveAptitude", "input param:", null == filingAptitudes ? "NULL"
                : filingAptitudes.toString());
        if (null == filingAptitudes || filingAptitudes.size() == 0)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        List<FilingAptitude> addList = new ArrayList<FilingAptitude>();
        List<FilingAptitude> updateList = new ArrayList<FilingAptitude>();
        String applyId = "";
        for (FilingAptitude filingAptitude : filingAptitudes)
        {
            if (isBlank(filingAptitude.getApplyId()))
            {
                throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
            }
            applyId = filingAptitude.getApplyId();
            if (isBlank(filingAptitude.getFilingAptId()))
            {// 新增
                filingAptitude.setFilingAptId(GuidAgent.getStringGuid(""));
                addList.add(filingAptitude);
            }
            else
            {// 修改
                updateList.add(filingAptitude);
            }
        }
        try
        {
            if (addList.size() > 0)
            {
                filingMapper.createAptitude(addList);
            }
            if (updateList.size() > 0)
            {
                filingMapper.updateAptitude(updateList);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "saveAptitude", BSRespCode.BS_FILING_SAVE_APT_ERROR.getCode()
                    + ":保存报备企业附件失败[param=filingAptitudes" + filingAptitudes + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SAVE_APT_ERROR, "保存报备企业附件失败[param=filingAptitudes"
                    + filingAptitudes + "]" + e);
        }

        return filingMapper.queryAptitudeByApplyId(applyId);
    }

    /**
     * 根据申请编号查询企业基本信息
     * 
     * @param applyId
     *            申请编号
     * @return 企业基本信息
     */
    @Override
    public FilingApp queryFilingBaseInfoById(String applyId) {
        if (isBlank(applyId))
        {
            return null;
        }
        return filingMapper.queryFilingAppById(applyId);
    }

    /**
     * 根据申请编号查询股东信息
     * 
     * @param applyId
     *            申请编号
     * @return 股东信息列表
     */
    @Override
    public List<FilingShareHolder> queryShByApplyId(String applyId) {
        if (isBlank(applyId))
        {
            return null;
        }
        return filingMapper.queryShByApplyId(applyId);
    }

    /**
     * 根据申请编号查询附件信息
     * 
     * @param applyId
     *            申请编号
     * @return 附件信息列表
     */
    @Override
    public List<FilingAptitude> queryAptByApplyId(String applyId) {
        if (isBlank(applyId))
        {
            return null;
        }
        return filingMapper.queryAptitudeByApplyId(applyId);
    }

    /**
     * 服务公司提交报备申请时要检查报备表和用户中心是否有相同企业名称和相同项
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回 0: 没有相同名称和相同项 1：有相同项 2:有完全相同企业名称
     */
    @Override
    public Integer isExistSameOrSimilar(String applyId) {
        FilingApp filingApp = filingMapper.queryFilingAppById(applyId);

        if (filingMapper.existSameEntName(applyId, filingApp.getEntCustName()))
        {
            return 2;
        }

        if (null != this.querySameItem(applyId))
        {
            return 1;
        }
        return 0;
    }

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
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean submitFiling(String applyId, String operator) throws HsException {
        BizLog.biz(this.getClass().getName(), "submitFiling", "input param:", "applyId=" + applyId + ",operator="
                + operator);
        boolean isSameItem = false;
        if (isBlank(applyId))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            FilingApp filingApp = filingMapper.queryFilingAppById(applyId);
            if (filingApp == null)
            {
                throw new HsException(BSRespCode.BS_NOT_QUERY_DATA, "未找到报备记录applyId=" + applyId);
            }
            // 检查被申报企业名称是否已在申报系统中存在
            boolean exist = declareMapper.checkDuplicateEntName(CustType.SERVICE_CORP.getCode(), filingApp
                    .getEntCustName(), null);
            HsAssert.isTrue(!exist, BSRespCode.BS_FILING_EXIST_SAME_ENT_NAME, "已存在相同的申报企业名："
                    + filingApp.getEntCustName());

            if (iUCBsEntService.isEntExist(filingApp.getEntCustName(), CustType.SERVICE_CORP.getCode()))
            {
                throw new HsException(BSRespCode.BS_FILING_EXIST_SAME_ENT_NAME, "报备企业名称已被使用:"
                        + filingApp.getEntCustName());
            }
            // 更新企业报备申请表
            filingMapper.apprFiling(applyId, operator, FilingStatus.WAIT_TO_APPROVE.getCode(), "");

            if (null != this.querySameItem(applyId))
            {// 如果有相同项，更新相同项标识
                isSameItem = true;
                filingMapper.updateSameItemFlag(applyId, true, operator);
            }

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), applyId, FilingBizAction.SUBMIT_FILING
                    .getCode(), FilingBizResult.WAIT_TO_APPROVE.getCode(), operator, null, null, null, null, null);

            // 生成审批任务
            Task task = new Task(applyId, TaskType.TASK_TYPE114.getCode(), commonService.getAreaPlatCustId());
            task.setCustName(filingApp.getEntCustName());
            taskService.saveTask(task);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "submitFiling", BSRespCode.BS_FILING_SUBMIT_ERROR.getCode()
                    + ":提交报备失败:[param=applyId:" + applyId + ",operator:" + operator + "]", e);
            throw new HsException(BSRespCode.BS_FILING_SUBMIT_ERROR, "提交报备失败:[param=applyId:" + applyId + ",operator:"
                    + operator + "]" + e);
        }
        return isSameItem;
    }

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
    @Override
    public PageData<FilingAppInfo> serviceQueryFiling(FilingQueryParam filingQueryParam, Page page) throws HsException {
        if (null == filingQueryParam || isBlank(filingQueryParam.getSerResNo()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        filingQueryParam.setIsDisagreed(false);
        filingQueryParam.setCountryNo(null);
        filingQueryParam.setProvinceNo(null);
        filingQueryParam.setCityNo(null);
        filingQueryParam.setLinkmanTel(null);
        filingQueryParam.setShareHolder(null);
        filingQueryParam.setLegalNo(null);
        filingQueryParam.setOptCustId(null);

        FilingParam filingParam = new FilingParam();
        BeanUtils.copyProperties(filingQueryParam, filingParam);

        return this.queryFiling(filingParam, page, false);
    }

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
    @Override
    public PageData<FilingAppInfo> serviceQueryDisagreedFiling(FilingQueryParam filingQueryParam, Page page)
            throws HsException {
        if (null == filingQueryParam || isBlank(filingQueryParam.getSerResNo()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        filingQueryParam.setIsDisagreed(true);
        filingQueryParam.setCountryNo(null);
        filingQueryParam.setProvinceNo(null);
        filingQueryParam.setCityNo(null);
        filingQueryParam.setLinkmanTel(null);
        filingQueryParam.setShareHolder(null);
        filingQueryParam.setLegalNo(null);
        filingQueryParam.setOptCustId(null);

        FilingParam filingParam = new FilingParam();
        BeanUtils.copyProperties(filingQueryParam, filingParam);
        return this.queryFiling(filingParam, page, false);
    }

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
    @Override
    public PageData<FilingAppInfo> platQueryFiling(FilingQueryParam filingQueryParam, Page page) throws HsException {
        if (null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        if (null != filingQueryParam)
        {
            filingQueryParam.setSerResNo(null);
            filingQueryParam.setIsDisagreed(null);
            filingQueryParam.setOptCustId(null);
            filingQueryParam.setShareHolder(null);
        }

        FilingParam filingParam = new FilingParam();
        BeanUtils.copyProperties(filingQueryParam, filingParam);
        String notInStatus = "(" + FilingStatus.WAIT_TO_SUBMIT.getCode() + ")";
        filingParam.setNotInStatus(notInStatus);
        return this.queryFiling(filingParam, page, false);
    }

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
    @Override
    public PageData<FilingAppInfo> platQueryApprFiling(FilingQueryParam filingQueryParam, Page page) throws HsException {
        if (null == page || filingQueryParam == null || isBlank(filingQueryParam.getOptCustId()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        filingQueryParam.setIsDisagreed(false);
        filingQueryParam.setSerResNo(null);
        filingQueryParam.setLinkmanTel(null);
        filingQueryParam.setShareHolder(null);
        filingQueryParam.setLegalNo(null);

        FilingParam filingParam = new FilingParam();
        BeanUtils.copyProperties(filingQueryParam, filingParam);
        String notInStatus = "(" + FilingStatus.WAIT_TO_SUBMIT.getCode() + ")";
        filingParam.setNotInStatus(notInStatus);
        return this.queryFiling(filingParam, page, true);
    }

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
    @Override
    public PageData<FilingAppInfo> platQueryDisagreedFiling(FilingQueryParam filingQueryParam, Page page)
            throws HsException {
        if (null == filingQueryParam || isBlank(filingQueryParam.getOptCustId()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        filingQueryParam.setIsDisagreed(true);
        filingQueryParam.setSerResNo(null);
        filingQueryParam.setLinkmanTel(null);
        filingQueryParam.setShareHolder(null);
        filingQueryParam.setLegalNo(null);

        FilingParam filingParam = new FilingParam();
        BeanUtils.copyProperties(filingQueryParam, filingParam);
        String notInStatus = "(" + FilingStatus.WAIT_TO_SUBMIT.getCode() + ")";
        filingParam.setNotInStatus(notInStatus);
        return this.queryFiling(filingParam, page, true);
    }

    /**
     * 查询报备
     * 
     * @param filingParam
     *            查询参数
     * @param page
     *            分页参数
     * @param taskFlag
     *            是否关联任务
     * @return 报备列表
     */
    private PageData<FilingAppInfo> queryFiling(FilingParam filingParam, Page page, boolean taskFlag) {
        PageContext.setPage(page);
        PageData<FilingAppInfo> pageData = null;
        List<FilingAppInfo> filingAppInfoList = null;

        if (taskFlag)
        {// 关联任务表，查出任务ID
            filingAppInfoList = filingMapper.queryFiling4ApprListPage(filingParam);
        }
        else
        {
            filingAppInfoList = filingMapper.queryFilingListPage(filingParam);
        }

        if (null != filingAppInfoList && filingAppInfoList.size() > 0)
        {
            pageData = new PageData<FilingAppInfo>();
            pageData.setResult(filingAppInfoList);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 服务公司/地区平台查询企业报备/异议报备详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回null则未查到数据；如果有数据返回map key value FILING_INFO FilingApp FILING_SH
     *         FilingShareHolder FILING_APT FilingAptitude
     */
    @Override
    public Map<String, Object> queryFilingById(String applyId) {
        if (isBlank(applyId))
        {
            return null;
        }
        FilingApp filingApp = filingMapper.queryFilingAppById(applyId);
        if (null == filingApp)
        {
            return null;
        }

        try
        {
            BsEntMainInfo bsEntMainInfo = iUCBsEntService.getMainInfoByResNo(filingApp.getOpResNo());
            if (null != bsEntMainInfo)
            {
                filingApp.setOpEntName(bsEntMainInfo.getEntName());
                filingApp.setOpLinkman(bsEntMainInfo.getContactPerson());
                filingApp.setOpLinkmanTel(bsEntMainInfo.getContactPhone());
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "queryFilingById", BSRespCode.BS_NOT_QUERY_DATA.getCode()
                    + ":没有查询到服务公司数据：互生号" + filingApp.getOpResNo(), e);
        }
        List<FilingShareHolder> shList = filingMapper.queryShByApplyId(applyId);
        List<FilingAptitude> aptList = filingMapper.queryAptitudeByApplyId(applyId);
        Map<String, Object> filingMap = new HashMap<String, Object>();
        filingMap.put(FilingAppKey.FILING_APP.getCode(), filingApp);
        filingMap.put(FilingAppKey.FILING_SH.getCode(), shList);
        filingMap.put(FilingAppKey.FILING_APT.getCode(), aptList);
        return filingMap;
    }

    /**
     * 查询报备企业相同项
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回相同项，没有则返回null
     */
    @Override
    public FilingSameItem querySameItem(String applyId) {
        if (isBlank(applyId))
        {
            return null;
        }
        FilingSameItem filingSameItem = new FilingSameItem();
        FilingApp filingApp = filingMapper.queryFilingAppById(applyId);
        if (null == filingApp)
        {
            return null;
        }
        List<SameItemParam> sameNameList = null;
        List<SameItemParam> sameLicenseList = null;
        List<SameItemParam> sameLegalList = null;
        List<SameItemParam> sameLinkManList = null;
        List<SameItemParam> sameShList = new ArrayList<>();
        // 1.公司名称是否相似
        if (!isBlank(filingApp.getEntCustName()))
        {
            SameItemParam sameName = new SameItemParam();
            sameName.setApplyId(filingApp.getApplyId());
            sameName.setEntCustName(filingApp.getEntCustName());
            sameNameList = filingMapper.querySameItem(sameName);
            if (CollectionUtils.isNotEmpty(sameNameList))
            {
                List<String[]> sameNames = new ArrayList<>();
                for (SameItemParam sameItem : sameNameList)
                {
                    String name[] = new String[2];
                    name[0] = sameItem.getOpResNo();
                    name[1] = sameItem.getEntCustName();
                    sameNames.add(name);
                }
                filingSameItem.setSameEntNames(sameNames);
            }
        }

        // 2.营业执照号是否相同
        if (!isBlank(filingApp.getLicenseNo()))
        {
            SameItemParam sameLicense = new SameItemParam();
            sameLicense.setApplyId(filingApp.getApplyId());
            sameLicense.setLicenseNo(filingApp.getLicenseNo());
            sameLicenseList = filingMapper.querySameItem(sameLicense);
            if (CollectionUtils.isNotEmpty(sameLicenseList))
            {
                List<String[]> sameLicenses = new ArrayList<>();
                for (SameItemParam sameItem : sameLicenseList)
                {
                    String license[] = new String[3];
                    license[0] = sameItem.getOpResNo();
                    license[1] = sameItem.getEntCustName();
                    license[2] = sameItem.getLicenseNo();
                    sameLicenses.add(license);
                }
                filingSameItem.setSameLicenses(sameLicenses);
            }
        }
        // 3.法人代表+证件类型+法人证件号码 是否相同
        if (!isBlank(filingApp.getLegalName()))
        {
            SameItemParam sameLegal = new SameItemParam();
            sameLegal.setApplyId(filingApp.getApplyId());
            sameLegal.setLegalName(filingApp.getLegalName());
            sameLegalList = filingMapper.querySameItem(sameLegal);
            if (CollectionUtils.isNotEmpty(sameLegalList))
            {
                List<String[]> sameLegals = new ArrayList<>();
                for (SameItemParam sameItem : sameLegalList)
                {
                    String legal[] = new String[5];
                    legal[0] = sameItem.getOpResNo();
                    legal[1] = sameItem.getEntCustName();
                    legal[2] = sameItem.getLegalName();

                    sameLegals.add(legal);
                }
                filingSameItem.setSameLegalReps(sameLegals);
            }
        }
        // 4.联系人+联系人手机 是否相同
        if (!isBlank(filingApp.getLinkman()) && !isBlank(filingApp.getPhone()))
        {
            SameItemParam sameLinkMan = new SameItemParam();
            sameLinkMan.setApplyId(filingApp.getApplyId());
            sameLinkMan.setLinkman(filingApp.getLinkman());
            sameLinkMan.setPhone(filingApp.getPhone());
            sameLinkManList = filingMapper.querySameItem(sameLinkMan);
            if (CollectionUtils.isNotEmpty(sameLinkManList))
            {
                List<String[]> sameLinkmans = new ArrayList<>();
                for (SameItemParam sameItem : sameLinkManList)
                {
                    String linkman[] = new String[4];
                    linkman[0] = sameItem.getOpResNo();
                    linkman[1] = sameItem.getEntCustName();
                    linkman[2] = sameItem.getLinkman();
                    linkman[3] = sameItem.getPhone();

                    sameLinkmans.add(linkman);
                }
                filingSameItem.setSameLinkmans(sameLinkmans);
            }
        }
        // 5.股东名称/姓名+证件类型+股东证件号码 是否相同
        List<FilingShareHolder> shList = filingMapper.queryShByApplyId(applyId);
        if (CollectionUtils.isNotEmpty(shList)) {
            for (FilingShareHolder sh : shList) {
                SameItemParam sameSh = new SameItemParam();
                sameSh.setApplyId(filingApp.getApplyId());
                sameSh.setShName(sh.getShName());
                sameSh.setIdType(sh.getIdType());
                sameSh.setIdNo(sh.getIdNo());
                List<SameItemParam> sameShListTmp = filingMapper.querySameSh(sameSh);
                for (SameItemParam sameShTmp : sameShListTmp) {
                    if (!sameShList.contains(sameShTmp)) {
                        sameShList.add(sameShTmp);
                    }
                }
            }
        }
        if (sameShList.size() > 0)
        {
            List<String[]> sameShs = new ArrayList<>();
            for (SameItemParam sameItem : sameShList)
            {
                String sh[] = new String[5];
                sh[0] = sameItem.getOpResNo();
                sh[1] = sameItem.getEntCustName();
                sh[2] = sameItem.getShName();
                sh[3] = sameItem.getIdNo();
                sh[4] = String.valueOf(sameItem.getIdType());

                sameShs.add(sh);
            }
            filingSameItem.setSameShareHolders(sameShs);
        }

        if (CollectionUtils.isNotEmpty(sameNameList) || CollectionUtils.isNotEmpty(sameLicenseList) || CollectionUtils.isNotEmpty(sameLegalList) || CollectionUtils.isNotEmpty(sameLinkManList) || sameShList.size() > 0)
        {
            filingSameItem.setEntName(filingApp.getEntCustName());
            filingSameItem.setCountryNo(filingApp.getCountryNo());
            filingSameItem.setProvinceNo(filingApp.getProvinceNo());
            filingSameItem.setCityNo(filingApp.getCityNo());
            filingSameItem.setAddress(filingApp.getEntAddress());
            return filingSameItem;
        }
        else
        {
            return null;
        }
    }

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
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void disagreedReject(String applyId, String operator, String remark) throws HsException {
        BizLog.biz(this.getClass().getName(), "disagreedReject", "input param:", "applyId=" + applyId + ",operator="
                + operator + ",remark=" + remark);
        if (isBlank(applyId) || isBlank(operator) || isBlank(remark))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            // 更新企业报备申请表
            Integer status = FilingStatus.WAIT_TO_APPROVE.getCode();// 提出异议后，状态变为"待审核"
            filingMapper.disagreedReject(applyId, operator, status, remark);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), applyId,
                    FilingBizAction.PLAT_APPR_DISAGREE_FILING.getCode(), FilingBizResult.WAIT_TO_APPROVE.getCode(),
                    operator, null, null, remark, null, null);

            // 生成审批任务
            Task task = new Task(applyId, TaskType.TASK_TYPE114.getCode(), commonService.getAreaPlatCustId());
            FilingApp filingApp = filingMapper.queryFilingAppById(applyId);
            task.setCustName(filingApp.getEntCustName());
            taskService.saveTask(task);
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "disagreedReject", BSRespCode.BS_FILING_DISAGREED_REJECT_ERROR
                    .getCode()
                    + ":提出异议失败[param=applyId:" + applyId + ",operator:" + operator + ",remark:" + remark + "]", e);
            throw new HsException(BSRespCode.BS_FILING_DISAGREED_REJECT_ERROR, "提出异议失败[param=applyId:" + applyId
                    + ",operator:" + operator + ",remark:" + remark + "]" + e);
        }
    }

    /**
     * 地区平台审批报备
     * 
     * @param apprParam
     *            申请参数
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprFiling(FilingApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprFiling", "input param:", null == apprParam ? "NULL" : apprParam
                .toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getApprOperator())
                || null == apprParam.getStatus())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getApprOperator());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try
        {
            // 更新企业报备申请表
            filingMapper.apprFiling(apprParam.getApplyId(), apprParam.getApprOperator(), apprParam.getStatus(),
                    apprParam.getApprRemark());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), apprParam.getApplyId(),
                    FilingBizAction.PLAT_APPR_FILING.getCode(), apprParam.getStatus(), apprParam.getApprOperator(),
                    null, null, apprParam.getApprRemark(), null, null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

            if (FilingStatus.DATE_TO_INTERVIEW.getCode() == apprParam.getStatus()
                    || FilingStatus.DATE_TO_TRAIN.getCode() == apprParam.getStatus()
                    || FilingStatus.NOTIFY_TO_REMIT.getCode() == apprParam.getStatus())
            {
                // 生成审批任务
                Task task = new Task(apprParam.getApplyId(), TaskType.TASK_TYPE114.getCode(), commonService
                        .getAreaPlatCustId());
                FilingApp filingApp = filingMapper.queryFilingAppById(apprParam.getApplyId());
                task.setCustName(filingApp.getEntCustName());
                taskService.saveTask(task);
            }

        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprFiling", BSRespCode.BS_FILING_APPR_ERROR.getCode()
                    + ":审批报备失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_FILING_APPR_ERROR, "审批报备失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 地区平台审批异议报备
     * 
     * @param apprParam
     *            申请参数
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprDisagreeFiling(FilingApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprDisagreeFiling", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getApprOperator())
                || null == apprParam.getStatus())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getApprOperator());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }
        try
        {
            // 更新企业报备申请表
            filingMapper.apprFiling(apprParam.getApplyId(), apprParam.getApprOperator(), apprParam.getStatus(),
                    apprParam.getApprRemark());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_FILING_APPR.getCode(), apprParam.getApplyId(),
                    FilingBizAction.PLAT_APPR_DISAGREE_FILING.getCode(), apprParam.getStatus(), apprParam
                            .getApprOperator(), null, null, apprParam.getApprRemark(), null, null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

            if (FilingStatus.DATE_TO_INTERVIEW.getCode() == apprParam.getStatus()
                    || FilingStatus.DATE_TO_TRAIN.getCode() == apprParam.getStatus()
                    || FilingStatus.NOTIFY_TO_REMIT.getCode() == apprParam.getStatus())
            {
                // 生成审批任务
                Task task = new Task(apprParam.getApplyId(), TaskType.TASK_TYPE114.getCode(), commonService
                        .getAreaPlatCustId());
                FilingApp filingApp = filingMapper.queryFilingAppById(apprParam.getApplyId());
                task.setCustName(filingApp.getEntCustName());
                taskService.saveTask(task);
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprDisagreeFiling", BSRespCode.BS_FILING_APPR_DISAGREED_ERROR
                    .getCode()
                    + ":审批异议报备失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_FILING_APPR_DISAGREED_ERROR, "审批异议报备失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 根据申请编号删除报备
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFilingById(String applyId) throws HsException {
        BizLog.biz(this.getClass().getName(), "deleteFilingById", "input param:", "applyId=" + applyId);
        if (isBlank(applyId))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            filingMapper.deleteFilingApp(applyId);
            filingMapper.deleteShareHolderByApplyId(applyId);
            filingMapper.deleteAptByApplyId(applyId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "deleteFilingById", BSRespCode.BS_FILING_DEL_ERROR.getCode()
                    + ":删除报备失败[param=applyId:" + applyId + "]", e);
            throw new HsException(BSRespCode.BS_FILING_DEL_ERROR, "删除报备失败[param=applyId:" + applyId + "]" + e);
        }

    }

    /**
     * 判断企业是否被服务公司报备过
     * 
     * @param serResNo
     *            服务公司互生号
     * @param entCustName
     *            企业名称
     * @return 已报备返回true,否则返回false
     */
    @Override
    public boolean isFiling(String serResNo, String entCustName) {
        return filingMapper.isFiling(serResNo, entCustName);
    }

    /**
     * 查询报备进行步骤
     * 
     * @param applyId
     *            申请编号
     * @return 返回 0:未找到该报备 1.企业基本信息填写完成,2.股东信息填写完成,3.附件信息填写完成
     */
    @Override
    public Integer queryFilingStep(String applyId) {
        FilingApp filingApp = filingMapper.queryFilingAppById(applyId);
        if (null == filingApp)
        {
            return 0;
        }
        List<FilingShareHolder> shList = filingMapper.queryShByApplyId(applyId);
        if (null == shList || shList.size() == 0)
        {
            return 1;
        }
        List<FilingAptitude> aptList = filingMapper.queryAptitudeByApplyId(applyId);
        if (null == aptList || aptList.size() == 0)
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }
}
