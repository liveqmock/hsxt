/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.bm.IBSmlmService;
import com.gy.hsxt.bs.bean.bm.*;
import com.gy.hsxt.bs.bm.interfaces.IBmlmDetailService;
import com.gy.hsxt.bs.bm.interfaces.IMlmDetailService;
import com.gy.hsxt.bs.bm.interfaces.IMlmTotalService;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询增值系统相关详情的接口实现
 *
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : BsMlmService
 * @Description : 查询增值系统相关详情的接口实现
 * <p>1.积分互生分配详情</p>
 * <p>2.再增值积分详情</p>
 * @Author : chenm
 * @Date : 2015/11/7 17:08
 * @Version V3.0.0.0
 */
@Service
public class BsMlmService implements IBSmlmService {

    /**
     * 业务系统基础数据
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 再增值积分详情接口
     */
    @Resource
    private IBmlmDetailService bmlmDetailService;

    /**
     * 积分互生分配统计接口
     */
    @Resource
    private IMlmTotalService mlmTotalService;

    /**
     * 积分互生分配详情接口
     */
    @Resource
    private IMlmDetailService mlmDetailService;

    /**
     * 再增值积分详情查询
     *
     * @param bizNo 业务流水号
     * @return {@link BmlmDetail}
     * @throws HsException
     */
    @Override
    public BmlmDetail queryBmlmByBizNo(String bizNo) throws HsException {
        //写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:queryBmlmByBizNo", "再增值积分详情查询:" + bizNo);
        //检验参数
        HsAssert.hasText(bizNo, RespCode.PARAM_ERROR, "记账流水号不能为空");

        return bmlmDetailService.queryById(bizNo);
    }

    /**
     * 分页查询再增值积分列表
     *
     * @param page      分页对象
     * @param bmlmQuery 查询实体
     * @return
     * @throws HsException
     */
    @Override
    public PageData<BmlmDetail> queryBmlmListPage(Page page, BmlmQuery bmlmQuery) throws HsException {
        //写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:queryMlmListPage", "分页查询互生积分分配:" + bmlmQuery);

        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象为null");

        HsAssert.notNull(bmlmQuery, BSRespCode.BS_PARAMS_NULL, "再增值积分查询实体[bmlmQuery]为null");

        HsAssert.hasText(bmlmQuery.getCustId(), BSRespCode.BS_PARAMS_EMPTY, "企业客户号[custId]为空");

        List<BmlmDetail> bmlmDetails = bmlmDetailService.queryBmlmListPage(page, bmlmQuery);

        return PageData.bulid(page.getCount(), bmlmDetails);
    }

    /**
     * 增值积分详情查询
     *
     * @param bizNo 业务流水号
     * @return {@link MlmTotal}
     * @throws HsException
     */
    @Override
    public MlmTotal queryMlmByBizNo(String bizNo) throws HsException {
        //写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:queryBmlmByBizNo", "再增值积分详情查询:" + bizNo);
        //检验参数
        HsAssert.hasText(bizNo, RespCode.PARAM_ERROR, "记账流水号不能为空");
        //查询互生积分分配汇总
        MlmTotal mlmTotal = mlmTotalService.queryById(bizNo);
        if (mlmTotal != null) {
            //查询详情
            List<MlmDetail> details = mlmDetailService.queryByTotalId(mlmTotal.getTotalId());
            mlmTotal.setDetails(details);
        }
        return mlmTotal;
    }

    /**
     * 分页查询互生积分分配
     *
     * @param page     分页对象
     * @param mlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public PageData<MlmTotal> queryMlmListPage(Page page, MlmQuery mlmQuery) throws HsException {
        //写请求参数日志
        SystemLog.info(bsConfig.getSysName(), "function:queryMlmListPage", "分页查询互生积分分配:" + mlmQuery);

        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");

        HsAssert.notNull(mlmQuery, BSRespCode.BS_PARAMS_NULL, "互生积分分配查询实体[mlmQuery]为null");

        HsAssert.hasText(mlmQuery.getCustId(), BSRespCode.BS_PARAMS_EMPTY, "企业客户号[custId]为空");

        List<MlmTotal> mlmTotals = mlmTotalService.queryMlmListPage(page, mlmQuery);

        return PageData.bulid(page.getCount(), mlmTotals);
    }
}
