package com.gy.hsxt.rp.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.gpf.fss.api.IfssNotifyService;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.DirPathBuilder;
import com.gy.hsxt.gpf.fss.utils.FileNameBuilder;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.gpf.fss.utils.MD5;
import com.gy.hsxt.gpf.fss.utils.NotifyNoUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.rp.api.IReportEntGiveIntegralMonthFileService;
import com.gy.hsxt.rp.bean.ReportAccountBatchJob;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportAccountEntGiveIntegrel;
import com.gy.hsxt.rp.common.bean.RpTools;
import com.gy.hsxt.rp.mapper.ReportAccountEntryBatchMapper;
import com.gy.hsxt.rp.service.runnable.ReportAccountIntegralMonthFileRunnable;

/**
 * 批生成企业月送积分列表Service
 * 
 * @Package: com.gy.hsxt.rp.service
 * @ClassName: AccountBatchProcesService
 * @Description: TODO
 * 
 * @author: maocan
 * @date: 2015-8-31 上午10:19:22
 * @version V1.0
 */
@Service
public class AccountBatchEntGiveIntegralMonthFileService implements IDSBatchService, IReportEntGiveIntegralMonthFileService {

    /**
     * 记账数据库操作Mapper
     */
    @Autowired
    private ReportAccountEntryBatchMapper accountEntryBatchMapper;
    
    /** 地区平台配送Service **/
	@Autowired
	private ILCSBaseDataService baseDataService;
	
	/**
     * 文件同步系统接口
     */
    @Resource
    private IfssNotifyService fssNotifyService;  


    protected static final Logger LOG = LoggerFactory.getLogger(AccountBatchEntGiveIntegralMonthFileService.class);
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
            	entGiveIntegralMonthFile(batchJobName, fileAddress,batchDate);
                LOG.info("执行成功!!!!");
                // 发送进度通知
                listener.reportStatus(executeId, 0,"执行成功");
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
     * 批生成企业月送积分列表
     * 
     * @param batchJobName
     *            记账任务名称
     * @param fileAddress
     *            批生成企业月送积分资源列表文件生成路径
     * @throws HsException
     * 
     */
    @Override
    public void entGiveIntegralMonthFile(String batchJobName, String fileAddress,String batchDate) throws HsException {
        Map<String, ReportAccountEntGiveIntegrel> accEntGiveIntegrelMap = new HashMap<String, ReportAccountEntGiveIntegrel>();
        
        // 月最后一天
        String newDate = "";
        // 月初
        Timestamp monthStartDate = null;
        // 月末
        Timestamp monthEndDate = null;
        
        if(StringUtils.isNotBlank(batchDate)){
            //参数指定的日期
            Date date = DateUtil.StringToDate(batchDate);
            if(StringUtils.isBlank(date)){
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "日期格式错误，正确格式为:yyyy-MM-dd"); 
            }
            newDate = DateUtil.DateToString(RpTools.getMonthEnd(date), DateUtil.DATE_FORMAT);// 指定时间月份的最后一天时间
            monthStartDate = RpTools.getMonthStartForTim(date);// 指定月初
            monthEndDate = RpTools.getMonthEndForTim(date);// 指定月末
        }else{
            newDate = DateUtil.DateToString(RpTools.preMonthLastDay(), DateUtil.DATE_FORMAT);// 上月最后一天
            monthStartDate = RpTools.preMonthFirstDayDate();// 上月初
            monthEndDate = RpTools.preMonthLastDayDate();// 上月末
        }
        
