/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.util;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosReqTypeEnum;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.service.UidApiService;
import com.gy.hsxt.common.utils.DateUtil;

@Component("posSeqGenerator")
public class PosSeqGenerator {
	
	@Autowired
	@Qualifier("uidApiService")
	private UidApiService uidApiService;

    /**
     * 流水号产生规则 渠道类型（2）+年份个位数（1）+当天是一年中的第几天（3）+时分秒（6）
     * @param seqPrefix
     * @return
     * @throws PosException
     */
    public static String getSeq(String seqPrefix) throws PosException {
        StringBuilder buffer = new StringBuilder(12);
        buffer.append(PosReqTypeEnum.getSeqPrefixByReqType(seqPrefix));
        Calendar now = Calendar.getInstance();
        buffer.append(String.valueOf(now.get(Calendar.YEAR)%10));
        
        int day = now.get(Calendar.DAY_OF_YEAR);
        
        buffer.append(StringUtils.leftPad(String.valueOf(day), 3, '0'));
        buffer.append(DateUtil.DateToString(DateUtil.getCurrentDate(), PosConstant.TIME_FORMAT));
        return buffer.toString();
    }
    
    /**
     * 流水号产生规则 渠道类型（2）+ System.currentTimeMillis()取前十位
     * @param seqPrefix
     * @return
     * @throws PosException
     */
    public static String getSeq2(String seqPrefix) throws PosException {
        StringBuilder buffer = new StringBuilder(12);
        buffer.append(PosReqTypeEnum.getSeqPrefixByReqType(seqPrefix));
        String timeSeq = String.valueOf(System.currentTimeMillis());
        if(timeSeq.length()<10){
            return getSeq(seqPrefix);
        }else{
            timeSeq = timeSeq.substring(0, 10);
        }
        buffer.append(timeSeq);
        
        return buffer.toString();
    }
    
    /**
     * 流水号产生规则 渠道类型（1）+ 11位序列值
     * @param reqId
     * @return
     * @throws PosException
     */
    public String getSeq3(String reqId, String entResNo) throws PosException {
        StringBuilder buffer = new StringBuilder(12);
        return buffer.append(getRouteIdByReqId(reqId))
        				.append(uidApiService.getUid(entResNo)).toString();
        
    }
    
    
    /**
     * 根据请求类型设定该业务的路由方向代码，用作流水号第一位
     * @param reqId 请求类型id 消息类型+3域+60.1域
     * @return
     * @throws PosException
     */
    private static String getRouteIdByReqId(String reqId) throws PosException{
    	switch(reqId){
    	case PosConstant.REQ_HSB_ENT_RECHARGE_ID://兑换互生币，到ao
    		return "1";
    	case PosConstant.REQ_HSB_PROXY_RECHARGE_ID://代兑互生币，到ao。
    		//因ao将兑换、代兑分在两张表，为避免轮询，提供了两个接口，这里暂时占用2个值。
    		return "2";
    	default://未声明的，默认都路由到ps
        	//start--modified by liuzh on 2016-05-28 修改原因: pos2.0历史数据前缀是0; 现在修噶成3和以前的历史数据区分
    		//return "0";
    		return "3";
    		//end--modified by liuzh on 2016-05-28	
    	}
    	
    	
    }
    
        
        
}



