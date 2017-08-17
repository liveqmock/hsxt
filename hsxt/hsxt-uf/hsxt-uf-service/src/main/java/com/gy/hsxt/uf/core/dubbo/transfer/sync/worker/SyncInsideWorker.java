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

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import com.gy.hsxt.uf.common.bean.DestPlatformAddress;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.utils.DateUtils;
import com.gy.hsxt.uf.common.utils.logger.Log2DbAdapter;
import com.gy.hsxt.uf.core.dubbo.transfer.PacketSecurityDog;
import com.gy.hsxt.uf.core.dubbo.transfer.abstracts.InsideWorker;
import com.gy.hsxt.uf.core.netty.NettyClient;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.sync.worker
 * 
 *  File Name       : SyncInsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 负责对来自内部子系统的的报文进行处理的工人类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SyncInsideWorker extends InsideWorker {

	public SyncInsideWorker() {
	}

	/**
	 * 处理来自内部子系统的报文
	 * 
	 * @param regionPacket
	 *            内部dubbo传递的报文对象
	 */
	@Override
	public PacketHandleResult handlePacket(RegionPacket regionPacket) {
		// 处理结果对象
		PacketHandleResult handleResult = new PacketHandleResult();

		// 组装跨地区平台报文结构
		SecureRegionPacket secureRegionPacket = null;

		try {
			// 报文头对象
			RegionPacketHeader header = regionPacket.getHeader();

			// 根据路由规则取得目标平台的ip和端口
			DestPlatformAddress destPlatformAddress = super
					.queryDestPlatformAddress(header);

			// 目标平台id
			String destPlatformId = destPlatformAddress.getPlatformId();

			// 如果属于自我回环式访问, 则直接返回错误
			if (PacketSecurityDog.getInstance().isSelfOverlap(destPlatformId)) {
				handleResult.putErrorInfo(UFResultCode.REQUEST_MODE_ERR,
						"该报文属于本地区平台内部报文, 不能通过UF转发本地内部请求报文, 请使用Dubbo直接发送！");

				return handleResult;
			}

			// 组装跨地区平台报文结构
			secureRegionPacket = super.assembleRegionPacket(regionPacket,
					destPlatformAddress);

			// 发送跨地区平台的同步消息
			handleResult = new NettyClient(destPlatformAddress)
					.sendRegionPacket(secureRegionPacket);
		} catch (HsException e) {
			handleResult.putErrorInfo(e.getErrorCode(), e.getMessage(), e);
		} catch (Exception e) {
			handleResult.putErrorInfo(UFResultCode.SYS_INNERNAL_ERR,
					"UF综合前置内部处理异常, 请联系相关研发人员处理!", e);
		} finally {
			if (null != handleResult.getCause()) {
				logger.error(handleResult.getCause());
			}

			// 消耗时间
			long elapsedMillsTime = DateUtils.getTimeDiff(regionPacket
					.getHeader().getSendDateTime(), new Date());

			// 为了记录日志
			if (null == secureRegionPacket) {
				secureRegionPacket = super.assembleRegionPacket(regionPacket,
						null);
			}

			// 异步记录发送日志
			Log2DbAdapter.recordSendingLog(secureRegionPacket, handleResult,
					elapsedMillsTime);
		}

		// 返回处理结果
		return handleResult;
	}
}
