/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.common.utils.PrintFileIdUtils;
import com.gy.hsi.fs.common.utils.ThreadUtils;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.mapper.vo.dbws01.TWsImg;
import com.gy.hsi.fs.service.IWSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker
 * 
 *  File Name       : UcWorker.java
 * 
 *  Creation Date   : 2015年11月25日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class WsFileIdWorker {
	private static final Logger logger = Logger.getLogger(WsFileIdWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 250;
	private static int MAX_FAILED_TIMES = 3;
		
	private static final List<String> statisticResultList = new ArrayList<String>();

	public static List<String> doIt() {
		System.out.println();
		System.out.println("======================WS积分福利数据库表处理========================");

		logger.info("======================WS积分福利数据库表处理========================");

		try {
			handleTWsImg("T_WS_IMG");
		} catch (Exception e) {
			logger.error("处理T_WS_IMG时出错：", e);
		}

		return statisticResultList;
	}

	private static void handleTWsImg(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TWsImg> list = null;

		IWSDataTransferService wsService = beanFactory
				.getWsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("IMG_PATH"));

		while (true) {
			try {
				list = wsService
						.queryTWsImgList(start, (start + PAGE_SIZE - 1));
			} catch (Exception e) {
				logger.error("查询" + tableName + "表时出错：start=" + start
						+ ",pageSize=" + PAGE_SIZE, e);

				queryFailedTimes++;

				if (MAX_FAILED_TIMES > queryFailedTimes) {
					ThreadUtils.sleepAmoment(1000);
					continue;
				}
			}

			if ((null == list) || (0 >= list.size())) {
				PrintFileIdUtils.printFinishLog(tableName, totalRows, counterList,
						statisticResultList);

				break;
			} else {
				System.out.println("正在努力地修理表" + tableName + ", from " + start
						+ " to " + (start + PAGE_SIZE - 1) + " ..., 累出汗了都!!!");

				totalRows += list.size();
			}

			for (TWsImg record : list) {
				// ---------------------------0:IMG_PATH-----------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getImgPath();

				record.setImgPath(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				wsService.updateTWsImg(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}
}
