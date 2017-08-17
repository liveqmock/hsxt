package com.gy.hsxt.access.web.aps.services.callCenter;

import com.gy.hsxt.access.web.bean.callCenter.SysService;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;

import java.util.Map;

/**
 * 查询消费者信息
 * @category      查询消费者信息
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.IPersonService.java
 * @className     IPersonService
 * @description   查询消费者信息 
 * @author        leiyt
 * @createDate    2016-1-27 下午3:41:47  
 * @updateUser    leiyt
 * @updateDate    2016-1-27 下午3:41:47
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
public interface IPersonService extends IBaseService {
    /**
     * 根据资源号查询客户号--消费者
     * @param resNo
     * @return
     */
    String findCustIdByResNo(String resNo);

    /**
     * 查询消费者所有信息
     * @param custId
     * @return
     */
    AsCardHolderAllInfo searchAllInfo(String custId);

    /**
     * 重要信息变更记录查询
     * @param custId
     * @return
     */
    PerChangeInfo queryPerChangeByCustId(String custId);

    /**
     * 互生卡补办记录查询
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    PageData<SysService> findCardapplyList(Map filterMap, Map sortMap, Page page) throws HsException;

}
