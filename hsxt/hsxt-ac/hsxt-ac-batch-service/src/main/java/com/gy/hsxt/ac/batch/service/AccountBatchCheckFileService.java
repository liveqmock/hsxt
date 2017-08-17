package com.gy.hsxt.ac.batch.service;

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
import com.gy.hsxt.ac.api.IAccountCheckFileService;
import com.gy.hsxt.ac.batch.mapper.AccountEntryBatchMapper;
import com.gy.hsxt.ac.batch.service.runnable.AccountCheckFileRunnable;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.common.bean.AccountTransSystem;
import com.gy.hsxt.ac.common.bean.PropertyConfigurer;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账务系统生成对账文件Service
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
public class AccountBatchCheckFileService implements IDSBatchService,IAccountCheckFileService {

    /**
     * 记账数据库操作Mapper
     */
    @Autowired
    private AccountEntryBatchMapper accountEntryBatchMapper;


    protected static final Logger LOG = LoggerFactory.getLogger(AccountBatchCheckFileService.class);
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
            listener.reportStatus(executeId,2, "执行中");
            
            String batchJobName = args.get("batchJobName");
        	String fileAddress = args.get("fileAddress");
        	String batchDate = args.get("batchDate");
        	String transSyse = args.get("transSyse");
            try
            {
            	//批记账任务
            	createAccountCheckFile(batchJobName, fileAddress, batchDate, transSyse);
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId,0,"执行成功");
            }
            catch (Exception e)
            {
                LOG.info(e.getMessage()+"异常，执行失败!!!!");
                // 发送进度通知
                listener.reportStatus(executeId,1,"执行失败:"+e.getMessage());
                result=false;
            }
        }
        return result;
    }
    
    /**
     * 生成对账文件任务
     * 
     * @param batchJobName
     *            任务名称
     * @param fileAddress
     *            文件写路径
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void createAccountCheckFile(String batchJobName, String fileAddress, String batchDate, String transSyse) throws HsException {
        SystemLog.info("HSXT_AC", "createAccountCheckFile", "生成对账文件任务begin");
    	if(fileAddress == null || fileAddress.length() ==0)
    	{
    		String dirRoot = PropertyConfigurer.getProperty("dir.root");
    		fileAddress = dirRoot+File.separator+"TCAS"+File.separator+"AC"+File.separator;
    	}
        // 对账文件系统分类
        String[] transSyses = transSyse.split("\\|");
        Map<String, AccountTransSystem> accTransSystemMap = new HashMap<String, AccountTransSystem>();
        
        //日期为文件夹名称
        String newDate = batchDate;
        if("".equals(newDate) || newDate == null)
        {
        	newDate = new SimpleDateFormat("yyyyMMdd").format(preYesterday());
        }
        
        
        
        File file = new File(fileAddress + newDate);
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
                	for(int y =0; y< transSyses.length; y++ )
                	{
                		if(ff[i].getName().equals("AC_" + transSyses[y] + "_" + newDate + "_CHK"))
                		{
                			ff[i].delete();
                		}
                		if(ff[i].getName().equals("AC_" + transSyses[y] + "_" + newDate + "_DET_"+i))
                		{
                			ff[i].delete();
                		}
                	}
                }
            }
        }
        for (int i = 0; i < transSyses.length; i++)
        {
            String fileName = "AC_" + transSyses[i] + "_" + newDate + "_DET_1";
            AccountTransSystem accountTransSystem = new AccountTransSystem();
            accountTransSystem.setTransSys(transSyses[i]);
            accountTransSystem.setFileName(fileName);
            accountTransSystem.setFileNum(1);
            accountTransSystem.setNewDate(newDate);
            accountTransSystem.setFilePath(fileAddress);
            accTransSystemMap.put(transSyses[i], accountTransSystem);
        }
        // 查询对账文件数据时间段条件
        Map<String, Object> accountEntryMap = new HashMap<String, Object>();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(preYesterday());
        if(batchDate!=null&&batchDate.length()>0)
        {
        	try {
        		date = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
        	} catch (ParseException e) {
        		SystemLog.error("HSXT_AC", "生成对账文件任务:createAccountCheckFile()", e.getMessage(), e);
            	throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), e.getMessage());
			}
        }
        
        //对账系统
      	accountEntryMap.put("transSyses",transSyses);
        // 开始时间
        accountEntryMap.put("beginDate", Timestamp.valueOf(date + " 00:00:00.0"));
        // 结束时间
        accountEntryMap.put("endDate", Timestamp.valueOf(date + " 23:59:59.999"));
        try
        {
            // 查询当前时间区间的记账分录数量
            int size = accountEntryBatchMapper.seachAccountEntryCount(accountEntryMap);
            if (size == 0)
            {
            	for (int i = 0; i < transSyses.length; i++)
                {
                    String fileCHKName = "AC_" + transSyses[i] + "_" + newDate + "_CHK";
                    String checkFileAdder = fileAddress + newDate + File.separator + fileCHKName;
                    StringBuilder writeFileData = new StringBuilder();
                    writeFileData.append(0+"\r\n");
                    FileWriter fw = new FileWriter(checkFileAdder, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(writeFileData.toString());
                    bw.write("END");
                    bw.flush();
                    fw.close();
                    bw.close();
                }
                return;
            }

            // 子线程查询总条数
            int sumRow = Integer.valueOf(PropertyConfigurer.getProperty("ac.accountCheck.sumRow"));

            // 子线程数量=当前时间区间的记账分录数量/子线程查询总条数
            int count = size / sumRow;

            // 获取当前时间区间的记账分录数量%子线程查询总条数的余数
            int remainder = size % sumRow;

            // 如果有余数，子线程数量+1
            if (remainder > 0)
            {
                count++;
            }

            // 线程池
            ExecutorService exe = Executors.newFixedThreadPool(count);
            for (int i = 0; i < count; i++)
            {
                // 获取批任务类
                AccountBatchJob accountBatchJob = new AccountBatchJob();

                // 线程池注入线程
                exe.execute(new AccountCheckFileRunnable(accountBatchJob, accTransSystemMap, accountEntryBatchMapper, accountEntryMap, i
                        * sumRow));
            }

            // 停止给线程池添加线程
            exe.shutdown();
            while (true)
            {
                if (exe.isTerminated())
                {
                    for (int i = 0; i < transSyses.length; i++)
                    {
                        AccountTransSystem accountTransSystem = accTransSystemMap.get(transSyses[i]);
                        int fileNum = accountTransSystem.getFileNum();
                        String fileCHKName = "AC_" + transSyses[i] + "_" + newDate + "_CHK";
                        String checkFileAdder = fileAddress + newDate + File.separator + fileCHKName;
                        StringBuilder writeFileData = new StringBuilder();
                        writeFileData.append(fileNum+"\r\n");
                        for (int y = 1; y <= fileNum; y++)
                        {
                            String fileName = "AC_" + transSyses[i] + "_" + newDate + "_DET_" + y;
                            String batchFileAddress = fileAddress + newDate + File.separator + fileName;
                            // 读取记账文件
                            File chargeFile = new File(batchFileAddress);
                            if (chargeFile.exists())
                            {
                                long fileLength = chargeFile.length();
                                LineNumberReader rf = null;
                                FileWriter fw = null;
                                BufferedWriter bw = null;
                                try
                                {
                                    rf = new LineNumberReader(new FileReader(chargeFile));
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
                                    fw = new FileWriter(batchFileAddress, true);
                                    bw = new BufferedWriter(fw);
                                    bw.write("END");
                                    bw.flush();
                                    // 获取check文件名称与记录条数
                                    writeFileData.append(fileName + "|" + lines);
                                    writeFileData.append("\r\n");
                                    if(y == fileNum)
                                    {
                                    	writeFileData.append("END");
                                    }
                                }
                                catch (IOException e)
                                {
                                    throw e;
                                }
                                finally
                                {
                                    try
                                    {
                                    	if(fw != null)
                                    	{
                                    		fw.close();
                                    	}
                                    	if(bw != null)
                                    	{
                                    		bw.close();
                                    	}
                                    }
                                    catch (IOException e)
                                    {
                                        throw e;
                                    }
                                }
                            }
                        }
                        // 回写check文件
                        FileWriter fw = null;
                        BufferedWriter bw = null;
                        try
                        {
                            fw = new FileWriter(checkFileAdder, true);
                            bw = new BufferedWriter(fw);
                            bw.write(writeFileData.toString());
                            bw.flush();
                        }
                        catch (IOException e)
                        {
                            throw e;
                        }
                        finally
                        {
                            try
                            {
                            	if(fw != null)
                            	{
                            		fw.close();
                            	}
                            	if(bw != null)
                            	{
                            		bw.close();
                            	}
                            }
                            catch (IOException e)
                            {
                                throw e;
                            }
                        }
                    }
                    break;
                }
                Thread.sleep(200);
            }

        }
        catch (IOException e)
        {
        	SystemLog.error("HSXT_AC", "生成对账文件任务:createAccountCheckFile()", e.getMessage(), e);
        	throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        catch (InterruptedException e)
        {
            SystemLog.error("HSXT_AC", "生成对账文件任务:createAccountCheckFile()", e.getMessage(), e);
            throw new HsException(RespCode.AC_INTERRUPTED.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "生成对账文件任务:createAccountCheckFile()", e.getMessage(), e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        SystemLog.info("HSXT_AC", "createAccountCheckFile", "生成对账文件任务end");
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
