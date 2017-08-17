/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.platformDebit;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.platformDebit.ApsHsbDeduction;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.deduction.IBSHsbDeductionService;
import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.bs.bean.deduction.HsbDeductionQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.CerTypeEnum;

/***
 * 平台扣款业务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.platformDebit
 * @ClassName: PlatformDebitServiceImpl
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-4-19 下午2:48:44
 * @version V1.0
 */
@Service
public class PlatformDebitServiceImpl extends BaseServiceImpl implements IPlatformDebitService {
    
    /**
     * 注入平台业务扣款服务
     */
    @Resource
    private IBSHsbDeductionService ibsHsbDeductionService;
    
    /**
     * 注入消费者信息服务
     */
    @Resource
    private IUCAsCardHolderService asCardHolderService;
    
    /**
     * 注入持卡人证件信息管理服务
     */
    @Resource
    private IUCAsCardHolderAuthInfoService iucAsCardHolderAuthInfoService;
    
    /**
     * 注入企业服务
     */
    @Resource
    private IUCAsEntService iuCAsEntService;
    
    /**
     * 注入账户余额查询服务
     */
    @Resource
    private IBalanceService balanceService;
    
    /**
     * 业务参数服务接口
     */
    @Autowired
    private BusinessParamSearch businessParamSearch;
    

    /**
     * 创建扣款申请查询类
     * @param filterMap
     * @return
     */
    private HsbDeductionQuery createHDQ(Map filterMap) {
        HsbDeductionQuery hdq = new HsbDeductionQuery();

        hdq.setStartDate((String) filterMap.get("queryStartDate"));             // 开始时间
        hdq.setEndDate((String) filterMap.get("queryEndDate"));                 // 结束时间
        hdq.setEntResNo((String) filterMap.get("queryResNo"));                  // 企业互生号
        hdq.setStatus(CommonUtils.toInteger(filterMap.get("queryStatus")));     // 状态
        hdq.setOptCustId((String) filterMap.get("custId"));                     //操作员

        return hdq;
    }

    /**
     * 分页查询平台互生币扣款申请列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#queryHsbDeductionListPage(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<HsbDeduction> queryHsbDeductionListPage(Map filterMap, Map sortMap, Page page) throws HsException {
        
        //获取查询条件
        HsbDeductionQuery hdq=this.createHDQ(filterMap);
        
        return ibsHsbDeductionService.queryHsbDeductionListPage(page, hdq);
    }

    /**
     * 分页查询平台互生币扣款申请复核列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#queryTaskListPage(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<HsbDeduction> queryTaskListPage(Map filterMap, Map sortMap, Page page) throws HsException {
        
        //创建查询条件类
        HsbDeductionQuery hdq=this.createHDQ(filterMap);
        
        return ibsHsbDeductionService.queryTaskListPage(page, hdq);
    }

    /**
     * 查询某一条平台互生币扣款申请
     * @param applyId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#queryDetailById(java.lang.String)
     */
    @Override
    public HsbDeduction queryDetailById(String applyId) throws HsException {
       
        return ibsHsbDeductionService.queryDetailById(applyId);
    }

    /**
     * 复核平台互生币扣款申请
     * @param hsbDeduction
     * @param apsBase
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#apprHsbDeduction(com.gy.hsxt.bs.bean.deduction.HsbDeduction, com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public boolean apprHsbDeduction(HsbDeduction hsbDeduction, ApsBase apsBase) throws HsException {
        //设置操作员信息
        hsbDeduction.setApprOper(apsBase.getCustId());
        hsbDeduction.setApprName(apsBase.getCustName()+"("+apsBase.getOperName()+")");
        
        return ibsHsbDeductionService.apprHsbDeduction(hsbDeduction);
    }

    /**
     * 平台扣款申请
     * @param ahd
     * @param apsBase
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#applyHsbDeduction(com.gy.hsxt.access.web.bean.platformDebit.ApsHsbDeduction, com.gy.hsxt.access.web.bean.ApsBase)
     */
    @Override
    public String applyHsbDeduction(ApsHsbDeduction ahd, ApsBase apsBase) throws HsException {
        // 设置申请人信息
        ahd.setApplyOper(apsBase.getCustId());
        ahd.setApplyName(apsBase.getCustName() + "(" + apsBase.getOperName() + ")");

        // 验证扣款金额充足
        chkDeductAmount(ahd);

        // 申请扣款
        return ibsHsbDeductionService.applyHsbDeduction(ahd.convertHsbDeduction());
    }
    
