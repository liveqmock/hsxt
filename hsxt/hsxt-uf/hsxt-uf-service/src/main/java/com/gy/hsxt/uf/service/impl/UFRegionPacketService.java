/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.service.impl;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.core.dubbo.transfer.sync.SyncPacketTransfer;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.service.impl
 * 
 *  File Name       : UFRegionPacketService.java
 * 
 *  Creation Date   : 2015年10月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置跨地区报文服务接口实现类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

@Service(value = "regionPacketService")
@Scope("singleton")
public class UFRegionPacketService implements IUFRegionPacketService {

	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody) {
		// 使用默认超时时间进行发送
		return this.sendSyncRegionPacket(packetHeader, packetBody,
				Constant.DEFAULT_SOCKET_CONNTIMEOUT);
	}

	@Override
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody, Integer timeoutMills) {
		PacketHandleResult handleResult = new PacketHandleResult();

		try {
			// 报文头校验
			this.checkHeader(packetHeader);

			// 报文体校验
			this.checkBody(packetBody);

			// 设置socket超时时间
			packetHeader.addObligateArgs(Constant.KEY_SOCKET_CONNTIMEOUT,
					timeoutMills);

			RegionPacket regionPacket = new RegionPacket(packetHeader,
					packetBody);

			// 转交给内部工作者处理
			handleResult = SyncPacketTransfer.getInstance()
					.handlePacketFromInside(regionPacket);
		} catch (HsException e) {
			handleResult = new PacketHandleResult(e);
		} catch (Exception e) {
			handleResult = new PacketHandleResult(
					UFResultCode.SYS_INNERNAL_ERR, "UF内部处理异常! 请找相关系统研发人员处理.", e);
		} finally {
			if (null != handleResult.getCause()) {
				logger.error("", handleResult.getCause());
			}

			// 为了节省流量, 网络上不传送这个比较重的异常对象
			handleResult.cleanUpCause();
		}

		return handleResult;
	}

	/**
	 * 校验报文头对象是否有效
	 * 
	 * @param header
	 * @throws HsException
	 */
	private void checkHeader(RegionPacketHeader header) throws HsException {
		if (null == header) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"空值异常, RegionPacketHeader对象不能为null !");
		}

		String destPlatformId = header.getDestPlatformId();
		String destResourceNo = header.getDestResNo();

		// 校验平台id和资源号
		if (StringUtils.isAllEmpty(destPlatformId, destResourceNo)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"报文头RegionPacketHeader对象的destPlatformId、destResNo两个属性中, 必须设置其中之一有值!");
		}

		// 校验资源号格式是否合法
		if (StringUtils.isEmpty(destPlatformId)
				&& !isValidResourceNo(destResourceNo)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"报文头RegionPacketHeader对象的destResNo属性值无效, 互生资源号必须为11位数字的组合 !");
		}

		String destBizCode = header.getDestBizCode();

		if (StringUtils.isEmpty(destBizCode)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"报文头RegionPacketHeader对象的destBizCode属性不能为空 !");
		} else if (64 < destBizCode.length()) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"报文头RegionPacketHeader对象的destBizCode属性值的最大长度不能超过64字符 !");
		}
	}

	/**
	 * 校验报文体对象是否有效
	 * 
	 * @param body
	 * @throws HsException
	 */
	private void checkBody(RegionPacketBody body) throws HsException {

		if (null == body) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"空值异常, RegionPacketBody对象不能为null !");
		}

		Object bodyContent = body.getBodyContent();

		if (StringUtils.isEmpty(bodyContent)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"RegionPacketBody对象中传递的报文体内容不能为null !");
		}
	}

	/**
	 * 判断是否为合法的资源号
	 * 
	 * @param resourceNo
	 * @return
	 */
	private boolean isValidResourceNo(String resourceNo) {
		String resourceNoRegex = "[0-9]{11}";

		if (!StringUtils.isEmpty(resourceNo)
				&& resourceNo.matches(resourceNoRegex)) {
			return true;
		}

		return false;
	}
}
