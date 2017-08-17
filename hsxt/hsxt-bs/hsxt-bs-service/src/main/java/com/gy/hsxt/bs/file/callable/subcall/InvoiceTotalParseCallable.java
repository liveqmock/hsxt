/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.ParseInfo;
import com.gy.hsxt.bs.file.callable.ParseFileCallable;
import com.gy.hsxt.bs.invoice.enumtype.PoolFlag;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePoolService;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * 发票统计数据文件解析调度
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : InvoiceTotalParseCallable
 * @Description : 发票统计数据文件解析调度
 * @Author : chenm
 * @Date : 2016/2/25 12:18
 * @Version V3.0.0.0
 */
public class InvoiceTotalParseCallable extends ParseFileCallable {


    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param parseInfo          解析文件信息
     */
    private InvoiceTotalParseCallable(ApplicationContext applicationContext, String executeId, ParseInfo parseInfo) {
        super(applicationContext, executeId, parseInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public ParseInfo call() throws Exception {
        try {
            this.getLogger().debug("================解析发票统计文件[{}]开始================", this.getParseInfo().getFileName());
            File file = FileUtils.getFile(this.getParseInfo().getDirPath() + this.getParseInfo().getFileName());
            List<String> lines = FileUtils.readLines(file, FileConfig.DEFAULT_CHARSET);
            if (CollectionUtils.isNotEmpty(lines)) {
                IInvoicePoolService invoicePoolService = this.getApplicationContext().getBean(IInvoicePoolService.class);
                for (String line : lines) {
                    //排除结束标识
                    if (!line.contains(FileConfig.DATA_END)) {
                        //分拆数据
                        String[] metadata = StringUtils.splitPreserveAllTokens(line, "|");

                        if (this.getLogger().isDebugEnabled()) {
                            for (int i = 0; i < metadata.length; i++) {
                                this.getLogger().debug(i + " →→→ " + metadata[i]);
                            }
                        }

                        String custId = metadata[0].trim();//客户号
                        Assert.hasText(custId, "企业客户号为空");
                        String custName = metadata[1].trim();//客户名称
                        String bizType = metadata[2].trim();//业务类型
                        Assert.isTrue(BizType.check(bizType), "[" + custName + "]客户的[" + bizType + "]业务类型不存在");
                        String amount = metadata[3].trim();//增量金额
                        //校验客户类型
                        Assert.isTrue(NumberUtils.isNumber(amount), "[" + custName + "]客户的[" + bizType + "]业务对应金额[" + amount + "]有误");

                        InvoicePool pool = invoicePoolService.queryBeanByIdAndType(custId, bizType);
                        String lastDate = DateUtil.DateToString(DateUtil.StringToDate(StringUtils.left(file.getName(), 8), DateUtil.DATE_FORMAT));
                        //判断发票池记录是否存在
                        if (pool == null) {//不存在则新增
                            pool = new InvoicePool();
                            pool.setCustId(custId);
                            pool.setResNo(StringUtils.left(custId, 11));
                            pool.setAllAmount(amount);
                            pool.setRemainAmount(amount);
                            pool.setBizType(bizType);
                            pool.setCustName(custName);
                            pool.setInvoiceMaker(InvoiceMaker.makeOf(bizType).ordinal());
                            pool.setLastDate(lastDate);
                            invoicePoolService.saveBean(pool);
                        } else {//存在则增加总数
                            if (!pool.getLastDate().equals(lastDate) && pool.getGoOn() != PoolFlag.STOP.ordinal()) {
                                pool.setAllAmount(BigDecimalUtils.floorAdd(amount, pool.getAllAmount()).toPlainString());
                                pool.setRemainAmount(BigDecimalUtils.floorAdd(amount, pool.getRemainAmount()).toPlainString());
                                pool.setLastDate(lastDate);
                                invoicePoolService.modifyBean(pool);
                            }
                        }
                    }
                }
            }
            this.getParseInfo().setCompleted(true);
            this.getLogger().debug("================解析发票统计文件[{}]成功================", this.getParseInfo().getFileName());
            //文件解析成功之后，报告成功
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 0, "执行成功");
        } catch (Exception e) {
            this.getLogger().error("========解析发票统计文件[{}]失败=======", this.getParseInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "解析发票统计文件[" + this.getParseInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
        }
        return this.getParseInfo();
    }

    /**
     * 构建函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param parseInfo          解析文件信息
     * @return {@code InvoiceTotalParseCallable}
     */
    public static InvoiceTotalParseCallable bulid(ApplicationContext applicationContext, String executeId, ParseInfo parseInfo) {
        return new InvoiceTotalParseCallable(applicationContext, executeId, parseInfo);
    }
}
