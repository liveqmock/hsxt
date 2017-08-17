/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: BaseActionChain 
 * @Description: 预处理基类
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:42:29 
 * @version V1.0
 */
//@Service("baseActionChain")
public class BaseActionChain extends ChainBase {

    /**
     * 预处理
     * 包括 数据初始化
     * 业务 校验
     * @param arr
     */
    public BaseActionChain(Command... arr) {
    	super(arr);
    }

	@Override
	public boolean execute(Context arg0) throws Exception {
		return super.execute(arg0);
	}
}

	