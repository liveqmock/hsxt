/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.TransOutFail;

/**
 * 银行转帐失败mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: TransOutFailMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-1 下午4:01:15
 * @version V3.0.0
 */
public interface TransOutFailMapper {

    /**
     * 插入银行转帐失败记录
     * 
     * @param transOutFail
     *            失败记录信息
     * @return 成功记录数
     */
    public int insertTransOutFail(TransOutFail transOutFail);

    /**
     * 更新处理结果
     * 
     * @param transNo
     *            交易流水号
     * @param processResult
     *            处理结果
     * @return 成功记录数
     */
    public int updateProcessResult(@Param("transNo") String transNo, @Param("processResult") int processResult);

    /**
     * 查询转账失败流水号
     * 
     * @param transNo
     *            原交易流水号
     * @return 失败流水号
     */
    public String findTransFailNo(@Param("transNo") String transNo);

    /**
     * 查询转账失败详情
     * 
     * @param transNo
     *            交易流水号
     * @return 失败详情
     */
    public TransOutFail findTransFailDetail(@Param("transNo") String transNo);

    /**
     * 查询未处理的退款
     * 
     * @param transNo
     *            原交易流水号
     * @return 记录数
     */
    public int findUnCheckRefund(@Param("transNo") String transNo);

}
