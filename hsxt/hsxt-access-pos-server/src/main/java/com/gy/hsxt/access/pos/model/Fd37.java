/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.util.CommonUtil;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd37 
 * @Description: 37域 asc 请求原POS中心流水号
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:05:51 
 * @version V1.0
 */
public class Fd37 extends Afd {
	
	private static final int FD37_LENGTH = 12;

	@Override
	public String doResponseProcess(Object data) throws Exception {
		CommonUtil.checkState(
				null == data || FD37_LENGTH != ((String) data).length(),
				"业务流水号:" + data + "格式异常!", PosRespCode.POS_CENTER_FAIL);
		return (String) data;
	}

}
