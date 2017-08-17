/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.netty.parent;

import org.apache.log4j.Logger;

import com.gy.hsxt.uf.common.spring.SpringContextLoader;
import com.gy.hsxt.uf.factory.BeansFactory;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.netty.parent
 * 
 *  File Name       : NettyParent.java
 * 
 *  Creation Date   : 2015-10-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : NettyParent
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class NettyParent {
	/** 记录日志对象 **/
	protected final Logger logger = Logger.getLogger(this.getClass());

	/** bean工厂 **/
	protected final BeansFactory beansFactory = SpringContextLoader
			.getBeansFactory();

	/**
	 * 构造函数
	 */
	public NettyParent() {
	}
}
