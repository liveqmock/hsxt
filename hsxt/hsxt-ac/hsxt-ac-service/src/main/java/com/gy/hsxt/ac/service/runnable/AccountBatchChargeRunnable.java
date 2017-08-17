package com.gy.hsxt.ac.service.runnable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.common.bean.PropertyConfigurer;
import com.gy.hsxt.ac.service.Transaction.AccountBatchTransaction;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 批记账多线程类
 * 
 * @Package: com.gy.hsxt.ac.service.runnable
 * @ClassName: AccountBatchChargeRunnable
 * @Description: 批记账线程
 * 
 * @author: maocan
 * @date: 2015-8-31 上午10:19:22
 * @version V1.0
 */
public class AccountBatchChargeRunnable implements Runnable {

    // 记账任务记录
    private AccountBatchJob accountBatchJob;

    // 批记账Service
    private AccountBatchTransaction accountBatchTransaction;
    
    private boolean flag;

    public AccountBatchChargeRunnable(AccountBatchJob accountBatchJob, AccountBatchTransaction accountBatchTransaction,boolean flag) {
        this.accountBatchJob = accountBatchJob;
        this.accountBatchTransaction = accountBatchTransaction;
        this.flag = flag;
    }

    public void run() {
        try
        {
            //批记账
            addAccount();
        }
        catch (HsException e)
        {
            SystemLog.debug("HSXT_AC", "批记账:addAccount()", e.getMessage());
        }
    }

