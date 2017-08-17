/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.job.runner.handler;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.bm.bean.DetailResult;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.job.runner.FileRunner;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.utils.MD5;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 增值积分上传文件的线程
 *
 * @Package :com.gy.hsxt.gpf.bm.job.runner.handler
 * @ClassName : MlmDataToFileHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/15 10:50
 * @Version V3.0.0.0
 */
public class MlmDataToFileHandler implements FileRunner<FutureBean<LocalNotify>> {

    private Logger logger = LoggerFactory.getLogger(MlmDataToFileHandler.class);

    private String toPlatCode;

    private String filePath;

    private Map<String, DetailResult<PointValue>> detailResultMap;

    private int exceCount;

    public MlmDataToFileHandler(String toPlatCode, String filePath, Map<String, DetailResult<PointValue>> detailResultMap) {
        this.toPlatCode = toPlatCode;
        this.filePath = filePath;
        this.detailResultMap = detailResultMap;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public FutureBean<LocalNotify> call() throws Exception {
        logger.info("==========创建的文件路径：{}==========", filePath);
        FutureBean<LocalNotify> futureBean = new FutureBean<>();
        futureBean.setToPlatCode(toPlatCode);
        futureBean.setFilePath(filePath);
        setExceCount(0);
        return doDeal(futureBean);
    }

    /**
     * 文件输出
     *
     * @param futureBean
     * @return
     */
    private FutureBean<LocalNotify> doDeal(FutureBean<LocalNotify> futureBean) {
        try {
            if (MapUtils.isNotEmpty(detailResultMap)) {
                LocalNotify localNotify = new LocalNotify();
                localNotify.setFileCount(1);
                List<FileDetail> details = new ArrayList<>();
                FileDetail detail = new FileDetail();
                detail.setStartTime(DateUtil.getCurrentDateTime());

                //上传文件处理
                File file = new File(filePath);
                if (file.exists()) {
                    FileUtils.deleteQuietly(file);
                }
                List<String> lines = new ArrayList<>();
                for (DetailResult<PointValue> dr : detailResultMap.values()) {
                    lines.add(JSON.toJSONString(dr));
                }
                FileUtils.writeLines(file, DEFAULT_CHARSET, lines, true);
                FileUtils.touch(file);

                //配置文件详情
                detail.setEndTime(DateUtil.getCurrentDateTime());
                detail.setPercent(1);
                detail.setSource(filePath);
                detail.setTarget(filePath);
                detail.setIsLocal(1);
                detail.setFileSize(file.length());
                detail.setFileName(file.getName());
                detail.setCode(MD5.toMD5code(file));
                detail.setUnit("B");

                details.add(detail);
                localNotify.setDetails(details);
                futureBean.setT(localNotify);
            }

        } catch (Exception e) {
            //异常次数加一
            logger.error("======创建增值积分数据汇总文件第{}次异常=======", exceCount++, e);
            if (exceCount < DEFAULT_MAX_COUNT) {//没有超过最大异常次数，重新尝试
                doDeal(futureBean);
            } else {
                futureBean.setHasException(true);
                futureBean.setT(null);
            }
        }
        return futureBean;
    }

    /**
     * 设置异常次数
     *
     * @param exceCount
     */
    public void setExceCount(int exceCount) {
        this.exceCount = exceCount;
    }
}
