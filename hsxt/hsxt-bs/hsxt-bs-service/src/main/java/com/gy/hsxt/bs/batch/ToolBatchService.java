/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.tool.mapper.CommonMapper;
import com.gy.hsxt.bs.tool.mapper.ShippingMapper;
import com.gy.hsxt.bs.tool.mapper.ToolConfigMapper;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工具定时任务(每天跑)
 * 
 * @Package: com.gy.hsxt.bs.batch
 * @ClassName: EverydayBatchService
 * @Description: 工具订单定时失效、签收、订单完成
 * @author: likui
 * @date: 2015年10月29日 下午4:59:18
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class ToolBatchService extends AbstractBatchService {

	/** 公共Mapper **/
	@Resource
	private CommonMapper commonMapper;

	/** 发货单Mapper **/
	@Resource
	private ShippingMapper shippingMapper;

	/** 工具配置单Mapper **/
	@Resource
	private ToolConfigMapper toolConfigMapper;

	/**
	 * 业务执行方法
	 *
	 * @param executeId
	 *            任务执行编号
	 * @param scanDate
	 *            扫描日期
	 * @throws Exception
	 */
	@Override
	public void execute(String executeId, String scanDate) throws Exception
	{
		try
		{
			// 修改订单超过支付时间的配置单无效
			commonMapper.updateToolConfigByPayOverTime(new String[]
			{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(),
					OrderType.APPLY_PERSON_RESOURCE.getCode() });

			// 修改资源段支付超时未待购买
			commonMapper.updateResSegmentByPayOverTime();

			// 删除定制卡样费订单失效的卡样
			commonMapper.deleteCardStyleByOrderPayOverTime(OrderType.CARD_STYLE_FEE.getCode());

			// 修改待付款的工具订单超过支付时间为失效
			commonMapper.updateOrderStatusByPayOverTime(new String[]
			{ OrderType.BUY_TOOL.getCode(), OrderType.REMAKE_MY_CARD.getCode(), OrderType.CARD_STYLE_FEE.getCode(),
					OrderType.APPLY_PERSON_RESOURCE.getCode() });

			// 签收发货单和配置单
			List<String> shippings = shippingMapper
					.selectShippingAutoSign(DateUtil.addDaysStr(null, -this.getBsConfig().getToolShippingSignDays()));
			if (null != shippings && shippings.size() > 0)
			{
				shippingMapper.batchSignShipping(shippings);
				toolConfigMapper.batchUpdateToolConfigBySign(shippings);
			}

			// 订单中配置单全部发货后修改订单为已发货
			commonMapper.updateOrderSended();

			// 订单中配置单全部签收后修改订单为已完成
			commonMapper.updateOrderFinish();
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "toolOrder", "工具定时任务失败", ex);
		}
		this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
	}
}
