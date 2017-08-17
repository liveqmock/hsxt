package com.gy.hsxt.access.web.aps.services.toolmanage;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolmanage
 * @className     : ICardStyleService.java
 * @description   : 供应商信息接口
 * @author        : maocy
 * @createDate    : 2016-04-06
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface ICardStyleService extends IBaseService {
    
    /**
     * 
     * 方法名称：添加互生卡样
     * 方法描述：互生卡样-添加互生卡样
     * @param bean 互生卡样
     * @throws HsException
     */
	public void addCardStyle(CardStyle bean)throws HsException;
	
	/**
     * 
     * 方法名称：互生卡样的启用/停用
     * 方法描述：互生卡样信息-互生卡样的启用/停用
     * @param bean 互生卡样
     * @throws HsException
     */
	public void cardStyleEnableOrStop(CardStyle bean)throws HsException;
	
	/**
     * 
     * 方法名称：查询互生卡样
     * 方法描述：互生卡样信息-依据互生卡样标识查询互生卡样
     * @param cardStyleId 互生卡样标识
     * @throws HsException
     */
	public CardStyle findCardStyleById(String cardStyleId);
	
}
