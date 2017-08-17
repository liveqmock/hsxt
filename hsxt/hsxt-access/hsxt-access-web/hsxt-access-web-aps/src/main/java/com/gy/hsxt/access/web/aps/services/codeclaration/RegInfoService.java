package com.gy.hsxt.access.web.aps.services.codeclaration;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareRegInfo;
import com.gy.hsxt.bs.bean.bm.Increment;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : RegInfoService.java
 * @description   : 企业申报-系统注册信息
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class RegInfoService extends BaseServiceImpl implements IRegInfoService {
    
    @Resource
    private IBSDeclareService iBSDeclareService;//企业申报服务类

    /**
     * 
     * 方法名称：查询系统注册信息
     * 方法描述：查询系统注册信息
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    @Override
    public DeclareRegInfo findDeclareByApplyId(String applyId) throws HsException {
        return this.iBSDeclareService.queryDeclareRegInfoByApplyId(applyId);
    }
    
    /**
     * 
     * 方法名称：新增系统注册信息
     * 方法描述：企业申报新增-新增系统注册信息
     * @param regInfo 系统注册信息对象
     * @throws HsException
     */
    @Override
    public String createDeclare(DeclareRegInfo regInfo) throws HsException {
        return this.iBSDeclareService.createDeclare(regInfo);
    }
    
    /**
     * 
     * 方法名称：修改系统注册信息
     * 方法描述：修改系统注册信息
     * @param regInfo 系统注册信息对象
     * @throws HsException
     */
    @Override
    public void updateDeclare(DeclareRegInfo regInfo) throws HsException {
        this.iBSDeclareService.platModifyDeclare(regInfo);
    }
    
    /**
     * 
     * 方法名称：查询管理公司信息和服务公司配额数
     * 方法描述：企业申报新增-查询管理公司信息和服务公司配额数
     * @param countryNo 所属国家代码
     * @param provinceNo 所属省份代码
     * @param cityNo 所属城市代码
     * @return
     * @throws HsException
     */
    @Override
    public Map<String, Object> queryManageEntAndQuota(String countryNo, String provinceNo, String cityNo) throws HsException {
        return this.iBSDeclareService.queryManageEntAndQuota(countryNo, provinceNo, cityNo);
    }

    /**
     * 
     * 方法名称：查询企业配额数和可用互生号列表
     * 方法描述：企业申报新增-查询成员企业、托管企业配额数和可用互生号列表
     * @param serResNo 服务公司互生号
     * @param custType 客户类型（2：成员企业 3：托管企业）
     * @param buyResRange 启用消费者资源类型(托管企业传入此参数)
     * @return
     * @throws HsException
     */
    @Override
    public Map<String, Object> getResNoListAndQuota(String serResNo, Integer custType, Integer buyResRange) throws HsException {
        return this.iBSDeclareService.getResNoListAndQuota(serResNo, custType, buyResRange);
    }
    
    /**
     * 
     * 方法名称：根据申请编号查询客户类型
     * 方法描述：企业申报新增-根据申请编号查询客户类型
     * @param applyId 申请编号
     * @return
     */
    @Override
    public Integer getCustTypeByApplyId(String applyId) {
        return this.iBSDeclareService.getCustTypeByApplyId(applyId);
    }

    /**
     * 
     * 方法名称：查询积分增值点信息
     * 方法描述：企业申报新增-查询积分增值点信息
     * @param spreadEntResNo 企业互生号
     * @param subResNo 企业互生号
     * @return
     */
    @Override
    public Increment queryIncrement(String spreadEntResNo,String subResNo) {
        return this.iBSDeclareService.queryIncrement(spreadEntResNo,subResNo);
    }
    

    
    /**
     * 
     * 方法名称：查询企业服务公司互生号列表
     * 方法描述：企业申报-查询企业服务公司互生号列表
     * @param countryNo 所属国家代码
     * @param provinceNo 所属省份代码
     * @param cityNo 所属城市代码
     * @return
     */
    public List<String> findSerResNoList(String countryNo, String provinceNo, String cityNo){
    	return this.iBSDeclareService.getSerResNoList4Plat(countryNo, provinceNo, cityNo);
    }
	
}
