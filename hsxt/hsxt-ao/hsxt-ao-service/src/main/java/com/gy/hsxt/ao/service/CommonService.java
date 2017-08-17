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

package com.gy.hsxt.ao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.interfaces.ICommonService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.lcs.LCSErrorCode;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;

/**
 * 平台公共接口服务，实现平台全局数据的获取
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: CommonService
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-17 下午4:54:53
 * @version V1.0
 */
@Service
public class CommonService implements ICommonService {

    /** 地区平台配送Service **/
    @Autowired
    private LcsClient baseDataService;

    /** 用户中心企业Service **/
    @Autowired
    private IUCBsEntService ucEntService;

    /**
     * 当前平台初始化信息
     */
    private LocalInfo localInfo;

    /**
     * 平台客户号
     */
    private String platCustId;

    /**
     * 平台互生号
     */
    private String platResNo;

    @Override
    public String getPlatCustId() {
        if (platCustId == null)
        {
            try
            {
                return ucEntService.findEntCustIdByEntResNo(getPlatResNo());
            }
            catch (HsException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                SystemLog.error(this.getClass().getName(), ucEntService.getClass().getName() + ".getPlatCustId()",
                        AOErrorCode.AO_INVOKE_UC_NOT_QUERY_DATA.getCode() + ":调用用户中心接口异常，未查询到企业信息", ex);
                throw new HsException(AOErrorCode.AO_INVOKE_UC_NOT_QUERY_DATA, "调用用户中心接口异常，未查询到企业信息:"
                        + ucEntService.getClass().getName() + ".getPlatCustId()" + ex);
            }

        }
        return platCustId;
    }

    @Override
    public String getPlatResNo() {
        if (platResNo == null)
        {
            platResNo = getLocalInfo().getPlatResNo();
        }
        return platResNo;
    }

    @Override
    public LocalInfo getLocalInfo() {
        if (localInfo == null)
        {
            localInfo = baseDataService.getLocalInfo();
            HsAssert.notNull(localInfo, LCSErrorCode.DATA_NOT_FOUND, "获取当前平台初始化信息失败");
        }
        return localInfo;
    }
}