        String sysName = PropertyConfigurer.getProperty("system.id");
        String dirRoot = PropertyConfigurer.getProperty("dir.root");
        LocalInfo localInfo = getLocalInfo();
        LocalNotify localNotify = new LocalNotify();
        localNotify.setFileCount(1);
        localNotify.setFromPlat(localInfo.getPlatNo());// 本平台代码
        localNotify.setFromSys(sysName);// 本系统代码
        localNotify.setToPlat(GlobalConstant.CENTER_PLAT_NO);// 总平台代码
        localNotify.setToSys("BM");// 增值系统
        localNotify.setPurpose(FssPurpose.BM_BMLM.getCode());
        localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
        localNotify.setNotifyType(FssNotifyType.MD5.getTypeNo());
        try {
        	localNotify.setNotifyTime(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(newDate)));
		} catch (ParseException e) {
			SystemLog.error("HSXT_RP", "批生成企业月送积分文件任务:entGiveIntegralMonthFile()", e.getMessage(), e);
        	throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), e.getMessage());
		}
        if(fileAddress == null || fileAddress.length() ==0)
    	{
    		fileAddress = dirRoot + DirPathBuilder.uploadDir(sysName, newDate, FssPurpose.BM_BMLM, localNotify.getToPlat());
    	}
        File file = new File(fileAddress);
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
        String fileName = FileNameBuilder.build(FssPurpose.BM_BMLM, newDate, 1, sysName);
        ReportAccountEntGiveIntegrel accountEntGiveIntegrel = new ReportAccountEntGiveIntegrel();
        accountEntGiveIntegrel.setFilePath(fileAddress);
        accountEntGiveIntegrel.setFileName(fileName);
        accountEntGiveIntegrel.setFileNum(1);
        accountEntGiveIntegrel.setNewDate(newDate);
        accountEntGiveIntegrel.setSysName(sysName);
        accEntGiveIntegrelMap.put("EGIM", accountEntGiveIntegrel);
        // 查询对账文件数据时间段条件
        Map<String, Object> accountEntryMap = new HashMap<String, Object>();
        
        String[] transTypes = PropertyConfigurer.getProperty("rp.accountEntGiveIntegrel.transTypes").split("\\|");
        String transType = "";
    	for(int i=0;i<transTypes.length;i++){
    		transType = transType+"'"+transTypes[i]+"'";
    		 if(i != transTypes.length - 1)
    		 {
    			 transType = transType + ", ";
    		 }
    	}
        
        // 开始时间
        accountEntryMap.put("beginDate", monthStartDate);
        // 结束时间
        accountEntryMap.put("endDate", monthEndDate);
        // 交易类型
        accountEntryMap.put("transType", transType);
        
        try
        {
            // 查询当前时间区间的记账分录数量
            int size = accountEntryBatchMapper.seachAccEntryCountByTransType(accountEntryMap);
            if(size == 0){
                return;
            }
            int row =  Integer.valueOf(PropertyConfigurer.getProperty("rp.accountCheck.sumRow"));
            
            
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
                ReportAccountBatchJob accountBatchJob = new ReportAccountBatchJob();
                exe.execute(new ReportAccountIntegralMonthFileRunnable(accountBatchJob, accEntGiveIntegrelMap, accountEntryBatchMapper, accountEntryMap, i,remainder));
            }
            exe.shutdown();
            while (true)
            {
                if (exe.isTerminated())
                {
                	ReportAccountEntGiveIntegrel accountEntGiveIntegrel_ = accEntGiveIntegrelMap.get("EGIM");
                    int fileNum = accountEntGiveIntegrel_.getFileNum();
                    List<FileDetail> details = new ArrayList<>();
                    for (int y = 1; y <= fileNum; y++)
                    {
                    	FileDetail detail = new FileDetail();
                        detail.setStartTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
                        String fileName_ = FileNameBuilder.build(FssPurpose.BM_BMLM, newDate, y, sysName);
                        File file_ = new File(accountEntGiveIntegrel_.getFilePath() + fileName_);
                        // 配置文件详情
                        detail.setEndTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));
                        detail.setPercent(1);
                        detail.setSource(accountEntGiveIntegrel_.getFilePath() + fileName_);
                        detail.setTarget(accountEntGiveIntegrel_.getFilePath() + fileName_);
                        detail.setIsLocal(1);
                        detail.setFileSize(file_.length());
                        detail.setFileName(file_.getName());
                        detail.setCode(MD5.toMD5code(file_));
                        detail.setUnit("B");
                        detail.setNotifyNo(localNotify.getNotifyNo());

                        details.add(detail);
                    }
                    localNotify.setDetails(details);

                    // 向文件同步系统发送通知
                    boolean success = fssNotifyService.localSyncNotify(localNotify);
                    //BizLog.biz(sysName, "entGiveIntegralMonthFile", "向文件同步系统发送通知==>" + success, "批生成企业月送积分列表");
                    break;
                }
                Thread.sleep(200);
            }

        }
        catch (InterruptedException e)
        {
        	SystemLog.error("HSXT_RP", "批生成企业月送积分文件任务:entGiveIntegralMonthFile()", e.getMessage(), e);
        	throw new HsException(RespCode.AC_INTERRUPTED.getCode(), e.getMessage());
        }
        catch (HsException e)
        {
        	SystemLog.error("HSXT_RP", "批生成企业月送积分文件任务:entGiveIntegralMonthFile()", e.getMessage(), e);
        	throw e;
        }
        catch (Exception e)
        {
        	SystemLog.error("HSXT_RP", "批生成企业月送积分文件任务:entGiveIntegralMonthFile()", e.getMessage(), e);
        	throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
	public LocalInfo getLocalInfo() {
		LocalInfo localInfo = baseDataService.getLocalInfo();
		return localInfo;
	}
}