    /**
     * 验证扣款金额充足
     * 
     * @param ahd
     */
    private void chkDeductAmount(ApsHsbDeduction ahd) throws HsException {
        double minimum = 0;                                                     //最低保底值
        double accountAmount = 0;                                               //账户金额
        double deductAmount = DoubleUtil.parseDouble(ahd.getAmount());          //扣款金额
        
        // 互生号类型
        Integer resNoType = HsResNoUtils.getCustTypeByHsResNo(ahd.getDeductResNo());
        
        //账户余额
        AccountBalance ab = balanceService.findAccNormal(ahd.getDeductCustId(), AccountType.ACC_TYPE_POINT20110.getCode());
        
        //获取账户金额
        if (null != ab && StringUtils.isNotBlank(ab.getAccBalance()))  {
             accountAmount = DoubleUtil.parseDouble(ab.getAccBalance());       
        }
        
        //获取成员企业、托管企业互生币保底值
        if(resNoType == CustType.TRUSTEESHIP_ENT.getCode() || 
           resNoType == CustType.MEMBER_ENT.getCode()){
            
            //获取保底值
            String savingVal = businessParamSearch.searchSysParamItemsByCodeKey(BusinessParam.PUB_ACCOUNT_THRESHOLD.getCode(), BusinessParam.C_SAVING_HSB.getCode()).getSysItemsValue();
            if(StringUtils.isNotBlank(savingVal)){
                minimum = DoubleUtil.parseDouble(savingVal);
            }
        }
        
        //扣款金额大于账户金额时，抛异常
        if (deductAmount > (accountAmount - minimum))  {
            throw new HsException(ASRespCode.APS_DEDUCT_ACCOUNT_AMOUNT_INSUFFICIENT);
        }
    }

    /**
     * 通过互生号查找详情
     * @param resNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.aps.services.platformDebit.IPlatformDebitService#getResNoInfo(java.lang.String)
     */
    @Override
    public Map<String, Object> getResNoInfo(String resNo) throws HsException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        Integer resNoType = HsResNoUtils.getCustTypeByHsResNo(resNo);   // 获取互生号类型
        retMap.put("custType", resNoType);                              //互生号类型
        
        //非空验证
        if(null == resNoType){
            // 扣款业务只支持消费者、成员企业、托管企业、服务公司
            throw new HsException(ASRespCode.APS_DEDUCT_TYPE_ERROR);
        }

        // 消费者
        if (resNoType == CustType.PERSON.getCode()) {
            // 消费者客户操作号
            String personCustId = asCardHolderService.findCustIdByResNo(resNo);
            if (StringUtils.isBlank(personCustId)) {
               throw new HsException(ASRespCode.APS_DEDUCT_RESNO_ERROR);
            }
            
            // 获取实名注册信息
            AsCardHolderAllInfo achai = asCardHolderService.searchAllInfo(personCustId);
            retMap.put("resNoCustId", personCustId);
            retMap.put("superEntResNo", achai.getBaseInfo().getEntResNo());
            
            if(CerTypeEnum.BUSILICENCE.getType().toString().equals(achai.getAuthInfo().getCerType())){//营业执照
                retMap.put("name", achai.getAuthInfo().getEntName());
            }else{//身份证、护照
                retMap.put("name", achai.getAuthInfo().getUserName());
            }
            
        } else if(  resNoType == CustType.SERVICE_CORP.getCode() || 
                    resNoType == CustType.TRUSTEESHIP_ENT.getCode() || 
                    resNoType == CustType.MEMBER_ENT.getCode()) {// 成员企业、托管企业、服务公司

            // 查找企业详情
            AsEntMainInfo entInfo = iuCAsEntService.getMainInfoByResNo(resNo);
            if (entInfo == null) {
                throw new HsException(ASRespCode.APS_DEDUCT_RESNO_ERROR);
             }

            retMap.put("resNoCustId", entInfo.getEntCustId());
            retMap.put("name", entInfo.getEntName());
        }else{
            // 扣款业务只支持消费者、成员企业、托管企业、服务公司
            throw new HsException(ASRespCode.APS_DEDUCT_TYPE_ERROR);
        }

        return retMap;
    }

}
