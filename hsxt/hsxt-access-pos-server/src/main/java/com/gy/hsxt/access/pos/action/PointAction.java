/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.Poi63;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.VersionAdapter;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: PointAction
 * @Description:消费积分
 * 
 * @author: wucl
 * @date: 2015-9-23 上午11:29:20
 * @version V1.0
 */
@Service("pointAction")
public class PointAction implements IBasePos {
	//请求类型，在《（POS）终端应用规范 消息域-消息交换说明》中定义
    public static final String reqType = PosConstant.REQ_POINT_ID;

    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;
    
    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;

    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    @Autowired
    @Qualifier("baseDataService")
    private ILCSBaseDataService baseDataService;

    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    

    @Override
    public String getReqType() {

        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("PointAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
      
        commonValidation.reqParamValid(cmd);
        
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_POINT.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_POINT.getCode();

        SystemLog.debug("PointAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
        
        
        //4 提取交易参数，做基础校验
        final Poi48 poi = (Poi48) reqParam.getGyBean();
        
        BigDecimal rate = poi.getRate();
        BigDecimal amt = poi.getAmt();
        //按照协议定义，对于积分，本币交易，pos机不上传交易金额的互生币金额。

        // 积分比率上下限阀值 
        //若为 免费成员企业，最小积分比例有特定要求。
        int startResType = (int) cmd.getTmpMap().get("startResType");
        BigDecimal poiRateMax = platformParamService.getPointRatMax();
        BigDecimal poiRateMin = null;
        //5为 免费成员企业
        if(startResType == 5) poiRateMin = platformParamService.getFreeEntPointRatMin();
        	else poiRateMin = platformParamService.getPointRatMin();
        CommonUtil.checkState(rate.compareTo(poiRateMin) < 0 || rate.compareTo(poiRateMax) > 0, 
        		startResType == 5?"免费成员企业":"非免费成员企业" +
        		" rate:" + rate + ",poiRateMin:" + poiRateMin
                + ",poiRateMax:" + poiRateMax, PosRespCode.OUT_POINTRATE_SCOPE);
        
        //交易金额阀值校验
        CommonUtil.checkState(reqParam.getTransAmount().compareTo(new BigDecimal(PosConstant.MIN_CURRENCY_AMT)) < 0, "交易金额不能小于"
                + PosConstant.MIN_CURRENCY_AMT, PosRespCode.REQUEST_PACK_FORMAT);
        
        
      //以交易金额（4域）计算消费者应得积分，用于校验pos机计算的积分的正确性。
        BigDecimal transAmount = reqParam.getTransAmount();
        BigDecimal checkAmt = transAmount.multiply(rate).setScale(2,RoundingMode.UP);
        
        //start--added by liuzh on 2016-05-23
        SystemLog.debug("PointAction", "action()", "请求消费金额:" + reqParam.getTransAmount()
        		+ ",请求积分比例:" + rate + ",请求积分额:" + amt + ",server计算积分额:" + checkAmt);        
        //end--added by liuzh on 2016-05-23
        
        //pos机计算的积分需要和pos server计算的积分结果一致
        CommonUtil.checkState(!amt.equals(checkAmt), "请求消费金额:" + reqParam.getTransAmount() + 
        			",请求积分比例:" + rate + ",请求积分额:" + amt + "与server计算积分额:" + checkAmt + "不一致", 
        			PosRespCode.CHECK_ASSUREOUTVALUE);
        CommonUtil.checkState(reqParam.getTransAmount().doubleValue() < PosConstant.MIN_CURRENCY_AMT, 
        			"交易金额不能小于" + PosConstant.MIN_CURRENCY_AMT, PosRespCode.HSB_PAY_LESS_ERROR);
        
        
        //5 组装积分参数
        // 企业与客户信息
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
        
        Point point = new Point();
        // 企业互生号(42企业编号) 必输
        point.setEntResNo(reqParam.getEntNo());
        // 消费者互生号(卡号) 非必输
        point.setPerResNo(custCardNo);
        // 设备编号（POS编号？） 非必输
        point.setEquipmentNo(reqParam.getPosNo());
        // 渠道类型 必输
        point.setChannelType(Channel.POS.getCode());
        // 设备类型 必输
        point.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        // 原始交易号(11 终端流水号？) 必输
        point.setSourceTransNo(cmd.getPointId());
        // 原始交易时间 必输
        point.setSourceTransDate(DateUtil.getCurrentDateTime());
        
        //start--added by liuzh on 2016-05-25 二维码支付要送二维码的时间 sourcePosDate
        if(!StringUtils.isEmpty(reqParam.getTradeDate()) 
        		&&!StringUtils.isEmpty(reqParam.getTradeTime())
        		&& reqParam.getTradeTimestamp()!=null)
        {
        	point.setSourcePosDate(reqParam.getTradeTimestamp().toString());
        }
        //end--added by liuzh on 2016-05-25
        
        // 原始交易币种代号（49货币代号） 必输
        point.setSourceCurrencyCode(reqParam.getCurrencyCode());
        // 原始币种金额 必输
        point.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
        // 交易金额（4 交易金额）pos机未返回，这里不做换算，ps去掉非空约束。
        //point.setTransAmount(String.format("%1$.2f", null));
        // 积分比例 必输
        point.setPointRate(String.valueOf(rate));
        point.setPointSum(String.format("%1$.2f", amt));
        // 企业客户号（UC查询） 必输
        point.setEntCustId(entStatusInfo.getEntCustId());
        // 企业名称（UC查询） 必输
        point.setEntName(entStatusInfo.getEntCustName());
        // 消费者客户号（UC查询） 非必输
        point.setPerCustId( (String)cmd.getTmpMap().get("custId"));
        // 操作员（63操作员编号） 非必输
        point.setOperNo(reqParam.getOperNo());
        // 批次号
        point.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        point.setTermRunCode(reqParam.getPosRunCode());
        point.setTermTradeCode(reqParam.getTermTradeCode());
        point.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
        point.setTransType(transType);
        
        //start--added by liuzh on 2016-06-23
        VersionAdapter versionAdapter = new VersionAdapter(cmd.getPartVersion());
        versionAdapter.build(cmd,point, poi);
        //end--added by liuzh on 2016-06-23
        
        //6 发起本地积分交易  第一步
        PointResult result = null;
        try{
        	result = psApiService.point(point);
        }
        catch (Exception e){
            if ( !e.getClass().equals(HsException.class)){
                cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            }
            SystemLog.error("POS SERVER ","PointAction","消费积分接口异常",e);
            throw e;
        }
        CommonUtil.checkState("本地卡交易失败".equals("result.getRetCode()"), "ps执行异常 result.getRetMsg()：" + 
        		"result.getRetMsg()", PosRespCode.POS_CENTER_FAIL);
        CommonUtil.checkState("异地卡交易失败".equals("result.getRetCode()"), "ps执行异常 result.getRetMsg()：" + 
        		"result.getRetMsg()", PosRespCode.POS_CENTER_FAIL);
        
        //7 若为异地卡则发起异地交易 第二步
        if(TransType.REMOTE_CARD_LOCAL_POINT.getCode().equals(transType) &&  
        			"异地卡交易处理完成".equals("result.getRetCode()")){
        	point.setTransType(TransType.LOCAL_CARD_REMOTE_POINT.getCode());
        	result = (PointResult) commonService.sendCrossPlatformIndicate(point,custCardNo,"point");
        	CommonUtil.checkState("异地交易失败".equals("result.getRetCode()"), "发卡平台ps执行异常 result.getRetMsg()：" + 
            		"result.getRetMsg()", PosRespCode.POS_CENTER_FAIL);
        }
        

        //8 组装返回报文各域
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
        for (int i = 0; i < cmd.getIn().size(); i++) {
			BitMap BitMap = cmd.getIn().get(i);
			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
		}
        if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
        
        
        //63域 国际信用卡公司代码+积分0.12 M/C
        cmd.getIn()
                .add(new BitMap(63, reqId, new Poi63(PosConstant.ICC_CODE_GYT, 
                		new BigDecimal(result.getPerPoint())), cmd.getPartVersion()));
        cmd.setRespCode(PosRespCode.SUCCESS);
        
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);// kend test 触发冲正
        
        return cmd;
    }

}
