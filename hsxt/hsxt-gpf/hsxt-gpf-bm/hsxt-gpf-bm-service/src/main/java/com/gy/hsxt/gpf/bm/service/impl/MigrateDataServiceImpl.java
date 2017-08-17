/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.service.BmlmService;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.service.MigrateDataService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : MigrateDataServiceImpl
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/11 19:00
 * @Version V3.0.0.0
 */
@Service("migrateDataService")
public class MigrateDataServiceImpl implements MigrateDataService {

    private Logger logger = LoggerFactory.getLogger(MigrateDataService.class);

    @Resource
    private IncrementService incrementService;

    @Resource
    private BmlmService bmlmService;

    /**
     * 数据迁移方法
     *
     * @param incNodeList
     * @return
     */
    @Override
    public boolean migrateData(List<IncNode> incNodeList) {
        try {
            for (IncNode incNode : incNodeList) {
                incrementService.save(incNode.getCustId(), incNode);
            }
        } catch (HsException e) {
            logger.error("=====迁移数据发生异常=====", e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean migrateBmlmMap(Map<String, Bmlm> bmlmMap) throws HsException {

        try {
            for (Map.Entry<String, Bmlm> entry : bmlmMap.entrySet()) {
                bmlmService.save(entry.getKey(),entry.getValue());
            }
        } catch (HsException e) {
            logger.error("=====迁移数据发生异常=====", e);
            throw e;
        }
        return true;
    }
}
