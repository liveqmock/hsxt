/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.GlobalConstant;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.bean.Version;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
//import com.gy.hsxt.lcs.api.INotifyService;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : VersionController.java
 * 
 *  Creation Date   : 2015-7-14
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 总平台手动触发变更通知，通知子平台更新全局配置信息
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Controller
public class VersionController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IVersionService versionService;
	
	@Autowired
	IPlatService platService;
	
	@Autowired
//	private INotifyService notifyChangeService;
	IUFRegionPacketService ufRegionPacketService;
	
	@RequestMapping(value = "/version/manage")
	public void manage() {
	}
	
	@RequestMapping(value = "version/managePost")
	@ResponseBody
	public String managePost() {
		try {
			List<Version> list = versionService.findAll();
			return JSON.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
		} catch (Exception e) {
			logger.error("获取记录列表异常：", e);
		}
		return null;
	}
	
	/**
	 * 手动触发更新全局配置信息
	 * 1.调用子平台NotifyChangeRpcService.notifyChange
	 * 2.子平台回调SyncDataRpcService.querySyncData
	 * @param versionCode
	 * @param version
	 * @return
	 */
	@RequestMapping(value = "/version/notifyChange")
	@ResponseBody
	public boolean notifyChange(final String versionCode) {
		Version versionDB = versionService.queryVersion(versionCode);
		final Long version = (versionDB==null?0:versionDB.getVersion());
		try {
			Thread t = new Thread(){
				@Override
				public void run() {
				    JSONObject  param = new JSONObject();
                    param.put("tableCode", versionCode);
                    param.put("version", version);
                    // 组装报文体
                    RegionPacketBody packetBody = RegionPacketBody.build(param.toString());
                    for(Plat plat : platService.findAllPlat()){
                        if(!GlobalConstant.CENTER_PLAT_NO.equals(plat.getPlatNo())){
    					    // 组装报文头
                            RegionPacketHeader packetHeader = RegionPacketHeader.build()
                                    .setDestPlatformId(plat.getPlatNo())
                                    .setDestBizCode(AcrossPlatBizCode.TO_REGION_NOTIFY_UPDATE.name());
                            try {
                                Object resultObj =  ufRegionPacketService.sendSyncRegionPacket(packetHeader, packetBody);
                                if("ok".equals(resultObj)){
                                    logger.debug("通知平台"+plat.getPlatNo()+"成功！"); 
                                }else{
                                    logger.warn("通知平台"+plat.getPlatNo()+"失败！"); 
                                }
                            } catch (HsException e) {
                                logger.error("调用通知变更远程接口异常：", e);
                            }
                        }
                    }
				}
			};
			
			t.start();
			Version versionObj = new Version(versionCode,version,true);//设为已通知
			
			versionService.updateVersion(versionObj);
			return true;
		} catch (Exception e) {
			logger.error("通知变更异常：", e);
		}
		return false;
	}

}
