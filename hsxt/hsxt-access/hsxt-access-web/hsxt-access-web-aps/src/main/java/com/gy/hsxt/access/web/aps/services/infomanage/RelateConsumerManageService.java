/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;

/**
 * 管理消费者信息管理接口
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.businessService  
 * @ClassName: IMembercompApprovalService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-11-3 下午5:33:15 
 * @version V3.0.0
 */
public interface RelateConsumerManageService extends IBaseService {
    
    /**
     * 查询地区平台下消费者资源
     * @param applyId 申请id
     * @return
     * @throws HsException
     */
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 分页查看消费者的修改记录信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     */
    public PageData queryConsumberInfoBakList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    
    /**
     * 消费者信息的修改
     * @param conditoanMap
     * @throws HsException
     */
    public void UpdateConsumerAndLog(Map conditoanMap) throws HsException;
    
    /**
     * 查看消费者信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    public AsCardHolderAllInfo searchAllInfo(String perCustId) throws HsException;
    
    

}
