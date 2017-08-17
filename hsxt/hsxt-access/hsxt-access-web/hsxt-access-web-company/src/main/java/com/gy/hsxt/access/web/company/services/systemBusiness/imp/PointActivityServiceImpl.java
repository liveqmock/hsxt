/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.EnterpriceInputParam;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointActivityService;
import com.gy.hsxt.bs.api.entstatus.IBSPointActivityService;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;


@SuppressWarnings("rawtypes")
@Service
public class PointActivityServiceImpl extends BaseServiceImpl implements IPointActivityService {

    @Autowired
    private  IBSPointActivityService ibSPointActivityService;
    @Autowired
    private EnterpriceService enterpriceService;
    @Autowired
    private IPsQueryService ipsQueryService;
    
    @Override
    public void pointActivityApply(PointActivity activity) throws Exception {
        
        /**
         * 如果是停止积分活动 则要判断电商处是否有未完结订单
         * 企业在申请停止积分活动时，必须符号以下条件：
         * 1.网上商城没有未完结订单（所有订单都已完成结算和确认收货）；
         * 2.没有未完结工具申购订单（所有工具申购订单都在已签收状态）；
         * 3.没有未完结账款（商业服务费（无欠商业服务费）、外卖月租费（无欠月租费）、系统使用年费（无欠年费）、日结销售收入（已完成所有订单结算）、欠款开启企业系统（已缴清欠款））；
         * 4.不在重要信息变更申请期间（未申请重要信息变更或重要信息变更已通过审批）；
         * 2/3/4 在调用BS接口时，BS自己做判断
         */
        if(PointAppType.STOP_PONIT_ACITIVITY.getCode() == activity.getApplyType()){
            EnterpriceInputParam inputParam = new EnterpriceInputParam();
            inputParam.setEnterpriceResourceNo(activity.getEntResNo());
            //验证电商是否有未完结订单
            ReturnResult result = enterpriceService.checkEnterpriceNotCompleteOrderAndRefund(inputParam);
            if(result.getRetCode() == 201 || result.getRetCode() == 509){
                throw new HsException(ASRespCode.EW_POINT_APPLY_QUIT_HAVE_NOTCOMPLETEORDER);
            }
            
            //根据custId查询是否有未结算的预付定金的交易
            if(!ipsQueryService.queryPrePayByCustId(activity.getEntCustId())){
                throw new HsException(ASRespCode.EW_POINT_APPLY_QUIT_HAVE_NOTCOMPLETETRANS);
            }
        }
        
        //申请停止/参与积分活动
        ibSPointActivityService.createPointActivity(activity);
        
        //如果是停止积分活动 通知电商系统做相应处理
        if(PointAppType.STOP_PONIT_ACITIVITY.getCode() == activity.getApplyType()){
            
            EnterpriceInputParam inputParam = new EnterpriceInputParam();
            inputParam.setEnterpriceResourceNo(activity.getEntResNo());
            
            /**发起（企业申请停止积分活动   做如下五件事：
             * 1、变更网上商城以及网上商城下的营业点为冻结状态
             * 2、通知搜索引擎删除网上商城以及网上商城下的营业点（零售、餐饮）的索引
             * 3、删除网上商城下营业点（餐饮）对应的菜单、菜品关联关系
             * 4、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询零售商品，批量更新零售商品为发布状态，同时通知搜索引擎删除网上商城下的商品
             * 5、按照商品状态非（待发布、待上架、审核失败、已下架、违规下架、删除）查询餐饮商品，批量更新餐饮菜品为发布状态，同时通知搜索引擎删除网上商城下的菜品
             * 
             * 发起企业申请停止积分活动审核通过的时候  做如下三件事：（BS业务系统接口负责）
             * 1、判断企业类型是托管企业还是成员企业
             * 2、如果是托管企业网上商城状态修改为积分停用,营业点状态修改为积分停用
             * 3、如果是成员企业网上商城状态修改为注销,营业点状态修改为积分停用
             * 
             * 发起企业申请停止积分活动审核不通过(企业用户取消申请停止积分活动或服务公司驳回取消申请)做如下两件事：（BS业务系统接口负责）
             * 1、网上商城和营业点状态修改为启用
             * 2、通知搜索引擎加入网上商城及实体店信息
             **/
            enterpriceService.applyEnterpriceStopPointActivity(inputParam);
        }
        
    }

}
