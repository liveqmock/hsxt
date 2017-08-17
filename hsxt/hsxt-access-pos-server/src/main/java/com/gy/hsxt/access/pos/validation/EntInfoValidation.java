/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.validation;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.uc.as.bean.device.AsDevice.AsDeviceStatusEnum;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.validation  
 * @ClassName: EntInfoValidation 
 * @Description: 企业信息校验 预处理
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:44:53 
 * @version V1.0
 */
@Service("entInfoValid")
public class EntInfoValidation implements Command {
	
	@Autowired
	@Qualifier("ucApiService")
	private UcApiService ucApiService;

	/**
	 * return false 会继续执行chain中后续的command，return true就不会了。
	 */
	@Override
	public boolean execute(Context context) throws Exception {
		SystemLog.info("Pos","EntInfoValidation","validation ent info");
		
		Cmd cmd = (Cmd)context.get("cmd");
		
		PosReqParam param = cmd.getPosReqParam();
		
		String entNo = param.getEntNo();
		String posNo = param.getPosNo();
		
		// 查询企业状态  + pos机状态
		AsEntStatusInfo entInfo = ucApiService.searchEntStatusInfoByResNo(entNo);
		
		SystemLog.info("Pos","EntInfoValidation","企业信息" + JSON.json(entInfo));
		
		
		CommonUtil.checkState(null == entInfo, "无此企业信息：" + entNo, PosRespCode.NO_FOUND_ENT);
		CommonUtil.checkState(entInfo.getIsClosedEnt().equals(PosConstant.POS_TRUE), "企业已被冻结：" + entNo, PosRespCode.CHECK_ACCOUNT_FREEZE);
		
		/**
         * @author XXX wucl create_date 2015-11-10 下午3:27:28
         * 
         * 基本状态   1：正常(NORMAL)  成员企业、托管企业用
                    2：预警(WARNING) 成员企业、托管企业用
                    3：休眠(DORMANT) 成员企业用
                    4：长眠（申请注销）成员企业
                    5：已注销 成员企业
                    6：申请停止积分活动中  托管企业用
                    7：停止积分活动    托管企业用
         */
		CommonUtil.checkState(entInfo.getBaseStatus() == 7, "企业基础状态异常：" + entNo, PosRespCode.NO_ENT_CARDPOINT);
		CommonUtil.checkState(entInfo.getBaseStatus() == 5, "企业已被注销：" + entNo, PosRespCode.NO_FOUND_ENT);
		
		//pos 设备校验
		AsPos posInfo = ucApiService.findPosByDeviceNo(entNo, posNo);
		
		CommonUtil.checkState(null == posInfo, "没找到POS设备, 编号：" + entNo + posNo, PosRespCode.NO_FOUND_DEVICE);
		SystemLog.info("Pos","EntInfoValidation","pos信息" + JSON.json(entInfo));
		/**
		 * @author wucl create_date 2015-11-10 下午3:56:45
		 * 
		 *    可用      ENABLED(1),
                            禁用       DISABLED(2),
                            返修       REPAIRED(3),
                            冻结          LOCKED(4);
		 */
		CommonUtil.checkState(posInfo.getStatus() != AsDeviceStatusEnum.ENABLED.getValue(), "pos机状态异常, 编号：" + entNo + posNo, PosRespCode.POS_STATUS);
		
		cmd.getTmpMap().put("entStatusInfo", entInfo);
		cmd.getTmpMap().put("posInfo", posInfo);
		
		return false;
	}

}

	