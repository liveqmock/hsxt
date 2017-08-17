/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.device;

/**
 * Pos机设备
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.device
 * @ClassName: BsPos
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-29 上午11:44:01
 * @version V1.0
 */
public class BsPos extends BsDevice {
	private static final long serialVersionUID = -6660359690648971793L;
	/** 设备秘钥 */
	private String pmk;

	/**
	 * @return the 设备秘钥
	 */
	public String getPmk() {
		return pmk;
	}

	/**
	 * @param 设备秘钥
	 *            the pmk to set
	 */
	public void setPmk(String pmk) {
		this.pmk = pmk;
	}

}
