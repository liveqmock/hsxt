/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.bm.MlmQuery;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.bs.bm.interfaces.IMlmTotalService;
import com.gy.hsxt.bs.bm.mapper.MlmTotalMapper;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 增值汇总业务实现
 *
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : MlmTotalService
 * @Description : 增值汇总业务实现
 * @Author : chenm
 * @Date : 2015/11/6 18:45
 * @Version V3.0.0.0
 */
@Service
public class MlmTotalService implements IMlmTotalService {

    /**
     * 业务系统基础配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 增值汇总Mapper接口
     */
    @Resource
    private MlmTotalMapper mlmTotalMapper;

    /**
     * 保存单个增值汇总实例
     *
     * @param mlmTotal 增值汇总
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int save(MlmTotal mlmTotal) throws HsException {
        try {
            return mlmTotalMapper.insert(mlmTotal);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:save", "保存单个增值汇总失败,参数[mlmTotal]:" + mlmTotal, e);
            throw new HsException(BSRespCode.BS_MLM_TOTAL_DB_ERROR, "保存单个增值汇总失败，原因:" + e.getMessage());
        }
    }

    /**
     * 根据业务流水号查询实体
     *
     * @param totalId 增值汇总ID
     * @return {@link MlmTotal}
     * @throws HsException
     */
    @Override
    public MlmTotal queryById(String totalId) throws HsException {
        try {
            return mlmTotalMapper.selectOneById(totalId);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:save", "根据业务流水号查询实体失败,参数[totalId]:" + totalId, e);
            throw new HsException(BSRespCode.BS_MLM_TOTAL_DB_ERROR, "根据业务流水号查询实体失败，原因:" + e.getMessage());
        }
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
    public List<MlmTotal> queryMlmListPage(Page page, MlmQuery mlmQuery) throws HsException {

        if (mlmQuery != null) {
            mlmQuery.checkDateFormat();
        }
        try {
            PageContext.setPage(page);

            return mlmTotalMapper.selectMlmListPage(mlmQuery);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryMlmListPage", "分页查询互生积分分配失败,参数[mlmQuery]:" + mlmQuery, e);
            throw new HsException(BSRespCode.BS_MLM_TOTAL_DB_ERROR, "分页查询互生积分分配失败，原因:" + e.getMessage());
        }
    }
}
