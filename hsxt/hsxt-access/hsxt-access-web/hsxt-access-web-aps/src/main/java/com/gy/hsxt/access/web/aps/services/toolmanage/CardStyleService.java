package com.gy.hsxt.access.web.aps.services.toolmanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolProductService;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className : CardStyleService.java
 * @description : 供应商信息接口实现
 * @author : maocy
 * @createDate : 2016-04-06
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class CardStyleService extends BaseServiceImpl implements
		ICardStyleService {

	@Autowired
	private IBSToolProductService bsService;// BS服务类

	/**
	 * 
	 * 方法名称：分页查询互生卡样 方法描述：分页查询互生卡样
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page)
			throws HsException {
		String cardStyleName = (String) filterMap.get("cardStyleName");// 互生卡样名称
		try {
			return this.bsService.queryCardStylePage(cardStyleName, null, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findScrollResult",
					"调用BS分页查询互生卡样式息失败", ex);
			return null;
		}

	}

	/**
	 * 
	 * 方法名称：添加互生卡样 方法描述：互生卡样-添加互生卡样
	 * 
	 * @param bean
	 *            互生卡样
	 * @throws HsException
	 */
	public void addCardStyle(CardStyle bean) throws HsException {
		try {
			this.bsService.addCardStyle(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "addCardStyle",
					"调用BS服务添加互生卡样失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：互生卡样的启用/停用 方法描述：互生卡样信息-互生卡样的启用/停用
	 * 
	 * @param bean
	 *            互生卡样
	 * @throws HsException
	 */
	public void cardStyleEnableOrStop(CardStyle bean) throws HsException {
		try {
			this.bsService.cardStyleEnableOrStop(bean);
		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "cardStyleEnableOrStop",
					"调用BS服务互生卡样的启用/停用失败", ex);
			throw new HsException(ASRespCode.AS_BIZ_OPT_FAII_RESET);
		}
	}

	/**
	 * 
	 * 方法名称：查询互生卡样 方法描述：互生卡样信息-依据互生卡样标识查询互生卡样
	 * 
	 * @param cardStyleId
	 *            互生卡样标识
	 * @throws HsException
	 */
	public CardStyle findCardStyleById(String cardStyleId) {
		try {
			return this.bsService.queryCardStyleById(cardStyleId);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "findCardStyleById",
					"调用BS查询互生卡样失败", ex);
			return null;
		}
	}

}