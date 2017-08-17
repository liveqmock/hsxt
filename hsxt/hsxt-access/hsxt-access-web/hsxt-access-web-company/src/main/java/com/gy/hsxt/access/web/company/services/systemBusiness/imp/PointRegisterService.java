/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointRegisterService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsPointService;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.QueryPointRecord;

@Service
public class PointRegisterService extends BaseServiceImpl<PointRegisterService>
		implements IPointRegisterService {

	@Autowired
	private IPsPointService ipsPointService;// 网上积分登记

	@Autowired
	private IPsQueryService ipsQueryService;

	/**
	 * 分页查询积分登记记录
	 * 
	 * @param filterMap
	 *            :查询条件map
	 * @param filterMap
	 *            :排序条件map
	 * @param page
	 *            :分页信息
	 * @return 发票的分页数据
	 */
	@Override
	public PageData<PointRecordResult> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {
		QueryPointRecord queryPointRecord = new QueryPointRecord();
		queryPointRecord.setCount(page.getPageSize());
		queryPointRecord.setPageNumber(page.getCurPage());
		if (filterMap != null) {

			// 起始时间
			String startDate = (String) filterMap.get("startDate");
			if (!StringUtils.isEmpty(startDate) && !"null".equals(startDate)) {
				queryPointRecord.setStartDate(startDate);
			}

			// 结束时间
			String endDate = (String) filterMap.get("endDate");
			if (!StringUtils.isEmpty(endDate) && !"null".equals(endDate)) {
				queryPointRecord.setEndDate(endDate);
			}
			// 消费者互生号
			String perResNo = (String) filterMap.get("perResNo");
			if (!StringUtils.isEmpty(perResNo) && !"null".equals(perResNo)) {
				queryPointRecord.setHsResNo(perResNo);
			}

			// 企业客户号
			String entCustId = (String) filterMap.get("entCustId");
			if (!StringUtils.isEmpty(entCustId) && !"null".equals(entCustId)) {
				queryPointRecord.setEntCustId(entCustId);
			}

		}
		return ipsQueryService
				.pointRegisterRecord(queryPointRecord);
		
	}

	@Override
	public PointResult pointRegister(Point point) throws HsException {
		return ipsPointService.pointRegister(point);
	}

}
