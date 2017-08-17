/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.bean;

import java.util.Arrays;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.bean
 * 
 *  File Name       : Period.java
 * 
 *  Creation Date   : 2015-10-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 重试周期对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class Period {
	private int count;
	private int[] interval;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getInterval() {
		return interval;
	}

	public void setInterval(int[] interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "Period [count=" + count + ", interval="
				+ Arrays.toString(interval) + "]";
	}

}
