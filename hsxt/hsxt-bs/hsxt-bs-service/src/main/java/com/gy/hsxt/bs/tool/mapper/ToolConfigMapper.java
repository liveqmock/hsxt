/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;

/**
 * 工具配置单Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ToolConfigMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午5:33:24
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "toolConfigMapper")
public interface ToolConfigMapper {

	/**
	 * 插入配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月5日 下午3:40:19
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertToolConfig(ToolConfig entity);

	/**
	 * 批量插入配置单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:58:18
	 * @param confs
	 * @return : void
	 * @version V3.0.0
	 */
	public void batchInsertToolConfig(@Param("confs") List<ToolConfig> confs);

	/**
	 * 配置单修改
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月20日 上午11:08:31
	 * @param entity
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateToolConfigByConfNo(ToolConfig entity);

	/**
	 * 根据订单号修改配置单状态
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 上午10:08:01
	 * @param orderNo
	 * @param confStatus
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateConfStatusByOrderNo(@Param("orderNo") String orderNo, @Param("confStatus") Integer confStatus);

	/**
	 * 发货单发货时,修改配送单为已发货
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 下午2:30:13
	 * @param confs
	 * @return : void
	 * @version V3.0.0
	 */
	public void updateToolConfigByShipping(@Param("confs") List<ToolConfig> confs);

	/**
	 * 售后服务发货单发货时,修改配送单为已发货
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月16日 上午11:33:43
	 * @param confNo
	 * @param shippingId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateToolConfigByAfterShipping(@Param("confNo") String[] confNo,
			@Param("shippingId") String shippingId);

	/**
	 * 发货单签收成功时,修改配置单为签收成功
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月9日 上午10:17:53
	 * @param shippingId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int updateToolConfigBySign(String shippingId);

	/**
	 * 批量签收发货单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月29日 下午6:01:45
	 * @param shippings
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int batchUpdateToolConfigBySign(List<String> shippings);

	/**
	 * 根据id查询配置单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午5:59:56
	 * @param confNo
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig selectToolConfigById(String confNo);

	/**
	 * 根据配置单编号和订单类型查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月2日 上午10:45:46
	 * @param confNo
	 * @param orderType
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig selectToolConfigByIdAndType(@Param("confNo") String confNo,
			@Param("orderType") String[] orderType);

	/**
	 * 根据id查询同状态配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月22日 下午2:02:12
	 * @param confNo
	 * @param confStatus
	 * @return
	 * @return : List<ToolConfig>
	 * @version V3.0.0
	 */
	public List<ToolConfig> selectToolConfigByIds(@Param("confNos") String[] confNos,
			@Param("confStatus") Integer confStatus);

	/**
	 * 根据发货单号查询配置单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:02:22
	 * @param shippingId
	 * @return
	 * @return : List<ToolConfig>
	 * @version V3.0.0
	 */
	public List<ToolConfig> selectToolConfigByShippingId(String shippingId);

	/**
	 * 根据订单号查询配置单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:02:35
	 * @param orderNo
	 * @return
	 * @return : List<ToolConfig>
	 * @version V3.0.0
	 */
	public List<ToolConfig> selectToolConfigByOrderNo(String orderNo);

	/**
	 * 分页查询工具制作配置单(刷卡工具)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月14日 下午7:18:19
	 * @param bean
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectServiceToolMarkListPage(BaseParam bean);

	/**
	 * 分页查询的灌秘钥工具订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月16日 下午4:46:02
	 * @param bean
	 * @return
	 * @return : List<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public List<SecretKeyOrderInfoPage> selectSecretKeyOrderListPage(BaseParam bean);

	/**
	 * 根据条件查询 灌秘钥工具订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月16日 下午5:27:03
	 * @param entCustId
	 * @param confNo
	 * @return
	 * @return : List<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public List<SecretKeyOrderInfoPage> selectSecretKeyOrderByIf(@Param("entCustId") String entCustId,
			@Param("confNo") String confNo);

	/**
	 * 查询刷卡器工具订单
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月16日 下午7:11:01
	 * @param entCustId
	 * @param confNo
	 * @return
	 * @return : List<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public List<SecretKeyOrderInfoPage> selectMcrOrderByIf(@Param("entCustId") String entCustId,
			@Param("confNo") String confNo);

	/**
	 * 分页查询工具制作配置单(互生卡)
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 上午10:04:02
	 * @param bean
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectToolConfigMarkCardListPage(BaseParam bean);

	/**
	 * 分页查询工具配送配送单(生成发货单)--新增申购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:07:31
	 * @param :
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectToolConfigShippingAddListPage(BaseParam entity);

	/**
	 * 分页查询工具配送配送单(生成发货单)--申报申购
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月30日 下午6:15:09
	 * @param :
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectToolConfigShippingAppListPage(BaseParam entity);

	/**
	 * 根据设备序列号查询配置详情(领用和报损除外)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月28日 上午10:32:28
	 * @param deviceSeqNo
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig selectDeviceConfigDetail(String deviceSeqNo);

	/**
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月3日 上午10:37:41
	 * @param entity
	 * @return
	 * @return : List<ToolConfig>
	 * @version V3.0.0
	 */
	public List<ToolConfig> selectToolConfigListPage(BaseParam entity);

	/**
	 * 查询未完成的配置单不包含(已发货,已签收,已取消)
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月1日 上午9:57:42
	 * @param hsCustId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int selectNoFinishConfig(@Param("hsCustId") String hsCustId);

	/**
	 * 根据订单号和工具类别查询配置单
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月16日 下午5:25:27
	 * @param orderNo
	 * @param categoryCode
	 * @return
	 * @return : ToolConfig
	 * @version V3.0.0
	 */
	public ToolConfig selecctToolConfigByNoAndCode(@Param("orderNo") String orderNo,
			@Param("categoryCode") String categoryCode);

	/**
	 * 查询已确认制作的卡数量
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月23日 上午11:40:40
	 * @param entCustId
	 * @return
	 * @return : int
	 * @version V3.0.0
	 */
	public int selectConfirmMardCardCount(@Param("entCustId") String entCustId);


	/**
	 * 分页查询刷卡工具配置单(刷卡工具)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016年06月54日 上午10:16:19
	 * @param bean
	 * @return
	 * @return : List<ToolConfigPage>
	 * @version V3.0.0
	 */
	public List<ToolConfigPage> selectServiceToolConfigListPage(BaseParam bean);

	/**
	 * 判断配置单是否可以转仓库
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/27 16:05
	 * @param:  confNo
	 * @rturn: int
	 * @comany: gyist
	 * @version V3.0.0
	 */
	public int toolConfigIsUpdateWh(@Param("confNo") String confNo);
}
