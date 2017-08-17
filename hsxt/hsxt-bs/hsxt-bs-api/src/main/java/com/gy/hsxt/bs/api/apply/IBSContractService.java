/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.apply;

import com.gy.hsxt.bs.bean.apply.*;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 合同管理接口
 *
 * @Package : com.gy.hsxt.bs.api.apply
 * @ClassName : IBSContractService
 * @Description : 合同管理接口
 * @Author : xiaofl
 * @Date : 2015-9-2 上午11:53:38
 * @Version V1.0
 */
public interface IBSContractService {

    /**
     * 创建合同模板
     *
     * @param templet 模板
     * @throws HsException
     */
    void createTemplet(Templet templet) throws HsException;

    /**
     * 修改合同模板
     *
     * @param templet 模板
     * @throws HsException
     */
    void modifyTemplet(Templet templet) throws HsException;

    /**
     * 查看合同模板详情
     *
     * @param templetId 合同模板ID
     * @return 返回合同模板详情，没有则返回null
     * @throws HsException
     */
    Templet queryTempletById(String templetId) throws HsException;

    /**
     * 分页查询合同模板
     *
     * @param query 模板名称
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTempletList(TempletQuery query, Page page) throws HsException;

    /**
     * 分页查询待复核合同模板
     *
     * @param query 模板查询实体
     * @param page  分页信息 必填
     * @return 返回证书模板列表，没有则返回null
     * @throws HsException
     */
    PageData<Templet> queryTemplet4Appr(TempletQuery query, Page page) throws HsException;

    /**
     * 复核合同模板
     *
     * @param templateAppr 审批内容
     * @throws HsException
     */
    void apprTemplet(TemplateAppr templateAppr) throws HsException;

    /**
     * 启用合同模板
     *
     * @param templetId 合同模板ID 必填
     * @param operator  操作员
     * @throws HsException
     */
    void enableTemplet(String templetId, String operator) throws HsException;

    /**
     * 停用合同模板
     *
     * @param templetId 合同模板ID 必填
     * @param operator  操作员
     * @throws HsException
     */
    void disableTemplet(String templetId, String operator) throws HsException;

    /**
     * 服务公司查询合同
     *
     * @param serResNo 服务公司互生号 必填
     * @param resNo    企业互生号
     * @param entName  企业名称
     * @param linkman  联系人
     * @param page     分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    public PageData<ContractBaseInfo> serviceQueryContract(String serResNo, String resNo, String entName,
                                                           String linkman, Page page) throws HsException;

    /**
     * 管理公司查询合同
     *
     * @param manResNo 管理公司互生号 必填
     * @param resNo    企业互生号
     * @param entName  企业名称
     * @param linkman  联系人
     * @param page     分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    public PageData<ContractBaseInfo> managerQueryContract(String manResNo, String resNo, String entName,
                                                           String linkman, Page page) throws HsException;

    /**
     * 地区平台查询合同
     *
     * @param resNo   企业互生号
     * @param entName 企业名称
     * @param linkman 联系人
     * @param page    分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    public PageData<ContractBaseInfo> platQueryContract(String resNo, String entName, String linkman, Page page)
            throws HsException;

    /**
     * 地区平台查询合同盖章
     *
     * @param resNo   企业互生号
     * @param entName 企业名称
     * @param status  盖章状态
     * @param page    分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    public PageData<ContractBaseInfo> queryContract4Seal(String resNo, String entName, Integer status, Page page)
            throws HsException;

    /**
     * 地区平台查询合同发放
     *
     * @param resNo      企业互生号
     * @param entName    企业名称
     * @param sendStatus 发放状态
     * @param page       分页信息 必填
     * @return 返回该服务公司所申报企业的已盖章合同列表，没有则返回null
     * @throws HsException
     */
    public PageData<ContractBaseInfo> queryContract4Send(String resNo, String entName, Boolean sendStatus, Page page)
            throws HsException;

    /**
     * 查看合同信息
     *
     * @param contractNo 合同编号
     * @return 返回合同信息
     * @throws HsException
     */
    public ContractBaseInfo queryContractById(String contractNo) throws HsException;

    /**
     * 查看合同内容--预览
     *
     * @param contractNo 合同编号
     * @return 合同内容
     * @throws HsException
     */
    public ContractContent queryContractContent4View(String contractNo) throws HsException;

    /**
     * 查看合同内容--盖章
     *
     * @param contractNo 合同编号
     * @param realTime 是否实时
     * @return 合同内容
     * @throws HsException
     */
    public ContractContent queryContractContent4Seal(String contractNo,boolean realTime);

    /**
     * 打印合同
     *
     * @param contractNo 合同编号
     * @return 合同内容
     * @throws HsException
     */
    public ContractContent printContract(String contractNo) throws HsException;

    /**
     * 合同盖章
     *
     * @param contractNo 合同编号 必填
     * @throws HsException
     */
    public ContractContent sealContract(String contractNo) throws HsException;

    /**
     * 发放合同
     *
     * @param contractSendHis 合同发放信息 必填
     * @throws HsException
     */
    public void sendContract(ContractSendHis contractSendHis) throws HsException;

    /**
     * 查询合同发放历史
     *
     * @param contractNo 合同编号 必填
     * @param page       分页
     * @return
     * @throws HsException
     */
    public PageData<ContractSendHis> queryContractSendHis(String contractNo, Page page) throws HsException;

    /**
     * 查询合同主要信息
     *
     * @param contractNo 合同唯一识别码
     * @param entResNo   合同编号
     * @return 合同主要信息
     */
    public ContractMainInfo queryContractMainInfo(String contractNo, String entResNo);

}
