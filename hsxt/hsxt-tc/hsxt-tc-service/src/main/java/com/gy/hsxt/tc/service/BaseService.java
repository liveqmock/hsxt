/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.text.SimpleDateFormat;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.enums.TcErrorCode;

/**
 * 
 * @Package: com.gy.hsxt.tc.service
 * @ClassName: BaseService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-18 下午6:19:14
 * @version V1.0
 */
public class BaseService {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public void validEmptyOrNull(Object... params) throws HsException {
		if (params.length < 1) {
			return;
		}
		for (Object param : params) {
			if (isBlank(param)) {
				throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
						TcErrorCode.PARAM_IS_REQUIRED.getDesc());
			}
		}
	}

	public void validDateParam(String... dates) throws HsException {
		if (dates.length < 1) {
			return;
		}
		for (String date : dates) {
			if (isBlank(date)) {
				continue;
			}

			try {
				dateFormat.parse(date);
			} catch (Exception e) {
				throw new HsException(TcErrorCode.DATE_IS_NOT_FORMAT.getCode(),
						TcErrorCode.DATE_IS_NOT_FORMAT.getDesc());
			}
		}
	}

}
