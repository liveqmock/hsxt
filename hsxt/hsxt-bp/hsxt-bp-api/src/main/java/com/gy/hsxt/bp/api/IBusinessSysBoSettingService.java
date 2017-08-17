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

package com.gy.hsxt.bp.api;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 业务操作许可设置接口
 * @Package: com.gy.hsxt.bp.api  
 * @ClassName: IBusinessSysBoSettingService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-2-24 上午11:50:29 
 * @version V1.0 
 

 */
public interface IBusinessSysBoSettingService {
	
    /**
     * 批量设置业务操作许可
     * @param custId 客户ID
     * @param sysBoSettingList 业务操作许可List
     * @throws HsException
     */
    public void setSysBoSettingList(String custId, String operCustId, List<BusinessSysBoSetting> sysBoSettingList) throws HsException;
    
    /**
     * 查询多个业务操作许可
     * @param BusinessSysBoSetting 业务操作许可对象
     * @return Map<BusinessSysBoSetting> 业务操作许可对象集合
     * @throws HsException
     */
    public Map<String, BusinessSysBoSetting> searchSysBoSettingList (BusinessSysBoSetting sysBoSetting) throws HsException;
}
