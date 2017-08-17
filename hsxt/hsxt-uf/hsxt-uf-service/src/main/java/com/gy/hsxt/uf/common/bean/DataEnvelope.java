/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.bean;

import java.io.Serializable;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.bean
 * 
 *  File Name       : DataEnvelope.java
 * 
 *  Creation Date   : 2015-10-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 包装handler之间传递的响应结果对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DataEnvelope implements Serializable {
	private static final long serialVersionUID = -8951365052271296678L;

	private Object data;

	public DataEnvelope() {
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static DataEnvelope build() {
		return new DataEnvelope();
	}
}
