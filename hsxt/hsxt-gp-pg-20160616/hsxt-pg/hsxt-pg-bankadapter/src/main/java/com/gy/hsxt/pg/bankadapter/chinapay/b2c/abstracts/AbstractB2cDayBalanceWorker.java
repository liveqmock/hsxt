/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.b2c.abstracts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.gy.hsxt.pg.bankadapter.chinapay.ChinapayOrderResultDTO;
import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayBalanceCallback;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.common.ChinapayB2cConst;
import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker
 * 
 *  File Name       : AbstractB2cDayBalanceWorker.java
 * 
 *  Creation Date   : 2014-12-19
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联"每日对账"工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractB2cDayBalanceWorker extends AbstractB2cWorker {

	private SecureLink secureLink;

	public AbstractB2cDayBalanceWorker() {
	}

	/**
	 * 将ChinapayBalanceRowItem对象转换为ChinapayOrderResultDTO对象
	 * 
	 * @param lineTxt
	 * @return
	 */
	protected abstract ChinapayOrderResultDTO changeLine2OrderResultDto(
			String lineTxt) throws Exception;

	/**
	 * 验证签名
	 * 
	 * @param lineTxt
	 * @return
	 */
	protected abstract boolean verifySignature(String lineTxt);

	/**
	 * 对账文件解析
	 * 
	 * @param localSavePaths
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public boolean doParsingBalanceFile(String localSavePath,
			IChinapayBalanceCallback callback) throws Exception {
		String[] localSavePaths = new String[] { localSavePath };

		return doParsingBalanceFile(Arrays.asList(localSavePaths), callback);
	}

	/**
	 * 对账文件解析
	 * 
	 * @param localSavePaths
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	public boolean doParsingBalanceFile(List<String> localSavePaths,
			IChinapayBalanceCallback callback) throws Exception {

		if ((null == localSavePaths) || (0 >= localSavePaths.size())) {
			callback.notifyFinished(0, 0); // 通知解析完成
			
			return true;
		}

		int currCounts = 0;

		int validTotalRows = 0;
		int validCurrPage = 0;

		String lineTxt;

		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;
		ChinapayOrderResultDTO orderItem = null;

		List<ChinapayOrderResultDTO> pageDatas = new ArrayList<ChinapayOrderResultDTO>(
				callback.getRowsPerPage());

		for (String localSavePath : localSavePaths) {
			try {
				int currRowIndex = 0;

				File balanceFile = new File(localSavePath);

				if (!balanceFile.exists() || !balanceFile.isFile()) {
					logger.info("对账失败，要处理的对账文件在本地不存在！  localSavePath="
							+ localSavePath);

					return false;
				}

				reader = new InputStreamReader(
						new FileInputStream(balanceFile), "utf-8");

				bufferedReader = new BufferedReader(reader);

				while ((lineTxt = bufferedReader.readLine()) != null) {
					currRowIndex++;

					// 略过第一行
					if (1 == currRowIndex) {
						continue;
					}

					orderItem = changeLine2OrderResultDto(lineTxt);

					if (!callback.isMyFocus(orderItem)) {
						continue;
					}

					pageDatas.add(orderItem);

					currCounts++;
					validTotalRows++;

					// 已经达到一页
					if (callback.getRowsPerPage() <= currCounts) {
						currCounts = 0;

						notifyPageDatas(callback, pageDatas, (++validCurrPage));
					}
				}
			} finally {
				closeStream(reader, bufferedReader);
			}
		}

		if (0 < currCounts) {
			notifyPageDatas(callback, pageDatas, (++validCurrPage));
		}

		// 通知解析完成
		callback.notifyFinished(validTotalRows, validCurrPage);

		return true;
	}

	/**
	 * 对账文件解析
	 * 
	 * @param localSavePath
	 * @return
	 * @throws Exception
	 */
	public boolean verifyBalanceFile(String localSavePath) {

		InputStreamReader reader = null;
		BufferedReader bufferedReader = null;

		String lineTxt;

		boolean verifySuccess = false;

		int currIndex = 0;
		int totalCounts = new java.util.Random().nextInt(10) + 5;

		try {
			File balanceFile = new File(localSavePath);

			reader = new InputStreamReader(new FileInputStream(balanceFile),
					"utf-8");

			bufferedReader = new BufferedReader(reader);

			bufferedReader.readLine();

			while ((lineTxt = bufferedReader.readLine()) != null) {
				currIndex++;

				if (currIndex >= totalCounts) {
					break;
				}

				verifySuccess = verifySignature(lineTxt);
			}
		} catch (Exception ex) {
		} finally {
			closeStream(reader, bufferedReader);
		}

		return verifySuccess;
	}

	/**
	 * 获取银联SecureLink安全链
	 * 
	 * @return
	 */
	protected SecureLink createSecureLink() {

		if (null == secureLink) {
			PrivateKey publicKey = new PrivateKey();

			if (!publicKey.buildKey(ChinapayB2cConst.PUB_MERID, 0,
					bankPubKeyPath)) {
				logger.error("createSecureLink: 中国银联公钥错误! mechantNo="
						+ mechantNo + " bankPubKeyPath =" + privateKeyFilePath);

				throw new BankAdapterException("处理中国银联对账文件时发生错误，原因：中国银联公钥错误!");
			}

			secureLink = new SecureLink(publicKey);
		}

		return secureLink;
	}

	/**
	 * 通知页数据
	 * 
	 * @param callback
	 * @param pageDatas
	 * @param currPage
	 * @throws Exception
	 */
	private void notifyPageDatas(IChinapayBalanceCallback callback,
			List<ChinapayOrderResultDTO> pageDatas, int currPage)
			throws Exception {

		int size = (null == pageDatas) ? 0 : pageDatas.size();

		logger.info("I am here DayBalanceWorker.notifyPageData size:" + size);

		try {
			callback.notifyPageData(pageDatas, currPage);
		} finally {
			if (null != pageDatas) {
				pageDatas.clear();
			}
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param readers
	 */
	private void closeStream(Reader... readers) {
		for (Reader reader : readers) {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
