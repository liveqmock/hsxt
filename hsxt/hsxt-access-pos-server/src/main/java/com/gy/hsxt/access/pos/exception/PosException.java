/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.exception;

import com.gy.hsxt.common.constant.IRespCode;

		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.exception  
 * @ClassName: PosException 
 * @Description: pos server 异常处理
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:57:44 
 * @version V1.0
 */
public class PosException extends Exception {

	private static final long serialVersionUID = 17435624308709L;

	private IRespCode rspCode;

	private String msg;

	public PosException(IRespCode rspCode, String msg) {
		this.rspCode = rspCode;
		this.msg = msg;
	}

	public IRespCode getRspCode() {
		return rspCode;
	}

	public String getMsg() {
		return msg;
	}
}

	