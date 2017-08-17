/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.interfaces;

import com.gy.hsxt.bs.apply.bean.ExpiryDeclare;
import com.gy.hsxt.bs.bean.apply.DeclareInfo;
import com.gy.hsxt.bs.bean.apply.EntBaseInfo;
import com.gy.hsxt.bs.bean.apply.EntInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.interfaces
 * @ClassName: IDeclareService
 * @Description: 申报接口
 * 
 * @author: xiaofl
 * @date: 2015-12-14 下午4:52:53
 * @version V1.0
 */
public interface IDeclareService {

    /**
     * 查询授权码已过期的申报申请单
     * 
     * @return 已过期的申报申请单列表
     */
    public List<ExpiryDeclare> getExpiryDeclareList();

    /**
     * 更新已过期的申报申请单
     * 
     * @param expiryDeclare
     *            已过期的申报申请单
     * @throws HsException
     */
    public void updateExpiryDeclare(ExpiryDeclare expiryDeclare) throws HsException;

    /**
     * 查询申报信息
     * 
     * @param applyId
     *            申请编号
     * @return 申报信息
     */
    public DeclareInfo queryDeclareById(String applyId);

    /**
     * 付款成功后更新申报相关信息
     * 
     * @param applyId
     *            申请编号
     * @param order  订单
     * @throws HsException
     */
    public void updateDeclareAfterPay(String applyId, Order order) throws HsException;

    /**
     * 查询公告期企业
     * 
     * @return 公告期企业列表
     */
    public List<EntBaseInfo> queryPlacardEnt();

    /**
     * 查询企业申报信息
     * 
     * @return 公告期企业列表
     */
    public List<EntBaseInfo> queryEntInfo(String entName);

    /**
     * 通过授权码查询申报企业信息
     * 
     * @param authCode
     *            授权码
     * @return 申报企业信息
     */
    public EntInfo queryEntInfoByAuthCode(String authCode);

    /**
     * 重试失败的开启增值与UC开户
     */
    void retryOpenSyncFail();
    
    /**
     * 自动处理失效的申报单
     */
    void autoExpiryDeclare();

    /**
     * 申报进去办理期首次发送申报授权码
     * @param applyId 申报单号
     * @throws HsException
     */
    void firstSendAuthCode(String applyId) throws HsException;

}
