/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.bs.bean.tool.*;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.resultbean.EntResource;
import com.gy.hsxt.bs.bean.tool.resultbean.OrderEnt;
import com.gy.hsxt.bs.bean.tool.resultbean.SpecialCardStyle;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderResult;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.device.AsDevice;

/**
 * 工具管理service
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness
 * @ClassName: IToolManageService
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-13 下午5:14:19
 * @version V3.0.0
 */

@SuppressWarnings("rawtypes")
@Service
public interface IToolManageService extends IBaseService {

	/**
	 * 根据客户类型查询可购买的工具
	 * 
	 * @param custType
	 *            客户类型
	 * @param toolServie
	 *            工具服务类型 见com.gy.hsxt.common.constant.CustType
	 * @return List
	 * @throws HsException
	 */
	public List<ToolProduct> findMayBuyTools(Integer custType, String toolServie) throws HsException;

	/**
	 * 新增收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:17:05
	 * @param entCustId
	 * @param addr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addDeliverAddress(String entCustId, String addr) throws HsException;

	/**
	 * 修改收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:12:29
	 * @param addr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyDeliverAddress(String entCustId, String addr) throws HsException;

	/**
	 * 查询收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:12:33
	 * @param addr
	 * @return
	 * @throws HsException
	 * @return : AsReceiveAddr
	 * @version V3.0.0
	 */
	public AsReceiveAddr queryDeliverAddress(String entCustId, Long addrId) throws HsException;

	/**
	 * 删除收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 下午4:12:36
	 * @param addr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void removeDeliverAddress(String entCustId, Long addrId) throws HsException;

	/**
	 * 查询企业收货地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月8日 下午12:08:55
	 * @param entCustId
	 * @return
	 * @throws HsException
	 * @return : JSONArray
	 * @version V3.0.0
	 */
	public JSONArray findEntDeliverAddress(String entCustId) throws HsException;

	/**
	 * 查询某企业客户可以购买某种工具产品的数量
	 * 
	 * @param custId
	 *            企业客户id
	 * @param toolType
	 *            要买的产品类型
	 * @return 可以购买的数量
	 * @throws HsException
	 */
	public int findMayToolNum(String custId, String toolType) throws HsException;

	/**
	 * 提交申购订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月9日 下午12:22:26
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            客户名称
	 * @param custId
	 *            操作员客户号
	 * @param confs
	 *            配置单列表
	 * @param addr
	 *            收货地址
	 * @param operName
	 *            创建人（系统当前登录人）姓名
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult commitToolOrder(String entCustId, String custEntName, String custId, String confs,
			String addr, String operName, String orderOperator) throws HsException;

	/**
	 * 系统资源购买下单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午5:04:07
	 * @param entCustId
	 *            客户号
	 * @param custEntName
	 *            客户名称
	 * @param custId
	 *            操作员客户号
	 * @param product
	 *            工具
	 * @param addr
	 *            收货地址
	 * @param segmentId
	 *            资源段Id
	 * @param operName
	 *            创建人（系统当前登录人）姓名
	 * @return
	 * @throws HsException
	 * @return : ToolOrderResult
	 * @version V3.0.0
	 */
	public ToolOrderResult commitToolOrderCard(String entCustId, String custEntName, String custId, String product,
			String addr, String segmentId, String operName) throws HsException;

	/**
	 * 工具订单支付
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午3:14:38
	 * @param entCustId
	 * @param tradePwd
	 * @param randomToken
	 * @param bean
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String toolOrderPayment(String entCustId, String tradePwd, String randomToken, ToolOrderPay bean,
			String orderAmount) throws HsException;

	/**
	 * 分页查询企业工具订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午4:03:36
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<OrderEnt>
	 * @version V3.0.0
	 */
	public PageData<OrderEnt> queryToolOrderList(Map filterMap, Map sortMap, Page page);

	/**
	 * 查询工具订单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:03:47
	 * @param orderNo
	 * @return
	 * @return : OrderBean
	 * @version V3.0.0
	 */
	public OrderBean queryToolOrderDetail(String orderNo);

	/**
	 * 根据订单号查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月16日 下午6:06:33
	 * @param orderNo
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig queryToolConfigByNo(String orderNo);

	/**
	 * 工具订单撤单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午5:10:36
	 * @param orderNo
	 * @return : void
	 * @version V3.0.0
	 */
	public void toolOrderCancel(String orderNo) throws HsException;

	/**
	 * 分页查询企业终端设备
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月11日 下午8:34:59
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<AsDevice>
	 * @version V3.0.0
	 */
	public PageData<AsDevice> queryEntTerminalList(Map filterMap, Map sortMap, Page page) throws HsException;

	/**
	 * 修改设备状态
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 上午9:33:31
	 * @param entResNo
	 * @param deviceType
	 * @param deviceNo
	 * @param deviceStatus
	 * @param optCustId
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void updateDeviceStatus(String entResNo, Integer deviceType, String deviceNo, Integer deviceStatus,
			String optCustId) throws HsException;

	/**
	 * 分页查询订制卡样服务
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月13日 下午7:37:04
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<SpecialCardStyle>
	 * @version V3.0.0
	 */
	public PageData<SpecialCardStyle> querySpecialCardStyleList(Map filterMap, Map sortMap, Page page);

	/**
	 * 查询企业个性卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月16日 下午6:20:54
	 * @param entResNo
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> entSpecialCardStyle(String entResNo);

	/**
	 * 定制卡样服务订单提交
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午10:05:40
	 * @param bean
	 * @param cardStyleName
	 * @param sourceFile
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public ToolOrderResult submitSpecialCardStyleOrder(CompanyBase bean, String cardStyleName, String sourceFile,
			String remark, String operName) throws HsException;

	/**
	 * 上传卡样素材
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午10:08:18
	 * @param orderNo
	 * @param bean
	 * @param microPic
	 * @param sourceFile
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addSpecialCardStyle(String orderNo, CompanyBase bean, String microPic, String sourceFile, String remark)
			throws HsException;

	/**
	 * 确认卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午10:19:53
	 * @param orderNo
	 * @param custId
	 * @param remark
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void confirmCardStyle(String orderNo, String custId, String remark) throws HsException;

	/**
	 * 上传卡制作卡样确认书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月18日 上午10:20:32
	 * @param confNo
	 * @param confirmFile
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void uploadCardMarkConfirmFile(String confNo, String confirmFile) throws HsException;

	/**
	 * 查询企业可以购买的资源段
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月14日 下午6:14:27
	 * @param entCustId
	 * @return
	 * @return : EntResource
	 * @version V3.0.0
	 */
	public EntResource queryEntResourceSegment(String entCustId);

	/**
	 * 查询企业可以购买的资源段(新)
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年6月24日 下午4:14:27
	 * @param entCustId
	 * @return
	 * @return : EntResource
	 * @version V3.0.0
	 */
	public EntResSegment queryEntResourceSegmentNew(String entCustId);

	/**
	 * 获取企业可以选择的卡样
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月15日 下午3:13:49
	 * @param entCustId
	 * @return
	 * @return : List<CardStyle>
	 * @version V3.0.0
	 */
	public List<CardStyle> queryEntCardStyleByAll(String entCustId);

}
