/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.interfaces;

import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.interfaces
 * @ClassName: IOpenSystemService
 * @Description: 开启系统接口
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:53:36
 * @version V1.0
 */
public interface IOpenSystemService {

    /**
     * 用户中心开户
     * 
     * @param declareInfo
     *            申报信息
     * @param optCustId
     *            操作员客户号
     * @return 被申报企业客户号
     * @throws HsException
     */
    public String openUc(DeclareInfo declareInfo, String optCustId) throws HsException;

    /**
     * 增值系统开户
     * 
     * @param declareInfo
     *            申报信息
     * @param optCustId
     *            操作员客户号
     * @throws HsException
     */
    public void openVas(DeclareInfo declareInfo, String optCustId) throws HsException;

    /**
     * 处理开启系统本地事务
     * 
     * @param declareInfo
     *            申报信息
     * @param apprParam
     *            开启系统信息
     * @param isFreeMemberEnt
     *            是否是免费的成员企业
     * @throws HsException
     */
    public void handleOpenSysLocal(DeclareInfo declareInfo, ApprParam apprParam, boolean isFreeMemberEnt)
            throws HsException;

    /**
     * 拒绝开启系统
     * 
     * @param declareInfo
     *            申报信息
     * @param apprParam
     *            开启系统信息
     * @throws HsException
     */
    public void rejectOpenSys(DeclareInfo declareInfo, ApprParam apprParam) throws HsException;
}
