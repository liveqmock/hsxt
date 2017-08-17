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

package com.gy.hsxt.tc.batch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tc.batch.bean.TcAccountDays;
import com.gy.hsxt.tc.batch.bean.TcPsacSummary;
import com.gy.hsxt.tc.batch.mapper.TcAccountDaysMapper;
import com.gy.hsxt.tc.batch.mapper.TcPsacMapper;
import com.gy.hsxt.tc.batch.mapper.TcPsacSummaryMapper;
import com.gy.hsxt.tc.batch.runnable.DataFileReader;
import com.gy.hsxt.tc.batch.runnable.IDataHandler;
import com.gy.hsxt.tc.batch.runnable.callback.DataHandler4psacAC;
import com.gy.hsxt.tc.batch.runnable.callback.DataHandler4psacPS;

/**
 * 消费积分(PS)与账务系统(AC)对账(PS-AC) 定时调度--计划每日执行一次。
 * 
 * @Package: com.gy.hsxt.tc.batch.service
 * @ClassName: PSACBatchService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
@Service
public class PSACBatchService implements IDSBatchService {
	// private static Log log = LogFactory.getLog(PSACBatchService.class);
	final static String moudleName = "PSACBatchService";

	/**
	 * 回调监听类
	 */
	@Autowired
	IDSBatchServiceListener listener;

	@Resource(name="changeRedisTemplate")
	RedisTemplate redisTemplate;

	@Autowired
	TcAccountDaysMapper tcAccountDaysMapper;

	@Autowired
	TcPsacMapper tcMapper;

	@Autowired
	TcPsacSummaryMapper tcSummaryMapper;

	/**
	 * 对账源端（长款端）
	 */
	DataHandler4psacPS srcDataHandler;

	/**
	 * 对账目标端（短款端）
	 */
	DataHandler4psacAC destDataHandler;

	/**
	 * 对账业务简称
	 */
	static final String MY_NAME = "PS-AC";

	/**
	 * 对账长款端
	 */
	static final String TC_MORE = "_PSAC_PS";

	/**
	 * 对账短款端
	 */
	static final String TC_LACK = "_PSAC_AC";

	/**
	 * 对账要素字段数组 取消: "ACC_TYPE"
	 */
	static final String[] CHECK_KEY_COLUMNS = new String[] { "CUST_ID",
			"ADD_AMOUNT", "DEC_AMOUNT", "TRANS_NO", "ACCOUNT_SUB_ID" };

	/**
	 * 对账要素中，交易金额的字段位置
	 */
	static final int MONEY_INDEX = 1;

	/**
	 * 对账日期 YYYY-MM-DD
	 */
	static final String TC_DATE = "TC_DATE";

	/**
	 * 对账源端数据文件列表
	 */
	static final String SRC_FILE_LIST = "SRC_FILE_LIST";

	/**
	 * 对账目标端数据文件列表
	 */
	static final String DEST_FILE_LIST = "DEST_FILE_LIST";

	/**
	 * 对账执行情况
	 */
	static final String TC_ACCOUNT_DAYS = "TC_ACCOUNT_DAYS";

	/**
	 * 对账汇总结果
	 */
	static final String TC_SUMMARY = "TC_SUMMARY";

	/**
	 * 对账文件属主
	 */
	static final String FILE_OWNER = "FILE_OWNER";

	String executeId;
	public void addListener(IDSBatchServiceListener listener) {
		this.listener = listener;
	}

	void reportStatus(int code, String msg) {
		if (listener != null) {
			SystemLog.info(moudleName, "", msg);
			// 回调发送进度通知给调度中心
			listener.reportStatus( executeId,code, msg);
		} else {
			SystemLog.info(moudleName, "", code
					+ " IDSBatchServiceListener is null,msg=" + msg);
		}
	}

	@Override
	/**
	 * 调入任务启动入口，由调度中心调起
	 * @param args need keys:checkFileAddress=待处理文件地址  or YYYYMMDD
	 * @return 
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	public boolean excuteBatch(String executeId,Map<String, String> args) {
		// 使用日志中心记日志
		SystemLog.info(moudleName, "", " excuteBatchbegin");
		this.executeId=executeId;
		// 数据容器,用于存储任务执行中的中间数据，以便传递给后续业务逻辑.
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		// 对账执行情况表
		TcAccountDays lTcAccountDays = new TcAccountDays();
		lTcAccountDays.setAccSys(MY_NAME); // 设置对账业务简称
		dataMap.put(TC_ACCOUNT_DAYS, lTcAccountDays);
		// 对账汇总结果
		TcPsacSummary lTcSummary = new TcPsacSummary();
		dataMap.put(TC_SUMMARY, lTcSummary);
		// 初始化对账文件处理器
		srcDataHandler = new DataHandler4psacPS(tcMapper, redisTemplate);
		destDataHandler = new DataHandler4psacAC(tcMapper, redisTemplate);

		try {// 开始执行任务
			reportStatus(DSTaskStatus.HANDLING, "任务开始"); // 回调发送进度通知给调度中心
			doTC(dataMap, args);
			reportStatus( DSTaskStatus.SUCCESS, "任务完成"); // 回调发送进度通知给调度中心
		} catch (Exception e) {
			String errMsg = e.getMessage();
			// 使用日志中心记日志
			SystemLog.error(moudleName, "excuteBatch", "对账异常结束", e);
			reportStatus(DSTaskStatus.FAILED, "任务异常:" + errMsg); // 回调发送进度通知给调度中心
			// 设置对账任务执行状态=失败
			lTcAccountDays.setAccState((short) 1);
			if (errMsg.length() > 250) {
				errMsg = errMsg.substring(0, 250);
			}
			lTcAccountDays.setAccDesc(errMsg);
			return false;
		} finally {// 保存 对账执行情况表
			saveTcAccountDays(lTcAccountDays);
		}
		// 使用日志中心记日志
		SystemLog.info(moudleName, "", "excuteBatch end");
		return true;
	}

	/**
	 * 开始对账处理
	 * 
	 * @param dataMap
	 * @param args
	 */
	void doTC(HashMap<String, Object> dataMap, Map<String, String> args) {
		readCheckFile(dataMap, args); // 读取 chk文件；把对账数据文件列表，存入dataMap

		readDataFile(dataMap); // 读取对账数据文件，对账数据入库；
		// 回调发送进度通知给调度中心
		reportStatus(2, "gp,ch对账数据文件读取完成,数据准备完成下一步开始对账");
		tcByCheckKey(dataMap); // 对账比较长短款(使用redis里面保存的对账要素字串做比对)

//		handelTcMore(dataMap); // 处理 长款账单
//		handelTcLack(dataMap); // 处理短款账单

//		moveDoubtToImbalance(dataMap); // 存疑账单移入 不均衡表
		// 设置对账正常完成
		((TcAccountDays) dataMap.get(TC_ACCOUNT_DAYS)).setAccState((short) 0);
	}

	/**
	 * 读取chk文件预处理,从args里获取chk文件地址
	 * 
	 * @param dataMap
	 * @param args
	 * @param fileFrom
	 *            数据文件来源
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	void prepareCheckFile(HashMap<String, Object> dataMap,
			Map<String, String> args, String fileFrom) throws Exception,
			FileNotFoundException {
		dataMap.put(FILE_OWNER, fileFrom);
		// chk文件地址
		String checkFileAddress = args.get("checkFileAddress" + fileFrom);
		String taskDate = args.get("YYYYMMDD"); // 对账日期 YYYYMMDD
		if (taskDate == null) {
			taskDate = getLastDay();
		}
		// 对账日期YYYY-MM-DD
		String lTC_DATE = dateFormat(taskDate);

		if (checkFileAddress == null) {
			// 对账文件目录： TCAS\GP\YYYYMMDD\
			// String checkFileDir = args.get("checkFileDir" + fileFrom);
			// // 对账文件名： GP_CH_YYYYMMDD_CHK
			// String checkFileName = args.get("checkFileName" + fileFrom);
			// 待处理文件目录地址
			String checkFileDir = HsPropertiesConfigurer
					.getProperty("checkFileDir" + fileFrom);
			String checkFileName = HsPropertiesConfigurer
					.getProperty("checkFileName" + fileFrom);

			if (checkFileDir == null || taskDate == null
					|| checkFileName == null) {
				SystemLog.error(moudleName, "prepareCheckFile",
						"cannot get checkFileAddress from args:" + args, null);
				reportStatus(1, "need param,cannot get checkFileAddress");
				throw new HsException(34000,
						"cannot get checkFileAddress from args:" + args);
			}

			// 支付系统对账文件路径：TCAS\GP\YYMMDD\ GP_CH_YYMMDD_CHK
			checkFileAddress = checkFileDir + checkFileName;
			checkFileAddress = checkFileAddress
					.replaceAll("YYYYMMDD", taskDate);

		}

		dataMap.put("checkFileAddress", checkFileAddress);
		dataMap.put(TC_DATE, lTC_DATE);
		((TcAccountDays) dataMap.get(TC_ACCOUNT_DAYS)).setAccDate(lTC_DATE);
		((TcPsacSummary) dataMap.get(TC_SUMMARY)).setAccDate(lTC_DATE);
	}

	/**
	 * 读取 chk文件；把对账数据文件列表，存入dataMap
	 * 
	 * @param args
	 */
	void readCheckFile(HashMap<String, Object> dataMap, Map<String, String> args) {
		TcAccountDays lTcAccountDays = (TcAccountDays) dataMap
				.get(TC_ACCOUNT_DAYS);
		// 设置对账进度 0 预处理
		lTcAccountDays.setAccProgress((short) 0);
		// 设置对账状态 2 处理中
		lTcAccountDays.setAccState((short) 2);

		try {
			// 从调度中心传入参数args里获取GP 端chk文件地址及对账日期
			prepareCheckFile(dataMap, args, TC_MORE);
			// 读取chk文件 获取对账数据文件列表及数据总记录数
			getDataFileListFromCheckFile(dataMap);

			// 从调度中心传入参数args里获取CH 端chk文件地址及对账日期
			prepareCheckFile(dataMap, args, TC_LACK);
			// 读取chk文件 获取对账数据文件列表及数据总记录数
			getDataFileListFromCheckFile(dataMap);

		} catch (Exception e) {
			SystemLog.error(moudleName, "prepareCheckFile", "args=" + args,
					null);

			lTcAccountDays.setAccState((short) 1);
			lTcAccountDays.setAccDesc("读取 chk文件出错");
			reportStatus(1, "执行失败:" + e.getMessage());
			e.printStackTrace();
			throw new HsException(34000, "readCheckFile wrong! args:" + args);
		}

	}

	/**
	 * 读取对账数据文件，对账数据入库
	 * 
	 * @param dataMap
	 */
	void readDataFile(HashMap<String, Object> dataMap) {
		// // GP对账文件入库处理器
		// DataHandler4gpchGp gpDataHandler = new
		// DataHandler4gpchGp(batchMapper, redisTemplate);
		// // CH对账文件入库处理器
		// DataHandler4gpchCh chataHandler = new DataHandler4gpchCh(batchMapper,
		// redisTemplate);
		// // dataMap.put(GP_DATA_HANDLER, gpDataHandler);
		// dataMap.put(CH_DATA_HANDLER, chataHandler);

		reportStatus(2, " 开始读取gp对账数据文件并把数据入库");
		readDataFileFromSrc(dataMap); // 读取gp对账数据文件并把数据入库
		reportStatus(2, " 开始读取ch对账数据文件并把数据入库");
		readDataFileFromDest(dataMap); // 读取ch对账数据文件并把数据入库

	}

	/**
	 * 对账
	 * 
	 * @param dataMap
	 */
	void tcByCheckKey(HashMap<String, Object> dataMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(TC_DATE, dataMap.get(TC_DATE));// 设置对账日期		
		tcMapper.tcProcedure(map);
	}

	/**
	 * 处理 长款账单 1,核对短款存疑账单 2,长款账单入库存疑表
	 * 
	 * @param dataMap
	 */
	void handelTcMore(HashMap<String, Object> dataMap) {
		Set<String> tcMoreSet = (Set<String>) dataMap.get(TC_MORE);
		if (tcMoreSet == null || tcMoreSet.isEmpty()) {
			SystemLog.info(moudleName, "", "无长款，tcMoreSet=" + tcMoreSet);
			return;
		}
		HashMap<String, Object> keyDataMap; // 对账要素数据
		// int lUpdatCount;
		for (String s : tcMoreSet) {
			keyDataMap = keyString2keyMap(s);
			SystemLog.info(moudleName, "", "" + keyDataMap);

			// 该记录作为 长款 记录 进入 存疑表
			keyDataMap.put(TC_DATE, dataMap.get(TC_DATE));// 设置对账日期
			tcMapper.insertDoubtByMore(keyDataMap);

		}

	}

	/**
	 * 处理 短款账单 1,核对长款存疑账单 2,短款账单入库存疑表
	 * 
	 * @param dataMap
	 */
	void handelTcLack(HashMap<String, Object> dataMap) {
		Set<String> tcLackSet = (Set<String>) dataMap.get(TC_LACK);
		if (tcLackSet == null || tcLackSet.isEmpty()) {
			SystemLog.info(moudleName, "", "tc无短款，tcLackSet=" + tcLackSet);
			return;
		}
		HashMap<String, Object> keyDataMap; // 对账要素数据
		int lUpdatCount;
		for (String s : tcLackSet) {
			keyDataMap = keyString2keyMap(s);
			SystemLog.info(moudleName, "", "" + keyDataMap);
			// 比对存疑表长款数据， 尝试更新状态为 要素不一致
			lUpdatCount = tcMapper.updateDoubtByLack(keyDataMap);
			if (lUpdatCount == 0) {// 不能匹配长款账单，该记录作为 短款 记录 进入 存疑表
				keyDataMap.put(TC_DATE, dataMap.get(TC_DATE));// 设置对账日期
				tcMapper.insertDoubtByLack(keyDataMap);
			}
		}

	}

	/**
	 * 把T-1期的存疑账单存入不均衡表,然后从存疑表删除
	 * 
	 * @param dataMap
	 */
	void moveDoubtToImbalance(HashMap<String, Object> dataMap) {
		// 把存疑账单 存入不均衡表
		tcMapper.insertImbalanceByDoubt(dataMap);
		// 删除存疑表账单数据
		tcMapper.deleteDoubtAfterMoveToImbalance(dataMap);
	}

	/**
	 * 读取对账源端数据文件内容，并入库。每个文件启动一个线程处理 *
	 * 
	 * @param dataMap
	 */
	void readDataFileFromSrc(HashMap<String, Object> dataMap) {

		ArrayList<String[]> dataFileList = (ArrayList<String[]>) dataMap
				.get(SRC_FILE_LIST);

		if (dataFileList == null || dataFileList.isEmpty()) {
			SystemLog.error(moudleName, "readDataFileFromSrc",
					"dataFileList.isEmpty" + dataFileList, null);
			return;
		}
		// 数据入库前 先 删除临时表旧数据
		tcMapper.deleteTableData(srcDataHandler.MY_TABLE);
		redisTemplate.delete(srcDataHandler.MY_TABLE);
		// 开始读取文件
		readDataFile(dataFileList, srcDataHandler);
	}

	/**
	 * 读取对账目标端数据文件内容，并入库。每个文件启动一个线程处理 *
	 * 
	 * @param dataMap
	 */
	void readDataFileFromDest(HashMap<String, Object> dataMap) {

		ArrayList<String[]> dataFileList = (ArrayList<String[]>) dataMap
				.get(DEST_FILE_LIST);

		if (dataFileList == null || dataFileList.isEmpty()) {
			SystemLog.error(moudleName, "readDataFileFromDest",
					"dataFileList.isEmpty" + dataFileList, null);
			return;
		}
		// 数据入库前 先 删除临时表旧数据
		tcMapper.deleteTableData(destDataHandler.MY_TABLE);
		redisTemplate.delete(destDataHandler.MY_TABLE);
		// 开始读取文件
		readDataFile(dataFileList, destDataHandler);
	}

	/**
	 * 读取对账数据文件内容，并入库。每个文件启动一个线程处理
	 * 
	 * @param dataFileList
	 * @param pDataHandler
	 */
	void readDataFile(ArrayList<String[]> dataFileList,
			IDataHandler pDataHandler) {
		// 数据文件总数据记录数
		long totalDataCountFromCheckFile = 0;
		// 最大线程数
		int MAX_THEADS = 10; // 限制最多10个线程

		// 待处理数据文件数量
		int fileCount = dataFileList.size();
		int threadCount = fileCount > MAX_THEADS ? MAX_THEADS : fileCount; // 线程数量

		DataFileReader runnableTask; // 单个数据文件处理程序
		// 文件处理线程池
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		// 线程阻塞器,可使线程阻塞，等待其他线程全部跑完后再被唤醒
		CountDownLatch countDownLatch = new CountDownLatch(fileCount);
		// argMap.put(DataFileReader.LATCH, countDownLatch);
		int dataCountFromCheckFile = 0;
		for (String[] dataFileInfo : dataFileList) { // dataFileInfo
														// :子文件名称|子文件记录数
			dataCountFromCheckFile = Integer.valueOf(dataFileInfo[1]);
			totalDataCountFromCheckFile += dataCountFromCheckFile;

			// 生成待处理任务
			runnableTask = new DataFileReader(dataFileInfo[0],
					dataCountFromCheckFile, pDataHandler, null, countDownLatch,
					listener);

			// 把任务提交线程池，线程池会从池中获取一个空闲线程执行任务
			executor.execute(runnableTask);
		}

		SystemLog.info(moudleName, "", Thread.currentThread().getName()
				+ " countDownLatch=" + countDownLatch.getCount());
		try {
			countDownLatch.await(); // 当前线程阻塞，等待所有子线程任务执行完毕 （countDown==0）
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		executor.shutdown();
		// chk文件里获取的对账数据条数 与 从数据文件里读到的对账数据条数 比较 ,不一致则抛异常
		if (totalDataCountFromCheckFile != pDataHandler.getDataCount()) {
			throw new HsException(34000, totalDataCountFromCheckFile
					+ "=check文件数据总数 不等于 数据文件记录数 ="
					+ pDataHandler.getDataCount());
		}

	}

	/**
	 * 读取chk文件，获取数据文件列表
	 * 
	 * @param args
	 * @param fileFrom
	 *            _GP or _CH
	 * @return
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	void getDataFileListFromCheckFile(HashMap<String, Object> dataMap)
			throws Exception, FileNotFoundException {
		// 待处理文件地址
		String checkFileAddress = (String) dataMap.get("checkFileAddress");

		ArrayList<String[]> dataFileList = new ArrayList<String[]>();
		File file = new File(checkFileAddress);
		// 获取文件夹地址
		String fileDir = file.getParent();
		// 读取CHK文件
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String lineText = "";
		// 拆解每行数据:子文件名称|子文件记录数
		String[] dataFileInfo;
		int lineNum = 0; // 文本行号
		int lDataFileNum = 0; // 数据文件个数(文件第一行文本)
		long lDataCount = 0; // 所有数据文件里的对账数据条数之和
		try {
			inputStreamReader = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			while (true) {
				lineNum++;
				lineText = bufferedReader.readLine();
				// 碰到END不在读取 or // 读到文件结尾
				if (lineText == null || lineText.equalsIgnoreCase("END")) {
					break;
				} else if (lineText.length() == 0) {
					SystemLog.error(moudleName, "getDataFileListFromCheckFile",
							checkFileAddress + " read a empty line at lineNum="
									+ lineNum, null);
					continue;
				}
				if (lineNum == 1) { // 第一行为数据文件数量
					lDataFileNum = Integer.valueOf(lineText);
					continue;
				}

				// 拆解每行数据:子文件名称|子文件记录数
				dataFileInfo = lineText.split("\\|");
				if (dataFileInfo.length >= 2) {
					String fileName = dataFileInfo[0];
					dataFileInfo[0] = fileDir + File.separator + fileName; // 拼装数据文件绝对路径文件名
					SystemLog.info(moudleName, "", dataFileInfo[0]);
					dataFileList.add(dataFileInfo);
					// 计算数据文件总记录数
					lDataCount += Integer.parseInt(dataFileInfo[1]);
				} else {
					SystemLog.error(moudleName, "getDataFileListFromCheckFile",
							"ERROR ---------------------- ", null);
					SystemLog.error(moudleName, "getDataFileListFromCheckFile",
							checkFileAddress
									+ " line text format is wrong,lineTxt="
									+ lineText, null);
					SystemLog.error(moudleName, "getDataFileListFromCheckFile",
							"ERROR ---------------------- ", null);
				}

			}

			if (lDataFileNum != dataFileList.size()) {
				throw new HsException(34000, "chk文件里数据文件数量值与数据文件列表数不等");
			}

			if (TC_MORE.equals(dataMap.get(FILE_OWNER))) {
				// 把 数据总记录数 存入 本次 对账汇总结果表
				((TcPsacSummary) dataMap.get(TC_SUMMARY))
						.setPsTranNum(lDataCount);

				dataMap.put(SRC_FILE_LIST, dataFileList);
			} else {
				// 把 数据总记录数 存入 本次 对账汇总结果表
				((TcPsacSummary) dataMap.get(TC_SUMMARY))
						.setAcTranNum(lDataCount);

				dataMap.put(DEST_FILE_LIST, dataFileList);
			}
		} catch (Exception e) {
			SystemLog.error(moudleName, "getDataFileListFromCheckFile", "#"
					+ checkFileAddress + "#", e);
			SystemLog.error(moudleName, "getDataFileListFromCheckFile", "#"
					+ lineText + "#", e);

			// e.printStackTrace();
			throw new HsException(RespCode.UNKNOWN, e.getMessage());
		} finally {
			try {
				// 关闭读取
				bufferedReader.close();
				// 关闭流
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new HsException(RespCode.AC_IO_ERROR.getCode(),
						e.getMessage());
			}
		}

	}

	/**
	 * 把对账执行情况入库
	 */
	private void saveTcAccountDays(TcAccountDays pTcAccountDays) {
		tcAccountDaysMapper.insert(pTcAccountDays);
	}

	/**
	 * 根据对账要素，计算交易总金额
	 * 
	 * @param gpMoreSet
	 * @return
	 */
	private BigDecimal sumAmount(Set<String> pCheckKeySet, int pMoneyIndex) {

		BigDecimal ret = new BigDecimal(0);
		BigDecimal lAmount;
		String[] lKeyValue;
		for (String s : pCheckKeySet) {
			lKeyValue = s.split("\\|");
			lAmount = new BigDecimal(lKeyValue[pMoneyIndex]);
			ret = ret.add(lAmount);
		}
		return ret;
	}

	/**
	 * 把对账要素字符串，拆分转换成map 结构
	 * 
	 * @param keyString
	 *            对账要素字符串
	 * @return
	 */
	private HashMap<String, Object> keyString2keyMap(String keyString) {
		HashMap<String, Object> lKeyDataMap = new HashMap<String, Object>();
		String[] lKeyValues = keyString.split("\\|");
		int lLen = CHECK_KEY_COLUMNS.length;
		for (int i = 0; i < lLen; i++) {
			lKeyDataMap.put(CHECK_KEY_COLUMNS[i], lKeyValues[i]);
		}
		return lKeyDataMap;
	}

	/**
	 * 返回YYYY-MM-DD 格式日期字符串长度10
	 * 
	 * @param dayString
	 *            YYYYMMDD格式日期字符串长度8
	 * @return
	 */
	public static String dateFormat(String dayString) {
		if (dayString != null && dayString.length() == 8) {
			StringBuilder sb = new StringBuilder(dayString);
			sb.insert(6, '-');
			sb.insert(4, '-');
			return sb.toString();
		} else {
			return dayString;
		}
	}

	/**
	 * 获取昨日日期
	 * 
	 * @return yyyyMMdd
	 */
	public static String getLastDay() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, -1); // 天减一

		String ret = sf.format(now.getTime());
		// SystemLog.info(moudleName, "", ret);
		return ret;
	}

	public static void main(String[] args) {
		SystemLog.info(moudleName, "", dateFormat("20151122"));
		SystemLog.info(moudleName, "", getLastDay());
	}

}
