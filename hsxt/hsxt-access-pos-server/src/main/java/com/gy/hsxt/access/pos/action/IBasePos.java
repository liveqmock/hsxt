/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import com.gy.hsxt.access.pos.model.Cmd;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: BasePosImpl
 * @Description: pos 业务 基类接口， 所有新增加的业务都应实现此接口
 *
 * @author: wucl 
 * @date: 2015-9-23 上午11:25:14
 * @version V1.0
 */
public interface IBasePos {

	/**
	 * 获取请求类型
	 * @author   wucl	
	 * 2015-9-23 上午11:27:52
	 * @return
	 */
	public String getReqType();

	/**
	 * 具体业务实现
	 * @author   wucl	
	 * 2015-9-23 上午11:27:34
	 * @param cmd
	 * @return
	 */
	public Object action(Cmd cmd) throws Exception;

}
