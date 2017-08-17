/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.interfaces;


/**
 * 自动对账内部接口
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: IBatchCheckResultService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-2-5 上午11:53:43
 * @version V3.0.0
 */
public interface IBatchCheckResultService {

    /**
     * 终端设备批结算数据老化
     */
    public void batchDataTransfer();
}
