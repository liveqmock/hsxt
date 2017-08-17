/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import org.apache.commons.lang.StringUtils;

import com.gy.hsxt.access.pos.constant.PosConstant;


/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: Fd32 
 * @Description: 受理方标识码  该哉只有响应数据
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:05:30 
 * @version V1.0
 */
public class Fd32 extends Afd {

	@Override
	public String doResponseProcess() {
		return PosConstant.ACCEPT_CODE;
	}
	
	/**
	 * 20151209 增加对当前企业管理企业互生号的获取
	 * @param bit
	 * @param entResNo
	 * @return 
	 * @see com.gy.hsxt.access.pos.model.Afd#doResponseProcess(int, java.lang.String)
	 */
	@Override
    public String doResponseProcess(Object data) {
	    String entResNo = (String)data;
	    String resno =  StringUtils.rightPad(entResNo.subSequence(0, 2).toString(), 8, '0');
        return resno;
    }
}
