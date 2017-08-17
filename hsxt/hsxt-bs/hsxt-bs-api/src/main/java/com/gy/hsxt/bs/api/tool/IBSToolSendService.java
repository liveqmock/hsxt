/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.DeliveryCorp;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.bs.bean.tool.ShippingMethod;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具发货API
 * 
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolSendService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:23:33
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolSendService {

	/**
	 * 添加配送方式
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:23:19
	 * @param bean
	 *            配送方式参数实体
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addShippingMethod(ShippingMethod bean) throws HsException;

	/**
	 * 修改配送方式
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:23:25
	 * @param bean
	 *            配送方式
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyShippingMethod(ShippingMethod bean) throws HsException;

	/**
	 * 删除配送方式
	 * 
	 * @Desc:
	 * @author: likui
	 * @created:2015年9月29日 下午5:23:36
	 * @param smId
	 *            配送方式id
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeShippingMethod(String smId) throws HsException;

	/**
	 * 分页查询配送方式
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 上午9:09:54
	 * @param smName
	 *            配送方式名称
	 * @param page
	 *            分页参数实体
	 * @param :
	 * @return : PageData<ShippingMethod>
	 * @version V3.0.0
	 */
	public PageData<ShippingMethod> queryShippingMethodByPage(String smName, Page page);

	/**
	 * 根据id查询配送方式详情
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:23:46
	 * @param smId
	 *            配送方式id
	 * @param
	 * @return : ShippingMethod
	 * @version V3.0.0
	 */
	public ShippingMethod queryShippingMethodById(String smId);

	/**
	 * 添加物流公司
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:23:52
	 * @param bean
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addDeliveryCorp(DeliveryCorp bean) throws HsException;

	/**
	 * 修改快递公司
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:23:58
	 * @param bean
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyDeliveryCorp(DeliveryCorp bean) throws HsException;

	/**
	 * 删除快递公司
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:24:03
	 * @param dcId
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeDeliveryCorp(String dcId) throws HsException;

	/**
	 * 分页查询快递公司
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 上午9:27:19
	 * @param corpName
	 * @param page
	 * @param :
	 * @return : PageData<DeliveryCorp>
	 * @version V3.0.0
	 */
	public PageData<DeliveryCorp> queryDeliveryCorpByPage(String corpName, Page page);

	/**
	 * 根据id查询快递公司详情
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:24:13
	 * @param dcId
	 * @param :
	 * @return : DeliveryCorp
	 * @version V3.0.0
	 */
	public DeliveryCorp queryDeliveryCorpById(String dcId);

	/**
	 * 分页查询工具配置单(生成发货单)--新增申购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月21日 上午9:15:47
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolConfigDeliveryPage(BaseParam bean, Page page);

	/**
	 * 查询发货单的数据(页面显示)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月21日 下午12:05:51
	 * @param orderType
	 *            订单类型
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 * @return : ShippingData
	 * @version V3.0.0
	 */
	public ShippingData queryShipingData(String orderType, String[] confNo) throws HsException;

	/**
	 * 查询入库的免费工具
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 上午11:40:22
	 * @param productId
	 *            工具产品id
	 * @param whId
	 *            仓库id
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String queryToolEnterByForFree(String productId, String whId);

	/**
	 * 分页查询工具配送单(生成发货单)申报申购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月21日 上午9:16:14
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolConfigDeliveryAppPage(BaseParam bean, Page page);

	/**
	 * 添加发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月22日 下午3:39:53
	 * @param bean
	 *            发货单参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addToolShipping(Shipping bean) throws HsException;

	/**
	 * 根据id查询发货单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:24:45
	 * @param shippingId
	 *            发货单id
	 * @param :
	 * @return : Shipping
	 * @version V3.0.0
	 */
	public Shipping queryShippingById(String shippingId);

	/**
	 * 分页查询配送单(打印配送单)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 上午9:46:10
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolShippingPage>
	 * @version V3.0.0
	 */
	public PageData<ToolShippingPage> queryShippingPage(BaseParam bean, Page page);

	/**
	 * 发货单工具签收(个人/企业)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:24:57
	 * @param bean
	 *            发货单参数实体
	 * @param HsException
	 * @return : void
	 * @version V3.0.0
	 */
	@Deprecated
	public void toolSignAccept(Shipping bean) throws HsException;
}
