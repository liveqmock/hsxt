package com.gy.hsxt.access.web.scs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.services.codeclaration
 * @className     : EntDeclareService.java
 * @description   : 企业申报接口实现
 * @author        : maocy
 * @createDate    : 2015-10-30
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareService extends BaseServiceImpl implements IEntDeclareService {
    
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类
    
    /**
     * 
     * 方法名称：提交申报
     * 方法描述：企业申报-提交申报
     * @param applyId 申请编号
     * @param operator 操作员信息
     * @throws HsException
     */
    public void submitDeclare(String applyId, OptInfo operator) throws HsException{
        this.bsService.submitDeclare(applyId, operator);
    }
    
    /**
     * 
     * 方法名称：查询服务公司可用互生号
     * 方法描述：企业申报-查询服务公司可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<ResNo> findSerResNos(Map filterMap, Map sortMap, Page page) throws HsException {
    	String countryNo = (String) filterMap.get("countryNo");//国家代码
    	String provinceNo = (String) filterMap.get("provinceNo");//省代码
    	String cityNo = (String) filterMap.get("cityNo");//城市代码
    	String resNo = (String) filterMap.get("resNo");//拟用互生号
    	return this.bsService.getSerResNos(countryNo, provinceNo, cityNo, resNo, page);
    }
    
    /**
     * 
     * 方法名称：查询成员企业、托管企业可用互生号
     * 方法描述：企业申报-查询成员企业、托管企业可用互生号
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<ResNo> findEntResNos(Map filterMap, Map sortMap, Page page) throws HsException {
    	String serResNo = (String) filterMap.get("serResNo");//服务公司互生号
    	Integer custType = CommonUtils.toInteger(filterMap.get("custType"));//客户类型
    	Integer resType = CommonUtils.toInteger(filterMap.get("resType"));//启用资源类型
    	String resNo = (String) filterMap.get("resNo");//拟用互生号
    	return this.bsService.getEntResNos(serResNo, custType, resType, resNo, page);
    }

    /**
     * 校验互生号是否可用
     *
     * @param entResNo 企业资源号
     * @return
     * @throws HsException
     */
    @Override
    public Boolean checkValidEntResNo(String entResNo) throws HsException {
        return bsService.checkValidEntResNo(entResNo);
    }
}
