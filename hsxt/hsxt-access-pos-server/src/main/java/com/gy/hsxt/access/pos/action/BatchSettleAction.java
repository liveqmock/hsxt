/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.model.BatchSettle;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: BatchSettleAction
 * @Description: pos 批结算
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:49:51
 * @version V1.0
 */

@Service("batchSettleAction")
public class BatchSettleAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_BATCH_SETTLE_ID;

    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("aoApiService")
    private AoApiService aoApiService;
    
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    @Autowired
    @Qualifier("entInfoValid")
    private EntInfoValidation entInfoValid;
    
    @Autowired
    @Qualifier("macValid")
    private MacValidation macValid;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;

    @Override
    public String getReqType() {

        return reqType;
    }

    /**
     * modified by liuzh on 2016-05-24
     */
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("BatchSettleAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));


        
        commonValidation.reqParamValid(cmd);
        
        // 企业与客户信息
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
        String entNo = reqParam.getEntNo();
        String entCustId = entStatusInfo.getEntCustId();
        final List<BitMap> list = cmd.getIn();

        //组装积分系统的批结算数据
        BatchSettle batchSettle = (BatchSettle) reqParam.getGyBean();
        BatSettle batSettle = new BatSettle();
        batSettle.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        batSettle.setEntResNo(reqParam.getEntNo());
        batSettle.setEquipmentNo(reqParam.getPosNo());
        batSettle.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        batSettle.setHsbCnt(batchSettle.getInHsbPayCount());
        batSettle.setHsbSum(String.valueOf(batchSettle.getInHsbPaySum()));
        batSettle.setInHsbCancelCount(batchSettle.getInHsbCancelCount());
        batSettle.setInHsbCancelSum(String.valueOf(batchSettle.getInHsbCancelSum()));
        batSettle.setHsbBackCnt(batchSettle.getInHsbReturnCount());
        batSettle.setHsbBackSum(String.valueOf(batchSettle.getInHsbReturnSum()));
        batSettle.setPointCancelCnt(batchSettle.getInPointCancelCount());
        batSettle.setPointCancelSum(String.valueOf(batchSettle.getInPointCancelSum()));    
        batSettle.setPointCnt(batchSettle.getInPointCount());
        batSettle.setPointSum(String.valueOf(batchSettle.getInPointSum()));
        
        batSettle.setPointBackCnt(Integer.valueOf(0));
        batSettle.setPointBackSum("0.00");
        
        
        //start--modified by liuzh on 2016-05-24 
        /* 改动原因:
         * 结算业务变动. pos3.0的结算,pos机界面已经去掉. pos2.0不送数据到ps比对,直接把接收到的pos机数据返回给pos机
         */
        
        /*
        //组装账户操作系统的批结算数据
        com.gy.hsxt.ao.bean.BatchSettle aoBatSettle = new com.gy.hsxt.ao.bean.BatchSettle();
        aoBatSettle.setEntCustId(entCustId);
        aoBatSettle.setEntResNo(entNo);
        aoBatSettle.setTermNo(reqParam.getPosNo());
        aoBatSettle.setChannel(Channel.POS.getCode());
        aoBatSettle.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        
        String buyHsbAmt = batchSettle.getInHsbBReChargeSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String hsbCount = String.valueOf(batchSettle.getInHsbBReChargeCount());
        String proxyBuyHsbAmt = batchSettle.getInHsbCReChargeSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String proxyHsbCount = String.valueOf(batchSettle.getInHsbCReChargeCount());
        Boolean aoFlag = false;
        
        
        try
        {
            SystemLog.debug("BatchSettleAction", "action()", "POS 账户操作系统批结算 请求参数："+ 
            			JSON.toJSONString(aoBatSettle)+"  buyHsbAmt="+buyHsbAmt+"  hsbCount=" + 
            			hsbCount+"  proxyBuyHsbAmt="+proxyBuyHsbAmt+"  proxyHsbCount="+proxyHsbCount);
            aoFlag = aoApiService.batchCheckResult(aoBatSettle, buyHsbAmt, hsbCount, 
            												proxyBuyHsbAmt, proxyHsbCount);
        }catch(Exception e){
            SystemLog.error("BatchSettleAction", "action()", "POS 账户操作系统批结算调用失败。"+JSON.toJSONString(aoBatSettle)+"  buyHsbAmt="+buyHsbAmt+"  hsbCount="+hsbCount+"  proxyBuyHsbAmt="+proxyBuyHsbAmt+"  proxyHsbCount="+proxyHsbCount,e);
        }
        
        try{
            SystemLog.debug("BatchSettleAction", "action()", "POS 积分系统批结算 请求参数：" + JSON.toJSONString(batSettle));
            psApiService.batSettle(batSettle);
            if(aoFlag){
                batchSettle.setSettleResult(PosConstant.BAT_SETTLE_SUCCESS);
                batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_SUCCESS);
            }else{
                batchSettle.setSettleResult(PosConstant.BAT_SETTLE_FAILE);
                batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_FAILE);
            }

        }
        catch (Exception e)
        {
            SystemLog.error("BatchSettleAction", "action()", "POS 积分系统批结算调用失败" + JSON.toJSONString(batSettle),e);
            batchSettle.setSettleResult(PosConstant.BAT_SETTLE_FAILE);
            batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_FAILE);
            throw e;
        }
        finally
        {
            list.add(new BitMap(48, reqId, batchSettle, cmd.getPartVersion()));
        }
        */
        
        //设置对账平成功标志, 原样返回batchSettle给pos机
        batchSettle.setSettleResult(PosConstant.BAT_SETTLE_SUCCESS);
        batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_SUCCESS);
        list.add(new BitMap(48, reqId, batchSettle, cmd.getPartVersion()));
        //end--modified by liuzh on 2016-05-24
        
        //暂写死
        list.add(new BitMap(15, "0000"));
        // 国际信用卡公司代码 M/C
        list.add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        
        
        return cmd;
    }

    
    /* 原代码逻辑 commented by liuzh 2016-05-24
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("BatchSettleAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));


        
        commonValidation.reqParamValid(cmd);
        
        // 企业与客户信息
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
        String entNo = reqParam.getEntNo();
        String entCustId = entStatusInfo.getEntCustId();
        final List<BitMap> list = cmd.getIn();

        //组装积分系统的批结算数据
        BatchSettle batchSettle = (BatchSettle) reqParam.getGyBean();
        BatSettle batSettle = new BatSettle();
        batSettle.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        batSettle.setEntResNo(reqParam.getEntNo());
        batSettle.setEquipmentNo(reqParam.getPosNo());
        batSettle.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        batSettle.setHsbCnt(batchSettle.getInHsbPayCount());
        batSettle.setHsbSum(String.valueOf(batchSettle.getInHsbPaySum()));
        batSettle.setInHsbCancelCount(batchSettle.getInHsbCancelCount());
        batSettle.setInHsbCancelSum(String.valueOf(batchSettle.getInHsbCancelSum()));
        batSettle.setHsbBackCnt(batchSettle.getInHsbReturnCount());
        batSettle.setHsbBackSum(String.valueOf(batchSettle.getInHsbReturnSum()));
        batSettle.setPointCancelCnt(batchSettle.getInPointCancelCount());
        batSettle.setPointCancelSum(String.valueOf(batchSettle.getInPointCancelSum()));    
        batSettle.setPointCnt(batchSettle.getInPointCount());
        batSettle.setPointSum(String.valueOf(batchSettle.getInPointSum()));
        
        batSettle.setPointBackCnt(Integer.valueOf(0));
        batSettle.setPointBackSum("0.00");
        
        
        
        //组装账户操作系统的批结算数据
        com.gy.hsxt.ao.bean.BatchSettle aoBatSettle = new com.gy.hsxt.ao.bean.BatchSettle();
        aoBatSettle.setEntCustId(entCustId);
        aoBatSettle.setEntResNo(entNo);
        aoBatSettle.setTermNo(reqParam.getPosNo());
        aoBatSettle.setChannel(Channel.POS.getCode());
        aoBatSettle.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        
        String buyHsbAmt = batchSettle.getInHsbBReChargeSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String hsbCount = String.valueOf(batchSettle.getInHsbBReChargeCount());
        String proxyBuyHsbAmt = batchSettle.getInHsbCReChargeSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String proxyHsbCount = String.valueOf(batchSettle.getInHsbCReChargeCount());
        Boolean aoFlag = false;
        
        
        try
        {
            SystemLog.debug("BatchSettleAction", "action()", "POS 账户操作系统批结算 请求参数："+ 
            			JSON.toJSONString(aoBatSettle)+"  buyHsbAmt="+buyHsbAmt+"  hsbCount=" + 
            			hsbCount+"  proxyBuyHsbAmt="+proxyBuyHsbAmt+"  proxyHsbCount="+proxyHsbCount);
            aoFlag = aoApiService.batchCheckResult(aoBatSettle, buyHsbAmt, hsbCount, 
            												proxyBuyHsbAmt, proxyHsbCount);
        }catch(Exception e){
            SystemLog.error("BatchSettleAction", "action()", "POS 账户操作系统批结算调用失败。"+JSON.toJSONString(aoBatSettle)+"  buyHsbAmt="+buyHsbAmt+"  hsbCount="+hsbCount+"  proxyBuyHsbAmt="+proxyBuyHsbAmt+"  proxyHsbCount="+proxyHsbCount,e);
        }
        
        try{
            SystemLog.debug("BatchSettleAction", "action()", "POS 积分系统批结算 请求参数：" + JSON.toJSONString(batSettle));
            psApiService.batSettle(batSettle);
            if(aoFlag){
                batchSettle.setSettleResult(PosConstant.BAT_SETTLE_SUCCESS);
                batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_SUCCESS);
            }else{
                batchSettle.setSettleResult(PosConstant.BAT_SETTLE_FAILE);
                batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_FAILE);
            }

        }
        catch (Exception e)
        {
            SystemLog.error("BatchSettleAction", "action()", "POS 积分系统批结算调用失败" + JSON.toJSONString(batSettle),e);
            batchSettle.setSettleResult(PosConstant.BAT_SETTLE_FAILE);
            batchSettle.setSettleResult_wild(PosConstant.BAT_SETTLE_FAILE);
            throw e;
        }
        finally
        {
            list.add(new BitMap(48, reqId, batchSettle, cmd.getPartVersion()));
        }
        
        //暂写死
        list.add(new BitMap(15, "0000"));
        // 国际信用卡公司代码 M/C
        list.add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        
        
        return cmd;
    }
	*/
}
