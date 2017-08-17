/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.gy.hsxt.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.toolorder.HSCardMadeService;
import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.CardInOut;
import com.gy.hsxt.bs.bean.tool.CardInfo;
import com.gy.hsxt.bs.bean.tool.CardMarkConfig;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.CardMarkData;
import com.gy.hsxt.bs.bean.tool.resultbean.ToolConfigPage;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@SuppressWarnings("rawtypes")
@Service
public class HSCardMadeServiceImpl extends BaseServiceImpl implements HSCardMadeService {

	@Autowired
	private IBSToolMarkService ibSToolMarkService;

	@Autowired
	private IBSToolOrderService ibSToolOrderService;

	@Resource
	private UserCenterService userCenterService;

	@Override
	public PageData<ToolConfigPage> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException
	{

		BaseParam param = new BaseParam();
		String platCustName = (String) filterMap.get("custName");
		if(StringUtils.isNotBlank(platCustName)&&!"0000".equals(platCustName))
		{
			param.setRoleIds(userCenterService.getRoleIds((String) filterMap.get("custId")));
		}
		param.setStartDate(filterMap.get("startDate").toString());
		param.setEndDate(filterMap.get("endDate").toString());
		param.setHsResNo(filterMap.get("hsResNo").toString());
		param.setHsCustName(filterMap.get("hsCustName").toString());
		param.setType(filterMap.get("type").toString());
		Integer status = filterMap.get("makeState") == null || "".equals(filterMap.get("makeState").toString()) ? null
				: Integer.parseInt(filterMap.get("makeState").toString());
		param.setStatus(status);

		try
		{
			PageData<ToolConfigPage> result = ibSToolMarkService.queryToolConfigMarkCardPage(param, page);
			return result == null || result.getCount() == 0 || result.getResult() == null ? null : result;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findScrollResult", "调用BS查询工具配置失败", ex);
			return null;
		}
		
	}

	@Override
	public List<CardInfo> findCardPwd(String confNo) throws HsException
	{
		try
		{
			return ibSToolMarkService.exportCardPwd(confNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findCardPwd", "调用BS查询卡密码失败", ex);
			return null;
		}
	}

	@Override
	public void openCard(String confNo, String operNo, String orderType) throws HsException
	{
	
		try
		{
			if (OrderType.APPLY_BUY_TOOL.getCode().equals(orderType) || OrderType.BUY_TOOL.getCode().equals(orderType)
					|| OrderType.APPLY_PERSON_RESOURCE.getCode().equals(orderType))
			{
				ibSToolMarkService.openCard(confNo, operNo);
			} else if (OrderType.REMAKE_BATCH_CARD.getCode().equals(orderType)
					|| OrderType.REMAKE_MY_CARD.getCode().equals(orderType))
			{
				ibSToolMarkService.remarkOpenCard(confNo, orderType, operNo);
			}
			
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openCard", "调用BS服务开卡失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
		
	}

	@Override
	public CardMarkData queryCardMarkData(String orderNo, String confNo, String hsResNo) throws HsException
	{
		try
		{
			return ibSToolMarkService.queryCardMarkData(orderNo, confNo, hsResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCardMarkData", "调用BS查询制作卡数据失败", ex);
			return null;
		}
	}

	@Override
	public List<CardInfo> exportCardDarkCode(String confNo) throws HsException
	{
		try
		{
			return ibSToolMarkService.exportCardDarkCode(confNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "exportCardDarkCode", "调用BS查询导出卡数据失败", ex);
			return null;
		}
	}

	@Override
	public CardMarkData queryCardConfigMarkData(String orderNo, String confNo, String entResNo) throws HsException
	{
		try
		{
			return ibSToolMarkService.queryCardConfigMarkData(orderNo, confNo, entResNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCardConfigMarkData", "调用BS查询数据失败", ex);
			return null;
		}
	}

	@Override
	public void closeToolOrder(String orderNo) throws HsException
	{
		try
		{
			ibSToolOrderService.closeOrWithdrawalsToolOrder(orderNo);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "closeToolOrder", "调用BS服务关闭工具订单失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public void cardConfigMark(CardMarkConfig bean) throws HsException
	{
		try
		{
			ibSToolMarkService.cardConfigMark(bean);
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "cardConfigMark", "调用BS服务卡制作失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	@Override
	public CardInOut queryCardInOutDetail(String orderNo) throws HsException
	{
		try
		{
			return ibSToolMarkService.queryCardInOutDetail(orderNo);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCardInOutDetail", "调用BS查询数据失败", ex);
			return null;
		}
	}

	@Override
	public void cardConfigEnter(String confNo, String operNo) throws HsException
	{
		ibSToolMarkService.cardConfigEnter(confNo, operNo);
	}

	@Override
	public void findConfigCarStyle(String orderNo, String entResNo) throws HsException
	{
		System.out.println("待定功能，无接口.......................");
	}

	@Override
	public String uploadConfigCardStyle(CardStyle bean) throws HsException
	{
		System.out.println("待定功能，无接口.......................");
		return null;
	}

	@Override
	public ToolConfig queryToolConfigDetail(String confNo) throws HsException
	{
		System.out.println("待定功能，无接口.......................");
		return null;
	}

	@Override
	public void modifyCaryStyleLockStatus(String confNo, Boolean isLock) throws HsException
	{
		System.out.println("待定功能，无接口.......................");

	}

}
