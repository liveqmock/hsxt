/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.MlmDetail;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bm.interfaces.IBmlmDetailService;
import com.gy.hsxt.bs.bm.interfaces.IBsFssNotifyService;
import com.gy.hsxt.bs.bm.interfaces.IMlmDetailService;
import com.gy.hsxt.bs.bm.interfaces.IMlmTotalService;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeService;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.lcs.bean.LocalInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理增值业务的实现层
 *
 * @Package :com.gy.hsxt.bs.bm
 * @ClassName : BsFssNotifyService
 * @Description : 处理增值业务的实现层
 * @Author : chenm
 * @Date : 2015/11/4 11:47
 * @Version V3.0.0.0
 */
@Service("bsFssNotifyService")
public class BsFssNotifyService implements IBsFssNotifyService {

    @Resource
    private BsConfig bsConfig;

    @Resource
    private IBmlmDetailService bmlmDetailService;

    @Resource
    private IMlmDetailService mlmDetailService;

    @Resource
    private IMlmTotalService mlmTotalService;

    @Resource
    private IAccountDetailService accountDetailService;

    @Resource
    private ITaxrateChangeService taxrateChangeService;

    @Resource
    private ICommonService commonService;

    /**
     * 文件同步系统同步通知
     *
     * @param localNotify 本地通知
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean fssSyncNotify(LocalNotify localNotify) throws HsException {
        boolean success = true;
        if (FssNotifyType.REMOTE_BACK.getTypeNo() == localNotify.getNotifyType()) {
            if (FssPurpose.BM_MLM.getCode().equals(localNotify.getPurpose())) {
                success = this.mlmDataNotify(localNotify);
            }
            if (FssPurpose.BM_BMLM.getCode().equals(localNotify.getPurpose())) {
                success = this.bmlmDataNotify(localNotify);
            }
        }
        return success;
    }

    /**
     * 增值数据通知
     *
     * @param localNotify 本地通知
     * @return boolean
     * @throws HsException
     */
    @Override
    @Transactional
    public Boolean mlmDataNotify(LocalNotify localNotify) throws HsException {
        // 写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:mlmDataNotify", "增值数据通知:" + localNotify);

        // 校验参数
        HsAssert.notNull(localNotify, RespCode.PARAM_ERROR, "通知实体不能为空");
        HsAssert.isTrue(!CollectionUtils.isEmpty(localNotify.getDetails()), RespCode.PARAM_ERROR, "文件详情列表不能为空");
        HsAssert.isTrue(localNotify.getFileCount() == localNotify.getDetails().size(), RespCode.PARAM_ERROR, "文件数目不对");
        HsAssert.isTrue(localNotify.getAllPass() == 1, RespCode.PARAM_ERROR, "文件校验没有完全通过");

        // 文件详情遍历处理
        for (FileDetail detail : localNotify.getDetails()) {
            // 1.1 读取文件内容
            List<String> contents = parseFile(detail);
            // 1.2 处理文件内容
            for (String content : contents) {
                // 写日志
                SystemLog.info(bsConfig.getSysName(), "function:mlmDataNotify", "增值数据内容:" + content);

                JSONObject json = JSON.parseObject(content);
                Object rows = json.get("totalRow");
                Object sum = json.get("totalNum");
                Object start = json.get("startDate");
                Object end = json.get("endDate");
                Object data = json.get("data");

                // 生成增值汇总主键ID
                String totalId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());

                // 1.2.1 增值汇总的处理
                MlmTotal mlmTotal = new MlmTotal();
                // 设置主键ID
                mlmTotal.setTotalId(totalId);
                // 总笔数
                mlmTotal.setTotalRow(rows == null ? 0 : Integer.parseInt(String.valueOf(rows)));
                // 总积分
                mlmTotal.setPoints(String.valueOf(sum));
                // 汇总开始时间
                String startStr = "";
                if (start != null) {
                    startStr = DateUtil.DateTimeToString(DateUtil.StringToDate(String.valueOf(start), DateUtil.DATE_FORMAT));
                }
                mlmTotal.setCalcStartTime(startStr);
                // 汇总结束时间
                String endStr = "";
                if (end != null) {
                    Date date = DateUtils.addSeconds(DateUtil.StringToDate(String.valueOf(end), DateUtil.DATE_FORMAT), -1);
                    endStr = DateUtil.DateTimeToString(date);
                }
                mlmTotal.setCalcEndTime(endStr);

                // 1.2.2 增值详情的处理
                MlmDetail mlmDetail = JSON.parseObject(String.valueOf(data), MlmDetail.class);

                mlmTotal.setCustId(mlmDetail.getCustId());
                mlmTotal.setResNo(mlmDetail.getResNo());

                // 计算扣除税金
                String validTax = taxrateChangeService.queryValidTaxrateByCustId(mlmTotal.getCustId());
                BigDecimal mlmDecimal = BigDecimalUtils.floor(mlmTotal.getPoints());
                BigDecimal tax = BigDecimalUtils.ceilingMul(mlmTotal.getPoints(), validTax);

                // 设置计算税率
                mlmTotal.setTaxRate(validTax);
                // 设置税金
                mlmTotal.setTax(BigDecimalUtils.ceiling(tax.toString(),2).toPlainString());

                // 保存增值汇总数据
                int num = mlmTotalService.save(mlmTotal);
                // 写日志
                BizLog.biz(bsConfig.getSysName(), "function:mlmDataNotify", "params==>" + mlmTotal, "保存增值汇总数据结果:" + num);

                // 生成增值详情主键ID
                String detailId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
                // 设置增值详情主键ID
                mlmDetail.setDetailId(detailId);
                // 设置关联的增值汇总主键ID
                mlmDetail.setTotalId(totalId);

                String date = mlmDetail.getDate();
                if (StringUtils.isNotEmpty(date)) {
                    if (date.contains("-")) {
                        mlmDetail.setDate(StringUtils.left(date, 10));
                    }else {
                        date = StringUtils.left(date, 8);
                        date = StringUtils.left(date, 4) + "-" + StringUtils.substring(date, 4, 6)+"-"+StringUtils.right(date,2);
                        mlmDetail.setDate(date);
                    }

                }

                // 保存增值详情数据
                int count = mlmDetailService.save(mlmDetail);
                // 写日志
                BizLog.biz(bsConfig.getSysName(), "function:mlmDataNotify", "params==>" + mlmDetail, "保存增值详情数据结果:"+ count);

                // 查询本平台信息 包括平台互生号和平台客户号
                LocalInfo localInfo = commonService.getAreaPlatInfo();

                /**
                 * 积分互生分配 F_DIST_HS_POINT 互生积分分配 M52600 U16000
                 * "企业：10110 加 平台：10340 减"
                 * 10510  积分税收  20610  互生币税收  30310  货币税收
                 */

                List<AccountDetail> accountDetails = new ArrayList<>();
                if (tax.doubleValue() > 0) {
                    // 城市税率计算
                    AccountDetail companyTax = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                            totalId, // 业务流水号
                            TransType.F_DIST_HS_POINT.getCode(),// 交易类型
                            mlmTotal.getCustId(),// 企业客户号
                            mlmTotal.getResNo(),// 企业互生号
                            "", // 企业名称
                            HsResNoUtils.getCustTypeByHsResNo(mlmTotal.getResNo()),// 企业类型
                            AccountType.ACC_TYPE_POINT10510.getCode(), // 账户类型
                            BigDecimalUtils.ceiling(tax.toString(),2).toPlainString(),// 增加金额
                            "0", // 减少金额
                            localInfo.getPointCode(),// 货币币种
                            endStr,
                            "增值积分分配企业税收",// 备注
                            false);// 是否实时

                    accountDetails.add(companyTax);
                }

                // 企业积分记账
                AccountDetail companyMlm = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                        totalId,// 业务流水号
                        TransType.F_DIST_HS_POINT.getCode(), // 交易类型
                        mlmTotal.getCustId(),// 企业客户号
                        mlmTotal.getResNo(), // 企业互生号
                        "", // 企业名称
                        HsResNoUtils.getCustTypeByHsResNo(mlmTotal.getResNo()),// 企业类型
                        AccountType.ACC_TYPE_POINT10110.getCode(), // 账户类型
                        BigDecimalUtils.floor(mlmDecimal.subtract(tax).toString(),2).toPlainString(), // 增加金额
                        "0", // 减少金额
                        localInfo.getPointCode(), // 货币币种
                        endStr,
                        "增值积分分配企业收入",// 备注
                        false);// 是否实时

