/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.dm.api.common;

import com.gy.hsxt.uc.dm.bean.common.AsDomain;
/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.dm.api.common  
 * @ClassName: IUCDomainService 
 * @Description: 域名信息管理
 *
 * @author: tianxh
 * @date: 2015-12-9 下午8:19:59 
 * @version V1.0
 */
public interface IUCDomainService {
    
    /**
     * 通过code查询其域名
     * @param code  互生卡前2位或者前5位 
     * @return  域名
     */
   public String findDomainInfoByCode(String code);
    
   /**
    * 更新域名信息
    * @param domain 域名信息
    */
   public void updateDomainInfo(AsDomain domain,String operCustId);
    
   /**
    * 新增域名信息
    * @param domain 域名信息
    */
    public void addDomainInfo(AsDomain domain,String operCustId);
}
