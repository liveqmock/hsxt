/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.Map;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;

/***
 * 资源配额管理接口类
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.resoucemanage
 * @ClassName: IResouceQuotaService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-20 下午9:27:33
 * @version V1.0
 */
public interface IResouceQuotaService extends IBaseService {

    /**
     * 消费者资源详情
     * @param SCSBase
     * @return
     * @throws HsException
     */
    public Map<String, Object> personResDetail(SCSBase scsBase) throws HsException;
    
    /**
     * 企业下的消费者统计
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<ReportsResourceStatistics> entNextPersonStatisticsPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 企业资源分页(2 成员企业;3 托管企业;4 服务公司)
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<ReportsEnterprisResource> entResPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
    

}
