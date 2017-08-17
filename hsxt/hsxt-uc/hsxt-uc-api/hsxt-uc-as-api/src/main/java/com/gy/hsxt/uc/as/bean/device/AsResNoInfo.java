/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.device;

import java.io.Serializable;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.bean.device
 * @ClassName: AsResNoInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-3 下午5:46:21
 * @version V1.0
 */
public class AsResNoInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String realName;

	/**
	 * 消费者姓名
	 * 
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 消费者姓名
	 * 
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

}
