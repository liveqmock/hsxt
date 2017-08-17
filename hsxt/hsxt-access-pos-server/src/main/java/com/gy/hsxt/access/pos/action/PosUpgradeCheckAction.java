package com.gy.hsxt.access.pos.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.PosUpgradeCheckReq;
import com.gy.hsxt.access.pos.service.BpApiService;
import com.gy.hsxt.access.pos.validation.CommonValidation;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;

/**
 * pos终端固件升级 - 请求版本检测
 * @author liuzh
 *
 */
@Service("posUpgradeCheckAction")
public class PosUpgradeCheckAction implements IBasePos {
    public static final String reqType = PosConstant.REQ_POS_UPGRADE_CHECK_ID;
    
    @Autowired
    @Qualifier("commonValidation")
    private CommonValidation commonValidation;
    
    @Autowired
    @Qualifier("bpApiService")
    private BpApiService bpApiService;
    
    @Override
	public String getReqType() {
		return reqType;
	}
	
    /**
     * 请求时存放pos机设备型号、固件版本号；
                ＊　应答时存放是否升级标记、该版本强制升级标记、pos机设备型号、对应型号的最新固件版本号。
     */
	@Override
	public Object action(Cmd cmd) throws Exception {
		
    	PosReqParam reqParam = cmd.getPosReqParam();
        String reqId = cmd.getReqId();
        SystemLog.info("PosUpgradeCheckAction", "action()", "entering method. reqParam:" + 
        		JSON.toJSONString(reqParam));
        
        /**
         * 校验请求报文参数
         */
        commonValidation.reqParamValid(cmd);
            
        /**
         * 获取请求报文62域内容
         */
        PosUpgradeCheckReq posUpgradeCheckReq = (PosUpgradeCheckReq)reqParam.getCustom2();
        String posDeviceType = posUpgradeCheckReq.getPosDeviceType();
        String posMachineNo = posUpgradeCheckReq.getPosMachineNo();
        String entResNo = reqParam.getEntNo();
        
        /**
         * 调用BP接口
         * 获取POS机型号、当前可升级版本号、固件下载的http地址、强制升级标记、是否更新标记、 CRC校验码;
         */
        BusinessPosUpgrade verInfo = null;        
    	try{
    		verInfo = bpApiService.getPosUpgradeVerInfo(posDeviceType,entResNo,posMachineNo);
        }catch(Exception e){
        	SystemLog.error("PosUpgradeCheckAction", "action()", 
    				"获取固件升级版本信息失败 ,调用bp业务参数接口发生异常.请求参数:" + JSON.toJSONString(posUpgradeCheckReq), e);
    	}
    	
    	//获取固件升级版本信息失败后,设置需更新版本为当前版本,作为不升级处理
		if(verInfo==null || StringUtils.isEmpty(verInfo.getPosUpgradeVerNo())){
			verInfo = new BusinessPosUpgrade();
			verInfo.setPosUpgradeVerNo(posUpgradeCheckReq.getPosCurrVerNo());
		}
		
        /**
         * 设置应答报文 
         * 62域
         */
		cmd.getIn().add(new BitMap(62, reqId, verInfo, posUpgradeCheckReq,cmd.getPartVersion()));	 
        
		return cmd;
	}
	
}
