/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.job.runner.handler;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.bm.bean.BmlmVo;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.job.runner.FileRunner;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.utils.MD5;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建再增值积分汇总数据文件线程
 *
 * @Package :com.gy.hsxt.gpf.bm.job.runner.handler
 * @ClassName : BmlmDataToFileHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/14 20:35
 * @Version V3.0.0.0
 */
public class BmlmDataToFileHandler implements FileRunner<FutureBean<LocalNotify>> {

    private Logger logger = LoggerFactory.getLogger(BmlmDataToFileHandler.class);

    /**
     * 再增值积分汇总数据
     */
    private List<BmlmVo> bmlmVos;

    /**
     * 目的平台代码
     */
    private String toPlatCode;

    /**
     * 文件上传路径
     */
    private String filePath;

    /**
     * 异常次数
     */
    private int exceCount;

    public BmlmDataToFileHandler(String toPlatCode, String filePath, List<BmlmVo> bmlmVos) {
        this.toPlatCode = toPlatCode;
        this.filePath = filePath;
        this.bmlmVos = bmlmVos;
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
        FutureBean<LocalNotify> futureBean = new FutureBean<LocalNotify>();
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
            if (CollectionUtils.isNotEmpty(bmlmVos)) {
                LocalNotify localNotify = new LocalNotify();
                localNotify.setFileCount(1);
                List<FileDetail> details = new ArrayList<>();
                FileDetail detail = new FileDetail();
                detail.setStartTime(DateUtil.getCurrentDateTime());

                File file = new File(filePath);
                if (file.exists()) {
                    FileUtils.deleteQuietly(file);
                }
                List<String> lines = new ArrayList<>();
                for (BmlmVo bmlmVo : bmlmVos) {
                    lines.add(JSON.toJSONString(bmlmVo));
                }
                FileUtils.writeLines(file, DEFAULT_CHARSET, lines, true);
                FileUtils.touch(file);

                //配置文件详情
                detail.setEndTime(DateUtil.DateTimeToString(new Date()));
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
            logger.error("======创建再增值积分数据汇总文件第{}次异常=======", exceCount++, e);
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
