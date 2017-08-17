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
 * @ClassName: Fd22 
 * @Description: 22域前二位 02表示刷卡  01表示手输
 *                       后二位 10 表示交易中包含pin（加密过的密码，pin在 53域）  20 表示 不包含
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:05:18 
 * @version V1.0
 */
public class Fd22 extends Afd {
	
	private static final int FD22_LENGTH = 3;

	@Override
	public String doRequestProcess(String data) throws Exception {
		
		CommonUtil.checkState(StringUtils.isEmpty(data) || FD22_LENGTH != data.length(), "22域，服务点输入方式码数据:" + data + "格式异常!", PosRespCode.REQUEST_PACK_FORMAT);
		
		return data;
	}

}
