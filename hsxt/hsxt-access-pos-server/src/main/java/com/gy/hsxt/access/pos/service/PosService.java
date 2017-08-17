/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.pos.service;

import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.SyncParamPosOut;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service  
 * @ClassName: PosService 
 * @Description: pos 业务处理
 *
 * @author: wucl 
 * @date: 2015-11-13 下午5:59:46 
 * @version V1.0
 */
public interface PosService {
    
    
    /**
     * 同步参数:基础信息4位数、货币信息4位数、国家代码信息4位数
     *
     * @param syncParamPosIn 输入终端基础版本信息（基础信息版本号、货币信息版本号、国家代码版本号） +基础信息（终端交易码、终端流水号、终端编号、企业管理号）
     * @return 返回同步参数+基础信息（成功：应答码、国际信用卡公司代码，中心流水号、时间，失败：应答码、应答消息、中心流水号、时间）
     * @throws PosException 
     */
    public SyncParamPosOut doPosSyncParamWork(PosReqParam reqParam, byte[] pversion) throws PosException;

}
