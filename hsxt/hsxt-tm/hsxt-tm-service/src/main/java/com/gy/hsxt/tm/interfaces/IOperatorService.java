/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.interfaces;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;

/**
 * 操作员内部接口
 * 
 * @Package: com.gy.hsxt.tm.interfaces
 * @ClassName: IOperatorService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-3-26 下午5:38:07
 * @version V3.0.0
 */
public interface IOperatorService {
    /**
     * 获取操作员权限ID列表
     * 
     * @param optCustId
     *            操作员编号
     * @return 权限ID列表
     */
    public List<String> getOptPermission(String optCustId);

    /**
     * 查询是否值班主任
     * 
     * @param optCustId
     *            操作员编号
     * @return true:是 false:否
     * @throws HsException
     */
    public boolean isChief(String optCustId) throws HsException;
}
