/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 * 批处理作业dubbo接口
 * @author yangyp
 * 
 */
package com.gy.hsi.ds.api;

/**
 * 作业执行状态监听接口
 * 
 * @Package: com.gy.hsi.ds.api
 * @ClassName: IDSBatchServiceListener
 * @Description: none
 *
 * @author: prgma15
 * @date: 2015年9月29日 下午2:50:20
 * @version V1.0
 */
public interface IDSBatchServiceListener {

	/**
	 * 作业执行状态上报
	 * 
	 * @param executeId
	 *            执行id
	 * @param status
	 *            作业执行状态 0 成功 1失败 2执行中
	 * @param desc
	 *            相对应执行状态的描述
	 */
	public void reportStatus(String executeId, int status, String desc);
}
