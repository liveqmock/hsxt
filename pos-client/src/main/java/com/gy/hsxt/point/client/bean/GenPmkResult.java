/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.point.client.bean;

import java.io.Serializable;

/***
 * PMK密钥输出类
 * 
 * @Package: com.gy.hsxt.access.web.bean.posInterface
 * @ClassName: PMKAndSyncParamOut
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-24 上午11:54:06
 * @version V3.0
 */
public class GenPmkResult implements Serializable {

    private static final long serialVersionUID = 7853396132779744196L;

    /**
     * PMK数组
     */
    private String pmk;

    /**
     * 密钥实体
     */
    private PosInfoResult posInfo;

    public String getPmk() {
        return pmk;
    }

    public void setPmk(String pmk) {
        this.pmk = pmk;
    }

	/**
	 * @return 密钥实体
	 */
	public PosInfoResult getPosInfo() {
		return posInfo;
	}

	/**
	 * @param 密钥实体 
	 */
	public void setPosInfo(PosInfoResult posInfo) {
		this.posInfo = posInfo;
	}
}
