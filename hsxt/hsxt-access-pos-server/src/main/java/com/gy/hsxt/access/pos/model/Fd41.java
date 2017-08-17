/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import org.apache.commons.lang3.StringUtils;

import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.util.CommonUtil;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd41 
 * @Description: 受卡方标识码(pos终端号)
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:06:13 
 * @version V1.0
 */
public class Fd41 extends Afd {
	
	public static final int FD41_LENGTH8 = 8;
	public static final int FD41_LENGTH4 = 4;

	@Override
	public String doRequestProcess(String data) throws Exception {
		if (data.length() != FD41_LENGTH8) {
			
			CommonUtil.checkState(true, "41域,终端编号:" + data + "格式异常!", PosRespCode.REQUEST_PACK_FORMAT);
		}
		
		//不管新老版本，都生成4位posNo,只与keyServer有关
		return data.substring(FD41_LENGTH4); 
	}
	/**
	 * 应答域处理   撤单、冲正等需要返回原交易数据
	 * @param data
	 * @return
	 */
	@Override
	public String doResponseProcess(Object data) {
		String posNo = StringUtils.leftPad((String) data, FD41_LENGTH8, '0');
		return posNo;
	}
}
