/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.companyInfo;

import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;

/***
 * 企业一般信息的操作
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.companyInfo  
 * @ClassName: IEntBaseInfoService 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-11-17 下午12:03:22 
 * @version V1.0
 */
public interface IEntBaseInfoService {
    
    /***
     * 查找企业的基本信息
     * @param entCustId
     * @return
     */
    public AsEntBaseInfo findEntBaseInfo(String entCustId);
    
    /**
     * 更新企业基本信息
     * @param info
     */
    public void updateEntBaseInfo(AsEntBaseInfo info,String operator);
    
}
