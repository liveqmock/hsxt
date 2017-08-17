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

package com.gy.hsxt.bp.mapper;

import java.sql.SQLException;
import java.util.List;

import com.gy.hsxt.bp.bean.BusinessSysBoSetting;

/** 
 * 业务操作许可设置
 * @Package: com.gy.hsxt.bp.mapper  
 * @ClassName: BusinessSysBoSettingMapper 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-2-24 上午11:48:59 
 * @version V1.0 
 

 */
public interface BusinessSysBoSettingMapper {

    /**
     * 新增业务操作许可
     * @param BusinessSysBoSetting 业务操作许可对象
     * @throws SQLException
     */
    public void addSysBoSetting(BusinessSysBoSetting sysBoSetting) throws SQLException;
    
    /**
     * 批量新增业务操作许可
     * @param List<BusinessSysBoSetting> 业务操作许可List
     * @throws SQLException
     */
    public void addSysBoSettingList(List<BusinessSysBoSetting> sysBoSettingList) throws SQLException;
    
    /**
     * 更新业务操作许可
     * @param BusinessSysBoSetting 业务操作许可对象
     * @throws SQLException
     */
    public void updateSysBoSetting(BusinessSysBoSetting sysBoSetting) throws SQLException;
    
    /**
     * 批量更新业务操作许可
     * @param List<BusinessSysBoSetting> 业务操作许可List
     * @throws SQLException
     */
    public void updateSysBoSettingList(List<BusinessSysBoSetting> sysBoSettingList) throws SQLException;
    
    /**
     * 查询多个业务操作许可
     * @param BusinessCustParamItems 业务操作许可对象
     * @return List<BusinessSysBoSetting> 业务操作许可List
     * @throws SQLException
     */
    public List<BusinessSysBoSetting> searchSysBoSettingList(BusinessSysBoSetting sysBoSetting) throws SQLException;
}
