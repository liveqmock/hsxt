/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeDetailService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeOrderService;
import com.gy.hsxt.bs.annualfee.utils.AnnualAreaUtils;
import com.gy.hsxt.bs.bean.annualfee.*;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.CheckFileCallable;
import com.gy.hsxt.bs.file.callable.subcall.AnnualFeeScanDataCallable;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 年费信息扫描任务实现
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : AnnualFeeScanTask
 * @Description : 1.年费计费单、年费业务单 的 新增或修改 2.更新年费信息 3.生成年费欠费文件
 * <p>
 * 每天凌晨后扫描昨天到期的企业年费信息
 * </p>
 * @Author : chenm
 * @Date : 2015-10-9 下午6:00:21
 * @Version V1.0
 */
@Service
public class AnnualFeeScanTask extends AbstractBatchService {

    /**
     * 年费计费单业务接口
     */
    @Resource
    private IAnnualFeeDetailService annualFeeDetailService;

    /**
     * 年费信息业务接口
     */
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    /**
     * 年费业务订单业务接口
     */
    @Resource
    private IAnnualFeeOrderService annualFeeOrderService;

    /**
     * 公共参数业务接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 用户中心状态同步接口
     */
    @Resource
    private IUCBsEntService bsEntService;


    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        //扫描日期
        Date scan = DateUtil.StringToDate(scanDate, DateUtil.DATE_FORMAT);
        String date = DateUtil.addDaysStr(scan, 1);
        //扫描创建企业年费计费单和业务订单
        doScan(date);

