package com.gy.hsxt.access.web.mcs.services.resoucemanage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.result.CompanyRes;
import com.gy.hsxt.bs.bean.quota.result.CompanyResS;
import com.gy.hsxt.bs.bean.quota.result.QuotaDetailOfProvince;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.resoucemanage
 * @className     : ResGlanceService.java
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
public class ResGlanceService extends BaseServiceImpl implements IResGlanceService {
    
    @Autowired
    private IBSQuotaService bsService;//BS服务类

    /**
     * 
     * 方法名称：统计管理公司下的资源数据
     * 方法描述：统计管理公司下的资源数据
     * @param mEntResNo 管理公司互生号
     * @return
     * @throws HsException
     */
    public QuotaStatOfManager statResDetailOfManager(String mEntResNo) throws HsException {
    	return this.bsService.statResDetailOfManager(mEntResNo);
    }
    
    /**
     * 
     * 方法名称：统计二级区域下的资源数据
     * 方法描述：统计二级区域下的资源数据
     * @param provinceNo 省份代码
     * @return
     * @throws HsException
     */
    public QuotaDetailOfProvince statResDetailOfProvince(String provinceNo, String cityNo) {
    	return this.bsService.statResDetailOfProvince(provinceNo, cityNo);
    }
    
    /**
     * 
     * 方法名称：三级区域(城市)下的资源列表
     * 方法描述：三级区域(城市)下的资源列表
     * @param cityNo 城市代码
     * @param status 状态
     * @return
     * @throws HsException
     */
    public List<ResInfo> listResInfoOfCity(String cityNo, String[] status) throws HsException {
    	return this.bsService.listResInfoOfCity(cityNo, status);
    }

    /**
     * 
     * 方法名称：统计管理公司下的企业资源
     * 方法描述：统计管理公司下的企业资源
     * @param mEntResNo 管理公司互生号
     * @return
     * @throws HsException
     */
	@Override
	public CompanyRes statResCompanyResM(String mEntResNo) {
		return this.bsService.statResCompanyResM(mEntResNo);
	}
	
    /**
     * 
     * 方法名称：统计服务公司的企业资源
     * 方法描述：统计服务公司的企业资源
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return PageData 分页对象
     * @throws HsException
     */
	@Override
	public PageData<CompanyResS> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String entResNo = (String) filterMap.get("pointNo");//管理公司互生号
		return this.bsService.queryCompanyResMByPage(entResNo, page);
	}
	
}
