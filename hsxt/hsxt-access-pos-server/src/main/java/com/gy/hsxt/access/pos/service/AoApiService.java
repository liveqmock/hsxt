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

package com.gy.hsxt.access.pos.service;

import java.util.List;

import com.gy.hsxt.ao.bean.BatchSettle;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.PosBuyHsbCancel;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: AoApiService 
 * @Description: 业务类接口封装
 *
 * @author: weiyq 
 * @date: 2015-12-11 上午11:10:26 
 * @version V1.0 
 

 */
public interface AoApiService {
    
    
    /**
     * 企业兑换（购买）互生币(POS)
     * @param buyHsb
     * @return
     */
    public String savePosBuyHsb(BuyHsb buyHsb) throws HsException;
    
    /**
     * 代兑互生币
     * @param proxyBuyHsb
     * @return
     */
    public String savePosProxyBuyHsb(ProxyBuyHsb proxyBuyHsb)throws HsException;
    /**
     * 兑换互生币冲正
     * @param posBuyHsbCancel
     * @throws HsException
     */
    public void reversePosBuyHsb(PosBuyHsbCancel posBuyHsbCancel) throws HsException;
    /**
     * 代兑互生币冲正
     * @param posBuyHsbCancel
     * @throws HsException
     */
    public void reversePosProxyBuyHsb (PosBuyHsbCancel posBuyHsbCancel) throws HsException;
    /**
     * 批结算
     * @param batchSettle
     * @param buyHsbAmt
     * @param hsbCount
     * @param proxyBuyHsbAmt
     * @param proxyHsbCount
     * @return
     */
    public Boolean batchCheckResult(BatchSettle batchSettle, String buyHsbAmt, String hsbCount, String proxyBuyHsbAmt,String proxyHsbCount);
    /**
     * 批上传
     * @param batchSettle
     * @param listBatchUpload
     */
    public void batchTerminalDataUpload(BatchSettle batchSettle,List<BatchUpload> listBatchUpload);
    /**
     * 查询兑换互生币
     * @param hsResNo
     * @param termNo
     * @param originNo
     * @return
     */
    public BuyHsb getPosBuyHsbInfo(String hsResNo, String originNo);
    /**
     * 查询代兑互生币
     * @param hsResNo
     * @param termNo
     * @param originNo
     * @return
     */
    public ProxyBuyHsb getPosProxyBuyHsbInfo(String hsResNo, String originNo);
}
