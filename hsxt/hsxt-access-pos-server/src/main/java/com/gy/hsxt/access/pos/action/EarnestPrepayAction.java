/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import com.gy.hsxt.access.pos.model.Poi48;
import com.gy.hsxt.access.pos.model.Poi63;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.VersionAdapter;
import com.gy.hsxt.access.pos.service.AcApiService;
import com.gy.hsxt.access.pos.service.CommonService;
import com.gy.hsxt.access.pos.service.LcsClientService;
import com.gy.hsxt.access.pos.service.PlatformParamService;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.service.UFApiService;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: EarnestPrepayAction
 * @Description: 预付定金
 * 
 * @author: zoukun
 * @date: 2016-03-04
 * @version V1.0
 */
@Service("earnestPrepayAction")
public class EarnestPrepayAction implements IBasePos {
	//请求类型，在《（POS）终端应用规范 消息域-消息交换说明》中定义
    public static final String reqType = PosConstant.REQ_EARNEST_PREPAY_ID;

    
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    @Autowired
    @Qualifier("acApiService")
    private AcApiService acApiService;

    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;

    @Autowired
    @Qualifier("lcsClientService")
    private LcsClientService lcsClientService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("uFApiService")
    private UFApiService uFApiService;
    
    @Autowired
    @Qualifier("platformParamService")
    private PlatformParamService platformParamService;

