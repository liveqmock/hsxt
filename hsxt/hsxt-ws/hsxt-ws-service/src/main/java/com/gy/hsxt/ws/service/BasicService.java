/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.common.WsErrorCode;

/**
 * 基本公共服务
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: BasicService
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-26 下午4:12:32
 * @version V1.0
 */
public class BasicService {

	public void validParamIsEmptyOrNull(Object param, String errorDesc) throws HsException {
		if (isBlank(param)) {
			throw new HsException(WsErrorCode.PARAM_IS_NULL.getCode(), errorDesc);
		}
	}

	public void logInfo(String msg) {
		SystemLog.info("hxst-ws", "BasicService", msg);
	}

	public void logError(String msg, Exception e) {
		SystemLog.error("hxst-ws", "BasicService", msg, e);
	}

}
