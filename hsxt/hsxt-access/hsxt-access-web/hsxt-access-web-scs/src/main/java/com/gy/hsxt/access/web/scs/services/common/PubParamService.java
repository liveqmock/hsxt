/**
 * 
 */
package com.gy.hsxt.access.web.scs.services.common;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;

/**   
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.common
 * @className     : PubParamService.java
 * @description   : 
 * @author        : maocy
 * @createDate    : 2015-11-18
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class PubParamService implements IPubParamService {
    
	//公共参数接口
    @Autowired
    private LcsClient lcsClient;

    // 获取登陆tonken
    @Resource
    private IUCAsTokenService ucAsTokenService;
    
    /**
     * 
     * 方法名称：获取系统信息
     * 方法描述：获取系统配置信息
     * @return 系统信息
     * @throws HsException
     */
    public LocalInfo findSystemInfo() throws HsException {
        return this.lcsClient.getLocalInfo();
    }
    
    /**
     * 取随机token 
     * 客户号为空    代表未登录的获取，
     * 客户号不为空    用户已登录使用
     * @param custId 客户号 
     * @return
     * @see com.gy.hsxt.access.web.person.services.common.IPubParamService#getRandomToken(java.lang.String)
     */
    @Override
    public String getRandomToken(String custId) {
        return this.ucAsTokenService.getRandomToken(custId);
    }

}
