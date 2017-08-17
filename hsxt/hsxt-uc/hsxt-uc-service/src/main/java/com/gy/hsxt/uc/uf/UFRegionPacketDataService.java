/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.uf;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.common.service.UCAsLoginService;
import com.gy.hsxt.uc.ent.service.BsEntService;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

/** 
 * 
 * @Package: com.gy.hsxt.uc.uf  
 * @ClassName: UFRegionPacketDataService 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2015-12-2 下午5:52:37 
 * @version V1.0 


 */
/**
 * 存在跨平台业务的业务子系统必须实现这个接口，并通过Spring进行注入。 只有实现这个接口的业务子系统才能接收到其他平台发送过来的“跨平台报文”。
 */
// inject to com.gy.hsxt.uf.UFRegionPacketSupport(constructor-arg)
@Service(value = "UFRegionPacketDataService")
public class UFRegionPacketDataService implements IUFRegionPacketDataService {
	@Resource
	BsEntService entService;

	@Resource
	UCAsLoginService asLoginService;

	/**
	 * 业务系统接收并处理UF接收到的跨地区平台报文数据
	 * 
	 * @param regionPacketData
	 *            接收到的跨地区平台数据对象
	 * @return 响应结果对象
	 */
	@Override
	public Object handleReceived(RegionPacketData regionPacketData) {
		// UF报文头对象
		RegionPacketDataHeader header = regionPacketData.getHeader();
		// String srcSubsysId = header.getSrcSubsysId();
		// UF报文体内容对象
		Object bodyData = regionPacketData.getBody().getBodyContent();

		// 跨平台业务代码
		String bizCode = header.getBizCode();
		// AcrossPlatBizCode bizCodeEnum=AcrossPlatBizCode.valueOf(bizCode);
		// 根据不同的业务代码进行相应处理
		// 业务代码1
		/**
		 * AcrossPlatBizCode.TO_REGION_INIT_M_ENT.name() 向地区平台通知开启管理公司,目标子系统：UC
		 * ** TO_REGION_INIT_M_ENT,
		 * 
		 * /** 向地区平台通知开启平台企业信息,目标子系统：UC ** TO_REGION_INIT_PLAT_ENT,
		 */
		switch (bizCode) {
		case "TO_UC_LOGIN":
			return login(regionPacketData);
		case "TO_REGION_INIT_M_ENT":
			return addM(bodyData);
		case "TO_REGION_INIT_PLAT_ENT":
			return addPlat(bodyData);

		default:
			return "bizCode UC UNKNOW=" + bizCode;
		}

	}

	/**
	 * 用户中心接收消息TO_REGION_INIT_M_ENT后创建管理公司，并生成管理员，发送对应密码等。 成功后返回”ok”.
	 * 
	 * @param bodyData
	 * @return
	 */
	private Object addM(Object bodyData) {
		JSONObject json = JSONObject.parseObject((String) bodyData);
		String ret = entService.addManEntByUf(json);
		return ret;
	}

	/**
	 * 用户中心接收消息TO_REGION_INIT_PLAT_ENT后创建平台企业信息，并生成两个管理员，发送对应密码等。 成功后返回”ok”.
	 * 
	 * @param bodyData
	 * @return
	 */
	private Object addPlat(Object bodyData) {
		JSONObject json = JSONObject.parseObject((String) bodyData);
		String ret = entService.addPlatEntByUf(json);
		return ret;
	}

	/**
	 * TO_UC_LOGIN 异地登陆
	 * @param regionPacketData
	 * @return
	 */
	private Object login(RegionPacketData regionPacketData) {
		// UF报文头对象
		RegionPacketDataHeader header = regionPacketData.getHeader();
		Object loginType = header.getObligateArgsValue("type");
		// String srcSubsysId = header.getSrcSubsysId();
		// UF报文体内容对象
		Object bodyData = regionPacketData.getBody().getBodyContent();
		if ("1".equals(loginType)) { // 持卡人登陆
			// AsCardHolderLoginResult cardHolderLogin(AsConsumerLoginInfo a)
			return asLoginService
					.cardHolderLogin((AsConsumerLoginInfo) bodyData);
		} else { // 企业登陆
			// return AsOperatorLoginResult
			return asLoginService.operatorLogin((AsOperatorLoginInfo) bodyData);
		}
	}
}
