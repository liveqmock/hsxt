/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ws.bean.ExecuteLog;
import com.gy.hsxt.ws.common.DataFileReader;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.mapper.AccumulativePointMapper;
import com.gy.hsxt.ws.mapper.ExecuteLogMapper;
import com.gy.hsxt.ws.mapper.WelfareQualificationMapper;

/**
 * 积分福利批处理服务
 * 
 * 每天定时处理账务生成的批处理文件（包含消费者当日累计的消费积分、投资积分） 处理已达到资格的消费者，
 * 
 * 自动生成一份保障单，对于连续失效的取消福利资格
 * 
 * @Package: com.gy.hsxt.ws.service
 * @ClassName: WelfareBatchService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-17 上午11:49:01
 * @version V1.0
 */
@Service("welfareBatchService")
public class WelfareBatchService extends BasicService implements IDSBatchService {

	private static final String DETAL_FILE_NAME = "WS_PS_DETAIL";

	private static final String KEY_ROOT_DIR = "ws.batchService_file_root_dir";

	@Autowired
	private IDSBatchServiceListener listener;

	@Autowired
	public BusinessParamSearch businessParamSearch;

	@Autowired
	private AccumulativePointMapper pointMapper;

	@Autowired
	private ExecuteLogMapper logMapper;

	@Autowired
	WelfareQualificationMapper qualificationMapper;

	// 批处理存放格式 C:\\WS_PS\\YYYYMM\\YYYYMMDD\\
	private static String batchFileDir = "";

	// check fifle format WS_PS_DETAIL_01.txt
	private static String batchFileName = "WS_PS_CHECK.TXT";

	// 享受意外保障福利需累计消费积分的阀值
	private int consumeThresholdPoint = 300;

	// 享受住院补贴福利需累计投资积分的阀值
	private int investThresholdPoint = 10000;

	// 意外保障最大赔付金额
	private int maxPayAmount = 3000;

	// 连续失效的天数 致使意外保障失效
	private int durInvalidDays = 7;

	private static StringBuffer msg = new StringBuffer();

	private void initParam(Date executeTime) {
		msg = new StringBuffer();
		batchFileDir = HsPropertiesConfigurer.getProperty(KEY_ROOT_DIR) + File.separator
				+ DateUtil.DateToString(executeTime, "yyyyMM") + File.separator
				+ DateUtil.DateToString(executeTime, "yyyyMMdd") + File.separator;
		logInfo("batchFileDir:---" + batchFileDir);

		// 从业务参数系统 获取积分福利相关参数组
		Map<String, BusinessSysParamItemsRedis> parmGroup = businessParamSearch
				.searchSysParamItemsByGroup(BusinessParam.JF_WELFARE.getCode());

		// 获取意外保障最大赔付金额参数
		BusinessSysParamItemsRedis paramItem = parmGroup
				.get(BusinessParam.P_ACCIDENT_INSURANCE_SUBSIDIES_YEAR_MAX.getCode());
		if (paramItem != null) {
			this.maxPayAmount = new BigDecimal(paramItem.getSysItemsValue().trim()).intValue();
		}

		// 获取享受意外保障福利需累计消费积分的阀值 参数
		paramItem = parmGroup.get(BusinessParam.P_CONSUME_THRESHOLD_POINT.getCode());
		if (paramItem != null) {
			this.consumeThresholdPoint = new BigDecimal(paramItem.getSysItemsValue().trim())
					.intValue();
		}

		// 获取享受住院补贴福利需累计投资积分的阀值
		paramItem = parmGroup.get(BusinessParam.P_INVEST_THRESHOLD_POINT.getCode());
		if (paramItem != null) {
			this.investThresholdPoint = new BigDecimal(paramItem.getSysItemsValue().trim())
					.intValue();
		}

		// 获取 连续失效的天数 致使意外保障失效
		paramItem = parmGroup.get(BusinessParam.P_DUR_INVALID_DAYS.getCode());
		if (paramItem != null) {
			this.durInvalidDays = new BigDecimal(paramItem.getSysItemsValue().trim()).intValue();
		}

		logInfo("consumeThresholdPoint:--" + this.consumeThresholdPoint);
		logInfo("investThresholdPoint:--" + this.investThresholdPoint);
		logInfo("durInvalidDays:--" + this.durInvalidDays);

	}

