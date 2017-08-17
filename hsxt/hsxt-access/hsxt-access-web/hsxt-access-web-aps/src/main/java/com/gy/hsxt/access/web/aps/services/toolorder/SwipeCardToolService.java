/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolRelationDetail;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
public interface SwipeCardToolService extends IBaseService {

	/**
	 * 查询终端编号列表
	 * 
	 * @param map
	 *            条件
	 * @param page
	 *            分页对象
	 * @return
	 */
	public List<ToolRelationDetail> findToolRelationList(Map<String, Object> map)
			throws HsException;

	/**
	 * 添加关联序列号之前的验证
	 * 
	 * @param param
	 * @throws HsException
	 */
	public ToolRelationDetail addToolRelation(Map<String, Object> param)
			throws HsException;

	/**
	 * 批量添加关联序列号
	 * 
	 * @param param
	 * @throws HsException
	 */
	public void addToolBatchRelation(Map<String, Object> param,
			List<String> listdeviceSeqNos) throws HsException;

	/**
	 * 查询询设备关联详情
	 * 
	 * @param param
	 * @throws HsException
	 */
	public List<ToolRelationDetail> queryDeviceRelevanceDetail(
			Map<String, Object> map) throws HsException;

	/**
	 * 分页查询刷卡工具配置单(申报新增)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<ToolConfigPage> queryServiceToolConfigPage(Map<String, Object> param, Page page) throws HsException;
}
