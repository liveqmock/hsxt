/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bm.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.bm.MlmDetail;
import com.gy.hsxt.bs.bm.interfaces.IMlmDetailService;
import com.gy.hsxt.bs.bm.mapper.MlmDetailMapper;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 增值详情业务层实现
 *
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : MlmDetailService
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 18:44
 * @Version V3.0.0.0
 */
@Service
public class MlmDetailService implements IMlmDetailService {

    /**
     * 业务系统基础配置
     */
    @Resource
    private BsConfig bsConfig;


    /**
     * 增值详情Mapper接口
     */
    @Resource
    private MlmDetailMapper mlmDetailMapper;

    /**
     * 保存单个增值详情实体
     *
     * @param mlmDetail 增值详情
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int save(MlmDetail mlmDetail) throws HsException {
        try {
            return mlmDetailMapper.insert(mlmDetail);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:save", "保存增值详情失败,参数[mlmDetail]:" + mlmDetail, e);
            throw new HsException(BSRespCode.BS_MLM_DETAIL_DB_ERROR, "保存增值详情失败，原因:" + e.getMessage());
        }
    }

    /**
     * 根据汇总ID查询增值详情列表
     *
     * @param totalId 总数ID
     * @return list
     * @throws HsException
     */
    @Override
    public List<MlmDetail> queryByTotalId(String totalId) throws HsException {
        try {
            return mlmDetailMapper.selectListByTotalId(totalId);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryById", "查询增值详情列表失败,参数[totalId]:" + totalId, e);
            throw new HsException(BSRespCode.BS_MLM_DETAIL_DB_ERROR, "查询增值详情列表失败，原因:" + e.getMessage());
        }
    }
}
