/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.common.IPubParamService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;

/***
 * 资源配额管理实现类
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.resoucemanage
 * @ClassName: ResouceQuotaService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-20 下午9:28:55
 * @version V1.0
 */
@Service
public class ResouceQuotaService extends BaseServiceImpl implements IResouceQuotaService {

    /** 报表服务类 */
    @Resource
    private IReportsSystemResourceService iReportsSystemResourceService;
    
    /** 平台服务公共信息服务类 */
    @Resource
    private IPubParamService pubParamService;

    /**
     * 消费者资源统计详情
     * @param mcsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService#personResDetail(com.gy.hsxt.access.web.bean.MCSBase)
     */
    @Override
    public Map<String, Object> personResDetail(SCSBase mcsBase) throws HsException {
        //声明变量
        ReportsResourceStatistics rrs = new ReportsResourceStatistics();
        /**
         * 描述  查询消费者资源统计汇总数据传值应该给企业客户号
         * 修改人：李智
         */
        rrs.setCustId(mcsBase.getEntCustId());
//        rrs.setHsResNo(mcsBase.getEntResNo());
        //获取地区平台信息
        LocalInfo li=pubParamService.findSystemInfo();
        // 获取消费者统计详情
        List<ReportsResourceStatistics> rrsList = iReportsSystemResourceService.searchResourceStatistics(rrs);
        // 非空时，返回集合首个对象
        if (null != rrsList && rrsList.size()>0)
        {
            rrs=rrsList.get(0);
        }
        return this.personResDetailResult(rrs,li);
    }
    
    /**
     * 返回统计结果
     * @param rrs
     * @param li
     * @return
     */
    private Map<String, Object> personResDetailResult(ReportsResourceStatistics rrs, LocalInfo li) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        /** 系统资源使用数 **/
        retMap.put("systemResourceUsageNumber", rrs.getSystemResourceUsageNumber());
        /** 实名注册数 **/
        retMap.put("registrationAuthNumber", rrs.getRegistrationAuthNumber());
        /** 实名认证数 **/
        retMap.put("realnameAuthNumber", rrs.getRealnameAuthNumber());
        /** 激活数 **/
        retMap.put("activationNumber", rrs.getActivationNumber());
        /** 活跃数 **/
        retMap.put("activeNumber", rrs.getActiveNumber());
        /** 国家区域 **/
        retMap.put("countryNo", li.getCountryNo());
        return retMap;
    }

    /**
     * 企业资源分页(2 成员企业;3 托管企业;4 服务公司)
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService#entResPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<ReportsEnterprisResource> entResPage(Map filterMap, Map sortMap, Page page) throws HsException {
        //创建查询参数对象
        ReportsEnterprisResource rer=this.createRER(filterMap);
        //查询企业资源
        return iReportsSystemResourceService.searchEnterprisResourcePage(rer,page);
    }

    /**
     * 企业下的消费者统计
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService#entNextPersonStatisticsPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<ReportsResourceStatistics> entNextPersonStatisticsPage(Map filterMap, Map sortMap, Page page) throws HsException {
        //查询参数对象
        ReportsResourceStatistics rrs = this.createRRS(filterMap);
        //查询资源统计结果
        return iReportsSystemResourceService.searchResourceStatisticsPage(rrs, page);
    }

    /**
     * 创建资源统计对象
     * @param filter
     * @return
     */
    ReportsResourceStatistics createRRS(Map filter) {
        ReportsResourceStatistics rrs = new ReportsResourceStatistics();
        // 管理公司互生号
        rrs.setServiceEntResNo((String) filter.get("hsResNo"));
        // 查询互生号
        String queryResNo = (String) filter.get("queryResNo");
        if (!StringUtils.isEmpty(queryResNo))
        {
            rrs.setHsResNo(queryResNo);
        }
        // 查询互生号名称
        String queryResNoName = (String) filter.get("queryResNoName");
        if (!StringUtils.isEmpty(queryResNoName))
        {
            rrs.setEntCustName(queryResNoName);
        }
        return rrs;
    }
    
    /**
     * 企业资源对象
     * @param filter
     * @return
     */
    ReportsEnterprisResource createRER(Map filter) {
        ReportsEnterprisResource rer=new ReportsEnterprisResource();
        // 管理公司互生号
       // rer.setAdminEntResNo((String) filter.get("entResNo"));
        //客户类型
       // rer.setCustType((Integer) filter.get("custType"));
        // 查询互生号
        String queryResNo = (String) filter.get("queryResNo");
        if (!StringUtils.isEmpty(queryResNo))
        {
            rer.setHsResNo(queryResNo);
        }
        // 查询互生号名称
        String queryResNoName = (String) filter.get("queryResNoName");
        if (!StringUtils.isEmpty(queryResNoName))
        {
            rer.setEntCustName(queryResNoName);
        }
        // 联系人姓名
        String contacts = (String) filter.get("contacts");
        if (!StringUtils.isEmpty(contacts))
        {
            rer.setContactPerson(contacts);
        }
        return rer;
    }
}
