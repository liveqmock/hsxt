/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: MacValidation 
 * @Description: mac 校验 预处理
 *  对1到63域数据加密码 与pos 机加密的对比， 如果出现连续三次错误提示重新签到
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:45:22 
 * @version V1.0
 */
@Service("macValid")
public class MacValidation implements Command {
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;

	/**
	 * return false 会继续执行chain中后续的command，return true就不会了。
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		
		SystemLog.info("Pos","MacValidation","validation mac");
		
		Cmd cmd = (Cmd)context.get("cmd");
		
		PosReqParam param = cmd.getPosReqParam();
		String entNo = param.getEntNo();
		String posNo = param.getPosNo();
		
		byte[] macData = param.getMacDat();
		
		if (macData == null) {
		    throw new PosException(PosRespCode.MAC_CHECK, "MAC校验失败，MAC串为空");
			//return true;
		}
		
		byte[] typeId = cmd.getTypeId();
		// 含消息 含64域
		byte[] requestBody = cmd.getBody();
		
		//去掉64域
		byte[] newBody = new byte[requestBody.length - 8];
		System.arraycopy(requestBody, 0, newBody, 0, requestBody.length - 8);
		
		byte[] dataByte = new byte[typeId.length + newBody.length];
		
		int count = 0;
		System.arraycopy(typeId, 0, dataByte, count, typeId.length);
		
		count += typeId.length;
		System.arraycopy(newBody, 0, dataByte, count, newBody.length);
		
		SystemLog.info("Pos","MacValidation","加密报文数据============" + Hex.encodeHexString(dataByte));
		ucApiService.checkMac(entNo, posNo, dataByte, macData);

		SystemLog.info("Pos","MacValidation","MAC校验成功！");
			
		
		return false;
	}

}

	