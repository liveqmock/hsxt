/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.batch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.uc.batch.mapper.BatchMapper;
import com.gy.hsxt.uc.batch.runnable.PvDateUpdateRunnable;

/**
 * 定时调度-更新企业及持卡人积分时间.计划每日执行一次。 定时任务启动后读取账务系统提供的积分变动数据文件， 多线程读取数据文件，每个文件对应一个线程；
 * 根据数据内容变更对应用户的积分时间及会员状态
 * 
 * @Package: com.gy.hsxt.uc.batch.service
 * @ClassName: PvDateUpdateBatchService
 * @Description: 更新企业及持卡人积分时间
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
@Service
public class PvDateUpdateBatchService implements IDSBatchService {

	@Autowired
	BatchMapper batchMapper;
	@Autowired
	private IDSBatchServiceListener listener;

	public void addListener(IDSBatchServiceListener listener) {
		this.listener = listener;
	}

	String executeId;

	private void reportStatus(int status, String desc) {
		logInfo(executeId+" report status to scheduler center ,status: ----" + status
				+ " desc:--" + desc);
		if(listener!=null){
			listener.reportStatus(executeId,status, desc);
		}else{
			logInfo(executeId+" listener==null " );
		}
	}

	/**
	 * 
	 * @param args
	 *            need keys:checkFileAddress=待处理文件地址
	 * @return
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	public boolean excuteBatch(String executeId, Map<String, String> args) {
		// 发送进度通知
		reportStatus(DSTaskStatus.HANDLING, "任务开始");
		this.executeId = executeId;
		String checkFileAddr = getCheckFileAddr(args);
		if (checkFileAddr == null) {
			return false;
		}
		logInfo("checkFilePath:---------" + checkFileAddr);

		ArrayList<String[]> dataFileList = null;
		try {
			dataFileList = getDataFileListFromCheckFile(checkFileAddr);
			if (dataFileList.isEmpty()) {
				reportStatus(DSTaskStatus.SUCCESS, "无数据文件");
				return true;
			}
		} catch (FileNotFoundException e) {
			logError("执行失败,文件不存在!", e);
			reportStatus(DSTaskStatus.FAILED, "执行失败,文件不存在!" + e.getMessage());
			return false;
		} catch (Exception e) {
			logError("执行失败!", e);
			reportStatus(DSTaskStatus.FAILED, "执行失败:" + e.getMessage());
			return false;
		}

		reportStatus(DSTaskStatus.HANDLING, "CHK文件处理完成，开始处理数据文件");
		readDataFileAndUpdateDb(dataFileList);
		reportStatus(DSTaskStatus.SUCCESS, "执行成功");
		return true;
	}

	private String getCheckFileAddr(Map<String, String> args) {
		String dirCfgName = "uc.batch.pv.checkFileDir";
		String fileCfgName = "uc.batch.pv.checkFileName";
		String checkFileAddr = null;
		String taskDate = null;
		String checkFileDir = null;
		String checkFileName = null;
		if (args != null) {
			checkFileAddr = args.get("checkFileAddress");
			if (checkFileAddr != null) {// 使用传入的文件地址
				return checkFileAddr;
			}
			taskDate = args.get("YYYYMMDD");// 使用传入任务日期
			checkFileDir = args.get(dirCfgName);
			checkFileName = args.get(fileCfgName);
		}
		//
		// 待处理文件目录地址
		if (checkFileDir == null) {
			checkFileDir = HsPropertiesConfigurer.getProperty(dirCfgName);
		}
		if (checkFileName == null) {
			checkFileName = HsPropertiesConfigurer.getProperty(fileCfgName);
		}
		if (checkFileDir == null || checkFileName == null) {
			logInfo("checkFileDir is null!");
			reportStatus(DSTaskStatus.FAILED, checkFileDir
					+ "执行失败! 待处理文件地址未配置!" + checkFileName);
			return null;
		}

		if (taskDate == null) {
			taskDate = DateUtil.DateToString(getYesterday(), "yyyyMMdd");
		}
		// YYYYMMDD_CHECK.TXT
		checkFileAddr = checkFileDir + checkFileName;
		checkFileAddr = checkFileAddr.replaceAll("YYYYMMDD", taskDate);
		return checkFileAddr;
	}

	/**
	 * 读取数据文件内容，并入库。每个文件启动一个线程处理
	 * 
	 * @param dataFileList
	 */
	void readDataFileAndUpdateDb(ArrayList<String[]> dataFileList) {
		if (dataFileList == null || dataFileList.isEmpty()) {
			logInfo("data file is null---");
			return;
		}
		// 最大线程数
		int MAX_THEADS = 10; // 限制最多10个线程
		// 批处理数据条数
		int batchCount = 1000; // 每1000条数据更新一次数据库,据说 in（）子句数据项最多1000

		// 待处理数据文件数量
		int fileCount = dataFileList.size();
		// int threadCount = fileCount > MAX_THEADS ? MAX_THEADS : fileCount; //
		// 线程数量
		// 记账任务多少记账文件初始化线程池，多少子线程
		ExecutorService executor = Executors.newFixedThreadPool(MAX_THEADS);
		CountDownLatch countDownLatch = new CountDownLatch(fileCount); // 线程阻塞器,使线程阻塞，等待其他线程全部跑完
		PvDateUpdateRunnable runnableTask; // 单个数据文件处理程序
		for (String[] dataFileInfo : dataFileList) {
			// 生成待处理任务
			runnableTask = new PvDateUpdateRunnable(dataFileInfo[0],
					batchMapper, batchCount, countDownLatch, listener);

			// 把任务提交线程池，线程池会创建一个新线程(或者从池中获取一个空闲线程)执行任务
			executor.execute(runnableTask);
		}

		logInfo(Thread.currentThread().getName() + " countDownLatch="
				+ countDownLatch.getCount());
		try {
			countDownLatch.await(); // 当前线程阻塞，等待所有子线程任务执行完毕 （countDown==0）
		} catch (InterruptedException e1) {
			logError(Thread.currentThread().getName() + "线程被打断", e1);
		}
		executor.shutdown();

		// 发送进度通知
		reportStatus(DSTaskStatus.HANDLING, fileCount + "个数据文件 处理完成");
	}

	ArrayList<String[]> getDataFileListFromCheckFile(String checkFileAddress)
			throws Exception, FileNotFoundException {
		String encoding = "UTF-8";
		ArrayList<String[]> dataFileList = new ArrayList<String[]>();
		File checkFile = new File(checkFileAddress);
		// 获取记账任务绝对路径文件夹地址
		String fileDir = checkFile.getParent();
		// 读取记账稽查文件
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(
					checkFile), encoding);
			bufferedReader = new BufferedReader(inputStreamReader);
			String textInLine = "";
			// 拆解每行数据:子文件名称|子文件记录数
			String[] columnsOfText;
			int lineNum = 0;
			while (true) {
				lineNum++;
				textInLine = bufferedReader.readLine();
				if (textInLine == null || textInLine.equalsIgnoreCase("END")
						|| "0".equals(textInLine)) {
					// END读取结束,or数据文件数为0 or 读到文件结尾
					break;
				} else if (textInLine.trim().length() == 0) {
					logInfo(checkFileAddress + " read a empty line at lineNum="
							+ lineNum);
					continue;
				}

				// 拆解每行数据:子文件名称|子文件记录数
				columnsOfText = textInLine.split("\\|");
				if (columnsOfText.length >= 2) {
					columnsOfText[0] = fileDir + File.separator
							+ columnsOfText[0]; // 拼装数据文件绝对路径文件名
					dataFileList.add(columnsOfText);
				} else {
					logInfo("ERROR ---------------------- "
							+ this.getClass().getName());
					logInfo(checkFileAddress
							+ " line text format is wrong,lineTxt="
							+ textInLine);
					logInfo("ERROR ---------------------- "
							+ this.getClass().getName());
				}

			}
		} catch (IOException e) {
			throw new HsException(RespCode.AC_IO_ERROR.getCode(),
					e.getMessage());
		} catch (Exception e) {
			logError("read file error!", e);
			throw e;
		} finally {

			// 关闭读取
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					logError("", e);
				}
			}
			// 关闭流
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					logError("", e);
				}
			}
		}

		return dataFileList;
	}

	public void logInfo(String msg) {
		SystemLog.info("hxst-uc-batch", "PvDateUpdateBatchService", msg);
	}

	public void logError(String msg, Exception e) {
		SystemLog.error("hxst-uc-batch", "PvDateUpdateBatchService", msg, e);
	}

	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();// 获取日历
		// calendar.setTime(new Date());// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		return calendar.getTime();
	}

	public static void main(String[] args) {
		String preDate = DateUtil.DateToString(getYesterday(), "yyyyMMdd");
		System.out.print(preDate);
	}

}
