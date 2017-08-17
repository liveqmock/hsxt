/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.mapper.vo;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.common.constant.ConfigConst.TcConfigConsts;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.pg.mapper.vo
 * 
 *  File Name       : BalanceParentVo.java
 * 
 *  Creation Date   : 2015-11-13
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

public class BalanceParentVo {
	
	protected boolean isEndLine = false;

	public boolean isEndLine() {
		return isEndLine;
	}

	public void setEndLine(boolean isEndLine) {
		this.isEndLine = isEndLine;
	}

	@Override
	public String toString() {
		return HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_TC_BALANCE_DATA_END);
	}
}
