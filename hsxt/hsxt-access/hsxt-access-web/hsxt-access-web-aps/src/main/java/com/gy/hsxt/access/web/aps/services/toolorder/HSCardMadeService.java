/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.CardInOut;
import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.bs.bean.tool.CardMarkConfig;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.CardMarkData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 互生卡制作
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.toolorder
 * @ClassName: HSCardMadeService
 * @Description: TODO
 *
 * @author: zhangcy
 * @date: 2015-11-20 下午3:53:44
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface HSCardMadeService extends IBaseService {

	/**
	 * 查询互生卡及其初始密码
	 * 
	 * @param confNo
	 * @return
	 * @throws HsException
	 */
	public List<CardInfo> findCardPwd(String confNo) throws HsException;

	/**
	 * 互生卡开卡
	 * 
	 * @param confNo
	 *            配置单编号
	 * @param operNo
	 *            操作员编号
	 * @throws HsException
	 */
	public void openCard(String confNo, String operNo, String orderType) throws HsException;

	/**
	 * 卡样上传数据查询
	 * 
	 * @param orderNo
	 *            订单号
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 */
	public void findConfigCarStyle(String orderNo, String entResNo) throws HsException;

	/**
	 * 查询制作单数据
	 * 
	 * @param orderNo
	 *            订单号
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 */
	public CardMarkData queryCardMarkData(String orderNo, String confNo, String hsResNo) throws HsException;

	/**
	 * 导出暗码数据查询
	 * 
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 */
	public List<CardInfo> exportCardDarkCode(String confNo) throws HsException;

	/**
	 * 查询制作单作成
	 * 
	 * @param orderNo
	 * @param confNo
	 * @param entResNo
	 * @return
	 */
	public CardMarkData queryCardConfigMarkData(String orderNo, String confNo, String entResNo) throws HsException;

	/**
	 * 关闭订单
	 * 
	 * @param orderNo
	 * @throws HsException
	 */
	public void closeToolOrder(String orderNo) throws HsException;

	/**
	 * 互生卡配置单制成
	 * 
	 * @param bean
	 * @throws HsException
	 */
	public void cardConfigMark(CardMarkConfig bean) throws HsException;

	/**
	 * 查询互生卡出入口详情
	 * 
	 * @param orderNo
	 * @return
	 * @throws HsException
	 */
	public CardInOut queryCardInOutDetail(String orderNo) throws HsException;

	/**
	 * 互生卡配置入库
	 * 
	 * @param confNo
	 * @throws HsException
	 */
	public void cardConfigEnter(String confNo, String operNo) throws HsException;

	/**
	 * 卡制作上传卡样
	 * 
	 * @param bean
	 * @return
	 * @throws HsException
	 */
	public String uploadConfigCardStyle(CardStyle bean) throws HsException;

	/**
	 * 查询配置单详情
	 * 
	 * @param confNo
	 * @return
	 * @throws HsException
	 */
	public ToolConfig queryToolConfigDetail(String confNo) throws HsException;

	/**
	 * 修改卡样锁定状态
	 * 
	 * @param confNo
	 * @param isLock
	 * @throws HsException
	 */
	public void modifyCaryStyleLockStatus(String confNo, Boolean isLock) throws HsException;

}
