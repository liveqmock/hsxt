/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.res.RESErrorCode;
import com.gy.hsxt.gpf.res.api.IRESQuotaService;
import com.gy.hsxt.gpf.res.bean.*;
import com.gy.hsxt.gpf.res.common.PageContext;
import com.gy.hsxt.gpf.res.enumtype.ResQuotaStatus;
import com.gy.hsxt.gpf.res.interfaces.IQuotaService;
import com.gy.hsxt.gpf.res.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.res.mapper.InitMapper;
import com.gy.hsxt.gpf.res.mapper.QuotaMapper;
import com.gy.hsxt.gpf.um.bean.GridData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * @version V1.0
 * @Package: com.gy.hsxt.gpf.res.service
 * @ClassName: QuotaService
 * @Description: 一级资源配额
 * @author: xiaofl
 * @date: 2015-12-17 下午3:37:42
 */
@Service
public class QuotaService implements IRESQuotaService, IQuotaService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private QuotaMapper quotaMapper;

    @Resource
    private InitMapper initMapper;

    @Resource
    private ISyncDataService syncDataService;

    /**
     * 地区平台的一级区域配额申请
     * @param quotaApp
     * @throws HsException 
     * @see com.gy.hsxt.gpf.res.interfaces.IQuotaService#addQuotaApp4AreaPlat(com.gy.hsxt.gpf.res.bean.QuotaApp)
     */
    @Override
    public void addQuotaApp4AreaPlat(QuotaApp quotaApp) throws HsException {
        if (null == quotaApp || isBlank(quotaApp.getApplyId()) || isBlank(quotaApp.getPlatNo())
                || isBlank(quotaApp.getEntResNo()) || null == quotaApp.getApplyNum()) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        try {
            String applyId = quotaApp.getPlatNo() + quotaApp.getApplyId();
            quotaApp.setApplyId(applyId);
            quotaApp.setReqOptName(quotaApp.getReqOperator());
            quotaApp.setReqOptId(quotaApp.getReqOperator());
            // 占用互生号
            if (!isBlank(quotaApp.getApplyList())) {
                List<String> resNoList = Arrays.asList(quotaApp.getApplyList().split(","));
                if (quotaMapper.resNoIsUsed(resNoList)) {// 互生号被占用或已使用
                    throw new HsException(RESErrorCode.RES_NO_OCCUPY_OR_USED);
                }
                quotaMapper.updateResNoStatus(applyId, quotaApp.getPlatNo(), resNoList, ResQuotaStatus.APPLYING
                        .getCode());
            }

            // 保存配额申请数据
            quotaMapper.addQuotaApp(quotaApp);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            log.error("保存地区平台配额申请失败[param=" + quotaApp + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_QUOTA_APPLY_ERROR.getCode(), "保存地区平台配额申请失败[param=" + quotaApp
                    + "]" + e);
        }
    }

    /**
     * 统计查询管理公司配额
     *
     * @param entResNo    管理公司互生号
     * @param entCustName 管理公司名称
     * @param page        分页信息
     * @return 管理公司配额统计
     * @throws HsException
     */
    @Override
    public GridData<QuotaStatOfMent> queryQuotaStatOfMent(String entResNo, String entCustName, Page page)
            throws HsException {
        if (null == page) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        PageContext.setPage(page);
        List<QuotaStatOfMent> list = quotaMapper.queryQuotaStatOfMentListPage(entResNo, entCustName);
        return GridData.bulid(true, page.getCount(), page.getCurPage(), list);
    }

    /**
     * 统计查询管理公司在地区平台上的配额
     *
     * @param entResNo 管理公司互生号
     * @param page     分页信息
     * @return 管理公司在地区平台上的配额
     * @throws HsException
     */
    @Override
    public GridData<QuotaStatOfPlat> queryQuotaStatOfPlat(String entResNo, Page page) throws HsException {
        if (null == page) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        PageContext.setPage(page);
        List<QuotaStatOfPlat> list = quotaMapper.queryQuotaStatOfPlatListPage(entResNo);
        return GridData.bulid(true, page.getCount(), page.getCurPage(), list);
    }

    @Override
    @Transactional
    public void applyQuota(QuotaApp quotaApp) throws HsException {
        if (null == quotaApp || isBlank(quotaApp.getPlatNo()) || isBlank(quotaApp.getEntResNo())
                || null == quotaApp.getApplyNum()) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        try {
            String applyId = GlobalConstant.CENTER_PLAT_NO + GuidAgent.getStringGuid("");// TODO
            quotaApp.setApplyId(applyId);

            // 占用互生号
            if (!isBlank(quotaApp.getApplyList())) {
                List<String> resNoList = Arrays.asList(quotaApp.getApplyList().split(","));
                if (quotaMapper.resNoIsUsed(resNoList)) {// 互生号被占用或已使用
                    throw new HsException(RESErrorCode.RES_NO_OCCUPY_OR_USED);
                }
                quotaMapper.updateResNoStatus(applyId, quotaApp.getPlatNo(), resNoList, ResQuotaStatus.APPLYING
                        .getCode());
            }

            // 保存配额申请数据
            quotaMapper.addQuotaApp(quotaApp);
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            log.error("保存配额申请失败[param=" + quotaApp + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_QUOTA_APPLY_ERROR.getCode(), "保存配额申请失败[param=" + quotaApp + "]"
                    + e);
        }
    }

    /**
     * 查询配额申请
     *
     * @param param 查询条件
     * @param page  分页
     * @return
     */
    @Override
    public GridData<QuotaAppBaseInfo> queryQuotaApplyList(QuotaAppParam param, Page page) throws HsException {
        if (null == page) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        PageContext.setPage(page);
        List<QuotaAppBaseInfo> list = quotaMapper.queryQuotaAppListPage(param);
        return GridData.bulid(true, page.getCount(), page.getCurPage(), list);
    }

    /**
     * 审批配额申请
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprQuotaApp(ApprParam apprParam) throws HsException {
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getApplyId())
                || null == apprParam.getIsPass()) {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        try {
            // 查询配额申请详情
            QuotaApp quotaApp = quotaMapper.queryQuotaAppById(apprParam.getApplyId());

            // 如果申请时已占用互生号，则先释放
            String applyResNos = quotaApp.getApplyList();
            if (!isBlank(applyResNos)) {
                List<String> applyList = Arrays.asList(applyResNos.split(","));
                if (applyList.size() > 0) {
                    // 释放申请时占用的互生号
                    quotaMapper.releaseResNo(quotaApp.getApplyId());
                }
            }

            if (apprParam.getIsPass()) {// 通过

                if (null == apprParam.getApprNum() || isBlank(apprParam.getResNoList())) {
                    throw new HsException(RespCode.PARAM_ERROR);
                }

                // 本次批复配额数
                int appringQuota = apprParam.getApprNum();
                // 更新审批配额清单中的互生号状态为已分配
                String apprList = apprParam.getResNoList();
                List<String> resNoList = Arrays.asList(apprList.split(","));

                // 管理公司在地区平台的初始配额数
                int initedQuota = initMapper.queryMentInitQuota(quotaApp.getEntResNo(), quotaApp.getPlatNo());
                // 管理公司在地区平台的已分配配额
                int apprQuota = quotaMapper.queryMentApprQuota(quotaApp.getEntResNo(), quotaApp.getPlatNo());

                if (appringQuota + apprQuota > initedQuota) {// 配额数不够
                    throw new HsException(RESErrorCode.RES_QUOTA_EXCCED);
                }

                if (quotaMapper.resNoIsUsed(resNoList)) {// 互生号被占用或已使用
                    throw new HsException(RESErrorCode.RES_NO_OCCUPY_OR_USED);
                }
                quotaMapper.updateResNoStatus(quotaApp.getApplyId(), quotaApp.getPlatNo(), resNoList,
                        ResQuotaStatus.ASSIGNED.getCode());

                // 更新配额申请状态
                quotaMapper.apprQuotaApp(apprParam);
            } else {// 驳回
                apprParam.setApprNum(null);
                apprParam.setResNoList(null);
                quotaMapper.apprQuotaApp(apprParam);
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            log.error("审批配额申请失败[param=" + apprParam + "]", e);
            throw new HsException(RESErrorCode.RES_APPR_QUOTA_APPLY_ERROR.getCode(), "审批配额申请失败[param=" + apprParam
                    + "]" + e);
        }

        final String applyId = apprParam.getApplyId();
        final QuotaApp quotaApp = quotaMapper.queryQuotaAppById(applyId);
        // 同步配额分配到子平台
        boolean syncQuotaAllot = apprParam.getIsPass() || !applyId.startsWith(GlobalConstant.CENTER_PLAT_NO);// 地区平台的申请和总平台申请审批通过的需同步数据
        if (syncQuotaAllot) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        // 1.同步配额分配到子平台
                        syncDataService.syncQuotaAllotData(quotaApp);
                    } catch (Exception e) {
                        log.error("审批配额申请时，同步配额分配数据到地区平台失败[param=" + quotaApp + "]", e);
                    }
                }
            };
            t.start();
        }

        // 同步路由数据到总平台
        boolean syncRoute = apprParam.getIsPass();
        if (syncRoute) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        List<String> resNoList = Arrays.asList(quotaApp.getResNoList().split(","));
                        // 同步路由数据到总平台
                        syncDataService.syncRouteData(applyId, quotaApp.getPlatNo(), resNoList);
                    } catch (Exception e) {
                        log.error("审批配额申请时，同步路由数据到总平台失败[param=" + quotaApp + "]", e);
                    }
                }
            };
            t.start();
        }
    }

    /**
     * 查询互生号占用情况
     *
     * @param manageEntResNo 管理公司互生号
     * @param applyId        申请编号
     * @return
     */
    @Override
    public List<IdType> queryIdTyp(String manageEntResNo, String applyId) {
        if (!HsResNoUtils.isHsResNo(manageEntResNo)) {
            return null;
        }
        return quotaMapper.queryIdTyp(manageEntResNo.substring(0, 2), applyId);
    }

    /**
     * 查询配额申请
     *
     * @param applyId 申请编号
     * @return
     */
    @Override
    public QuotaAppInfo queryQuotaAppInfo(String applyId) {
        return quotaMapper.queryQuotaAppInfo(applyId);
    }

    /**
     * 同步配额数据到地区平台业务系统(BS)
     *
     * @param applyId 申请编号
     * @throws HsException
     */
    @Override
    public void syncQuotaAllot(String applyId) throws HsException {
        QuotaApp quotaApp = quotaMapper.queryQuotaAppById(applyId);
        syncDataService.syncQuotaAllotData(quotaApp);
    }

    /**
     * 同步路由数据到总平台全局配置系统(GCS)
     *
     * @param applyId 申请编号
     * @throws HsException
     */
    @Override
    public void syncQuotaRoute(String applyId) throws HsException {
        QuotaApp quotaApp = quotaMapper.queryQuotaAppById(applyId);
        List<String> resNoList = Arrays.asList(quotaApp.getResNoList().split(","));
        // 同步路由数据到总平台
        syncDataService.syncRouteData(applyId, quotaApp.getPlatNo(), resNoList);
    }
}
