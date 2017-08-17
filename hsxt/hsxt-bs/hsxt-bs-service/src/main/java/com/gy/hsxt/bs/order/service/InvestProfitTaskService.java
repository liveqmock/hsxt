/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.DividendRate;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.order.enumtype.DividendStatus;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.bs.order.mapper.DividendDetailMapper;
import com.gy.hsxt.bs.order.mapper.DividendRateMapper;
import com.gy.hsxt.bs.order.mapper.PointDividendMapper;
import com.gy.hsxt.bs.order.mapper.PointInvestMapper;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 任务调度投资分红，实现积分投资分红、分红记帐、生成记帐文件
 * 
 * @Package: com.gy.hsxt.bs.order.service
 * @ClassName: InvestProfitTaskService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-27 上午11:36:16
 * @version V3.0.0
 */
@Service
public class InvestProfitTaskService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private BsConfig bsConfig;

    /**
     * 文件配置
     */
    @Resource
    private FileConfig fileConfig;

    // 注入年度分红比率mapper
    @Autowired
    DividendRateMapper dividendRateMapper;

    // 注入积分投资mapper
    @Autowired
    PointInvestMapper pointInvestMapper;

    // 注入积分投资分红mapper
    @Autowired
    PointDividendMapper pointDividendMapper;

    // 注入积分投资分红计算明细mapper
    @Autowired
    DividendDetailMapper dividendDetailMapper;

    // 注入记帐分解mapper
    @Autowired
    AccountDetailMapper accountDetailMapper;

    @Autowired
    InvestProfitService investProfitService;

    // 声明一个map对象，获取存储过程out参数的返回值
    Map<String, Object> paramMap = new HashMap<String, Object>();

    @Autowired
    IAccountRuleService accountRuleService;

    /**
     * 投资分红生成记帐分解
     * 
     * @param dividendStatus
     *            分红状态
     */
    public void genPointDividendAC(int dividendStatus, String dividendYear, String savePath) {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "投资分红生成记帐分解,params[dividendStatus:" + dividendStatus + ",dividendYear:" + dividendYear + ",savePath:"
                        + savePath + "]");
        // 分红完成，开始记帐分解
        if (dividendStatus == DividendStatus.INVEST_DIVIDEND_COMPLETE.getCode())
        {
            // 记录业务日志
            BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "params",
                    "已分红完成，开始记帐分解,params[dividendStatus:" + dividendStatus + ",dividendYear:" + dividendYear
                            + ",savePath:" + savePath + "]");
            try
            {
                Map<String, Object> paramMapAC = new HashMap<String, Object>();
                // 为记帐分解存储过程传入分红年份
                paramMapAC.put("dividendYear", dividendYear);
                // 流通帐户
                paramMapAC.put("normalAccount", AccountType.ACC_TYPE_POINT20110.getCode());
                // 定向消费帐户
                paramMapAC.put("directionalAccount", AccountType.ACC_TYPE_POINT20120.getCode());
                // 慈善救助基金帐户
                paramMapAC.put("fundAccount", AccountType.ACC_TYPE_POINT20130.getCode());
                // 互生币税收帐户
                paramMapAC.put("tuaxAccount", AccountType.ACC_TYPE_POINT20610.getCode());
                // 自定义企业分红默认税率
                paramMapAC.put("custDividendTaux", "");
                // 货币币种
                paramMapAC.put("currencyCode", CurrencyCode.HSB.getCode());
                // 企业交易码
                paramMapAC.put("transCodeEnt", TransType.C_INVEST_BONUS.getCode());
                // 个人交易码
                paramMapAC.put("transCodePer", TransType.P_INVEST_BONUS.getCode());
                // 执行分红记帐，返回执行结果
                pointInvestMapper.callGenInvestDividendAC(paramMapAC);
                // 获取存储过程返回值，1表示分红成功
                Integer res = (Integer) paramMapAC.get("returnStatus");
                // 1表示存储过程成功
                if (res == 1)
                {
                    // 记录业务日志
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "已分红完成，执行记帐分解存储过程成功,params[dividendStatus:" + dividendStatus + ",dividendYear:"
                                    + dividendYear + ",savePath:" + savePath + "]");
                    // 首次执行生成投资分红记帐文件--改由年费处统一生成记帐文件
                    // genPointDividendACFile(DividendStatus.ACCOUNT_DETAILS_COMPLETE.getCode(),
                    // dividendYear, savePath);
                }
                // 记录业务日志
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "params", "已分红完成，记帐分解完成,params[dividendStatus:" + dividendStatus + ",dividendYear:"
                                + dividendYear + ",savePath:" + savePath + "]");
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "method:"
                        + Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_GEN_POINT_INVEST_ACCOUNT_DETAIL_ERROR.getCode()
                                + "投资分红生成记帐分解异常,params[dividendStatus:" + dividendStatus + ",dividendYear:"
                                + dividendYear + ",savePath:" + savePath + "]", e);
                throw new HsException(BSRespCode.BS_GEN_POINT_INVEST_ACCOUNT_DETAIL_ERROR.getCode(),
                        "投资分红生成记帐分解异常,params[dividendStatus:" + dividendStatus + ",dividendYear:" + dividendYear
                                + ",savePath:" + savePath + "]" + e);
            }
        }
    }

    /**
     * 生成投资分红记录
     * 
     * @param dividendYear
     *            分红年份
     */
    public void genPointDividendRecord(String year) {
        String dividendYear = "";
        if (StringUtils.isNotBlank(year))
        {
            dividendYear = year;
        }
        else
        {
            // 获取分红年份，-1为去年
            dividendYear = DateUtil.getAssignYear(-1);
        }
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "生成投资分红记录,params[dividendYear:" + dividendYear + "]");
        // 对账文件生成路径
        String savePath = fileConfig.getDirRoot();
        // 获取最新年度分红比率
        DividendRate dividendRate = dividendRateMapper.findNewDividendRate(dividendYear);
        if (dividendRate != null)
        {
            // 如果分红状态为1：还未执行分红
            if (DividendStatus.WAIT_DIVIDEND.getCode().intValue() == dividendRate.getDividendStatus())
            {
                // 记录业务日志
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "params", "生成投资分红记录：开始执行分红");
                try
                {
                    /**
                     * 获取个人投资分红比率{
                     */
                    // 个人投资分红流通币比率
                    String perDividHsbScale = accountRuleService.getPerInvesDividHsbScale();
                    // 个人投资分红定向消费币比率
                    String perDirectHsbScale = accountRuleService.getPerInvesDirectHsbScale();
                    /**
                     * }
                     */
                    /**
                     * 获取企业投资分红比率{
                     */
                    // 企业投资分红流通币比率
                    String entDividHsbScale = accountRuleService.getEntInvesDividHsbScale();
                    // 企业投资分红慈善救助基金比率
                    String entDirectHsbScale = accountRuleService.getEntInvesDirectHsbScale();
                    /**
                     * }
                     */

                    // 传入分红年份
                    paramMap.put("dividendYear", dividendYear);
                    // 传入自定义企业分红默认税率
                    paramMap.put("custDividendTaux", "");
                    // 传入个人投资分红流通币比率
                    paramMap.put("perDividHsbScale", perDividHsbScale);
                    // 传入个人投资分红定向消费币比率
                    paramMap.put("perDirectHsbScale", perDirectHsbScale);
                    // 传入企业投资分红流通币比率
                    paramMap.put("entDividHsbScale", entDividHsbScale);
                    // 传入企业投资分红慈善救助基金比率
                    paramMap.put("entDirectHsbScale", entDirectHsbScale);

                    // 执行分红，返回执行结果
                    pointInvestMapper.callGenInvestDividend(paramMap);
                    // 获取存储过程返回值，1表示分红成功
                    Integer res = (Integer) paramMap.get("returnStatus");
                    // 1表示执行存储过程投资分红成功
                    if (res == 1)
                    {
                        // 记录业务日志
                        BizLog.biz(this.getClass().getName(),
                                Thread.currentThread().getStackTrace()[1].getMethodName(), "params",
                                "生成投资分红记录：执行分红存储过程成功");
                        // 首次执行分红后继续执行记帐分解，这时分红状态应为分红完成
                        genPointDividendAC(DividendStatus.INVEST_DIVIDEND_COMPLETE.getCode(), dividendYear, savePath);
                    }

                    // 记录业务日志
                    BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                            "params", "生成投资分红记录：执行分红完成");
                }
                catch (Exception e)
                {
                    SystemLog.error(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(),
                            BSRespCode.BS_GEN_POINT_INVEST_RECORD_ERROR.getCode() + "生成投资分红记录异常,params[dividendYear:"
                                    + dividendYear + "]", e);
                    throw new HsException(BSRespCode.BS_GEN_POINT_INVEST_RECORD_ERROR.getCode(),
                            "生成投资分红记录异常,params[dividendYear:" + dividendYear + "]" + e);
                }
            }
            // 其它情况中断后，但分红已经完成，在次触发分红动作后继续执行记帐分解
            else if (DividendStatus.INVEST_DIVIDEND_COMPLETE.getCode().intValue() == dividendRate.getDividendStatus())
            {
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "params", dividendYear + "年再次触发分红动作执行记帐分解");
                // 再次执行投资分红记帐分解
                genPointDividendAC(dividendRate.getDividendStatus(), dividendYear, savePath);
            }
            // 其它情况首次未完成记帐文件时，但记帐分解已完成，再次在次触发分红动作后提交执行记帐文件生成
            else if (DividendStatus.ACCOUNT_DETAILS_COMPLETE.getCode().intValue() == dividendRate.getDividendStatus())
            {
                // 16.1.26注释，改为统一生成记帐文件com.gy.hsxt.bs.batch.BatchExportAcctDetailTxtFile
                // 执行生成投资分红记帐文件（多线程）
                // investProfitService.genPointDividendACFile(dividendYear,
                // savePath);
                // 记录业务日志
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        dividendYear, "生成投资分红记录：" + dividendYear + "年待生成记账文件");
            }
            else if (DividendStatus.ACCOUNT_FILE_COMPLETE.getCode().intValue() == dividendRate.getDividendStatus())
            {
                // 记录业务日志
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        dividendYear, "生成投资分红记录：" + dividendYear + "年已生成记账文件");
            }
            else
            {
                // 记录业务日志
                BizLog.biz(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        dividendYear, "生成投资分红记录：" + dividendYear + "年没有需要分红的记录");
            }
        }
    }
}
