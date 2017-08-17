/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.bean.quota.result.AllotProvince;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfCity;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***
 * 区域配额
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: IAreaQuotaService
 * @Description: TODO 区域配额分页查询、单条明细操作
 * @author: wangjg
 * @date: 2015-11-16 下午2:38:34
 * @version V1.0
 */
public interface IAreaQuotaQueryService extends IBaseService {

    /**
     * 一级区域配额申请分页查询
     * 
     * @return
     * @throws HsException
     */
    public List<PlatAppManage> applyPlatQuotaPage() throws HsException;

    /**
     * 一级区域配额申请查询分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> applyPlatQuotaQueryPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 查询一级区域(地区平台)配额分配详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     */
    public PlatQuotaApp applyPlatQuotaDetail(String applyId) throws HsException;

    /**
     * 查询管理公司可以进行分配或调整配额的二级区域
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     */
    public List<String> getProvinceNoAllot(String mEntResNo) throws HsException;

    /**
     * 二级区域配额配置查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> provinceQuotaPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 二级区域配额配置详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     */
    public ProvinceQuotaApp provinceQuotaDetail(String applyId) throws HsException;

    /**
     * 查询地区平台分配了配额的省(省代码 + 管理公司互生号)
     * 
     * @return
     */
    public List<AllotProvince> allocatedQuotaProvinceQuery() throws HsException;

    /**
     * 三级区域配额配置申请分页查询
     * 
     * @param provinceNo
     * @return
     * @throws HsException
     */
    public List<QuotaStatOfCity> cityQuotaApplyPage(String provinceNo) throws HsException;

    /**
     * 三级区域配额配置查询分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> cityQuotaApplyQueryPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 三级区域配额配置详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     */
    public CityQuotaApp cityQuotaDetail(String applyId) throws HsException;

    /**
     * 统计城市配额分配使用情况
     * @param provinceNo
     * @param cityNo
     * @return
     * @throws HsException
     */
    public QuotaStatOfCity statQuotaByCity(String provinceNo, String cityNo) throws HsException;
}
