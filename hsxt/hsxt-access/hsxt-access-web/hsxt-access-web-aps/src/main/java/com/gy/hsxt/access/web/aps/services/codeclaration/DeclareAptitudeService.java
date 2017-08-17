package com.gy.hsxt.access.web.aps.services.codeclaration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareAptitude;
import com.gy.hsxt.bs.bean.base.OptInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.codeclaration
 * @className     : DeclareAptitudeService.java
 * @description   : 企业申报-企业资料上传接口实现
 * @author        : maocy
 * @createDate    : 2015-12-15
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class DeclareAptitudeService extends BaseServiceImpl implements IDeclareAptitudeService {
    
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

    /**
     * 
     * 方法名称：查询企业上传资料
     * 方法描述：查询企业上传资料
     * @param applyId 申请编号
     * @return
     * @throws HsException
     */
    @Override
    public Map<String, Object> queryAptitudeWithRemarkByApplyId(String applyId) throws HsException {
        return this.bsService.queryAptitudeWithRemarkByApplyId(applyId);
    }
    
    /**
     * 
     * 方法名称：修改企业上传资料
     * 方法描述：修改企业上传资料
     * @param aptList 上传资料集合对象
     * @param info 操作员信息
     * @param aptRemark 备注说明
     * @throws HsException
     */
    @Override
    public List<DeclareAptitude> saveAptitude(List<DeclareAptitude> aptList, OptInfo info, String aptRemark) throws HsException {
        return this.bsService.platSaveDeclareAptitude(aptList, info, aptRemark);
    }
	
}