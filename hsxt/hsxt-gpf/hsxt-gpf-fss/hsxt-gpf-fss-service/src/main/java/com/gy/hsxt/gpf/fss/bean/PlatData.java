/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.bean;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.fss.constant.FSSRespCode;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台数据类
 *
 * @Package : com.gy.hsxt.gpf.fss.bean
 * @ClassName : PlatData
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/13 17:16
 * @Version V3.0.0.0
 */
@Service
public class PlatData {

    /**
     * 全局路由接口
     */
    @Resource
    private IGCSRouteRuleService gcsRouteRuleService;

    /**
     * 获取平台代码
     *
     * @param resNo
     * @return
     * @throws HsException
     */
    public String obtainCode(String resNo) throws HsException {
        String platCode = gcsRouteRuleService.getPlatByResNo(resNo);
        HsAssert.hasText(platCode, FSSRespCode.QUERY_DATA_ERROR, "无法获取[" + resNo + "]对应的平台代码");
        return platCode;
    }

    /**
     * 获取所有有效的地区平台代码
     *
     * @return list
     * @throws HsException
     */
    public List<String> obtainAllCode() throws HsException {
        //获取所有有效的地区平台
        List<Plat> plats = gcsRouteRuleService.findRegionPlats();
        List<String> platCodes = new ArrayList<>();
        //判断平台代码实体是否为空
        if (CollectionUtils.isNotEmpty(plats)) {
            for (Plat plat : plats) {
                platCodes.add(plat.getPlatNo());
            }
        }
        return platCodes;
    }
}
