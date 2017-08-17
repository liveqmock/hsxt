/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.interfaces;

import java.util.Map;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 自动派单接口
 * 
 * @Package: com.gy.hsxt.tm.interfaces
 * @ClassName: ISendOrderService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-5 上午9:29:32
 * @version V3.0.0
 */
public interface ISendOrderService extends IDSBatchService {

    public boolean excuteBatch(String executeId, Map<String, String> arg0) throws HsException;
}
