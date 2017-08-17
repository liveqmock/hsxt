/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.reveivingManage;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.gy.hsxt.access.web.bean.AbstractAPSBase;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/***
 * 订单关闭实体类
 * 
 * @Package: com.gy.hsxt.access.web.bean.reveivingManage
 * @ClassName: ApsOrderColoseBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-15 下午4:53:18
 * @version V1.0
 */
public class ApsOrderColoseBean extends AbstractAPSBase {
    private static final long serialVersionUID = -2041315441085264045L;

    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 复核帐号
     */
    //private String checkAccount;

    /**
     * 复核密码
     */
    //private String checkPwd;

    /**
     * 复核意见
     */
    /*private String checkViews;*/
    
    /**
     * 验证数据
     */
    public void checkData() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.orderNo, RespCode.APS_ORDERNO_NOT_NULL.getCode(),
                        RespCode.APS_ORDERNO_NOT_NULL.getDesc() }/*,
                new Object[] { this.checkAccount, RespCode.APS_CHECK_ACCOUNT_NOT_NULL.getCode(),
                        RespCode.APS_CHECK_ACCOUNT_NOT_NULL.getDesc() },
                new Object[] { this.checkPwd, RespCode.APS_CHECK_PWD_NOT_NULL.getCode(),
                        RespCode.APS_CHECK_PWD_NOT_NULL.getDesc() }*/
               /* ,new Object[] { this.checkViews, RespCode.APS_CHECK_VIEWS_NOT_NULL.getCode(),
                        RespCode.APS_CHECK_VIEWS_NOT_NULL.getDesc() }*/
                );
    }

    /**
     * 创建操作员登录信息
     * @return
     */
    public AsOperatorLoginInfo createOperatorLoginInfo() {
        AsOperatorLoginInfo aoli = new AsOperatorLoginInfo();
        aoli.setResNo(this.getResNo());
        aoli.setChannelType(ChannelTypeEnum.WEB);
        /* aoli.setRandomToken(this.getToken());
        aoli.setUserName(this.getCheckAccount());
        aoli.setLoginPwd(this.getCheckPwd());*/
        return aoli;
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

   /* public String getCheckAccount() {
        return checkAccount;
    }

    public void setCheckAccount(String checkAccount) {
        this.checkAccount = checkAccount;
    }

    public String getCheckPwd() {
        return checkPwd;
    }

    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }*/
    
    public static void main(String[] args) {
        String a="归一互生";
        boolean b= a.startsWith("一",3);
        System.out.println("=========="+b);
    }
}
