/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.mapper
 * @ClassName: RealNameAuthMapper
 * @Description: 实名认证Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午5:05:34
 * @version V1.0
 */
@Repository
public interface RealNameAuthMapper {

    /**
     * 创建消费者实名认证申请
     * 
     * @param perRealnameAuth
     *            消费者实名认证申请
     */
    public int createPerRealnameAuth(PerRealnameAuth perRealnameAuth);

    /**
     * 是否存在消费者实名认证
     * 
     * @param perCustId
     *            消费者客户号
     * @return true存在， false则不存在
     */
    public boolean existPerAuth(String perCustId);

    /**
     * 更新消费者实名认证
     * 
     * @param perRealnameAuth
     *            消费者实名认证
     */
    public void updatePerRealnameAuth(PerRealnameAuth perRealnameAuth);

    /**
     * 查询消费者实名认证
     * 
     * @param realNameQueryParam
     *            查询参数
     * @return 消费者实名认证列表
     */
    public List<PerRealnameBaseInfo> queryPerAuthListPage(RealNameQueryParam realNameQueryParam);

    /**
     * 关联工单查询消费者实名认证
     * 
     * @param realNameQueryParam
     *            查询参数
     * @return 消费者实名认证列表
     */
    public List<PerRealnameBaseInfo> queryPerAuth4ApprListPage(RealNameQueryParam realNameQueryParam);

    /**
     * 通过申请编号查询消费者实名认证详情
     * 
     * @param applyId
     *            申请编号
     * @return 消费者实名认证详情
     */
    public PerRealnameAuth queryPerRealnameAuthById(String applyId);

    /**
     * 通过消费者客户号查询消费者实名认证详情
     * 
     * @param perCustId
     *            消费者客户号
     * @return 消费者实名认证详情
     */
    public PerRealnameAuth queryPerRealnameAuthByCustId(String perCustId);

    /**
     * 更新消费者实名认证状态
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param apprOperator
     *            操作员
     */
    public void updatePerRealnameAuthStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("apprOperator") String apprOperator, @Param("apprRemark") String apprRemark);

    /**
     * 查询个人实名认证记录
     * 
     * @param perCustId
     *            个人客户号
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 个人实名认证记录列表
     */
    public List<SysBizRecord> queryPerAuthRecordListPage(@Param("perCustId") String perCustId,
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 是否存在企业实名认证
     * 
     * @param entCustId
     *            企业客户号
     * @return true存在，false则不存在
     */
    public boolean existEntAuth(String entCustId);

    /**
     * 创建企业实名认证申请
     * 
     * @param entRealnameAuth
     *            企业实名认证申请
     */
    public int createEntRealnameAuth(EntRealnameAuth entRealnameAuth);

    /**
     * 更新企业实名认证申请
     * 
     * @param entRealnameAuth
     *            企业实名认证申请
     */
    public void updateEntRealnameAuth(EntRealnameAuth entRealnameAuth);

    /**
     * 查询企业实名认证申请
     * 
     * @param realNameQueryParam
     *            查询参数
     * @return 企业实名认证申请列表
     */
    public List<EntRealnameBaseInfo> queryEntAuthListPage(RealNameQueryParam realNameQueryParam);

    /**
     * 关联工单查询企业实名认证审批
     * 
     * @param realNameQueryParam
     *            查询参数
     * @return 企业实名认证申请列表
     */
    public List<EntRealnameBaseInfo> queryEntAuth4ApprListPage(RealNameQueryParam realNameQueryParam);

    /**
     * 通过申请编号查询企业实名认证申请详情
     * 
     * @param applyId
     *            申请编号
     * @return 企业实名认证申请详情
     */
    public EntRealnameAuth queryEntRealnameAuthById(String applyId);

    /**
     * 通过企业客户号查询企业实名认证申请详情
     * 
     * @param entCustId
     *            企业客户号
     * @return 企业实名认证申请详情
     */
    public EntRealnameAuth queryEntRealnameAuthByCustId(String entCustId);

    /**
     * 更新企业实名认证状态
     * 
     * @param applyId
     *            申请编号
     * @param status
     *            状态
     * @param apprOperator
     *            操作员
     * @param apprRemark
     *            审批意见
     */
    public void updateEntRealnameAuthStatus(@Param("applyId") String applyId, @Param("status") Integer status,
            @Param("apprOperator") String apprOperator, @Param("apprRemark") String apprRemark);

}
