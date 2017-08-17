/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.SwipeCardToolService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolRelationDetail;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 刷卡工具Service
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder.impl
 * @ClassName: SwipeCardToolServiceImpl
 * @Description: TODO
 * @author: likui
 * @date: 2016年4月25日 下午4:53:21
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public class SwipeCardToolServiceImpl extends BaseServiceImpl implements
		SwipeCardToolService {

	/**
	 * BS工具制作Service
	 */
	@Autowired
	private IBSToolMarkService ibSToolMarkService;

	/**
	 * 用户中心Service
	 */
	@Resource
	private UserCenterService userCenterService;

	/**
	 * 分页查询刷卡工具制作
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<ToolConfigPage> findScrollResult(Map filterMap,
			Map sortMap, Page page) throws HsException {

		BaseParam param = new BaseParam();
		String platCustName = (String) filterMap.get("custName");
		if (StringUtils.isNotBlank(platCustName)
				&& !"0000".equals(platCustName)) {
			param.setRoleIds(userCenterService.getRoleIds((String) filterMap
					.get("custId")));
		}
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		param.setHsCustName(filterMap.get("hsCustName").toString());
		param.setType(filterMap.get("type").toString());
		try {
			PageData<ToolConfigPage> result = ibSToolMarkService
					.queryToolConfigMarkPage(param, page);
			return result == null || result.getCount() == 0 ? null : result;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询刷卡工具制作失败", ex);
			return null;
		}
	}

	/**
	 * 分页查询刷卡工具配置单
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<ToolConfigPage> queryServiceToolConfigPage(Map<String, Object> params,Page page) throws HsException {
		BaseParam param = new BaseParam();
		if(params!=null){
			if(params.get("custName")!=null&&!"".equals(params.get("custName"))){
				String platCustName = (String) params.get("custName");
				if (StringUtils.isNotBlank(platCustName)
						&& !"0000".equals(platCustName)) {
					param.setRoleIds(userCenterService.getRoleIds((String) params
							.get("custId")));
				}
			}
			if(params.get("startDate")!=null&&!"".equals(params.get("startDate"))){
				param.setStartDate(params.get("startDate").toString());
			}
			if(params.get("endDate")!=null&&!"".equals(params.get("endDate"))){
				param.setEndDate(params.get("endDate").toString());
			}
			if(params.get("hsResNo")!=null&&!"".equals(params.get("hsResNo"))){
				param.setHsResNo(params.get("hsResNo").toString());
			}
			if(params.get("hsCustName")!=null&&!"".equals(params.get("hsCustName"))){
				param.setHsCustName(params.get("hsCustName").toString());
			}
			if(params.get("type")!=null&&!"".equals(params.get("type"))){
				param.setType(params.get("type").toString());
			}
		}
		try {
			PageData<ToolConfigPage> result = ibSToolMarkService.queryServiceToolConfigPage(param, page);
			return result == null || result.getCount() == 0 ? null : result;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询刷卡工具配置单失败", ex);
			return null;
		}
	}
	/**
	 * 查询工具关联详情
	 * 
	 * @Description:
	 * @param map
	 * @return
	 * @throws HsException
	 */
	@Override
	public List<ToolRelationDetail> findToolRelationList(Map<String, Object> map)
			throws HsException {
		try {
			return ibSToolMarkService.queryDeviceRelevanceDetail(map.get(
					"confNo").toString());
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findToolRelationList",
					"调用BS查询工具关联详情失败", ex);
			return null;
		}
	}

	/**
	 * 添加刷卡工具关联
	 * 
	 * @Description:
	 * @param param
	 * @throws HsException
	 */
	@Override
	public ToolRelationDetail addToolRelation(Map<String, Object> param)
			throws HsException {
		ToolRelationDetail toolRelationDetail = null;
		try {
			toolRelationDetail = ibSToolMarkService.vaildMcrDeviceLawful(param
					.get("confNo").toString(), param.get("deviceSeqNo")
					.toString());
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addToolRelation",
					"调用BS添加刷卡工具关联失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		return toolRelationDetail;
	}

	/**
	 * 批量添加关联序列号
	 * 
	 * @param param
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.toolorder.SwipeCardToolService#addToolBatchRelation(java.util.Map)
	 */
	public void addToolBatchRelation(Map<String, Object> param,
			List<String> listdeviceSeqNos) throws HsException {
		try {
			ibSToolMarkService.batchConfigKsnRelation(param.get("entCustId")
					.toString(), param.get("confNo").toString(),
					listdeviceSeqNos, param.get("operNo").toString());
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addToolRelation",
					"调用BS添加刷卡工具关联失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}

	}

	/**
	 * 查询设备关联详情
	 * 
	 * @param confNo
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.toolorder.SwipeCardToolService#queryDeviceRelevanceDetail(java.lang.String)
	 */
	@Override
	public List<ToolRelationDetail> queryDeviceRelevanceDetail(
			Map<String, Object> param) throws HsException {
		try {
			return ibSToolMarkService.queryDeviceRelevanceDetail(param.get(
					"confNo").toString());
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(),
					"queryDeviceRelevanceDetail", "调用BS查询设备关联详情失败", ex);
			return null;
		}

	}
}
