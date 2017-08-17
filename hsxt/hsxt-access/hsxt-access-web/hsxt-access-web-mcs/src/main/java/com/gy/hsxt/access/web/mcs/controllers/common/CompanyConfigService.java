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

package com.gy.hsxt.access.web.mcs.controllers.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;

/***************************************************************************
 * <PRE>
 * Description 		: 企业web规则约束条件配置文件
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.common  
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
@DisconfFile(filename = "companyConfig.properties")
@DisconfUpdateService(classes = { CompanyConfigService.class })
public class CompanyConfigService implements IDisconfUpdate {

    /** 互生币最大兑换数量 */
    private int hsbSingleConvertibleMax;

    /** 互生币最小兑换数量 */
    private int hsbSingleConvertibleMin;

    /** 互生币账户的流通币可转出，转出数为不小于的整数*/
    private int hsbCirculationConvertibleMin;
    
    /**互生币转货币账户须扣除%的互生币作为货币转换费 */
    private int hsbConvertibleFee;
    
    /** 货币账户可转出，转出金额为不小于100的整数 */
    private int monetaryConvertibleMin;
    /** 法人代表证件正面照FileId */
    private String lrCredentialFrontFileId;

    /** 法人代表证件背面照FileId */
    private String lrCredentialBackFileId;

    /** 营业执照图片ID *******/
    private String busLicenceFileId;

    /*** 组织机构证件照ID ******/
    private String organizationFileId;

    /** 税务登记证件照ID */
    private String taxplayerFileId;
    
    /**变更申请书扫描文件**/
    private String applyFileId;
    
    /**授权委拖书扫描文件**/
    private String powerOfAttorneyFileId;
    
    /**银行资金证明文件**/
    private String bankPicFileId;
    
    /**创业帮扶协议**/
    private String venBefProtocolId;
    
    /**合作股东证明文件文件**/
    private String sharePicFileId;
    
    /** 系统每年的年费价格 **/
    private String annualFee ;

    
    
    /** 登录密码长度 */
    private int loginPasswordLength;

    /** 交易密码长度 */
    private int tradingPasswordLength;
    
    /**
     * 交易密码申请重置业务文件下载id
     */
    private String  tradPwdRequestFile;
    
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
    @DisconfFileItem(name = "login.passWord.length", associateField = "loginPasswordLength")
    public int getLoginPasswordLength() {
        return loginPasswordLength;
    }

    public void setLoginPasswordLength(int loginPasswordLength) {
        this.loginPasswordLength = loginPasswordLength;
    }


    /**
     * 获取互生币最大兑换数量
     * @return
     */
    @DisconfFileItem(name = "hsb.single.convertible.max", associateField = "hsbSingleConvertibleMax")
    public int getHsbSingleConvertibleMax() {
        return hsbSingleConvertibleMax;
    }

    public void setHsbSingleConvertibleMax(int hsbSingleConvertibleMax) {
        this.hsbSingleConvertibleMax = hsbSingleConvertibleMax;
    }
    
    /**
     * 获取互生币最小兑换数量
     * @return
     */
    @DisconfFileItem(name = "hsb.single.convertible.min", associateField = "hsbSingleConvertibleMin")
    public int getHsbSingleConvertibleMin() {
        return this.hsbSingleConvertibleMin;
    }

    public void setHsbSingleConvertibleMin(int hsbSingleConvertibleMin) {
        this.hsbSingleConvertibleMin = hsbSingleConvertibleMin;
    }

    /**
     * 互生币账户的流通币可转出，转出数为不小于的整数
     * @return
     */
    @DisconfFileItem(name = "hsb.circulation.convertible.min", associateField = "hsbCirculationConvertibleMin")
    public int getHsbCirculationConvertibleMin() {
        return hsbCirculationConvertibleMin;
    }

    public void setHsbCirculationConvertibleMin(int hsbCirculationConvertibleMin) {
        this.hsbCirculationConvertibleMin = hsbCirculationConvertibleMin;
    }
    
    /**
     * 互生币转货币账户须扣除%的互生币作为货币转换费
     * @return
     */
    @DisconfFileItem(name = "hsb.convertible.fee", associateField = "hsbConvertibleFee")
    public int getHsbConvertibleFee() {
        return hsbConvertibleFee;
    }

    public void setHsbConvertibleFee(int hsbConvertibleFee) {
        this.hsbConvertibleFee = hsbConvertibleFee;
    }
    
    /**
     * 货币账户可转出，转出金额为不小于100的整数 
     * @return
     */
    @DisconfFileItem(name = "monetary.convertible.min", associateField = "monetaryConvertibleMin")
    public int getMonetaryConvertibleMin() {
        return monetaryConvertibleMin;
    }

    public void setMonetaryConvertibleMin(int monetaryConvertibleMin) {
        this.monetaryConvertibleMin = monetaryConvertibleMin;
    }

    /**
     * @throws Exception
     * @see com.baidu.disconf.client.common.update.IDisconfUpdate#reload()
     */
    @Override
    public void reload() throws Exception {
        // TODO Auto-generated method stub

    }

    public String getLrCredentialFrontFileId() {
        return lrCredentialFrontFileId;
    }

    public void setLrCredentialFrontFileId(String lrCredentialFrontFileId) {
        this.lrCredentialFrontFileId = lrCredentialFrontFileId;
    }

    public String getLrCredentialBackFileId() {
        return lrCredentialBackFileId;
    }

    public void setLrCredentialBackFileId(String lrCredentialBackFileId) {
        this.lrCredentialBackFileId = lrCredentialBackFileId;
    }

    public String getBusLicenceFileId() {
        return busLicenceFileId;
    }

    public void setBusLicenceFileId(String busLicenceFileId) {
        this.busLicenceFileId = busLicenceFileId;
    }

    public String getOrganizationFileId() {
        return organizationFileId;
    }

    public void setOrganizationFileId(String organizationFileId) {
        this.organizationFileId = organizationFileId;
    }

    public String getTaxplayerFileId() {
        return taxplayerFileId;
    }

    public void setTaxplayerFileId(String taxplayerFileId) {
        this.taxplayerFileId = taxplayerFileId;
    }

    public String getApplyFileId() {
        return applyFileId;
    }

    public void setApplyFileId(String applyFileId) {
        this.applyFileId = applyFileId;
    }

    public String getPowerOfAttorneyFileId() {
        return powerOfAttorneyFileId;
    }

    public void setPowerOfAttorneyFileId(String powerOfAttorneyFileId) {
        this.powerOfAttorneyFileId = powerOfAttorneyFileId;
    }
    
    @DisconfFileItem(name = "jnxtsynf.annualFee", associateField = "annualFee")
    public String getAnnualFee() {
        return annualFee;
    }

    public void setAnnualFee(String annualFee) {
        this.annualFee = annualFee;
    }

    @DisconfFileItem(name = "entfiling.bankpicfileid", associateField = "bankPicFileId")
    public String getBankPicFileId() {
        return bankPicFileId;
    }

    public void setBankPicFileId(String bankPicFileId) {
        this.bankPicFileId = bankPicFileId;
    }

    @DisconfFileItem(name = "entfiling.sharepicfileid", associateField = "sharePicFileId")
    public String getSharePicFileId() {
        return sharePicFileId;
    }

    public void setSharePicFileId(String sharePicFileId) {
        this.sharePicFileId = sharePicFileId;
    }

    @DisconfFileItem(name = "entfiling.venbefprotocolid", associateField = "venBefProtocolId")
    public String getVenBefProtocolId() {
        return venBefProtocolId;
    }

    public void setVenBefProtocolId(String venBefProtocolId) {
        this.venBefProtocolId = venBefProtocolId;
    }

}
