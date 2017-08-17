/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.points.handle;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.*;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.common.TransTypeUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;
import com.gy.hsxt.ps.points.bean.BackDetail;
import com.gy.hsxt.ps.points.bean.CancellationDetail;
import com.gy.hsxt.ps.points.bean.CorrectDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.points.handle
 * @ClassName: PointHandle
 * @Description: 订单处理
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */
public class PointHandle {
    /**
     * 积分明细处理
     *
     * @param pointDetail 积分明细对象信息
     * @param point       pos入参积分对象信息
     * @return 返回积分明细对象
     */
    public PointDetail pointDispose(PointDetail pointDetail, Point point) {
        // 生成流水号
        pointDetail.setTransNo(GuidAgent.getStringGuid(Constants.TRANS_NO_POINT10+PsTools.getInstanceNo()));

        // 设置货币转换率
        if (!PsTools.isEmpty(point.getCurrencyRate())) {
            pointDetail.setCurrencyRate(new BigDecimal(point.getCurrencyRate()));
        } else {
            pointDetail.setCurrencyRate(new BigDecimal(Constants.CURRENCY_RATE));
        }
        // 交易状态
        String transStatus = TransTypeUtil.transStatus(point.getTransType());

        // 设置订单状态
        pointDetail.setTransStatus(Integer.parseInt(transStatus));

        // 批次号
        pointDetail
                .setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));

        // 设置交易金额
        pointDetail.setTransAmount(new BigDecimal(point.getTransAmount()));

        // 设置原始币种金额
        pointDetail.setSourceTransAmount(new BigDecimal(point.getSourceTransAmount()));
        if (StringUtils.isNotEmpty(point.getPointRate())) {
            // 设置积分比例
            pointDetail.setPointRate(new BigDecimal(point.getPointRate()));
        }

        if (StringUtils.isNotBlank(point.getOrderAmount())) {
            //设置抵扣券和实际交易金额
            pointDetail.setOrderAmount(new BigDecimal(point.getOrderAmount()));
        }

        // 设置原始交易时间
        pointDetail.setSourceTransDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

        if(StringUtils.isNotEmpty(point.getSourcePosDate())) {
            // 设置单据码生成时间
            pointDetail.setSourcePosDate(DateUtil.StringToDateHMS(point.getSourcePosDate()));
        }
        // 设置业务状态
        pointDetail.setStatus(Constants.SUCCESS);

        // 设置不扣商业服务费
        pointDetail.setIsDeduction(Constants.IS_DEDUCTION1);

        // 设置未结算
        pointDetail.setIsSettle(Constants.IS_SETTLE1);

        // 设置原始币种金额转换后金额
        pointDetail.setTargetTransAmount(new BigDecimal(point.getTransAmount()));

        return pointDetail;
    }

    /**
     * 积分返回处理
     *
     * @param pointResult 积分返回对象信息
     * @param pointDetail 积分明细对象信息
     * @return 返回积分返回对象
     */
    public PointResult pointResultDispose(PointResult pointResult, PointDetail pointDetail) {
        // 设置交易流水号
        pointResult.setTransNo(pointDetail.getTransNo());

        // 设置消费者积分
        pointResult.setPerPoint(pointDetail.getPerPoint().toString());

        // 设置企业积分
        pointResult.setEntPoint(pointDetail.getEntPoint().toString());

        // 设置会计时间
        pointResult.setAccountantDate(DateUtil.DateToString(DateUtil.today(),
                DateUtil.DEFAULT_DATE_TIME_FORMAT));

        return pointResult;
    }

    /**
     * 积分分录数据处理 数据绑定
     *
     * @param list        积分分录信息
     * @param pointDetail 积分明细对象信息
     * @return 返回积分分录信息
     */
    public List<Alloc> pointAccountData(List<Alloc> list, PointDetail pointDetail) {
        for (Alloc alloc : list) {
            String entryNo = GuidAgent.getStringGuid(Constants.TRANS_NO_POINT10+PsTools.getInstanceNo());
            // 设置关联交易流水号
            // alloc.setRelTransNo(pointDetail.getTransNo());

            // 设置交易流水号
            alloc.setTransNo(pointDetail.getTransNo());

            // 设置备注
            alloc.setRemark(pointDetail.getRemark());

            // 设置分录表-序号
            alloc.setEntryNo(entryNo);

            // 设置红冲标识
            alloc.setWriteBack(TransTypeUtil.writeBack(alloc.getTransType()));
        }
        return list;
    }

    /**
     * 积分冲正 数据绑定
     *
     * @param transType 交易类型
     * @param transNo 流水号
     * @return 返回账务冲正实体对象
     */
    public AccountWriteBack writeBackPoint(String transType,String transNo) {

        // 创建冲正对象
        AccountWriteBack accountWriteBack = new AccountWriteBack();

        // 解析交易类型冲正标志
        //int writeBack = Integer.parseInt(TransTypeUtil.writeBack(pointDetail.getTransType()));

        // 设置冲正标志
        accountWriteBack.setWriteBack(Constants.WRITE_BACK_1);

        // 设置积分明细交易类型
        accountWriteBack.setTransType(transType);

        // 设置积分明细交易流水号
        accountWriteBack.setRelTransNo(transNo);

        // 返回
        return accountWriteBack;
    }

    /**
     * 退货明细处理 数据绑定
     *
     * @param backDetail 退货明细对象信息
     * @param oldOrder   积分明细对象信息(原积分单)
     * @return 返回退货明细对象信息
     */
    public BackDetail backDispose(BackDetail backDetail, PointDetail oldOrder,Back back) {
        // 设置订单交易流水号
        backDetail.setTransNo(GuidAgent.getStringGuid(Constants.TRANS_NO_BACK12+PsTools.getInstanceNo()));

        // 设置批次号
        backDetail
                .setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));

        // 设置原交易金额
        backDetail.setOldTransAmount(oldOrder.getTransAmount());

        // 设置原积分预付款
        backDetail.setOldEntPoint(oldOrder.getEntPoint());

        // 设置原消费者的积分
        backDetail.setOldPerPoint(oldOrder.getPerPoint());

        // 设置订单状态
        backDetail.setTransStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0));

        // 设置原交易流水号
        backDetail.setOldTransNo(oldOrder.getTransNo());

        // 设置业务状态
        backDetail.setStatus(Constants.SUCCESS);

        // 设置未结算
        backDetail.setIsSettle(oldOrder.getIsSettle());

        if (StringUtils.isEmpty(backDetail.getEntName())) {
            //设置企业名称
            backDetail.setEntName(oldOrder.getEntName());
        }

        backDetail.setBatchNo(DateUtil.DateToString(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
        backDetail.setSourceTransNo(back.getSourceTransNo());
        backDetail.setSourceTransDate(Timestamp.valueOf(back.getSourceTransDate()));
        return backDetail;
    }

    /**
     * 退货返回处理 数据绑定
     *
     * @param backResult 退货返回对象信息
     * @param backDetail 退货明细对象信息
     * @return 返回退货返回对象信息
     */
    public BackResult backResultDispose(BackResult backResult, BackDetail backDetail) {
        // 积分比例
        backResult.setPointRate(backDetail.getPointRate().toString());

        // 设置原交易币种
        backResult.setCurrency(backDetail.getSourceCurrencyCode());

        // 设置企业互生号
        backResult.setEntNo(backDetail.getEntResNo());

            // 设置积分应付款金额
        backResult.setAssureOutValue(backDetail.getEntPoint().toString());

        // 设置原订单金额
        backResult.setOrderAmount(backDetail.getSourceTransAmount().toString());

        // 设置交易金额
        backResult.setTransAmount(backDetail.getTransAmount().toString());

        // 设置交易流水号
        backResult.setTransNo(backDetail.getTransNo());
        
        //start--modified by liuzh on 2016-05-13  积分为0时,无需设置为0.1
        /*
        if (backDetail.getPerPoint()==null||backDetail.getPerPoint().compareTo(BigDecimal.ZERO) == 0) {
            // 设置积分应付款金额
            backResult.setPerPoint(String.valueOf(Constants.MIN_POINT));
        }
        else {
            // 设置消费者积分
            backResult.setPerPoint(backDetail.getPerPoint().toString());
        }
        */
        // 设置消费者积分
        backResult.setPerPoint(backDetail.getPerPoint().toString());
        //end--modified by liuzh on 2016-05-13 
        
        // 设置企业积分款
        backResult.setEntPoint(backDetail.getEntPoint().toString());

        // 设置会计时间
        backResult.setAccountantDate(DateUtil.DateToString(DateUtil.today(),
                DateUtil.DEFAULT_DATE_TIME_FORMAT));

        return backResult;
    }

    /**
     * 退货分录数据处理
     *
     * @param list       原分录信息
     * @param backDetail 退货明细对象信息
     * @param oldOrder   积分明细对象信息(原积分单)
     * @return 返回退货分录信息
     */
    public List<Alloc> backEntry(List<Alloc> list, BackDetail backDetail, PointDetail oldOrder) {
        for (Alloc alloc : list) {
            // 设置原交易流水号
            alloc.setSourceTransNo(backDetail.getSourceTransNo());

            // 设置实时分录表-关联分录流水号
            alloc.setRelEntryNo(alloc.getEntryNo());

            // 设置交易流水号
            alloc.setTransNo(oldOrder.getTransNo());

            // 设置关联交易流水号
            alloc.setRelTransNo(backDetail.getTransNo());

            // 设置分录表-序号
            alloc.setEntryNo(GuidAgent.getStringGuid(Constants.TRANS_NO_BACK12+PsTools.getInstanceNo()));

            // 设置交易类型
            alloc.setTransType(backDetail.getTransType());

            // 设置红冲标识
            alloc.setWriteBack(TransTypeUtil.writeBack(backDetail.getTransType()));

            // 设置原始交易时间
            alloc.setSourceTransDate(backDetail.getSourceTransDate());

            alloc.setIsSettle(oldOrder.getIsSettle());

            // 设置备注
            alloc.setRemark(backDetail.getRemark());
        }
        return list;
    }



    /**
     * 撤单明细处理 数据绑定
     *
     * @param cancelDetail 撤单明细对象信息
     * @param oldOrder     积分明细对象信息(原积分单)
     * @return 返回撤单明细对象信息
     */
    public CancellationDetail cancelDispose(CancellationDetail cancelDetail, PointDetail oldOrder,Cancel cancel) {
        // 设置交易流水号
        String cancelTransNo =GuidAgent.getStringGuid(Constants.TRANS_NO_CANCEL11+PsTools.getInstanceNo());
        cancelDetail.setTransNo(cancelTransNo);

        // 设置订单类型
        cancelDetail.setTransStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0));

        // 设置原交易流水号
        cancelDetail.setOldTransNo(oldOrder.getTransNo());

        // 设置业务状态
        cancelDetail.setStatus(Constants.SUCCESS);

        // 设置未结算
        cancelDetail.setIsSettle(oldOrder.getIsSettle());

        if(StringUtils.isEmpty(cancelDetail.getEntName())){
            //设置企业名称
            cancelDetail.setEntName(oldOrder.getEntName());
        }
        cancelDetail.setBatchNo(DateUtil.DateToString(new Date(),DateUtil.DEFAULT_DATE_FORMAT));
        cancelDetail.setSourceTransDate(Timestamp.valueOf(cancel.getSourceTransDate()));
        cancelDetail.setSourceTransNo(cancel.getSourceTransNo());
        return cancelDetail;
    }

    /**
     * 撤单返回处理 数据绑定
     *
     * @param cancelResult 撤单返回对象信息
     * @param cancelDetail 撤单明细对象信息
     * @return 返回撤单返回对象信息
     */
    public CancelResult cancelResultDispose(CancelResult cancelResult,
                                            CancellationDetail cancelDetail) {
        // 设置交易流水号
        cancelResult.setTransNo(cancelDetail.getTransNo());

        // 设置消费者积分
        cancelResult.setPerPoint(PsTools.bigDecimal2String(cancelDetail.getPerPoint()));

        // 设置企业应付积分
        cancelResult.setEntPoint(PsTools.bigDecimal2String(cancelDetail.getEntPoint()));

        // 设置企业互生号
        cancelResult.setEntResNo(cancelDetail.getEntResNo());

        // 设置撤单金额
        cancelResult.setTransAmount(PsTools.bigDecimal2String(cancelDetail.getTransAmount()));

        // 设置积分应付款金额
        cancelResult.setAssureOutValue(PsTools.bigDecimal2String(cancelDetail.getEntPoint()));

        // 设置积分
        cancelResult.setPointsValue(PsTools.bigDecimal2String(cancelDetail.getEntPoint()));

        // 积分比例
        cancelResult.setPointRate(PsTools.bigDecimal2String(cancelDetail.getPointRate()));

        // 原始币种
        cancelResult.setSourceCurrencyCode(cancelDetail.getSourceCurrencyCode());

        // 原始币种金额
        cancelResult.setSourceTransAmount(PsTools.bigDecimal2String(cancelDetail.getSourceTransAmount()));

        // 设置会计时间
        cancelResult.setAccountantDate(DateUtil.DateToString(DateUtil.today(),
                DateUtil.DEFAULT_DATE_TIME_FORMAT));

        return cancelResult;
    }

    /**
     * 撤单分录数据处理 数据绑定
     *
     * @param list         原分录信息
     * @param cancelDetail 撤单明细对象信息
     * @param oldOrder     积分明细对象信息(原积分单)
     * @return 返回撤单分录信息
     */
    public List<Alloc> cancelEntry(List<Alloc> list, CancellationDetail cancelDetail,
                                   PointDetail oldOrder) {
        for (Alloc alloc : list) {
            // 设置原交易流水号
            alloc.setSourceTransNo(cancelDetail.getSourceTransNo());

            // 设置实时分录表-关联分录流水号
            alloc.setRelEntryNo(alloc.getEntryNo());

            // 设置交易流水号
            alloc.setTransNo(oldOrder.getTransNo());

            // 设置分录表-序号
            alloc.setEntryNo(Constants.TRANS_NO_CANCEL11+PsTools.getInstanceNo());

            // 设置关联交易流水号
            alloc.setRelTransNo(cancelDetail.getTransNo());

            // 设置交易类型
            alloc.setTransType(cancelDetail.getTransType());

            // 设置红冲标识
            alloc.setWriteBack(TransTypeUtil.writeBack(cancelDetail.getTransType()));

            // 设置原始交易时间
            alloc.setSourceTransDate(cancelDetail.getSourceTransDate());

            //设置结算状态
            alloc.setIsSettle(oldOrder.getIsSettle());

            // 设置备注
            alloc.setRemark(cancelDetail.getRemark());

            alloc.setSourceTransDate(cancelDetail.getSourceTransDate());
        }
        return list;
    }


    /**
     * 关联分录号
     *
     * @param allocList 分路记录，
     * @param entryAllot 新分路记录
     */
    public void relEntryNoHandle(List<Alloc> allocList, EntryAllot entryAllot) {
        for (Alloc alloc : allocList) {
          if (alloc.getCustType() ==CustType.MEMBER_ENT.getCode()
                  ||alloc.getCustType()==CustType.TRUSTEESHIP_ENT.getCode()
                   ||alloc.getCustType()==CustType.PERSON.getCode()) {
            alloc.setEntryNo(entryAllot.getEntryNo());}
        }
    }



    /**
     * 关联分录号
     *
     * @param backDetail 退货记录，
     */
    public void pointMInZero(BackDetail backDetail) {

        if(backDetail!=null) {
        	//start--modified by liuzh on 2016-05-13
        	/*
            if (backDetail.getPerPoint().compareTo(BigDecimal.ZERO)==0){
                backDetail.setPerPoint(BigDecimal.valueOf(Constants.MIN_POINT2));
                backDetail.setEntPoint(BigDecimal.valueOf(Constants.MIN_POINT));
            }
            */
            SystemLog.debug("PointHandle", "pointMInZero:before",JSON.toJSONString(backDetail));  
        	//原订单金额
        	BigDecimal oldTransAmount = backDetail.getOldTransAmount();
        	//退货金额
        	BigDecimal backAmount = backDetail.getTransAmount();
        	//积分金额
        	BigDecimal pointRate = backDetail.getPointRate();
        	//最小积分
        	BigDecimal minPointValue = BigDecimal.valueOf(Constants.MIN_POINT);

        	//@业务需求
        	//互生币支付退货，支持多次退货退款。
        	//1、当退货退款金额*积分比例小于0.1积分时，积分金额按0.1处理，扣除持卡人0.05PV。
        	//2、当累积退货金额等于消费金额时，最后一次退货退款金额*积分比例小于0.1PV时，退款时不退积分。
        	//@业务需求        	
        	
        	//退货退款金额*积分比例小于0.1积分时的处理
        	if(backAmount.multiply(pointRate).compareTo(minPointValue)<0) {
        		if(oldTransAmount.compareTo(BigDecimal.ZERO)==0) {
        			//当累积退货金额等于消费金额时,退款时不退积分
        			backDetail.setPerPoint(BigDecimal.ZERO);
        			backDetail.setEntPoint(BigDecimal.ZERO);
        		}else{
        			//当累积退货金额小于消费金额时,退货退款金额*积分比例小于0.1积分时, 积分金额按0.1处理，扣除持卡人0.05,扣除企业0.1
	                backDetail.setPerPoint(BigDecimal.valueOf(Constants.MIN_POINT2));
	                backDetail.setEntPoint(BigDecimal.valueOf(Constants.MIN_POINT));
        		}
        	}        	
            SystemLog.debug("PointHandle", "pointMInZero:after",JSON.toJSONString(backDetail));            
            //end--modified by liuzh on 2016-05-13
        }

    }
    /**
     * 冲正明细处理 数据绑定
     *
     * @param correctDetail 冲正明细对象信息
     * @param correct       POS入参冲正对象信息
     * @return 返回冲正明细对象信息
     */
    public CorrectDetail correctDetailDispose(CorrectDetail correctDetail, Correct correct) {
        // 冲正流水号
        correctDetail.setReturnNo(GuidAgent.getStringGuid(Constants.TRANS_NO_CORRECT13+PsTools.getInstanceNo()));

        // 设置冲正状态
        correctDetail.setTransStatus(Integer.parseInt(TransTypeUtil.transStatus(correct
                .getTransType())));

        // 设置业务状态
        correctDetail.setStatus(Constants.SUCCESS);

        return correctDetail;
    }

    /**
     * 冲正返回处理 数据绑定
     *
     * @param returnResult 冲正返回对象信息
     * @param oldOrder     积分明细对象信息(原积分单)
     * @return 返回冲正返回对象信息
     */
    public ReturnResult correctResultDispose(ReturnResult returnResult, PointDetail oldOrder) {
        // 设置原交易币种
        returnResult.setSourceCurrencyCode(oldOrder.getSourceCurrencyCode());

        // 设置设备号
        returnResult.setEquipmentNo(oldOrder.getEquipmentNo());

        // 设置企业互生号
        returnResult.setEntResNo(oldOrder.getEntResNo());

        // 设置原订单金额
        returnResult.setSourceTransAmount(PsTools.bigDecimal2String(oldOrder.getSourceTransAmount()));

        // 设置会计时间
        returnResult.setAccountantDate(DateUtil.DateToString(DateUtil.today(),
                DateUtil.DEFAULT_DATE_TIME_FORMAT));

        // 设置个人互生号
        returnResult.setPerResNo(oldOrder.getPerResNo());

        return returnResult;
    }

    /**
     * 冲正分录数据处理 数据绑定
     *
     * @param list          原分录信息
     * @param correctDetail 冲正明细对象信息
     * @param oldOrder      积分明细对象信息(原积分单)
     * @return 返回冲正分录信息
     */
    public List<Alloc> correctEntry(List<Alloc> list, CorrectDetail correctDetail,
                                    PointDetail oldOrder) {
        for (Alloc alloc : list) {
            // 设置实时分录表-关联分录流水号
            alloc.setRelEntryNo(alloc.getEntryNo());

            // 设置关联交易流水号
            alloc.setRelTransNo(correctDetail.getTransNo());

            // 设置交易流水号
            alloc.setTransNo(oldOrder.getTransNo());

            // 设置分录表-序号
            alloc.setEntryNo(GuidAgent.getStringGuid(Constants.TRANS_NO_CORRECT13+PsTools.getInstanceNo()));

            // 设置交易类型
            alloc.setTransType(correctDetail.getTransType());

            // 设置红冲标识
            alloc.setWriteBack(TransTypeUtil.writeBack(correctDetail.getTransType()));

            // 设置原始交易时间
            alloc.setSourceTransDate(correctDetail.getSourceTransDate());
        }
        return list;
    }

}
