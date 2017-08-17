/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.IncNode;

import java.util.List;
import java.util.Map;

/**
 * 数据迁移业务层
 *
 * @Package :com.gy.hsxt.gpf.bm.service
 * @ClassName : MigrateDataService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/11 18:56
 * @Version V3.0.0.0
 */
public interface MigrateDataService {

    /**
     * 数据迁移方法
     *
     * @param incNodeList
     * @return
     */
    boolean migrateData(List<IncNode> incNodeList);

    /**
     * 迁移再增值数据
     *
     * @param bmlmMap 再增值数据
     * @return {@link Boolean}
     * @throws HsException
     */
    boolean migrateBmlmMap(Map<String, Bmlm> bmlmMap) throws HsException;
}
