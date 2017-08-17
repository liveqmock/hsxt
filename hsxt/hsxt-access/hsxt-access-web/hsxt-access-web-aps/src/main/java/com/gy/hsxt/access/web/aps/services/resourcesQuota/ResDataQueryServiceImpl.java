/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/***
 * 资源数据查询
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: IResDataQueryService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-16 下午2:36:47
 * @version V1.0
 */
@Service
public class ResDataQueryServiceImpl extends BaseServiceImpl implements IResDataQueryService {

    @Resource
    private IBSQuotaService ibsQuotaService;

    /**
     * 统计管理公司下的资源数据
     * @param mEntResNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IResDataQueryService#getListTable(java.lang.String)
     */
    @Override
    public QuotaStatOfManager getListTable(String mEntResNo) throws HsException {

        // 1、统计管理公司下的资源数据
        QuotaStatOfManager qsom = ibsQuotaService.statResDetailOfManager(mEntResNo);

        // 2、空对象验证
        if (qsom == null){
            qsom=new QuotaStatOfManager();
        }

        return qsom;
    }

    /**
     * 资源配额查询
     * 
     * @param provinceNo
     * @param cityNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IResDataQueryService#resQuotaQuery(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public QuotaDetailOfProvince resQuotaQuery(String provinceNo, String cityNo) throws HsException {

        // 1、统计省级(二级区域)下的资源数据
        QuotaDetailOfProvince qdop = ibsQuotaService.statResDetailOfProvince(provinceNo, cityNo);

        // 2、空对象验证
        if (qdop == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        return qdop;
    }
}
