/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.parent;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.gp.common.constant.Constant;
import com.gy.hsxt.gp.common.constant.Constant.FileStatus;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.common.utils.StringUtils;
import com.gy.hsxt.gp.constant.GPConstant.GPErrorCode;
import com.gy.hsxt.gp.mapper.TGpCheckAccountsFileMapper;
import com.gy.hsxt.gp.mapper.TGpPaymentOrderMapper;
import com.gy.hsxt.gp.mapper.vo.TGpCheckAccountsFile;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl.parent
 * 
 *  File Name       : ParentAccountingFileDSBatchService.java
 * 
 *  Creation Date   : 2016年2月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : GP支付文件生成的父类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class ParentAccountingFileDSBatchService implements
		IDSBatchService {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	protected IDSBatchServiceListener listener;

	@Autowired
	protected TGpCheckAccountsFileMapper checkAccountsFileMapper;

	@Autowired
	protected TGpPaymentOrderMapper paymentOrderMapper;
	
	/**
	 * 创建对账文件
	 * 
	 * @param balanceDate
	 *            前一天日期, 格式：yyyyMMdd
	 * @throws Exception
	 */
	protected abstract void createBalanceFile(String balanceDate)
			throws Exception;

	/**
	 * 获取CHK文件配置的key
	 * 
	 * @return
	 */
	protected abstract String getTcChkFileNameKey();

	/**
	 * 获取DET文件配置的key
	 * 
	 * @return
	 */
	protected abstract String getTcDetFileNameKey();

	/**
	 * DS批量调度
	 * 
	 * @param executeId
	 * @param args
	 * @return
	 */
	@Override
	public boolean excuteBatch(String executeId, Map<String, String> args) {

		logger.info("支付系统：执行中, 对账文件正在生成...");

		// 发送进度通知
		listener.reportStatus(executeId, DSTaskStatus.HANDLING,
				"支付系统：执行中, 对账文件正在生成...");

		try {
			// 获取DS调度传递的日期
			String balanceDate = generateBalanceDate(args);

			// 开始生成对账文件
			this.createBalanceFile(balanceDate);

			logger.info("支付系统：执行成功, 对账文件成功生成.");
		} catch (Exception e) {
			logger.info("支付系统：执行失败!!!! 对账文件生成失败!", e);

			// 发送进度通知
			listener.reportStatus(executeId, DSTaskStatus.FAILED, "支付系统：执行失败!!!!  "
					+ e.getMessage());

			return false;
		}
		
		// 发送进度通知
		listener.reportStatus(executeId, DSTaskStatus.SUCCESS,
							"支付系统：执行成功, 对账文件成功生成.");

		return true;
	}

	/**
	 * 检测对账文件是否正在生成中
	 * 
	 * @param tcFileNamePrefix 形如:GP_AO_PAY_20160228
	 * @return
	 */
	protected boolean checkAccFileStatus(String tcFileNamePrefix) {
		// 查询“对账文件信息表”，判断文件状态
		TGpCheckAccountsFile chkAccFile = checkAccountsFileMapper
				.selectByFileName(tcFileNamePrefix);

		if (null == chkAccFile) {
			return true;
		}

		if ((FileStatus.CREATING == chkAccFile.getFileStatus())
				|| (FileStatus.READY == chkAccFile.getFileStatus())) {
			long timeDiff = System.currentTimeMillis()
					- chkAccFile.getUpdatedDate().getTime();

			if (10 * 60 * 1000 > timeDiff) {
				throw new HsException(GPErrorCode.BE_HANDLING,
						"对账文件正在创建中, 不能频繁请求, 请10分钟后再试！");
			}
		}

		return true;
	}

	/**
	 * 向表T_GP_CHECK_ACCOUNTS_FILE中插入对账文件信息
	 * 
	 * @param balanceDate
	 * @param checkType
	 * @param tcFileNamePrefix
	 *            形如:GP_AO_PAY_20160228
	 */
	protected void insertAccFileInfo2DB(String balanceDate,
			Integer checkType, String tcFileNamePrefix) {
		// 清除历史垃圾文件
		try {
			deleteTcFileByDate(balanceDate, tcFileNamePrefix);
		} catch (Exception e) {
			logger.info("删除对账文件历史数据失败：", e);
		}

		String tcNfsShareRootDir = format2Placeholder(
				TcConfigConsts.KEY_SYS_TC_NFS_SHARE_DIR,
				TcConfigConsts.$YYYYMMDD, balanceDate);

		String sysInstanceNo = HsPropertiesConfigurer
				.getProperty(ConfigConst.SYSTEM_INSTANCE_NO);

		// 记录“对账文件信息表”文件状态为“待创建”
		TGpCheckAccountsFile checkAccFile = new TGpCheckAccountsFile();
		checkAccFile.setFileName(tcFileNamePrefix);
		checkAccFile.setFileStatus(FileStatus.READY);
		checkAccFile.setFileSavePath(tcNfsShareRootDir);

		checkAccFile.setCheckType(checkType);
		checkAccFile.setCheckDate(DateUtils.stringToDate(balanceDate,
				"yyyyMMdd"));
		checkAccFile.setOwnerNode(sysInstanceNo);
		checkAccFile.setCreatedDate(DateUtils.getCurrentDate());
		checkAccFile.setUpdatedDate(DateUtils.getCurrentDate());

		checkAccountsFileMapper.insert(checkAccFile);
	}
	
	/**
	 * 更新对账文件信息到DB
	 * 
	 * @param tcFileNamePrefix
	 * @param fileStatus
	 */
	protected void updateAccFileInfoByFileName(String tcFileNamePrefix,
			Integer fileStatus) {

		TGpCheckAccountsFile file = new TGpCheckAccountsFile();
		file.setFileName(tcFileNamePrefix);
		file.setFileStatus(fileStatus);

		try {
			checkAccountsFileMapper.updateByFileName(file);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 计算页数
	 * 
	 * @param sumCounts
	 * @return
	 */
	protected int calculatePageSize(int sumCounts, int rowsPerPage) {

		double pageSize = (sumCounts * 1.0) / rowsPerPage;

		return (int) Math.ceil(pageSize);
	}

	/**
	 * 使用替换符替换掉占位符
	 * 
	 * @param configKey
	 * @param placeholders
	 * @return
	 */
	protected String format2Placeholder(String configKey,
			String... placeholders) {

		String configValue = HsPropertiesConfigurer.getProperty(configKey);

		if (0 == placeholders.length % 2) {
			for (int i = 0; i < placeholders.length; i++) {
				if (0 < i % 2) {
					continue;
				}

				configValue = configValue.replace(placeholders[i],
						placeholders[i + 1]);
			}
		}

		return configValue;
	}

	/**
	 * 解析出文件名称前缀
	 * 
	 * @param tcFileName
	 * @return
	 */
	protected String parseFileNamePrefix(String tcFileName) {
		String result = "";

		Pattern p = Pattern.compile("^.*_\\d{8}_");
		Matcher m = p.matcher(tcFileName);

		while (m.find()) {
			result = m.group(0);

			break;
		}

		return result.replaceFirst("_$", "");
	}
	
	/**
	 * 获取NFS保存对账文件的根目录
	 * 
	 * @param balanceDate
	 * @return
	 */
	protected String getTcNfsShareRootDir(String balanceDate) {
		
		String tcNfsShareRootDir = format2Placeholder(
				TcConfigConsts.KEY_SYS_TC_NFS_SHARE_DIR,
				TcConfigConsts.$YYYYMMDD, balanceDate);
		
		return tcNfsShareRootDir;
	}
	
	/**
	 * 获取对账文件CHK文件名称
	 * 
	 * @param balanceDate
	 * @return
	 */
	protected String getTcChkFileName(String balanceDate) {

		// 对账文件CHK文件名称
		String tcChkFileName = format2Placeholder(getTcChkFileNameKey(),
				TcConfigConsts.$YYYYMMDD, balanceDate);

		return tcChkFileName;
	}
	
    /**
	 * 获取对账文件DET文件名称
	 * 
	 * @param balanceDate
	 * @param index
	 * @return
	 */
	protected String getTcDetFileName(String balanceDate, int index) {

		// 文件名：GP_AO_PAY_$YYYYMMDD_DET_$SN
		String tcDetFileName = format2Placeholder(
				getTcDetFileNameKey(), TcConfigConsts.$YYYYMMDD,
				balanceDate, TcConfigConsts.$SN, String.valueOf(index));

		return tcDetFileName;
	}

	/**
	 * 根据日期删除对账文件
	 * 
	 * @param balanceDate
	 * @param tcFileNamePrefix
	 *            形如: GP_AO_PAY_20160228
	 */
	private void deleteTcFileByDate(String balanceDate, String tcFileNamePrefix) {

		String tcNfsShareRootDir = format2Placeholder(
				TcConfigConsts.KEY_SYS_TC_NFS_SHARE_DIR,
				TcConfigConsts.$YYYYMMDD, balanceDate);

		File dir = new File(tcNfsShareRootDir);

		// 判断目标文件所在的目录是否存在
		if (!dir.exists() && !dir.mkdirs()) {
			logger.error("创建目录失败！！ tcNfsShareRootDir=" + tcNfsShareRootDir);
		}

		// 如果目标文件所在的目录存在，则强制删除之
		File[] files = dir.listFiles();

		if (null != files) {

			for (File oldfile : files) {
				if (!oldfile.getName().startsWith(tcFileNamePrefix)) {
					continue;
				}

				try {
					FileUtils.forceDelete(oldfile);
				} catch (IOException e) {
					logger.info("清理原始文件失败: file=" + oldfile.getAbsolutePath());
				}
			}
		}

		checkAccountsFileMapper.deleteByFileName(tcFileNamePrefix);
	}

	/**
	 * 取出对账时间, 首先取ACC_FILE_DATE, 如果为空则取BATCH_DATE, 最后如果还是空, 则当前日期
	 * 
	 * @param args
	 * @return
	 * @throws ParseException
	 */
	private String generateBalanceDate(Map<String, String> args)
			throws ParseException {
		// 获取DS调度传递的对账文件日期
		String accFileDate = StringUtils.avoidNull(args
				.get(Constant.ACC_FILE_DATE));

		if (!StringUtils.isEmpty(accFileDate)
				&& (accFileDate.matches("\\d{8,}"))) {
			return accFileDate;
		}

		// 获取DS调度传递的日期
		String balanceDate = DateUtils.getBeforeDate(
				args.get(Constant.BATCH_DATE), "yyyyMMdd");

		// 如果对账日期为空, 则默认为昨天
		if (!StringUtils.isEmpty(balanceDate)) {
			return balanceDate;
		}

		return DateUtils.getBeforeDate(new Date(), "yyyyMMdd");
	}
}
