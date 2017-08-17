/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.pwd.mapper;

import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业交易密码重置 Mapper接口
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.pwd.mapper
 * @ClassName: TransPwdMapper
 * @Description:
 * @author: liuhq
 * @date: 2015-10-15 上午11:45:08
 */
@Repository
public interface TransPwdMapper {
    /**
     * 创建 重置交易密码申请记录
     *
     * @param resetTransPwd 交易密码重置申请 实体类 非null
     *                      <p/>
     *                      无异常便成功，Exception失败
     */
    void createResetTranPwdApply(ResetTransPwd resetTransPwd);

    /**
     * 分页查询 重置交易密码申请记录
     *
     * @param transPwdQuery 条件查询
     * @return 返回分好页的数据列表
     */
    List<ResetTransPwd> queryResetTranPwdApplyListPage(TransPwdQuery transPwdQuery);

    /**
     * 分页查询重置交易密码待审核列表
     *
     * @param transPwdQuery 条件查询
     * @return 返回分好页的数据列表
     */
    List<ResetTransPwd> selectTaskListPage(TransPwdQuery transPwdQuery);

    /**
     * 获取 某一条重置交易密码申请记录
     *
     * @param applyId 申请编号 必须 非null
     * @return 返回某一条重置交易密码申请记录
     */
    ResetTransPwd getResetTranPwdApplyBean(@Param(value = "applyId") String applyId);

    /**
     * 审批 某一条重置交易密码申请记录
     *
     * @param resetTransPwd 重置交易密码申请记录
     */
    void apprResetTranPwdApply(ResetTransPwd resetTransPwd);

    /**
     * 判断某个企业是否存在待审的申请
     *
     * @param entCustId 企业客户号 必须 非null
     * @return
     */
    String getApplyPendingId(@Param(value = "entCustId") String entCustId);

    /**
     * 根据企业客户号查询最新的申请记录
     *
     * @param entCustId 企业客户号
     * @return bean
     */
    ResetTransPwd selectLastApplyBeanByCustId(@Param("entCustId") String entCustId);
}
