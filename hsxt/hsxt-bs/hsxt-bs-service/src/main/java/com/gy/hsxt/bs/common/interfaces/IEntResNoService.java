/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.interfaces;

import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 
 * @Package: com.gy.hsxt.bs.common.interfaces
 * @ClassName: IEntResNoService
 * @Description: 企业资源管理
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:22:42
 * @version V1.0
 */
public interface IEntResNoService {

    /**
     * 生成服务公司下的所有互生号
     * 
     * @param serResNo
     *            互生公司互生号
     */
    public void createEntResNo(String serResNo);

    /**
     * 判断企业互生号是否可用
     * 
     * @param custType
     *            客户类型
     * @param entResNo
     *            企业互生号
     * @return true 可用， false 不可用
     */
    public Boolean checkValidEntResNo(Integer custType, String entResNo);

    /**
     * 更新企业互生号使用状态
     * 
     * @param custType
     *            客户类型
     * @param entResNo
     *            企业互生号
     * @param status
     *            状态
     */
    public int updateEntResNoStatus(Integer custType, String entResNo, Integer status);

    /**
     * 更新服务公司资源
     * 
     * @param resNo
     *            互生号
     * @param status
     *            状态
     * @param entCustId
     *            服务公司客户号
     * @param entCustName
     *            服务公司名称
     */
    public int updateServiceResNo(String resNo, Integer status, String entCustId, String entCustName);

    /**
     * 查询服务公司配额数
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @return 服务公司配额数
     */
    public Integer getServiceQuota(String countryNo, String provinceNo, String cityNo);

    /**
     * 查询成员企业、托管企业配额数
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业配额数
     */
    public Integer getNotServiceQuota(String serResNo, Integer custType, Integer resType);

    /**
     * 查询服务公司可用互生号列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            资源状态
     * @return 服务公司可用互生号列表
     */
    public List<String> getSerResNoList(String countryNo, String provinceNo, String cityNo, Integer resStatus);

    /**
     * 查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @return 成员企业或托管企业可用互生号列表
     */
    public List<String> getNotSerResNoList(String serResNo, Integer custType, Integer resType);

    /**
     * 查询管理公司互生号
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @return 管理公司互生号
     */
    public String getManResNo(String countryNo, String provinceNo);

    /**
     * 查询服务公司可用互生号列表
     * 
     * @param countryNo
     *            国家代码
     * @param provinceNo
     *            省代码
     * @param cityNo
     *            城市代码
     * @param resStatus
     *            资源状态
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 服务公司可用互生号列表
     */
    public PageData<ResNo> getSerResNos(String countryNo, String provinceNo, String cityNo, Integer resStatus,
            String resNo, Page page);

    /**
     * 查询成员企业、托管企业可用互生号列表
     * 
     * @param serResNo
     *            服务公司互生号
     * @param custType
     *            客户类型
     * @param resType
     *            启用资源类型
     * @param resNo
     *            拟用互生号
     * @param page
     *            分页
     * @return 成员企业或托管企业可用互生号列表
     */
    public PageData<ResNo> getEntResNos(String serResNo, Integer custType, Integer resType, String resNo, Page page);

    /**
     * 查询已占用互生号资源详情
     *
     * @param resNo 互生号
     * @return {@code ResNo}
     * @throws HsException
     */
    ResNo queryUsedSerResNoByResNo(String resNo) throws HsException;
}
