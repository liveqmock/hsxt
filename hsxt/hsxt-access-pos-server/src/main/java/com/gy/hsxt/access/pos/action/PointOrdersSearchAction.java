
package com.gy.hsxt.access.pos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PointOrdersSearchReq;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosDateUtil;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.QueryPointRecord;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/** 
 * @Description: 查询积分卡积分交易单

 * @author: liuzh 
 * @createDate: 2016年6月23日 上午9:54:47
 * @version V1.0 
 */
@Service("pointOrdersSearchAction")
public class PointOrdersSearchAction implements IBasePos {
	/*查询积分卡积分交易单	0920/0930	700010    */
    public static final String reqType = PosConstant.REQ_POINT_ORDERS_SEARCH_ID;
    
    @Autowired
    @Qualifier("psApiService")
    private PsApiService psApiService;
    
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
        SystemLog.info("PointOrderSearchAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));

        commonValidation.reqParamValid(cmd);
                
        Object resultObj = null;  
        List<PointRecordResult> list = null;
        int recordCount = 0;
        QueryPointRecord queryPointRecord = null;        
        PointOrdersSearchReq ordersSearchReq = (PointOrdersSearchReq)reqParam.getGyBean();
        String lastDays = ordersSearchReq.getLastDays();
    	SystemLog.debug("PointOrderSearchAction", "action()", "lastDays:" + lastDays + ",reqId:" + reqId);
    	
    	
    	AsEntStatusInfo entInfo = (AsEntStatusInfo)cmd.getTmpMap().get("entStatusInfo");
    	String entCustId = null;
    	if(entInfo!=null) {
    		entCustId = entInfo.getEntCustId();
    	}
    	
        if (PosConstant.REQ_POINT_ORDER_CANCEL_SEARCH_ID.equals(reqId)){
        	
        	//可撤单的积分交易单列表
        	String[] startEndDates = PosDateUtil.getTodayStartEndDateYMD();
        	String startDate = startEndDates[0];
        	String endDate = startEndDates[1];
        	
        	SystemLog.debug("PointOrderSearchAction", "action()", 
        			"lastDays:" + lastDays + ",startDate:" + startDate + ",endDate:" + endDate);
        	
        	queryPointRecord = new QueryPointRecord();
        	queryPointRecord.setEntCustId(entCustId);
           	queryPointRecord.setHsResNo(reqParam.getCardNo());
        	queryPointRecord.setStartDate(startDate);
        	queryPointRecord.setEndDate(endDate);
        	queryPointRecord.setBusinessType(PosConstant.TRANS_NO_CANCEL11);
        	queryPointRecord.setPageNumber(1);
        	queryPointRecord.setCount(PosConstant.DAYSEARCH_SIZE);

        	list = psApiService.pointRecord(queryPointRecord);
        	recordCount = list.size();
        	resultObj = list;
        	
        }else if (PosConstant.REQ_POINT_ORDER_BACK_SEARCH_ID.equals(reqId)){
        	//可退货的积分交易单列表
        	int days = Integer.parseInt(lastDays);
        	String[] startEndDates = PosDateUtil.getBeforeDaysStartEndDateYMD(days);
        	String startDate = startEndDates[0];
        	String endDate = startEndDates[1];
        	
        	SystemLog.debug("PointOrderSearchAction", "action()", 
        			"lastDays:" + lastDays + ",days:" + days + ",startDate:" + startDate + ",endDate:" + endDate);
        	
        	queryPointRecord = new QueryPointRecord();
        	queryPointRecord.setEntCustId(entCustId);
           	queryPointRecord.setHsResNo(reqParam.getCardNo());
        	queryPointRecord.setStartDate(startDate);
        	queryPointRecord.setEndDate(endDate);        	
        	queryPointRecord.setBusinessType(PosConstant.TRANS_NO_BACK12);
        	queryPointRecord.setPageNumber(1);
        	queryPointRecord.setCount(PosConstant.DAYSEARCH_SIZE);
        	
        	list = psApiService.pointRecord(queryPointRecord);
          	recordCount = list.size();
        	resultObj = list;
        	
        }else {
        	throw new PosException(PosRespCode.REQUEST_PACK_FORMAT, "查询方式错误");   
        }
        
        CommonUtil.checkState((null == resultObj || recordCount<=0) , "查询积分卡积分交易单,返回空", PosRespCode.NO_FOUND_HSEC_ORDER);
        
        cmd.getIn().add(new BitMap(48, reqId, resultObj, cmd.getPartVersion()));

        return cmd;
    }
}


