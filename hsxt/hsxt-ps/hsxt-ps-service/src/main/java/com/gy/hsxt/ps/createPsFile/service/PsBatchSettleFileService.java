package com.gy.hsxt.ps.createPsFile.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsec.external.api.TmsSettlementService;
import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsBatchSettleFileService;
import com.gy.hsxt.ps.common.PropertyConfigurer;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.createPsFile.bean.PsEntrySettle;
import com.gy.hsxt.ps.createPsFile.mapper.PsBatchSettleFileMapper;
import com.gy.hsxt.ps.createPsFile.runnable.PsBatchSettleFileRunnable;

/**
 * 批生成互商结算信息文件Service
 *
 * @version V1.0
 * @Package: com.gy.hsxt.ps.createPsFile.service
 * @ClassName: PsBatchSettleFileService
 * @Description: TODO
 * @author: weixz
 * @date: 2016-3-21 pm:14:19:22
 */
@Service("psBatchSettleFileService")
public class PsBatchSettleFileService implements IDSBatchService, IPsBatchSettleFileService {

    /**
     * 查询结算信息操作Mapper
     */
    @Autowired
    private PsBatchSettleFileMapper psBatchSettleFileMapper;
    
    /**
     * 对外API
     */
    @Autowired
    private TmsSettlementService tmsSettlementService;

    protected static final Logger LOG = LoggerFactory.getLogger(PsBatchSettleFileService.class);

