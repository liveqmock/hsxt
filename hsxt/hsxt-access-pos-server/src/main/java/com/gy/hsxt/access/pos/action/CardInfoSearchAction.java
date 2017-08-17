/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: CardInfoSearchAction
 * @Description: 卡姓名查询
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:53:58
 * @version V1.0
 * 代兑互生币第一步
 * pos机3.0增加扫码支持。设备读取二维码后，按格式解析出消费者11位互生卡号后写入2域，因此报文不做变更。
 */
@Service("cardInfoSearchAction")
public class CardInfoSearchAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_CARDNAME_SEARCH_ID;

    @Autowired
    @Qualifier("signinValid")
    private SigninValidation signinValid;

    @Autowired
    @Qualifier("macValid")
    private MacValidation macValid;

    @Autowired
    @Qualifier("entInfoValid")
    private EntInfoValidation entInfoValid;

    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Override
    public String getReqType() {

        return reqType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("CardInfoSearchAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

//        Context ctx1 = new ContextBase();
//        ctx1.put("cmd", cmd);
//        Command process = new BaseActionChain(signinValid, macValid, entInfoValid);
//
//        process.execute(ctx1);
        
        //原不校验卡，但这里要支持跨地区，故仍需校验
        commonValidation.reqParamValid(cmd);
        
        
        boolean isLocalCard = (boolean)cmd.getTmpMap().get("localCardFlag");
        String cardNo = reqParam.getCardNo();
        String cardName = null;
        JSONObject remoteResult = null;
        
        if(isLocalCard){
        	//有待uc提供优化后的接口一次获取
            String cradCustId = ucApiService.findCustIdByResNo(cardNo);
            cardName = ucApiService.searchCardNameByCustId(cradCustId);
        }else{
        	remoteResult = (JSONObject) commonService.sendCrossPlatformIndicate(cardNo,cardNo,
        				"getCardOwnerName");
	    	CommonUtil.checkState(RespCode.FAIL.getCode() == remoteResult.getIntValue("respCode"), 
	    				"发卡平台oa执行异常 respDesc：" + remoteResult.get("respDesc"), PosRespCode.AGAIN_REVERSE);
	    	cardName = remoteResult.getString("cardName");
        }
        

        String abbreviateName = "";
        if (StringUtils.isNotEmpty(cardName))
        {
            abbreviateName = StringUtils.left(cardName, 1) + "X" + StringUtils.right(cardName, 1);
        }
        cmd.getIn().add(new BitMap(62, abbreviateName));
        SystemLog.debug("CardInfoSearchAction", "action()","省略后卡名称:" + abbreviateName);

        return cmd;
    }

}
