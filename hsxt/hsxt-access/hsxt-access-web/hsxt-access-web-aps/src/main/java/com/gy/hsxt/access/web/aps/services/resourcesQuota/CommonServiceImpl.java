/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.resourcesQuota;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.quota.IBSQuotaService;
import com.gy.hsxt.bs.bean.quota.result.PlatAppManage;
import com.gy.hsxt.bs.bean.quota.result.QuotaStatOfManager;
import com.gy.hsxt.bs.bean.quota.result.ResInfo;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/***
 * 公共服务方法
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.resourcesQuota
 * @ClassName: CommonService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-18 上午10:29:56
 * @version V1.0
 */
@Service("resQuotaComService")
public class CommonServiceImpl extends BaseServiceImpl implements ICommonService {

    @Resource
    private IUCAsEntService iuCAsEntService;

    @Resource
    private IBSQuotaService ibsQuotaService;

    /**
     * 获取地区平台下的管理公司
     * 
     * @param apsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.ICommonService#getMEntResList(com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public List<Map<String, Object>> getMEntResList(ApsBase apsBase) throws HsException {

        List<Map<String, Object>> retMapList = new ArrayList<>();// 返回集合map
        Map<String, Object> tempMap = null; // 临时记录map值

        // 1、获取管理公司
        List<AsEntInfo> aeiList = iuCAsEntService.listEntInfo(CustType.MANAGE_CORP.getCode());
        
        // 2、按照管理公司互生号排序
        Collections.sort(aeiList, new Comparator<AsEntInfo>() {
            @Override
            public int compare(AsEntInfo o1, AsEntInfo o2) {
                return o1.getEntResNo().compareTo(o2.getEntResNo());
            }});
        
        ///3、循环集合
        for (AsEntInfo aei : aeiList)
        {
            tempMap = new HashMap<String, Object>();
            tempMap.put("mEntResNo", aei.getEntResNo());
            retMapList.add(tempMap);
        }

        return retMapList;
    }

    /**
     * 获取管理公司配额详情
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.ICommonService#getMEntResDetail(java.lang.String)
     */
    @Override
    public Map<String, Object> getMEntResDetail(String mEntResNo) throws HsException {
        Map<String, Object> retMap = new HashMap<String, Object>(); // 返回结果

        // 1、调用管理公司配额详情
        QuotaStatOfManager qsom = ibsQuotaService.statResDetailOfManager(mEntResNo);

        // 2、验证对象数据存在
        if (qsom == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        // 3、组合map数据
        retMap.put("mEntResNo", qsom.getmEntResNo());
        retMap.put("mCustName", qsom.getmCustName());
        retMap.put("sResNum", qsom.getsResNum());
        retMap.put("planNum", qsom.getPlanNum());
        retMap.put("notPlanNum", qsom.getNotPlanNum());
        retMap.put("useNum", qsom.getUseNum());
        retMap.put("usableNum", qsom.getUsableNum());

        return retMap;
    }

    /**
     * 城市资源状态详情
     * @param cityNo
     * @param status
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.ICommonService#cityResStatusDetail(java.lang.String, java.lang.String[])
     */
    @Override
    public List<ResInfo> cityResStatusDetail(String cityNo,String [] status) throws HsException {

        // 1、城市(三级区域)下的资源列表
        List<ResInfo> riList =ibsQuotaService.listResInfoOfCity(cityNo,status);

        // 2、验证对象数据存在
        if (riList == null){
            throw new HsException(RespCode.GL_DATA_NOT_FOUND);
        }

        return riList;
    }

    /**
     * 管理公司配额分配详情
     * 
     * @param mEntResNo
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.resourcesQuota.ICommonService#manageAllotDetail(java.lang.String)
     */
    @Override
    public PlatAppManage manageAllotDetail(String mEntResNo) throws HsException {

        // 1、管理公司配额分配详情
        PlatAppManage pam = ibsQuotaService.queryManageAllotDetail(mEntResNo);

        // 2、验证对象数据存在
        if (pam == null){
            pam = new PlatAppManage();
        }

        return pam;
    }
}
