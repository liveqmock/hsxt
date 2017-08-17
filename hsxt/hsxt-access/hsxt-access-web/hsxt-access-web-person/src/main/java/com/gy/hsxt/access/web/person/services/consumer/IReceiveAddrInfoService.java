/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;


/***************************************************************************
 * <PRE>
 * Description 		: 持卡用户的操作服务类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.hsc  
 *
 * File Name        : ICardholderService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-11-16 下午6:38:47
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-11-16 下午6:38:47
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface IReceiveAddrInfoService extends IBaseService{
    /**
     * 根据客户号与客户类型查询收货地址
     * @param custId 客户号
     * @param userType 客户类型
     * @return 收货地址集合
     * @throws HsException
     */
    public List<AsReceiveAddr > findReceiveAddrsByCustId(String custId, String userType)throws HsException ;
}