	/**
	 * 执行批处理任务
	 * 
	 * @param arg0
	 * @return
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	@Override
	public boolean excuteBatch(String executeId, Map<String, String> arg1) {
		String batchDate = null;
		Date executeTime = null;
		try {
			// 上报任务进度给调度中心
			reportStatus(executeId, DSTaskStatus.HANDLING, "积分福利系统：执行开始");

			// 获取执行日期
			batchDate = arg1.get("WS_BATCH_DATE");
			executeTime = DateUtil.addDays(new Date(), -1);
			if (isNotBlank(batchDate)) {
				executeTime = DateUtil.addDays(DateUtil.StringToDate(batchDate, "yyyyMMdd"), -1);
				if (executeTime.after(new Date())) {
					reportStatus(executeId, DSTaskStatus.FAILED, "积分福利系统：执行失败>" + batchDate
							+ " 执行时间不能大于当前时间");
					return false;
				}
			}
			batchDate = DateUtil.DateToString(executeTime, "yyyyMMdd");

			// 查询是否已执行服务
			ExecuteLog executeLog = logMapper.querySucessLog(batchDate, ExecuteLog.EXECUTE_SERVICE);
			if (executeLog != null) {
				reportStatus(executeId, DSTaskStatus.FAILED, "积分福利系统：执行失败>" + batchDate
						+ " 已执行成功过一次了");
				return false;
			}

			// 初始化参数
			initParam(executeTime);

			// 获取check文件
			String checkFilePath = batchFileDir + batchFileName;
			logInfo("checkFilePath-----:" + checkFilePath);
			logInfo("executeDate-----:" + batchDate);
			File checkFile = new File(checkFilePath);

			// 读取check文件获取 详细文件列表
			List<String> detailFileList = getDetailFileList(executeId, checkFile);

			logInfo("detailFileList size-----" + detailFileList.size());

			logInfo("clean temp data!-----");
			// 清空临时数据
			pointMapper.clearTempData(batchDate);

			// 多线程处理文件（读取文件数据入库，插入数据到临时表）
			handDetailFile(detailFileList, batchDate);

			// 统计累计积分
			logInfo("count accumulative point begin!----");
			pointMapper.countAccumulativePoint(batchDate);
			logInfo("count accumulative point end!----");

			// 检查处理投资积分福利资格
			logInfo("hand invest point begin!----");
			try {
				qualificationMapper.handInvestPoint(investThresholdPoint,
						DateUtil.addDays(executeTime, 1));
			} catch (Exception e) {
				logError(e.getMessage(), e);
				msg.append(e.getMessage());
			}
			logInfo("hand invest point end!----");

			// 检查处理消费积分福利资格
			logInfo("hand consume point begin!----");
			try {
				qualificationMapper.handConsumePoint(consumeThresholdPoint, durInvalidDays,
						maxPayAmount, DateUtil.addDays(executeTime, 1));
			} catch (Exception e) {
				logError(e.getMessage(), e);
				msg.append(e.getMessage());
			}
			logInfo("hand consume point end!----");

			// 处理到期的福利资格
			logInfo("hand Expierd Welfare begin!----");
			try {
				qualificationMapper.handExpierdWelfare(consumeThresholdPoint, maxPayAmount,
						DateUtil.addDays(executeTime, 1));
			} catch (Exception e) {
				logError(e.getMessage(), e);
				msg.append(e.getMessage());
			}
			logInfo("hand Expierd Welfare begin!----");

			// 任务处理完成，上报进度给调度中心
			if (isNotBlank(msg.toString())) {
				reportStatus(executeId, DSTaskStatus.FAILED, "积分福利系统：执行失败>" + msg.toString());
				saveLog(batchDate, 0, msg.toString());
				return false;
			}
			reportStatus(executeId, DSTaskStatus.SUCCESS, "积分福利系统：执行成功");
			saveLog(batchDate, 1, null);
			return true;
		} catch (HsException e) {
			logError(e.getMessage(), e);
			reportStatus(executeId, DSTaskStatus.FAILED, "积分福利系统：执行失败>" + e.getMessage());
			saveLog(batchDate, 0, e.getMessage());
			return false;
		} catch (Exception e) {
			logError(e.getMessage(), e);
			reportStatus(executeId, DSTaskStatus.FAILED, "积分福利系统：执行失败>" + e.toString());
			saveLog(batchDate, 0, e.getMessage());
			return false;
		}
	}

	private void saveLog(String executeDate, int status, String msg) {
		ExecuteLog log = ExecuteLog.bulidExecuteLog();
		log.setStatus(status);
		log.setExecuteDate(executeDate);
		log.setCreateDate(new Timestamp(new Date().getTime()));
		log.setErrorDesc(msg);
		logMapper.insertSelective(log);
	}

	private void handDetailFile(List<String> detailFileList, String executeDate) {
		if (detailFileList.isEmpty()) {
			return;
		}
		logInfo("hand detail file begin---");
		DataFileReader runnableTask; // 单个数据文件处理程序
		int threadPoolSise = detailFileList.size();
		if (threadPoolSise > 5) {
			threadPoolSise = 5;
		}
		logInfo("threadPoolSise:---" + threadPoolSise);
		// 文件处理线程池
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSise);
		// 线程阻塞器,可使线程阻塞，等待其他线程全部跑完后再被唤醒
		CountDownLatch countDownLatch = new CountDownLatch(detailFileList.size());
		for (String dataFileInfo : detailFileList) {
			// 生成待处理任务
			runnableTask = new DataFileReader(dataFileInfo, countDownLatch, pointMapper,
					executeDate);
			// 把任务提交线程池，线程池会从池中获取一个空闲线程执行任务
			executor.execute(runnableTask);
		}
		try {
			logInfo("thread run begin:---");
			countDownLatch.await(); // 当前线程阻塞，等待所有子线程任务执行完毕 （countDown==0）
			logInfo("all thread run over:---");
		} catch (InterruptedException e1) {
			logInfo(Thread.currentThread().getName() + " is Interrupted!");
		}
		executor.shutdown();
		logInfo("hand detail file over---");
	}

	private List<String> getDetailFileList(String executeId, File checkFile) {
		List<String> detailFileList = new ArrayList<>();
		if (!checkFile.exists()) {
			msg.append("批处理check文件[" + checkFile.getPath() + "]未找到");
			return detailFileList;
		}
		// 读取check文件路径
		String fileContent = readFile(checkFile);
		logInfo("check file content:" + fileContent);
		// 获取详细文件列表
		String[] fileArray = fileContent.split("==");
		for (String file : fileArray) {
			if (isBlank(file)) {
				continue;
			}
			file = file.trim();
			if (!file.contains(DETAL_FILE_NAME) || !file.contains("|")) {
				continue;
			}
			String detailFilePath = batchFileDir + file.split("\\|")[0].trim();
			// 验证文件是否存在
			File f = new File(detailFilePath);
			if (!f.exists()) {
				msg.append("批处理detail文件[" + detailFilePath + "]未找到");
				continue;
			}
			detailFileList.add(batchFileDir + file.split("\\|")[0].trim());
		}
		return detailFileList;
	}

	private String readFile(File checkFile) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new FileReader(checkFile));
			String tempStr = null;
			while ((tempStr = br.readLine()) != null) {
				sb.append(tempStr);
				sb.append("==");
			}
		} catch (FileNotFoundException e) {
			throw new HsException(WsErrorCode.FILE_NOT_FOUND.getCode(), "file["
					+ checkFile.getPath() + "] not found!");
		} catch (IOException e) {
			throw new HsException(WsErrorCode.READ_FILE_FAIL.getCode(), "file["
					+ checkFile.getPath() + "] read exception!");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new HsException(WsErrorCode.READ_FILE_FAIL.getCode(), "file["
							+ checkFile.getPath() + "] read exception!");
				}
			}
		}
		return sb.toString();
	}

	private void reportStatus(String executeId, int status, String desc) {
		logInfo("report status to scheduler center ,status: ----" + status + " desc:--" + desc);
		listener.reportStatus(executeId, status, desc);
	}

}
