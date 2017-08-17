/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.bean;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.bean
 * 
 *  File Name       : Counter.java
 * 
 *  Creation Date   : 2016年5月28日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 计数器
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class Counter {

	private long value;
	private long maxValue;
	private long defaultValue;

	public Counter(long defaultValue, long maxValue) {
		this.value = defaultValue;
		this.maxValue = maxValue;
		this.defaultValue = defaultValue;
	}

	public long doIncreasing() {
		if (this.maxValue <= this.value) {
			this.value = this.defaultValue;
		}

		this.value++;

		return this.value;
	}

	public long doIncreasingFrom(long startValue) {
		if (this.maxValue <= startValue) {
			startValue = this.defaultValue;
		} else {
			startValue++;
		}

		return startValue;
	}
}
