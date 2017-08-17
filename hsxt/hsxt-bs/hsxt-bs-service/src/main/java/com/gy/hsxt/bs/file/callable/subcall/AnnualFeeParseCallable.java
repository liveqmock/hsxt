/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.annualfee.service.AnnualFeeDetailService;
import com.gy.hsxt.bs.annualfee.service.AnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.service.AnnualFeeOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.ParseInfo;
import com.gy.hsxt.bs.file.callable.ParseFileCallable;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 年费解析调度
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : AnnualFeeParseCallable
 * @Description : 年费解析调度
 * @Author : chenm
 * @Date : 2016/2/26 10:10
 * @Version V3.0.0.0
 */
public class AnnualFeeParseCallable extends ParseFileCallable {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param parseInfo          解析文件信息
     */
    private AnnualFeeParseCallable(ApplicationContext applicationContext, String executeId, ParseInfo parseInfo) {
        super(applicationContext, executeId, parseInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    @Transactional
    public ParseInfo call() throws Exception {
        try {
            this.getLogger().debug("================年费解析文件[{}]开始================", this.getParseInfo().getFileName());
            File file = FileUtils.getFile(this.getParseInfo().getDirPath() + this.getParseInfo().getFileName());
            List<String> lines = FileUtils.readLines(file, FileConfig.DEFAULT_CHARSET);
            if (CollectionUtils.isNotEmpty(lines)) {
                //系统互动消息列表
                List<DynamicBizMsgBean> dynamicBeans = new ArrayList<>();
                //短信信息列表
                List<NoteBean> noteBeans = new ArrayList<>();
                //分解记账
                List<AccountDetail> details = new ArrayList<>();
                for (String line : lines) {
                    //排除结束标识
                    if (!line.contains(FileConfig.DATA_END)) {
                        //分拆数据
                        String[] metadata = StringUtils.splitPreserveAllTokens(line, "|");

                        for (int i = 0; i < metadata.length; i++) {
                            this.getLogger().debug(i + " →→→ " + metadata[i]);
                        }

                        String custId = StringUtils.trimToEmpty(metadata[0]);//客户号

                        String resNo = StringUtils.trimToEmpty(metadata[1]);//互生号

                        String custType = StringUtils.trimToEmpty(metadata[2]);//客户类型
                        //校验客户类型
                        Assert.isTrue(NumberUtils.isNumber(custType), "[" + custId + "]的客户类型不是数字");

                        String orderNo = StringUtils.trimToEmpty(metadata[10]);//年费业务订单号

                        String success = StringUtils.trimToEmpty(metadata[metadata.length - 1]);//记账结果 0-为失败 1-成功

                        Assert.isTrue(NumberUtils.isNumber(success), "[" + custId + "]的记账结果不是数字(0-失败 1-成功)");
                        //查询年费业务单
                        AnnualFeeOrderService annualFeeOrderService = this.getApplicationContext().getBean(AnnualFeeOrderService.class);

                        AnnualFeeOrder annualFeeOrder = annualFeeOrderService.queryBeanById(orderNo);
                        if ("1".equals(success)) {//1-成功
                            if (HsResNoUtils.isServiceResNo(resNo) || HsResNoUtils.isTrustResNo(resNo) || HsResNoUtils.isMemberResNo(resNo)) {//如果没有更新状态，则更新

                                AnnualFeeDetailService annualFeeDetailService = this.getApplicationContext().getBean(AnnualFeeDetailService.class);
                                //更新年费计费单
                                annualFeeDetailService.modifyBeanForPaid(orderNo);

                                AnnualFeeInfoService annualFeeInfoService = this.getApplicationContext().getBean(AnnualFeeInfoService.class);
                                //更新年费信息
                                String orgEndDate = annualFeeInfoService.modifyBeanForPaid(custId, orderNo);

                                //更新年费业务单 与 记账分解
                                Order order = annualFeeOrder.getOrder();
                                order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());
                                order.setPayStatus(PayStatus.PAY_FINISH.getCode());
                                order.setPayChannel(PayChannel.HS_COIN_PAY.getCode());
                                annualFeeOrderService.modifyBeanForPaid(annualFeeOrder, orgEndDate, false);

                                //拼装互动消息实体
                                dynamicBeans.add(generateDynamicBean(annualFeeOrder, true));
                                //拼装短信消息实体
                                noteBeans.add(generateNoteBean(annualFeeOrder));
                            }
                            //保存记账分解记录
                            AccountDetail accountDetail = new AccountDetail();// 创建对象
                            accountDetail.setAccountNo(StringUtils.trimToEmpty(metadata[16]));// 生成guid
                            accountDetail.setBizNo(orderNo);// 交易流水号
                            accountDetail.setTransType(StringUtils.trimToEmpty(metadata[9]));// 交易类型
                            accountDetail.setCustId(custId);// 客户号
                            accountDetail.setHsResNo(resNo);// 互生号
                            accountDetail.setCustName(StringUtils.EMPTY);// 客户名称
                            accountDetail.setCustType(Integer.valueOf(custType));// 客户类型
                            accountDetail.setAccType(StringUtils.trimToEmpty(metadata[4]));// 账户类型编码
                            accountDetail.setAddAmount(StringUtils.trimToEmpty(metadata[5]));// 增向金额
                            accountDetail.setDecAmount(StringUtils.trimToEmpty(metadata[6]));// 减向金额
                            accountDetail.setCurrencyCode(StringUtils.EMPTY);// 币种
                            accountDetail.setFiscalDate(StringUtils.trimToEmpty(metadata[12]));// 会计日期
                            accountDetail.setRemark(StringUtils.trimToEmpty(metadata[15]));// 备注内容
                            accountDetail.setStatus(true);// 记账状态
                            details.add(accountDetail);
                        } else {
                            //拼装互动消息实体
                            if (HsResNoUtils.isServiceResNo(resNo) || HsResNoUtils.isTrustResNo(resNo) || HsResNoUtils.isMemberResNo(resNo)) {
                                dynamicBeans.add(generateDynamicBean(annualFeeOrder, false));
                            }
                        }
                    }
                }
                //保存分解记账记录
                if (CollectionUtils.isNotEmpty(details)) {
                    // 执行插入
                    IAccountDetailService accountDetailService = this.getApplicationContext().getBean(IAccountDetailService.class);
                    accountDetailService.saveGenActDetail(details, "自动缴扣年费", false);
                }
                INtService ntService = this.getApplicationContext().getBean(INtService.class);
                //先发短信
                if (CollectionUtils.isNotEmpty(noteBeans)) {
                    for (NoteBean note : noteBeans) {
                        try {
                            ntService.sendNote(note);
                        } catch (NoticeException e) {
                            this.getLogger().error("========发送扣除年费成功的短信[{}]失败=======", note, e);
                        }
                    }
                }
                //推送互动消息
                if (CollectionUtils.isNotEmpty(dynamicBeans)) {
                    for (DynamicBizMsgBean bean : dynamicBeans) {
                        try {
                            ntService.sendDynamicBiz(bean);
                        } catch (NoticeException e) {
                            this.getLogger().error("========推送扣除年费互动信息[{}]失败=======", bean, e);
                        }
                    }
                }
            }
            this.getLogger().info("================年费解析文件[{}]结束================", this.getParseInfo().getFileName());
            this.getParseInfo().setCompleted(true);
            //文件解析成功之后，报告成功
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 0, "执行成功");
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            this.getLogger().error("========年费解析文件[{}]失败=======", this.getParseInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "年费解析文件[" + this.getParseInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
        }
        return this.getParseInfo();
    }

    /**
     * 构建函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param parseInfo          解析文件信息
     * @return {@code AnnualFeeParseCallable}
     */
    public static AnnualFeeParseCallable bulid(ApplicationContext applicationContext, String executeId, ParseInfo parseInfo) {
        return new AnnualFeeParseCallable(applicationContext, executeId, parseInfo);
    }

    /**
     * 扣年费的互动信息实体
     *
     * @param annualFeeOrder 年费订单
     * @param success        是否扣款成功
     * @return bean
     */
    private DynamicBizMsgBean generateDynamicBean(AnnualFeeOrder annualFeeOrder, boolean success) {
        /**
         * 成功模版：
         * {0}，贵单位的互生系统使用年费已从互生币账户扣除成功，本次缴纳年费区间为{1}至{2}。
         *
         * 失败模版：
         * {0}，贵单位的互生系统使用年费因互生币账户余额不足扣除失败，请及时缴纳，以免影响贵单位在平台中的权益。
         */
        DynamicBizMsgBean bean = new DynamicBizMsgBean();

        try {
            Order order = annualFeeOrder.getOrder();
            Map<String, String> contents = new HashMap<>();
            contents.put("{0}", order.getCustName());//企业名称
            bean.setMsgCode("203");//消息大类
            if (success) {
                contents.put("{1}", annualFeeOrder.getAreaStartDate());// 开始时间
                contents.put("{2}", annualFeeOrder.getAreaEndDate());// 截至时间
                bean.setSubMsgCode("2030105");//消息成功子类
            }else {
                bean.setSubMsgCode("2030106");//消息失败子类
            }

            bean.setMsgId(order.getOrderNo());// 消息编号
            bean.setCustType(order.getCustType());//客户类型
            //查询基础信息
            ICommonService commonService = this.getApplicationContext().getBean(ICommonService.class);
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            bean.setHsResNo(localInfo.getPlatResNo());//发送消息的互生号
            bean.setCustName(localInfo.getPlatNameCn());//发送消息的客户名称

            //从用户中心获取企业基本信息
            IUCBsEntService bsEntService = this.getApplicationContext().getBean(IUCBsEntService.class);

            BsEntBaseInfo baseInfo = bsEntService.searchEntBaseInfo(order.getCustId());

            if (baseInfo.getStartResType() != null) { //服务公司为空
                bean.setBuyResType(baseInfo.getStartResType());
            }

            String type = success ? String.valueOf(MsgBizType.MSG_BIZ_TYPE111.getCode()) : String.valueOf(MsgBizType.MSG_BIZ_TYPE112.getCode());

            bean.setBizType(type);// 消息类型
            bean.setMsgReceiver(new String[]{order.getHsResNo()});//接受者

            String title = success ? "扣除年费成功通知" : "扣除年费失败通知";
            bean.setMsgTitle(title);//消息标题
            bean.setContent(contents);//消息内容
            bean.setPriority(Priority.HIGH.getPriority());//权限级别
            bean.setSender(BSConstants.SYS_OPERATOR);
        } catch (Exception e) {
            this.getLogger().error("========拼装扣年费的互动信息实体失败=======", e);
        }
        return bean;
    }

    /**
     * 扣年费成功的短信信息
     *
     * @param annualFeeOrder 年费订单
     * @return bean
     */
    private NoteBean generateNoteBean(AnnualFeeOrder annualFeeOrder) {
        //拼装短信信息
        NoteBean noteBean = new NoteBean();
        try {
            //年费订单
            Order order = annualFeeOrder.getOrder();
            //从用户中心获取企业基本信息
            IUCBsEntService bsEntService = this.getApplicationContext().getBean(IUCBsEntService.class);
            BsEntAllInfo allInfo = bsEntService.searchEntAllInfo(order.getCustId());
            BsEntMainInfo mainInfo = allInfo.getMainInfo();
            BsEntBaseInfo baseInfo = allInfo.getBaseInfo();
            /**
             * {0}，贵单位的互生系统使用年费已从互生币账户扣除成功，本次缴纳年费区间为{1}至{2}。
             */
            Map<String, String> contents = new HashMap<>();
            contents.put("{0}", order.getCustName());//企业名称
            contents.put("{1}", annualFeeOrder.getAreaStartDate());// 开始时间
            contents.put("{2}", annualFeeOrder.getAreaEndDate());// 截至时间

            noteBean.setMsgId(GuidAgent.getStringGuid(BizGroup.BS));
            noteBean.setHsResNo(mainInfo.getEntResNo());
            noteBean.setCustName(mainInfo.getEntName());
            if (baseInfo.getStartResType() != null) { //服务公司为空
                noteBean.setBuyResType(baseInfo.getStartResType());
            }
            noteBean.setMsgReceiver(new String[]{mainInfo.getContactPhone()});//收信手机号
            noteBean.setContent(contents);
            noteBean.setCustType(mainInfo.getEntCustType());
            noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE111.getCode()));
            noteBean.setPriority(Priority.HIGH.getPriority());//消息级别
            noteBean.setSender(BSConstants.SYS_OPERATOR);
        } catch (Exception e) {
            this.getLogger().error("========拼装扣年费成功的短信信息失败=======", e);
        }
        return noteBean;
    }

}
