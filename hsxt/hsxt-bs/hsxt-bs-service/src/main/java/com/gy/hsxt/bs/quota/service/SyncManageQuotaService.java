/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.quota.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.bs.quota.interfaces.ISyncManageQuotaService;
import com.gy.hsxt.bs.quota.mapper.QuotaMapper;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

@Service
public class SyncManageQuotaService implements ISyncManageQuotaService {

    @Autowired
    private QuotaMapper quotaMapper;

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
    @Override
    public void initManageQuota(String entResNo, String entCustName, Integer totalNum) throws HsException {
        try
        {
            // 如果管理公司初始化最大配额已经存在，则更新，否则
            if (quotaMapper.existInitManageQuota(entResNo) > 0)
            {
                quotaMapper.updateInitManageQuota(entResNo, entCustName, totalNum);
            }
            else
            {
                quotaMapper.initManageQuota(entResNo, entCustName, totalNum);
            }
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.FAIL, "保存管理公司初始化最大配额数出现异常" + e);
        }
    }
}
