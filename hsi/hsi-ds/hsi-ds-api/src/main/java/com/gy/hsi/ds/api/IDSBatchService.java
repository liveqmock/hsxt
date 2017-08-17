/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 * 批处理作业dubbo接口
 * @author yangyp
 * 
 */
package com.gy.hsi.ds.api;

import java.util.Map;

/**
 * 定时任务统一接口
 * 
 * @Package: com.gy.hsi.ds.api
 * @ClassName: IDSBatchService
 * @Description: none
 *
 * @author: prgma15
 * @date: 2015年9月29日 下午2:59:05
 * @version V1.0
 */
public interface IDSBatchService {

	/**
	 * 批处理作业
	 * 
	 * @param executeId
	 *            执行id
	 * @param args
	 *            参数map
	 * @return
	 */
	public boolean excuteBatch(String executeId, Map<String, String> args);
}
