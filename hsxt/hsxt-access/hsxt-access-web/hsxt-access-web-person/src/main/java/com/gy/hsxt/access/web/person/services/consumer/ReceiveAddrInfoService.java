/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.services.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;


/***************************************************************************
 * <PRE>
 * Description 		: 收货地址服务类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.hsc  
 *
 * File Name        : CardholderService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-11-16 下午6:40:59
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-11-16 下午6:40:59
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public class ReceiveAddrInfoService extends BaseServiceImpl implements IReceiveAddrInfoService {
//  @Autowired
//  private IUCAsCardHolderService ucAsCardHolderService;//用户中心-持卡用户dubbo
    /**  
     * @param custId
     * @param userType
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.consumer.IReceiveAddrInfoService#findReceiveAddrsByCustId(java.lang.String, java.lang.String) 
     */
    @Override
    public List<AsReceiveAddr> findReceiveAddrsByCustId(String custId, String userType) throws HsException {
        List<AsReceiveAddr> receiveAddrList = new ArrayList<AsReceiveAddr>();
        AsReceiveAddr receiveAddr = new AsReceiveAddr();
        
        receiveAddr.setAddress(custId);
        receiveAddr.setReceiver("张三");
        receiveAddr.setCountryNo("001");
        receiveAddr.setProvinceNo("001001");
        receiveAddr.setCityNo("001001001");
        receiveAddr.setPostCode("0755");
//        receiveAddr.setPhone("0755-82348138");
        receiveAddr.setMobile("13588888888");
        receiveAddr.setIsDefault(1);
        receiveAddrList.add(receiveAddr);
        
        receiveAddr = new AsReceiveAddr();
        receiveAddr.setAddress(custId);
        receiveAddr.setReceiver("张三");
        receiveAddr.setCountryNo("001");
        receiveAddr.setProvinceNo("001002");
        receiveAddr.setCityNo("001001002");
        receiveAddr.setPostCode("0755");
//        receiveAddr.setPhone("0755-82348138");
        receiveAddr.setMobile("13588888888");
        receiveAddr.setIsDefault(0);
        receiveAddrList.add(receiveAddr);
        return receiveAddrList;
    }

    
    

    

}
