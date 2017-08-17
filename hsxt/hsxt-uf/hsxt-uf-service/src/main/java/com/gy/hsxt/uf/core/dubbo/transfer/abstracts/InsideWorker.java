/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer.abstracts;

import org.apache.log4j.Logger;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacket;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import com.gy.hsxt.uf.common.bean.DestPlatformAddress;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.spring.SpringContextLoader;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.factory.BeansFactory;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer.abstracts
 * 
 *  File Name       : InsideWorker.java
 * 
 *  Creation Date   : 2015-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自内部子系统的报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class InsideWorker {
	/** 记录日志对象 **/
	protected final Logger logger = Logger.getLogger(this.getClass());

	/** bean工厂 **/
	protected final BeansFactory beansFactory = SpringContextLoader
			.getBeansFactory();

	/**
	 * 构造函数
	 */
	public InsideWorker() {
	}

	/**
	 * 处理来自内部子系统的报文(即：dubbo报文)
	 * 
	 * @param regionPacket
	 * @return
	 */
	public abstract PacketHandleResult handlePacket(RegionPacket regionPacket);

	/**
	 * 到本地配置平台查询目标地区平台地址信息
	 * 
	 * @param header
	 * @return
	 */
	protected DestPlatformAddress queryDestPlatformAddress(
			RegionPacketHeader header) {
		// 调用LCS或GCS查询路由目标地址数据
		DestPlatformAddress routerAddress = this.fetchRouteTarget(header);

		if (null != routerAddress) {
			String ip = routerAddress.getIp();
			String port = routerAddress.getPort();
			String subsysId = routerAddress.getSubsysId();
			String platformId = routerAddress.getPlatformId();

			// 没有找到路由目标地址(ip and port)
			if (StringUtils.isExistEmpty(ip, port)) {
				throw new HsException(
						UFResultCode.DEST_ADDRESS_NOT_FOUND,
						"UF没有找到路由目标地址！ 可能原因:(1)报文头中传递的地区平台id或资源号不正确;"
								+ "(2)GCS全局配置系统中尚未配置对应的地区平台路由数据;(3)GCS与LCS之间数据同步发生异常;(4)UF内部其他错误;");
			}

			// 没有找到路由目标业务子系统id(只有在非回声测试时判断)
			if (!isEchoTest(header) && StringUtils.isEmpty(subsysId)) {
				throw new HsException(
						UFResultCode.DEST_SUBSYS_NOT_FOUND,
						"UF没有找到路由目标业务子系统id！ 可能原因:(1)报文头中传递的业务代码不正确;"
								+ "(2)GCS全局配置系统中尚未配置对应的业务代码数据;(3)GCS与LCS之间数据同步发生异常;(4)UF内部其他错误;");
			}

			return new DestPlatformAddress(ip, port, subsysId, platformId);
		}

		throw new HsException(UFResultCode.DEST_ADDRESS_NOT_FOUND,
				"UF没有找到路由目标地址！");
	}

	/**
	 * 组装跨地区平台报文结构
	 * 
	 * @param regionPacket
	 * @return
	 */
	protected SecureRegionPacket assembleRegionPacket(RegionPacket regionPacket) {
		return this.assembleRegionPacket(regionPacket, null);
	}

	/**
	 * 组装跨地区平台报文结构
	 * 
	 * @param regionPacket
	 * @param targetAddress
	 * @return
	 */
	protected SecureRegionPacket assembleRegionPacket(
			RegionPacket regionPacket, DestPlatformAddress targetAddress) {
		// 报文头
		RegionPacketHeader header = regionPacket.getHeader();

		// 报文体(真正要发送到的目标地区平台某个子系统的对象)
		RegionPacketBody body = regionPacket.getBody();

		// 消息体内容
		Object bodyContent = body.getBodyContent();

		// 源地区平台ID
		String srcPlatformId = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_ID);

		// 源子系统ID
		String srcSubsysId = header.getSrcSubsysId();

		// 目标地区平台ID
		String destPlatformId = "";

		// 目标子系统ID
		String destSubsysId = "";

		if (null != targetAddress) {
			destPlatformId = targetAddress.getPlatformId();
			destSubsysId = targetAddress.getSubsysId();
		} else {
			destPlatformId = header.getDestPlatformId();
		}

		// 组装报文结构
		SecureRegionPacket secureRegionPacket = SecureRegionPacket.build();

		secureRegionPacket.getHeader().setPacketId(header.getPacketId())
				.setSrcPlatformId(srcPlatformId).setSrcSubsysId(srcSubsysId)
				.setDestPlatformId(destPlatformId)
				.setDestSubsysId(destSubsysId)
				.setDestBizCode(header.getDestBizCode())
				.addObligateArgs(header.copyObligateArgs());
		secureRegionPacket.getBody().setBodyContent(bodyContent);

		return secureRegionPacket;
	}

	/**
	 * 获取路由目标地址
	 * 
	 * @param header
	 * @return
	 */
	private DestPlatformAddress fetchRouteTarget(RegionPacketHeader header) {
		// 如果是总平台, 需要做特殊处理!!!
		if (isCurrGlobalPlatform()) {
			return this.fetchGcsRouteTarget(header);
		}

		// 目标平台ID
		String destPlatformId = header.getDestPlatformId();

		// 资源号
		String destResNo = header.getDestResNo();

		// 业务代码
		String destBizCode = header.getDestBizCode();

		// 跨平台路由目标(LCS系统)
		com.gy.hsxt.lcs.bean.RouteTarget lcsRouteTarget = null;

		try {
			// =========================== 调用LCS服务 ==========================//
			// 如果目标平台ID不为空, 则直接根据destPlatformId查询出ip和端口号即可
			if (!StringUtils.isEmpty(destPlatformId)) {
				lcsRouteTarget = beansFactory.getLcsRouteRuleService()
						.getRouteTarget(destPlatformId, destBizCode);
			}
			// 否则, 根据资源号进行查询
			else if (!StringUtils.isEmpty(destResNo)) {
				lcsRouteTarget = beansFactory.getLcsRouteRuleService()
						.getRouteTargetByResNo(destResNo, destBizCode);
			}
		} catch (Exception ex) {
			logger.error(ex);

			throw new HsException(UFResultCode.DEST_ADDRESS_QRY_FAILED,
					"UF查询目标路由地址数据失败！请检查LCS本地配置系统服务是否正常! 可能原因:(1)LCS本地配置系统LcsRouteRuleService服务不可用;"
							+ "(2)dubbo服务调用方version配置错误;(3)UF内部其他错误;");
		}

		String ip = lcsRouteTarget.getTargetIP();
		String port = lcsRouteTarget.getTargetPort();
		String subsysId = lcsRouteTarget.getTargetSubsys();
		String platformId = lcsRouteTarget.getPlatNo();

		return new DestPlatformAddress(ip, port, subsysId, platformId);
	}

	/**
	 * 获取GCS路由目标地址
	 * 
	 * @param header
	 * @return
	 */
	private DestPlatformAddress fetchGcsRouteTarget(RegionPacketHeader header) {
		// 目标平台ID
		String destPlatformId = header.getDestPlatformId();

		// 资源号
		String destResNo = header.getDestResNo();

		// 业务代码
		String destBizCode = header.getDestBizCode();

		// 跨平台路由目标(GCS系统)
		com.gy.hsxt.gpf.gcs.bean.RouteTarget gcsRouteTarget = null;

		try {
			// =========================== 调用GCS服务 ==========================//
			// 如果目标平台ID不为空, 则直接根据destPlatformId查询出ip和端口号即可
			if (!StringUtils.isEmpty(destPlatformId)) {
				gcsRouteTarget = beansFactory.getGcsRouteRuleService()
						.getRouteTarget(destPlatformId, destBizCode);
			}
			// 否则, 根据资源号进行查询
			else if (!StringUtils.isEmpty(destResNo)) {
				gcsRouteTarget = beansFactory.getGcsRouteRuleService()
						.getRouteTargetByResNo(destResNo, destBizCode);
			}
		} catch (Exception ex) {
			logger.error(ex);

			throw new HsException(UFResultCode.DEST_ADDRESS_QRY_FAILED,
					"UF查询目标路由地址数据失败！请检查GCS全局配置系统服务是否正常! 可能原因:(1)GCS全局配置系统GcsRouteRuleService服务不可用;"
							+ "(2)dubbo服务调用方version配置错误;(3)UF内部其他错误;");
		}

		String ip = gcsRouteTarget.getTargetIP();
		String port = gcsRouteTarget.getTargetPort();
		String subsysId = gcsRouteTarget.getTargetSubsys();
		String platformId = gcsRouteTarget.getPlatNo();

		return new DestPlatformAddress(ip, port, subsysId, platformId);
	}

	/**
	 * 判断当前平台是否为'总平台'
	 * 
	 * @return
	 */
	private boolean isCurrGlobalPlatform() {
		// 当前平台ID
		String currPlatformId = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_ID);

		return Constant.GLOBAL_PLATFORM_ID.equals(currPlatformId);
	}

	/**
	 * 判断是否为回声测试
	 * 
	 * @param header
	 * @return
	 */
	private boolean isEchoTest(RegionPacketHeader header) {
		// 回声测试标志
		boolean isEchoTest = Constant.UF_ECHO_TEST.equals(header
				.getObligateArgsValue(Constant.UF_ECHO_TEST));

		return isEchoTest;
	}
}