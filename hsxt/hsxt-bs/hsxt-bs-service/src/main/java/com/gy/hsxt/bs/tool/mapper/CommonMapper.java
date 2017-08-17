/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import com.gy.hsxt.bs.tool.bean.ToolBatch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.DeliverInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.resultbean.CardMarkData;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.EntInfo;
import com.gy.hsxt.bs.bean.tool.resultbean.OrderEnt;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;

/**
 * 公用的Mapper
 * 
 * @Package: com.gy.hsxt.bs.tool.mapper
 * @ClassName: CommonMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年10月19日 下午3:31:40
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "commonMapper")
public interface CommonMapper {

	/**
	 * 卡制作数据
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月20日 上午9:14:09
	 * @param orderNo
	 * @return
	 * @return : CardMarkData
	 * @version V3.0.0
	 */
	public CardMarkData selectCardMarkData(String orderNo);

	/**
	 * 验证配置单的类型是否一致
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月16日 上午11:02:15
	 * @param confNo
	 * @param confType
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int vaildToolConfigType(@Param("confNo") String[] confNo, @Param("confType") Integer confType);

	/**
	 * 验证配置单是否是生成同一个企业的发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月21日 上午10:20:35
	 * @param confNo
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	public List<String> vaildShippingIsSameEnt(String[] confNo);

	/**
	 * 验证配置单生成的发货单的地址是否一致
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 上午10:10:03
	 * @param confNo
	 * @return
	 * @return : List<DeliverInfo>
	 * @version V3.0.0
	 */
	public List<DeliverInfo> vaildShippingIsSameAddr(String[] confNo);

	/**
	 * 验证申报申购是否同一个服务公司下的企业
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 上午10:37:59
	 * @param confNo
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public List<String> vaildShippingIsSameEntApp(String[] confNo);

	/**
	 * 使用设备查询设备清单(报损和领用)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 上午11:36:20
	 * @param deviceSeqNo
	 * @return
	 * @return : DeviceDetail
	 * @version V3.0.0
	 */
	public DeviceDetail selectDeviceDetailByNo(String deviceSeqNo);

	/**
	 * 企业分页查询业务订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 下午4:57:01
	 * @param param
	 * @return
	 * @return : List<OrderEnt>
	 * @version V3.0.0
	 */
	public List<OrderEnt> selectOrderEntByListPage(BaseParam param);

	/**
	 * 平台分页查询工具订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月14日 下午5:31:02
	 * @param param
	 * @return
	 * @return : List<ToolOrderPage>
	 * @version V3.0.0
	 */
	public List<ToolOrderPage> selectToolOrderPlatListPage(BaseParam param);

	/**
	 * 修改支付超时的订单失效
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月15日 上午10:21:02
	 * @param orderType
	 * @return : void
	 * @version V3.0.0
	 */
	public void updateOrderStatusByPayOverTime(@Param("orderType") String[] orderType);

	/**
	 * 修改订单已发货
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/16 14:34
	 * @eturn:
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public int updateOrderSended();

	/**
	 * 修改订单已完成
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月29日 下午6:21:34
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateOrderFinish();

	/**
	 * 删除定制卡样费订单失效的卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月15日 上午10:32:30
	 * @param orderType
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int deleteCardStyleByOrderPayOverTime(@Param("orderType") String orderType);

	/**
	 * 修改配置单支付超时
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 下午7:49:40
	 * @param orderType
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateToolConfigByPayOverTime(@Param("orderType") String[] orderType);

	/**
	 * 查询未完成的工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月5日 下午3:23:20
	 * @param entCustId
	 * @param orderType
	 * @return
	 * @return : List<Order>
	 * @version V3.0.0
	 */
	public List<Order> selectNotFinishToolOrder(@Param("entCustId") String entCustId,
			@Param("orderType") String[] orderType);

	/**
	 * 分页查询查询个人补卡订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月8日 下午5:03:32
	 * @param param
	 * @return
	 * @return : List<Order>
	 * @version V3.0.0
	 */
	public List<Order> selectPersonMendCardOrderListPage(BaseParam param);

	/**
	 * 查询申报发货单包含企业
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月13日 上午10:55:57
	 * @param shippingId
	 * @return
	 * @return : List<EntInfo>
	 * @version V3.0.0
	 */
	public List<EntInfo> selectApplyShippingHavEnt(@Param("shippingId") String shippingId);

	/**
	 * 工具执行跑批
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/16 15:46
	 * @param: entity
	 * @eturn: void
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public void toolExecuteBatch(ToolBatch entity);

	/**
	 * 修改资源段支付超时
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/27 9:25
	 * @comany: gyist
	 * @version V3.0.0
	 */
	public void updateResSegmentByPayOverTime();
}
