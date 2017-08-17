/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.access.pos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.AoApiService;
import com.gy.hsxt.ao.api.IAOBatchCheckResultService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.BatchSettle;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 
 * @Package: com.gy.hsxt.access.pos.service.impl  
 * @ClassName: AoApiServiceImpl 
 * @Description: 
 *
 * @author: weiyq 
 * @date: 2015-12-11 上午11:12:54 
 * @version V1.0 
 

 */

@Service("aoApiService")
public class AoApiServiceImpl implements AoApiService {
    
    @Autowired
    @Qualifier("aoExchangeHsbService")
    private IAOExchangeHsbService aoExchangeHsbService;
    
    @Autowired
    @Qualifier("aoBatchCheckResultService")
    private IAOBatchCheckResultService aoBatchCheckResultService;
    
    
    @Override
    public String savePosBuyHsb(BuyHsb buyHsb) throws HsException {
        SystemLog.debug("AoApiServiceImpl", "savePosBuyHsb()",  "请求参数:" +  JSON.toJSONString(buyHsb));
        String ret = aoExchangeHsbService.savePosBuyHsb(buyHsb);
        SystemLog.debug("AoApiServiceImpl", "savePosBuyHsb()", "返回结果:" +  ret);
        return ret;
    }
    
    @Override
    public String savePosProxyBuyHsb(ProxyBuyHsb proxyBuyHsb) throws HsException {
        SystemLog.debug("AoApiServiceImpl", "savePosProxyBuyHsb()",  "请求参数:" +  JSON.toJSONString(proxyBuyHsb));
        String ret = aoExchangeHsbService.savePosProxyBuyHsb(proxyBuyHsb);
        SystemLog.debug("AoApiServiceImpl", "savePosProxyBuyHsb()", "返回结果:" +  ret);
        return ret;
    }
    @Override
    public void reversePosBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException {
        SystemLog.debug("AoApiServiceImpl", "reversePosBuyHsb()",  "请求参数:" +  JSON.toJSONString(posBuyHsbCancel));
        aoExchangeHsbService.reversePosBuyHsb(posBuyHsbCancel);
    }
    @Override
    public void reversePosProxyBuyHsb (PosBuyHsbCancel posBuyHsbCancel) throws HsException {
        SystemLog.debug("AoApiServiceImpl", "reversePosProxyBuyHsb()",  "请求参数:" +  JSON.toJSONString(posBuyHsbCancel));
        aoExchangeHsbService.reversePosProxyBuyHsb(posBuyHsbCancel);
    }
    @Override
    public Boolean batchCheckResult(BatchSettle batchSettle, String buyHsbAmt, String hsbCount, String proxyBuyHsbAmt,String proxyHsbCount){
        SystemLog.debug("AoApiServiceImpl", "batchCheckResult()",  "请求参数:" +  JSON.toJSONString(batchSettle)+" buyHsbAmt="+buyHsbAmt+" hsbCount="+hsbCount+" proxyBuyHsbAmt="+proxyBuyHsbAmt+" proxyHsbCount="+proxyHsbCount);
        Boolean ret = aoBatchCheckResultService.batchCheckResult(batchSettle, buyHsbAmt, hsbCount, proxyBuyHsbAmt,proxyHsbCount);
        SystemLog.debug("AoApiServiceImpl", "batchCheckResult()", "返回结果:" +  String.valueOf(ret));
        return ret;
    }
    
    @Override
    public void batchTerminalDataUpload(BatchSettle batchSettle,List<BatchUpload> listBatchUpload){
        SystemLog.debug("AoApiServiceImpl", "POS aoBatchCheckResultService.batchTerminalDataUpload()",  "请求参数:" +  JSON.toJSONString(batchSettle)+JSON.toJSONString(listBatchUpload));
        aoBatchCheckResultService.batchTerminalDataUpload(batchSettle,listBatchUpload);
    }
    
    @Override
    public BuyHsb getPosBuyHsbInfo(String hsResNo, String originNo){
        SystemLog.debug("AoApiServiceImpl", "getPosBuyHsbInfo()",  "请求参数:" +  " hsResNo="+hsResNo+" originNo="+originNo);
        BuyHsb result = aoExchangeHsbService.getPosBuyHsbInfo(hsResNo, originNo);
        SystemLog.debug("AoApiServiceImpl", "getPosBuyHsbInfo()", "返回结果:" +  String.valueOf(result));
        return result;
    }
    
    @Override
    public ProxyBuyHsb getPosProxyBuyHsbInfo(String hsResNo, String originNo){
        SystemLog.debug("AoApiServiceImpl", "getPosProxyBuyHsbInfo()",  "请求参数:" +  " hsResNo="+hsResNo+" originNo="+originNo);
        ProxyBuyHsb result = aoExchangeHsbService.getPosProxyBuyHsbInfo(hsResNo, originNo);
        SystemLog.debug("AoApiServiceImpl", "getPosProxyBuyHsbInfo()", "返回结果:" +  String.valueOf(result));
        return result;
    }
}
