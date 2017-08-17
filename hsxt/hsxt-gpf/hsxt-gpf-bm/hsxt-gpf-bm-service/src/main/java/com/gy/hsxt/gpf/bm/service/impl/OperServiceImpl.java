/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.OperRecord;
import com.gy.hsxt.gpf.bm.mapper.OperMapper;
import com.gy.hsxt.gpf.bm.service.OperService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作记录服务层接口实现
 *
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : OperServiceImpl
 * @Description : 操作记录服务层接口实现
 * @Author : chenm
 * @Date : 2015/10/16 11:43
 * @Version V3.0.0.0
 */
@Service("operService")
public class OperServiceImpl implements OperService {

    /**
     * 操作记录Mapper接口
     */
    @Resource
    private OperMapper operMapper;

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, OperRecord value) throws HsException {
        operMapper.save(key, value);
    }

    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    @Override
    public OperRecord getValueByKey(String key) throws HsException {
        return operMapper.getValueByKey(key);
    }
}
