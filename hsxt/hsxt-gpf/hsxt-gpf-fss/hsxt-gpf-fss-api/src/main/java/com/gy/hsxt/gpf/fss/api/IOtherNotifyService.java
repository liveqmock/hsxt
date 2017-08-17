/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.dubbo.service.IHsDubboService;

/**
 * 其他子系统接收本地通知业务接口
 * 需要子系统各自实现
 *
 * @Package :com.gy.hsxt.gpf.fss.api
 * @ClassName : IOtherNotifyService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/26 16:46
 * @Version V3.0.0.0
 */
public interface IOtherNotifyService extends IHsDubboService{

    /**
     * 文件同步系统同步通知
     *
     * @param notify 本地通知
     * @return boolean
     * @throws HsException
     */
    boolean fssSyncNotify(LocalNotify notify) throws HsException;
}
