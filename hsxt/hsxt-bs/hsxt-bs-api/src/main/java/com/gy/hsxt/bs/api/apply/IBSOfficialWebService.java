/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.apply;

import java.util.List;

import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.bs.bean.apply.EntBaseInfo;
import com.gy.hsxt.bs.bean.apply.EntInfo;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.tool.CardProvideApply;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.api.app
 * @ClassName: IBSOfficialWebService
 * @Description: 官网服务接口
 * 
 * @author: xiaofl
 * @date: 2015-8-31 下午3:39:50
 * @version V1.0
 */
public interface IBSOfficialWebService {

    /**
     * 添加意向客户
     * 
     * @param intentCust
     *            意向客户详情 必填
     * @throws HsException
     */
    public void createIntentCust(IntentCust intentCust) throws HsException;

    /**
     * 查询公告期企业
     * 
     * @return 公告期企业列表
     */
    public List<EntBaseInfo> queryPlacardEnt();

    /**
     * 查询企业申报信息
     * 
     * @return 企业申报信息
     */
    public List<EntBaseInfo> queryEntInfo(String entName) throws HsException;

    /**
     * 通过授权码查询申报企业信息
     * 
     * @param authCode
     *            授权码
     * @return 申报企业信息
     */
    public EntInfo queryEntInfoByAuthCode(String authCode) throws HsException;

    /**
     * 查询合同内容
     * 
     * @param contractNo
     *            合同唯一识别码
     * @param entResNo
     *            合同编号
     * @return 合同内容
     */
    public ContractContent queryContractContent(String contractNo, String entResNo) throws HsException;

    /**
     * 查询销售资格证书
     * 
     * @param certificateNo
     *            唯一识别码
     * @param entResNo
     *            证书编号
     * @return 销售资格证书
     * @throws HsException
     */
    public CertificateContent querySaleCre(String certificateNo, String entResNo) throws HsException;

    /**
     * 查询第三方业务证书
     * 
     * @param certificateNo
     *            唯一识别码
     * @param entResNo
     *            证书编号
     * @return 第三方业务证书
     * @throws HsException
     */
    public CertificateContent queryBizCre(String certificateNo, String entResNo) throws HsException;

    /**
     * 添加互生卡发放申请
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/23 17:37
     * @param: bean 互生卡发放申请实体
     * @throws: HsException
     * @company: gyist
     * @version V3.0.0
     */
    public void addCardProvideApply(CardProvideApply bean) throws HsException;

    /**
     * 分页查询互生卡发放申请
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/23 17:39
     * @param: receiver 收件人
     * @param: mobile 收件人手机
     * @param: page 分页参数
     * @return: PageData<CardProvideApply>
     * @company: gyist
     * @version V3.0.0
     */
    public PageData<CardProvideApply> queryCardProvideApplyByPage(String receiver, String mobile, Page page);

}
