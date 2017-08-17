/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.CityQuotaApp;
import com.gy.hsxt.bs.bean.quota.CityQuotaQueryParam;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.bean.quota.ProvinceQuotaApp;
import com.gy.hsxt.bs.bean.quota.result.AllotProvince;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfCity;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 区域配额
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: AreaQuotaQueryService
 * @Description: TODO 区域配额分页、单条明细查询
 * @author: wangjg
 * @date: 2015-11-18 下午4:03:27
 * @version V1.0
 */
@Service
public class AreaQuotaQueryServiceImpl extends BaseServiceImpl implements IAreaQuotaQueryService {

    @Resource
    private IBSQuotaService ibsQuotaService;
    @Autowired
    IUCAsOperatorService operatorService;
    
    /**
     * 一级区域配额申请分页查询
     * 
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#applyPlatQuotaPage()
     */
    @Override
    public List<PlatAppManage> applyPlatQuotaPage() throws HsException {

        // 1、一级区域配额分配申请(管理公司列表)
        List<PlatAppManage> pamList = ibsQuotaService.queryPlatAppManageList();

        // 2、空对象验证
        if (pamList == null) {
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }
        
        return pamList;
    }

    /**
     * 一级区域配额申请查询分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @throws HsException
     * @return
     */
    @Override
    public PageData<?> applyPlatQuotaQueryPage(Map filterMap, Map sortMap, Page page) throws HsException {
        //查询条件
        String mEntResNo = (String) filterMap.get("mEntResNo");
        String mEntResName = (String) filterMap.get("mEntResName");
        String exeCustId = (String) filterMap.get("exeCustId");

        // 分页结果
        return ibsQuotaService.queryPlatQuotaPage(mEntResNo, mEntResName, exeCustId, page);
    }

    /**
     * 查询一级区域(地区平台)配额分配详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#applyPlatQuotaDetail(java.lang.String)
     */
    @Override
    public PlatQuotaApp applyPlatQuotaDetail(String applyId) throws HsException {

        //1、根据id查询地区平台(一级区域)配额申请详情
        PlatQuotaApp pqa= ibsQuotaService.getPlatQuotaById(applyId);
        // 设置操作员名称
        try{
        	// 只有ID为19位时才查询
        	if(StringUtils.isNotBlank(pqa.getReqOperator()) && pqa.getReqOperator().length() == 19){
	        	AsOperator operator = operatorService.searchOperByCustId(pqa.getReqOperator());
	        	if(operator != null){
	        		pqa.setReqOperator(operator.getUserName());
	        	}
        	}
        }catch(Exception e){
        	
        }
        // 2、空对象验证
        if (pqa == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }
        
        return pqa;
    }

    /**
     * 查询管理公司可以进行分配或调整配额的二级区域
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#getProvinceNoAllot(java.lang.String)
     */
    @Override
    public List<String> getProvinceNoAllot(String mEntResNo) throws HsException {
        
        //1、查找管理公司下级未分配和已分配配额省份
        List<String>  provinceList= ibsQuotaService.listProvinceNoForAllot(mEntResNo);
        
        return provinceList;
    }

    /**
     * 二级区域配额配置查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#provinceQuotaPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> provinceQuotaPage(Map filterMap, Map sortMap, Page page) throws HsException {
       
        String proviceNo = (String) filterMap.get("proviceNo");                 // 省
        Integer applyType = CommonUtils.toInteger(filterMap.get("applyType"));  // 配置类型   
        Integer status =CommonUtils.toInteger(filterMap.get("status"));         // 状态
        String exeCustId = (String) filterMap.get("exeCustId");                 // 经办人

        // 分页查询
        PageData<?> pqPageData = ibsQuotaService.queryProvinceQuotaPage(proviceNo, applyType, status, exeCustId, page);

        return pqPageData;
    }

    /**
     * 二级区域配额配置详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#provinceQuotaDetail(java.lang.String)
     */
    @Override
    public ProvinceQuotaApp provinceQuotaDetail(String applyId) throws HsException 
    {
        //1、根据id查询省级(二级区域)配额申请
        ProvinceQuotaApp pqa = ibsQuotaService.getProvinceQuotaById(applyId);

        // 2、空对象验证
        if (pqa == null) {
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }
        
        return pqa;
    }

    /**
     * 查询地区平台分配了配额的省(省代码 + 管理公司互生号)
     * 
     * @return
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#allocatedQuotaProvinceQuery()
     */
    @Override
    public List<AllotProvince> allocatedQuotaProvinceQuery()  throws HsException
    {
        //查询地区平台分配了配额的省
        return ibsQuotaService.queryAllotProvinceList();
        
    }

    /**
     * 三级区域配额配置申请分页查询
     * 
     * @param provinceNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#cityQuotaApplyPage(java.lang.String)
     */
    @Override
    public List<QuotaStatOfCity> cityQuotaApplyPage(String provinceNo) throws HsException 
    {
        //查询省下申请城市配额分配的列表
        return ibsQuotaService.queryAppCityAllotList(provinceNo);
       
    }

    /**
     * 三级区域配额配置查询分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#cityQuotaApplyQueryPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> cityQuotaApplyQueryPage(Map filterMap, Map sortMap, Page page) throws HsException {

        // 1、map转查询对象
        CityQuotaQueryParam cqqp = mapConvertObj(filterMap);

        // 2、分页查询市级(三级区域)配额分配
        return ibsQuotaService.queryCityQuotaPage(cqqp, page);
    }

    /**
     * map转换成类
     * 
     * @param filterMap
     * @return
     */
    CityQuotaQueryParam mapConvertObj(Map filterMap) {
        CityQuotaQueryParam cqqp = new CityQuotaQueryParam();

        cqqp.setStartDate((String) filterMap.get("startDate"));                 // 开始时间
        cqqp.setEndDate((String) filterMap.get("endDate"));                     // 结束时间
        cqqp.setCityNo((String) filterMap.get("cityNo"));                       // 城市编号
        cqqp.setCityName((String) filterMap.get("cityName"));                   // 城市名称
        cqqp.setExeCustId((String) filterMap.get("exeCustId"));                 // 经办人
        cqqp.setApplyType(CommonUtils.toInteger(filterMap.get("applyType")));   // 配置类型
        cqqp.setStatus(CommonUtils.toInteger(filterMap.get("status")));         // 状态

        return cqqp;
    }

    /**
     * 三级区域配额配置详情
     * 
     * @param applyId
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#cityQuotaDetail(java.lang.String)
     */
    @Override
    public CityQuotaApp cityQuotaDetail(String applyId) throws HsException 
    {
        //1、获取配额配置详情
        CityQuotaApp cqa= ibsQuotaService.getCityQuotaById(applyId);
        
        // 2、空对象验证
        if (cqa == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }
        
        return cqa;
    
    }

    /**
     * 统计城市配额分配使用情况
     * @param provinceNo
     * @param cityNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaQueryService#statQuotaByCity(java.lang.String, java.lang.String)
     */
    @Override
    public QuotaStatOfCity statQuotaByCity(String provinceNo, String cityNo) throws HsException {
        return ibsQuotaService.statQuotaByCity(provinceNo, cityNo);
    }

}
