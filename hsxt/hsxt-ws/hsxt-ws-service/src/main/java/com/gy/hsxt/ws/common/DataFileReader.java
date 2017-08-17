/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.common;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.AccumulativePoint;
import com.gy.hsxt.ws.mapper.AccumulativePointMapper;

/**
 * 读取文件内容，入库
 * 
 * @Package: com.gy.hsxt.ws.common
 * @ClassName: DataFileReader
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-28 上午9:41:14
 * @version V1.0
 * 
 */
public class DataFileReader implements Runnable {
	private static final String KEY_WS_BATCH_SUM = "ws.batchSum";

	private static final String UTF_8 = "UTF-8";

	AccumulativePointMapper pointMapper;

	String detailFilePath;
	
	String executeDate;

	CountDownLatch countDownLatch;

	public DataFileReader(String detailFilePath, CountDownLatch pCountDownLatch,
			AccumulativePointMapper pointMapper,String executeDate) {
		this.pointMapper = pointMapper;
		this.detailFilePath = detailFilePath;
		this.countDownLatch = pCountDownLatch;
		this.executeDate = executeDate;

	}

	public void run() {
		try {
			doAction();
		} catch (Exception e) {
			logError(e.getMessage(), e);
		} finally {
			countDownLatch.countDown(); // 通知主线程阻塞器，本线程执行完毕
		}
	}

	void doAction() {
		File file = new File(detailFilePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
			String tempStr = null;
			int batchCount = HsPropertiesConfigurer.getPropertyIntValue(KEY_WS_BATCH_SUM);
			List<AccumulativePoint> list = new ArrayList<AccumulativePoint>();
			// 文件数据格式 ：客户号|互生号|消费者姓名|当日累计消费积分|当日累计投资积分
			while ((tempStr = br.readLine()) != null) {
				if (isBlank(tempStr)) {
					continue;
				}
				tempStr = tempStr.trim();
				if(tempStr.equals("END")){
					continue;
				}
				if (!tempStr.contains("|")) {
					logInfo("file：" + detailFilePath + "data：" + tempStr + " not format!");
					continue;
				}
				String[] array = tempStr.split("\\|");
				if (array.length != 5) {
					logInfo("file：" + detailFilePath + "data：" + tempStr + " not format!");
					continue;
				}
				if (!isDigest(array[3]) || !isDigest(array[4])) {
					logInfo("file：" + detailFilePath + "data：" + tempStr + " not format!");
					continue;
				}
				AccumulativePoint accumulativePoint = bulidAccumulativePoint(array);
				list.add(accumulativePoint);
				if (list.size() > batchCount) {
					pointMapper.batchInsertPoint(list);
					list.clear();
				}
			}
			if (!list.isEmpty()) {
				pointMapper.batchInsertPoint(list);
			}
		} catch (FileNotFoundException e) {
			throw new HsException(WsErrorCode.READ_FILE_FAIL.getCode(), detailFilePath + " 文件未找到!");
		} catch (Exception e) {
			throw new HsException(WsErrorCode.READ_FILE_FAIL.getCode(), detailFilePath + " 文件读取错误!");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				throw new HsException(RespCode.AC_IO_ERROR.getCode(), e.getMessage());
			}

		}
	}
	
	private boolean isDigest(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	private AccumulativePoint bulidAccumulativePoint(String[] array) {
		AccumulativePoint accumulativePoint = new AccumulativePoint();
		accumulativePoint.setCustId(array[0].trim());
		accumulativePoint.setHsResNo(array[1].trim());
		accumulativePoint.setCustName(array[2].trim());
		// accumulativePoint.setConsumePoint(Long.parseLong(array[3]));
		accumulativePoint.setConsumePoint(new BigDecimal(array[3].trim()));
		// accumulativePoint.setInvestPoint(Long.parseLong(array[4]));
		accumulativePoint.setInvestPoint(new BigDecimal(array[4].trim()));
		accumulativePoint.setAccountingDate(executeDate);
		return accumulativePoint;
	}

	private void logInfo(String msg) {
		SystemLog.info("hs-ws", "DataFileReader", msg);
	}

	private void logError(String msg, Exception e) {
		SystemLog.error("hs-ws", "DataFileReader", msg, e);
	}

}
