/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;

/**
 * 再增值积分汇总统计业务接口
 *
 * @Package :com.gy.hsxt.gpf.bm.service
 * @ClassName : BmlmStatisticsService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/26 19:39
 * @Version V3.0.0.0
 */
public interface BmlmStatisticsService {

    /**
     * 开启再增值积分汇总统计
     *
     * @param localNotify 本地通知
     * @return boolean
     * @throws HsException
     */
    boolean bmlmStatistics(LocalNotify localNotify) throws HsException;
}
