/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.BmlmVo;
import com.gy.hsxt.gpf.bm.bean.OperRecord;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.file.BatchDealFileFactory;
import com.gy.hsxt.gpf.bm.file.BatchDownBmlmHandler;
import com.gy.hsxt.gpf.bm.file.BatchUploadBmlmHandler;
import com.gy.hsxt.gpf.bm.service.BmlmService;
import com.gy.hsxt.gpf.bm.service.BmlmStatisticsService;
import com.gy.hsxt.gpf.bm.service.OperService;
import com.gy.hsxt.gpf.fss.api.IfssNotifyService;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 再增值积分数据汇总服务层实现
 *
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : BmlmStatisticsServiceImpl
 * @Description : 再增值积分数据汇总服务层实现
 * @Author : chenm
 * @Date : 2015/10/14 18:12
 * @Version V3.0.0.0
 */
@Service("bmlmStatisticsService")
public class BmlmStatisticsServiceImpl implements BmlmStatisticsService {

    private Logger logger = LoggerFactory.getLogger(BmlmStatisticsServiceImpl.class);

    @Resource
    private BmlmService bmlmService;

    @Resource
    private OperService operService;

    @Resource
    private BatchDealFileFactory batchDealFileFactory;

    @Resource
    private IfssNotifyService fssNotifyService;

    private Lock lock = new ReentrantLock();

    /**
     * 开启再增值积分汇总统计
     *
     * @param localNotify 本地通知
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean bmlmStatistics(final LocalNotify localNotify) throws HsException {
        final String key = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.SHORT_DATE_FORMAT) + Constants.OPER_BMLM_DOWNLOAD;
        lock.lock();//加锁
        try {
            OperRecord record = operService.getValueByKey(key);
            if (record == null || record.getSuccess() == 0) {
                //默认为失败的
                record = new OperRecord(Constants.OPER_BMLM_DOWNLOAD);
                record.setReason("Received message of notify");
                operService.save(key, record);
                batchDealFileFactory.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Bmlm> bmlms = batchDealFileFactory.buildDealHandler(BatchDownBmlmHandler.class).setLocalNotify(localNotify).dealFiles();
                        if (CollectionUtils.isNotEmpty(bmlms)) {
                            //插入T_APP_BMLM表
                            Date date = bmlmService.calcBmlmPv(bmlms);
                            if (date != null) {
                                //下载成功后已保存再增值数据,更新操作记录
                                OperRecord uRecord = operService.getValueByKey(key);
                                uRecord.setSuccess(1);
                                uRecord.setReason("bmlm save success");
                                operService.save(key, uRecord);
                                //查询T_APP_BMLM表
                                List<BmlmVo> bmlmVos = getBmlmList(date);
                                if (CollectionUtils.isNotEmpty(bmlmVos)) {
                                    //再增值积分统计数据上传
                                    List<LocalNotify> notifies = batchDealFileFactory.buildDealHandler(BatchUploadBmlmHandler.class).uploadBmlmFile(bmlmVos);
                                    if (CollectionUtils.isNotEmpty(notifies)) {
                                        String dataKey = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH, FssDateUtil.SHORT_DATE_FORMAT) + Constants.OPER_BMLM_UPLOAD;
                                        OperRecord uploadRecord = new OperRecord(Constants.OPER_BMLM_UPLOAD);
                                        uploadRecord.setSuccess(1);
                                        uploadRecord.setReason("bmlm upload success");
                                        operService.save(dataKey, uploadRecord);

                                        for (LocalNotify notify : notifies) {
                                            boolean success = fssNotifyService.localSyncNotify(notify);
                                            logger.info("==============[{}]平台的增值积分汇总文件通知的结果：{} ============", notify.getToPlat(), success);
                                        }
                                    }
                                    logger.info("==============再增值积分汇总上传文件的结果：{} ============", notifies.size());
                                }
                            }
                        } else {
                            logger.info("=========再增值积分所需数据文件解析结果为空=====");
                        }
                    }
                });
            } else {
                logger.info("==============={}已经通知过==========", key);
            }
        } catch (Exception e) {
            logger.error("========再增值积分统计异常==========", e);
        } finally {
            lock.unlock();
        }
        return true;
    }


    /**
     * 2015 07 07 修改，根据插入数据的时间进行查询，避免重复操作搜索出重复的数据
     * getBmlmList(查询再增值积分列表：list存储格式   00000000001,50)
     *
     * @param date 日期
     * @return list
     * @throws HsException
     */
    private List<BmlmVo> getBmlmList(Date date) throws HsException {
        //3、从T_APP_BMLM表取出上个月再增值数据计算再增值积分保存至list中
        Calendar c = Calendar.getInstance();
        //查询范围，插入数据的时间的前一秒到后一秒。
        c.setTime(date);
        c.add(Calendar.SECOND, -1);
        String startRowKey = DateFormatUtils.format(c.getTime(), Constants.DATE_TIME_FORMAT_SSS);
        c.setTime(date);
        c.add(Calendar.SECOND, 1);
        String endRowKey = DateFormatUtils.format(c.getTime(), Constants.DATE_TIME_FORMAT_SSS);

        List<BmlmVo> bmlmVos = new ArrayList<>();
        List<Bmlm> list = bmlmService.findByRowKeyRange(startRowKey, endRowKey);
        //实体类型转换
        if (CollectionUtils.isNotEmpty(list)) {
            for (Bmlm bmlm : list) {
                bmlmVos.add(BmlmVo.bulid(bmlm));
            }
        } else {
            logger.info("========查询再增值积分统计结果为空===========");
        }
        return bmlmVos;
    }
}
