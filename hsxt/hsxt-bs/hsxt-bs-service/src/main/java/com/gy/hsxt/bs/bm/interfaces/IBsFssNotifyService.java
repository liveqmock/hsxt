/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.interfaces;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.api.IOtherNotifyService;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;

/**
 * @Package :com.gy.hsxt.bs.bm.interfaces
 * @ClassName : IBsFssNotifyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/27 9:35
 * @Version V3.0.0.0
 */
public interface IBsFssNotifyService extends IOtherNotifyService {
    /**
     * 增值数据通知
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    Boolean mlmDataNotify(LocalNotify localNotify) throws HsException;

    /**
     * 再增值数据通知
     *
     * @param localNotify
     * @return
     * @throws HsException
     */
    Boolean bmlmDataNotify(LocalNotify localNotify) throws HsException;
}
