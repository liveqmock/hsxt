/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.entstatus;

import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.entstatus
 * @ClassName: IBSCloseOpenEntService
 * @Description: 关闭、开启企业系统管理
 * 
 * @author: xiaofl
 * @date: 2015-11-30 上午10:28:08
 * @version V1.0
 */
public interface IBSCloseOpenEntService {

    /**
     * 申请关闭系统
     * 
     * @param closeOpenEnt
     *            关闭系统信息
     * @throws HsException
     */
    public void closeEnt(CloseOpenEnt closeOpenEnt) throws HsException;

    /**
     * 申请开启系统
     * 
     * @param closeOpenEnt
     *            开启系统信息
     * @throws HsException
     */
    public void openEnt(CloseOpenEnt closeOpenEnt) throws HsException;

    /**
     * 查询关开系统复核
     * 
     * @param closeOpenEntParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 关开系统申请列表
     * @throws HsException
     */
    public PageData<CloseOpenEnt> queryCloseOpenEnt4Appr(CloseOpenEntQueryParam closeOpenEntParam, Page page)
            throws HsException;

    /**
     * 查询开关系统审核查询
     * 
     * @param closeOpenEntParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 关开系统列表
     * @throws HsException
     */
    public PageData<CloseOpenEnt> queryCloseOpenEnt(CloseOpenEntQueryParam closeOpenEntParam, Page page)
            throws HsException;

    /**
     * 查询关闭、开启系统详情
     * 
     * @param applyId
     *            申请编号
     * @return 关闭、开启系统详情
     */
    public CloseOpenEntDetail queryCloseOpenEntDetail(String applyId);

    /**
     * 审批申请关闭、开启系统
     * 
     * @param apprParam
     *            审批参数
     * @throws HsException
     */
    public void apprCloseOpenEnt(ApprParam apprParam) throws HsException;

    /**
     * 查询企业上一次关闭或开启系统信息
     * 
     * @param entCustId
     *            企业客户号
     * @param applyType
     *            申请类别
     * @return 返回企业上一次关闭系统信息
     */
    public CloseOpenEntInfo queryLastCloseOpenEntInfo(String entCustId, Integer applyType);

}
