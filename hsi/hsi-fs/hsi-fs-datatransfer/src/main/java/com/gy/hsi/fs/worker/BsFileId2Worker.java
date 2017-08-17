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
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyle;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStop;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgame;
import com.gy.hsi.fs.service.IBSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker.bs
 * 
 *  File Name       : BsWorker2.java
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
public class BsFileId2Worker {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(BsFileId2Worker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 500;
	private static int MAX_FAILED_TIMES = 3;
	
	private static final List<String> statisticResultList = new ArrayList<String>();

	public static List<String> doIt() {
		try {
			handleTBsCardStyle("T_BS_CARD_STYLE");
		} catch (Exception e) {
			logger.error("处理T_BS_CARD_STYLE时出错：", e);
		}

		try {
			handleTBsEntRealnameAuth("T_BS_ENT_REALNAME_AUTH");
		} catch (Exception e) {
			logger.error("处理T_BS_ENT_REALNAME_AUTH时出错：", e);
		}

		try {
			handleTBsPerRealnameAuth("T_BS_PER_REALNAME_AUTH");
		} catch (Exception e) {
			logger.error("处理T_BS_PER_REALNAME_AUTH时出错：", e);
		}

		try {
			handleTBsPointgame("T_BS_POINTGAME");
		} catch (Exception e) {
			logger.error("处理T_BS_POINTGAME时出错：", e);
		}

		try {
			handleTBsCompanyStop("T_BS_COMPANY_STOP");
		} catch (Exception e) {
			logger.error("处理T_BS_POINTGAME时出错：", e);
		}

		return statisticResultList;
	}

	private static void handleTBsCardStyle(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsCardStyle> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("MICRO_PIC"));
		counterList.add(new ResultCounter("SOURCE_FILE"));
		counterList.add(new ResultCounter("MATERIAL_MICRO_PIC"));
		counterList.add(new ResultCounter("MATERIAL_SOURCE_FILE"));
		counterList.add(new ResultCounter("CONFIRM_FILE"));

		while (true) {
			try {
				list = bsService.queryTBsCardStyleList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

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

			for (TBsCardStyle record : list) {
				// -------------------------------0:MICRO_PIC-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getMicroPic();
				record.setMicroPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:SOURCE_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getSourceFile();
				record.setSourceFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------2:MATERIAL_MICRO_PIC-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getMaterialMicroPic();
				record.setMaterialMicroPic(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------3:MATERIAL_SOURCE_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getMaterialSourceFile();
				record.setMaterialSourceFile(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------4:CONFIRM_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getConfirmFile();
				record.setConfirmFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsCardStyle(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsEntRealnameAuth(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsEntRealnameAuth> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("LRC_FACE_PIC"));
		counterList.add(new ResultCounter("LRC_BACK_PIC"));
		counterList.add(new ResultCounter("LICENSE_PIC"));
		counterList.add(new ResultCounter("ORG_PIC"));
		counterList.add(new ResultCounter("TAX_PIC"));

		while (true) {
			try {
				list = bsService.queryTBsEntRealnameAuthList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

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

			for (TBsEntRealnameAuth record : list) {
				// -------------------------------0:LRC_FACE_PIC-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getLrcFacePic();
				record.setLrcFacePic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:LRC_BACK_PIC-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getLrcBackPic();
				record.setLrcBackPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------2:LICENSE_PIC-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getLicensePic();
				record.setLicensePic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------3:ORG_PIC-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getOrgPic();
				record.setOrgPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------4:TAX_PIC-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getTaxPic();
				record.setTaxPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsEntRealnameAuth(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsPerRealnameAuth(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsPerRealnameAuth> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("CERPICA"));
		counterList.add(new ResultCounter("CERPICB"));
		counterList.add(new ResultCounter("CERPICH"));

		while (true) {
			try {
				list = bsService.queryTBsPerRealnameAuthList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

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

			for (TBsPerRealnameAuth record : list) {
				// -------------------------------0:CERPICA-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getCerpica();
				record.setCerpica(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:CERPICB-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getCerpicb();
				record.setCerpicb(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------2:CERPICH-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getCerpich();
				record.setCerpich(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsPerRealnameAuth(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsPointgame(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsPointgame> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("BIZ_APPLY_FILE"));

		while (true) {
			try {
				list = bsService.queryTBsPointgameList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

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

			for (TBsPointgame record : list) {

				// -------------------------------0:BIZ_APPLY_FILE-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getBizApplyFile();
				record.setBizApplyFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsPointgame(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsCompanyStop(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsCompanyStop> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("BIZ_APPLY_FILE"));
		counterList.add(new ResultCounter("REPORT_FILE"));
		counterList.add(new ResultCounter("STATEMENT_FILE"));
		counterList.add(new ResultCounter("OTHER_FILE"));

		while (true) {
			try {
				list = bsService.queryTBsCompanyStopList(start, (start
						+ PAGE_SIZE - 1));
			} catch (Exception e) {
				e.printStackTrace();

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

			for (TBsCompanyStop record : list) {
				// -------------------------------0:BIZ_APPLY_FILE-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getBizApplyFile();
				record.setBizApplyFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:REPORT_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getReportFile();
				record.setReportFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------2:STATEMENT_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getStatementFile();
				record.setStatementFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------3:OTHER_FILE-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getOtherFile();
				record.setOtherFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsCompanyStop(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}
}
