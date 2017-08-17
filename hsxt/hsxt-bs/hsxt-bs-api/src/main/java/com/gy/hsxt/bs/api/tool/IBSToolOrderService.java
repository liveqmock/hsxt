/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import java.util.List;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tool.*;
import com.gy.hsxt.bs.bean.tool.resultbean.EntResource;
import com.gy.hsxt.bs.bean.tool.resultbean.OrderEnt;
import com.gy.hsxt.bs.bean.tool.resultbean.SpecialCardStyle;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具下单
 * 
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolOrderService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:22:58
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolOrderService {

	/**
	 * 查询可以购买的工具
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:20:15
	 * @param custType
	 *            客户类型
	 * @return
	 * @return : List<ToolProduct>
	 * @version V3.0.0
	 */
	public List<ToolProduct> queryMayBuyToolProduct(Integer custType);

	/**
	 * 根据工具服务查询客户可以购买的工具
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月22日 上午10:23:12
	 * @param custType
	 *            客户类型
	 * @param toolService
	 *            工具服务
	 * @return
	 * @return : List<ToolProduct>
	 * @version V3.0.0
	 */
	public List<ToolProduct> queryMayBuyToolProductByToolSevice(Integer custType, String toolService);

	/**
	 * 查询企业资源段
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月14日 下午7:32:47
	 * @param entCustId
	 *            客户号
	 * @return
	 * @return : EntResource
	 * @version V3.0.0
	 */
	public EntResource queryEntResourceSegment(String entCustId);

	/**
	 * 查询企业资源段(新接口)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/14 18:13
	 * @param entCustId
	 *            客户号
	 * @eturn: EntResSegment
	 * @trows: HsException
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public EntResSegment queryEntResSegmentNew(String entCustId) throws HsException;

	/**
	 * 查询企业的所有个性卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月22日 上午10:59:48
	 * @param entResNo
	 *            互生号
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> queryEntSpecialCardStyle(String entResNo);

	/**
	 * 查询企业的所有卡样(标准 + 个性)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午3:06:18
	 * @param entResNo
	 *            互生号
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> queryEntCardStyleByAll(String entResNo);

	/**
	 * 查询工具类别可以购买数量
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:20:38
	 * @param entCustId
	 *            企业客户号
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public Integer queryMayBuyToolNum(String entCustId, String categoryCode);

	/**
	 * 申购工具下单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月15日 下午7:32:38
	 * @param bean
	 *            工具下单参数实体
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult addToolBuyOrder(OrderBean bean) throws HsException;

	/**
	 * 申购企业资源段下单
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016年06月24日 下午3:32:38
	 * @param bean
	 *            企业资源段下单参数实体
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult addResSegmentBuyOrder(OrderBean bean) throws HsException;

	/**
	 * 定制互生卡样下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月1日 下午5:48:32
	 * @param bean
	 *            订制卡样下单参数实体
	 * @param style
	 *            卡样参数实体
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult addCardStyleFeeOrder(Order bean, CardStyle style) throws HsException;

	/**
	 * 添加平台代购订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:20:49
	 * @param bean
	 *            平台代购参数实体
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult addPlatProxyOrder(ProxyOrder bean) throws HsException;

	/**
	 * 分页平台代购订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月13日 下午12:24:08
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ProxyOrder>
	 * @version V3.0.0
	 */
	public PageData<ProxyOrder> queryPlatProxyOrderPage(BaseParam param, Page page);

	/**
	 * 根据代购订单编号查询代购订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:21:01
	 * @param proxyOrderNo
	 *            平台代购订单号
	 * @return
	 * @return : ProxyOrder
	 * @version V3.0.0
	 */
	public ProxyOrder queryPlatProxyOrderById(String proxyOrderNo);

	/**
	 * 平台代购订单审批
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:21:06
	 * @param bean
	 *            平台代购参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void platProxyOrderAppr(ProxyOrder bean) throws HsException;

	/**
	 * 分页查询平台代购订单记录
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月20日 上午9:50:01
	 * @param param
	 * @param page
	 * @return
	 * @return : PageData<ProxyOrder>
	 * @version V3.0.0
	 */
	public PageData<ProxyOrder> queryPlatProxyOrderRecordPage(BaseParam param, Page page);

	/**
	 * 个人补卡下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月12日 下午2:48:12
	 * @param bean
	 *            个人补卡下单参数实体
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult personMendCardOrder(OrderBean bean) throws HsException;

	/**
	 * 企业重做卡下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月12日 下午2:52:08
	 * @param bean
	 *            企业重做卡参数实体
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult entMakingCardOrder(OrderBean bean) throws HsException;

	/**
	 * 工具订单支付(申购工具,补卡、重做卡、订制卡样)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月17日 下午12:18:44
	 * @param bean
	 *            工具订单支付参数实体
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String buyToolOrderToPay(ToolOrderPay bean) throws HsException;

	/**
	 * 企业分页查询工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 下午4:54:08
	 * @param param
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<OrderEnt>
	 * @version V3.0.0
	 */
	public PageData<OrderEnt> queryToolOrderEntByPage(BaseParam param, Page page);

	/**
	 * 平台分页查询工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月1日 下午7:32:12
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolOrderPage>
	 * @version V3.0.0
	 */
	public PageData<ToolOrderPage> queryToolOrderPlatPage(BaseParam param, Page page);

	/**
	 * 查询订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 下午6:14:04
	 * @param orderNo
	 *            订单号
	 * @return
	 * @return : OrderBean
	 * @version V3.0.0
	 */
	public OrderBean queryOrderDetailByNo(String orderNo);

	/**
	 * 根据订单号和工具类别查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月16日 下午5:24:04
	 * @param orderNo
	 *            订单号
	 * @param categoryCode
	 *            工具类别
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig queryToolConfigByNoAndCode(String orderNo, String categoryCode);

	/**
	 * 分页查询互生个性卡样(平台)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月7日 下午3:56:24
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<SpecialCardStyle>
	 * @version V3.0.0
	 */
	public PageData<SpecialCardStyle> querySpecialCardStylePage(BaseParam param, Page page);

	/**
	 * 根据订单号查询互生卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月2日 上午10:11:53
	 * @param orderNo
	 *            订单号
	 * @return
	 * @return : CardStyle
	 * @version V3.0.0
	 */
	public CardStyle queryCardStyleByOrderNo(String orderNo);

	/**
	 * 修改卡样锁定状态
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月25日 上午11:05:00
	 * @param confNo
	 *            配置单号
	 * @param isLock
	 *            是否锁定
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyCardStyleLockStatus(String confNo, Boolean isLock) throws HsException;

	/**
	 * 上传卡样制作文件(平台)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月14日 下午6:48:09
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void uploadCardStyleMarkFile(CardStyle bean) throws HsException;

	/**
	 * 企业分页查询个性卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月14日 下午2:06:29
	 * @param entResNo
	 *            企业互生号
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<SpecialCardStyle>
	 * @version V3.0.0
	 */
	public PageData<SpecialCardStyle> queryEntSpecialCardStylePage(String entResNo, Page page);

	/**
	 * 企业上传卡样素材(订制卡样)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 上午11:36:15
	 * @param bean
	 *            卡样参数实体
	 * @return
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addSpecialCardStyleEnt(CardStyle bean) throws HsException;

	/**
	 * 企业确认卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月2日 上午10:09:49
	 * @param bean
	 *            卡样参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void entConfirmCardStyle(CardStyle bean) throws HsException;

	/**
	 * 企业上传互生卡制作确认书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月13日 下午8:48:29
	 * @param confNo
	 *            配置单号
	 * @param confirmFile
	 *            确认书文件
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void uploadCardMarkConfirmFile(String confNo, String confirmFile) throws HsException;

	/**
	 * 工具订单确认制作
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 上午10:55:45
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolOrderConfirmMark(String orderNo) throws HsException;

	/**
	 * 工具订单撤单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月16日 下午6:49:07
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	@Deprecated
	public void toolOrderWithdrawals(String orderNo) throws HsException;

	/**
	 * 关闭或撤销工具订单 关闭(未付款之前的订单) 撤单(付款成功的,退款)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:21:12
	 * @param orderNo
	 *            订单号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void closeOrWithdrawalsToolOrder(String orderNo) throws HsException;

	/**
	 * 分页查询个人补卡订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月8日 下午5:01:46
	 * @param param
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return
	 * @return : PageData<Order>
	 * @version V3.0.0
	 */
	public PageData<Order> queryPersonMendCardOrderPage(BaseParam param, Page page);
}
