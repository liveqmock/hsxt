/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.UploadParamPosIn;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: ParamUploadAction
 * @Description:参数上传
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:48:36
 * @version V1.0
 */
@Service("paramUploadAction")
public class ParamUploadAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_PARAM_UPLOAD_ID;

    @Autowired
    @Qualifier("entInfoValid")
    private EntInfoValidation entInfoValid;

    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;

    @Override
    public String getReqType() {

        return reqType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("ParamUploadAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        Context ctx1 = new ContextBase();
        ctx1.put("cmd", cmd);
        Command process = new BaseActionChain(entInfoValid);
        process.execute(ctx1);

        UploadParamPosIn serviceIn = (UploadParamPosIn) reqParam.getGyBean();
        BigDecimal[] pointRates = serviceIn.getPointRates();
        String entNo = reqParam.getEntNo();
        String posNo = reqParam.getPosNo();
        String operNo = reqParam.getOperNo();

        // 积分比例参数(控制积分设置的范围)
        BigDecimal min = platformParamService.getPointRatMin();
        BigDecimal max = platformParamService.getPointRatMax();

        String[] rates = new String[pointRates.length];
        // 上传积分比例
        for (int i = 0; i < pointRates.length; i++)
        {
            rates[i] = pointRates[i].setScale(4, BigDecimal.ROUND_HALF_UP).toString();

            CommonUtil.checkState(pointRates[i].compareTo(min) < 0 || pointRates[i].compareTo(max) > 0,
            		"积分比例参数超出范围！", PosRespCode.OUT_POINTRATE_SCOPE);
        }

        // 调用uc更新积分比例
        ucApiService.updatePointRate(entNo, AsDeviceTypeEumn.POS, posNo, rates, operNo);

        // 国际信用卡公司代码+积分0.12 M/C
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        return cmd;
    }

}