        //扫描生成文件
        generateFile(executeId, scanDate);
    }

    /**
     * 扫描创建企业年费计费单和业务订单
     *
     * @param scanDate 扫描日期
     * @throws Exception
     */
    @Transactional
    private void doScan(String scanDate) throws Exception {

        this.getLogger().debug("=======[{}]扫描创建企业年费计费单和业务订单开始========", scanDate);

        // 设置查询条件
        AnnualFeeInfoQuery query = new AnnualFeeInfoQuery();
        query.setEndLineDate(scanDate);// 截至日期小于今天
        // 查询所有欠费的年费信息
        List<AnnualFeeInfo> annualFeeInfos = annualFeeInfoService.queryListByQuery(query);

        if (CollectionUtils.isNotEmpty(annualFeeInfos)) {
            //刚到期的年费企业
            List<BsEntStatusInfo> infos = new ArrayList<>();
            for (AnnualFeeInfo annualFeeInfo : annualFeeInfos) {
                try {
                    /**
                     * 未欠费状态的，则新增年费业务单和计费单
                     */
                    if (annualFeeInfo.getIsArrear() == 0) {
                        //同步年费状态
                        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
                        bsEntStatusInfo.setEntCustId(annualFeeInfo.getEntCustId());
                        bsEntStatusInfo.setIsOweFee(1);//1-欠费 0-未欠费
                        bsEntStatusInfo.setExpireDate(annualFeeInfo.getEndDate());
                        infos.add(bsEntStatusInfo);
                    }
                    this.dealAnnualFeeInfo(annualFeeInfo);
                } catch (Exception e) {
                    this.getLogger().error("=======扫描处理年费信息异常，参数：\n\t {} ========", annualFeeInfo, e);
                }
            }
            //同步年费状态
            if (CollectionUtils.isNotEmpty(infos)) {
                bsEntService.batchUpdateEntStatusInfo(infos);
            }
        }
        this.getLogger().debug("=======[{}]扫描创建企业年费计费单和业务订单结束========", scanDate);
    }

    /**
     * 处理年费信息
     *
     * @param annualFeeInfo 年费信息
     * @throws Exception
     */
    @Transactional
    private void dealAnnualFeeInfo(AnnualFeeInfo annualFeeInfo) throws Exception {

        //计算生成计费单列表 不含缴纳期
        List<AnnualFeeDetail> details = AnnualAreaUtils.parseAnnualAreas(annualFeeInfo, false);
        if (CollectionUtils.isNotEmpty(details)) {
            // 获取平台本地信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 查询未支付的年费业务订单
            AnnualFeeOrder annualFeeOrder = annualFeeOrderService.queryBeanForWaitPayByCustId(annualFeeInfo.getEntCustId());
            // 新建年费业务单
            Order order = new Order();

            order.setQuantity(details.size());// 年数
            //应缴年费对应的互生币数额
            String hsbAmount = BigDecimalUtils.ceilingMul(annualFeeInfo.getPrice(), String.valueOf(details.size())).toPlainString();
            order.setOrderHsbAmount(BigDecimalUtils.ceiling(hsbAmount, 2).toPlainString());//互生币金额
            //应缴年费对应的现金数额
            String cash = BigDecimalUtils.ceilingDiv(hsbAmount, localInfo.getExchangeRate()).toPlainString();
            order.setOrderCashAmount(BigDecimalUtils.ceiling(cash, 2).toPlainString());// 货币金额

            order.setOrderDerateAmount("0");// 订单减免金额
            //应缴年费对应的原始金额
            String originalAmount = BigDecimalUtils.ceilingAdd(hsbAmount, order.getOrderDerateAmount()).toPlainString();// 原始金额
            order.setOrderOriginalAmount(originalAmount);

            order.setQuantity(details.size());//数量

            order.setOrderOperator(BSConstants.SYS_OPERATOR);// 订单操作员
            //没有年费业务单，则生成
            if (annualFeeOrder.getOrder() == null) {
                order.setCustId(annualFeeInfo.getEntCustId());// 客户号
                order.setHsResNo(annualFeeInfo.getEntResNo());// 互生号
                order.setCustName(annualFeeInfo.getEntCustName());// 客户名称
                order.setCurrencyCode(CurrencyCode.HSB.getCode());// 货币币种
                order.setOrderChannel(Channel.SYS.getCode());// 订单渠道
                order.setExchangeRate(localInfo.getExchangeRate());// 货币转换率

                annualFeeOrderService.saveBean(AnnualFeeOrder.bulid(order));

            } else {
                //有年费业务单，则更新金额
                annualFeeOrderService.modifyBeanForAmount(annualFeeOrder);
            }
            //接着处理计费单
            for (AnnualFeeDetail detail : details) {
                detail.setEntCustId(annualFeeInfo.getEntCustId());//设置客户号

                // 查询企业是否提交过下一个缴费区间的计费单信息
                AnnualFeeDetail detailInDB = annualFeeDetailService.queryBeanByAnnualArea(detail);
                if (detailInDB == null) {
                    // 设置计费单参数
                    detail.build(order);
                    // 保存计费单
                    annualFeeDetailService.saveBean(detail);
                }
            }
            //接着修改年费信息
            annualFeeInfo.setIsArrear(1);// 设置欠费状态
            annualFeeInfo.setArrearYearNum(details.size());// 欠费一年
            annualFeeInfo.setArrearAmount(BigDecimalUtils.ceiling(hsbAmount, 2).toPlainString());// 设置欠费金额
            annualFeeInfo.setStatusChangeDate(AnnualAreaUtils.nextToday(0));// 今天
            annualFeeInfo.setUpdatedby(BSConstants.SYS_OPERATOR);// 修改操作者
            annualFeeInfoService.modifyBean(annualFeeInfo);
        } else {
            this.getLogger().info("=====计算生成的计费单列表为空，参数：\n\t {} =======", annualFeeInfo);
        }
    }

    /**
     * 年费信息文件形成处理
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    private void generateFile(String executeId, String scanDate) throws Exception {

        String dirPath = this.getFileConfig().joinPreDirPath(FileConfig.ANNUAL_FEE_ACCT, scanDate);
        //最大行数
        int maxLineNum = this.getFileConfig().getMaxLineNum();
        // 设置查询条件
        AnnualFeeOrderQuery annualFeeOrderQuery = new AnnualFeeOrderQuery();
        annualFeeOrderQuery.setOrderStatus(OrderStatus.WAIT_PAY.getCode());

        Page page = new Page(1, maxLineNum);
        // 查询所有欠费的年费信息
        List<AnnualFeeOrder> annualFeeOrders = annualFeeOrderService.queryListForPage(page, annualFeeOrderQuery);

        int fileNum = (page.getCount() + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, scanDate + FileConfig.FILE_CHECK_TAIL);

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);

        if (CollectionUtils.isNotEmpty(annualFeeOrders)) {
            for (int i = fileNum; i > 0; i--) {

                List<AnnualFeeOrder> data;//每一页的数据

                if (i == 1) {
                    data = annualFeeOrders;
                } else {
                    page.setCurPage(i);
                    data = annualFeeOrderService.queryListForPage(page, annualFeeOrderQuery);
                }
                /**
                 * 数据文件名称格式：YYYYMMDD_N.TXT（YYYYMMDD年月日，N从1开始递增）
                 */
                String txtName = scanDate + "_" + i + FileConfig.FILE_SUFFIX;//数据文件名称

                DataInfo<AnnualFeeOrder> dataInfo = DataInfo.bulid(dirPath, txtName, data);

                AnnualFeeScanDataCallable annualFeeScanDataCallable = AnnualFeeScanDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);

                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, annualFeeScanDataCallable);
            }
        } else {
            //没有数据文件，只生成check文件
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}
