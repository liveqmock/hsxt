/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;
import com.gy.hsxt.uf.common.constant.UFConstant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.dubbo.DubboConfiger;
import com.gy.hsxt.uf.common.dubbo.DubboServiceSupport;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf
 * 
 *  File Name       : UFRegionPacketSupport.java
 * 
 *  Creation Date   : 2015年10月29日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置跨地区平台报文发送入口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UFRegionPacketSupport implements IUFRegionPacketService {

	/**
	 * 构造函数
	 * 
	 * @param regionPacketDataService
	 */
	public UFRegionPacketSupport(
			IUFRegionPacketDataService regionPacketDataService) {
		// 执行初始化
		this.init(regionPacketDataService);
	}

	@Override
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody) {
		return sendSyncRegionPacket(packetHeader, packetBody, null);
	}

	@Override
	public Object sendSyncRegionPacket(RegionPacketHeader packetHeader,
			RegionPacketBody packetBody, Integer timeoutMills) {
		if ((null == timeoutMills) || (0 >= timeoutMills)) {
			timeoutMills = DubboConfiger.getServTimeout30000();
		}

		// 处理结果对象
		PacketHandleResult handleResult = null;

		try {
			// 取得dubbo服务对象
			IUFRegionPacketService service = (IUFRegionPacketService) DubboServiceSupport
					.getInstance().getDubboServiceForSending(
							IUFRegionPacketService.class, null, timeoutMills);

			// 发送RPC请求
			handleResult = (PacketHandleResult) service.sendSyncRegionPacket(
					packetHeader, packetBody, timeoutMills);

			if (null == handleResult) {
				throw new HsException(UFResultCode.FAILED,
						"报文发送时发生异常, 响应的结果为空, 请检查网络是否正常!");
			}
		} catch (Exception e) {
			if (e instanceof HsException) {
				throw e;
			}

			if (e instanceof RpcException) {
				throw new HsException(UFResultCode.INNERNAL_COMM_ERR,
						UfUtils.getStackTraceInfo(e));
			}

			throw new HsException(UFResultCode.SYS_INNERNAL_ERR,
					UfUtils.getStackTraceInfo(e));
		}

		// 如果返回的结果是失败的, 则抛出异常, 均以HsException异常类型抛出
		if (!handleResult.isSuccess()) {
			Throwable cause = handleResult.getCause();

			if (null != cause) {
				if (cause instanceof HsException) {
					throw (HsException) cause;
				}

				throw new HsException(UFResultCode.FAILED,
						UfUtils.getStackTraceInfo(cause));
			}

			String params = " --> 传参信息： "
					+ JSON.toJSONStringWithDateFormat(packetHeader,
							"yyyy-MM-dd HH:mm:ss",
							SerializerFeature.WriteDateUseDateFormat);

			throw new HsException(handleResult.getResultCode(),
					handleResult.getResultDesc() + params);
		}

		// 返回响应结果数据对象
		return handleResult.getResultData();
	}

	/**
	 * 初始化
	 * 
	 * @param regionPacketDataService
	 */
	private void init(IUFRegionPacketDataService regionPacketDataService) {
		if (null == regionPacketDataService) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"请事先创建一个实现接口IUFRegionPacketDataService的类,"
							+ " 然后将其进行实例化并注入到UFRegionPacketSupport的构造函数中!");
		}

		// 服务群组
		String serviceGroup = UfPropertyConfigurer
				.getProperty(UFConstant.KEY_SYSTEM_ID);

		if (StringUtils.isEmpty(serviceGroup)) {
			throw new HsException(UFResultCode.PARAM_INVALID,
					"当前业务子系统的properties配置文件中没有配置" + UFConstant.KEY_SYSTEM_ID
							+ "属性值! 综合前置需要根据该属性值来识别各个业务子系统, 所以必须进行配置.");
		}

		// 向Dubbo Server订阅服务
		DubboServiceSupport.getInstance().registerSyncServiceToServer(
				IUFRegionPacketDataService.class, regionPacketDataService,
				serviceGroup);
	}
}
