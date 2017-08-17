/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import java.util.List;

import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.CardInOut;
import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.bs.bean.tool.CardMarkConfig;
import com.gy.hsxt.bs.bean.tool.ConsumeKSN;
import com.gy.hsxt.bs.bean.tool.KsnExportRecord;
import com.gy.hsxt.bs.bean.tool.McrKsnRecord;
import com.gy.hsxt.bs.bean.tool.PointKSN;
import com.gy.hsxt.bs.bean.tool.resultbean.CardMarkData;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceTerminalNo;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolRelationDetail;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具制作API
 *
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolMarkService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:22:37
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolMarkService {

	/**
	 * 分页查询刷卡器KSN生成记录
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月23日 下午6:48:32
	 * @param storeStatus
	 *            库存状态
	 * @param mcrType
	 *            刷卡器类型
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<McrKsnRecord>
	 * @version V3.0.0
	 */
	public PageData<McrKsnRecord> queryMcrKsnRecordPage(Integer storeStatus, Integer mcrType, Page page);

	/**
	 * 生成积分KSN信息
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:57
	 * @param number
	 *            数量
	 * @param operNo
	 *            操作员编号
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String createPointKSNInfo(Integer number, String operNo) throws HsException;

	/**
	 * 导出积分KSN信息
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:51
	 * @param bean
	 *            导出记录实体
	 * @return
	 * @throws HsException
	 * @return : List<PointKSN>
	 * @version V3.0.0
	 */
	public List<PointKSN> exportPointKSNInfo(KsnExportRecord bean) throws HsException;

	/**
	 * 查询积分ksn信息
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月15日 上午9:17:42
	 * @param batchNo
	 *            批次号
	 * @return
	 * @return : List<PointKSN>
	 * @version V3.0.0
	 */
	public List<PointKSN> queryPointKSNInfo(String batchNo);

	/**
	 * 生成消费KSN信息
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:45
	 * @param number
	 *            数量
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void createConsumeKSNInfo(Integer number, String operNo) throws HsException;

	/**
	 * 导入消费刷卡器KSN数据
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:40
	 * @param beans
	 *            消费刷卡器参数实体
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void importConsumeKSNInfo(List<ConsumeKSN> beans, String operNo) throws HsException;

	/**
	 * 导出消费KSN信息
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:35
	 * @param bean
	 *            导出记录实体
	 * @return
	 * @throws HsException
	 * @return : List<ConsumeKSN>
	 * @version V3.0.0
	 */
	public List<ConsumeKSN> exportConsumeKSNInfo(KsnExportRecord bean) throws HsException;

	/**
	 * 查询消费ksn信息
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月15日 上午9:18:34
	 * @param batchNo
	 *            批次号
	 * @return
	 * @throws HsException
	 * @return : List<ConsumeKSN>
	 * @version V3.0.0
	 */
	public List<ConsumeKSN> queryConsumeKSNInfo(String batchNo);

	/**
	 * 查询刷卡器导出记录
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月12日 下午4:16:03
	 * @param batchNo
	 *            批次号
	 * @return
	 * @return : List<KsnExportRecord>
	 * @version V3.0.0
	 */
	public List<KsnExportRecord> queryKsnExportRecord(String batchNo);

	/**
	 * 分页查询刷卡工具制作配置单(申报新增)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:23
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolConfigMarkPage(BaseParam bean, Page page);

	/**
	 * 查询设备关联详情
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:17
	 * @param confNo
	 *            配置单号
	 * @return
	 * @return : List<ToolRelationDetail>
	 * @version V3.0.0
	 */
	public List<ToolRelationDetail> queryDeviceRelevanceDetail(String confNo);

	/**
	 * 分页查询刷卡工具配置单(申报新增)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2016年06月24日 上午10:12:23
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryServiceToolConfigPage(BaseParam bean, Page page);

	/**
	 * 分页查询秘钥配置(申报新增)--灌秘钥
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:36:01
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<SecretKeyOrderInfoPage>
	 * @version V3.0.0
	 */
	public PageData<SecretKeyOrderInfoPage> querySecretKeyConfigByPage(BaseParam bean, Page page);

	/**
	 * 验证设备信息合法(POS机、平板)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月16日 下午2:12:56
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return : void
	 * @version V3.0.0
	 */
	public void vaildDeviceInfoLawful(String deviceSeqNo) throws HsException;

	/**
	 * 查询设备的终端编号列表(POS机、平板)
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年10月29日 下午12:06:27
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param categoryCode
	 *            工具类别代码
	 * @return
	 * @return : DeviceTerminalNo
	 * @version V3.0.0
	 */
	public DeviceTerminalNo queryConifgDeviceTerminalNo(String entCustId, String confNo, String categoryCode);

	/**
	 * 配置POS机
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午2:35:49
	 * @param entCustId
	 *            企业互生号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String configToolPos(String entCustId, String confNo, String deviceSeqNo) throws HsException;

	/**
	 * 配置设备关联已使用
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月3日 下午5:18:50
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param terminalNo
	 *            终端编号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void configToolDeviceIsUsed(String entCustId, String confNo, String terminalNo, String operNo)
			throws HsException;

	/**
	 * 配置刷卡器关联
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:18:22
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void configKsnRelation(String entCustId, String confNo, String deviceSeqNo, String operNo)
			throws HsException;

	/**
	 * 验证刷卡器设备是否合法
     *
	 * @Desc:
	 * @author: likui
	 * @created: 2016/6/15 9:39
	 * @param: confNo
	 * 				配置单编号
	 * @param: deviceSeqNo
	 * 				设备序列号
	 * @eturn: ToolRelationDetail
	 * @trows: HsException
	 * @copany: gyist
	 * @version V3.0.0
	 */
	public ToolRelationDetail vaildMcrDeviceLawful(String confNo, String deviceSeqNo)throws HsException;

	/**
	 * @Desc:
	 * @author: likui
	 * @created: 2016/7/5 12:22
	 * @param: confNo
	 * 				配置单编号
	 * @param: deviceSeqNo
	 * 				设备序列号
	 * @eturn: ToolRelationDetail
	 * @trows: HsException
	 * @comany: gyist
	 * @version V3.0.0
	 */
	public ToolRelationDetail vaildDeviceLawfulNew(String confNo, String deviceSeqNo)throws HsException;

    /**
     * 批量配置刷卡器关联
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/15 9:55
     * @param entCustId
     *            企业客户号
     * @param confNo
     *            配置单号
     * @param deviceSeqNos
     *            设备序列号
     * @param operNo
     *            操作员编号
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void batchConfigKsnRelation(String entCustId, String confNo, List<String> deviceSeqNos, String operNo)
            throws HsException;

	/**
	 * 配置平板
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月4日 上午11:28:49
	 * @param entCustId
	 *            企业客户号
	 * @param confNo
	 *            配置单号
	 * @param deviceSeqNo
	 *            设备序列号
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String configToolTablet(String entCustId, String confNo, String deviceSeqNo) throws HsException;

	/**
	 * 分页查询互生卡制作配置单
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 上午10:03:00
	 * @param bean
	 *            查询条件参数实体
	 * @param page
	 *            分页参数实体
	 * @return
	 * @return : PageData<ToolConfigPage>
	 * @version V3.0.0
	 */
	public PageData<ToolConfigPage> queryToolConfigMarkCardPage(BaseParam bean, Page page);

	/**
	 * 互生卡开卡
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午4:50:41
	 * @param confNo
	 *            配置单号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void openCard(String confNo, String operNo) throws HsException;

	/**
	 * 重做互生卡开卡(补卡、重做卡)
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月12日 下午3:37:31
	 * @param confNo
	 *            配置单号
	 * @param orderType
	 *            订单类型
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void remarkOpenCard(String confNo, String orderType, String operNo) throws HsException;

	/**
	 * 导出初始化密码
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:19:11
	 * @param confNo
	 *            配置单号
	 * @return
	 * @return : List<CardInfo>
	 * @version V3.0.0
	 */
	public List<CardInfo> exportCardPwd(String confNo);

	/**
	 * 查询互生卡配置单制作数据(制作单)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月19日 下午6:45:05
	 * @param orderNo
	 *            订单号
	 * @param confNo
	 *            配置单号
	 * @param hsResNo
	 *            互生号
	 * @return
	 * @return : CardMarkData
	 * @version V3.0.0
	 */
	public CardMarkData queryCardConfigMarkData(String orderNo, String confNo, String hsResNo);

	/**
	 * 导出暗码数据
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年9月29日 下午5:19:33
	 * @param confNo
	 *            配置单号
	 * @return
	 * @return : List<CardInfo>
	 * @version V3.0.0
	 */
	public List<CardInfo> exportCardDarkCode(String confNo);

	/**
	 * 互生卡配置单制成
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月20日 上午10:59:20
	 * @param bean
	 *            互生卡配置单制成参数实体
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void cardConfigMark(CardMarkConfig bean) throws HsException;

	/**
	 * 查询卡制作数据(制作单)
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月20日 上午11:31:26
	 * @param orderNo
	 *            订单号
	 * @param confNo
	 *            配置单号
	 * @param hsResNo
	 *            互生号
	 * @return
	 * @return : CardMarkData
	 * @version V3.0.0
	 */
	public CardMarkData queryCardMarkData(String orderNo, String confNo, String hsResNo);

	/**
	 * 查看互生卡出入库详情
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月17日 下午4:27:20
	 * @param orderNo
	 *            订单号
	 * @return
	 * @return : CardInOut
	 * @version V3.0.0
	 */
	public CardInOut queryCardInOutDetail(String orderNo);

	/**
	 * 互生卡配置入库
	 *
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月8日 下午5:01:27
	 * @param confNo
	 *            配置单号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void cardConfigEnter(String confNo, String operNo) throws HsException;
}
