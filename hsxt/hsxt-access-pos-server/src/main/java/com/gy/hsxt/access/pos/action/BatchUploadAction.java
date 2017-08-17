/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.model.BatchUploadPosIn;
import com.gy.hsxt.access.pos.model.BatchUploadPosOut;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.PointDetail;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.TradeType;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.ps.bean.BatUpload;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: BatchUploadAction
 * @Description:批上传
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:50:05
 * @version V1.0
 */
@Service("batchUploadAction")
public class BatchUploadAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_BATCH_UPLOAD_ID;

    @Resource(name = "changeRedisUtil")
    RedisUtil<BatUpload> psRedisUtil;

    @Resource(name = "changeRedisUtil")
    RedisUtil<BatchUpload> bsRedisUtil;

    @Autowired
    @Qualifier("entInfoValid")
    private EntInfoValidation entInfoValid;
    
    @Autowired
    @Qualifier("macValid")
    private MacValidation macValid;

    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
    @Autowired
    @Qualifier("aoApiService")
    private AoApiService aoApiService;
    
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    @Override
    public String getReqType() {

        return reqType;
    }

    /**
     * modified by liuzh on 2016-05-24 结算业务变动,暂时去掉了结算. 不批上送数据了
     * @param cmd
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info(this.getClass().getName(), "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        Context ctx1 = new ContextBase();
        ctx1.put("cmd", cmd);
        Command process = new BaseActionChain(entInfoValid);
        process.execute(ctx1);
        
        //start--commented by liuzh on 2016-05-24
        // 业务接口-处理
        /*
        BatchUploadPosOut serviceOut = doPosPointBatchUploadWork(cmd);
        
        if (PosConstant.BATCH_UPLOAD_NETCODE_END.equals(serviceOut.getNetCode())
                || PosConstant.BATCH_UPLOAD_NETCODE_END_TWO.equals(serviceOut.getNetCode())){
        	cmd.getIn().add(new BitMap(48, reqId, serviceOut.getBatchUploadCount(), cmd.getPartVersion()));
        }
        */
        //end--commented by liuzh on 2016-05-24
        TradeType tradeType = reqParam.getTradeTypeInfoBean();
        
        //start--modified by liuzh on 2016-05-24
        //tradeType.setNetCode(serviceOut.getNetCode());
        tradeType.setNetCode("207"); //批上送使用201; 对帐平衡时，批上送结束使用207
        //end--modified by liuzh on 2016-05-24
        
        cmd.getIn().add(new BitMap(60, tradeType));
        
        return cmd;
    }

    /* 原代码逻辑 commented by liuzh 2016-05-24
    @SuppressWarnings("unchecked")
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info(this.getClass().getName(), "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        Context ctx1 = new ContextBase();
        ctx1.put("cmd", cmd);
        Command process = new BaseActionChain(entInfoValid);
        process.execute(ctx1);
        
        // 业务接口-处理
        BatchUploadPosOut serviceOut = doPosPointBatchUploadWork(cmd);
        
        if (PosConstant.BATCH_UPLOAD_NETCODE_END.equals(serviceOut.getNetCode())
                || PosConstant.BATCH_UPLOAD_NETCODE_END_TWO.equals(serviceOut.getNetCode())){
        	cmd.getIn().add(new BitMap(48, reqId, serviceOut.getBatchUploadCount(), cmd.getPartVersion()));
        }
        TradeType tradeType = reqParam.getTradeTypeInfoBean();
        tradeType.setNetCode(serviceOut.getNetCode());
        cmd.getIn().add(new BitMap(60, tradeType));
        
        return cmd;
    }
    */
    
    @SuppressWarnings("unchecked")
    private BatchUploadPosOut doPosPointBatchUploadWork(Cmd cmd) throws Exception {
        PosReqParam reqParam = cmd.getPosReqParam();
        int batchUploadCount = 0;
        BatchUploadPosIn batIn = (BatchUploadPosIn) reqParam.getGyBean();
        int batUpCnt = batIn.getBatchUploadCount();
        TradeType tradeType = reqParam.getTradeTypeInfoBean();
        String entNo = reqParam.getEntNo();
        String posNo = reqParam.getPosNo();
        String batchNo = tradeType.getBatNo();

        String netCode = tradeType.getNetCode();

        List<BatUpload> psCacheList = null;
        List<BatchUpload> bsCacheList = null;
        //拼装缓存key
        StringBuffer buffer = new StringBuffer();
        buffer.append("PS").append(entNo).append(posNo).append(batchNo);
        String psCacheListKey = buffer.toString();
        buffer.delete(0, buffer.length());
        buffer.append("BS").append(entNo).append(posNo).append(batchNo);
        String bsCacheListKey = buffer.toString();
        try{
            psCacheList = (List<BatUpload>) psRedisUtil.getList(psCacheListKey, BatUpload.class);
            bsCacheList = (List<BatchUpload>) bsRedisUtil.getList(bsCacheListKey, BatchUpload.class);
            // 批上送：201 表示后续还有内容
            if (netCode.equals(PosConstant.BATCH_UPLOAD_NETCODE)){
            	List<PointDetail> list = batIn.getBatchUploadDetails();
                SystemLog.debug("Pos Server ","BatchUploadAction","本批明细数量:" + list.size() + ",batUpCnt:" + batUpCnt);
                psCacheList = psCacheList == null ? new LinkedList<BatUpload>() : psCacheList;
                bsCacheList = bsCacheList == null ? new LinkedList<BatchUpload>() : bsCacheList;
                
                for (PointDetail pd : list){
                    if( pd.getTermTypeCode().equalsIgnoreCase("0070") || pd.getTermTypeCode().equalsIgnoreCase("0071")){
                        //代兑互生币和兑换互生币的业务
                        BatchUpload bu = new BatchUpload();
                        bu.setBizType(pd.getTermTypeCode());
                        bu.setOriginNo(pd.getOriginNo());
                        bu.setTermRuncode(pd.getTermRunCode());
                        bu.setTransAmt(pd.getOrderAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        bsCacheList.add(bu);
                    }else{
                        //其他的业务，都属于消费积分相关
                        BatUpload bu = new BatUpload();
                        bu.setBatchNo(batchNo);
                        bu.setEntPoint(pd.getAssureOutValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        bu.setEntResNo(entNo);
                        bu.setEquipmentNo(posNo);
                        bu.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
                        bu.setPerPoint(pd.getPointsValue().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        bu.setPerResNo(pd.getCardNo());
                        bu.setPointRate(pd.getPointRate().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
                        bu.setSourceTransNo(cmd.getPointId());
                        bu.setTransAmount(pd.getOrderAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        bu.setTransNo(pd.getOriginNo());
                        psCacheList.add(bu);
                    }
                    
                }
                //本批次总数
                batchUploadCount = list.size();
                if( psCacheList!=null && !psCacheList.isEmpty() ){
                    psRedisUtil.remove(psCacheListKey);
                    //本批次积分系统的缓存内容
                    psRedisUtil.addList(psCacheListKey, psCacheList, BatUpload.class);
                }
                
                if( bsCacheList!=null && !bsCacheList.isEmpty() ){
                    bsRedisUtil.remove(bsCacheListKey);
                    //本批次账户操作系统的缓存内容
                    bsRedisUtil.addList(bsCacheListKey, bsCacheList, BatchUpload.class);
                }
            }
        }
        catch (Exception e)
        {
            psRedisUtil.remove(psCacheListKey);
            bsRedisUtil.remove(bsCacheListKey);
            throw e;
        }

        // 批上送结束： 202 表明是该批次的最后一个上传包，只有结束标记。
        if (netCode.equals(PosConstant.BATCH_UPLOAD_NETCODE_END)
                || netCode.equals(PosConstant.BATCH_UPLOAD_NETCODE_END_TWO)){

            if (psCacheList == null && bsCacheList == null)
            	SystemLog.debug("Pos Server","BatchUploadAction","缓存没数据");
            else{
                try{
                    String entCustId = ucApiService.findEntCustIdByEntResNo(entNo);
                    //从缓存中获取积分系统的数据
                    psCacheList = (List<BatUpload>) psRedisUtil.getList(psCacheListKey, BatUpload.class);
                    //从缓存中获取账户系统的数据
                    bsCacheList = (List<BatchUpload>) bsRedisUtil.getList(bsCacheListKey, BatchUpload.class);
                    
                    com.gy.hsxt.ao.bean.BatchSettle aoBatSettle = new com.gy.hsxt.ao.bean.BatchSettle();
                    aoBatSettle.setEntCustId(entCustId);
                    aoBatSettle.setEntResNo(entNo);
                    aoBatSettle.setTermNo(reqParam.getPosNo());
                    aoBatSettle.setChannel(Channel.POS.getCode());
                    aoBatSettle.setBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
                    //积分的批上送
                    if(psCacheList!=null && !psCacheList.isEmpty()){
                        psApiService.batUpload(psCacheList);
                    }
                    //账户操作系统批上送
                    if(bsCacheList!=null && !bsCacheList.isEmpty()){
                        aoApiService.batchTerminalDataUpload(aoBatSettle, bsCacheList);
                    }

                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    psRedisUtil.remove(psCacheListKey);
                    bsRedisUtil.remove(bsCacheListKey);
                }
            }
            
            batchUploadCount =  psCacheList.size() + bsCacheList.size();
        }
        return new BatchUploadPosOut(batchUploadCount, netCode);
    }
}
