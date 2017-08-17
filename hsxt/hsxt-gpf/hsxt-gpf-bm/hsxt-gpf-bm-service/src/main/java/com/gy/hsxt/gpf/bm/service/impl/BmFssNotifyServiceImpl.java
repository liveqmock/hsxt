/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.service.BmFssNotifyService;
import com.gy.hsxt.gpf.bm.service.BmlmStatisticsService;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * BM文件同步系统同步通知实现
 *
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : BmNotifyServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/26 19:40
 * @Version V3.0.0.0
 */
@Service("bmFssNotifyService")
public class BmFssNotifyServiceImpl implements BmFssNotifyService {

    @Resource
    private BmlmStatisticsService bmlmStatisticsService;

    /**
     * 文件同步系统同步通知
     * <p/>
     * 本地通知分为两种 远程回调 本地回调
     * <p/>
     * 远程回调：再增值积分统计
     * <p/>
     * 本地回调：增值积分文件上传  ， 再增值积分文件上传
     *
     * @param notify 本地通知
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean fssSyncNotify(LocalNotify notify) throws HsException {

        boolean success = true;

        //再增值积分统计
        if (FssNotifyType.REMOTE_BACK.getTypeNo() == notify.getNotifyType()
                && FssPurpose.BM_BMLM.getCode().equals(notify.getPurpose())) {
            success = bmlmStatisticsService.bmlmStatistics(notify);
        }

        return success;
    }
}
