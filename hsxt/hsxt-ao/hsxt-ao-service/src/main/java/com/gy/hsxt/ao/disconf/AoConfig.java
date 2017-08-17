/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.disconf;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * AO系统属性配置读取类
 * 
 * @version V1.0
 * @Package: com.gy.hsxt.ao.disconf
 * @ClassName: AoConfig
 * @Description: TODO
 * @author: liyh
 * @date: 2015-12-17 上午11:34:43
 */
@Component
@Scope("singleton")
// @DisconfFile(filename = "hsxt-ao.properties")
@DisconfUpdateService(classes = { AoConfig.class })
public class AoConfig implements IDisconfUpdate, InitializingBean {

    /**
     * 默认字符集 - UTF-8
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 数据分割符
     */
    public static final String DATA_SEPARATOR = "|";

    /**
     * 数据结束标识
     */
    public static final String DATA_END = "END";

    /**
     * AO与AC对账文件名前缀
     */
    public static final String AO_AC_PREFIX = "AO_AC_";

    /**
     * AO与GP对账文件名前缀
     */
    public static final String AO_GP_PREFIX = "AO_GP_PAY_";

    /**
     * 校验文件的后缀 例 :20160101_CHK
     */
    public static final String FILE_CHECK_TAIL = "_CHK";

    /**
     * 集群部署应用节点编号
     */
    private String appNode;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 银行转帐商户号
     */
    private String merchantNo;

    /**
     * 转帐加急标志
     */
    private String urgentFlag;

    /**
     * 批结算，批上传老化月份数
     */
    private Integer dataTransfer;

    /**
     * 生成记账对账文件切割的交易笔数
     */
    private Integer sumRow;

    /**
     * 生成文件路径
     */
    private String savePath;

    /**
     * 批量转账数量
     */
    private Integer transCashBatchNum;

    /**
     * 批量自动提现时间
     */
    private Integer batchAutoTransCommitTime;

    @DisconfFileItem(name = "batch.auto.trans.time", associateField = "batchAutoTransCommitTime")
    public Integer getBatchAutoTransCommitTime() {
        return batchAutoTransCommitTime;
    }

    public void setBatchAutoTransCommitTime(Integer batchAutoTransCommitTime) {
        this.batchAutoTransCommitTime = batchAutoTransCommitTime;
    }

    @DisconfFileItem(name = "trans.batch.max", associateField = "transCashBatchNum")
    public Integer getTransCashBatchNum() {
        return transCashBatchNum;
    }

    public void setTransCashBatchNum(Integer transCashBatchNum) {
        this.transCashBatchNum = transCashBatchNum;
    }

    /**
     * @return the 生成文件路径
     */
    @DisconfFileItem(name = "file.savePath", associateField = "savePath")
    public String getSavePath() {
        return savePath;
    }

    /**
     * @param savePath
     *            生成文件路径 the savePath to set
     */
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    /**
     * @return the 生成记账对账文件切割的交易笔数
     */
    @DisconfFileItem(name = "sumRow.num", associateField = "sumRow")
    public Integer getSumRow() {
        return sumRow;
    }

    /**
     * @param sumRow
     *            生成记账对账文件切割的交易笔数 the sumRow to set
     */
    public void setSumRow(Integer sumRow) {
        this.sumRow = sumRow;
    }

    /**
     * @return the 转帐加急标志
     */
    @DisconfFileItem(name = "trans.out.urgent.flag", associateField = "urgentFlag")
    public String getUrgentFlag() {
        return urgentFlag;
    }

    /**
     * @param urgentFlag
     *            转帐加急标志 the urgentFlag to set
     */
    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    /**
     * @return the 银行转帐商户号
     */
    @DisconfFileItem(name = "trans.out.merchant.no", associateField = "merchantNo")
    public String getMerchantNo() {
        return merchantNo;
    }

    /**
     * @param merchantNo
     *            银行转帐商户号 the merchantNo to set
     */
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    /**
     * @return the appNode 集群部署应用节点编号
     */
    @DisconfFileItem(name = "system.instance.no", associateField = "appNode")
    public String getAppNode() {
        return appNode;
    }

    /**
     * @param appNode
     *            集群部署应用节点编号 the appNode to set
     */
    public void setAppNode(String appNode) {
        this.appNode = appNode;
    }

    /**
     * @return the sysName 系统名称
     */
    @DisconfFileItem(name = "system.id", associateField = "sysName")
    public String getSysName() {
        return sysName;
    }

    /**
     * @param sysName
     *            系统名称 the sysName to set
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    /**
     * @return the dataTransfer 批结算，批上传老化月份数
     */
    @DisconfFileItem(name = "datatransfer.lastmonth", associateField = "dataTransfer")
    public Integer getDataTransfer() {
        return dataTransfer;
    }

    /**
     * @param dataTransfer
     *            批结算，批上传老化月份数 the dataTransfer to set
     */
    public void setDataTransfer(Integer dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    @Override
    public void reload() throws Exception {
    }

    public static String getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(c.getTime());
    }

    /**
     * 获取对账文件路径
     * 
     * @return
     */
    public String joinPreDirPath(String scanDate) {
        return savePath + File.separator + "TCAS" + File.separator + sysName + File.separator + scanDate
                + File.separator;
    }

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>
     * This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an exception
     * in the event of misconfiguration.
     * 
     * @throws Exception
     *             in the event of misconfiguration (such as failure to set an
     *             essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        HsAssert.notNull(this.sumRow, AOErrorCode.AO_PARAMS_NULL, "属性[sumRow.num]不能为空");
        HsAssert.isTrue(this.sumRow > 0, AOErrorCode.AO_PARAM_ERROR, "属性[sumRow.num]必须大于0");
        HsAssert.hasText(this.savePath, AOErrorCode.AO_PARAM_ERROR, "属性[file.savePath]不能为空");
    }
}
