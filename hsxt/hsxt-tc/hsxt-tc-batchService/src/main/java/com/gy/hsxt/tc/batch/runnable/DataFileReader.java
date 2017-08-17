/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tc.batch.runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.batch.mapper.TcGpchMapper;

/**
 * 读取单个文件，根据文件内容，批量数据入库
 * 
 * @Package: com.gy.hsxt.tc.batch.runnable
 * @ClassName: DataFileReader
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
public class DataFileReader implements Runnable {
    private static Log log = LogFactory.getLog(DataFileReader.class);

    /**
     * 文件地址
     */
    String mFileAddr;

    /**
     * check文件里设置的本文件对账数据记录数
     */
    int mDataCountFromCheckFile;

    /**
     * 对账数据入库处理器
     */
    IDataHandler mDataHandler;

    /**
     * 批量入库条数
     */
    int batchCount = 1000;

    /**
     * 线程阻塞器
     */
    CountDownLatch countDownLatch;

    /**
     * 调度系统 状态回调 监听器
     */
    IDSBatchServiceListener listener;

    /**
     * 线程返回值：文件数据ＩＤ列表
     */
    private ArrayList<String> totalCheckKeyList;
    
    public boolean isSaveCheckKeyToRedis=true;

    public DataFileReader(String pFileAddr, int pFileDataCount, IDataHandler pDataHandler,
            ArrayList<String> pTotalCheckKeyList, CountDownLatch pCountDownLatch, IDSBatchServiceListener pListener) {
        this.mFileAddr = pFileAddr;
        this.mDataCountFromCheckFile = pFileDataCount;
        this.mDataHandler = pDataHandler;
        this.totalCheckKeyList = pTotalCheckKeyList;
        this.countDownLatch = pCountDownLatch;
        this.listener = pListener;

    }

    void reportStatus(int code, String msg) {
        if (listener != null)
        {
            log.info(Thread.currentThread().getName() + msg);
            // 发送进度通知
//            listener.reportStatus(code, msg);
        }
        else
        {
            log.info(Thread.currentThread().getName() + code + " IDSBatchServiceListener is null,msg=" + msg);
        }
    }

    public void run() {
        try
        {
            reportStatus(2, "准备读取文件:" + mFileAddr);
            doAction();
            reportStatus(2, "单个文件处理完成:" + mFileAddr);
        }
        catch (Exception e)
        {
            log.error(mFileAddr + "文件处理异常:" + e.getMessage());
            reportStatus(1, mFileAddr + "文件处理异常:" + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            countDownLatch.countDown(); // 通知主线程阻塞器，本线程执行完毕
        }
    }

    /**
     * 读取文件内容，并把数据入库
     */
    void doAction() {
        String encoding = "UTF-8";
        File file = new File(mFileAddr); // GP_CH_YYMMDD_DET_01
        // 读取记账数据文件
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try
        {
            inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
            bufferedReader = new BufferedReader(inputStreamReader);

            // 待入库数据列表
            ArrayList<Map<String, String>> lDataList = new ArrayList<Map<String, String>>(1024);
            // 待返回对账要素列表
            ArrayList<String> lCheckKeyList =getCheckKeyList();

            int lLineNum = 0;

            while (true)
            {
                // 从数据文件里获取一行文本
                String lLineTxt = bufferedReader.readLine();
                // 读取到END就结束
                if (lLineTxt == null || lLineTxt.equalsIgnoreCase("END"))
                {
                    log.info(mFileAddr + Thread.currentThread().getName() + " end =" + lLineTxt);
                    break;
                }
                lLineNum++;
                mDataHandler.proccessRow(lDataList, lCheckKeyList, lLineTxt);

                if (lDataList.size() >= batchCount)
                {
                    mDataHandler.saveData(lDataList, lCheckKeyList, totalCheckKeyList);
                    lDataList = new ArrayList<Map<String, String>>(1024);
                    lCheckKeyList = getCheckKeyList();
                }

            }
            // chk文件里面的文件记录数 与 实际从数据文件的读取的记录数 做比较，若不一致 则抛异常
            if (mDataCountFromCheckFile != lLineNum)
            {
                log.error(mDataCountFromCheckFile + "=check文件记录数 不等于 数据文件记录数 =" + lLineNum);
                throw new HsException(34000, mDataCountFromCheckFile + "=check文件记录数 不等于 数据文件记录数 =" + lLineNum);
            }
            // 把数据入库
            mDataHandler.saveData(lDataList, lCheckKeyList, totalCheckKeyList);
           

        }

        catch (FileNotFoundException e)
        {
            reportStatus(1, "执行出错,单个文件处理出错FileNotFoundException:" + mFileAddr);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            reportStatus(1, mFileAddr + "执行出错,单个文件处理出错:" + e.getMessage());
            e.printStackTrace();
        }
        finally
        {

            try
            {
                // 关闭读取
                bufferedReader.close();
                // 关闭流
                inputStreamReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
            }

        }

    }
    
    private ArrayList<String> getCheckKeyList(){
         if(isSaveCheckKeyToRedis){
        	 return new ArrayList<String>(1024);
         }
         return null;
    }

}
