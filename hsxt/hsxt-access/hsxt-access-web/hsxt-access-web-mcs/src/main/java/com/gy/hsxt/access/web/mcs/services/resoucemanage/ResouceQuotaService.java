/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.resoucemanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.mcs.services.common.IPubParamService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.rp.api.IReportsSystemResourceService;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.rp.bean.ReportsResourceStatistics;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;
import com.gy.hsxt.uc.as.bean.ent.AsQueryBelongEntCondition;

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
    
    @Resource
    private IUCAsEntService ucEntService;

    /**
     * 消费者资源统计详情
     * 
     * @param mcsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService#personResDetail(com.gy.hsxt.access.web.bean.MCSBase)
     */
    @Override
    public Map<String, Object> personResDetail(MCSBase mcsBase) throws HsException {
        // 声明变量
        ReportsResourceStatistics rrs = new ReportsResourceStatistics();
        rrs.setHsResNo(mcsBase.getEntResNo());
        // 获取地区平台信息
        LocalInfo li = pubParamService.findSystemInfo();
        // 获取消费者统计详情
        List<ReportsResourceStatistics> rrsList = iReportsSystemResourceService.searchResourceStatistics(rrs);
        // 非空时，返回集合首个对象
        if (null != rrsList && rrsList.size() > 0)
        {
            rrs = rrsList.get(0);
        }
        return this.personResDetailResult(rrs, li);
    }

    /**
     * 返回统计结果
     * 
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
     * 
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
        // 创建查询参数对象
 //       ReportsEnterprisResource rer = this.createRER(filterMap);
        // 查询企业资源
 //       return iReportsSystemResourceService.searchEnterprisResourcePage(rer, page);
        
        AsQueryBelongEntCondition condition = getCondition(filterMap);
		// 查询资源一览
		PageData<ReportsEnterprisResource> data = new PageData<ReportsEnterprisResource>();
		PageData<AsBelongEntInfo> ucData = ucEntService.listBelongEntInfo(
				condition, page);
		List<ReportsEnterprisResource> dataList = new ArrayList<>();
		if (null != ucData && null != ucData.getResult()) {
			data.setCount(ucData.getCount());
			ReportsEnterprisResource resource = null;
			for (AsBelongEntInfo asBelongEnt : ucData.getResult()) {
				resource = buildResource(asBelongEnt);
				dataList.add(resource);
			}
		}
		data.setResult(dataList);
		return data;
    }
    
    private ReportsEnterprisResource buildResource(AsBelongEntInfo asBelongEnt) {
		ReportsEnterprisResource resourece = new ReportsEnterprisResource();
		resourece
				.setHsResNo(StringUtils.nullToEmpty(asBelongEnt.getEntResNo()));
		resourece.setEntCustName(StringUtils.nullToEmpty(asBelongEnt
				.getEntName()));
		resourece.setEntRegAddr(StringUtils.nullToEmpty(asBelongEnt
				.getEntAddr()));
		resourece.setContactPerson(StringUtils.nullToEmpty(asBelongEnt
				.getContactPerson()));
		resourece.setContactPhone(StringUtils.nullToEmpty(asBelongEnt
				.getContactPhone()));
		resourece
				.setOpenDate(StringUtils.nullToEmpty(asBelongEnt.getOpenDate()));
		if (null != asBelongEnt.getRealNameAuthSatus()) {
			resourece.setRealnameAuth(asBelongEnt.getRealNameAuthSatus());
		}
		if (null != asBelongEnt.getPointStatus()) {
			resourece.setBaseStatus(asBelongEnt.getPointStatus());
			resourece.setParticipationScore(asBelongEnt.getPointStatus());
		}
		if (null != asBelongEnt.getCustNum()) {
			resourece.setEnabledCardholderNumber(asBelongEnt.getCustNum()
					.intValue());
		}
		if(StringUtils.isNotBlank(asBelongEnt.getEntCustId())){
			resourece.setCustId(asBelongEnt.getEntCustId());
		}
		return resourece;
	}

    private AsQueryBelongEntCondition getCondition(Map filterMap) {
		AsQueryBelongEntCondition condition = new AsQueryBelongEntCondition();
		String entResNo = (String) filterMap.get("hsResNo");
		if (StringUtils.isBlank(entResNo)) {
			entResNo = (String) filterMap.get("pointNo");
		}
		if (StringUtils.isNotBlank(entResNo)) {
			condition.setEntResNo(entResNo);
		}
		String queryResNo = (String) filterMap.get("queryResNo");
		if (StringUtils.isNotBlank(queryResNo)) {
			condition.setBelongEntResNo(queryResNo);
		}
		Integer custType = null;
		Integer staus = null;
		String tempStatus = (String) filterMap.get("status");
		String tempCustType = (String) filterMap.get("custType");
		try {
			if(StringUtils.isNotBlank(tempStatus)){
				staus = Integer.parseInt(tempStatus);
			}
			if(StringUtils.isNotBlank(tempCustType)){
				custType = Integer.parseInt(tempCustType);
			}
			
		} catch (Exception e) {
			SystemLog.error("ResouceQuotaService", "getCondition", "转换Integer类型出错：staus["+tempStatus+",custType["+tempCustType+"]", e);
		}
		String belongEntName = (String) filterMap.get("queryResNoName");
		if(StringUtils.isBlank(belongEntName)){
			belongEntName = (String) filterMap.get("entName");
		}
		
		if (StringUtils.isNotBlank(belongEntName)) {
			condition.setBelongEntName(belongEntName);
		}
		String linkman = (String) filterMap.get("contacts");
		if(StringUtils.isBlank(linkman)){
			linkman = (String) filterMap.get("linkman");
		}
		if (StringUtils.isNotBlank(linkman)) {
			condition.setBelongEntContacts(linkman);
		}
		if(null != custType){
			condition.setBlongEntCustType(custType);
		}
		condition.setBeginDate((String) filterMap.get("startDate"));
		condition.setEndDate((String) filterMap.get("endDate"));
		condition.setBelongEntBaseStatus(staus);
		return condition;
	}
    
    /**
     * 企业下的消费者统计
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IResouceQuotaService#entNextPersonStatisticsPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<ReportsResourceStatistics> entNextPersonStatisticsPage(Map filterMap, Map sortMap, Page page)
            throws HsException {
        // 查询参数对象
        ReportsResourceStatistics rrs = this.createRRS(filterMap);
        // 查询资源统计结果
        return iReportsSystemResourceService.searchResourceStatisticsPage(rrs, page);
    }

    

    /**
     * 创建资源统计对象
     * 
     * @param filter
     * @return
     */
    ReportsResourceStatistics createRRS(Map filter) {
        ReportsResourceStatistics rrs = new ReportsResourceStatistics();
        // 管理公司互生号
        rrs.setAdminEntResNo((String) filter.get("entResNo"));
        rrs.setCustType(CustType.SERVICE_CORP.getCode());
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
     * 
     * @param filter
     * @return
     */
    ReportsEnterprisResource createRER(Map filter) {
        ReportsEnterprisResource rer = new ReportsEnterprisResource();
        // 管理公司互生号
        rer.setAdminEntResNo((String) filter.get("entResNo"));
        // 客户类型
        rer.setCustType(Integer.parseInt(filter.get("custType").toString()));
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
