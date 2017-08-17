/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsPlatQuotaApp;
import com.gy.hsxt.access.web.bean.resourcesQuota.ApsProvinceQuotaApp;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.PlatQuotaApp;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.quota.AppType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;

/***
 * 区域配额操作服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: AreaQuotaOperateService
 * @Description: TODO 区域配额申请、修改、初始化操作
 * @author: wangjg
 * @date: 2015-11-18 下午4:04:11
 * @version V1.0
 */
@Service
public class AreaQuotaOperateServiceImpl extends BaseServiceImpl implements IAreaQuotaOperateService {

    @Resource
    private IBSQuotaService ibsQuotaService;

    // 全局配置服务
    @Autowired
    private LcsClient ilcsBaseDataService;

    /**
     * 平台信息
     * 
     * @return
     */
    LocalInfo getLocalInfo() throws HsException {
        // 1、获取平台详情
        LocalInfo li = ilcsBaseDataService.getLocalInfo();

        // 2、空对象验证
        if (li == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        return li;

    }

    /**
     * 一级区域配额申请
     * 
     * @param apqa
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#applyPlatQuota(com.gy.hsxt.access.web.bean.resourcesQuota.ApsPlatQuotaApp)
     */
    @Override
    public void applyPlatQuota(ApsPlatQuotaApp apqa) throws HsException {
        // 1、设置申请参数
        apqa.setPlatNo(this.getLocalInfo().getPlatNo());    //平台代码
        apqa.setApplyType(AppType.ADD.getCode());           //配置类型
        apqa.setStatus(ApprStatus.WAIT_APPR.getCode());     //默认状态
        apqa.setReqOperator(apqa.getCustId());              //申请操作员
        apqa.setEntResNo(apqa.getApplyEntResNo());          //申请管理公司

        // 2、申请
        ibsQuotaService.applyPlatQuota((PlatQuotaApp)apqa);
    }

    /**
     * 二级区域配额申请
     * 
     * @param apqa
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#provinceQuotaApply(com.gy.hsxt.access.web.bean.resourcesQuota.ApsProvinceQuotaApp)
     */
    @Override
    public void provinceQuotaApply(ApsProvinceQuotaApp apqa) throws HsException {

        // 1、获取平台信息
        LocalInfo li = this.getLocalInfo();

        // 2、赋值
        apqa.setCountryNo(li.getCountryNo());       //国家代码
        apqa.setReqOperator(apqa.getCustId());      //操作员
        apqa.setEntResNo(apqa.getApplyEntResNo());  //管理公司

        // 3、插入新增数据
        ibsQuotaService.applyProvinceQuota(apqa);
    }

    /**
     * 二级区域配额审批
     * 
     * @param apqa
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#provinceQuotaApprove(com.gy.hsxt.access.web.bean.resourcesQuota.ApsProvinceQuotaApp)
     */
    @Override
    public void provinceQuotaApprove(ApsProvinceQuotaApp apqa) throws HsException {
        apqa.setApprOperator(apqa.getCustId());//审核员
        
        // 二级区域审批
        ibsQuotaService.apprProvinceQuota(apqa);
    }

    /**
     * 三级区域配额修改
     * 
     * @param acqa
     * @param apsBase
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#cityQuotaUpdate(com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp,
     *      com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public void cityQuotaUpdate(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException {
        // 1、设置申请参数
        setCityQuotaUpdateParam(acqa, apsBase);

        // 2、三级区域配额修改
        ibsQuotaService.applyCityQuota(acqa);
    }

    /**
     * 三级区域配额初始化
     * 
     * @param acqa
     * @param apsBase
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#cityQuotaInit(com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp,
     *      com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public void cityQuotaInit(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException {
        // 1、设置申请配额参数
        setCityQuotaInitParam(acqa, apsBase);

        // 2、初始化三级区域配额
        ibsQuotaService.applyCityQuota(acqa);
    }

    /**
     * 设置三级区域配额参数
     * 
     * @param acqa
     * @param apsBase
     */
    void setCityQuotaParam(ApsCityQuotaApp acqa, ApsBase apsBase) {
        // 1、获取平台信息
        LocalInfo li = this.getLocalInfo();

        // 2、构建属性值
        acqa.setCountryNo(li.getCountryNo());
        acqa.setEntResNo(acqa.getQuotaEntResNo());
        acqa.setReqOperator(apsBase.getCustId());
    }

    /**
     * 设置三级区域配额初始化参数
     * 
     * @param acqa
     * @param apsBase
     */
    void setCityQuotaInitParam(ApsCityQuotaApp acqa, ApsBase apsBase) {
        // 1、设置基础值
        setCityQuotaParam(acqa, apsBase);

        // 2、设置初始化状态
        acqa.setApplyType(AppType.FIRST.getCode());
    }

    /**
     * 设置三级区域配额修改参数
     * 
     * @param acqa
     * @param apsBase
     */
    void setCityQuotaUpdateParam(ApsCityQuotaApp acqa, ApsBase apsBase) {
        // 设置基础值
        setCityQuotaParam(acqa, apsBase);
    }

    /**
     * 三级区域配额审批
     * 
     * @param acqa
     * @param apsBase
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.IAreaQuotaOperateService#cityQuotaApprove(com.gy.hsxt.access.web.bean.resourcesQuota.ApsCityQuotaApp,
     *      com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public void cityQuotaApprove(ApsCityQuotaApp acqa, ApsBase apsBase) throws HsException {

        // 1、审核参数
        acqa.setApprOperator(acqa.getCustId());  //审批操作员
        acqa.setExeCustId(acqa.getCustId());     //经办人

        // 2、审批
        ibsQuotaService.apprCityQuota(acqa);
    }

}
