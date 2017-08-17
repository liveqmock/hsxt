/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.web.mcs.services.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 * Description 		: 企业web规则约束条件配置文件
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.scs.services.common  
 * 
 * File Name        : CompanyConfigService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-9 下午4:42:32
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-9 下午4:42:32
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
@Scope("singleton")
@DisconfFile(filename = "hsxt-access-web-mcs.properties")
@DisconfUpdateService(classes = { MCSConfigService.class })
public class MCSConfigService implements IDisconfUpdate {

  
    /** 登录密码长度 */
    private int loginPasswordLength;

    /** 交易密码长度 */
    private int tradingPasswordLength;
    
    /**
     * 交易密码申请重置业务文件下载id
     */
    private String  tradPwdRequestFile;
    
    /** 系统每年的年费价格 **/
    private String annualFee ;
    
    /**
     * 管理员帐号
     */
    private String adminUser;
    
    /**
     * 缓存图片验证码保存时间 
     */
    private int imgCodeOverdueTime;
    
    /**
     * @return the 管理员帐号
     */
    @DisconfFileItem(name = "admin.user", associateField = "adminUser")
    public String getAdminUser() {
        return adminUser;
    }

    /**
     * @param 管理员帐号 the adminUser to set
     */
    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }
    
    /**
     * 重置交易密码申请,申请文件下载地址
     * @return
     */
    @DisconfFileItem(name = "tradPwd.request.file", associateField = "tradPwdRequestFile")
     public String getTradPwdRequestFile() {
        return tradPwdRequestFile;
    }

    public void setTradPwdRequestFile(String tradPwdRequestFile) {
        this.tradPwdRequestFile = tradPwdRequestFile;
    }

    /**
     * 交易密码长度
     * 
     * @return
     */
    @DisconfFileItem(name = "trading.password.length", associateField = "tradingPasswordLength")
    public int getTradingPasswordLength() {
        return tradingPasswordLength;
    }

    public void setTradingPasswordLength(int tradingPasswordLength) {
        this.tradingPasswordLength = tradingPasswordLength;
    }

    /**
     * 登录密码长度
     * 
     * @return
     */
    @DisconfFileItem(name = "login.password.length", associateField = "loginPasswordLength")
    public int getLoginPasswordLength() {
        return loginPasswordLength;
    }

    public void setLoginPasswordLength(int loginPasswordLength) {
        this.loginPasswordLength = loginPasswordLength;
    }

    
    @DisconfFileItem(name = "jnxtsynf.annualFee", associateField = "annualFee")
    public String getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(String annualFee) {
        this.annualFee = annualFee;
    }
    /** 验证码是否为固定值(1111) **/
   	private boolean imgCodeFixed;

   	/**
   	 * @return the 验证码是否为固定值(1111)
   	 */
   	@DisconfFileItem(name = "img.code.isFixed", associateField = "imgCodeFixed")
   	public boolean isImgCodeFixed() {
   		return imgCodeFixed;
   	}

   	/**
   	 * @return the 验证码是否为固定值(1111)
   	 */
   	public void setImgCodeFixed(boolean imgCodeFixed) {
   		this.imgCodeFixed = imgCodeFixed;
   	}
       
    /**
     * @throws Exception
     * @see com.baidu.disconf.client.common.update.IDisconfUpdate#reload()
     */
    @Override
    public void reload() throws Exception {
        // TODO Auto-generated method stub

    }
    
    public static void main(String[] args) {
      
    }

    /**
     * 图片验证码缓存时长
     * @return the imgCodeOverdueTime
     */
    @DisconfFileItem(name = "img.code.overdueTime", associateField = "imgCodeOverdueTime")
    public int getImgCodeOverdueTime() {
        return imgCodeOverdueTime;
    }

    /**
     * @param imgCodeOverdueTime the imgCodeOverdueTime to set
     */
    public void setImgCodeOverdueTime(int imgCodeOverdueTime) {
        this.imgCodeOverdueTime = imgCodeOverdueTime;
    }
}
