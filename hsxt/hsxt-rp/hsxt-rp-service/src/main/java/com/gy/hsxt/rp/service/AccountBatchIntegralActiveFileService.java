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
import com.gy.hsxt.rp.api.IReportIntegralFileService;
import com.gy.hsxt.rp.bean.ReportAccountBatchJob;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportAccountIntegralActive;
import com.gy.hsxt.rp.mapper.ReportAccountEntryBatchMapper;
import com.gy.hsxt.rp.service.runnable.ReportAccountIntegralFileRunnable;

/**
 * 批生成日积分活动资源列表Service
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
public class AccountBatchIntegralActiveFileService implements IDSBatchService,IReportIntegralFileService {

	/**
	 * 记账数据库操作Mapper
	 */
	@Autowired
	private ReportAccountEntryBatchMapper accountEntryBatchMapper;

	protected static final Logger LOG = LoggerFactory
			.getLogger(AccountBatchIntegralActiveFileService.class);
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
				String batchJobName = args.get("batchJobName");
				String fileAddress = args.get("fileAddress");
				String batchDate = args.get("batchDate");
				// 批记账任务
				integralActiveFile(batchJobName, fileAddress, batchDate);
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
	 * 批生成日积分活动资源列表
	 * 
	 * @param batchJobName
	 *            记账任务名称
	 * @param fileAddress
	 *            日积分活动资源列表文件生成路径
	 * @throws HsException
	 * 
	 */
	@Override
	public void integralActiveFile(String batchJobName, String fileAddress,
			String batchDate) throws HsException {
		SystemLog.info("HSXT_RP", "integralActiveFile", "批生成日积分活动资源列表begin");
		Map<String, ReportAccountIntegralActive> accIntegralActiveMap = new HashMap<String, ReportAccountIntegralActive>();
		// 日期为文件夹名称
		String newDate = batchDate;
		if ("".equals(newDate) || newDate == null) {
			newDate = new SimpleDateFormat("yyyyMMdd").format(preYesterday());
		}
		if (fileAddress == null || fileAddress.length() == 0) {
			String dirRoot = PropertyConfigurer.getProperty("dir.root");
			fileAddress = dirRoot + File.separator + "AC" + File.separator
					+ "INTEGRALACTIVE" + File.separator;
		}
		File file = new File(fileAddress + newDate);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		} else {
			if (file.listFiles().length == 0) {
				file.delete();
			} else {
				File[] ff = file.listFiles();
				for (int i = 0; i < ff.length; i++) {
					ff[i].delete();
				}
			}
		}
		String fileName = newDate + "_DET_1.TXT";
		ReportAccountIntegralActive accountIntegralActive = new ReportAccountIntegralActive();
		accountIntegralActive.setFilePath(fileAddress);
		accountIntegralActive.setFileName(fileName);
		accountIntegralActive.setFileNum(1);
		accountIntegralActive.setNewDate(newDate);
		accIntegralActiveMap.put("RP", accountIntegralActive);
		// 查询对账文件数据时间段条件
		Map<String, Object> accountEntryMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterdayDate = sdf.format(preYesterday());
		if (batchDate != null && batchDate.length() > 0) {
			try {
				yesterdayDate = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("yyyyMMdd")
								.parse(batchDate));
			} catch (ParseException e) {
				SystemLog.error("HSXT_RP", "生成对账文件任务:createAccountCheckFile()",
						e.getMessage(), e);
				throw new HsException(
						RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(),
						e.getMessage());
			}
		}

		// 昨天开始时间
		String beginDate = yesterdayDate + " 00:00:00";
		Timestamp beginDateTim = Timestamp.valueOf(beginDate);

		// 昨天结束时间
		String endDate = yesterdayDate + " 23:59:59";
		Timestamp endDateTim = Timestamp.valueOf(endDate);

		String[] transTypes = PropertyConfigurer.getProperty(
				"rp.accountEntGiveIntegrel.transTypes").split("\\|");

		String transType = "";
		for (int i = 0; i < transTypes.length; i++) {
			transType = transType + "'" + transTypes[i] + "'";
			if (i != transTypes.length - 1) {
				transType = transType + ", ";
			}
		}

		// 开始时间
		accountEntryMap.put("beginDate", beginDateTim);
		// 结束时间
		accountEntryMap.put("endDate", endDateTim);
		// 托管企业成员企业不给积分交易类型
		accountEntryMap.put("transType", transType);

		try {
			// 查询当前时间区间的记账分录数量
			int size = accountEntryBatchMapper
					.seachAccEntryCountByFisDate(accountEntryMap);
			if (size == 0) {
				String fileCHKName = newDate + "_CHK.TXT";
				String checkFileAdder = fileAddress + newDate + File.separator
						+ fileCHKName;
				StringBuilder writeFileData = new StringBuilder();
				FileWriter fw = new FileWriter(checkFileAdder, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(writeFileData.toString());
				bw.write("END");
				bw.flush();
				fw.close();
				bw.close();
				return;
			}
			int row = Integer.valueOf(PropertyConfigurer
					.getProperty("rp.accountCheck.sumRow"));
			int count = size / row;
			int num = size % row;
            int remainder = row;
            if (num > 0)
            {
                count++;
            }
			// 每十万数据启动一个线程，做文件生成
			ExecutorService exe = Executors.newFixedThreadPool(count);
			for (int i = 0; i < count; i++) {
			    if(num > 0 && i == count - 1){
                    remainder = size % row;
                }
			    ReportAccountBatchJob accountBatchJob = new ReportAccountBatchJob();
				exe.execute(new ReportAccountIntegralFileRunnable(accountBatchJob,
						accIntegralActiveMap, accountEntryBatchMapper,
						accountEntryMap, i,remainder));
			}
			exe.shutdown();
			while (true) {
				if (exe.isTerminated()) {
					ReportAccountIntegralActive accountIntegralActive_ = accIntegralActiveMap
							.get("RP");
					int fileNum = accountIntegralActive_.getFileNum();
					String fileCHKName = newDate + "_CHK.TXT";
					String checkFileAdder = fileAddress + newDate
							+ File.separator + fileCHKName;
					StringBuilder writeFileData = new StringBuilder();
					for (int y = 1; y <= fileNum; y++) {
						String fileName_ = newDate + "_DET_" + y + ".TXT";
						String batchFileAddress = fileAddress + newDate
								+ File.separator + fileName_;
						// 读取记账文件
						File chargeFile = new File(batchFileAddress);
						if (chargeFile.exists()) {
							long fileLength = chargeFile.length();
							LineNumberReader rf = new LineNumberReader(
									new FileReader(chargeFile));
							// 记账文件记录数
							Integer lines = 0;
							if (rf != null) {
								rf.skip(fileLength);
								// 得到记账文件记录数
								lines = rf.getLineNumber();
								// 关闭记账文件记录数读取流
								rf.close();
							}
							FileWriter fw = new FileWriter(batchFileAddress,
									true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write("END");
							bw.flush();
							fw.close();
							bw.close();
							writeFileData.append(fileName + "|" + lines);
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

		} catch (IOException e) {
			SystemLog.error("HSXT_RP", "批生成日积分活动资源列表:integralActiveFile()",
					e.getMessage(), e);
			throw new HsException(RespCode.AC_IO_ERROR.getCode(),
					e.getMessage());
		} catch (InterruptedException e) {
			SystemLog.error("HSXT_RP", "批生成日积分活动资源列表:integralActiveFile()",
					e.getMessage(), e);
			throw new HsException(RespCode.AC_INTERRUPTED.getCode(),
					e.getMessage());
		} catch (Exception e) {
			SystemLog.error("HSXT_RP", "批生成日积分活动资源列表:integralActiveFile()",
					e.getMessage(), e);
			throw new HsException(RespCode.AC_SQL_ERROR.getCode(),
					e.getMessage());
		}
	}

	// 获取昨日日期
	public Date preYesterday() {
		Calendar calendar = Calendar.getInstance();// 获取日历
		calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		return calendar.getTime();
	}

	// 获取上个月第一天的日期
	public Timestamp preMonthFirstDayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		String monthStart = String.valueOf(sdf.format(calendar.getTime()));
		Timestamp monthStartDate = Timestamp.valueOf(monthStart + " 00:00:00");
		return monthStartDate;
	}

	// 获取上个月最后一天的日期
	public Timestamp preMonthLastDayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String monthEnd = String.valueOf(sdf.format(calendar.getTime()));
		Timestamp monthEndDate = Timestamp.valueOf(monthEnd + " 23:59:59");
		return monthEndDate;
	}
}
