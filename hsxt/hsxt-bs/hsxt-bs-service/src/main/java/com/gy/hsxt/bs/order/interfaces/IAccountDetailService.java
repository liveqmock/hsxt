/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.common.exception.HsException;

/**
 * 内部业务通用记帐分解接口定义
 * 
 * @Package: com.gy.hsxt.bs.order.interfaces
 * @ClassName: IAccountDetail
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-23 上午9:39:09
 * @version V3.0.0
 */
public interface IAccountDetailService {

    /**
     * 保存通用记帐分解
     * 
     * @param accountDetails
     *            记帐分解信息列表
     * @param bizName
     *            业务名称，例：年费业务、工具业务
     * @throws HsException
     */
    public void saveGenActDetail(List<AccountDetail> accountDetails, String bizName, boolean note) throws HsException;

    /**
     * 保存临帐记帐分解
     * 
     * @param orderNo
     *            订单号
     * @throws HsException
     */
    public void saveTempActDetail(String orderNo) throws HsException;
}
