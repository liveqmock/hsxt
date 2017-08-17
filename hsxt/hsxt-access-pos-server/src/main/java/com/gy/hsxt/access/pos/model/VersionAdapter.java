
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.service.BpApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.QueryResult;

/** 
 * @Description: 版本适配

 * @author: liuzh 
 * @createDate: 2016年6月27日 下午8:36:59
 * @version V1.0 
 */

public class VersionAdapter {

	private byte[] pversion;

	public byte[] getPversion() {
		return pversion;
	}

	public void setPversion(byte[] pversion) {
		this.pversion = pversion;
	}
	
	public VersionAdapter(byte[] pversion) {
		super();
		this.pversion = pversion;
	}

	/**
	 * 构建积分数据结构 point
	 * @param point
	 * @param poi
	 */
	public void buildHsb(Cmd cmd,Point point,Poi48 poi) {
		PosReqParam reqParam = cmd.getPosReqParam();
        if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
        	//ps在3.1版本调整. 原来的sourceTransAmount为实付金额. 新增的orderAmount为消费总额
        	
            SystemLog.debug("VersionAdapter", "build(Point point,Poi48 poi)", "actualPayAmount：" + poi.getActualPayAmount() + 
            		"; deductionVoucherCount：" + poi.getDeductVoucherCount());
            
        	BigDecimal actualPayAmount = poi.getActualPayAmount();
        	int deductionVoucherCount = poi.getDeductVoucherCount();
        	//实付金额
        	if(deductionVoucherCount>0 && actualPayAmount!=null) {        	
        		point.setSourceTransAmount(String.format("%1$.2f", actualPayAmount));
        		point.setTransAmount(String.format("%1$.2f", actualPayAmount));
        	}else{
                point.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
                point.setTransAmount(String.format("%1$.2f", poi.getHsbAmount()));
        	}    
            //消费总额
            point.setOrderAmount(String.format("%1$.2f", reqParam.getTransAmount()));   
            //抵扣券张数
            point.setDeductionVoucher(deductionVoucherCount);
        }		
	}
	
	/**
	 * 构建积分数据结构 point
	 * @param point
	 * @param poi
	 */
	public void build(Cmd cmd,Point point,Poi48 poi) {
		PosReqParam reqParam = cmd.getPosReqParam();
        if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
        	//ps在3.1版本调整. 原来的sourceTransAmount为实付金额. 新增的orderAmount为消费总额
        	
            SystemLog.debug("VersionAdapter", "build(Point point,Poi48 poi)", "actualPayAmount：" + poi.getActualPayAmount() + 
            		"; deductionVoucherCount：" + poi.getDeductVoucherCount());
            
        	BigDecimal actualPayAmount = poi.getActualPayAmount();
        	int deductionVoucherCount = poi.getDeductVoucherCount();
        	//实付金额
        	if(deductionVoucherCount>0 && actualPayAmount!=null) {        	
        		point.setSourceTransAmount(String.format("%1$.2f", actualPayAmount));
        	}else{
                point.setSourceTransAmount(String.format("%1$.2f", reqParam.getTransAmount()));
        	}    
            //消费总额
            point.setOrderAmount(String.format("%1$.2f", reqParam.getTransAmount()));   
            //抵扣券张数
            point.setDeductionVoucher(deductionVoucherCount);
        }		
	}
	
	/**
	 * 构建二维码查询结果 QueryResult
	 * @param qr
	 * @param qrCodeTrans
	 */
	public void build(QueryResult qr,QrCodeTrans qrCodeTrans) {
		BigDecimal transAmount = CommonUtil.changePackDataToMoney(qrCodeTrans.getTransAmount());
		
		if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
        	//消费总额
            qr.setOrderAmount(String.valueOf(transAmount)); 	            
            //实付金额
            BigDecimal actualAmount = CommonUtil.changePackDataToMoney(qrCodeTrans.getActualAmount());
            qr.setSourceTransAmount(String.valueOf(actualAmount));	  
            //抵扣券
            int deductionVoucherCount = 0;
            if(!StringUtils.isEmpty(qrCodeTrans.getDeductionVoucherCount())) {
            	deductionVoucherCount = Integer.parseInt(qrCodeTrans.getDeductionVoucherCount());
            }
            qr.setDeductionVoucher(deductionVoucherCount);
            
            SystemLog.debug("VersionAdapter", "build(QueryResult point,QrCodeTrans poi)",
            		"qr:" + JSON.toJSONString(qr));
        }		
	}
	
	/**
	 * 构建同步参数输出数据 SyncParamPosOut
	 * @param syncParamPosOut
	 * @param bpApiService
	 */
	public void build(SyncParamPosOut syncParamPosOut, BpApiService bpApiService) {
		if(Arrays.equals(pversion, PosConstant.POSVERSION301)) {
            //5抵扣券
            //从bp获取抵扣券参数
            DeductionVoucherParam deductionVoucherParam = bpApiService.getDeductionVoucherParamInfo();
            syncParamPosOut.setDeductionVoucherCount(deductionVoucherParam.getDeductionVoucherCount());
            syncParamPosOut.setDeductionVoucherCountList(deductionVoucherParam.getDeductionVoucherCountList());
            syncParamPosOut.setDeductionVoucherParValue(deductionVoucherParam.getDeductionVoucherParValue());
            syncParamPosOut.setDeductionVoucherRate(deductionVoucherParam.getDeductionVoucherRate());    
            
            //此处暂时写死版本号  0001
            String deductionVoucherInfoVersion = StringUtils.leftPad("1",4, '0');
            syncParamPosOut.setDeductionVoucherInfoVersion(deductionVoucherInfoVersion);
            
            SystemLog.debug("VersionAdapter", "build(SyncParamPosOut point,BpApiService poi)",
            		"syncParamPosOut" + JSON.toJSONString(syncParamPosOut));
		}
	}
	
}


