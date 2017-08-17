/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
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
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseCheck;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.PointResult;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: PointCancelAction
 * @Description: 消费积分撤单
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:37:51
 * @version V1.0
 */
@Service("pointCancelAction")
public class PointCancelAction implements IBasePos {
    public static final String reqType = PosConstant.REQ_POINT_CANCEL_ID;

    @Autowired
    @Qualifier("baseCheck")
    private BaseCheck baseCheck;

    @Autowired
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;

    @Override
    public String getReqType() {

        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("PointCancelAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        
        
        commonValidation.reqParamValid(cmd);
        
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_POINT_CANCEL.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_POINT_CANCEL.getCode();

        SystemLog.debug("PointCancelAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
        
        
        
    	
    	//4 组织交易参数
        Cancel cancel = new Cancel();
        //本交易POS中心流水号
        cancel.setSourceTransNo(cmd.getPointId());
        //原交易POS中心流水号
        cancel.setOldSourceTransNo(reqParam.getOriginalNo());
        // 交易时间
        cancel.setSourceTransDate(DateUtil.getCurrentDateTime());
        // 设备编号（POS编号？） 非必输
        cancel.setEquipmentNo(reqParam.getPosNo());
        // 企业互生号
        cancel.setEntResNo(reqParam.getEntNo());
        // 操作员（63操作员编号） 非必输
        cancel.setOperNo(reqParam.getOperNo());
        // 原业务批次号
        cancel.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        // POS机终端流水号
        cancel.setTermRunCode(reqParam.getPosRunCode());
		//start--added by liuzh on 2016-04-26
		cancel.setTermTradeCode(reqParam.getTermTradeCode());
		cancel.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
		//end--added by liuzh on 2016-04-26
		//start--added by liuzh on 2016-05-06
		cancel.setPerResNo(custCardNo);
		//end--added by liuzh on 2016-05-06
        CancelResult result;
        
        //5 若是异地卡，则发起异地平台处理 第一步
        if(TransType.REMOTE_CARD_LOCAL_POINT_CANCEL.getCode().equals(transType)){
        	cancel.setTransType(TransType.LOCAL_CARD_REMOTE_POINT_CANCEL.getCode());
        	result = (CancelResult) commonService.sendCrossPlatformIndicate(cancel,custCardNo,"cancelPoint");
        	CommonUtil.checkState("异地交易失败".equals("result.getRetCode()"), "发卡平台ps执行异常 result.getRetMsg()：" + 
            		"result.getRetMsg()", PosRespCode.POS_CENTER_FAIL);
        }
    	
    	
    	//6 执行本地平台处理 第二步
        //无论本地异地，都要实现,transType保存有交易平台对应的正确业务类型
        cancel.setTransType(transType);
        try{
        	result = psApiService.cancelpoint(cancel);
        }catch (Exception e){
            if (!e.getClass().equals(HsException.class)) {
                cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            }
            SystemLog.error("PointCancelAction", "action()","积分撤单失败，ps抛出异常",e);
            throw e;
        }
    	
        
    	//7 组装返回报文各域
        //15域 清算日期 目前未用上 暂写死
        cmd.getIn().add(new BitMap(15, "0000"));
    	
        //企业互生号必须有值
        if(null == result.getEntResNo()){
            Exception e = new NullPointerException();
            SystemLog.error("PointCancelAction", "action()", "积分撤单，企业互生号为空", e);
            cmd.setRespCode(PosRespCode.AGAIN_REVERSE);
            throw e;
        }
        //42域 受卡方标识码（企业互生号）同原交易数据
        BitMap bitMap = null;
        
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
        
        for (int i = 0; i < cmd.getIn().size(); i++){
            bitMap = (BitMap) cmd.getIn().get(i);
            if(bitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
            if (bitMap.getBit() == 42){
                cmd.getIn().set(i, new BitMap(42, result.getEntResNo()));
            }
        }
        
        if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
        
    	//4域 交易金额
        String soureceTAStr = result.getSourceTransAmount();
		BigDecimal sourceTransAmout = null == soureceTAStr ? BigDecimal.ZERO : new BigDecimal(soureceTAStr);
		cmd.getIn().add(new BitMap(4, reqId, sourceTransAmout, 
						platformParamService.getLocalCurrencyCode().equals(result.getSourceCurrencyCode())?
								PosConstant.ISLOCAL_CURRENCY:0, cmd.getPartVersion()));
		
    	//48域 存储积分比例 积分 原始交易金额
        cmd.getIn().add(
                new BitMap(48, reqId, new Poi48(new BigDecimal(result.getPointRate()), 
                		new BigDecimal(result.getAssureOutValue()), 
                		new BigDecimal(result.getTransAmount())), 
                		cmd.getPartVersion()));
        //63域 gyt标记
        cmd.getIn().add(
                new BitMap(63, cmd.getReqId(), new Poi63(PosConstant.ICC_CODE_GYT, 
                		new BigDecimal(result.getPerPoint())), cmd.getPartVersion()));

        
        //冲正测试 kend test
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);

        return cmd;
    }

}
