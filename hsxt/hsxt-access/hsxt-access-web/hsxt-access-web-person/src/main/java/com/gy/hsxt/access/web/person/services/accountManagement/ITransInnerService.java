/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.accountManagement;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.exception.HsException;

/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 账户余额查询服务
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : BalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:11:39
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:11:39
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public interface ITransInnerService extends IBaseService {
  
    
    
    /**
     * 积分转互生币
     *
     * @param pvToHsb 积分转互生实体
     * @return 无异常表示成功
     * @throws HsException
     */
    public void savePvToHsb(PvToHsb pvToHsb) throws HsException;
    
    /**
     * 保存互生币转货币
     *
     * @param hsbToCash 互生币转货币实体
     * @return 无异常表示成功
     * @throws HsException
     */
    public void saveHsbToCash(HsbToCash hsbToCash) throws HsException;
    
    /**
     * 获取转银行的手续费金额
     * @param transOut 转银行实体类
     * @param sysFlag 转账加急标志[1-大额(>=5万)，等同Y][2-小额(<5万)，等同N ][S：特急] [Y：加急][N：普通（默认）]

     * @return
     * @throws HsException
     */
    public String getBankTransFee(TransOut transOut,String sysFlag)throws HsException;
    
    /**
     * 货币转银行操作
     * @param transOut 转银行实体
     * @param accId 账户id
     * @return 
     * @throws HsException
     */
    public void saveTransOut(TransOut transOut, Long accId)throws HsException;
    
    /**
     * 保存积分投资接口
     * @param pointInvest 积分投资账户
     * @throws HsException
     */
    public void savePointInvest(PointInvest pointInvest)throws HsException;
}
