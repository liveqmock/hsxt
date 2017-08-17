/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.apply.IntentCustBaseInfo;
import com.gy.hsxt.bs.bean.apply.IntentCustQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.mapper
 * @ClassName: IntentCustMapper
 * @Description: 意向客户Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-15 下午3:56:31
 * @version V1.0
 */
@Repository
public interface IntentCustMapper {

    /**
     * 创建意向客户
     * 
     * @param intentCust
     *            意向客户
     */
    public void createIntentCust(IntentCust intentCust);

    /**
     * 根据受理编号和营业执照号查询意向客户
     * 
     * @param acceptNo
     *            受理编号
     * @param licenseNo
     *            企业执照号码
     * @return 处理操作历史列表
     */
    public IntentCust queryIntentCustByAcceptNoAndLicenseNo(@Param("acceptNo") String acceptNo,
            @Param("licenseNo") String licenseNo);

    /**
     * 查询意向客户
     * 
     * @param intentCustQueryParam
     *            查询参数
     * @return 意向客户列表
     */
    public List<IntentCustBaseInfo> queryIntentCustListPage(IntentCustQueryParam intentCustQueryParam);

    /**
     * 通过ID查询意向客户
     * 
     * @param applyId
     *            申请编号
     * @return 意向客户
     */
    public IntentCust queryIntentCustById(String applyId);

    /**
     * 处理意向客户
     * 
     * @param applyId
     *            申请编号
     * @param apprOperator
     *            操作员
     * @param status
     *            状态
     * @param apprRemark
     *            处理意见
     */
    public void apprIntentCust(@Param("applyId") String applyId, @Param("apprOperator") String apprOperator,
            @Param("status") Integer status, @Param("apprRemark") String apprRemark);

    /**
     * 校验意向客户的唯一性
     * @param intentCust 意向客户
     * @return int
     */
    int queryUniqueIntentCust(IntentCust intentCust);
}
