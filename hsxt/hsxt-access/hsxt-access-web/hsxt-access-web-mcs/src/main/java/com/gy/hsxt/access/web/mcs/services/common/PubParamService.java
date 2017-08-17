/**
 * 
 */
package com.gy.hsxt.access.web.mcs.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;

/**   
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.mcs.services.common
 * @className     : PubParamService.java
 * @description   : 
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class PubParamService implements IPubParamService{
    
    //公共参数接口
    @Autowired
    private LcsClient lcsClient;
    
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

}
