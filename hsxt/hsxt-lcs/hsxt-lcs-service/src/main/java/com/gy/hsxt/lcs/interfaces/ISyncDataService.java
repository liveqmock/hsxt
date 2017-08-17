/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.lcs.interfaces;

/** 
 * @Description: 地区平台增量更新全局基础数据
 *
 * @Package: com.gy.hsxt.lcs.interfaces  
 * @ClassName: ISyncDataService 
 * @author: yangjianguo 
 * @date: 2016-5-9 下午6:22:27 
 * @version V1.0  
 */
public interface ISyncDataService {
    /**
     * 增量更新地区平台全局配置数据
     * @param tableCode 数据表代码
     * @param version 数据表本地
     */
    public void syncData(String tableCode, Long version);
}
