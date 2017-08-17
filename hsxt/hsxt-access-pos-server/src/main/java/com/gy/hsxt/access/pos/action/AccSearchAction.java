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
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.access.pos.validation.BaseActionChain;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.access.pos.validation.EntInfoValidation;
import com.gy.hsxt.access.pos.validation.MacValidation;
import com.gy.hsxt.access.pos.validation.SigninValidation;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: AccSearchAction
 * @Description:账户查询
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:53:43
 * @version V1.0
 */
@Service("accSearchAction")
public class AccSearchAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_BALANCE_SEARCH_ID;

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
    @Qualifier("acApiService")
    private AcApiService acApiService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;

    @Override
    public String getReqType() {

        return reqType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("AccSearchAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

//        Context ctx1 = new ContextBase();
//        ctx1.put("cmd", cmd);
//        Command process = new BaseActionChain(signinValid, macValid, entInfoValid);
//        process.execute(ctx1);
        
        
        commonValidation.reqParamValid(cmd);
        

        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");

        BigDecimal ccyHsb = new BigDecimal(0); // 查询账务hsb额度
        AccountBalance balance = acApiService.searchAccBalance(entStatusInfo.getEntCustId(),
                AccountType.ACC_TYPE_POINT20110.getCode());
        SystemLog.debug(null, "action()", "账户余额 balance:" + JSON.toJSONString(balance));
        ccyHsb = null != balance? new BigDecimal(balance.getAccBalance()) : ccyHsb;
        //人民币兑积分比率
        BigDecimal hsbExchangeRate = new BigDecimal(1); 
        BigDecimal point = ccyHsb.multiply(hsbExchangeRate);

        cmd.getIn().add(new BitMap(48, reqId, new Poi48(new BigDecimal(0), ccyHsb, point), cmd.getPartVersion()));
        return cmd;
    }

}
