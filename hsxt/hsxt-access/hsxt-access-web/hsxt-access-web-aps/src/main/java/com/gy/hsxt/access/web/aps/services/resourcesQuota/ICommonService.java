/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.exception.HsException;

/***
 * 公共服务方法
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: ICommonService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-18 上午10:29:35
 * @version V1.0
 */
public interface ICommonService extends IBaseService {
    /**
     * 获取地区平台下的管理公司
     * 
     * @param apsBase
     * @return
     * @throws HsException
     */
    public List<Map<String, Object>> getMEntResList(ApsBase apsBase) throws HsException;

    /**
     * 获取管理公司配额详情
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     */
    public Map<String, Object> getMEntResDetail(String mEntResNo) throws HsException;

    /**
     * 管理公司配额分配详情
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     */
    public PlatAppManage manageAllotDetail(String mEntResNo) throws HsException;

    /**
     * 城市资源状态详情
     * 
     * @param cityNo
     * @param status
     * @return
     * @throws HsException
     */
    public List<ResInfo> cityResStatusDetail(String cityNo, String[] status) throws HsException;
}
