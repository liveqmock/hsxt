/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bm.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.BmlmQuery;
import com.gy.hsxt.bs.bm.bean.BmlmSum;
import com.gy.hsxt.bs.bm.interfaces.IBmlmDetailService;
import com.gy.hsxt.bs.bm.mapper.BmlmDetailMapper;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.api.IfssNotifyService;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.*;
import com.gy.hsxt.lcs.bean.LocalInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 再增值积分详情业务层实现
 *
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : BmlmDetailService
 * @Description : 再增值积分详情业务层实现
 * @Author : chenm
 * @Date : 2015/11/6 18:44
 * @Version V3.0.0.0
 */
@Service("bmlmDetailService")
public class BmlmDetailService implements IBmlmDetailService, IDSBatchService {

    /**
     * 再增值积分详情Mapper接口
     */
    @Resource
    private BmlmDetailMapper bmlmDetailMapper;

    /**
     * 业务系统基础配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 文件同步系统接口
     */
    @Resource
    private IfssNotifyService fssNotifyService;

    /**
     * 公共信息接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 文件根路径
     */
    @Value("${dir.root}")
    private String dirRoot;

    /**
     * 回调监听类
     */
    @Resource
    private IDSBatchServiceListener batchServiceListener;

    /**
     * 保存单个再增值详情
     *
     * @param detail 详情
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int save(BmlmDetail detail) throws HsException {
        try {
            return bmlmDetailMapper.insert(detail);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:save", "保存再增值积分详情失败,参数[detail]:" + detail, e);
            throw new HsException(BSRespCode.BS_BMLM_DETAIL_DB_ERROR, "保存再增值积分详情失败，原因:" + e.getMessage());
        }
    }

    /**
     * 根据业务流水号查询再增值详情
     *
     * @param bmlmId
     * @return
     * @throws HsException
     */
    @Override
    public BmlmDetail queryById(String bmlmId) throws HsException {
        try {
            return bmlmDetailMapper.selectOneById(bmlmId);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryById", "查询再增值积分详情失败,参数[bmlmId]:" + bmlmId, e);
            throw new HsException(BSRespCode.BS_BMLM_DETAIL_DB_ERROR, "查询再增值积分详情失败，原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询再增值积分列表
     *
     * @param page      分页对象
     * @param bmlmQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<BmlmDetail> queryBmlmListPage(Page page, BmlmQuery bmlmQuery) throws HsException {
        if (bmlmQuery != null) {
            bmlmQuery.checkDateFormat();
        }

        try {
            PageContext.setPage(page);
            return bmlmDetailMapper.selectBmlmListPage(bmlmQuery);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBmlmListPage", "分页查询再增值积分列表失败,参数[bmlmQuery]:" + bmlmQuery, e);
            throw new HsException(BSRespCode.BS_BMLM_DETAIL_DB_ERROR, "分页查询再增值积分列表失败，原因:" + e.getMessage());
        }
    }

    /**
     * 调度执行方法
     *
     * @param executeId 任务执行编号
     * @param map       调度任务参数
     * @return
     * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.lang.String,
     * java.util.Map)
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> map) {
        BizLog.biz(bsConfig.getSysName(), "function:executeBatch", "params==>" + JSON.toJSONString(map), "再增值数据扫描");
        batchServiceListener.reportStatus(executeId, 2, "执行中");
        try {
            // 查询参数
            Map<String, Object> params = new HashMap<>();
            // 上个月第一天
            params.put("startDate", FssDateUtil.obtainMonthFirstDay(FssDateUtil.PREVIOUS_MONTH));
            // 这个月第一天
            params.put("endDate", FssDateUtil.obtainMonthFirstDay(FssDateUtil.THIS_MONTH));

            // 查询各个服务公司申报的企业数量
            List<BmlmSum> bmlmSumList = bmlmDetailMapper.selectBmlmSum(params);

            LocalInfo localInfo = commonService.getAreaPlatInfo();

            LocalNotify localNotify = new LocalNotify();
            localNotify.setFileCount(1);
            localNotify.setFromPlat(localInfo.getPlatNo());// 本平台代码
            localNotify.setFromSys(bsConfig.getSysName());// 本系统代码
            localNotify.setToPlat(GlobalConstant.CENTER_PLAT_NO);// 总平台代码
            localNotify.setToSys("BM");// 增值系统
            localNotify.setPurpose(FssPurpose.BM_BMLM.getCode());
            localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
            localNotify.setNotifyType(FssNotifyType.MD5.getTypeNo());
            // 上个月最后一天
            String lastDate = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH);
            localNotify.setNotifyTime(lastDate);
            String dirPath = dirRoot
                    + DirPathBuilder.uploadDir(bsConfig.getSysName(), StringUtils.replace(lastDate,"-",""), FssPurpose.BM_BMLM, localNotify
                    .getToPlat());
            String fileName = FileNameBuilder.build(FssPurpose.BM_BMLM, StringUtils.replace(lastDate,"-",""), bsConfig.getSysName());

            List<FileDetail> details = new ArrayList<>();
            FileDetail detail = new FileDetail();
            detail.setStartTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
            // 输出文件
            File file = new File(dirPath + fileName);
            if (file.exists()) {
                FileUtils.deleteQuietly(file);
            }
            List<String> lines = new ArrayList<>();
            for (BmlmSum bmlmSum : bmlmSumList) {
                lines.add(JSON.toJSONString(bmlmSum));
            }
            FileUtils.writeLines(file, FileConfig.DEFAULT_CHARSET, lines);
            FileUtils.touch(file);
            // 配置文件详情
            detail.setEndTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
            detail.setPercent(1);
            detail.setSource(dirPath + fileName);
            detail.setTarget(dirPath + fileName);
            detail.setIsLocal(1);
            detail.setFileSize(file.length());
            detail.setFileName(file.getName());
            detail.setCode(MD5.toMD5code(file));
            detail.setUnit("B");
            detail.setNotifyNo(localNotify.getNotifyNo());

            details.add(detail);
            localNotify.setDetails(details);

            // 向文件同步系统发送通知
            boolean success = fssNotifyService.localSyncNotify(localNotify);
            BizLog.biz(bsConfig.getSysName(), "function:executeBatch", "向文件同步系统发送通知==>" + success, "再增值数据扫描");
            if (success) {
                batchServiceListener.reportStatus(executeId, 0, "执行成功");
            }
        } catch (Exception ex) {
            SystemLog.error(bsConfig.getSysName(), "function:executeBatch", "再增值数据扫描发生异常", ex);
            batchServiceListener.reportStatus(executeId, 1, "执行失败原因:[" + ex.getMessage() + "]");
            return false;
        }
        return true;
    }
}
