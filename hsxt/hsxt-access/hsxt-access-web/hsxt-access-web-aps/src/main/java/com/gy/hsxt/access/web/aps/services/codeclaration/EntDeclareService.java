package com.gy.hsxt.access.web.aps.services.codeclaration;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.ResNo;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : EntDeclareService.java
 * @description   : 企业申报接口服务类
 * @author        : maocy
 * @createDate    : 2015-12-22
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class EntDeclareService extends BaseServiceImpl implements IEntDeclareService {
	
    @Resource
    private IBSDeclareService iBSDeclareService;//企业申报服务类
    
    @Resource
    private IUCBsEntService iucBsEntService;//企业申报服务类

    @Resource
    private IUCAsEntService iuCAsEntService;//用户中心接口

	/**
     * 
     * 方法名称：依据互生号查询企业信息
     * 方法描述：企业申报-依据互生号查询企业信息
     * @param resNo 企业互生号
     * @throws HsException
     */
    public BsEntMainInfo findEntInfo(String resNo) throws HsException {
		return this.iucBsEntService.getMainInfoByResNo(resNo);
	}
    
    /**
     * 
     * 方法名称：申报提交
     * 方法描述：企业申报-申报提交
     * @param applyId 申请编号
     * @param operator 操作信息
     * @throws HsException
     */
    public void submitDeclare(String applyId, OptInfo operator) throws HsException {
    	this.iBSDeclareService.submitDeclare(applyId, operator);
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
    	return this.iBSDeclareService.getSerResNos(countryNo, provinceNo, cityNo, resNo, page);
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
    	return this.iBSDeclareService.getEntResNos(serResNo, custType, resType, resNo, page);
    }

    /**
     * 查询推广服务公司互生号资源详情
     *
     * @param resNo 互生号
     * @return
     * @throws HsException
     */
    @Override
    public AsEntExtendInfo findResNoDetail(String resNo) throws HsException {
        String custId = this.iuCAsEntService.findEntCustIdByEntResNo(resNo);
        return this.iuCAsEntService.searchEntExtInfo(custId);
    }

    /**
     * 校验互生号是否可用
     *
     * @param entResNo 企业资源号
     * @return
     * @throws HsException
     */
    @Override
    public Boolean checkValidEntResNo( String entResNo) throws HsException {
        return iBSDeclareService.checkValidEntResNo(entResNo);
    }
}