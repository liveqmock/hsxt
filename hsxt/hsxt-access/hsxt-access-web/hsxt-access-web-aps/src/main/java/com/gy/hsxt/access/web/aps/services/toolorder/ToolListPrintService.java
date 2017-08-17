/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.toolorder;

import java.util.Map;

import com.gy.hsxt.common.utils.HsResNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.PrintDetailInfo;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.api.tool.IBSToolSendService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.Shipping;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

import javax.annotation.Resource;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.toolorder
 * @className     : ToolListPrintService.java
 * @description   : 售后工具配送清单打印
 * @author        : maocy
 * @createDate    : 2016-03-09
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public class ToolListPrintService extends BaseServiceImpl implements IToolListPrintService {

    @Autowired
    private IBSToolSendService bsSendService;//BS接口服务
    
    @Autowired
    private IBSToolAfterService bsAfterService;//BS接口服务
    
    @Autowired
    private IUCAsCardHolderService ucAsCardHolderService;//用户中心消费者接口服务
    
    @Autowired
    private IUCAsEntService ucAsEntService;//用户中心企业接口服务

	@Autowired
	private EntInfoService entInfoService;
    
    /**
     * 
     * 方法名称：查询发货清单
     * 方法描述：售后订单管理-消查询发货清单
     * @param shippingId 发货单编号
     * @return
     */
	public Shipping findShippingAfterById(String shippingId) {
		return this.bsAfterService.queryShippingAfterById(shippingId);
	}
	
	public PrintDetailInfo findPrintDetailById(String shippingType,String shippingId) throws HsException{
		Shipping sh = bsSendService.queryShippingById(shippingId);
		PrintDetailInfo info = new PrintDetailInfo();
		info.setContent(sh);
		info.setHsResNo(sh.getHsResNo());
		String custId = sh.getCustId();
		if(shippingType.equalsIgnoreCase("3")){ //消费者补卡
			AsCardHolderAllInfo  allInfo = ucAsCardHolderService.searchAllInfo(custId);
			info.setContact(allInfo.getAuthInfo().getUserName());
			info.setPhoneNo(allInfo.getBaseInfo().getMobile());
		}else if(shippingType.equalsIgnoreCase("2")){//企业新增
			 AsEntAllInfo allInfo = ucAsEntService.searchEntAllInfo(custId);
			 info.setAddr(allInfo.getExtendInfo().getContactAddr());
			 info.setContact(allInfo.getExtendInfo().getContactPerson());
			 info.setEntName(allInfo.getBaseInfo().getEntName());
			 info.setPhoneNo(allInfo.getExtendInfo().getContactPhone());
			 info.setPostCode(allInfo.getExtendInfo().getPostCode());
		}else{//服务公司申报
//			 String[] shippingTypes=shippingType.split(",");
//			 //服务公司互生号
//			 String fwEntResNo=shippingTypes[1];
//			 custId=ucAsEntService.findEntCustIdByEntResNo(fwEntResNo);
			 AsEntAllInfo allInfo = entInfoService.searchEntAllInfoByResNo(HsResNoUtils.cResNoToSresNo(sh.getHsResNo()));
			 info.setAddr(allInfo.getExtendInfo().getContactAddr());
			 info.setContact(allInfo.getExtendInfo().getContactPerson());
			 info.setEntName(allInfo.getBaseInfo().getEntName());
			 info.setPhoneNo(allInfo.getExtendInfo().getContactPhone());
			 info.setPostCode(allInfo.getExtendInfo().getPostCode());
		}
		return info;
	}
	
	/**
     * 
     * 方法名称：售后工具配送列表
     * 方法描述：售后订单管理-售后工具配送列表
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	BaseParam param = new BaseParam();
        param.setOperNo((String) filterMap.get("custId"));
        param.setStartDate((String) filterMap.get("startDate"));
        param.setEndDate((String) filterMap.get("endDate"));
        param.setHsResNo((String) filterMap.get("hsResNo"));
        param.setHsCustName((String) filterMap.get("hsCustName"));
        param.setType((String)filterMap.get("type"));
        return this.bsSendService.queryShippingPage(param, page);
    }
    /**
     * 
     * 方法名称：查询售后发货清单列表
     * 方法描述：售后订单管理-售后工具配送清单打印
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页属性
     * @return
     */
    public PageData<?> findPrintListResult(Map filterMap, Map sortMap, Page page) throws HsException{
    	BaseParam param = new BaseParam();
        param.setOperNo((String) filterMap.get("custId"));
        param.setStartDate((String) filterMap.get("startDate"));
        param.setEndDate((String) filterMap.get("endDate"));
        param.setHsResNo((String) filterMap.get("hsResNo"));
        param.setHsCustName((String) filterMap.get("hsCustName"));
        param.setType((String)filterMap.get("type"));
        return this.bsAfterService.queryShippingAfterPage(param, page);
    }
    /**
     * 
     * 方法名称：查询售后发货清单打印信息
     * 方法描述：售后订单管理-售后工具配送清单打印-打印配送单
     * @param shippingId 发货单编号
     * @return
     */
    public PrintDetailInfo findPrintDetailAfterById(String shippingId) throws HsException{
		Shipping sh = bsAfterService.queryShippingAfterById(shippingId);
		PrintDetailInfo info = new PrintDetailInfo();
		info.setContent(sh);
		info.setHsResNo(sh.getHsResNo());
		String custId = sh.getCustId();
		AsEntAllInfo allInfo = ucAsEntService.searchEntAllInfo(custId);
		info.setAddr(allInfo.getExtendInfo().getContactAddr());
		info.setContact(allInfo.getExtendInfo().getContactPerson());
		info.setEntName(allInfo.getBaseInfo().getEntName());
		info.setPhoneNo(allInfo.getExtendInfo().getContactPhone());
		info.setPostCode(allInfo.getExtendInfo().getPostCode());
		return info;
	}
    
}