    /**
     * 回调监听类
     */
    @Autowired
    private IDSBatchServiceListener listener;

    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) {
        boolean result = true;
        if (listener != null) {
            LOG.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");

            try {
                String batchDate = args.get("batchDate");
                // 批记账任务
                createBatSettleFile("","",batchDate);
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0, "执行成功");
            } catch (Exception e) {
                LOG.info(e.getMessage() + "异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 1, "执行失败:" + e.getMessage());
                result = false;
            }
        }
        return result;
    }

    /**
     * 批生成互商结算信息文件列表
     *
     * @param batchJobName 记账任务名称
     * @param fileAddress  批生成互商结算信息文件生成路径
     * @throws HsException
     */
    @Override
    public void createBatSettleFile(String batchJobName, String fileAddress, String batchDate) throws HsException {
        SystemLog.info("HSXT_PS", "createBatSettleFile", "批生成互商结算信息文件begin");
        Map<String, PsEntrySettle> psEntrySettleMap = new HashMap<String, PsEntrySettle>();
        // 日期为文件夹名称
        String newDate = batchDate;
        if ("".equals(newDate) || newDate == null) {
            newDate = new SimpleDateFormat("yyyyMMdd").format(preYesterday());
        }
        String fileName = newDate + ".cvs";
        if (fileAddress == null || fileAddress.length() == 0) {
            String dirRoot = PropertyConfigurer.getProperty("ps.filePath");
            fileAddress = dirRoot + File.separator + "PS" + File.separator + "SETTLEINFO";
        }
        
        File file = new File(fileAddress);
        boolean isCreated=true;
        boolean deleted=true;
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
             isCreated = file.mkdirs();
        } else {
            File files = new File(fileAddress + File.separator + fileName);
            if(files.exists()){
                deleted = files.delete();
            }
           /* if (file != null) {
                File[] f=file.listFiles();
                if (f!=null) {
                    if (f.length == 0) {
                        deleted = file.delete();
                        isCreated = true;
                    } else {
                        File[] ff = file.listFiles();
                        if (ff != null) {
                            for (File aFf : ff) {
                                deleted = aFf.delete();
                                isCreated = true;
                            }
                        }
                    }
                }
            }*/
        }
        if (deleted&&isCreated) {
            //String fileName = newDate + ".CVS";
            PsEntrySettle psEntrySettle = new PsEntrySettle();
            psEntrySettle.setFilePath(fileAddress);
            psEntrySettle.setFileName(fileName);
            psEntrySettle.setFileNum(1);
            //psEntrySettle.setNewDate(newDate);
            psEntrySettleMap.put("PS", psEntrySettle);
            // 查询对账文件数据时间段条件
            Map<String, Object> accountEntryMap = new HashMap<String, Object>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String yesterdayDate = sdf.format(preYesterday());
            if (batchDate != null && batchDate.length() > 0) {
                try {
                    yesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd")
                            .parse(batchDate));
                } catch (ParseException e) {
                    SystemLog.error("HSXT_PS", "批生成互商结算信息文件任务:createBatSettleFile()", e.getMessage(), e);
                    throw new HsException(RespCode.PS_PARAM_ERROR.getCode(), e.getMessage());
                }
            }

            // 昨天开始时间
            String beginDate = yesterdayDate + " 00:00:00";
            Timestamp beginDateTim = Timestamp.valueOf(beginDate);

            // 昨天结束时间
            String endDate = yesterdayDate + " 23:59:59";
            Timestamp endDateTim = Timestamp.valueOf(endDate);


            // 开始时间
            accountEntryMap.put("beginDate", beginDateTim);
            // 结束时间
            accountEntryMap.put("endDate", endDateTim);

            try {
                // 查询当前时间区间的记账分录数量
                int size = psBatchSettleFileMapper.searchPsEntryCounts(accountEntryMap);
                if (size == 0) {
                    String checkFileAdder = fileAddress + File.separator + fileName;
                    StringBuilder writeFileData = new StringBuilder();
                    //FileWriter fw = new FileWriter(checkFileAdder, true);
                    Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(checkFileAdder),Charset.defaultCharset()));
                    BufferedWriter bw = new BufferedWriter(writer);
                    writeFileData.append(0 + "\r\n");
                    bw.write(writeFileData.toString());
                    bw.write("END");
                    bw.flush();
                    writer.close();
                    bw.close();
                    return;
                }
                int row = Integer.parseInt(PropertyConfigurer.getProperty("ps.batchSum"));
                int count = size / row;
                int num = size % row;
                int remainder = row;
                if (num > 0) {
                    count++;
                }
                // 每十万数据启动一个线程，做文件生成
                ExecutorService exe = Executors.newFixedThreadPool(count);
                for (int i = 0; i < count; i++) {
                    if (num > 0 && i == count - 1) {
                        remainder = size % row;
                    }
                    exe.execute(new PsBatchSettleFileRunnable(psEntrySettleMap,
                            psBatchSettleFileMapper, accountEntryMap, i, remainder,size));
                }
                exe.shutdown();
                while (true) {
                    if (exe.isTerminated()) {
                        // 调用电商对外API，更新结算结果信息
                        tmsSettlementService.executeSettlementResult(newDate);
                        break;
                    }
                    Thread.sleep(200);
                }

            } catch (IOException e) {
                SystemLog.error("HSXT_PS", "批生成互商结算信息文件列表:createBatSettleFile()", e.getMessage(), e);
                throw new HsException(RespCode.PS_WRITE_FILE_ERROR.getCode(), e.getMessage());
            } catch (InterruptedException e) {
                SystemLog.error("HSXT_PS", "批生成互商结算信息文件列表:createBatSettleFile()", e.getMessage(), e);
                throw new HsException(RespCode.PS_THREAD_ERROR.getCode(), e.getMessage());
            } catch (Exception e) {
                SystemLog.error("HSXT_PS", "批生成互商结算信息文件列表:createBatSettleFile()", e.getMessage(), e);
                throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
            }
        }
    }

    // 获取昨日日期
    public Date preYesterday() {
        Calendar calendar = Calendar.getInstance();// 获取日历
        calendar.setTime(new Date());// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
        return calendar.getTime();
    }

    public void insertStringInFile(File inFile, int lineno, String lineToBeInserted)
            throws Exception {
        // 临时文件
        File outFile = File.createTempFile("name", ".tmp");
        // 输入
        FileInputStream fis = new FileInputStream(inFile);
        BufferedReader in=new BufferedReader(new InputStreamReader(fis,Charset.defaultCharset()));
        // 输出
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(fos,Charset.defaultCharset()));
        // 保存一行数据
        String thisLine;
        // 行号从1开始
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            // 如果行号等于目标行，则输出要插入的数据
            if (i == lineno) {
                out.println(lineToBeInserted);
            }
            // 输出读取到的数据
            out.println(thisLine);
            // 行号增加
            i++;
        }
        out.flush();
        out.close();
        fis.close();
        fos.close();
        in.close();
        // 删除原始文件
        boolean deleted=inFile.delete();
        if (!deleted){
            PsException.psExceptionNotThrow(new Throwable().getStackTrace()[0],RespCode.FAIL.getCode(),"删除文件失败！");
        }
        // 把临时文件改名为原文件名
        boolean reanmed=outFile.renameTo(inFile);
        if (!reanmed){
            PsException.psExceptionNotThrow(new Throwable().getStackTrace()[0],RespCode.FAIL.getCode(),"修改文件失败！");
        }
    }
}
