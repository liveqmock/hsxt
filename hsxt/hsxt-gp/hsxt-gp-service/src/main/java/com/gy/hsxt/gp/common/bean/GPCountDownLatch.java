/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.bean;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.bean
 * 
 *  File Name       : PGCountDownLatch.java
 * 
 *  Creation Date   : 2015年12月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class GPCountDownLatch extends CountDownLatch {

	public GPCountDownLatch(int count) {
		super(count);
	}

	@Override
	public void countDown() {
		super.countDown();
	}

	public void awaitFaithfully() {
		try {
			super.await();
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void await() {
		try {
			super.await(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public boolean await(long timeout, TimeUnit unit) {
		try {
			return super.await(timeout, unit);
		} catch (InterruptedException e) {
		}

		return false;
	}
}