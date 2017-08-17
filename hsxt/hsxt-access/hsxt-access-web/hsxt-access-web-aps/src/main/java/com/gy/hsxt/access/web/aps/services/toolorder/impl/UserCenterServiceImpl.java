/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.toolorder.UserCenterService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;


@SuppressWarnings("rawtypes")
@Service
public class UserCenterServiceImpl extends BaseServiceImpl implements UserCenterService {

    @Override
    public void doubleSign(String userName, String password, String entHsResNo ,String secretKey) throws HsException {
       System.out.println("用户中心鉴权双签，用户名：" + userName + ",密码:" + password + ",企业互生号:" + entHsResNo + "AES秘钥:" + secretKey);
        
    }

    @Override
    public String getsecretKey() throws HsException {
        
        return "123456";
    }

    @Override
    public String getRandomToken(String custId) throws HsException {
//        com.gy.hsxt.access.web.common.controller.LoginController.getRandomToken(custid)
        return "138ed9c920698bfa";
    }
    
    @Autowired
    private IUCAsRoleService ucService;
    
    /**
     * 
     * 方法名称：查询用户拥有的角色ID
     * 方法描述：查询用户拥有的角色ID
     * @param operCustId 操作员ID
     * @return
     */
    @Override
    public String[] getRoleIds(String operCustId){
        Set<String> roleSet = this.ucService.getCustRoleIdSet(operCustId);
        if(roleSet == null || roleSet.isEmpty()){
            return new String[0];
        }
        return roleSet.toArray(new String[0]);
    }
}
