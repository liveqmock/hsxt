/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.gy.hsxt.access.pos.model.PosTransNote;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.ps.bean.PosEarnest;
import com.gy.hsxt.ps.bean.QuerySingle;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: EarnestSearchAction
 * @Description:根据消费者卡号查询生效中的定金
 */
@Service("earnestSearchAction")
public class EarnestSearchAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_EARNEST_SEARCH_ID;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;

    @Override
    public String getReqType() {
        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.debug("EarnestSearchAction", "action()","entering method. pos 请求参数" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String custId = (String)cmd.getTmpMap().get("custId");
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
        String entCustId = entStatusInfo.getEntCustId();
        

        SystemLog.debug("EarnestSearchAction","action()","查询生效定金, custId:" + custId + 
        						"   ; entCustId:" + entCustId);
        
        QuerySingle querySingle = new QuerySingle();
        querySingle.setEntCustId(entCustId);
        querySingle.setPerCustId(custId);
        Page page = new Page(1,9);
        
        // 调用接口
        PageData<PosEarnest> qr = psApiService.searchPosEarnest(querySingle, page);
        // 获取返回值
        List<PosEarnest> earnestList = qr.getResult();
        
        
        CommonUtil.checkState(null == qr || 0 == qr.getCount(), "生效中的定金单据未找到", PosRespCode.EARNEST_NOT_FOUND);
        
        
        final List<PosTransNote> list = new ArrayList<>();
        for (PosEarnest earnest : earnestList)
        	try{
        		list.add(new PosTransNote(earnest.getSourceTransNo(), new BigDecimal(earnest.getTransAmount()), 
    					earnest.getSourceTransDate()));
        	}catch(NullPointerException npe){
        		SystemLog.warn("EarnestSearchAction","action()", "定金检索，ps返回值中有null项，已忽略。");
        	}
        	
        
        
        //组装返回域
        //2域 消费者卡号  手输、刷卡有，扫码无
        boolean exist2area = false;
        for (int i = 0; i < cmd.getIn().size(); i++) {
			BitMap BitMap = cmd.getIn().get(i);
			if(BitMap.getBit() == 2) {
				cmd.getIn().set(i, new BitMap(2, custCardNo));
				exist2area = true;
			}
		}
        if(!exist2area)cmd.getIn().add(new BitMap(2, custCardNo));
        
        
        cmd.getIn().add(new BitMap(48, reqId, list, cmd.getPartVersion()));

        return cmd;
    }

}
