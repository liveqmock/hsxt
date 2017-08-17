/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.EnterpriceInputParam;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.bs.api.entstatus.IBSMemberQuitService;
import com.gy.hsxt.bs.api.entstatus.IBSPointActivityService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

@SuppressWarnings("rawtypes")
@Service
public class MemberEnterpriseServiceImpl extends BaseServiceImpl implements IMemberEnterpriseService {

    //查询企业状态dubbo接口
    @Autowired
    private IUCAsEntService iuCAsEntService;
    
    @Autowired
    private IUCAsBankAcctInfoService iuCAsBankAcctInfoService;
    
    @Autowired
    private IBSMemberQuitService ibSMemberQuitService;
    
    @Autowired
    private IBSPointActivityService ibSPointActivityService;
    
    @Autowired
    private EnterpriceService enterpriceService;
    
    @Autowired
    private IPsQueryService ipsQueryService;
    
    @Override
    public AsEntStatusInfo searchEntStatusInfo(String custId) throws HsException {
        AsEntStatusInfo info = iuCAsEntService.searchEntStatusInfo(custId);
        return info;
    }


    @Override
    public List<AsBankAcctInfo> findBanksByCustId(String custId, String userType) throws HsException {
        List<AsBankAcctInfo> result = iuCAsBankAcctInfoService.listBanksByCustId(custId, userType);
        return result;
    }

    /**
     * 成员企业注销
     * @param member
     * @throws Exception 
     * @see com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService#createMemberQuit(com.gy.hsxt.bs.bean.entstatus.MemberQuit)
     */
    @Override
    public void createMemberQuit(MemberQuit member) throws Exception {
        
        /**
         * 提交时要检查下列业务，存在任一业务则不能提交申请：
         *   a、网上商城是否有未完结订单
         *   b、是否有未完结工具申购订单（配送单作成前）
         *   c、是否有未完结账款（检查欠款及货币转银行账户申请）
         *   d、是否有提交重要信息变更申请
         *   
         *   bcd在BS创建是自己做判断
         */
        EnterpriceInputParam inputParam = new EnterpriceInputParam();
        inputParam.setEnterpriceResourceNo(member.getEntResNo());
        //网上商城是否有未完结订单
        ReturnResult result = enterpriceService.checkEnterpriceNotCompleteOrderAndRefund(inputParam);
        
        if(result.getRetCode() == 201 || result.getRetCode() == 509){
            throw new HsException(ASRespCode.EW_MEMBER_APPLY_QUIT_HAVE_NOTCOMPLETEORDER);
        }
        
        /*** 根据企业custId查询是否有未结单的交易*/
        if(!ipsQueryService.queryIsSettleByEntCustId(member.getEntCustId())){
            throw new HsException(ASRespCode.EW_ENT_HAVE_ORDER);
        }
        
        //成员企业注销
        ibSMemberQuitService.createMemberQuit(member);
        
        EnterpriceInputParam _inputParam = new EnterpriceInputParam();
        _inputParam.setEnterpriceResourceNo(member.getEntResNo());
        
        /** 通知电商  发起（成员企业资质注销   做如下五件事：
         * 1、变更网上商城以及网上商城下的营业点为冻结状态
         * 2、通知搜索引擎删除网上商城以及网上商城下的营业点（零售、餐饮）的索引
         * 3、删除网上商城下营业点（餐饮）对应的菜单、菜品关联关系
         * 4、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询零售商品，批量更新零售商品为发布状态，同时通知搜索引擎删除网上商城下的商品
         * 5、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询餐饮商品，批量更新餐饮菜品为发布状态，同时通知搜索引擎删除网上商城下的菜品
         **/
        enterpriceService.applyEnterpriceStopPointActivity(inputParam);
    }

    @Override
    public PointActivityStatus queryPointActivityStatus(String entCustId) {
        return ibSPointActivityService.queryPointActivityStatus(entCustId);
    }

    @Override
    public MemberQuitStatus queryMemberQuitStatus(String entCustId) {
        return ibSMemberQuitService.queryMemberQuitStatus(entCustId);
    }

}
