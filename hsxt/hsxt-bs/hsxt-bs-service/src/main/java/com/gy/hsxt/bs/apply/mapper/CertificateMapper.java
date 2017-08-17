/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.apply.bean.CertificateParam;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.CertificateBaseInfo;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.CertificateMainInfo;
import com.gy.hsxt.bs.bean.apply.CertificateSendHis;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.mapper
 * @ClassName: CertificateMapper
 * @Description: 证书Mapper
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午6:32:52
 * @version V1.0
 */
@Repository
public interface CertificateMapper {

    /**
     * 查询证书列表
     * 
     * @param certificateParam
     *            证书查询参数
     * @return 证书列表
     */
    List<CertificateBaseInfo> queryCreListPage(CertificateParam certificateParam);

    /**
     * 通过ID查询证书
     * 
     * @param certificateNo
     *            证书ID
     * @return 证书
     */
    Certificate queryCreById(String certificateNo);

    /**
     * 查询证书内容
     * 
     * @param certificateNo
     *            证书ID
     * @return 证书内容
     */
    CertificateContent queryCreContentById(String certificateNo);

    /**
     * 更新打印次数
     * 
     * @param certificateNo
     *            证书ID
     * @param operator
     *            操作员
     */
    void updatePrintCount(@Param("certificateNo") String certificateNo, @Param("operator") String operator);

    /**
     * 更新盖章状态
     * 
     * @param certificateNo
     *            证书ID
     * @param status
     *            盖章状态
     * @param operator
     *            操作员
     */
    void updateSealStatus(@Param("certificateNo") String certificateNo, @Param("status") Integer status,
            @Param("operator") String operator);

    /**
     * 更新发放状态
     * 
     * @param certificateNo
     *            证书ID
     */
    void updateSendStatus(String certificateNo);

    /**
     * 创建发放历史
     * 
     * @param certificateSendHis
     *            发放历史
     */
    void createSendHis(CertificateSendHis certificateSendHis);

    /**
     * 查询证书发放历史
     * 
     * @param certificateNo
     *            证书ID
     * @return 证书发放历史列表
     */
    List<CertificateSendHis> querySendHisListPage(String certificateNo);

    /**
     * 通过ID查询证书基本信息
     * 
     * @param certificateNo
     *            证书ID
     * @return 证书基本信息
     */
    CertificateBaseInfo queryCreBaseInfoById(String certificateNo);

    /**
     * 创建证书
     * 
     * @param certificate
     *            证书
     */
    void createCertificate(Certificate certificate);

    /**
     * 查询证书主要信息
     * 
     * @param cerType
     *            证书类型
     * @param certificateNo
     *            证书唯一识别码
     * @param entResNo
     *            证书编号
     * @return 证书主要信息
     */
    CertificateMainInfo queryCerMainInfo(@Param("cerType") int cerType, @Param("certificateNo") String certificateNo,
            @Param("entResNo") String entResNo);

    /**
     * 查询企业唯一有效的证书
     *
     * @param entCustId 企业客户号
     * @return 证书
     */
    Certificate queryEffectCerByCustId(@Param("entCustId") String entCustId);

    /**
     * 重要信息变更后修改证书
     *
     * @param certificate 证书
     */
    void updateCerForChangeInfo(Certificate certificate);

    /**
     * 设置第三方证书失效
     *
     * @param entResNo 互生号
     */
    void setLostEfficacyForThird(String entResNo);
}
