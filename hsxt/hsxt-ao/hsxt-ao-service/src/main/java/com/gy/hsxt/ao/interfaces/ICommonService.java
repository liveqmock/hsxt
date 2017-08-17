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

package com.gy.hsxt.ao.interfaces;

import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 公共服务接口，定义获取平台公共信息等接口
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: ICommonService
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-17 下午4:53:04
 * @version V1.0
 */
public interface ICommonService {

    /**
     * 获取当前地区平台企业客户号
     * 
     * @return
     */
    String getPlatCustId();

    /**
     * 获取当前平台初始化信息
     * 
     * @return
     */
    LocalInfo getLocalInfo();

    /**
     * 获取当前平台互生号
     * 
     * @return
     */
    String getPlatResNo();

}
