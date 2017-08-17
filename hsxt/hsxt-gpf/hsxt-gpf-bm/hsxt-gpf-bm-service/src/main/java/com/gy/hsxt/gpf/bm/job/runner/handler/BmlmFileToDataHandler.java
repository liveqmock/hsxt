/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.job.runner.handler;

import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.job.runner.FileRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 再增值积分下载文件解析线程
 *
 * @Package :com.gy.hsxt.gpf.bm.job.runner.handler
 * @ClassName : BmlmFileToDataHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/14 14:55
 * @Version V3.0.0.0
 */
public class BmlmFileToDataHandler implements FileRunner<FutureBean<List<Bmlm>>> {

    private Logger logger = LoggerFactory.getLogger(BmlmFileToDataHandler.class);

    private File file;

    private int exceCount;

    public BmlmFileToDataHandler(File file) {
        this.file = file;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public FutureBean<List<Bmlm>> call() throws Exception {
        FutureBean<List<Bmlm>> futureBean = new FutureBean<List<Bmlm>>();
        futureBean.setFilePath(file.getPath());
        List<Bmlm> bmlms = new ArrayList<Bmlm>();
        futureBean.setT(bmlms);
        setExceCount(0);
        return doDeal(futureBean);
    }

    /**
     * 解析文件
     *
     * @param futureBean
     * @return
     */
    private FutureBean<List<Bmlm>> doDeal(FutureBean<List<Bmlm>> futureBean) {
        try {
            List<String> contents = FileUtils.readLines(file, DEFAULT_CHARSET);
            if (contents.isEmpty()) {
                logger.info("==========文件[{}]为空==========", file.getName());
            } else {
                for (String content : contents) {
                    String[] elements = StringUtils.splitByWholeSeparatorPreserveAllTokens(content,"|");
                    String custId = StringUtils.trimToEmpty(elements[0]);
                    String point = StringUtils.trimToEmpty(elements[1]);
                    logger.debug("==========数据分拆结果custId:[{}],point:[{}]==========", custId,point);
                    if (elements.length == 2) {
                        Bmlm bmlm = new Bmlm(custId,point);
                        futureBean.getT().add(bmlm);
                    } else {
                        logger.info("============内容[{}]格式有问题===========", content);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("===========文件[{}]第{}次解析异常=========", file.getName(), exceCount++, e);
            //出现异常，必须清除之前的装载
            futureBean.getT().clear();
            if (exceCount < DEFAULT_MAX_COUNT) {//没有超过最大异常次数，重新尝试
                doDeal(futureBean);
            } else {
                futureBean.setHasException(true);
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
