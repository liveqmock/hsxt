package com.gy.hsxt.access.web.aps.services.accountPerNoCard;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;

public interface IAccountNoCarService extends IBaseService {
	
    /**
     * 非持卡人账户流水查询
     * @param rpEnterprisResource 非持卡人查询条件
     * @return List<ReportsAccountEntry> 非持卡人账户流水数据集合
     * @throws HsException
     */
    public PageData<ReportsAccountEntry> searchPerNoCardAccPage(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
