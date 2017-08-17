/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;


import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.util.CommonUtil;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd60 
 * @Description: 60域 bcd LLL 交易类型码+批次号+网络管理信息码
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:07:03 
 * @version V1.0
 */
public class Fd60 extends Afd {

	
	@Override
	public Object doRequestProcess(String messageId, String data) throws Exception {
		
		CommonUtil.checkState(null == data || 8 > data.length(), "60域交易类型信息:" + data + "格式异常!", PosRespCode.REQUEST_PACK_FORMAT);

		String termTradeType = data.substring(0, 2);
		String batchNo = data.substring(2, 8);
		
		//并发高使用积分做判断可能快点
		if (!ReqMsg.SIGNIN.getReqId().equals(messageId)
				&& !ReqMsg.SIGNOFF.getReqId().equals(messageId)
				&& !ReqMsg.BATCHSETTLE.getReqId().equals(messageId)
				&& !ReqMsg.BATCHUPLOAD.getReqId().equals(messageId)) {
			
			return new TradeType(termTradeType, batchNo, StringUtils.EMPTY);
		} else {
			String netCode = data.substring(8, 11);
			return new TradeType(termTradeType, batchNo, netCode);
		}
	}

	@Override
	public String doResponseProcess(Object data) {
		final TradeType tradeType = (TradeType) data;
		return new StringBuilder(12).append(tradeType.getTermTypeCode()) //
				.append(tradeType.getBatNo()).append(tradeType.getNetCode()).toString();
	}
}
