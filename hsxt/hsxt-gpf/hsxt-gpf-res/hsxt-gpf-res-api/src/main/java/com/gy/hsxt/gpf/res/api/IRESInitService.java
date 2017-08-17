/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.api
 * @ClassName: IRESInitService
 * @Description: 初始化管理接口
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午3:49:29
 * @version V1.0
 */
public interface IRESInitService {

    /**
     * 查询未初始化的平台信息
     * 
     * @param page
     *            分页信息
     * @return 未初始化的平台信息列表
     * @throws HsException
     */
    public PageData<PlatInfo> queryUnInitPlatInfo(Page page) throws HsException;

    /**
     * 添加平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    public void addPlatInfo(PlatInfo platInfo) throws HsException;

    /**
     * 查询平台公司信息
     * 
     * @param platNo
     *            平台代码
     * @param entCustName
     *            平台名称
     * @param page
     *            分页信息
     * @return 平台公司信息列表
     */
    public PageData<PlatInfo> queryPlatInfo(String platNo, String entCustName, Page page);

    /**
     * 添加管理公司信息
     * 
     * @param mentInfo
     *            管理公司信息
     * @throws HsException
     */
    public void addMentInfo(MentInfo mentInfo) throws HsException;

    /**
     * 查询管理公司信息
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页信息
     * @return 管理公司信息列表
     */
    public PageData<MentInfo> queryMentInfo(String entResNo, String entCustName, Page page);

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMent
     *            平台与管理公司关系
     * @throws HsException
     */
    public void addPlatMent(PlatMent platMent) throws HsException;

    /**
     * 查询平台与管理公司关系
     * 
     * @param platNo
     *            平台
     * @param mentNo
     *            管理公司公司互生号
     * @return 返回平台与管理公司关系
     */
    public List<PlatMent> queryPlatMent(String platNo, String mentNo);

    /**
     * 手动同步初始化平台公司信息到地区平台
     * 
     * @param platNo
     *            平台代码
     * @throws HsException
     */
    public void syncPlatInfo(String platNo) throws HsException;

    /**
     * 手动同步向平台同步初始化管理公司信息
     * 
     * @param platNo
     *            平台代码
     * @param mentNo
     *            管理公司互生号
     * @throws HsException
     */
    public void syncPlatMent(String platNo, String mentNo) throws HsException;

}
