/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsec.external.bean.OrderResult;
import com.gy.hsec.external.bean.QueryParam;
import com.gy.hsec.external.bean.QueryResult;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.EtErrorCode;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.PosTransNote;
import com.gy.hsxt.access.pos.service.EtApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.action
 * @ClassName: HsOrderSearchAction
 * @Description:互生订单查询
 * 
 * @author: wucl
 * @date: 2015-10-16 下午3:54:12
 * @version V1.0
 */
@Service("hsOrderSearchAction")
public class HsOrderSearchAction implements IBasePos {

    public static final String reqType = PosConstant.REQ_HSORDERS_SEARCH_ID;

    @Autowired
    @Qualifier("etApiService")
    private EtApiService etApiService;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;

    @Override
    public String getReqType() {
        return reqType;
    }

    @Override
    public Object action(Cmd cmd) throws Exception {
    	
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.debug("HsOrderSearchAction", "action()","entering method. pos 请求参数" + 
        		JSON.toJSONString(reqParam));
        
        commonValidation.reqParamValid(cmd);

        Object resultObj = null;
        
        //手工输入订单号查询订单 3.0中已取消
        if (PosConstant.REQ_HSORDER_SEARCH_ID.equals(reqId)){
            // 手输订单查询
            final PosTransNote hsOrder = (PosTransNote) reqParam.getGyBean();
            SystemLog.info("HsOrderSearchAction", "action()","根据订单号查询互商订单，订单号:" + hsOrder.getPosBizSeq());
            // 拼凑查询接口参数
            QueryParam qp = new QueryParam();
            qp.setOrderId(hsOrder.getPosBizSeq());
            qp.setCompanyResourceNo(reqParam.getEntNo());
            qp.setPosNo(reqParam.getPosNo());
            qp.setOperNo(reqParam.getOperNo());
            // 调用接口
            QueryResult qr = etApiService.queryOrderInfoByOrderId(qp);
            //检索不到订单
            
            //start--modified by liuzh on 2016-05-27
            /*
            CommonUtil.checkState(780 == qr.getRetCode().intValue(), qr.getRetMsg(), 
    				PosRespCode.NO_FOUND_HSEC_ORDER);
    		*/            
            CommonUtil.checkState(EtErrorCode.ORDER_NOT_EXIST.getErrorCode() == qr.getRetCode().intValue(), qr.getRetMsg(), 
    				PosRespCode.NO_FOUND_HSEC_ORDER);
            CommonUtil.checkState(EtErrorCode.USER_NOT_EXIST.getErrorCode() == qr.getRetCode().intValue(), qr.getRetMsg(), 
    				PosRespCode.NO_FOUND_ACCT);            
            //end--modified by liuzh on 2016-05-27
            
            // 获取返回对象
            OrderResult or = qr.getOrderResult();
            resultObj = or;
            //resultObj = new HsOrder(or.getOrderId(), or.getOrderAmount().doubleValue());
            
         //刷卡查询订单
        }else if (HsOrderSearchAction.reqType.equals(reqId)){
            // 刷卡查询
            final String cardNo = reqParam.getCardNo();
            SystemLog.debug("HsOrderSearchAction","action()","刷卡查询互商订单, 消费者互生号:" + cardNo);
            // 拼凑查询接口参数
            QueryParam qp = new QueryParam();
            qp.setCardNo(cardNo);
            qp.setCompanyResourceNo(reqParam.getEntNo());
            qp.setPosNo(reqParam.getPosNo());
            qp.setOperNo(reqParam.getOperNo());
            // 调用接口
            QueryResult qr = etApiService.queryOrderListByResNo(qp);
            CommonUtil.checkState(null == qr, "刷卡检索互商订单，返回空。", PosRespCode.NO_FOUND_HSEC_ORDER);
            //电商返回码780表示未检索到订单
            
            //start--modified by liuzh on 2016-05-27
            /*
            CommonUtil.checkState(780 == qr.getRetCode().intValue(), qr.getRetMsg(), 
            				PosRespCode.NO_FOUND_HSEC_ORDER);
            */            
            CommonUtil.checkState(EtErrorCode.ORDER_NOT_EXIST.getErrorCode() == qr.getRetCode().intValue(), qr.getRetMsg(), 
            				PosRespCode.NO_FOUND_HSEC_ORDER);            
            CommonUtil.checkState(EtErrorCode.USER_NOT_EXIST.getErrorCode() == qr.getRetCode().intValue(), qr.getRetMsg(), 
    				PosRespCode.NO_FOUND_ACCT);             
            //end--modified by liuzh on 2016-05-27
            
            // 获取返回值
            List<OrderResult> orl = qr.getResult();
            final List<PosTransNote> list = new ArrayList<>(32);
            for (OrderResult item : orl){
                list.add(new PosTransNote(item.getOrderId(), item.getOrderAmount()));
            }
            resultObj = list;
        }else
        	throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "查询方式错误");
        
        cmd.getIn().add(new BitMap(48, reqId, resultObj, cmd.getPartVersion()));

        return cmd;
    }

}
