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
 * @ClassName: Fd35 
 * @Description: 35域 bcd LL 第2磁道数据
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:05:42 
 * @version V1.0
 */
public class Fd35 extends Afd {

	@Override
	public String doRequestProcess(String data) throws Exception {
		
		try {
			String[] cardNoArr = data.toLowerCase().split("d");
			return cardNoArr[0] + cardNoArr[1]; 
		} catch (Exception e) {
			CommonUtil.checkState(true, "35域数据异常:" + data
					+ ", invalid format.", PosRespCode.REQUEST_PACK_FORMAT, e);
		}
		return data;
	}
}
