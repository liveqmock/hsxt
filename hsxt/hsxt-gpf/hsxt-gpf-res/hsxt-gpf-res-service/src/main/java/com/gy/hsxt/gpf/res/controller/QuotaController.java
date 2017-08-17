/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.controller;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.*;
import com.gy.hsxt.gpf.res.interfaces.IQuotaService;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("quota")
public class QuotaController extends BaseController {

    @Resource
    private IQuotaService quotaService;

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
    @RequestMapping(value = { "/queryQuotaStatOfMent" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GridData<QuotaStatOfMent> queryQuotaStatOfMent(String entResNo, String entCustName, Page page)
            throws HsException {
        return quotaService.queryQuotaStatOfMent(entResNo, entCustName, page);
    }

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
    @RequestMapping(value = { "/queryQuotaStatOfPlat" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GridData<QuotaStatOfPlat> queryQuotaStatOfPlat(String entResNo, Page page) throws HsException {
        return quotaService.queryQuotaStatOfPlat(entResNo, page);
    }

    /**
     * 申请配额
     * 
     * @param quotaApp
     *            申请配额信息
     * @throws HsException
     */
    @RequestMapping(value = { "/applyQuota" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> applyQuota(QuotaApp quotaApp) throws HsException {
        quotaService.applyQuota(quotaApp);
        return RespInfo.bulid(true);
    }

    /**
     * 查询配额申请
     * 
     * @param param
     *            查询条件
     * @param page
     *            分页
     * @return
     */
    @RequestMapping(value = { "/queryQuotaApplyList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GridData<QuotaAppBaseInfo> queryQuotaApplyList(QuotaAppParam param, Page page) throws HsException {
        return quotaService.queryQuotaApplyList(param, page);
    }

    /**
     * 审批配额申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @RequestMapping(value = { "/apprQuotaApp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> apprQuotaApp(ApprParam apprParam) throws HsException {
        quotaService.apprQuotaApp(apprParam);
        return RespInfo.bulid(true);
    }

    /**
     * 查询互生号占用情况
     * 
     * @param manageEntResNo
     *            管理公司互生号
     * @param applyId
     *            申请编号
     * @return
     */
    @RequestMapping(value = { "/queryIdTyp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<List<IdType>> queryIdTyp(String manageEntResNo, String applyId) throws HsException {
        List<IdType> idTypes = quotaService.queryIdTyp(manageEntResNo, applyId);
        return RespInfo.bulid(idTypes);
    }

    /**
     * 查询配额申请
     * 
     * @param applyId
     *            申请编号
     * @return
     */
    @RequestMapping(value = { "/queryQuotaAppInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<QuotaAppInfo> queryQuotaAppInfo(String applyId) {
        return RespInfo.bulid(quotaService.queryQuotaAppInfo(applyId));
    }

    /**
     * 同步配额数据到地区平台业务系统(BS)
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    @RequestMapping(value = { "/syncQuotaAllot" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> syncQuotaAllot(String applyId) throws HsException {
        quotaService.syncQuotaAllot(applyId);
        return RespInfo.bulid(true);
    }

    /**
     * 同步路由数据到总平台全局配置系统(GCS)
     * 
     * @param applyId
     *            申请编号
     * @throws HsException
     */
    @RequestMapping(value = { "/syncQuotaRoute" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> syncQuotaRoute(String applyId) throws HsException {
        quotaService.syncQuotaRoute(applyId);
        return RespInfo.bulid(true);
    }

}
