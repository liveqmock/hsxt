package com.gy.hsxt.ac.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountBatchProcesService;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.common.bean.PropertyConfigurer;
import com.gy.hsxt.ac.mapper.AccountBatchProcesMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.ac.service.Transaction.AccountBatchTransaction;
import com.gy.hsxt.ac.service.runnable.AccountBatchChargeRunnable;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账务系统批任务处理Service
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
public class AccountBatchProcesService implements IDSBatchService, IAccountBatchProcesService {

    /**
     * 批处理任务数据库操作Mapper
     */
    @Autowired
    private AccountBatchProcesMapper accountBatchProcesMapper;

    /**
     * 记账数据库操作Mapper
     */
    @Autowired
    private AccountEntryMapper accountEntryMapper;

    /**
     * 批任务统一事务处理Service
     */
    @Autowired
    private AccountBatchTransaction accountBatchTransaction;

    
    protected static final Logger LOG = LoggerFactory.getLogger(AccountBatchProcesService.class);
   
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
            //执行任务名称
            String batchJobName = args.get("batchJobName");
            //执行任务文件夹路径
        	String fileAddress = args.get("fileAddress");
        	//执行任务数据日期
        	String batchDate = args.get("batchDate");
        	//是否返回所有数据执行情况
        	String returnInforFile = args.get("returnInforFile");
        	//账户是否可以为负数标识(1:账户正数，2：账户负数)
        	String accFlag = args.get("accFlag");
        	
