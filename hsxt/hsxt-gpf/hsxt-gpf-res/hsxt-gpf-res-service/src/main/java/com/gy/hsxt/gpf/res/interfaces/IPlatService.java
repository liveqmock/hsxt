/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.interfaces;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.um.bean.GridData;

public interface IPlatService {

    /**
     * 查询平台列表
     * 
     * @param platNo
     *            平台代码
     * @param entCustName
     *            平台名称
     * @param page
     *            分页
     * @return
     * @throws HsException
     */
    public GridData<PlatInfo> queryPlatInfo(String platNo, String entCustName, Page page) throws HsException;

    /**
     * 查询未初始化平台
     * 
     * @return
     * @throws HsException
     */
    public List<PlatInfo> queryUnInitPlatInfo() throws HsException;

    /**
     * 添加平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    public void addPlatInfo(PlatInfo platInfo) throws HsException;

    /**
     * 修改平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    public void updatePlatInfo(PlatInfo platInfo) throws HsException;

    /**
     * 同步平台公司信息到用户中心
     * 
     * @param platNo
     *            平台代码
     * @throws HsException
     */
    public void syncPlatToUc(String platNo) throws HsException;

    /**
     * 查询所有已初始化平台
     * 
     * @return
     */
    public List<PlatInfo> platListAll();

}
