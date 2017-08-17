/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.utils.DateUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-param
 * 
 *  Package Name    : com.gy.hsi.ds.job.beans
 * 
 *  File Name       : TempArgsBean.java
 * 
 *  Creation Date   : 2016年2月1日
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
public class TempArgsBean {
	private final Map<String, String> map = new HashMap<String, String>();

	private Date createDate = new Date();

	public TempArgsBean() {
	}

	public TempArgsBean(Map<String, String> c) {
		if ((null != c) && (0 < c.size())) {
			map.putAll(c);
		}
	}

	public void put(String key, String value) {
		map.put(key, value);
	}

	public void put(Map<String, String> c) {
		c.putAll(c);
	}

	public String get(String key) {
		return map.get(key);
	}

	public Map<String, String> getMap() {
		return map;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public String getTempArgsKeyAndValue() {

		Entry<String, String> entry;

		StringBuilder buf = new StringBuilder("");

		Iterator<Entry<String, String>> itor = map.entrySet().iterator();

		while (itor.hasNext()) {
			entry = itor.next();
			buf.append(entry.getKey()).append("=").append(entry.getValue())
					.append(";");
		}

		String result = buf.toString();
		
		if(!StringUtils.isEmpty(result)) {
			return result;
		}
		
		return "";
	}

	public boolean isExpired() {
		return DateUtils.getDateBeforeDays(2).getTime() >= createDate.getTime();
	}
}
