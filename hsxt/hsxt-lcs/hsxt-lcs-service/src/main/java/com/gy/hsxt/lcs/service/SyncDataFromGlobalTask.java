/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;

import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ISyncDataService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : SyncDataFromGlobalTask.java
 * 
 *  Creation Date   : 2015-7-14
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 定时从总平台更新全局配置信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Component("syncDataFromGlobalTask")
public class SyncDataFromGlobalTask implements IDSBatchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDSBatchServiceListener listener;

    @Autowired
    ISyncDataService syncDataService;
    
    @Autowired
    IVersionService versionService;

    public void syncDataTask() {

        List<Version> versionList = versionService.findAll();
        Map<String, Long> versionMap = new HashMap<String, Long>();
        if (null != versionList)
        {
            for (Version version : versionList)
            {
                versionMap.put(version.getVersionCode(), version.getVersion());
            }
        }

        // 更新GL_COUNTRY
        syncDataService.syncData(Constant.GL_COUNTRY, versionMap.get(Constant.GL_COUNTRY) == null ? new Long(0) : versionMap
                .get(Constant.GL_COUNTRY));
        // 更新GL_PROVINCE
        syncDataService.syncData(Constant.GL_PROVINCE, versionMap.get(Constant.GL_PROVINCE) == null ? new Long(0) : versionMap
                .get(Constant.GL_PROVINCE));
        // 更新GL_CITY
        syncDataService.syncData(Constant.GL_CITY, versionMap.get(Constant.GL_CITY) == null ? new Long(0) : versionMap
                .get(Constant.GL_CITY));
        // 更新GL_LANGUAGE
        syncDataService.syncData(Constant.GL_LANGUAGE, versionMap.get(Constant.GL_LANGUAGE) == null ? new Long(0) : versionMap
                .get(Constant.GL_LANGUAGE));
        // 更新GL_ERROR_MSG
        syncDataService.syncData(Constant.GL_ERROR_MSG, versionMap.get(Constant.GL_ERROR_MSG) == null ? new Long(0) : versionMap
                .get(Constant.GL_ERROR_MSG));
        // 更新GL_PROMPT_MSG
        syncDataService.syncData(Constant.GL_PROMPT_MSG, versionMap.get(Constant.GL_PROMPT_MSG) == null ? new Long(0) : versionMap
                .get(Constant.GL_PROMPT_MSG));
        // 更新GL_UNIT
        syncDataService.syncData(Constant.GL_UNIT, versionMap.get(Constant.GL_UNIT) == null ? new Long(0) : versionMap
                .get(Constant.GL_UNIT));
        // 更新GL_ENUM_ITEM
        syncDataService.syncData(Constant.GL_ENUM_ITEM, versionMap.get(Constant.GL_ENUM_ITEM) == null ? new Long(0) : versionMap
                .get(Constant.GL_ENUM_ITEM));
        // 更新GL_CURRENCY
        syncDataService.syncData(Constant.GL_CURRENCY, versionMap.get(Constant.GL_CURRENCY) == null ? new Long(0) : versionMap
                .get(Constant.GL_CURRENCY));
        // 更新GL_PLAT
        syncDataService.syncData(Constant.GL_PLAT, versionMap.get(Constant.GL_PLAT) == null ? new Long(0) : versionMap
                .get(Constant.GL_PLAT));
        // 更新GL_RESNO_ROUTE
        syncDataService.syncData(Constant.GL_RESNO_ROUTE, versionMap.get(Constant.GL_RESNO_ROUTE) == null ? new Long(0) : versionMap
                .get(Constant.GL_RESNO_ROUTE));
        // 更新GL_SUBSYS
        syncDataService.syncData(Constant.GL_SUBSYS, versionMap.get(Constant.GL_SUBSYS) == null ? new Long(0) : versionMap
                .get(Constant.GL_SUBSYS));
        // 更新GL_BIZ_ROUTE
        syncDataService.syncData(Constant.GL_BIZ_ROUTE, versionMap.get(Constant.GL_BIZ_ROUTE) == null ? new Long(0) : versionMap
                .get(Constant.GL_BIZ_ROUTE));
////      更新GL_POINT_ASSIGN_RULE
//        syncDataService.syncData(Constant.GL_POINT_ASSIGN_RULE, versionMap.get(Constant.GL_POINT_ASSIGN_RULE) == null ? new Long(0)
//                : versionMap.get(Constant.GL_POINT_ASSIGN_RULE));
        
    }

    /**
     * 调度执行方法
     * 
     * @param executeId
     *            任务执行编号
     * @param param
     *            参数
     * @return
     * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.lang.String,
     *      java.util.Map)
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> param) {
        boolean result = true;
        if (listener != null)
        {
            logger.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");
            try
            {
                syncDataTask();
                logger.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0, "执行成功");
            }
            catch (Exception e)
            {
                logger.info("执行失败!!!!", e);
                // 发送进度通知
                listener.reportStatus(executeId, 1, "执行失败");
                result = false;
            }
        }
        return result;
    }
}
