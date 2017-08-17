/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.platformDebit;

import java.util.Map;

import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.bean.platformDebit.ApsHsbDeduction;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***
 * 平台扣款业务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.platformDebit
 * @ClassName: IPlatformDebitService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-4-19 下午2:41:46
 * @version V1.0
 */
public interface IPlatformDebitService extends IBaseService {

    /**
     * 分页查询平台互生币扣款申请列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<HsbDeduction> queryHsbDeductionListPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 分页查询平台互生币扣款申请复核列表
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<HsbDeduction> queryTaskListPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /***
     * 查询某一条平台互生币扣款申请
     * 
     * @param applyId
     * @return
     * @throws HsException
     */
    public HsbDeduction queryDetailById(String applyId) throws HsException;

    /**
     * 复核平台互生币扣款申请
     * @param hsbDeduction
     * @param apsBase
     * @return
     * @throws HsException
     */
    public boolean apprHsbDeduction(HsbDeduction hsbDeduction,ApsBase apsBase) throws HsException;

    /**
     * 平台扣款申请
     * @param ahd
     * @param apsBase
     * @return
     * @throws HsException
     */
    public String applyHsbDeduction(ApsHsbDeduction ahd, ApsBase apsBase) throws HsException;

    /**
     * 通过互生号查找详情
     * 
     * @param resNo
     * @return
     */
    public Map<String, Object> getResNoInfo(String resNo) throws HsException;
}
