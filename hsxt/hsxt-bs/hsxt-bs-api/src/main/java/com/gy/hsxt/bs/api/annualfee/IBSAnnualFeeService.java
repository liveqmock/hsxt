/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.annualfee;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrderQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 年费 接口类
 *
 * @Package : com.gy.hsxt.bs.api.annualfee
 * @ClassName : IAnnualFeePriceService
 * @Description :
 * @Author  : liuhq
 * @Date  : 2015-9-1 下午12:19:11
 * @Version  V1.0
 */
public interface IBSAnnualFeeService {

    // =================== 企业年费信息 开始 =====================//

    /**
     * 获取 某一个企业年费信息
     *
     * @param entCustId 企业客户号 必须 非null
     * @return 返回某一个企业年费信息， Exception失败
     * @throws HsException
     */
    AnnualFeeInfo queryAnnualFeeInfo(String entCustId) throws HsException;

    // =================== 综合处理 开始 =====================//

    /**
     * 提交年费业务订单
     *
     * @param annualFeeOrder 年费业务订单
     * @return 业务订单号
     * @throws HsException
     */
    AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder) throws HsException;

    /**
     * 人工提前缴纳年费
     *
     * @param annualFeeOrder 实体类 非null
     * @throws HsException
     */
    String payAnnualFeeOrder(AnnualFeeOrder annualFeeOrder) throws HsException;

    /**
     * 查询年费订单详情
     *
     * @param orderNo 订单编号 非空
     * @return AnnualFeeOrder 年费订单实体
     * @throws HsException 异常
     */
    AnnualFeeOrder queryAnnualFeeOrder(String orderNo) throws HsException;

    /**
     * 分页查询年费业务单
     *
     * @param page                分页实体
     * @param annualFeeOrderQuery 查询实体
     * @return 查询数据
     * @throws HsException
     */
    PageData<AnnualFeeOrder> queryAnnualFeeOrderForPage(Page page, AnnualFeeOrderQuery annualFeeOrderQuery) throws HsException;

}
