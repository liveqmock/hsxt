/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsec.external.bean.QueryParam;
import com.gy.hsec.external.bean.QueryResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.EtApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsOrderReverseAction
 * @Description:互商订单冲正
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:55:28
 * @version V1.0
 */
@Service("hsOrderReverseAction")
public class HsOrderReverseAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_HSORDER_HSB_PAY_REVERSE_ID;

    @Autowired
    @Qualifier("etApiService")
    private EtApiService etApiService;
    
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
        SystemLog.info("HsOrderReverseAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        //1 发起冲正业务参数校验(冲正交易只有mac、签到、企业信息的校验)
        commonValidation.reqParamValid(cmd);

        //2 组装互商订单冲正参数
        QueryParam qp = new QueryParam();
        String tradeType = null;
        if (reqId.equalsIgnoreCase(PosConstant.REQ_HSORDER_CASH_PAY_REVERSE_ID))
        	// 现金支付冲正
            tradeType = "31";
        else if (reqId.equalsIgnoreCase(PosConstant.REQ_HSORDER_HSB_PAY_REVERSE_ID))
            // 互生币支付冲正
            tradeType = "32";
        else
        	// 进入在这里表示请求非法
            throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, 
            			"互商业务冲正请求码非法。reqId：" + reqId);
       
        qp.setTradeType(tradeType);
        qp.setEntNo(reqParam.getEntNo());
        qp.setPosNo(reqParam.getPosNo());
        qp.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        qp.setPosBizSeq(reqParam.getPosRunCode());
        qp.setOperNo(reqParam.getOperNo());
        
        QueryResult qr = null;
        try{
        	qr = etApiService.correctOrder(qp);
        }catch(HsException he){
        	//ServerHandler会映射为pos异常处理
        	SystemLog.error("HsOrderReverseAction", "action()", "互商业务冲正失败，电商抛出互生异常。", he);
        	throw he;
        }catch(Exception e){
        	//非互生异常则需发起再次冲正，pos机连续三次失败后会终止。
        	CommonUtil.checkState(true, "互商业务冲正失败，电商抛出非互生异常。", PosRespCode.AGAIN_REVERSE, e);
        }
        
        CommonUtil.checkState(null == qr, "互商业务冲正失败，电商返回冲正结果为空，需再次冲正。", PosRespCode.AGAIN_REVERSE);
        SystemLog.info("HsOrderReverseAction", "action()", 
				"电商返回码：" + qr.getRetCode() + " ； 返回信息：" + qr.getRetMsg());
        
        //明确冲正完成或提示未找到原订单，冲正目的已达到。（未找到原订单，也可能是电商逻辑错误。）
        if(200 == qr.getRetCode() || 780 == qr.getRetCode()) cmd.setRespCode(PosRespCode.SUCCESS);
        else cmd.setRespCode(PosRespCode.REPEAT_REVERSE);
        	
    	//9 组装返回报文各域
        //2域 消费者卡号  手输、刷卡有，扫码无
        
        //二维码扫码要传入卡号 commented on 2016-04-21 by liuzh
        //start--modified by liuzh on 2016-04-21
        //String custCardNo = "12345678999";//kend test
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
    	SystemLog.debug("HsOrderReverseAction", "action()", "custCardNo:" + custCardNo);
        //end--modified by liuzh on 2016-04-21
        
        boolean exist2area = false;
        for (int i = 0; i < cmd.getIn().size(); i++) {
			BitMap BitMap = cmd.getIn().get(i);
			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));//电商要返回消费者卡号
				exist2area = true;
			}
		}
        if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
        
        //63域 处理完成，返回归一标记
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        
        return cmd;
    }

}
