/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.quota.interfaces;

import com.gy.hsxt.common.exception.HsException;

public interface ISyncManageQuotaService {

    /**
     * 初始化管理公司配额
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param totalNum
     *            配额总数
     * @throws HsException
     */
    public void initManageQuota(String entResNo, String entCustName, Integer totalNum) throws HsException;

}
