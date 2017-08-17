package com.gy.hsxt.rp.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
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

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsBatchWelfareScoreFileService;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportsWelfareScoreExportFile;
import com.gy.hsxt.rp.mapper.ReportsSystemResourceMapper;
import com.gy.hsxt.rp.service.runnable.ReportsWelfareScoreRunnable;

/**
 * 福利积分值批任务处理Service
 * 
 * @Package: com.gy.hsxt.ac.service
 * @ClassName: AccountBatchProcesService
 * @Description: TODO
 * 
 * @author: maocan
 * @date: 2015-8-31 上午10:19:22
 * @version V1.0
 */
@Service
public class ReportsBatchWelfareScoreFileBatchService implements IDSBatchService, IReportsBatchWelfareScoreFileService {

    /**
     * 系统资源信息Mapper
     */
    @Autowired
    private ReportsSystemResourceMapper reportsSystemResourceMapper;

    
    protected static final Logger LOG = LoggerFactory.getLogger(ReportsBatchWelfareScoreFileBatchService.class);
    /**
     * 回调监听类
     */
    @Autowired
    private IDSBatchServiceListener listener;
    
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) 
    {
        boolean result=true;
        if(listener!=null){
            LOG.info("执行中!!!!");
            // 发送进度通知
            listener.reportStatus(executeId, 2, "执行中");
            
            try
            {
            	String batchJobName = args.get("batchJobName");
            	String fileAddress = args.get("fileAddress");
            	String batchDate = args.get("batchDate");
            	//批记账任务
            	welfareScoreFile(batchJobName, fileAddress, batchDate);
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0,"执行成功");
            }
            catch (Exception e)
            {
                LOG.info("异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 1,"执行失败");
                result=false;
            }
        }
        return result;
    }


    /**
     * 批生成福利积分值资源列表
     * 
     * @param batchJobName
     *            任务名称
     * @param fileAddress
     *            福利积分值资源列表文件生成路径
     * @throws HsException
     * 
     */
    @Override
    public void welfareScoreFile(String batchJobName, String fileAddress, String batchDate) throws HsException {
        Map<String, ReportsWelfareScoreExportFile> reportsExportFileMap = new HashMap<String, ReportsWelfareScoreExportFile>();
        String newDate = new SimpleDateFormat("yyyyMMdd").format(preYesterday());
        String newMonth = new SimpleDateFormat("yyyyMM").format(preYesterday());
        if(batchDate!=null&&batchDate.length()>0)
        {
        	try {
	        	newDate = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
	            newMonth = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
        	} catch (ParseException e) {
        		SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
            	throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), e.getMessage());
			}
        }
        
        if(fileAddress==null || fileAddress.length() == 0)
        {
        	String dirRoot = PropertyConfigurer.getProperty("dir.root");
        	fileAddress = dirRoot + File.separator + "WS_PS" + File.separator;
        }
        File file = new File(fileAddress + newMonth);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory())
        {
            file.mkdirs();
        }
        else
        {
            if (file.listFiles().length == 0)
            {
                file.delete();
            }
            else
            {
                File[] ff = file.listFiles();
                for (int i = 0; i < ff.length; i++)
                {
                    ff[i].delete();
                }
            }
        }
        file = new File(fileAddress + newMonth+ File.separator +newDate);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory())
        {
            file.mkdirs();
        }
        else
        {
            if (file.listFiles().length == 0)
            {
                file.delete();
            }
            else
            {
                File[] ff = file.listFiles();
                for (int i = 0; i < ff.length; i++)
                {
                    ff[i].delete();
                }
            }
        }
        
        String fileName = "WS_PS_DETAIL_1.TXT";
        ReportsWelfareScoreExportFile reportsExportFile = new ReportsWelfareScoreExportFile();
        reportsExportFile.setFilePath(fileAddress);
        reportsExportFile.setFileName(fileName);
        reportsExportFile.setFileNum(1);
        reportsExportFile.setNewMonth(newMonth);
        reportsExportFile.setNewDate(newDate);
        reportsExportFileMap.put("WS_PS", reportsExportFile);
        // 查询对账文件数据时间段条件
        Map<String, Object> welfareScoreMap = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayDate = sdf.format(preYesterday());
        if(batchDate!=null&&batchDate.length()>0)
        {
        	try {
        		yesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
        	} catch (ParseException e) {
        		SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
            	throw new HsException(RespCode.RP_PARAMETER_FORMAT_ERROR.getCode(), e.getMessage());
			}
        }
        
        // 昨天开始时间
        String beginDate = yesterdayDate + " 00:00:00";
        Timestamp beginDateTim = Timestamp.valueOf(beginDate);
        
        // 昨天结束时间
        String endDate = yesterdayDate + " 23:59:59";
        Timestamp endDateTim = Timestamp.valueOf(endDate);
        
        // 开始时间
        welfareScoreMap.put("beginDate", beginDateTim);
        // 结束时间
        welfareScoreMap.put("endDate", endDateTim);
        try
        {
            // 查询当前时间区间的记账分录数量
            int size = reportsSystemResourceMapper.searchWelfareScoreCount(welfareScoreMap);
            if(size == 0){
                return;
            }
            int row =  Integer.valueOf(PropertyConfigurer.getProperty("rp.reportsCheck.sumRow"));
            int count = size / row;
            int num = size % row;
            int remainder = row;
            if (num > 0)
            {
                count++;
            }
            // 每十万数据启动一个线程，做文件生成
            ExecutorService exe = Executors.newFixedThreadPool(count);
            for (int i = 0; i < count; i++)
            {
                if(num > 0 && i == count - 1){
                    remainder = size % row;
                }
                exe.execute(new ReportsWelfareScoreRunnable(reportsExportFileMap, reportsSystemResourceMapper, welfareScoreMap, i,remainder));
            }
            exe.shutdown();
            while (true)
            {
                if (exe.isTerminated())
                {
                    ReportsWelfareScoreExportFile accountTransSystem_ = reportsExportFileMap.get("WS_PS");
                    int fileNum = accountTransSystem_.getFileNum();
                    String fileCHKName = "WS_PS_CHECK.TXT";
                    String checkFileAdder = fileAddress + newMonth + File.separator + newDate + File.separator + fileCHKName;
                    StringBuilder writeFileData = new StringBuilder();
                    for (int y = 1; y <= fileNum; y++)
                    {
                        String fileName_ = "WS_PS_DETAIL_" + y + ".TXT";
                        String batchFileAddress = fileAddress + newMonth + File.separator + newDate + File.separator + fileName_;
                        // 读取记账文件
                        File chargeFile = new File(batchFileAddress);
                        if (chargeFile.exists())
                        {
                            long fileLength = chargeFile.length();
                            LineNumberReader rf = new LineNumberReader(new FileReader(chargeFile));
                            // 记账文件记录数
                            Integer lines = 0;
                            if (rf != null)
                            {
                                rf.skip(fileLength);
                                // 得到记账文件记录数
                                lines = rf.getLineNumber();
                                // 关闭记账文件记录数读取流
                                rf.close();
                            }
                            FileWriter fw = new FileWriter(batchFileAddress, true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write("END");
                            bw.flush();
                            fw.close();
                            bw.close();
                            writeFileData.append(fileName_ + "|" + lines);
                            writeFileData.append("\r\n");
                        }
                    }
                    FileWriter fw = new FileWriter(checkFileAdder, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(writeFileData.toString());
                    bw.write("END");
                    bw.flush();
                    fw.close();
                    bw.close();
                    break;
                }
                Thread.sleep(200);
            }

        }
        catch (IOException e)
        {
        	SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
        	throw new HsException(RespCode.RP_IO_ERROR.getCode(), e.getMessage());
        }
        catch (InterruptedException e)
        {
        	SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
        	throw new HsException(RespCode.RP_INTERRUPTED.getCode(), e.getMessage());
        }
        catch (HsException e)
        {
        	SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
        	throw e;
        }
        catch (Exception e)
        {
        	SystemLog.error("HSXT_RP", "批生成福利积分值资源文件任务:welfareScoreFile()", e.getMessage(), e);
        	throw new HsException(RespCode.RP_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
    //获取昨日日期
    public Date preYesterday() {
    	Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        return calendar.getTime();
    }
    

    //获取上个月第一天的日期
    public Timestamp preMonthFirstDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        String monthStart = String.valueOf(sdf.format(calendar.getTime()));
        Timestamp monthStartDate = Timestamp.valueOf(monthStart + " 00:00:00");
        return monthStartDate;
    }
    //获取上个月最后一天的日期
    public Timestamp preMonthLastDayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String monthEnd = String.valueOf(sdf.format(calendar.getTime()));
        Timestamp monthEndDate = Timestamp.valueOf(monthEnd + " 23:59:59");
        return monthEndDate;
    }
}
