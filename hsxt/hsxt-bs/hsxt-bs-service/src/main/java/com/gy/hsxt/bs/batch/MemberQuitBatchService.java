/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.entstatus.bean.CancelAccountParam;
import com.gy.hsxt.bs.entstatus.interfaces.ICancelAccountService;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 *定时任务注销成员企业
 *
 * @Package: com.gy.hsxt.bs.batch
 * @ClassName: DeclareBatchService
 * @Description: 定时任务注销成员企业
 *
 * @author: xiaofl
 * @date: 2016-1-16 下午3:18:01
 * @version V1.0
 */
@Service
public class MemberQuitBatchService extends AbstractBatchService {

    /**
     * 成员企业销户业务接口
     */
    @Resource
    private ICancelAccountService cancelAccount;

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    @Transactional
    public void execute(String executeId, String scanDate) throws Exception {
        List<CancelAccountParam> list = cancelAccount.getUnCompletedCancelAcList();
        for (CancelAccountParam ca : list) {
            SystemLog.info(this.getClass().getName(), "cancelAc", "input param:" + ca);
            try {
                cancelAccount.cancelAccount4Batch(ca);
            } catch (HsException e) {
                SystemLog.error(this.getClass().getName(), "cancelAc", "成员企业注销失败[param=" + ca + "]", e);
            }
        }
        this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
    }

}
