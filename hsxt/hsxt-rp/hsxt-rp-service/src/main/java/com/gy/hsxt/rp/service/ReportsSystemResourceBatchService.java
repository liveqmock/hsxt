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

package com.gy.hsxt.rp.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;

/** 
 * 生成系统资源数据
 * @Package: com.gy.hsxt.rp.service  
 * @ClassName: ReportsSystemResourceBatchService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2016-1-6 上午10:51:26 
 * @version V1.0 
 */

@Service
public class ReportsSystemResourceBatchService implements IDSBatchService {

    
    @Autowired
    private ReportsSystemResourceMapper reportsSystemResourceMapper;
    
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsSystemResourceBatchService.class);
    /**
     * 回调监听类
     */
    @Autowired
    private IDSBatchServiceListener listener;
    
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) 
    {
        boolean result=true;
        if(listener!=null){
            LOG.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");
            
            try
            {
            	//生成系统资源数据
            	reportsSystemResourceMapper.generateResourceStatistics();
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0,"执行成功");
            }
            catch (Exception e)
            {
                LOG.info("异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 1,"执行失败");
                result=false;
            }
        }
        return result;
    }
    
}
