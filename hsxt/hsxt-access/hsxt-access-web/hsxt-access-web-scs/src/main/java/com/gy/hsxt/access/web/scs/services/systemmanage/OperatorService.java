/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.systemmanage;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * 
 * 操作员管理服务
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage
 * @ClassName: OperatorService
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2016-1-9 下午12:12:30
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface OperatorService extends IBaseService {

    /**
     * 添加操作员
     * 
     * @param oper
     * @param adminCustId
     * @throws HsException
     */
    public String addOper(AsOperator oper, String adminCustId) throws HsException;

    /**
     * 查询操作员
     * @param operCustId
     * @return
     * @throws HsException
     */
    public AsOperator searchOperByCustId(String operCustId) throws HsException;

    /**
     * 查询企业操作员列表
     * 
     * @param entCustId
     * @return
     * @throws HsException
     */
    public List<AsOperator> listOperByEntCustId(String entCustId) throws HsException;

    /**
     * 修改企业操作员信息
     * @param oper
     * @param adminCustId
     * @throws HsException
     */
    public void updateOper(AsOperator oper, String adminCustId) throws HsException;

    /**
     * 删除操作员
     * @param operCustId
     * @param adminCustId
     * @throws HsException
     */
    public void deleteOper(String operCustId, String adminCustId) throws HsException;

    /**
     * 管理员给操作员绑定互生卡
     * @param operCustId
     *            待绑定操作员客户号
     * @param operResNo
     *            待绑定操作员互生号
     * @param adminCustId
     *            管理员客户号
     * @throws HsException
     */
    public void bindCard(String operCustId, String perResNo, String adminCustId) throws HsException;

    /**
     * 操作员确定绑定互生卡
     * @param operResNo
     *            待绑定消费者互生号
     * 
     * @throws HsException
     */
    public void confirmBindCard(String perResNo) throws HsException;

    /**
     * 操作员解绑互生卡绑定
     * @param operCustId
     *            待解绑操作员客户号
     * @throws HsException
     * @see com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService#unBindCard(java.lang.String)
     */
    public void unBindCard(String operCustId) throws HsException;

    /**
     * 获取操作员详情
     * @param scsBase
     * @return
     */
    public Map<String, Object> getOperatorDetail(SCSBase scsBase);
}