    @Override
    public String getReqType() {

        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("EarnestPrepayAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
        
        String custCardNo = (String)cmd.getTmpMap().get("custCardNo");
        String transType = TransType.LOCAL_CARD_LOCAL_EARNEST.getCode();
        transType = (boolean)cmd.getTmpMap().get("localCardFlag")? 
        			transType : TransType.REMOTE_CARD_LOCAL_EARNEST.getCode();

        SystemLog.debug("EarnestPrepayAction", "action()", "custCardNo：" + custCardNo + 
        		"; transType：" + transType);
        		
        //4 提取支付参数并作基础校验
        //48域 归一自定义信息
        final Poi48 poi = (Poi48) reqParam.getGyBean();
        //以互生币计量的定金金额
        BigDecimal hsbAmount = poi.getHsbAmount();
        
        
        //5 组装支付参数
        // 企业与客户信息
        AsEntStatusInfo entStatusInfo = (AsEntStatusInfo) cmd.getTmpMap().get("entStatusInfo");
        Point point = new Point();
        point.setChannelType(Channel.POS.getCode());
        point.setEntCustId(entStatusInfo.getEntCustId());
        point.setEntResNo(reqParam.getEntNo());
        point.setEntName(entStatusInfo.getEntCustName());
        point.setEquipmentNo(reqParam.getPosNo());
        point.setEquipmentType(CommonConstant.EQUIPMENT_POS.getCode());
        point.setOperNo(reqParam.getOperNo());
        point.setPerCustId((String)cmd.getTmpMap().get("custId"));
        point.setPerResNo(custCardNo);
        point.setSourceBatchNo(reqParam.getTradeTypeInfoBean().getBatNo());
        point.setSourceCurrencyCode(reqParam.getCurrencyCode());
        point.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
        point.setSourceTransDate(DateUtil.getCurrentDateTime());
        point.setSourceTransNo(cmd.getPointId());
        point.setTransAmount(String.format("%1$.2f", hsbAmount));
        point.setTermRunCode(reqParam.getPosRunCode());
        point.setTermTradeCode(reqParam.getTermTradeCode());
        point.setTermTypeCode(reqParam.getTradeTypeInfoBean().getTermTypeCode());
        point.setTransType(transType);
        
        //start--added by liuzh on 2016-06-23
        VersionAdapter versionAdapter = new VersionAdapter(cmd.getPartVersion());
        versionAdapter.buildHsb(cmd,point, poi);
        //end--added by liuzh on  2016-06-23
        
        PointResult result = null;
        
        
        //6 若为异地卡则需先到发卡地扣定金 第一步
        if(TransType.REMOTE_CARD_LOCAL_EARNEST.equals(transType)){
//          if(true){//测试用  
          	//需明示前一步执行正确，必须返回唯一期望结果。
//          	CommonUtil.checkState(10010 != result.getRetCode(), "异地卡交易，交易平台ps异常，执行point()返回码期望“10010”，实际“" + 
//          				result.getRetCode() + "”", PosRespCode.POS_CENTER_FAIL);
          	//对发卡平台而言，是本地卡异地交易。发起异地平台处理请求。第二步 异地
          	point.setTransType(TransType.LOCAL_CARD_REMOTE_EARNEST.getCode());
          	//发卡平台需验卡及支付密码
          	Set<String> checkItem = new HashSet<String>();
          	checkItem.add("card");checkItem.add("TP");
          	//为发卡平台验卡提供参数
          	JSONObject p = commonValidation.assembleRemoteValidParm(cmd, checkItem);
          	p.put("transType", TransType.LOCAL_CARD_REMOTE_EARNEST.getCode());
          	//point.setCardValidParm(p);//待ps更新bean
          	result = null;
          	try{
          		result = (PointResult) commonService.sendCrossPlatformIndicate(point,custCardNo,"hsbPay");
          	}catch(HsException he){
          		String subSys = commonService.checkSubSysByHsErrCode(he.getErrorCode());
          		//因为已经执行了第一步，这里的任何执行失败，都需要为第一步冲正。即便第一步中ps可能并未有实际转账动作，但为了不涉及ps的业务，这里仍然发起。
          		CommonUtil.checkState(true, "异地卡交易，互生币支付，发卡平台处理失败，调用 "+ subSys +" 服务发生互生异常。异常码：" + 
          				he.getErrorCode() + " 异常信息：" + he.getLocalizedMessage(), PosRespCode.AGAIN_REVERSE, he);
          	}catch(Exception e){
          		CommonUtil.checkState(true, "异地卡交易，互生币支付，发卡平台处理失败，调用综合前置服务抛出非互生异常。" , PosRespCode.AGAIN_REVERSE, e);
          	}
          	//第二步执行失败，则需要回滚第一步。
          	CommonUtil.checkState(null == result, "异地卡交易，发卡平台ps异常，执行point()返回null", PosRespCode.AGAIN_REVERSE);
//          	CommonUtil.checkState(10020 != result.getRetCode(), "异地卡交易，发卡平台ps异常，执行point()返回码期望“10011”，实际“" + 
//          				result.getRetCode() + "”", PosRespCode.AGAIN_REVERSE);
//              SystemLog.info(this.getClass().getName(), "action()", "发卡平台ps处理完成。retCode：" +  result.getRetCode() +
//              			"retMsg：" +  result.getRetMsg());
          	
          	//本地交易平台继续处理 第二步
            point.setTransType(transType);
          }
        
        
        
        //7 发起定金预付请求  交易地处理
        SystemLog.debug("EarnestPrepayAction", "action()", "发起交易地预付定金请求。point：" +  JSON.toJSONString(point));
        try{
            result = psApiService.point(point);
        }catch (HsException he){
        	//互生异常做映射处理
        	SystemLog.error("EarnestPrepayAction", "action()", 
        				"预付定金失败，调用ps服务发生互生异常。请求预付定金参数：" + JSON.toJSONString(point), he);
        	throw he;
        }catch (Exception e){
        	CommonUtil.checkState(true, "预付定金失败，调用ps服务发生非互生异常。请求预付定金参数：" + JSON.toJSONString(point), 
                		PosRespCode.AGAIN_REVERSE, e);
        }
        CommonUtil.checkState(null == result, "预付定金失败，ps异常，执行point()返回null", PosRespCode.POS_CENTER_FAIL);
        
        
        
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
        
        //国际信用卡公司代码
        cmd.getIn().add(new BitMap(63, PosConstant.ICC_CODE_GYT));
        
//        cmd.setRespCode(PosRespCode.AGAIN_REVERSE);// kend test 触发冲正
        
        return cmd;
    }
     


}
