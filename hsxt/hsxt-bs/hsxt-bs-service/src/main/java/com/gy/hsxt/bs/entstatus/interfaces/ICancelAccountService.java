/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.bs.entstatus.bean.CancelAccountParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 成员企业销户
 * 
 * @Package : com.gy.hsxt.bs.entstatus.interfaces
 * @ClassName : ICancelAccountService
 * @Description : 成员企业销户
 * @Author : xiaofl
 * @Date : 2015-12-16 下午4:23:52
 * @Version V1.0
 */
public interface ICancelAccountService {

    /**
     * 查询未完成的销户（STATUS=4,PROGRESS<>5）
     * 
     * @return 未完成的销户列表
     */
    List<CancelAccountParam> getUnCompletedCancelAcList();

    /**
     * 成员企业销户-开始
     * 
     * @param quitApplyId
     *            成员企业销户申请单编号
     * @param optInfo
     *            操作员信息
     * @return
     * @throws HsException
     */
    boolean cancelAccount(String quitApplyId, OptInfo optInfo) throws HsException;

    /**
     * 注销增值系统账户--成员企业销户
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    boolean cancelBmAccount(CancelAccountParam ca) throws HsException;

    /**
     * 互生币转货币--成员企业销户
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    boolean hsb2hb(CancelAccountParam ca) throws HsException;

    /**
     * 银行转账--成员企业销户
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    boolean bankTransOut(CancelAccountParam ca) throws HsException;

    /**
     * 注销用户中心账户--成员企业销户
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    boolean cancelUcAccount(CancelAccountParam ca) throws HsException;

    /**
     * 释放互生号--成员企业销户
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    void releaseResNo(CancelAccountParam ca) throws HsException;

    /**
     * 成员企业销户--调度任务
     * 
     * @param ca
     *            销户信息
     * @throws HsException
     */
    void cancelAccount4Batch(CancelAccountParam ca) throws HsException;

}
