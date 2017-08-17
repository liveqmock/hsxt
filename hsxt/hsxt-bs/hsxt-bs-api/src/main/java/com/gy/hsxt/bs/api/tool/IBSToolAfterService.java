/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import java.util.List;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterKeepConfig;
import com.gy.hsxt.bs.bean.tool.AfterService;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;
import com.gy.hsxt.bs.bean.tool.ImportAfterService;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.bs.bean.tool.ShippingData;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.EntDeviceInfo;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolOrderPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolShippingPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具售后服务API接口
 * 
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolAfterService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:21:53
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolAfterService {

	/**
	 * 分页查询售后订单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月22日 下午5:36:20 查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolOrderPage>
	 * @version V3.0.0
	 */
	public PageData<ToolOrderPage> queryAfterOrderPlatPage(BaseParam param, Page page);

	/**
	 * 验证批量导入设备
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月19日 下午3:58:40
	 * @param seqNos
	 *            设备序列号列表
	 * @return
	 * @throws HsException
	 * @return : List<ImportAfterService>
	 * @version V3.0.0
	 */
	public List<ImportAfterService> validBatchImportSeqNo(List<String> seqNos) throws HsException;

	/**
	 * 分页查询企业设备信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月27日 下午12:00:47
	 * @param seqNo
	 *            设备序列号
	 * @param entResNo
	 *            企业互生号
	 * @param page
	 *            分页参数
	 * @return
	 * @return : PageData<EntDeviceInfo>
	 * @version V3.0.0
	 */
	public PageData<EntDeviceInfo> queryEntDeviceInfoPage(String seqNo, String entResNo, Page page);

	/**
	 * 增加售后服务
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:21:38
	 * @param bean
	 *            售后参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addAfterService(AfterService bean) throws HsException;

	/**
	 * 批量导入售后
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:02
	 * @param beans
	 *            导入售后服务参数列表
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchImportAfterService(List<ImportAfterService> beans) throws HsException;

	/**
	 * 根据编号查询售后单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:10
	 * @param afterOrderNo
	 *            售后服务订单号
	 * @return
	 * @return : AfterService
	 * @version V3.0.0
	 */
	public AfterService queryAfterServiceByNo(String afterOrderNo);

	/**
	 * 分页查询售后单审批
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:17
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<AfterService>
	 * @version V3.0.0
	 */
	public PageData<AfterService> queryAfterOrderApprPage(BaseParam bean, Page page);

	/**
	 * 审批售后单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:25
	 * @param bean
	 *            售后服务参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void apprAfterService(AfterService bean) throws HsException;

	/**
	 * 分页查询售后刷卡工具制作配置单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:32
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolAfterConfigPage(BaseParam bean, Page page);

	/**
	 * 查询售后配置单详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午4:33:43
	 * @param confNo
	 *            配置单号
	 * @return
	 * @return : List<AfterServiceDetail>
	 * @version V3.0.0
	 */
	public List<AfterServiceDetail> queryAfterConfigDetail(String confNo);

	/**
	 * 售后保持关联
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月22日 上午10:36:13
	 * @param bean
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void keepDeviceRelationAfter(AfterKeepConfig bean) throws HsException;

	/**
	 * 配置刷卡器关联售后
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 上午10:13:36
	 * @param bean
	 *            售后服务配置参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void configMcrRelationAfter(AfterServiceConfig bean) throws HsException;

	/**
	 * 验证灌秘钥设备售后(POS机、平板)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月12日 上午10:46:21
	 * @param deviceSeqNo
	 *            设备序列号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void vaildSecretKeyDeviceAfter(String deviceSeqNo) throws HsException;

	/**
	 * 配置秘钥设备售后(POS机、平板)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 上午9:36:21
	 * @param bean
	 *            售后服务配置参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void configSecretKeyDeviceAfter(AfterServiceConfig bean) throws HsException;

	/**
	 * 分页查询秘钥配置--售后灌秘钥
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:22:51
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public PageData<SecretKeyOrderInfoPage> queryAfterSecretKeyConfigByPage(BaseParam bean, Page page);

	/**
	 * 查询售后秘钥设备配置清单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月7日 下午2:02:39
	 * @param confNo
	 *            配置单号
	 * @return
	 * @return : List<AfterDeviceDetail>
	 * @version V3.0.0
	 */
	public List<AfterDeviceDetail> queryAfterSecretKeyConfigDetail(String confNo);

	/**
	 * 分页查询售后工具配送配送单(生成发货单)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:23:12
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolConfigShippingAfterPage(BaseParam bean, Page page);

	/**
	 * 查询发货单的数据(页面显示)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月6日 下午2:57:38
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 * @return : ShippingData
	 * @version V3.0.0
	 */
	public ShippingData queryAfterShipingData(String[] confNo) throws HsException;

	/**
	 * 添加售后服务发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月16日 上午11:24:23
	 * @param bean
	 *            发货单参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addToolShippingAfter(Shipping bean) throws HsException;

	/**
	 * 分页查询售后发货单(打印发货单)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:23:23
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolShippingPage>
	 * @version V3.0.0
	 */
	public PageData<ToolShippingPage> queryShippingAfterPage(BaseParam bean, Page page);

	/**
	 * 根据id查询售后发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午2:25:31
	 * @param shippingId
	 *            发货单id
	 * @return
	 * @return : Shipping
	 * @version V3.0.0
	 */
	public Shipping queryShippingAfterById(String shippingId);
}
