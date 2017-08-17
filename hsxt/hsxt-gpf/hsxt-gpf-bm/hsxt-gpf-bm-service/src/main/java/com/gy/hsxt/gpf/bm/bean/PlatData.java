/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取平台代码类
 *
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : PlatData
 * @Description : 获取平台代码类
 * @Author : chenm
 * @Date : 2015/10/13 17:16
 * @Version V3.0.0.0
 */
@Service
public class PlatData {

    @Resource
    private IGCSRouteRuleService gcsRouteRuleService;

    /**
     * 获取平台代码
     *
     * @param resNo 互生号
     * @return code
     * @throws HsException
     */
    public String obtainCode(String resNo) throws HsException {
        String platCode = gcsRouteRuleService.getPlatByResNo(resNo);
        HsAssert.hasText(platCode, BMRespCode.BM_QUERY_DATA_ERROR, "无法获取[" + resNo + "]对应的平台代码");
        return platCode;
    }

    /**
     * 获取所有有效的平台代码
     *
     * @return
     * @throws HsException
     */
    public List<String> obtainAllCode() throws HsException {
        //判断平台代码实体是否为空
        List<String> platCodes = new ArrayList<>();
        List<Plat> plats = gcsRouteRuleService.findRegionPlats();
        if (CollectionUtils.isNotEmpty(plats)) {
            for (Plat plat : plats) {
                platCodes.add(plat.getPlatNo());
            }
        }
        return platCodes;
    }
}
