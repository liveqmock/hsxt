package com.gy.hsi.fs.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.bean.ResultCounter;
import com.gy.hsi.fs.common.constant.Constant;
import com.gy.hsi.fs.common.constant.TotalContant;
import com.gy.hsi.fs.common.exception.HsDuplicatedException;
import com.gy.hsi.fs.common.exception.HsHasHandledException;
import com.gy.hsi.fs.common.exception.HsIllegalFileIdException;
import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.factory.BeansFactory;
import com.gy.hsi.fs.service.IFSDataTransferService;

public class PrintFileIdUtils {

	private static final Logger logger = Logger.getLogger(PrintFileIdUtils.class);
	
	private static BeansFactory beanFactory = SpringContextLoader
			.getBeansFactory();
	
	private static IFSDataTransferService fsService = beanFactory
			.getFsDataTransferService();

	public static void printFinishLog(String tableName, int totalRows,
			List<ResultCounter> counterList, List<String> statisticResultList) {
		
		if ((null == counterList) || (0 >= counterList.size())) {
			logger.info("处理完毕, 表" + tableName + "没有查询到任何需要处理的处理。");

			return;
		}

		List<String> statisticList = new ArrayList<String>();

		int successRows = 0;
		int failedRows = 0;
		int nullRows = 0;
		int duplicatedRows = 0;

		int total = 0;
		int totalSuccessRows = 0;
		int totalFailedRows = 0;
		int totalNullRows = 0;
		int totalDuplicatedRows = 0;

		for (ResultCounter r : counterList) {
			if (null == r) {
				continue;
			}

			successRows = r.getSuccessRows();
			failedRows = r.getFailedRows();
			duplicatedRows = r.getDuplicatedRows();
			nullRows = totalRows - successRows - failedRows - duplicatedRows;

			if (0 >= nullRows) {
				nullRows = 0;
			}

			total += totalRows;

			totalSuccessRows += successRows;
			totalFailedRows += failedRows;
			totalNullRows += nullRows;
			totalDuplicatedRows += duplicatedRows;

			String msg = "字段" + r.getFieldName() + "：total=" + totalRows
					+ ", success=" + successRows + ", failed=" + failedRows
					+ ", nullValue=" + nullRows;

			logger.info("处理完毕, 表" + tableName + "的" + msg);

			statisticList.add("- " + msg);
		}

		statisticList.add(0, "");
		statisticList.add(1, "表" + tableName + "(total=" + total + ", success="
				+ totalSuccessRows + ", failed=" + totalFailedRows
				+ ", nullValue=" + totalNullRows + ")");

		statisticResultList.addAll(statisticList);

		TotalContant.total += total;
		TotalContant.totalSuccessRows += totalSuccessRows;
		TotalContant.totalFailedRows += totalFailedRows;
		TotalContant.totalNullRows += totalNullRows;
		TotalContant.totalDuplicatedRows += totalDuplicatedRows;
	}
	
	/**
	 * 将文件id保存到FS数据库中
	 * 
	 * @param tfsFileId
	 * @param counterList
	 * @param $$$$FIELDS_NAME_INDEX
	 * @return
	 */
	public static String saveFileId2FS(String tfsFileId,
			List<ResultCounter> counterList, int $$$$FIELDS_NAME_INDEX) {
		return doSaveFileId2FS(tfsFileId, counterList, $$$$FIELDS_NAME_INDEX,
				false);
	}

	/**
	 * 将文件id保存到FS数据库中(用户中心头像)
	 * 
	 * @param tfsFileId
	 * @param counterList
	 * @param $$$$FIELDS_NAME_INDEX
	 * @return
	 */
	public static String saveFileId2FSForUCHeadshot(String tfsFileId,
			List<ResultCounter> counterList, int $$$$FIELDS_NAME_INDEX) {
		return doSaveFileId2FS(tfsFileId, counterList, $$$$FIELDS_NAME_INDEX,
				true);
	}
	
	/**
	 * 将文件id保存到FS数据库中
	 * 
	 * @param tfsFileId
	 * @param counterList
	 * @param $$$$FIELDS_NAME_INDEX
	 * @return
	 */
	private static String doSaveFileId2FS(String tfsFileId,
			List<ResultCounter> counterList, int $$$$FIELDS_NAME_INDEX,
			boolean isUCHeadshot) {

		String fileId = null;

		try {
			fileId = fsService.insert2FsDb(tfsFileId, isUCHeadshot);

			if (Constant.NO_FILE.equals(fileId) || StringUtils.isEmpty(fileId)) {
				logger.info("no file or empty file id, fileId=" + fileId);

				return fileId;
			}

			if (!StringUtils.isEmpty(fileId)) {
				counterList.get($$$$FIELDS_NAME_INDEX).addSuccessRows();
				logger.info("fileId success1, fileid=" + fileId + ", src tfsFileId="+tfsFileId);
			}
		} catch (HsHasHandledException ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addSuccessRows();
			
			logger.info("fileId success2, fileid=" + fileId + ", src tfsFileId="+tfsFileId);

			logger.info("字段:"
					+ counterList.get($$$$FIELDS_NAME_INDEX).getFieldName()
					+ " -- >" + ex.getMessage());
			
			// 已经处理过的 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			return ex.getFileId();
		} catch (HsDuplicatedException ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addDuplicatedRows();
			
			logger.info("字段:"
					+ counterList.get($$$$FIELDS_NAME_INDEX).getFieldName()
					+ " -- > [fileId duplicated] " + ex.getMessage());
			
			// 重复文件id的 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			return ex.getFileId();
		} catch (HsIllegalFileIdException ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();
			
			logger.info("字段:"
					+ counterList.get($$$$FIELDS_NAME_INDEX).getFieldName()
					+ " -- > [fileId failed] " + ex.getMessage());
			
			// 对于非法文件名或者文件不存在, 统一置为NO_FILE, marked by zhangysh
			return Constant.NO_FILE;
			
			// marked by zhangysh
			// return tfsFileId;
		} catch (Exception ex) {
			counterList.get($$$$FIELDS_NAME_INDEX).addFailedRows();

			logger.error("[fileId failed] 转换文件id时出错：tfsFileId=" + tfsFileId + " , 字段:"
					+ counterList.get($$$$FIELDS_NAME_INDEX).getFieldName(), ex);
		}

		return fileId;
	}
}
