/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

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
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.rp.api.IReportsInvoiceValueStatisticsBatchService;
import com.gy.hsxt.rp.common.bean.PropertyConfigurer;
import com.gy.hsxt.rp.common.bean.ReportsInvoiceValueStatisticsExportFile;
import com.gy.hsxt.rp.mapper.ReportsInvoiceValueStatisticsMapper;
import com.gy.hsxt.rp.service.runnable.ReportsInvoiceValueStatisticsRunnable;

/**
 * 发票金额统计每日统计
 * 
 * @Package: com.gy.hsxt.rp.service
 * @ClassName: ReportsInvoiceValueStatisticsBatchService
 * @Description: TODO
 * 
 * @author: maocan
 * @date: 2016-1-6 上午10:51:26
 * @version V1.0
 */

@Service
public class ReportsInvoiceValueStatisticsBatchService implements
		IDSBatchService, IReportsInvoiceValueStatisticsBatchService {

	/** 地区平台配送Service **/
	@Autowired
	private ILCSBaseDataService baseDataService;

	/** 发票金额统计查询 **/
	@Autowired
	private ReportsInvoiceValueStatisticsMapper reportsInvoiceValueStatisticsMapper;

	protected static final Logger LOG = LoggerFactory
			.getLogger(ReportsInvoiceValueStatisticsBatchService.class);
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
				invoiceValueStatistics(batchJobName, fileAddress, batchDate);
				LOG.info("执行成功!!!!");
				// 发送进度通知
				listener.reportStatus(executeId, 0, "执行成功");
			} catch (Exception e) {
				LOG.info("异常，执行失败!!!!");
				// 发送进度通知
				listener.reportStatus(executeId, 1, "执行失败");
				result = false;
			}
		}
		return result;
	}

	/**
	 * 发票金额统计每日统计
	 * 
	 * @param batchJobName
	 * @param fileAddress
	 * @throws HsException
	 */
	@Override
	public void invoiceValueStatistics(String batchJobName, String fileAddress,
			String batchDate) throws HsException {
		Map<String, ReportsInvoiceValueStatisticsExportFile> reportsExportFileMap = new HashMap<String, ReportsInvoiceValueStatisticsExportFile>();
		String newDate = new SimpleDateFormat("yyyyMMdd")
				.format(preYesterday());
		if (batchDate != null && batchDate.length() > 0) {
			try {
				newDate = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
			} catch (ParseException e) {
				e.getMessage();
			}
		}

		if (fileAddress == null || fileAddress.length() == 0) {
			String dirRoot = PropertyConfigurer.getProperty("dir.root");
			fileAddress = dirRoot + File.separator + "RP" + File.separator
					+ "IVS" + File.separator;
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

		String fileName = newDate + "_1.TXT";
		ReportsInvoiceValueStatisticsExportFile reportsExportFile = new ReportsInvoiceValueStatisticsExportFile();
		reportsExportFile.setFilePath(fileAddress);
		reportsExportFile.setFileName(fileName);
		reportsExportFile.setFileNum(1);
		reportsExportFile.setNewDate(newDate);
		reportsExportFileMap.put("IVS_BS", reportsExportFile);
		// 查询对账文件数据时间段条件
		Map<String, Object> welfareScoreMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterdayDate = sdf.format(preYesterday());
		if (batchDate != null && batchDate.length() > 0) {
			try {
				yesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(batchDate));
			} catch (ParseException e) {
				e.getMessage();
			}
		}

		// 昨天开始时间
		String beginDate = yesterdayDate + " 00:00:00";
		Timestamp beginDateTim = Timestamp.valueOf(beginDate);

		// 昨天结束时间
		String endDate = yesterdayDate + " 23:59:59";
		Timestamp endDateTim = Timestamp.valueOf(endDate);

		// 汇率
		String exchangeRate = getLocalInfo().getExchangeRate();

		// 开始时间
		welfareScoreMap.put("beginDate", beginDateTim);
		// 结束时间
		welfareScoreMap.put("endDate", endDateTim);
		// 汇率
		welfareScoreMap.put("exchangeRate", exchangeRate);

		try {
			// 查询当前时间区间的记账分录数量
			int size = reportsInvoiceValueStatisticsMapper
					.searchEntInvoiceValueStatisticsCount(welfareScoreMap);
			if (size == 0) {
				return;
			}
			int row = Integer.valueOf(PropertyConfigurer
					.getProperty("rp.reportsCheck.sumRow"));
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
				exe.execute(new ReportsInvoiceValueStatisticsRunnable(
						reportsExportFileMap,
						reportsInvoiceValueStatisticsMapper, welfareScoreMap, i, remainder));
			}
			exe.shutdown();
			while (true) {
				if (exe.isTerminated()) {
					reportsExportFile = reportsExportFileMap.get("IVS_BS");
					int fileNum = reportsExportFile.getFileNum();
					String fileCHKName = newDate + "_CHECK.TXT";
					String checkFileAdder = fileAddress + newDate
							+ File.separator + fileCHKName;
					StringBuilder writeFileData = new StringBuilder();
					for (int y = 1; y <= fileNum; y++) {
						String fileName_ = newDate + "_" + y + ".TXT";
						String batchFileAddress = fileAddress + newDate
								+ File.separator + fileName_;
						// 读取文件
						File chargeFile = new File(batchFileAddress);
						if (chargeFile.exists()) {
							long fileLength = chargeFile.length();
							LineNumberReader rf = new LineNumberReader(
									new FileReader(chargeFile));
							// 文件记录数
							Integer lines = 0;
							if (rf != null) {
								rf.skip(fileLength);
								// 得到文件记录数
								lines = rf.getLineNumber();
								// 关闭文件记录数读取流
								rf.close();
							}
							FileWriter fw = new FileWriter(batchFileAddress,
									true);
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

		} catch (IOException e) {
			e.getMessage();
		} catch (InterruptedException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public LocalInfo getLocalInfo() {
		LocalInfo localInfo = baseDataService.getLocalInfo();
		return localInfo;
	}

	// 获取昨日日期
	public Date preYesterday() {
		Calendar calendar = Calendar.getInstance();// 获取日历
		calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		return calendar.getTime();
	}
}
