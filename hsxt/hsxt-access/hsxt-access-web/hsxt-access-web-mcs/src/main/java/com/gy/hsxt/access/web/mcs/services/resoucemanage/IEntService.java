/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.resoucemanage;


import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/***
 * 企业接口类
 * @Package: com.gy.hsxt.access.web.mcs.services.resoucemanage
 * @ClassName: IEntService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-1-21 下午5:46:09
 * @version V1.0
 */
public interface IEntService extends IBaseService {
    /**
     * 获取企业明细(服务公司、托管企业、服务公司....)
     * @param entCustId
     * @return
     * @throws HsException
     */
    public AsEntAllInfo getEntAllInfo(String entCustId) throws HsException;

    /**
     * 获取企业银行信息
     * @param entCustId
     * @return
     * @throws HsException
     */
    public List<AsBankAcctInfo> getEntBankList(String entCustId) throws HsException;
}
