/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.interfaces;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.TransCashOrderState;

/**
 * 银行转帐通知处理接口
 * 
 * @Package: com.gy.hsxt.ao.interfaces
 * @ClassName: ITransOutNotifyService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-21 上午10:59:14
 * @version V3.0.0
 */
public interface ITransOutNotifyService {

    /**
     * 更新转帐结果
     * 
     * @param transCashOrderState
     *            转帐结果实体类
     * @return true更新成功false更新失败
     * @throws HsException
     */
    public boolean updateTransStatus(TransCashOrderState transCashOrderState) throws HsException;

}