            try
            {
            	boolean isReturnInforFile = "1".equals(returnInforFile)?true:false;
            	// true:账户可以为负数，false:账户不可以为负数
            	boolean flag = "2".equals(accFlag)?true:false;
            	//批记账任务
            	batchChargeAccount(batchJobName, fileAddress, batchDate, isReturnInforFile,flag);
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0,"执行成功");
            }
            catch (HsException e)
            {
                LOG.info("异常，执行失败!!!!");
                SystemLog.error("HSXT_AC", "任务失败！批记账任务:"+batchJobName, e.getMessage(),e);
                // 发送进度通知
                listener.reportStatus(executeId, 1,"任务失败！批记账任务:"+batchJobName+e.getMessage());
                result=false;
            }
            catch (Exception e)
            {
                LOG.info("异常，执行失败!!!!");
                SystemLog.error("HSXT_AC", "任务失败！批记账任务:"+batchJobName, e.getMessage(),e);
                // 发送进度通知
                listener.reportStatus(executeId, 1,"任务失败！批记账任务:"+batchJobName+e.getMessage());
                result=false;
            }
        }
        return result;
    }    
    
    /**
     * 批记账任务
     * 
     * @param fileAddress
     *            记账文件读取路径
     * @throws HsException
     *             异常处理类
     */
    public void batchChargeAccount(String batchJobName, String fileAddress, String batchDate, boolean isReturnInforFile,boolean flag)
            throws HsException {
        SystemLog.info("HSXT_AC", "batchChargeAccount", batchJobName + "begin");
        String encoding = "UTF-8";
        // 日期
        if("".equals(batchDate) || batchDate == null)
        {
        	batchDate = new SimpleDateFormat("yyyyMMdd").format(preYesterday());
        	if("PS/CSC".equals(fileAddress))
            {
                batchDate = new SimpleDateFormat("yyyyMMdd").format(preDayOfMonth()); 
            }
        }
        
        String dirRoot = PropertyConfigurer.getProperty("dir.root");
        fileAddress = dirRoot+File.separator+fileAddress + File.separator + batchDate + File.separator + batchDate + "_CHECK.TXT";
        
        File file = FileUtils.getFile(fileAddress);
        
        // 获取记账任务绝对路径文件夹地址
        String fileParent = file.getParent();
        
        try
        {
            // 当前时间
            Timestamp batchBeginDate = new Timestamp(System.currentTimeMillis());

            // 读取记账稽查文件
            InputStreamReader read = null;
            List<AccountBatchJob> checkFileList = null;
            try
            {
                read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader checkReader = new BufferedReader(read);
                
                // 获取记账稽查文件内容
                checkFileList = checkFileList(checkReader);
                //无记账文件记录，不做处理
                if(checkFileList==null || checkFileList.size() == 0)
                {
                    return;
                }
            }
            catch (UnsupportedEncodingException e)
            {
            	SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
            	throw new HsException(RespCode.AC_IO_ERROR, e.getMessage());
            }
            catch (FileNotFoundException e)
            {
                SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
                //throw new HsException(RespCode.AC_FILE, "文件不存在"+e.getMessage());
                return;// 遇到文件异常不做处理
            }
            finally
            {
                // 关闭稽查文件读取流
                try
                {
                    if(read != null){
                        read.close(); 
                    }
                }
                catch (IOException e)
                {
                    SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
                    throw new HsException(RespCode.AC_IO_ERROR, e.getMessage());
                }
            }

            List<AccountEntry> accBatchErrorList = new ArrayList<AccountEntry>();
            for (int i = 0; i < checkFileList.size(); i++)
            {
                // 批处理子文件信息
                AccountBatchJob accountCheckFile = checkFileList.get(i);

                // 批记账任务名称
                accountCheckFile.setBatchJobName(batchJobName);

                // 批记账任务日期
                accountCheckFile.setBatchDate(batchDate);

                // 任务开始时间
                accountCheckFile.setBatchBeginDate(batchBeginDate);

                // 任务状态
                accountCheckFile.setBatchStatus(1);

                // 任务记录完成数
                accountCheckFile.setBatchAchieveLines(0);

                // 是否返回所有信息文件
                accountCheckFile.setReturnInforFile(isReturnInforFile);

                // 批处理子文件地址
                String batchFileAddress = fileParent + File.separator + accountCheckFile.getBatchFileName();
                accountCheckFile.setBatchFileAddress(batchFileAddress);

                AccountBatchJob accountBatchJob = searchAccountBatchJobByJobNameAndFileName(batchJobName,
                        accountCheckFile.getBatchFileName());

                // 判断是否执行过批处理任务
                if (accountBatchJob == null)
                {
                    // 新增批处理任务记录
                    addBatchJob(accountCheckFile);
                }
                else
                {
                    if (accountBatchJob.getBatchAchieveLines().equals(accountCheckFile.getBatchLines()))
                    {
                        accountCheckFile.setAccount(true);
                        SystemLog.debug("HSXT_AC", "方法：batchChargeAccount", batchJobName+":"+accountCheckFile.getBatchFileName()+"重复执行批记账任务");
                        continue;
                    }
                    else
                    {
                        accountCheckFile.setAccount(false);
                        accountCheckFile.setBatchAchieveLines(accountBatchJob.getBatchAchieveLines());
                        updateBatchJob(accountCheckFile);
                    }
                }

                // 读取记账文件
                File chargeFile = new File(batchFileAddress);
                long fileLength = chargeFile.length();
                LineNumberReader rf = null;

                // 记账文件记录数
                Integer lines = 0;
                try
                {
                    rf = new LineNumberReader(new FileReader(chargeFile));
                    if (rf != null)
                    {
                        rf.skip(fileLength);

                        // 得到记账文件记录数
                        lines = rf.getLineNumber();
                    }
                }
                catch (FileNotFoundException e)
                {
                    SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
                    throw new HsException(RespCode.AC_FILE, "文件不存在"+e.getMessage());
                }
                catch (IOException e)
                {
                    SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
                    throw new HsException(RespCode.AC_IO_ERROR, e.getMessage());
                }
                finally
                {
                    // 关闭记账文件记录数读取流
                    try
                    {
                        rf.close();
                    }
                    catch (IOException e)
                    {
                        SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
                        throw new HsException(RespCode.AC_IO_ERROR, e.getMessage());
                    }
                }

                // 实际文件记录数值与稽查文件记录数值对比
                if (!lines.equals(accountCheckFile.getBatchLines()))
                {
                    AccountEntry accBatchError = new AccountEntry();
                    accBatchError.setBatchJobName(batchJobName);
                    accBatchError.setBatchDate(batchDate);
                    accBatchError.setErrorFileAddress(batchFileAddress);
                    accBatchError.setErrorDescription("记账文件记录数值校验失败！");
                    accBatchErrorList.add(accBatchError);
                }
            }

            // 记账文件校验失败做错误记录并停止批记账任务
            if (accBatchErrorList != null && accBatchErrorList.size() > 0)
            {
                Timestamp batchEndDate = new Timestamp(System.currentTimeMillis());
                for (int i = 0; i < checkFileList.size(); i++)
                {
                    // 获取批记账子文件信息
                    AccountBatchJob accountBatchJob = checkFileList.get(i);
                    accountBatchJob.setBatchStatus(2);
                    accountBatchJob.setBatchEndDate(batchEndDate);

                    // 把批记账任务记录状态为失败与任务结束时间
                    updateBatchJob(accountBatchJob);
                }

                // 新增批记账任务错误记录
                addBatchErrores(accBatchErrorList);
                return;
            }
            int count = checkFileList.size();

            // 记账任务多少记账文件初始化线程池，多少子线程
            ExecutorService exe = Executors.newFixedThreadPool(count);
            for (int i = 0; i < count; i++)
            {
                // 获取批记账子文件信息
                AccountBatchJob accountBatchJob = checkFileList.get(i);

                // 判断是否已记账，已记账就不做批处理记账
                if (!accountBatchJob.isAccount())
                {
                    // 向线程池里注入需要执行的子线程
                    exe.execute(new AccountBatchChargeRunnable(accountBatchJob, accountBatchTransaction,flag));
                }
            }
            exe.shutdown();

            while (true)
            {
                if (exe.isTerminated())
                {
                    // Timestamp batchEndDate = new
                    // Timestamp(System.currentTimeMillis());
                    break;
                }
                Thread.sleep(200);
            }

        }
        catch (InterruptedException e)
        {
            SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
            throw new HsException(RespCode.AC_INTERRUPTED, e.getMessage());
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "批记账任务:batchChargeAccount()", e.getMessage(),e);
            throw e;
        }
        SystemLog.info("HSXT_AC", "batchChargeAccount", batchJobName + "end");
    }

    /**
     * 获取稽查文件数据
     * 
     * @param checkReader
     * @return
     */
    private List<AccountBatchJob> checkFileList(BufferedReader checkReader) throws HsException {
        List<AccountBatchJob> checkList = new ArrayList<AccountBatchJob>();
        try
        {
            String lineTxt = "";
            // 碰到END不在读取
            while (!lineTxt.equals("END"))
            {
                lineTxt = checkReader.readLine();
                if (lineTxt == null || lineTxt.length() == 0)
                {
                    continue;
                }
                if (!lineTxt.equals("END"))
                {
                    String[] checkFile = lineTxt.split("\\|");
                    if (checkFile != null && checkFile.length > 0)
                    {
                        AccountBatchJob accountBatchJob = new AccountBatchJob();
                        // 批记账子文件名称
                        accountBatchJob.setBatchFileName(checkFile[0]);
                        // 批记账子文件记录数
                        accountBatchJob.setBatchLines(Integer.valueOf(checkFile[1]));
                        checkList.add(accountBatchJob);
                    }
                }
            }
        }
        catch (IOException e)
        {
            SystemLog.error("HSXT_AC", "获取稽查文件数据:checkFileList()", e.getMessage(),e);
            throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        finally
        {
            try
            {
                checkReader.close();
            }
            catch (IOException e)
            {
                SystemLog.error("HSXT_AC", "获取稽查文件数据:checkFileList()", e.getMessage(),e);
                throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
            }
        }
        return checkList;
    }

    /**
     * 新增批处理任务记录
     * 
     * @param accountBatchJob
     *            任务记录数据参数
     */
    private void addBatchJob(AccountBatchJob accountBatchJob) throws HsException {
        try
        {
            accountBatchProcesMapper.addBatchJob(accountBatchJob);
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 修改批处理任务记录
     * 
     * @param accountBatchJob
     *            任务记录数据参数
     */
    private void updateBatchJob(AccountBatchJob accountBatchJob) throws HsException {
        try
        {
            accountBatchProcesMapper.updateBatchJob(accountBatchJob);
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

//    /**
//     * 删除批处理任务记录
//     * 
//     * @param accountBatchJob
//     *            任务记录数据参数
//     */
//    private void deleteBatchJob(AccountBatchJob accountBatchJob) throws HsException {
//        try
//        {
//            accountBatchProcesMapper.deleteBatchJob(accountBatchJob);
//        }
//        catch (Exception e)
//        {
//            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
//        }
//    }

    /**
     * 增加批处理任务报错记录
     * 
     * @param accountEntry
     *            出错数据参数
     */
    private void addBatchErrores(List<AccountEntry> accBatchErrorList) throws HsException {
        try
        {
            accountBatchProcesMapper.addBatchErrores(accBatchErrorList);
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

//    /**
//     * 删除批处理任务报错记录
//     * 
//     * @param accountEntry
//     *            出错数据参数
//     */
//    private void deleteBatchError(AccountBatchJob accountBatchJob) throws HsException {
//        try
//        {
//            accountBatchProcesMapper.deleteBatchError(accountBatchJob);
//        }
//        catch (Exception e)
//        {
//            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
//        }
//    }

    /**
     * 根据批处理任务名称与任务文件名称查询批处理任务信息
     * 
     * @param batchJobName
     *            任务名称
     * @param batchFileName
     *            任务文件名称
     * @return 任务信息
     * @throws HsException
     *             数据库异常
     */
    private AccountBatchJob searchAccountBatchJobByJobNameAndFileName(String batchJobName, String batchFileName)
            throws HsException {
        try
        {
            return accountBatchProcesMapper.searchAccountBatchJobByJobNameAndFileName(batchJobName, batchFileName);
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    //获取昨日日期
    public Date preYesterday() {
    	Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        return calendar.getTime();
    }
    
    public Date preDayOfMonth()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.set(Calendar.DAY_OF_MONTH, 1); 
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
}
