/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *定时任务更新已过期的申报申请
 *
 * @Package: com.gy.hsxt.bs.batch
 * @ClassName: DeclareBatchService
 * @Description: 定时任务更新已过期的申报申请
 *
 * @author: xiaofl
 * @date: 2016-1-16 下午3:18:01
 * @version V1.0
 */
@Service
public class DeclareBatchService extends AbstractBatchService {

    /**
     * 申报业务接口
     */
    @Resource
    private IDeclareService declareService;

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        //自动处理过期的申报单
        declareService.autoExpiryDeclare();
        //处理申报单同步开启增值或UC失败的申请
        declareService.retryOpenSyncFail();
        
        this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
    }
}
