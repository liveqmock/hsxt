package com.gy.hsxt.access.web.scs.services.accountManagement;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.bm.IBSmlmService;
import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.AllocDetailSum;
import com.gy.hsxt.ps.bean.QueryDetail;

/***************************************************************************
 * <PRE>
 * Description 		: 用户积分处理
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.integration.service
 * 
 * File Name        : IntegrationServiceImpl
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-18 下午3:10:41  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-18 下午3:10:41
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class IntegralAccountService extends BaseServiceImpl implements
		IIntegralAccountService {
	@Resource
	private IBSmlmService bsmlmService;

	@Resource
	private IPsQueryService psQueryService;// 消费积分查询服务

	// 分页查询互生积分分配列表
	@Override
	public PageData<MlmTotal> queryMlmListPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		MlmQuery mlmQuery = new MlmQuery();
		// 客户号
		mlmQuery.setCustId((String) filterMap.get("entCustId"));
		// 开始时间
		mlmQuery.setStartDate((String) filterMap.get("beginDate"));
		// 结束时间
		mlmQuery.setEndDate((String) filterMap.get("endDate"));

		return bsmlmService.queryMlmListPage(page, mlmQuery);

	}

	// 分页查询再增值积分汇总列表
	@Override
	public PageData<BmlmDetail> queryBmlmListPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		BmlmQuery bmlmQuery = new BmlmQuery();
		// 客户号
		bmlmQuery.setCustId((String) filterMap.get("entCustId"));
		// 开始时间
		bmlmQuery.setStartDate((String) filterMap.get("beginDate"));
		// 结束时间
		bmlmQuery.setEndDate((String) filterMap.get("endDate"));
		return bsmlmService.queryBmlmListPage(page, bmlmQuery);

	}

	/**
	 * 分页查询消费积分分配汇总列表
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return 查询消费积分分配汇总列表集合
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.IIntegralAccountService#queryPointDetailSumPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AllocDetailSum> queryPointDetailSumPage(Map filterMap,
			Map sortMap, Page page) throws HsException {
		QueryDetail qd = this.createConditon(filterMap);
		return psQueryService.queryPointDetailSumPage(qd, page);

	}

	/**
	 * 封装查询条件
	 * 
	 * @param filterMap
	 * @return
	 */
	QueryDetail createConditon(Map filterMap) {
		QueryDetail qd = new QueryDetail();
		qd.setResNo((String) filterMap.get("hsResNo"));// 互生号
		qd.setBeginBatchNo((String) filterMap.get("beginBatchNo"));// 开始批次号
		qd.setEndBatchNo((String) filterMap.get("endBatchNo"));// 结束批次号
		return qd;
	}

}
