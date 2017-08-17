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
 * @ClassName: Fd11 
 * @Description: 11域 bcd POS终端交易流水号
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:04:40 
 * @version V1.0
 */
public class Fd11 extends Afd {

	private static final int FD11_LENGTH = 6;

	@Override
	public String doRequestProcess(String data) throws Exception {
		CommonUtil.checkState(
				StringUtils.isEmpty(data) || FD11_LENGTH != data.length(),
				"11域，Terminal serial number:" + data + " Invalid format.",
				PosRespCode.REQUEST_PACK_FORMAT);
		return data;
	}
}
