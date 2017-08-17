/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.sync.worker;

import java.util.Date;

import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacketHeader;
import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.dubbo.DubboServiceSupport;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.DateUtils;
import com.gy.hsxt.uf.common.utils.logger.Log2DbAdapter;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.OutsideWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.sync.worker
 * 
 *  File Name       : SyncOutsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 负责对来自外部子系统的的报文进行处理的工人类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SyncOutsideWorker extends OutsideWorker {
	/**
	 * 处理来自外部地区平台的同步报文
	 */
	@Override
	public PacketHandleResult handlePacket(SecureRegionPacket regionPacket) {
		PacketHandleResult handleResult = new PacketHandleResult();
		SecureRegionPacketHeader header = regionPacket.getHeader();

		// 记录当前时间点
		Date beginTime = new Date();
		
		// 对于子系统的响应对象
		Object respData = null;

		try {
			// 回声测试标志
			boolean isEchoTest = Constant.UF_ECHO_TEST.equals(header
					.getObligateArgsValue(Constant.UF_ECHO_TEST));
			
			// 正常请求
			if(!isEchoTest) {
				// 将接收到的安全报文SecureRegionPacket抽取成内部报文数据对象
				RegionPacketData regionPacketData = this
						.extractInnerPacketData(regionPacket);

				// 目标业务系统id
				String destSubsysId = header.getDestSubsysId();

				// 转发请求到“业务分发系统”
				IUFRegionPacketDataService service = (IUFRegionPacketDataService) DubboServiceSupport
						.getInstance().getDubboServiceForSending(
								IUFRegionPacketDataService.class,
								destSubsysId, null);

				// 将外部来源的报文转发给对应的内部业务子系统
				respData = service.handleReceived(regionPacketData);
			}
			// 回声测试
			else {
				respData = this.handleEchoTest();
			}

			handleResult.setResultData(respData);
		} catch (Exception e) {
			String errorDesc = "目标平台综合前置UF通过dubbo转发接收到的跨地区报文数据时发生异常, 原因:"
					+ e.getMessage();

			logger.error(errorDesc, e);

			handleResult.putErrorInfo(UFResultCode.DEST_PLATFORM_HANDLE_ERR,
					errorDesc, e);
		}

		// 消耗时间
		long elapsedMillsTime = DateUtils.getTimeDiff(beginTime, new Date());

		// 异步记录报文接收流水日志
		Log2DbAdapter.recordReceivingLog(regionPacket, handleResult,
				elapsedMillsTime);

		return handleResult;
	}

	/**
	 * 将接收到的安全报文SecureRegionPacket抽取成内部报文数据对象
	 * 
	 * @param regionPacket
	 * @return
	 */
	private RegionPacketData extractInnerPacketData(
			SecureRegionPacket regionPacket) {
		// 安全数据报文头
		SecureRegionPacketHeader header = regionPacket.getHeader();

		// 安全数据报文体内容
		Object bodyContent = regionPacket.getBody().getBodyContent();

		// 跨地区平台数据对象
		RegionPacketData regionPacketData = RegionPacketData.build(bodyContent);

		regionPacketData.getHeader().setPacketId(header.getPacketId())
				.setSrcPlatformId(header.getSrcPlatformId())
				.setSrcSubsysId(header.getSrcSubsysId())
				.setBizCode(header.getDestBizCode())
				.addObligateArgs(header.copyObligateArgs());

		return regionPacketData;
	}

	/**
	 * 处理回声测试
	 * 
	 * @return
	 */
	private String handleEchoTest() {
		// 当前平台id
		String platformId = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_ID);
		
		StringBuilder respBuf = new StringBuilder();
		respBuf.append("Pong <<---- response from ");
		respBuf.append("[platformId: ").append(platformId).append("].");

		return respBuf.toString();
	}
}