                accountDetails.add(companyMlm);

                // 平台客户号
                String platCustId = commonService.getAreaPlatCustId();

                // 平台积分记账
                AccountDetail platMlm = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                        totalId,// 业务流水号
                        TransType.F_DIST_HS_POINT.getCode(),// 交易类型
                        platCustId,// 平台客户号
                        localInfo.getPlatResNo(),// 平台互生号
                        localInfo.getPlatNameCn(),// 平台名称
                        CustType.AREA_PLAT.getCode(),// 平台类型
                        AccountType.ACC_TYPE_POINT10340.getCode(),// 账户类型
                        "0",// 增加金额
                        mlmTotal.getPoints(),// 减少金额
                        localInfo.getPointCode(),// 货币币种
                        endStr,
                        "增值积分分配平台支出",// 备注
                        false);// 是否定时

                accountDetails.add(platMlm);

                // 批量插入分解记录
                accountDetailService.saveGenActDetail(accountDetails, "互生积分分配记账", false);
            }
        }
        return true;
    }

    /**
     * 再增值数据通知
     *
     * @param localNotify 本地通知
     * @return boolean
     * @throws HsException
     */
    @Override
    @Transactional
    public Boolean bmlmDataNotify(LocalNotify localNotify) throws HsException {
        // 写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:bmlmDataNotify", "再增值数据通知:" + localNotify);

        // 校验参数
        HsAssert.notNull(localNotify, RespCode.PARAM_ERROR, "通知实体不能为空");
        HsAssert.isTrue(!CollectionUtils.isEmpty(localNotify.getDetails()), RespCode.PARAM_ERROR, "文件详情列表不能为空");
        HsAssert.isTrue(localNotify.getFileCount() == localNotify.getDetails().size(), RespCode.PARAM_ERROR,
                "文件数目不对");
        HsAssert.isTrue(localNotify.getAllPass() == 1, RespCode.PARAM_ERROR, "文件校验没有完全通过");

        // 处理文件详情
        for (FileDetail detail : localNotify.getDetails()) {

            // 1.1 读取文件内容
            List<String> contents = parseFile(detail);

            // 1.2 解析文件内容并保存
            for (String content : contents) {
                // 写日志
                SystemLog.info(bsConfig.getSysName(), "function:bmlmDataNotify", "再增值数据内容:" + content);

                // 解析内容
                BmlmDetail bmlmDetail = JSON.parseObject(content, BmlmDetail.class);
                // 生成再增值详情主键ID 即业务流水号
                String bmlmId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
                bmlmDetail.setBmlmId(bmlmId);
                // 汇总开始时间
                bmlmDetail.setCalcStartTime(DateUtil.DateTimeToString(DateUtil.StringToDate(bmlmDetail .getCalcStartTime(), DateUtil.DATE_FORMAT)));
                // 汇总结束时间
                Date date = DateUtils.addSeconds(DateUtil.StringToDate(bmlmDetail.getCalcEndTime(),DateUtil.DATE_FORMAT), -1);
                String endDate = DateUtil.DateTimeToString(date);
                bmlmDetail.setCalcEndTime(endDate);
                // 分解时间
                bmlmDetail.setCreatedDate(DateUtil.getCurrentDateTime());
                // 计算扣除税金
                String validTax = taxrateChangeService.queryValidTaxrateByCustId(bmlmDetail.getCustId());
                BigDecimal bmlmDecimal = BigDecimalUtils.floor(bmlmDetail.getBmlmPoint());
                BigDecimal tax = BigDecimalUtils.ceilingMul(bmlmDetail.getBmlmPoint(), validTax);

                bmlmDetail.setTaxRate(validTax);
                bmlmDetail.setTax(BigDecimalUtils.ceiling(tax.toString(),2).toPlainString());
                // 保存再增值数据
                int count = bmlmDetailService.save(bmlmDetail);
                // 写日志
                BizLog.biz(bsConfig.getSysName(), "function:bmlmDataNotify", "params==>" + bmlmDetail, "保存再增值详情数据结果:"
                        + count);

                // 查询本平台信息 包括平台互生号和平台客户号
                LocalInfo localInfo = commonService.getAreaPlatInfo();

                /**
                 * 积分再增值分配 F_DIST_EXT_POINT 积分再增值分配 M52500 U26000
                 * "企业：10110 加 平台：10340 减"
                 */
                List<AccountDetail> accountDetails = new ArrayList<>();
                if (tax.doubleValue() > 0) {
                    // 城市税率计算
                    AccountDetail companyTax = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                            bmlmId,// 业务流水号
                            TransType.F_DIST_EXT_POINT.getCode(), // 交易类型
                            bmlmDetail.getCustId(),// 客户号
                            bmlmDetail.getResNo(),// 互生号
                            "",// 企业名称
                            HsResNoUtils.getCustTypeByHsResNo(bmlmDetail.getResNo()),// 企业类型
                            AccountType.ACC_TYPE_POINT10510.getCode(),// 账户类型
                            BigDecimalUtils.ceiling(tax.toString(),2).toPlainString(),// 增加金额
                            "0",// 减少金额
                            localInfo.getPointCode(),// 货币币种
                            endDate,
                            "再增值积分分配企业税收",// 备注
                            false);// 是否实时

                    accountDetails.add(companyTax);
                }

                // 企业积分记账
                AccountDetail companyBmlm = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                        bmlmId, // 业务流水号
                        TransType.F_DIST_EXT_POINT.getCode(),// 交易类型
                        bmlmDetail.getCustId(),// 企业客户号
                        bmlmDetail.getResNo(), // 企业互生号
                        "", // 企业名称
                        HsResNoUtils.getCustTypeByHsResNo(bmlmDetail.getResNo()),// 企业类型
                        AccountType.ACC_TYPE_POINT10110.getCode(),// 账户类型
                        BigDecimalUtils.floor(bmlmDecimal.subtract(tax).toString(),2).toPlainString(), // 增加金额
                        "0", // 减少金额
                        localInfo.getPointCode(), // 货币币种
                        endDate,
                        "再增值积分分配企业收入",// 备注
                        false);// 是否实时

                accountDetails.add(companyBmlm);

                // 平台客户号
                String platCustId = commonService.getAreaPlatCustId();
                // 平台积分记账
                AccountDetail platBmlm = new AccountDetail(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()),// 记账流水号
                        bmlmId, // 业务流水号
                        TransType.F_DIST_EXT_POINT.getCode(),// 交易类型
                        platCustId,// 平台客户号
                        localInfo.getPlatResNo(),// 平台互生号
                        localInfo.getPlatNameCn(), // 平台名称
                        CustType.AREA_PLAT.getCode(),// 平台类型
                        AccountType.ACC_TYPE_POINT10340.getCode(),// 账户类型
                        "0", // 增加金额
                        BigDecimalUtils.floor(bmlmDetail.getBmlmPoint(),2).toPlainString(),// 减少金额
                        localInfo.getPointCode(),// 货币币种
                        endDate,
                        "再增值积分分配平台支出",// 备注
                        false);// 是否实时

                accountDetails.add(platBmlm);
                // 批量插入记帐
                accountDetailService.saveGenActDetail(accountDetails, "再增值积分分配记账分解", false);
            }
        }
        return true;
    }

    /**
     * 文件读取
     *
     * @param detail 文件详情
     * @return list
     * @throws HsException
     */
    private List<String> parseFile(FileDetail detail) throws HsException {
        File file = new File(detail.getTarget());
        List<String> contents;
        try {
            contents = FileUtils.readLines(file, "utf-8");
        } catch (IOException e) {
            SystemLog.error(bsConfig.getSysName(), "function:bm", "文件读取异常", e);
            throw new HsException(RespCode.UNKNOWN.getCode(), "文件读取异常，原因：" + e.getMessage());
        }
        return contents;
    }
}
