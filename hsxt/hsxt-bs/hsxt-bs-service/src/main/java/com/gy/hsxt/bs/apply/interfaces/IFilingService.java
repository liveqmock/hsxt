/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.apply.interfaces;

/** 
 * 
 * @Package: com.gy.hsxt.bs.apply.interfaces  
 * @ClassName: IFilingService 
 * @Description: 报备接口
 *
 * @author: xiaofl 
 * @date: 2015-12-14 下午4:53:15 
 * @version V1.0 
 

 */ 
public interface IFilingService {
    
    /**
     * 判断企业是否被服务公司报备过
     * @param serResNo 服务公司互生号
     * @param entCustName 企业名称
     * @return 已报备返回true,否则返回false
     */
    public boolean isFiling(String serResNo,String entCustName);

}
