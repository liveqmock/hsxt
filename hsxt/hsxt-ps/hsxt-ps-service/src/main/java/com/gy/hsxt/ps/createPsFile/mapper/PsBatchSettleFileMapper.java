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

package com.gy.hsxt.ps.createPsFile.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.ps.createPsFile.bean.BatSettleInfo;

/** 
 * 
 * @Package: com.gy.hsxt.ps.createPsFile.mapper  
 * @ClassName: PsBatchSettleFileMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-3-21 下午2:32:32 
 * @version V1.0 
 

 */
public interface PsBatchSettleFileMapper {
    
    /**
     *  查询批消费积分结算信息数量
     * @param psEntryMap 开始时间与结束时间
     * @return int 分录记账信息数量
     * @throws SQLException
     */
    int searchPsEntryCounts(Map<String, Object> psEntryMap) throws SQLException;
    
    /**
     *  查询批消费积分结算信息记录
     * @param psEntryMap 
     * @return List<Alloc> 分录记账信息集合
     * @throws SQLException
     */
    List<BatSettleInfo> searchPsEntryPage(Map<String, Object> psEntryMap) throws SQLException;
    
}
