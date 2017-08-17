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
 * @ClassName: Fd42 
 * @Description: 42域  ascii 企业管理号
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:06:24 
 * @version V1.0
 */
public class Fd42 extends Afd{
	
	private static final int FD42_LENGTH = 15;

	@Override
	public String doRequestProcess(String data) throws Exception{
		
		if(data.length() != FD42_LENGTH){
			CommonUtil.checkState(true, "42域,企业管理号:" + data + "格式异常!", PosRespCode.REQUEST_PACK_FORMAT);
		}
		return data.substring(4);
	}	
	/**
	 * 应答域处理   撤单、冲正等需要返回原交易数据
	 */
	@Override
	public String doResponseProcess(Object data) {
		String entNo = StringUtils.leftPad((String) data, FD42_LENGTH, '0');
		return entNo;
	}
}
