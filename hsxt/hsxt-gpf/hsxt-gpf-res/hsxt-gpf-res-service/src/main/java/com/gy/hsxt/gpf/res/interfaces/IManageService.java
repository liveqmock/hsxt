/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.interfaces;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.bean.PlatMents;
import com.gy.hsxt.gpf.um.bean.GridData;

public interface IManageService {

    /**
     * 查询管理公司列表
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页
     * @return
     */
    public GridData<PlatMent> queryManageList(String entResNo, String entCustName, Page page);

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMents
     *            平台与管理公司关系
     * @throws HsException
     */
    public void addPlatMents(PlatMents platMents) throws HsException;

    /**
     * 添加管理公司信息
     * 
     * @param mentInfo
     *            管理公司信息
     * @throws HsException
     */
    public void addMentInfo(MentInfo mentInfo) throws HsException;

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMent
     *            平台与管理公司关系
     * @throws HsException
     */
    public void addPlatMent(PlatMent platMent) throws HsException;

    /**
     * 同步管理公司信息到地区平台用户中心
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     */
    public void syncManageToUc(String platNo, String entResNo);

    /**
     * 同步管理公司信息到地区平台业务系统
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     */
    public void syncManageToBs(String platNo, String entResNo);

}
