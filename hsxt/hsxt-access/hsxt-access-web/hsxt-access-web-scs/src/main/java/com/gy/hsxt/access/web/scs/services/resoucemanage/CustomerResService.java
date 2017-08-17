/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***
 * 消费者资源统计服务类
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.resoucemanage
 * @ClassName: CustomerResService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-5 下午3:05:16
 * @version V1.0
 */
@Service("customerResService")
public class CustomerResService extends BaseServiceImpl implements ICustomerResService {

    /**
     * 消费者关联资源分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        Map<String, Object> temMap = null;
        for (int i = 0; i < 50; i++)
        {
            temMap = new HashMap<String, Object>();
            temMap.put("qyglh", "0500101000" + i);
            temMap.put("qymc", "北京故宫管理有限公司" + i + "号");
            temMap.put("smzcs", i);
            temMap.put("smrzs", i);
            temMap.put("jhhsks", i);
            temMap.put("hyhsks", i);
            returnList.add(temMap);
        }

        return new PageData(returnList.size(), returnList);
    }

    /**
     * 消费者关联资源统计
     * 
     * @param resNo
     * @return
     * @see com.gy.hsxt.access.web.scs.services.resoucemanage.ICustomerResService#getCorrelatStatistics(java.lang.String)
     */
    @Override
    public Map<String, Object> getCorrelatStatistics(String resNo) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("xtzyzs", 9999999);
        retMap.put("xtzysys", 30000);
        retMap.put("jhhsks", 5625);
        retMap.put("hyhsks", 5625);
        retMap.put("smzcs", 5660);
        retMap.put("smrzs", 215653);
        return retMap;
    }
   
}
