package com.gy.hsxt.gpf.bm.job;

import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.*;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.file.BatchDealFileFactory;
import com.gy.hsxt.gpf.bm.file.BatchUploadMlmHandler;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.service.OperService;
import com.gy.hsxt.gpf.bm.service.PointValueService;
import com.gy.hsxt.gpf.fss.api.IfssNotifyService;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 互生积分分配定时任务
 *
 * @Package : com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : ApplyRecordServiceImpl
 * @Description : 互生积分分配定时任务
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class PointValueJob {

    private static final Logger logger = LoggerFactory.getLogger(PointValueJob.class);

    @Resource
    private PointValueService pointValueService;

    @Resource
    private IncrementService incrementService;

    @Resource
    private PlatData platData;

    @Resource
    private BatchDealFileFactory batchDealFileFactory;

    @Resource
    private OperService operService;

    @Resource
    private IfssNotifyService fssNotifyService;

    private Lock lock = new ReentrantLock();

    /**
     * 执行定时统计任务 每周星期一的0:30执行该任务,查询上周增值积分生成文件并上传至ftp(账务下载并处理)
     * 自行指定方法
     */
    public void execute() {
        String key = FssDateUtil.obtainWeekDay(Calendar.SUNDAY, FssDateUtil.PREVIOUS_WEEK,FssDateUtil.SHORT_DATE_FORMAT) + Constants.OPER_MLM_UPLOAD;

        lock.lock();//加锁

        try {
            OperRecord record = operService.getValueByKey(key);
            if (record == null) {
                //保存操作记录，默认失败状态
                record = new OperRecord(Constants.OPER_MLM_UPLOAD);
                record.setReason(" job start ");
                operService.save(key,record);
            }else{
                if (record.getSuccess() == 1) {
                    logger.info("============[{}]积分增值定时任务已经执行过===============",key);
                    return;
                }
            }
            logger.info("============积分增值定时任务执行开始===============");
            /**
             * 按照平台分类存储各个的增值积分
             * 存储结构：
             * 平台代码：{企业互生号：增值积分总数}
             */
            Map<String, Map<String, DetailResult<PointValue>>> platMap = new HashMap<>();

            String startRowKey = FssDateUtil.obtainWeekDay(Calendar.MONDAY, FssDateUtil.PREVIOUS_WEEK,FssDateUtil.SHORT_DATE_FORMAT); //上周一日期
            String endRowKey = FssDateUtil.obtainThisWeekDay(Calendar.MONDAY,FssDateUtil.SHORT_DATE_FORMAT);      //本周一日期,因为查询时不包括endRowKey,开区间
            //查询上周一至上周日积分增值数据
            Collection<PointValue> collection = pointValueService.findByRowKeyRange(startRowKey, endRowKey);

            if (CollectionUtils.isNotEmpty(collection)) {
                gatherValue(collection, startRowKey, endRowKey,platMap);
                for (Map.Entry<String, Map<String, DetailResult<PointValue>>> entry : platMap.entrySet()) {
                    //检查是否有增值积分达到100000
                    checkValuesMap(entry.getValue());
                }
                List<LocalNotify> notifies = batchDealFileFactory.buildDealHandler(BatchUploadMlmHandler.class).uploadMlmFile(platMap);
                // 成功后更新操作记录
                if (CollectionUtils.isNotEmpty(notifies)) {
                    record.setSuccess(1);
                    record.setReason(" mlm upload success");
                    operService.save(key, record);
                    for (LocalNotify notify : notifies) {
                        boolean success = fssNotifyService.localSyncNotify(notify);
                        logger.info("==============[{}]平台的增值积分汇总文件通知的结果：{} ============",notify.getToPlat(),success);
                    }
                }
                logger.info("==============增值积分汇总上传文件的通知数量：{} ============",notifies.size());
            }else{ //数据为空
                record.setSuccess(1);
                record.setReason(" data is null ");
                operService.save(key, record);
            }
            logger.info("============积分增值定时任务执行结束=============");
        } catch (HsException e) {
            logger.error("==========积分增值定时任务执行异常 =========" , e);
            //修改操作记录
            OperRecord exceRecord = operService.getValueByKey(key);
            exceRecord.setRecount(exceRecord.getRecount()+1);
            exceRecord.setReason(" exception occurs, "+e.getMessage());
            operService.save(key, exceRecord);
        }finally {
            lock.unlock();//解锁
        }
    }

    /**
     * 发给账务的增值信息。汇总本周企业增值的所有记录，重新封装并装入MAP
     *
     * @param collection  从数据库查出的增值信息列表
     * @param startRowKey 开始时间
     * @param endRowKey   结束时间
     */
    private void gatherValue(Collection<PointValue> collection, String startRowKey, String endRowKey,Map<String, Map<String, DetailResult<PointValue>>> platMap) throws HsException {

        for (PointValue value : collection) {
            //获取平台代码
            String code = platData.obtainCode(value.getResNo());
            Map<String, DetailResult<PointValue>> valueMap = platMap.get(code);
            //检查是否有对应平台的数据
            if (valueMap == null) {
                valueMap = new HashMap<>();
                platMap.put(code, valueMap);
            }
            DetailResult<PointValue> dr = valueMap.get(value.getResNo());
            //如果是新对象，设置企业的增值点信息
            if (dr == null) {
                dr = new DetailResult<>();
                dr.setStartDate(startRowKey);
                dr.setEndDate(endRowKey);
                dr.setTotalRow(1);
                dr.setTotalNum(value.getPv());
                dr.setData(value);
                //获取主节点最新信息
                IncNode mainEltVal = incrementService.getValueByKey(value.getCustId());
                value.setMain(mainEltVal, value.getMlmPoint1());
                //非成员企业获取左右节点最新信息
                if (CustType.SERVICE_CORP.getCode() == mainEltVal.getType() || CustType.TRUSTEESHIP_ENT.getCode() == mainEltVal.getType()) {
                    //非成员企业获取左节点最新信息
                    if (StringUtils.isNotEmpty(mainEltVal.getLeft())) {
                        IncNode leftVal = incrementService.getValueByKey(mainEltVal.getLeft());
                        value.setLeft(leftVal, value.getMlmPoint2());
                    }
                    //非成员企业获取右节点最新信息
                    if (StringUtils.isNotEmpty(mainEltVal.getRight())) {
                        IncNode rightVal = incrementService.getValueByKey(mainEltVal.getRight());
                        value.setRight(rightVal, value.getMlmPoint3());
                    }
                }
                valueMap.put(value.getResNo(), dr);
            } else {
                //如果是一个企业的多条记录，汇总信息
                dr.setTotalRow(dr.getTotalRow() + 1);
                //赠送积分数相加
                PointValue data = dr.getData();
                data.setMlmPoint1(data.getMlmPoint1() + value.getMlmPoint1());
                data.setMlmPoint2(data.getMlmPoint2() + value.getMlmPoint2());
                data.setMlmPoint3(data.getMlmPoint3() + value.getMlmPoint3());
                valueMap.put(value.getResNo(), dr);
            }
        }
    }

    /**
     * 检查是否有增值积分达到100000
     * @param valuesMap
     * @return
     * @throws HsException
     */
    private Map<String, DetailResult<PointValue>> checkValuesMap(Map<String, DetailResult<PointValue>> valuesMap) throws HsException{
        //检查是否有增值积分达到100000
        for (DetailResult<PointValue> d : valuesMap.values()) {
            PointValue detailValue = d.getData();
            detailValue.setMlmPoint1(reSetValue(detailValue.getMlmPoint1(), detailValue.getResNo()));
            detailValue.setMlmPoint2(reSetValue(detailValue.getMlmPoint2(), detailValue.getLeft1()));
            detailValue.setMlmPoint3(reSetValue(detailValue.getMlmPoint3(), detailValue.getRight1()));
            detailValue.setPv(detailValue.getMlmPoint1() + detailValue.getMlmPoint2() + detailValue.getMlmPoint3());
            d.setTotalNum(detailValue.getPv());
        }
        return valuesMap;
    }
    /**
     * 如果某个积分点的增值积分超过10W，此积分点的左右立刻清零。积分设为10W
     *
     * @param pv
     * @param key
     * @return
     * @throws HsException
     */
    private Integer reSetValue(Integer pv, String key) throws HsException {
        if (pv != null && pv > 100000 && StringUtils.isNotEmpty(key)) {
            IncNode incNode = incrementService.getValueByKey(key);
            incNode.setLP(0);
            incNode.setRP(0);
            incrementService.save(key, incNode);
            return 100000;
        } else {
            return pv;
        }
    }


}
