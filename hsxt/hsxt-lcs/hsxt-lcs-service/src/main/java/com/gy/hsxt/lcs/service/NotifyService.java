/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.interfaces.INotifyService;
import com.gy.hsxt.lcs.interfaces.ISyncDataService;
import com.gy.hsxt.lcs.interfaces.IVersionService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : NotifyService.java
 * 
 *  Creation Date   : 2015-7-13
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 通知变更远程接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("notifyService")
public class NotifyService implements INotifyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ISyncDataService syncDataService;
    
    @Autowired
    IVersionService versionService;

    @Override
    public void notifyChange(String tableCode, Long version) throws HsException {
        logger.info("NotifyChangeRpcServiceImpl.notifyChange() begin");

        if (tableCode == null || tableCode.isEmpty() || version == null)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "表名或者版本号不应该为空");
        }

        Version versionDB = versionService.queryVersion(tableCode);
        logger.info("Version in DB=" + JSON.toJSONString(versionDB));
        if (null == versionDB)
        {// 子平台没有该表的数据，更新全表
            version = new Long(0);
        }
        else
        {// 子平台有该表的数据，如果子平台version小于总平台version则更新大于的那部分数据，否则不更新
            if (version > versionDB.getVersion())
            {
                version = versionDB.getVersion();
            }
            else
            {
                return;
            }
        }
        syncDataService.syncData(tableCode, version);
        
        logger.info("NotifyChangeRpcServiceImpl.notifyChange() end");
    }
}
