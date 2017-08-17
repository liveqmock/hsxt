package com.gy.hsxt.access.web.mcs.services.resoucemanage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.quota.result.CompanyRes;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.resoucemanage
 * @className     : IResGlanceService.java
 * @description   : 资源配额一览表
 * @author        : maocy
 * @createDate    : 2016-02-17
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IResGlanceService extends IBaseService{
    
    /**
     * 
     * 方法名称：统计管理公司下的资源数据
     * 方法描述：统计管理公司下的资源数据
     * @param mEntResNo 管理公司互生号
     * @return
     * @throws HsException
     */
    public QuotaStatOfManager statResDetailOfManager(String mEntResNo) throws HsException;
    
    /**
     * 
     * 方法名称：统计二级区域下的资源数据
     * 方法描述：统计二级区域下的资源数据
     * @param provinceNo 省份代码
     * @return
     * @throws HsException
     */
    public QuotaDetailOfProvince statResDetailOfProvince(String provinceNo, String cityNo);
    
    /**
     * 
     * 方法名称：三级区域(城市)下的资源列表
     * 方法描述：三级区域(城市)下的资源列表
     * @param cityNo 城市代码
     * @param status 状态
     * @return
     * @throws HsException
     */
    public List<ResInfo> listResInfoOfCity(String cityNo, String[] status) throws HsException;
    
    /**
     * 
     * 方法名称：统计管理公司下的企业资源
     * 方法描述：统计管理公司下的企业资源
     * @param mEntResNo 管理公司互生号
     * @return
     * @throws HsException
     */
    public CompanyRes statResCompanyResM(String mEntResNo);

}