    /**
     * 批记账
     */
    public void addAccount() throws HsException {
        String encoding = "UTF-8";
        File file = new File(accountBatchJob.getBatchFileAddress());
        
        // 读取记账数据文件
        InputStreamReader read = null;
        BufferedReader chargeReader = null;
        try
        {
            read = new InputStreamReader(new FileInputStream(file), encoding);
            chargeReader = new BufferedReader(read);
            
            //记账过的文件行排除记账
            for (int y = 0; y < accountBatchJob.getBatchAchieveLines(); y++)
            {
                chargeReader.readLine();
            }
            
            // 每次提交数据量
            int batchPage = Integer.valueOf(PropertyConfigurer.getProperty("ac.accountCharge.batchPage"));
            
            // 文件剩余数据条数
            int lines = accountBatchJob.getBatchLines() - accountBatchJob.getBatchAchieveLines();
            
            // 循环次数=文件剩余数据条数/每次提交数据量
            int count = lines / batchPage;
            
            // 文件剩余数据条数%每次提交数据量的余数
            int remainder = lines % batchPage;
            
            // 如有余数循环次数+1
            if (remainder > 0)
            {
                count++;
            }
            String fileAdder = "";
            
            // 获取一行记账数据
            String lineTxt = "";
            
            for (int i = 0; i < count; i++)
            {
            	
            	// 读取到END就结束
                if (lineTxt.equals("END"))
                { 
                    break;
                }
            	
                //文件读取可以记账数据List
                List<String> lineTxtList = new ArrayList<String>();
                
                //文件读取校验失败无法记账数据List
                List<String> lineTxtExceList = new ArrayList<String>();
                
                // 分录记账List
                List<AccountEntry> accountEntryList = new ArrayList<AccountEntry>();
                
                // 账户余额快照List
                List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();
                
                // 记账校验未通过信息
                List<AccountEntry> accountBatchError = new ArrayList<AccountEntry>();
                
                // 账户余额变动Map
                Map<String, AccountBalance> accountBalanceMap = new HashMap<String, AccountBalance>();
                
                // 账户余额查询信息
                Map<String, AccountBalance> accBalanceAll =  new HashMap<String, AccountBalance>();
                
                // 账户类型与客户类型关系Map
                Map<String, AccountCustType> custAccTypeMap = new HashMap<String, AccountCustType>();
                
                // 校验失败单号，与失败信息
                Map<String, HsException> hsExceptionMap = new HashMap<String, HsException>();
                
                // 客户ID对应的单号
                Map<String, String> custIdBytransNo = new HashMap<String, String>();
                
                // 返回的文件内容
                StringBuilder writeFileData = new StringBuilder();

                for (int y = 0; y < batchPage; y++)
                {
                    // 获取一行记账数据
                    lineTxt = chargeReader.readLine();
                    
                    // 读取到END就结束
                    if (lineTxt.equals("END"))
                    { 
                        break;
                    }
                    
                    // 记账行数据解析成记账实体类
                    AccountEntry accountEntry = accountBatchTransaction.getAccountEntry(lineTxt, accountBatchJob);

                    custIdBytransNo.put(accountEntry.getCustID(), accountEntry.getTransNo());
                    
                    // 获取异常信息
                    HsException hsException = hsExceptionMap.get(accountEntry.getTransNo());
                    
                    // 校验记账数据
                    Map<String, Object> errorMap = new HashMap<String, Object>();
                    
                    if(hsException == null)
                    {
                    	errorMap = accountBatchTransaction.checkAccountEntry(accountEntry,
	                            accountBalanceMap, accBalanceAll, custAccTypeMap,flag);
	                    
                    	hsException = (HsException) errorMap.get("ERROR");
                    }
                    
                     
                    
                    
                    
                    // 判断数据校验是否有异常
                    if (hsException != null)
                    {
                        // 数据库异常
                        if (hsException.getErrorCode() == RespCode.AC_SQL_ERROR.getCode())
                        {
                            throw hsException;
                        }
                        else
                        {
                            // 校验异常记录到批处理任务报错表
                            accountBatchError.add(accountBatchTransaction.getBatchError(accountBatchJob,
                                    accountEntry, hsException));
                            // 错误信息
                            lineTxtExceList.add(lineTxt + "|" + hsException.getMessage() + "|0");
                            hsExceptionMap.put(accountEntry.getTransNo(), hsException);
                        }
                        continue;
                    }

                    // 新增余额快照信息
                    AccountBalanceSnap accountBalanceSnap = (AccountBalanceSnap) errorMap.get("accountBalanceSnap");
                    
                    // 余额快照List
                    accountBalanceSnapList.add(accountBalanceSnap);
                    
                    // 记账信息List
                    accountEntryList.add(accountEntry);
                    
                    //判断是否是否返回所有信息文件
//                    if (accountBatchJob.isReturnInforFile())
//                    {
                    	lineTxtList.add(lineTxt);
//                    }
                }
                
                //初始化重调数据库次数
                int size = 0;
                
                // 记录每次插入数=记账信息数+记账校验未通过数
                Integer batchAchieveLines = accountBatchJob.getBatchAchieveLines() + accountEntryList.size()
                        + accountBatchError.size();
                
                // 更新批任务插入数
                accountBatchJob.setBatchAchieveLines(batchAchieveLines);
                
                // 记账数据插入更新处理
                writeFileData = addAccountEntry(accountBatchJob, accountEntryList, accountBatchError, accountBalanceSnapList,
                        accountBalanceMap, size, custIdBytransNo, lineTxtList, lineTxtExceList);
                
                //判断是否有写文件信息
                if (writeFileData != null && writeFileData.length() > 0)
                {
                    File directory = new File(accountBatchJob.getBatchFileAddress());
                    //获取文件地址
                    fileAdder = directory.getParent();
                    String fileName = "";
                    if (accountBatchJob.isReturnInforFile())
                    {
                        //回写所以的文件名
                        fileName = directory.getName().split("\\.")[0] + "_RETURN.TXT";
                    }
                    else
                    {
                        //只回写错误的文件名
                        fileName = directory.getName().split("\\.")[0]+"_ERROR.TXT";
                    }
                    fileAdder = fileAdder + File.separator + fileName;
                    writeFile(writeFileData, fileAdder);
                }
            }
            if(fileAdder.length() > 0)
            {
	            StringBuilder writeFileData = new StringBuilder();
	            writeFileData.append("END");
	            writeFile(writeFileData,fileAdder);
            }
            Timestamp batchEndDate = new Timestamp(System.currentTimeMillis());
            accountBatchJob.setBatchStatus(0);
            accountBatchJob.setBatchEndDate(batchEndDate);
            accountBatchTransaction.updateBatchJob(accountBatchJob);
        }
        catch (HsException e)
        {
            if (e.getErrorCode() == RespCode.AC_SQL_ERROR.getCode())
            {
                Timestamp batchEndDate = new Timestamp(System.currentTimeMillis());
                accountBatchJob.setBatchStatus(2);
                accountBatchJob.setBatchEndDate(batchEndDate);
                accountBatchTransaction.updateBatchJob(accountBatchJob);
                AccountEntry errorEntry = new AccountEntry();
                errorEntry = accountBatchTransaction.getBatchError(accountBatchJob, errorEntry, e);
                try
                {
                    accountBatchTransaction.addBatchError(errorEntry);
                }
                catch (SQLException e1)
                {
                    throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e1.getMessage());
                }
            }
        }
        catch (UnsupportedEncodingException e)
        {
            throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        catch (FileNotFoundException e)
        {
            throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        catch (IOException e)
        {
            throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
        	throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
        }
        finally
        {
           
            try
            {
                // 关闭读取
                chargeReader.close();
                // 关闭流
                read.close();
            }
            catch (IOException e)
            {
                throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
            }
            
        }
    }

    /**
     * 批量记账到数据库
     * @param accountBatchJob 
     * @param accountEntryList
     * @param accountBalanceSnapList
     * @param accountBalanceMap
     * @param size 当前SQL异常错误重试次数
     * @throws HsException
     */
    public StringBuilder addAccountEntry(AccountBatchJob accountBatchJob, List<AccountEntry> accountEntryList,
            List<AccountEntry> accountBatchError, List<AccountBalanceSnap> accountBalanceSnapList,
            Map<String, AccountBalance> accountBalanceMap, int size, Map<String, String> custIdBytransNo,
            List<String> lineTxtList, List<String> lineExceTxtList) throws HsException {
        //SQL异常错误重试次数
        int batchSQLErrorSize = Integer.valueOf(PropertyConfigurer.getProperty("ac.accountCharge.batchSQLErrorSize"));
        
        //SQL异常错误重试间隔时间
        int batchSQLErrorTime = Integer.valueOf(PropertyConfigurer.getProperty("ac.accountCharge.batchSQLErrorTime"));
        
        StringBuilder writeFileData = new StringBuilder();
        
        try
        {
            //记账数据插入或更新
        	writeFileData = accountBatchTransaction.addAccountEntry(accountBatchJob, accountEntryList, accountBatchError,
                    accountBalanceSnapList, accountBalanceMap, custIdBytransNo, lineTxtList, lineExceTxtList);

        }
        catch (Exception e)
        {
            //判断是否达到最大重试次数
            if (size < batchSQLErrorSize)
            {
                try
                {
                    Thread.sleep(batchSQLErrorTime);
                }
                catch (InterruptedException e1)
                {
                    throw new HsException(RespCode.AC_INTERRUPTED.getCode(), e1.getMessage());
                }
                
                //当前SQL异常错误重试次数累加
                size++;
                
                //重做批量记账到数据库
                writeFileData = addAccountEntry(accountBatchJob, accountEntryList, accountBatchError, accountBalanceSnapList,
                        accountBalanceMap, size, custIdBytransNo, lineTxtList, lineExceTxtList);
            }
            else
            {
                //达到最大重试次数，抛出异常
                Integer batchAchieveLines = accountBatchJob.getBatchAchieveLines() - accountEntryList.size();
                accountBatchJob.setBatchAchieveLines(batchAchieveLines);
                throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
            }
        }
        return writeFileData;
    }

    /**
     * 批处理信息文件回写
     * @param writeFileData 回写文件信息
     * @param fileAdder 回写文件地址
     * @throws HsException
     */
    public void writeFile(StringBuilder writeFileData, String fileAdder) throws HsException {
        if (writeFileData != null && writeFileData.length() > 0)
        {
        	OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try
            {
            	osw = new OutputStreamWriter(new FileOutputStream(fileAdder, true),"UTF-8");
                bw = new BufferedWriter(osw);
                bw.write(writeFileData.toString());
                bw.flush();
            }
            catch (IOException e)
            {
                throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
            }
            finally
            {
                try
                {
                	osw.close();
                    bw.close();
                }
                catch (IOException e)
                {
                    throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
                }
            }
        }
    }

}
