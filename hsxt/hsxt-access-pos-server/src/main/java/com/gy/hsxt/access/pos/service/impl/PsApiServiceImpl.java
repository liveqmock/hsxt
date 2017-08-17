/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.service.PsApiService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsPointService;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.api.IPsSettleService;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointRecordResult;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.PosEarnest;
import com.gy.hsxt.ps.bean.QueryPointRecord;
import com.gy.hsxt.ps.bean.QueryPosSingle;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;
import com.gy.hsxt.ps.bean.ReturnResult;
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service.impl  
 * @ClassName: PsApiServiceImpl 
 * @Description: 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:13:41 
 * @version V1.0
 */

@Service("psApiService")
public class PsApiServiceImpl implements PsApiService {

    @Autowired
    @Qualifier("psPointService")
    private IPsPointService psPointService;

    @Autowired
    @Qualifier("psQueryService")
    private IPsQueryService psQueryService;

    @Autowired
    @Qualifier("psSettleService")
    private IPsSettleService psSettleService;

    @Override
    public PointResult point(Point point) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "point()", "积分系统接口 请求参数:"+JSON.toJSONString(point));
        PointResult pr = psPointService.point(point);
        SystemLog.debug("PsApiServiceImpl", "point()", "积分系统接口 返回结果:"+JSON.toJSONString(pr));
        return pr;
    }


    @Override
    public CancelResult cancelpoint(Cancel cancel) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "cancelpoint()", "积分系统接口 请求参数"+JSON.toJSONString(cancel));
        CancelResult cr = psPointService.cancelPoint(cancel);
        SystemLog.debug("PsApiServiceImpl", "cancelpoint()", "积分系统接口 返回结果"+JSON.toJSONString(cr));
        return cr;

    }

    @Override
    public ReturnResult reversePoint(Correct correct) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "reversePoint()", "积分系统接口  ps:returnPoint() 请求参数"+JSON.toJSONString(correct));
        ReturnResult rr = psPointService.returnPoint(correct);
        SystemLog.debug("PsApiServiceImpl", "reversePoint()", "积分系统接口  ps:returnPoint() 返回结果"+JSON.toJSONString(rr));
        return rr;
    }


    @Override
    public BackResult backPoint(Back back) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "backPoint()", "积分系统接口 请求参数"+JSON.toJSONString(back));
        BackResult br = psPointService.backPoint(back);
        SystemLog.debug("PsApiServiceImpl", "backPoint()", "积分系统接口 返回结果"+JSON.toJSONString(br));
        return br;
    }

    @Override
    public QueryResult singlePosQuery(QuerySingle qs) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "singlePosQuery(QuerySingle)", "积分系统接口 请求参数"+JSON.toJSONString(qs));
        QueryResult qr = psQueryService.singlePosQuery(qs);
        SystemLog.debug("PsApiServiceImpl", "singlePosQuery(QuerySingle)", "积分系统接口 返回结果"+JSON.toJSONString(qr));
        return qr;
    }
    
    
    @Override
    public QueryResult singlePosQuery(QueryPosSingle queryPosSingle) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "singlePosQuery(QueryPosSingle)", "积分系统接口 请求参数"+JSON.toJSONString(queryPosSingle));
        QueryResult qr = psQueryService.singlePosQuery(queryPosSingle);
        SystemLog.debug("PsApiServiceImpl", "singlePosQuery(QueryPosSingle)", "积分系统接口 返回结果"+JSON.toJSONString(qr));
        return qr;
    }
    
    
    @Override
    public PageData<PosEarnest> searchPosEarnest(QuerySingle querySingle, Page page) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "searchPosEarnest()", "积分系统接口 请求参数 "+JSON.toJSONString(querySingle));
        PageData<PosEarnest> qr = psQueryService.searchPosEarnest(querySingle, page);
        SystemLog.debug("PsApiServiceImpl", "searchPosEarnest()", "积分系统接口 返回结果"+JSON.toJSONString(qr));
        return qr;
    }
    
    
    @Override
    public PointResult earnestSettle(Point point) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "earnestSettle()", "积分系统接口 请求参数:"+JSON.toJSONString(point));
        PointResult pr = psPointService.earnestSettle(point);
        SystemLog.debug("PsApiServiceImpl", "earnestSettle()", "积分系统接口 返回结果:"+JSON.toJSONString(pr));
        return pr;
    }

    @Override
    public void batSettle(BatSettle batSettle) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "batSettle()", "积分系统接口 请求参数"+JSON.toJSONString(batSettle));
        psSettleService.batSettle(batSettle);
    }

    @Override
    public void batUpload(List<BatUpload> list) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "batUpload()", "积分系统接口 请求参数"+JSON.toJSONString(list));
        psSettleService.batUpload(list);
    }
    
    //start--added by liuzh on 2016-06-23
    @Override
    public List<PointRecordResult> pointRecord(QueryPointRecord queryPointRecord) throws HsException {
        SystemLog.debug("PsApiServiceImpl", "pointRecord()", "积分系统接口 请求参数:"+JSON.toJSONString(queryPointRecord));
        List<PointRecordResult> list = psQueryService.pointRecord(queryPointRecord);
        SystemLog.debug("PsApiServiceImpl", "earnestSettle()", "积分系统接口 返回结果:"+JSON.toJSONString(list));
        return list;
    }
    //end--added by liuzh on 2016-06-23
    
}
