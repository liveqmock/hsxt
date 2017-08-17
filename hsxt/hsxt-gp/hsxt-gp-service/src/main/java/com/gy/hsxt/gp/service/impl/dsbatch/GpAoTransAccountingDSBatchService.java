/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.service.impl.dsbatch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.gp.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.gp.common.utils.DateUtils;
import com.gy.hsxt.gp.mapper.vo.GPBSTransBalanceVo;
import com.gy.hsxt.gp.service.impl.parent.ParentAccountingFileDSBatchService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.impl
 * 
 *  File Name       : GpAoTransAccountingDSBatchService.java
 * 
 *  Creation Date   : 2016年2月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : GP支付系统与AO对账文件生成调度接口(提供给DS系统调用), 因为字段跟BS完全一致,所以直接继承GpBs的类,
 *  
 *                    (货币转银行交易对账)
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("gpAoTransAccountingDSBatchService")
public class GpAoTransAccountingDSBatchService extends
		ParentAccountingFileDSBatchService {

	/**
	 * 获取子系统标识
	 * 
	 * @return
	 */
	protected String getSrcSubsysId() {
		return "AO";
	}

	/**
	 * 获取CHK文件配置的key
	 * 
	 * @return
	 */
	protected String getTcChkFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_CHK_GP_AO_TRANS;
	}

	/**
	 * 获取DET文件配置的key
	 * 
	 * @return
	 */
	protected String getTcDetFileNameKey() {
		return TcConfigConsts.KEY_SYS_TC_DET_GP_AO_TRANS;
	}

	/**
	 * 创建对账文件
	 * 
	 * @param balanceDate
	 *            前一天日期
	 * @throws Exception
	 */
	@Override
	protected void createBalanceFile(String balanceDate) throws Exception {

//		// 对账文件CHK文件名称
//		String tcChkFileName = getTcChkFileName(balanceDate);
//
//		// 形如: GP_BS_PAY_20160228
//		String tcFileNamePrefix = parseFileNamePrefix(tcChkFileName);
//
//		// 检测对账文件是否正在生成中
//		this.checkAccFileStatus(tcFileNamePrefix);
//
//		// 向表T_GP_CHECK_ACCOUNTS_FILE中插入对账文件信息
//		this.insertAccFileInfo2DB(balanceDate, GPCheckType.PAYMENT,
//				tcFileNamePrefix);
//
//		// 处理生成对账文件逻辑
//		this.actionCreateBalanceFile(balanceDate, tcFileNamePrefix);
	}

	/**
	 * 根据订单日期查询
	 * 
	 * @param orderDate
	 * @param fromIndex
	 * @param count
	 * @param srcSubsysId
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<GPBSTransBalanceVo> queryOrderDatasByOrderDate(String orderDate,
			int fromIndex, int count, String srcSubsysId) {
//		Date date = DateUtils.stringToDate(orderDate, "yyyyMMdd");
//
//		String startDate = DateUtils.dateToString(date, "yyyy-MM-dd 00:00:00");
//		String endDate = DateUtils.dateToString(date, "yyyy-MM-dd 23:59:59");
//
//		Map<String, Object> parasMap = new HashMap<String, Object>();
//
//		parasMap.put("startDate", startDate);
//		parasMap.put("endDate", endDate);
//		parasMap.put("fromIndex", fromIndex);
//		parasMap.put("limitCount", count);
//		parasMap.put("srcSubsysId", srcSubsysId);
//
//		List<GPBSTransBalanceVo> paymentOrders = paymentOrderMapper
//				.selectByOrderDate(parasMap);
//
//		// 加入结尾提示标识
//		if (null != paymentOrders) {
//			paymentOrders.add(new GPBSTransBalanceVo(true));
//		}
//
//		return paymentOrders;
		
		return null;
	}

	/**
	 * 根据订单日期统计记录总数
	 * 
	 * @param orderDate
	 * @param srcSubsysId
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getCountByOrderDate(String orderDate, String srcSubsysId) {

		Date date = DateUtils.stringToDate(orderDate, "yyyyMMdd");

		String startDate = DateUtils.dateToString(date, "yyyy-MM-dd 00:00:00");
		String endDate = DateUtils.dateToString(date, "yyyy-MM-dd 23:59:59");

		Map<String, String> dateMap = new HashMap<String, String>();
		dateMap.put("startDate", startDate);
		dateMap.put("endDate", endDate);
		dateMap.put("srcSubsysId", srcSubsysId);

		return paymentOrderMapper.getcountByOrderDate$GPBS(dateMap);
	}

	/**
	 * 生成对账文件
	 * 
	 * @param balanceDate
	 * @param tcFileNamePrefix
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void actionCreateBalanceFile(String balanceDate,
			String tcFileNamePrefix) throws Exception {
//		// 来源子系统id
//		String srcSubsysId = getSrcSubsysId();
//
//		// NFS根目录
//		String tcNfsShareRootDir = getTcNfsShareRootDir(balanceDate);
//
//		// 对账文件CHK文件名称
//		String tcChkFileName = getTcChkFileName(balanceDate);
//
//		// 每次从数据库中查出的记录
//		List<GPBSTransBalanceVo> paymentOrders;
//
//		// CHK文件内容
//		List<String> chkFileContents = new ArrayList<String>(5);
//
//		try {
//			int totalCounts = getCountByOrderDate(balanceDate, srcSubsysId);
//			int rowsPerPage = HsPropertiesConfigurer
//					.getPropertyIntValue(TcConfigConsts.KEY_SYS_TC_BALANCE_COUNT);
//			int pageSize = calculatePageSize(totalCounts, rowsPerPage);
//
//			for (int i = 1; i <= pageSize; i++) {
//				// 分页从“支付表”中查询前一个交易日的交易记录
//				paymentOrders = queryOrderDatasByOrderDate(balanceDate, (i - 1)
//						* rowsPerPage, rowsPerPage, srcSubsysId);
//
//				// 更新对账文件状态：正在创建中...
//				updateAccFileInfoByFileName(tcFileNamePrefix,
//						FileStatus.CREATING);
//
//				// 文件名：GP_BS_PAY_$YYYYMMDD_DET_$SN
//				String tcDetFileName = getTcDetFileName(balanceDate, i);
//
//				// ---------------生成对账DET明细文件--------------------
//				FileUtils.writeLines(
//						new File(tcNfsShareRootDir + tcDetFileName),
//						Constant.ENCODE_UTF8, paymentOrders,
//						Constant.LINE_SEPARATOR);
//
//				// 添加记录到CHK中
//				chkFileContents.add(tcDetFileName + "|"
//						+ (paymentOrders.size() - 1));
//			}
//
//			// 生成对账CHK校验文件处理, CHK文件内容：文件名|数据记录总数
//			{
//				chkFileContents.add(0, String.valueOf(pageSize));
//				chkFileContents
//						.add(HsPropertiesConfigurer
//								.getProperty(TcConfigConsts.KEY_SYS_TC_BALANCE_DATA_END));
//
//				// ----------------生成对账CHK校验文件-------------------
//				FileUtils.writeLines(
//						new File(tcNfsShareRootDir + tcChkFileName),
//						Constant.ENCODE_UTF8, chkFileContents,
//						Constant.LINE_SEPARATOR);
//			}
//
//			// 更新对账文件状态：创建成功
//			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.SUCCESS);
//		} catch (Exception ex) {
//			// 更新对账文件状态：创建失败
//			updateAccFileInfoByFileName(tcFileNamePrefix, FileStatus.FAILED);
//
//			throw ex;
//		}
	}

}
