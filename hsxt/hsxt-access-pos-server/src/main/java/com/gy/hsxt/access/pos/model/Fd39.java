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
 * @ClassName: Fd39 
 * @Description: 应答码
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:06:03 
 * @version V1.0
 */
public class Fd39 extends Afd {

	@Override
	public String doResponseProcess(Object data) throws Exception {
		
		final String returnStr = String.valueOf(data);
		final int len = returnStr.length();
		if (len == 2) {
			return returnStr;
		} else if (len == 4) {
			return returnStr.substring(2, 4);
		} else {
			CommonUtil.checkState(true, "39域,应答码:" + returnStr + "格式异常!", PosRespCode.POS_CENTER_FAIL);
		}
		return null;
	}
}
