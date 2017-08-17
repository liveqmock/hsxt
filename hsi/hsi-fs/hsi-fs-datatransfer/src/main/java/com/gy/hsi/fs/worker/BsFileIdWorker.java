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
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebit;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkman;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsResetTranpwdapply;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethod;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChange;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProduct;
import com.gy.hsi.fs.service.IBSDataTransferService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.worker.bs
 * 
 *  File Name       : BsWorker.java
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
public class BsFileIdWorker {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(BsFileIdWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static final int PAGE_SIZE = 500;
	private static final int MAX_FAILED_TIMES = 3;
	
	private static final List<String> statisticResultList = new ArrayList<String>();

	public static List<String> doIt() {
		System.out.println();
		System.out
				.println("======================BS业务服务数据库表处理=========================");

		logger.info("======================BS业务服务数据库表处理=========================");

		try {
			handleTBsDeclareCorpAptitude("T_BS_DECLARE_CORP_APTITUDE");
		} catch (Exception e) {
			logger.error("处理T_BS_DECLARE_CORP_APTITUDE时出错：", e);
		}

		try {
			handleTBsDeclareEntLinkman("T_BS_DECLARE_ENT_LINKMAN");
		} catch (Exception e) {
			logger.error("处理T_BS_DECLARE_ENT_LINKMAN时出错：", e);
		}

		try {
			handleTBsEntChangeInfoApp("T_BS_ENT_CHANGE_INFO_APP");
		} catch (Exception e) {
			logger.error("处理T_BS_ENT_CHANGE_INFO_APP时出错：", e);
		}

		try {
			handleTBsPerChangeInfoApp("T_BS_PER_CHANGE_INFO_APP");
		} catch (Exception e) {
			logger.error("处理T_BS_PER_CHANGE_INFO_APP时出错：", e);
		}

		try {
			handleTBsFilingAptitude("T_BS_FILING_APTITUDE");
		} catch (Exception e) {
			logger.error("处理T_BS_FILING_APTITUDE时出错：", e);
		}

		try {
			handleTBsTaxrateChange("T_BS_TAXRATE_CHANGE");
		} catch (Exception e) {
			logger.error("处理T_BS_TAXRATE_CHANGE时出错：", e);
		}

		try {
			handleTBsShippingMethod("T_BS_SHIPPING_METHOD");
		} catch (Exception e) {
			logger.error("处理T_BS_SHIPPING_METHOD时出错：", e);
		}

		try {
			handleTBsDebit("T_BS_DEBIT");
		} catch (Exception e) {
			logger.error("处理T_BS_DEBIT时出错：", e);
		}

		try {
			handleTBsResetTranpwdapply("T_BS_RESET_TRANPWDAPPLY");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("处理T_BS_RESET_TRANPWDAPPLY时出错：", e);
		}

		try {
			handleTBsToolProduct("T_BS_TOOL_PRODUCT");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("处理T_BS_TOOL_PRODUCT时出错：", e);
		}

		// BS其他表的处理
		statisticResultList.addAll(BsFileId2Worker.doIt());
		
		// BS其他表的处理, 后补充!!!!!!!!!!!!!!!
		statisticResultList.addAll(BsFileId3Worker.doIt());

		return statisticResultList;
	}

	private static void handleTBsDeclareCorpAptitude(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsDeclareCorpAptitude> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("FILE_ID"));

		while (true) {
			try {
				list = bsService.queryTBsDeclareCorpAptitudeList(start, (start
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

			for (TBsDeclareCorpAptitude record : list) {
				// -------------------------------0:FILE_ID-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getFileId();
				record.setFileId(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsDeclareCorpAptitude(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsDeclareEntLinkman(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsDeclareEntLinkman> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("CERTIFICATE_FILE"));

		while (true) {
			try {
				list = bsService.queryTBsDeclareEntLinkmanList(start, (start
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

			for (TBsDeclareEntLinkman record : list) {
				// -------------------------------0:FILE_ID-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getCertificateFile();
				record.setCertificateFile(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsDeclareEntLinkman(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsEntChangeInfoApp(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsEntChangeInfoApp> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("IMPTAPP_PIC"));

		while (true) {
			try {
				list = bsService.queryTBsEntChangeInfoAppList(start, (start
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

			for (TBsEntChangeInfoApp record : list) {
				// -------------------------------0:IMPTAPP_PIC-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getImptappPic();
				record.setImptappPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsEntChangeInfoApp(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsFilingAptitude(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsFilingAptitude> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("FILE_ID"));

		while (true) {
			try {
				list = bsService.queryTBsFilingAptitudeList(start, (start
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

			for (TBsFilingAptitude record : list) {
				// -------------------------------0:FILE_ID-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getFileId();
				record.setFileId(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsFilingAptitude(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsPerChangeInfoApp(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsPerChangeInfoApp> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("RESIDENCE_ADDR_PIC"));

		while (true) {
			try {
				list = bsService.queryTBsPerChangeInfoAppList(start, (start
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

			for (TBsPerChangeInfoApp record : list) {
				// -------------------------------0:RESIDENCE_ADDR_PIC-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getResidenceAddrPic();
				record.setResidenceAddrPic(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsPerChangeInfoApp(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsShippingMethod(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsShippingMethod> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("ICO"));

		while (true) {
			try {
				list = bsService.queryTBsShippingMethodList(start, (start
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

			for (TBsShippingMethod record : list) {
				// -------------------------------0:ICO-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getIco();
				record.setIco(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsShippingMethod(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsTaxrateChange(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsTaxrateChange> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("PROVE_MATERIAL_FILE"));

		while (true) {
			try {
				list = bsService.queryTBsTaxrateChangeList(start, (start
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

			for (TBsTaxrateChange record : list) {
				// -------------------------------0:PROVE_MATERIAL_FILE-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getProveMaterialFile();
				record.setProveMaterialFile(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsTaxrateChange(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsDebit(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsDebit> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("FILE_ID"));

		while (true) {
			try {
				list = bsService.queryTBsDebitList(start,
						(start + PAGE_SIZE - 1));
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

			for (TBsDebit record : list) {
				// -------------------------------0:FILE_ID-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getFileId();
				record.setFileId(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsDebit(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsResetTranpwdapply(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsResetTranpwdapply> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("APPLY_PATH"));

		while (true) {
			try {
				list = bsService.queryTBsResetTranpwdapplyList(start, (start
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

			for (TBsResetTranpwdapply record : list) {
				// -------------------------------0:APPLY_PATH-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getApplyPath();
				record.setApplyPath(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsResetTranpwdapply(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTBsToolProduct(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TBsToolProduct> list = null;

		IBSDataTransferService bsService = beanFactory
				.getBsDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("MICRO_PIC"));

		while (true) {
			try {
				list = bsService.queryTBsToolProductList(start, (start
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

			for (TBsToolProduct record : list) {
				// -------------------------------0:MICRO_PIC-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getMicroPic();
				record.setMicroPic(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				bsService.updateTBsToolProduct(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}
}
