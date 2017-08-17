/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bp.common.bean;

import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.BizGroup;

/**
 * 封装或者获取数据方法
 * 
 * @Package: com.gy.hsxt.ac.common.bean
 * @ClassName: SetAndGetDataMethod
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-14 下午5:15:49
 * @version V1.0
 */
@Service
public class SetAndGetDataMethod {

    
    /**
     * 获取前缀
     * 
     * @return 前缀
     */
    public String getPreNo() {
        
        String sysInstanceNo = PropertyConfigurer.getProperty("system.instance.no");// 当前机器编号
        return BizGroup.BP + sysInstanceNo;
    }

}
