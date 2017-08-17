/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.apply;

import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 证书管理接口
 *
 * @Package : com.gy.hsxt.bs.api.apply
 * @ClassName : IBSCertificateService
 * @Description : 证书管理接口
 * @Author : xiaofl
 * @Date : 2015-9-2 上午11:53:38
 * @Version V3.0
 */
public interface IBSCertificateService {

    /**
     * 创建证书模板
     *
     * @param templet 证书模板
     * @throws HsException
     */
    void createTemplet(Templet templet) throws HsException;

    /**
     * 修改证书模板
     *
     * @param templet 证书模板
     * @throws HsException
     */
    void modifyTemplet(Templet templet) throws HsException;

    /**
     * 查看证书模板详情
     *
     * @param templetId 合同模板ID
     * @return 返回合同模板详情，没有则返回null
     * @throws HsException
     */
    Templet queryTempletById(String templetId) throws HsException;

    /**
     * 分页查询证书模板
     *
     * @param query 模板名称
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTempletList(TempletQuery query, Page page) throws HsException;

    /**
     * 分页查询待复核证书模板
     *
     * @param query 模板查询实体
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTemplet4Appr(TempletQuery query, Page page) throws HsException;

    /**
     * 复核证书模板
     *
     * @param templateAppr 审批内容
     * @throws HsException
     */
    void apprTemplet(TemplateAppr templateAppr) throws HsException;

    /**
     * 查询模版最新审核记录
     *
     * @param templetId 模版ID
     * @return TemplateAppr
     * @throws HsException
     */
    TemplateAppr queryLastTemplateAppr(String templetId) throws HsException;

    /**
     * 启用证书模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员
     * @throws HsException
     */
    void enableTemplet(String templetId, String operator) throws HsException;

    /**
     * 停用证书模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员
     * @throws HsException
     */
    void disableTemplet(String templetId, String operator) throws HsException;

    /**
     * 查询第三方业务证书发放
     *
     * @param certificateQueryParam 查询条件
     * @param page                  分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    public PageData<CertificateBaseInfo> queryBizCre4Send(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException;

    /**
     * 查询第三方业务证书历史
     *
     * @param certificateQueryParam 查询条件
     * @param page                  分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    public PageData<CertificateBaseInfo> queryBizCre4His(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException;

    /**
     * 查询销售证书盖章
     *
     * @param certificateQueryParam 查询条件
     * @param page                  分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    public PageData<CertificateBaseInfo> querySaleCre4Seal(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException;

    /**
     * 查询销售证书发放
     *
     * @param certificateQueryParam 查询条件
     * @param page                  分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    public PageData<CertificateBaseInfo> querySaleCre4Send(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException;

    /**
     * 查询销售证书历史
     *
     * @param certificateQueryParam 查询条件
     * @param page                  分页
     * @return 返回证书基本信息列表
     * @throws HsException
     */
    public PageData<CertificateBaseInfo> querySaleCre4His(CertificateQueryParam certificateQueryParam, Page page)
            throws HsException;

    /**
     * 通过ID查询证书
     *
     * @param certificateNo 证书唯一识别码
     * @return 返回证书信息
     * @throws HsException
     */
    public Certificate queryCreById(String certificateNo) throws HsException;

    /**
     * 通过ID查询证书内容
     *
     * @param certificateNo 证书唯一识别码
     * @param realTime 是否实时
     * @return 返回证书内容
     * @throws HsException
     */
    public CertificateContent queryCreContentById(String certificateNo,boolean realTime) throws HsException;

    /**
     * 打印证书
     *
     * @param certificateNo 证书编号 必填
     * @param operator      操作员 必填
     * @return 返回证书内容，没有则返回null
     * @throws HsException
     */
    public CertificateContent printCertificate(String certificateNo, String operator) throws HsException;

    /**
     * 证书盖章
     *
     * @param certificateNo 证书编号 必填
     * @param operator      操作员 必填
     * @throws HsException
     */
    public void sealCertificate(String certificateNo, String operator) throws HsException;

    /**
     * 发放证书
     *
     * @param certificateSendHis 证书发放信息 必填
     * @throws HsException
     */
    public void sendCertificate(CertificateSendHis certificateSendHis) throws HsException;

    /**
     * 查询证书发放历史
     *
     * @param certificateNo 证书唯一识别码
     * @param page          分页信息
     * @return
     * @throws HsExcepton
     */
    public PageData<CertificateSendHis> queryCreSendHis(String certificateNo, Page page) throws HsException;

    /**
     * 查询销售证书主要信息
     *
     * @param certificateNo 证书唯一识别码
     * @param entResNo      证书编号
     * @return 销售证书主要信息
     */
    public CertificateMainInfo querySaleCer(String certificateNo, String entResNo);

    /**
     * 查询第三方证书主要信息
     *
     * @param certificateNo 证书唯一识别码
     * @param entResNo      证书编号
     * @return 第三方证书主要信息
     */
    public CertificateMainInfo queryThirdPartCer(String certificateNo, String entResNo);
}
