/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.pwd;


import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 重置交易密码 接口类
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.api.pwd
 * @ClassName: IBSTransPwdService
 * @Description:
 * @author: liuhq
 * @date: 2015-9-6 下午12:21:16
 */
public interface IBSTransPwdService {

    /**
     * 创建 重置交易密码申请记录
     *
     * @param resetTransPwd 交易密码重置申请 实体类 非null
     *                      <p/>
     *                      无异常便成功，Exception失败
     * @throws HsException
     */
    void createResetTransPwdApply(ResetTransPwd resetTransPwd) throws HsException;

    /**
     * 分页查询 重置交易密码申请记录
     *
     * @param page          分页对象 必须
     * @param transPwdQuery 条件查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    PageData<ResetTransPwd> queryResetTransPwdApplyListPage(Page page, TransPwdQuery transPwdQuery) throws HsException;

    /**
     * 分页查询重置交易密码待审核列表
     *
     * @param page          分页对象 必须
     * @param transPwdQuery 条件查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    PageData<ResetTransPwd> queryTaskListPage(Page page, TransPwdQuery transPwdQuery) throws HsException;

    /**
     * 获取 某一条重置交易密码申请记录
     *
     * @param applyId 申请编号 必须 非null
     * @return 返回某一条重置交易密码申请记录
     * @throws HsException
     */
    ResetTransPwd getResetTransPwdApplyBean(String applyId) throws HsException;

    /**
     * 根据企业客户号查询最新的申请记录
     *
     * @param entCustId 企业客户号
     * @return bean
     * @throws HsException
     */
    ResetTransPwd queryLastApplyBean(String entCustId) throws HsException;

    /**
     * 审批 某一条重置交易密码申请记录
     *
     * @param resetTransPwd 实体对象
     * @throws HsException
     */
    void apprResetTranPwdApply(ResetTransPwd resetTransPwd) throws HsException;

}
