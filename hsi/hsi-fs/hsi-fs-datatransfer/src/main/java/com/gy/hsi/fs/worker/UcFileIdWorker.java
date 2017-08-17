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
import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtend;
import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfo;
import com.gy.hsi.fs.service.IUCDataTransferService;

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
public class UcFileIdWorker {
	private static final Logger logger = Logger.getLogger(UcFileIdWorker.class);

	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();

	private static int PAGE_SIZE = 500;
	private static int MAX_FAILED_TIMES = 3;
	
	private static final List<String> statisticResultList = new ArrayList<String>();

	public static List<String> doIt() {
		System.out.println();
		System.out
				.println("======================UC用户中心数据库表处理========================");

		logger.info("======================UC用户中心数据库表处理========================");

		try {
			handleTCustIdInfo("T_CUST_ID_INFO");
		} catch (Exception e) {
			logger.error("处理T_CUST_ID_INFO时出错：", e);
		}

		try {
			handleTEntExtend("T_ENT_EXTEND");
		} catch (Exception e) {
			logger.error("处理T_ENT_EXTEND时出错：", e);
		}

		try {
			handleTNetworkInfo("T_NETWORK_INFO");
		} catch (Exception e) {
			logger.error("处理T_NETWORK_INFO时出错：", e);
		}

		return statisticResultList;
	}

	private static void handleTCustIdInfo(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TCustIdInfo> list = null;

		IUCDataTransferService ucService = beanFactory
				.getUcDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("CERTIFICATE_FRONT"));
		counterList.add(new ResultCounter("CERTIFICATE_BACK"));
		counterList.add(new ResultCounter("CERTIFICATE_HANDHOLD"));

		while (true) {
			try {
				list = ucService.queryTCustIdInfoList(start,
						(start + PAGE_SIZE - 1));
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

			for (TCustIdInfo record : list) {
				// ---------------------------0:CERTIFICATE_FRONT-----------------------
				int $$$$FIELDS_NAME_INDEX = 0;

				String tfsFileId = record.getCertificateFront();
				record.setCertificateFront(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:CERTIFICATE_BACK-----------------------------
				$$$$FIELDS_NAME_INDEX++;

				tfsFileId = record.getCertificateBack();
				record.setCertificateBack(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// ----------------------------2:CERTIFICATE_HANDHOLD--------------------------
				$$$$FIELDS_NAME_INDEX++;

				tfsFileId = record.getCertificateHandhold();
				record.setCertificateHandhold(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				ucService.updateTCustIdInfo(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTEntExtend(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TEntExtend> list = null;

		IUCDataTransferService ucService = beanFactory
				.getUcDataTransferService();

		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("BUSI_LICENSE_IMG"));
		counterList.add(new ResultCounter("ORG_CODE_IMG"));
		counterList.add(new ResultCounter("TAX_REG_IMG"));
		counterList.add(new ResultCounter("LEGAL_PERSON_PIC_FRONT"));
		counterList.add(new ResultCounter("LEGAL_PERSON_PIC_BACK"));
		counterList.add(new ResultCounter("LOGO_IMG"));
		counterList.add(new ResultCounter("CONTACT_PROXY"));
		counterList.add(new ResultCounter("HELP_AGREEMENT"));
		counterList.add(new ResultCounter("ENT_REG_IMG"));
		counterList.add(new ResultCounter("BUSI_REG_IMG"));

		while (true) {
			try {
				list = ucService.queryTEntExtendList(start,
						(start + PAGE_SIZE - 1));
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

			for (TEntExtend record : list) {
				// -------------------------------0:BUSI_LICENSE_IMG-------------------------
				int $$$$FIELDS_NAME_INDEX = 0;
				String tfsFileId = record.getBusiLicenseImg();
				record.setBusiLicenseImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------1:ORG_CODE_IMG----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getOrgCodeImg();
				record.setOrgCodeImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------2:TAX_REG_IMG-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getTaxRegImg();
				record.setTaxRegImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------3:LEGAL_PERSON_PIC_FRONT-------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getLegalPersonPicFront();
				record.setLegalPersonPicFront(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------4:LEGAL_PERSON_PIC_BACK--------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getLegalPersonPicBack();
				record.setLegalPersonPicBack(PrintFileIdUtils.saveFileId2FS(
						tfsFileId, counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------5:LOGO_IMG-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getLogoImg();
				record.setLogoImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------6:CONTACT_PROXY---------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getContactProxy();
				record.setContactProxy(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------7:HELP_AGREEMENT--------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getHelpAgreement();
				record.setHelpAgreement(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------8:ENT_REG_IMG-----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getEntRegImg();
				record.setEntRegImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				// -------------------------------9:BUSI_REG_IMG----------------------------
				$$$$FIELDS_NAME_INDEX++;
				tfsFileId = record.getBusiRegImg();
				record.setBusiRegImg(PrintFileIdUtils.saveFileId2FS(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));

				ucService.updateTEntExtend(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}

	private static void handleTNetworkInfo(String tableName) {
		System.out.println("----->>开始修理表" + tableName + "......");
		logger.info("----->>开始修理表" + tableName + "......");

		int start = 1;
		int totalRows = 0;

		int queryFailedTimes = 0;

		List<TNetworkInfo> list = null;

		IUCDataTransferService ucService = beanFactory
				.getUcDataTransferService();

		// 头像特殊处理, 因为互生使用FS, 而电商使用TFS, 文件id不兼容
		List<ResultCounter> counterList = new ArrayList<ResultCounter>();
		counterList.add(new ResultCounter("HEAD_SHOT"));

		while (true) {
			try {
				list = ucService.queryTNetworkInfoList(start, (start
						+ PAGE_SIZE - 1));
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

			for (TNetworkInfo record : list) {
				// -------------------------------0:HEAD_SHOT-----------------------------
				int $$$$FIELDS_NAME_INDEX = 0;

				String tfsFileId = record.getHeadShot();

				record.setHeadShot(PrintFileIdUtils.saveFileId2FSForUCHeadshot(tfsFileId,
						counterList, $$$$FIELDS_NAME_INDEX));
				ucService.updateTNetworkInfo(record);
			}

			// 注意一定要放到末尾
			start += PAGE_SIZE;
		}
	}
}
