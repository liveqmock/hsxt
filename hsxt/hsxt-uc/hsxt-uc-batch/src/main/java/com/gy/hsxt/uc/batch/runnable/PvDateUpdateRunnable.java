/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.batch.runnable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.uc.batch.mapper.BatchMapper;

/**
 * 读取单个文件，根据文件内容，批量更新用户积分时间
 * 
 * @Package: com.gy.hsxt.uc.batch.runnable
 * @ClassName: PvDateUpdateRunnable
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
public class PvDateUpdateRunnable implements Runnable {
    private Log log = LogFactory.getLog(this.getClass());

    String fileAddr; // 文件地址

    BatchMapper batchMapper; // 数据库操作dao

    int batchCount; // 批量入库条数

    CountDownLatch countDownLatch;

    IDSBatchServiceListener listener;

    /**
     * 读取单个文件，根据文件内容，批量更新用户积分时间
     * 
     * @param fileAddr
     *            文件地址
     * @param batchMapper
     *            数据库处理dao
     * @param batchCount
     *            批处理数量，每batchCount条数据，提交一次数据库操作
     * @param countDownLatch
     *            线程阻塞器
     * @param listener
     *            任务监听器
     */
    public PvDateUpdateRunnable(String fileAddr, BatchMapper batchMapper, int batchCount,
            CountDownLatch countDownLatch, IDSBatchServiceListener listener) {
        this.fileAddr = fileAddr;
        this.batchMapper = batchMapper;
        this.batchCount = batchCount;
        this.countDownLatch = countDownLatch;
        this.listener = listener;

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
            reportStatus(2, "准备读取文件:" + fileAddr);
            doAction();
            reportStatus(2, "单个文件处理完成:" + fileAddr);
        }
        catch (Exception e)
        {
            reportStatus(1, "单个文件处理出错:" + fileAddr);
            e.printStackTrace();
        }
        finally
        {
            countDownLatch.countDown(); // 汇报线程执行完毕
        }
    }

    /**
     * 读取文件内容，并把数据入库
     */
    void doAction() throws Exception {
        String encoding = "UTF-8";
        File file = new File(fileAddr);
        // 读取记账数据文件
        InputStreamReader isReader = null;
        BufferedReader bfReader = null;
        try
        {
            String fileName = file.getName(); // yyyymmdd_n.txt
            String[] fileNameCols = fileName.split("_");
            String pvDate = fileNameCols[0];// yyyymmdd
            isReader = new InputStreamReader(new FileInputStream(file), encoding);
            bfReader = new BufferedReader(isReader);
            ArrayList<String> custIdList = new ArrayList<String>(1024);
            ArrayList<String> entIdList = new ArrayList<String>(1024);
            while (true)
            {
                // 获取一行记账数据( cust_id|cust_type)(type:1持卡人，2成员企业，3托管企业)
                String lineTxt = bfReader.readLine();

                // 读取到END就结束
                if (lineTxt == null || lineTxt.equals("END"))
                {
                    log.info(fileAddr + Thread.currentThread().getName() + "end =" + lineTxt);
                    break;
                }

                String[] cust_id_info = lineTxt.split("\\|");
                if ("1".equals(cust_id_info[1]))
                { // 1持卡人
                    custIdList.add(cust_id_info[0]);
                    if (custIdList.size() == batchCount)
                    {
                        updateCust(batchMapper, pvDate, custIdList);
                        custIdList = new ArrayList<String>(1024);
                    }
                }
                else
                {
                    entIdList.add(cust_id_info[0]);
                    if (entIdList.size() == batchCount)
                    {
                        updateEnt(batchMapper, pvDate, entIdList);
                        entIdList = new ArrayList<String>(1024);
                    }
                }

            }
            updateCust(batchMapper, pvDate, custIdList);
            updateEnt(batchMapper, pvDate, entIdList);
        }

        catch (FileNotFoundException e)
        {
            reportStatus(1, "执行出错,单个文件处理出错FileNotFoundException:" + fileAddr);
            e.printStackTrace();
            throw e;
        }
        catch (Exception e)
        {
            reportStatus(1, fileAddr + "执行出错,单个文件处理出错:" + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally
        {

            // 关闭读取
            if (bfReader != null)
            {
                try
                {
                    bfReader.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            // 关闭流
            if (isReader != null)
            {
                try
                {
                    isReader.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 持卡人积分时间批量入库更新,同步方法，避免并发进行表更新
     * 
     * @param pvDate
     * @param idList
     */
    private synchronized static void updateCust(BatchMapper batchMapper, String pvDate, List<String> idList) {
        if (idList == null || idList.isEmpty())
        {
            System.err.println(Thread.currentThread().getName() + " updateCust idList == null || idList.isEmpty");
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("pvDate", pvDate);
        map.put("idList", idList);

        System.out.println(System.currentTimeMillis() + Thread.currentThread().getName()
                + "begin updateCust idList.size=" + idList.size());
        int updateCount = batchMapper.updateCustPvDate(map);
        System.out.println(Thread.currentThread().getName() + "end updateCust updateCount=" + updateCount);

    }

    /**
     * 企业积分时间批量入库更新 ,同步方法，避免并发进行表更新
     * 
     * @param pvDate
     * @param idList
     */
    private synchronized static void updateEnt(BatchMapper batchMapper, String pvDate, List<String> idList) {
        if (idList == null || idList.isEmpty())
        {
            System.err.println(Thread.currentThread().getName() + "updateEnt idList == null || idList.isEmpty");
            return;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("pvDate", pvDate);
        map.put("idList", idList);
        System.out.println(Thread.currentThread().getName() + " begin updateEnt idList.size=" + idList.size());
        int updateCount = batchMapper.updateEntPvDate(map);
        System.out.println(Thread.currentThread().getName() + "end updateCust updateCount=" + updateCount);
    }
}
