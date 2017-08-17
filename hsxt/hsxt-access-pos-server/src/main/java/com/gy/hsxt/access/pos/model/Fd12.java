/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import java.sql.Timestamp;

import com.gy.hsxt.common.utils.DateUtil;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd12 
 * @Description: 12域 bcd  小时分秒
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:04:54 
 * @version V1.0
 */
public class Fd12 extends Afd {

	@Override
	public String doResponseProcess(Object data) {

		String date = DateUtil.timestampToString((Timestamp)data, DateUtil.DATE_FORMAT_HHmmss);
		return date;
	}
}
